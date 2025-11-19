package fyi.dslab.car.maintenance.tracker.users.repository;

import fyi.dslab.car.maintenance.tracker.users.service.model.UserAuthCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthCodeRepository extends CrudRepository<UserAuthCode, String> {
}
