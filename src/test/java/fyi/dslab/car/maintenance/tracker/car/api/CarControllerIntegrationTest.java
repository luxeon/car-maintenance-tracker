package fyi.dslab.car.maintenance.tracker.car.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import fyi.dslab.car.maintenance.tracker.IntegrationTest;
import fyi.dslab.car.maintenance.tracker.car.repository.CarRepository;
import fyi.dslab.car.maintenance.tracker.car.repository.entity.CarEntity;
import fyi.dslab.car.maintenance.tracker.common.JwtTestTokenProvider;
import fyi.dslab.car.maintenance.tracker.users.repository.UserRepository;
import fyi.dslab.car.maintenance.tracker.users.repository.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static fyi.dslab.car.maintenance.tracker.car.api.CarsControllerApi.*;
import static fyi.dslab.car.maintenance.tracker.common.ResourceLoader.readFile;
import static net.javacrumbs.jsonunit.spring.JsonUnitResultMatchers.json;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
@Sql(executionPhase = BEFORE_TEST_METHOD, value = "/db/insert-users.sql")
class CarControllerIntegrationTest {

    private static final String USER_EMAIL = "dmytro.shvechikov@example.com";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTestTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void create_whenRequestIsValid_shouldBeOk() throws Exception {
        UserEntity user = userRepository.findByEmail(USER_EMAIL)
                .orElseThrow();
        mockMvc.perform(post(PATH_CREATE, user.getId()).header(HttpHeaders.AUTHORIZATION,
                                tokenProvider.createByEmail(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFile("fixture/cars/create/request/valid.json")))
                .andExpect(status().isCreated())
                .andExpect(json().isEqualTo(readFile("fixture/cars/create/response/201.json")));
    }

    @Test
    void create_whenRequestIsInvalid_shouldReturnBadRequest() throws Exception {
        UserEntity user = userRepository.findByEmail(USER_EMAIL)
                .orElseThrow();
        mockMvc.perform(post(PATH_CREATE, user.getId()).header(HttpHeaders.AUTHORIZATION,
                                tokenProvider.createByEmail(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFile("fixture/cars/create/request/invalid.json")))
                .andExpect(status().isBadRequest())
                .andExpect(json().isEqualTo(readFile("fixture/cars/create/response/400.json")));
    }

    @Test
    void update_whenRequestIsValid_shouldBeOk() throws Exception {
        UserEntity user = userRepository.findByEmail(USER_EMAIL)
                .orElseThrow();

        String response =
                mockMvc.perform(post(PATH_CREATE, user.getId()).header(HttpHeaders.AUTHORIZATION,
                                        tokenProvider.createByEmail(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFile("fixture/cars/create/request/valid.json")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        long carId = mapper.readTree(response)
                .get("id")
                .asLong();

        mockMvc.perform(put(PATH_UPDATE, user.getId(), carId).header(HttpHeaders.AUTHORIZATION,
                                tokenProvider.createByEmail(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFile("fixture/cars/update/request/valid.json")))
                .andExpect(status().isOk())
                .andExpect(json().isEqualTo(readFile("fixture/cars/update/response/200.json").formatted(carId)));
    }

    @Test
    void update_whenRequestIsInvalid_shouldBeBadRequest() throws Exception {
        UserEntity user = userRepository.findByEmail(USER_EMAIL)
                .orElseThrow();

        String response =
                mockMvc.perform(post(PATH_CREATE, user.getId()).header(HttpHeaders.AUTHORIZATION,
                                        tokenProvider.createByEmail(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFile("fixture/cars/create/request/valid.json")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        long carId = mapper.readTree(response)
                .get("id")
                .asLong();

        mockMvc.perform(put(PATH_UPDATE, user.getId(), carId).header(HttpHeaders.AUTHORIZATION,
                                tokenProvider.createByEmail(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFile("fixture/cars/update/request/invalid.json")))
                .andExpect(status().isBadRequest())
                .andExpect(json().isEqualTo(readFile("fixture/cars/update/response/400.json")));
    }

    @Test
    void update_whenCarIdIsNotPresentInTheDb_shouldReturnNotFound() throws Exception {
        UserEntity user = userRepository.findByEmail(USER_EMAIL)
                .orElseThrow();

        mockMvc.perform(put(PATH_UPDATE, user.getId(), 99999L).header(HttpHeaders.AUTHORIZATION,
                                tokenProvider.createByEmail(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFile("fixture/cars/update/request/valid.json")))
                .andExpect(status().isNotFound())
                .andExpect(json().isEqualTo(readFile("fixture/cars/update/response/404.json")));
    }

    @Test
    void delete_whenRequestIsValid_shouldBeOk() throws Exception {
        UserEntity user = userRepository.findByEmail(USER_EMAIL)
                .orElseThrow();

        String response =
                mockMvc.perform(post(PATH_CREATE, user.getId()).header(HttpHeaders.AUTHORIZATION,
                                        tokenProvider.createByEmail(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFile("fixture/cars/create/request/valid.json")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        long carId = mapper.readTree(response)
                .get("id")
                .asLong();

        mockMvc.perform(delete(PATH_DELETE, user.getId(), carId).header(HttpHeaders.AUTHORIZATION
                        , tokenProvider.createByEmail(user)))
                .andExpect(status().isOk());

        List<CarEntity> cars = carRepository.findAll();
        assertThat(cars).isEmpty();
    }

    @Test
    void delete_whenCarIdIsNotPresentInTheDb_shouldReturnNotFound() throws Exception {
        UserEntity user = userRepository.findByEmail(USER_EMAIL)
                .orElseThrow();

        mockMvc.perform(delete(PATH_DELETE, user.getId(), 99999L).header(HttpHeaders.AUTHORIZATION,
                        tokenProvider.createByEmail(user)))
                .andExpect(status().isNotFound())
                .andExpect(json().isEqualTo(readFile("fixture/cars/delete/response/404.json")));
    }

    @Test
    void findCarsByUserId_shouldReturnOneCarInTheList() throws Exception {
        UserEntity user = userRepository.findByEmail(USER_EMAIL)
                .orElseThrow();

        String response =
                mockMvc.perform(post(PATH_CREATE, user.getId()).header(HttpHeaders.AUTHORIZATION,
                                        tokenProvider.createByEmail(user))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readFile("fixture/cars/create/request/valid.json")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        long carId = mapper.readTree(response)
                .get("id")
                .asLong();

        mockMvc.perform(get(PATH_FIND_BY_USER_ID, user.getId()).header(HttpHeaders.AUTHORIZATION,
                        tokenProvider.createByEmail(user)))
                .andExpect(status().isOk())
                .andExpect(json().isEqualTo(readFile("fixture/cars/getByUserId" + "/response" +
                        "/200_single_car.json").formatted(carId)));
    }

    @Test
    void findCarsByUserId_shouldReturnEmptyList() throws Exception {
        UserEntity user = userRepository.findByEmail(USER_EMAIL)
                .orElseThrow();

        mockMvc.perform(get(PATH_FIND_BY_USER_ID, user.getId()).header(HttpHeaders.AUTHORIZATION,
                        tokenProvider.createByEmail(user)))
                .andExpect(status().isOk())
                .andExpect(json().isEqualTo(readFile("fixture/cars/getByUserId" + "/response" +
                        "/200_empty.json")));
    }
}