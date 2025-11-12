package fyi.dslab.car.maintenance.tracker.users.validation;

import fyi.dslab.car.maintenance.tracker.user.api.model.UserRegistrationRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class EqualPasswordsValidator implements ConstraintValidator<EqualPasswords,
        UserRegistrationRequestDTO> {

    @Override
    public boolean isValid(UserRegistrationRequestDTO requestDTO,
                           ConstraintValidatorContext constraintValidatorContext) {
        if (requestDTO == null) {
            return true;
        }
        if (requestDTO.getPassword() == null && requestDTO.getPasswordRepeat() == null) {
            return true;
        }
        return requestDTO.getPassword() != null && requestDTO.getPassword()
                .equals(requestDTO.getPasswordRepeat());
    }
}
