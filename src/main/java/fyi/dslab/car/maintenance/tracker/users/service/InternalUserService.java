package fyi.dslab.car.maintenance.tracker.users.service;

import fyi.dslab.car.maintenance.tracker.users.repository.UserRepository;
import fyi.dslab.car.maintenance.tracker.users.repository.entity.UserEntity;
import fyi.dslab.car.maintenance.tracker.users.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InternalUserService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    @Transactional
    public UserEntity getOrCreate(String email) {
        try {
            return userRepository.save(mapper.toUserEntity(email));
        } catch (DuplicateKeyException _) {
            return userRepository.findByEmail(email).orElseThrow(() -> {
                log.error("Unable to create and get user by email '{}'.", email);
                return new UsernameNotFoundException("Unable to create and get user by email '" + email + "'.");
            });
        }
    }

    @Transactional
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
