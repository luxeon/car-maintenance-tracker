package fyi.dslab.car.maintenance.tracker.users.api;

import fyi.dslab.car.maintenance.tracker.IntegrationTest;
import fyi.dslab.car.maintenance.tracker.users.repository.UserAuthCodeRepository;
import fyi.dslab.car.maintenance.tracker.users.repository.UserRepository;
import fyi.dslab.car.maintenance.tracker.users.repository.entity.UserEntity;
import fyi.dslab.car.maintenance.tracker.users.service.model.UserAuthCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static fyi.dslab.car.maintenance.tracker.auth.api.AuthControllerApi.PATH_GENERATE_AND_SEND_AUTH_CODE;
import static fyi.dslab.car.maintenance.tracker.auth.api.AuthControllerApi.PATH_LOGIN;
import static fyi.dslab.car.maintenance.tracker.common.ResourceLoader.readFile;
import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthCodeRepository userAuthCodeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void tearDown() {
        userAuthCodeRepository.deleteAll();
    }

    @Test
    void generateAndSendAuthCode_whenRequestIdValid_shouldBe201() throws Exception {
        persistUser("login@test.com", "StrongPassword123");

        mockMvc.perform(post(PATH_GENERATE_AND_SEND_AUTH_CODE).content(readFile(
                        "fixture/auth/generateAndSendAuthCode/request/valid.json"))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

        Iterable<UserAuthCode> codes = userAuthCodeRepository.findAll();
        assertThat(codes).hasSize(1);
    }

    @Test
    void generateAndSendAuthCode_whenTwoRequestMadeWithTheSameEmail_theCodeShouldNotBeRegenerated()
            throws Exception {
        persistUser("login@test.com", "StrongPassword123");

        mockMvc.perform(post(PATH_GENERATE_AND_SEND_AUTH_CODE).content(readFile(
                        "fixture/auth/generateAndSendAuthCode/request/valid.json"))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

        Iterable<UserAuthCode> codes = userAuthCodeRepository.findAll();
        assertThat(codes).hasSize(1);
        String firstAuthCode = codes.iterator().next().getAuthCode();

        mockMvc.perform(post(PATH_GENERATE_AND_SEND_AUTH_CODE).content(readFile(
                        "fixture/auth/generateAndSendAuthCode/request/valid.json"))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());

        codes = userAuthCodeRepository.findAll();
        assertThat(codes).hasSize(1);
        String secondAuthCode = codes.iterator().next().getAuthCode();

        assertThat(firstAuthCode).isEqualTo(secondAuthCode);
    }

    @Test
    void shouldAuthenticateUserWithValidCredentials() throws Exception {
        persistUser("login@test.com", "StrongPassword123");

        String requestBody = """
                {
                    "email": "login@test.com",
                    "password": "StrongPassword123"
                }
                """;

        String expectedResponse = """
                {
                    "accessToken": "${json-unit.any-string}"
                }
                """;

        mockMvc.perform(post(PATH_LOGIN).content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(json().isEqualTo(expectedResponse));
    }

    @Test
    void shouldRejectAuthenticationWhenPasswordIsInvalid() throws Exception {
        persistUser("login@test.com", "StrongPassword123");

        String requestBody = """
                {
                    "email": "login@test.com",
                    "password": "WrongPassword123"
                }
                """;

        mockMvc.perform(post(PATH_LOGIN).content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
    }

    private void persistUser(String email, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setPassword(passwordEncoder.encode(password));
        userRepository.save(userEntity);
    }
}
