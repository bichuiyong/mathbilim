package kg.edu.mathbilim.exceptions.advice;

import kg.edu.mathbilim.ErrorServiceImpl;
import kg.edu.mathbilim.exceptions.model.ErrorResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@org.springframework.web.bind.annotation.RestControllerAdvice
@RequiredArgsConstructor
public class RestControllerAdvice {
    private final ErrorServiceImpl errorService;

    @ExceptionHandler(NoSuchElementException.class)
    private ResponseEntity<ErrorResponseBody> noSuchElementHandler(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorService.makeResponse(e, "", HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    private ResponseEntity<ErrorResponseBody> illegalArgumentHandler(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorService.makeResponse(e, "invalid request", HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponseBody> validHandler(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(errorService.makeResponse(e.getBindingResult()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<ErrorResponseBody> alreadyExist (HttpMessageNotReadableException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorService.makeResponse(e, "wrong account type", HttpStatus.BAD_REQUEST));
    }
}
