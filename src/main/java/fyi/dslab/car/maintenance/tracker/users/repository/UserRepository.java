package fyi.dslab.car.maintenance.tracker.users.repository;

import fyi.dslab.car.maintenance.tracker.users.repository.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
}
