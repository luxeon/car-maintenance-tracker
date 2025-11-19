package fyi.dslab.car.maintenance.tracker.users.service;

import fyi.dslab.car.maintenance.tracker.auth.api.model.GenerateAndSendAuthCodeRequestDTO;
import fyi.dslab.car.maintenance.tracker.users.config.UserAuthCodeProperties;
import fyi.dslab.car.maintenance.tracker.users.repository.UserAuthCodeRepository;
import fyi.dslab.car.maintenance.tracker.users.service.model.AuthenticatedUserDetails;
import fyi.dslab.car.maintenance.tracker.users.service.model.UserAuthCode;
import fyi.dslab.car.maintenance.tracker.users.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserAuthCodeRepository userAuthCodeRepository;

    private final UserAuthCodeProperties userAuthCodeProperties;

    private final JwtUtils jwtUtils;

    @Transactional(readOnly = true)
    public String authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                email,
                password));
        AuthenticatedUserDetails user = (AuthenticatedUserDetails) authentication.getPrincipal();
        return jwtUtils.generateToken(user);
    }

    @Transactional
    public void generateAndSendAuthCode(
            GenerateAndSendAuthCodeRequestDTO generateAndSendAuthCodeRequestDTO) {
        if (userAuthCodeRepository.findById(generateAndSendAuthCodeRequestDTO.getEmail())
                .isPresent()) {
            return;
        }
        long authCode = RandomUtils.secure().randomLong(1000, 10_000);
        UserAuthCode userAuthCode = new UserAuthCode(generateAndSendAuthCodeRequestDTO.getEmail(),
                String.valueOf(authCode),
                userAuthCodeProperties.getTimeToLiveSec());
        userAuthCodeRepository.save(userAuthCode);
    }
}
