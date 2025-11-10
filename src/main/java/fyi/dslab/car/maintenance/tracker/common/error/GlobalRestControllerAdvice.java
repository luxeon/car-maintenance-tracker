package fyi.dslab.car.maintenance.tracker.common.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;

import static java.util.Comparator.comparing;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;


@Slf4j
@RestControllerAdvice
public class GlobalRestControllerAdvice {

    private static ErrorDTO getErrorDTO(ObjectError error) {
        if (error instanceof FieldError fieldError) {
            return new ErrorDTO(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ErrorDTO(error.getObjectName(), error.getDefaultMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleValidationException(MethodArgumentNotValidException ex) {
        List<ErrorDTO> errors = ex.getAllErrors()
                .stream()
                .map(GlobalRestControllerAdvice::getErrorDTO)
                .sorted(comparing(ErrorDTO::field))
                .toList();
        return new ErrorResponseDTO(errors);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDTO handleHandlerMethodValidationException(
            HandlerMethodValidationException ex) {
        List<ErrorDTO> errors = ex.getParameterValidationResults()
                .stream()
                .sorted(comparing(error -> trimToEmpty(error.getMethodParameter()
                        .getParameterName())))
                .map(error -> new ErrorDTO(error.getMethodParameter()
                        .getParameterName(), error.getResolvableErrors()
                        .getFirst()
                        .getDefaultMessage()))
                .toList();

        return new ErrorResponseDTO(errors);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponseDTO handleBadCredentialsException(BadCredentialsException ex) {
        return new ErrorResponseDTO(List.of(new ErrorDTO(null, "Invalid username or password")));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDTO handleException(Exception ex) {
        log.error("Unhandled exception occurred.", ex);
        return new ErrorResponseDTO(List.of(new ErrorDTO(null, "Unhandled exception occurred" +
                ".")));
    }
}
