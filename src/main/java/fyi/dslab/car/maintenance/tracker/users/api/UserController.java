package fyi.dslab.car.maintenance.tracker.users.api;

import fyi.dslab.car.maintenance.tracker.user.api.UsersControllerApi;
import fyi.dslab.car.maintenance.tracker.user.api.model.UserRegistrationRequestDTO;
import fyi.dslab.car.maintenance.tracker.user.api.model.UserResponseDTO;
import fyi.dslab.car.maintenance.tracker.users.UserDTO;
import fyi.dslab.car.maintenance.tracker.users.UserService;
import fyi.dslab.car.maintenance.tracker.users.api.mapper.UserApiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersControllerApi {

    private final UserService userService;

    private final UserApiMapper mapper;

    @Override
    public UserResponseDTO create(UserRegistrationRequestDTO userRegistrationRequestDTO) {
        UserDTO createdUser = userService.create(userRegistrationRequestDTO);
        return mapper.toUserResponseDTO(createdUser);
    }
}
