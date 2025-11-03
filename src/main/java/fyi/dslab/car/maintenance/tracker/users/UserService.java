package fyi.dslab.car.maintenance.tracker.users;

import fyi.dslab.car.maintenance.tracker.user.api.model.UserRegistrationRequestDTO;
import fyi.dslab.car.maintenance.tracker.users.mapper.UserMapper;
import fyi.dslab.car.maintenance.tracker.users.repository.UserRepository;
import fyi.dslab.car.maintenance.tracker.users.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    @Transactional
    public UserDTO create(UserRegistrationRequestDTO requestDTO) {
        UserEntity entity = userRepository.save(mapper.toUserEntity(requestDTO));
        return mapper.toUserDTO(entity);
    }
}
