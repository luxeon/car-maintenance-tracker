package fyi.dslab.car.maintenance.tracker.car.service;

import fyi.dslab.car.maintenance.tracker.car.api.model.CreateCarRequestDTO;
import fyi.dslab.car.maintenance.tracker.car.api.model.UpdateCarRequestDTO;
import fyi.dslab.car.maintenance.tracker.car.repository.CarRepository;
import fyi.dslab.car.maintenance.tracker.car.repository.entity.CarEntity;
import fyi.dslab.car.maintenance.tracker.car.service.exception.CarNotFoundException;
import fyi.dslab.car.maintenance.tracker.car.service.mapper.CarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarMapper mapper;
    private final CarRepository repository;

    @Transactional
    public CarEntity create(Long userId, CreateCarRequestDTO createCarRequestDTO) {
        CarEntity car = mapper.toUser(userId, createCarRequestDTO);
        return repository.save(car);
    }

    @Transactional
    public CarEntity update(Long userId, Long carId, UpdateCarRequestDTO updateCarRequestDTO) {
        Optional<CarEntity> optionalCar = repository.findFirstByUserIdAndId(userId, carId);
        CarEntity car = optionalCar.orElseThrow(() -> new CarNotFoundException(userId, carId));
        mapper.update(car, updateCarRequestDTO);
        return repository.save(car);
    }

    @Transactional
    public void delete(Long userId, Long carId) {
        int count = repository.deleteByUserIdAndId(userId, carId);
        if (count == 0) {
            throw new CarNotFoundException(userId, carId);
        }
    }

    @Transactional(readOnly = true)
    public List<CarEntity> findByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }
}
