package fyi.dslab.car.maintenance.tracker.users.api;

import fyi.dslab.car.maintenance.tracker.user.api.UsersControllerApi;
import fyi.dslab.car.maintenance.tracker.user.api.model.UserRegistrationRequestDTO;
import fyi.dslab.car.maintenance.tracker.user.api.model.UserResponseDTO;
import fyi.dslab.car.maintenance.tracker.users.api.mapper.UserApiMapper;
import fyi.dslab.car.maintenance.tracker.users.repository.entity.UserEntity;
import fyi.dslab.car.maintenance.tracker.users.service.InternalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersControllerApi {

    private final InternalUserService internalUserService;

    private final UserApiMapper mapper;

    @Override
    public UserResponseDTO create(UserRegistrationRequestDTO userRegistrationRequestDTO) {
        UserEntity createdUser = internalUserService.create(userRegistrationRequestDTO);
        return mapper.toUserResponseDTO(createdUser);
    }
}
