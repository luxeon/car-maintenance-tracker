package fyi.dslab.car.maintenance.tracker.users.api;

import fyi.dslab.car.maintenance.tracker.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.AutoConfigureDataJdbc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static fyi.dslab.car.maintenance.tracker.user.api.UsersControllerApi.PATH_CREATE;
import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureDataJdbc
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

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

        mockMvc.perform(post(PATH_CREATE)
                        .content(requestBody)
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

        mockMvc.perform(post(PATH_CREATE)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
                //.andExpect(json().isEqualTo(expectedResponse));
    }
}