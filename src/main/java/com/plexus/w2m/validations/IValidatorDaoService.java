package com.plexus.w2m.validations;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import java.util.ArrayList;
import java.util.stream.Collectors;
import com.plexus.w2m.exception.ServiceException;
public interface IValidatorDaoService<E> {
    default void validations(E entity) {
        var validator = Validation.buildDefaultValidatorFactory().getValidator();
        var violations = validator.validate(entity);
        var message = new ArrayList<String>();

        message.addAll(violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));

        if (!violations.isEmpty()) {
            throw new ServiceException(StringUtils.join(message, ". ").concat("."), HttpStatus.CONFLICT.value());
        }
    }
}
