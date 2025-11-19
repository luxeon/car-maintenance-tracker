package fyi.dslab.car.maintenance.tracker.users.api;

import fyi.dslab.car.maintenance.tracker.auth.api.AuthControllerApi;
import fyi.dslab.car.maintenance.tracker.auth.api.model.GenerateAndSendAuthCodeRequestDTO;
import fyi.dslab.car.maintenance.tracker.auth.api.model.LoginRequestDTO;
import fyi.dslab.car.maintenance.tracker.auth.api.model.LoginResponseDTO;
import fyi.dslab.car.maintenance.tracker.users.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerApi {

    private final AuthService authService;

    @Override
    public void generateAndSendAuthCode(
            GenerateAndSendAuthCodeRequestDTO generateAndSendAuthCodeRequestDTO) {
        authService.generateAndSendAuthCode(generateAndSendAuthCodeRequestDTO);
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        String accessToken = authService.authenticate(loginRequestDTO.getEmail(),
                loginRequestDTO.getPassword());
        return new LoginResponseDTO(accessToken);
    }
}
