package fyi.dslab.car.maintenance.tracker.users.api.error;

import fyi.dslab.car.maintenance.tracker.common.error.ErrorDTO;
import fyi.dslab.car.maintenance.tracker.common.error.ErrorResponseDTO;
import fyi.dslab.car.maintenance.tracker.users.service.exception.UserAlreadyExistException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserRestControllerAdvice {

    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDTO handleUserAlreadyExistsException(UserAlreadyExistException e) {
        return new ErrorResponseDTO(List.of(new ErrorDTO("email", "User already exists.")));
    }

}
