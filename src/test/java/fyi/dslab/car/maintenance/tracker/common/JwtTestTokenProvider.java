package fyi.dslab.car.maintenance.tracker.common;

import fyi.dslab.car.maintenance.tracker.users.repository.entity.UserEntity;
import fyi.dslab.car.maintenance.tracker.users.service.model.AuthenticatedUserDetails;
import fyi.dslab.car.maintenance.tracker.users.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTestTokenProvider {

    private final JwtUtils jwtUtils;

    public String createByEmail(UserEntity user) {
        return "Bearer " + jwtUtils.generateToken(new AuthenticatedUserDetails(user.getId(),
                user.getEmail()));
    }
}
