package fyi.dslab.car.maintenance.tracker.car.repository;

import fyi.dslab.car.maintenance.tracker.car.repository.entity.CarEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends CrudRepository<CarEntity, Long> {

    Optional<CarEntity> findFirstByUserIdAndId(Long userId, Long carId);

    @Modifying
    int deleteByUserIdAndId(Long userId, Long carId);

    List<CarEntity> findAllByUserId(Long userId);
}
