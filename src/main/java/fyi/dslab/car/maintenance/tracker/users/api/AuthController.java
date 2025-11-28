package fyi.dslab.car.maintenance.tracker.users.api;

import fyi.dslab.car.maintenance.tracker.auth.api.AuthControllerApi;
import fyi.dslab.car.maintenance.tracker.auth.api.model.AuthRequestDTO;
import fyi.dslab.car.maintenance.tracker.auth.api.model.AuthResponseDTO;
import fyi.dslab.car.maintenance.tracker.auth.api.model.GenerateAndSendAuthCodeRequestDTO;
import fyi.dslab.car.maintenance.tracker.users.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthControllerApi {

    private final AuthService authService;

    @Override
    public AuthResponseDTO auth(AuthRequestDTO authRequestDTO) {
        String accessToken = authService.authenticate(authRequestDTO.getEmail(),
                authRequestDTO.getAuthCode());
        return new AuthResponseDTO(accessToken);
    }

    @Override
    public void generateAndSendAuthCode(
            GenerateAndSendAuthCodeRequestDTO generateAndSendAuthCodeRequestDTO) {
        authService.generateAndSendAuthCode(generateAndSendAuthCodeRequestDTO);
    }
}
