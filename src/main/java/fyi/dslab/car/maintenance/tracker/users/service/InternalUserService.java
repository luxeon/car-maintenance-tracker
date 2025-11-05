package fyi.dslab.car.maintenance.tracker.users.service;

import fyi.dslab.car.maintenance.tracker.user.api.model.UserRegistrationRequestDTO;
import fyi.dslab.car.maintenance.tracker.users.mapper.UserMapper;
import fyi.dslab.car.maintenance.tracker.users.repository.UserRepository;
import fyi.dslab.car.maintenance.tracker.users.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InternalUserService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    @Transactional
    public UserEntity create(UserRegistrationRequestDTO requestDTO) {
        return userRepository.save(mapper.toUserEntity(requestDTO));
    }
}
