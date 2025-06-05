package kg.edu.mathbilim;

import kg.edu.mathbilim.exceptions.model.ErrorResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;

public interface ErrorService {

    ErrorResponseBody makeResponse(Exception e, String title, HttpStatus status);

    public ErrorResponseBody makeResponse(BindingResult bindingResult);

}
