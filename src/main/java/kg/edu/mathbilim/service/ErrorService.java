package kg.edu.mathbilim.service;

import kg.edu.mathbilim.exception.model.ErrorResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

public interface ErrorService {

    ErrorResponseBody makeResponse (Exception e, String title, HttpStatus status);

    ErrorResponseBody makeResponse(BindingResult bindingResult);
}
