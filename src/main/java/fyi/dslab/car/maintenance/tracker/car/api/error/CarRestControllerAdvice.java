package fyi.dslab.car.maintenance.tracker.car.api.error;

import fyi.dslab.car.maintenance.tracker.car.service.exception.CarNotFoundException;
import fyi.dslab.car.maintenance.tracker.common.error.ErrorDTO;
import fyi.dslab.car.maintenance.tracker.common.error.ErrorResponseDTO;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CarRestControllerAdvice {

    @ExceptionHandler(CarNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDTO handleCarNotFoundException(CarNotFoundException e) {
        return new ErrorResponseDTO(List.of(new ErrorDTO("car",
                "Car with id '%d' is not found.".formatted(e.getCarId()))));
    }

}
