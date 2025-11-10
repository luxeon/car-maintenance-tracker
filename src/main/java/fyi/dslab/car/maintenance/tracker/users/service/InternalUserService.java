package fyi.dslab.car.maintenance.tracker.users.service;

import fyi.dslab.car.maintenance.tracker.user.api.model.UserRegistrationRequestDTO;
import fyi.dslab.car.maintenance.tracker.users.repository.UserRepository;
import fyi.dslab.car.maintenance.tracker.users.repository.entity.UserEntity;
import fyi.dslab.car.maintenance.tracker.users.service.exception.UserAlreadyExistException;
import fyi.dslab.car.maintenance.tracker.users.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InternalUserService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    @Transactional
    public UserEntity create(UserRegistrationRequestDTO requestDTO) {
        try {
            return userRepository.save(mapper.toUserEntity(requestDTO));
        } catch (DbActionExecutionException e) {
            throw new UserAlreadyExistException();
        }
    }

    @Transactional
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
