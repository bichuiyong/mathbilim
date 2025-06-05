package kg.edu.mathbilim.exception.advice;

import jakarta.servlet.http.HttpServletRequest;
import kg.edu.mathbilim.exception.customExceptions.NotFound;
import kg.edu.mathbilim.service.impl.ErrorServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@RequiredArgsConstructor
public class MvcControllerAdvice {
    private final ErrorServiceImpl errorService;

    @ExceptionHandler(NotFound.class)
    public String handleNoSuchElementException(Model model, HttpServletRequest request) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("details", request);
        return "errors/error";
    }
}
