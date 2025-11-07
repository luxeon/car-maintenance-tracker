package fyi.dslab.car.maintenance.tracker.users.api;

import fyi.dslab.car.maintenance.tracker.IntegrationTest;
import fyi.dslab.car.maintenance.tracker.users.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static fyi.dslab.car.maintenance.tracker.user.api.UsersControllerApi.PATH_CREATE;
import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void cleanUpUsers() {
        userRepository.deleteAll();
    }

    @Test
    void shouldRegisterNewUser() throws Exception {
        String requestBody = """
                {
                    "email": "test@example.com",
                    "password": "SomePassword123",
                    "passwordRepeat": "SomePassword123"
                }
                """;

        String expectedResponse = """
                {
                "id": "${json-unit.any-number}",
                "email": "test@example.com"
                }
                """;

        mockMvc.perform(post(PATH_CREATE).content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(json().isEqualTo(expectedResponse));
    }

    @Test
    void shouldNotRegisterUserBecausePasswordsAreNotEqual() throws Exception {
        String requestBody = """
                {
                    "email": "test@example.com",
                    "password": "SomePassword123",
                    "passwordRepeat": "AnotherPassword"
                }
                """;

        //        String expectedResponse = """
        //                {
        //                "id": "${json-unit.any-number}",
        //                "email": "test@example.com"
        //                }
        //                """;

        mockMvc.perform(post(PATH_CREATE).content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        //.andExpect(json().isEqualTo(expectedResponse));
    }
}