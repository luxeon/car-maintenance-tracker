package fyi.dslab.car.maintenance.tracker.users.api;

import fyi.dslab.car.maintenance.tracker.user.api.UsersControllerApi;
import fyi.dslab.car.maintenance.tracker.user.api.model.UserRegistrationRequestDTO;
import fyi.dslab.car.maintenance.tracker.user.api.model.UserResponseDTO;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UsersControllerApi {

    @Override
    public UserResponseDTO create(UserRegistrationRequestDTO userRegistrationRequestDTO) {
        // TODO implement me
        return null;
    }
}
