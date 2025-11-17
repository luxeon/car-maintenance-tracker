package fyi.dslab.car.maintenance.tracker.car.api;

import fyi.dslab.car.maintenance.tracker.car.api.mapper.CarApiMapper;
import fyi.dslab.car.maintenance.tracker.car.api.model.CarDetailsResponseDTO;
import fyi.dslab.car.maintenance.tracker.car.api.model.CreateCarRequestDTO;
import fyi.dslab.car.maintenance.tracker.car.api.model.UpdateCarRequestDTO;
import fyi.dslab.car.maintenance.tracker.car.repository.entity.CarEntity;
import fyi.dslab.car.maintenance.tracker.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CarController implements CarsControllerApi {

    private final CarService service;

    private final CarApiMapper mapper;

    @Override
    public CarDetailsResponseDTO create(Long userId, CreateCarRequestDTO createCarRequestDTO) {
        CarEntity createdCar = service.create(userId, createCarRequestDTO);
        return mapper.toCreateCarResponseDTO(createdCar);
    }

    @Override
    public void delete(Long userId, Long carId) {
        service.delete(userId, carId);
    }

    @Override
    public List<CarDetailsResponseDTO> findByUserId(Long userId) {
        List<CarEntity> cars = service.findByUserId(userId);
        return cars.stream().map(mapper::toCreateCarResponseDTO).toList();
    }

    @Override
    public CarDetailsResponseDTO update(Long userId, Long carId,
                                        UpdateCarRequestDTO updateCarRequestDTO) {
        CarEntity updatedCar = service.update(userId, carId, updateCarRequestDTO);
        return mapper.toCreateCarResponseDTO(updatedCar);
    }
}
