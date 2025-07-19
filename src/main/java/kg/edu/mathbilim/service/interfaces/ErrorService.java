package kg.edu.mathbilim.service.interfaces;

import jakarta.validation.ConstraintViolation;
import kg.edu.mathbilim.exception.ErrorResponseBody;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartException;

import java.util.Set;

public interface ErrorService {
    ErrorResponseBody makeResponse(IllegalStateException e);

    ErrorResponseBody makeResponse(IllegalArgumentException e);

    ErrorResponseBody makeResponse(DataIntegrityViolationException e);

    ErrorResponseBody makeResponse(MultipartException e);

    ErrorResponseBody makeResponse(Exception e);

    ErrorResponseBody makeResponse(BindingResult bindingResult);

    ErrorResponseBody makeResponse(Set<ConstraintViolation<?>> constraintViolations);
}
