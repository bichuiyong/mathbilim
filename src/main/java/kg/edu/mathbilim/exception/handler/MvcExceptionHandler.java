package kg.edu.mathbilim.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.NoSuchElementException;

@ControllerAdvice(annotations = Controller.class)
public class MvcExceptionHandler {
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(Model model, HttpServletRequest request, NoHandlerFoundException e) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("details", request);
        return "error/error";
    }

    @ExceptionHandler(MultipartException.class)
    public String handleMultipartException(Model model, HttpServletRequest request, MultipartException e) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        return "error/error";
    }

    @ExceptionHandler(IllegalStateException.class)
    public String handleISE(Model model, HttpServletRequest request, IllegalStateException e) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        return "error/error";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntime(Model model, HttpServletRequest request, RuntimeException e) {
        model.addAttribute("status", HttpStatus.CONFLICT.value());
        model.addAttribute("reason", HttpStatus.CONFLICT.getReasonPhrase());
        model.addAttribute("details", request);
        return "error/error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIAE(Model model, HttpServletRequest request, IllegalArgumentException e) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        return "error/error";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolation(Model model, HttpServletRequest request, ConstraintViolationException ex) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        return "error/error";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNSEE(Model model, HttpServletRequest request, NoSuchElementException e) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("details", request);
        return "error/error";
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public String handleDataIntegrityViolation(Model model, HttpServletRequest request, DataIntegrityViolationException e) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        return "error/error";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(Model model, HttpServletRequest request, AccessDeniedException e) {
        model.addAttribute("status", HttpStatus.FORBIDDEN.value());
        model.addAttribute("reason", HttpStatus.FORBIDDEN.getReasonPhrase());
        model.addAttribute("details", request);
        return "error/error";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidation(Model model, HttpServletRequest request, MethodArgumentNotValidException e) {
        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("reason", HttpStatus.BAD_REQUEST.getReasonPhrase());
        model.addAttribute("details", request);
        return "error/error";
    }
}