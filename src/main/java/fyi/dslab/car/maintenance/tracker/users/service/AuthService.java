package fyi.dslab.car.maintenance.tracker.users.service;

import fyi.dslab.car.maintenance.tracker.auth.api.model.GenerateAndSendAuthCodeRequestDTO;
import fyi.dslab.car.maintenance.tracker.email.EmailService;
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

    private static final int AUTH_CODE_MIN = 1000;
    private static final int AUTH_CODE_MAX = 10_000;

    private final AuthenticationManager authenticationManager;

    private final UserAuthCodeRepository userAuthCodeRepository;

    private final UserAuthCodeProperties userAuthCodeProperties;

    private final JwtUtils jwtUtils;

    private final EmailService emailService;

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
        String recipientEmail = generateAndSendAuthCodeRequestDTO.getEmail();
        if (userAuthCodeRepository.findById(recipientEmail)
                .isPresent()) {
            return;
        }
        long authCode = RandomUtils.secure().randomLong(AUTH_CODE_MIN, AUTH_CODE_MAX);
        UserAuthCode userAuthCode = new UserAuthCode(recipientEmail,
                String.valueOf(authCode),
                userAuthCodeProperties.getTimeToLiveSec());
        userAuthCodeRepository.save(userAuthCode);

        emailService.send(recipientEmail, authCode);
    }
}
