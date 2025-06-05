package kg.edu.mathbilim.exceptions.advice;

import jakarta.servlet.http.HttpServletRequest;
import kg.edu.mathbilim.ErrorServiceImpl;
import kg.edu.mathbilim.exceptions.customExeptions.NotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@RequiredArgsConstructor
public class MvcControllerAdvice {
    private final ErrorServiceImpl errorService;

    @ExceptionHandler(NotFound.class)
    public String handleNoSuchElementException(Model model, HttpServletRequest request) {
//        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
//        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
//        model.addAttribute("details", request);
        return "errors/error";
    }

}
