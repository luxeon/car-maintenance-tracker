package fyi.dslab.car.maintanance.tracker.users.api;

import fyi.dslab.car.maintanance.tracker.user.api.UsersControllerApi;
import fyi.dslab.car.maintanance.tracker.user.api.model.UserRegistrationRequestDTO;
import fyi.dslab.car.maintanance.tracker.user.api.model.UserResponseDTO;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UsersControllerApi {

    @Override
    public UserResponseDTO create(UserRegistrationRequestDTO userRegistrationRequestDTO) {
        // TODO implement me
        return null;
    }
}
