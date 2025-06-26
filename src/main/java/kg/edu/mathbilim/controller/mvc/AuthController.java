package kg.edu.mathbilim.controller.mvc;


import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.edu.mathbilim.dto.user.UserDto;
import kg.edu.mathbilim.dto.user.UserTypeDto;
import kg.edu.mathbilim.service.interfaces.TranslationService;
import kg.edu.mathbilim.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final TranslationService translationService;

    @GetMapping("login")
    public String login(@RequestParam(name = "error", required = false) Boolean error,
                        @RequestParam(name = "emailVerified", required = false) Boolean emailVerified,
                        @RequestParam(name = "emailNotVerified", required = false) Boolean emailNotVerified,
                        @RequestParam(name = "email", required = false) String email,
                        Model model) {
        if (Boolean.TRUE.equals(error)) {
            model.addAttribute("error", true);
        }

        if (Boolean.TRUE.equals(emailVerified)) {
            model.addAttribute("success", "Email успешно подтвержден! Теперь вы можете войти в систему.");
        }

        if (Boolean.TRUE.equals(emailNotVerified)) {
            model.addAttribute("emailNotVerified", true);
            model.addAttribute("errorMessage", "Email не подтвержден. Проверьте почту и перейдите по ссылке подтверждения.");
            if (email != null) {
                model.addAttribute("userEmail", email);
            }
        }

        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto",userDto);
        if (userDto.getType() == null) {
            userDto.setType(new UserTypeDto());
        }
        model.addAttribute("types", translationService.getUserTypesByLanguage());
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegistration(@ModelAttribute("userDto") @Valid UserDto userDto,
                                      BindingResult result,
                                      Model model, HttpServletRequest request) {

        if (result.hasErrors()) {
            model.addAttribute("types", translationService.getUserTypesByLanguage());
            model.addAttribute("errors", result);
            return "auth/register";
        }
        try {
            userService.createUser(userDto, request);
            return "redirect:/auth/registration-success";
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при регистрации: " + e.getMessage());
            model.addAttribute("types", translationService.getUserTypesByLanguage());
            return "auth/register";
        }
    }

    @GetMapping("/registration-success")
    public String registrationSuccess() {
        return "auth/registration-success";
    }


    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, Model model) {
        try {
            boolean isVerified = userService.verifyEmail(token);

            if (isVerified) {
                return "redirect:/auth/login?emailVerified=true";
            } else {
                model.addAttribute("error", "Недействительный токен верификации");
                return "auth/verification-error";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при верификации email: " + e.getMessage());
            return "auth/verification-error";
        }
    }

    @GetMapping("/resend-verification")
    public String showResendVerificationForm() {
        return "auth/resend-verification";
    }

    @PostMapping("/resend-verification")
    public String resendVerificationEmail(HttpServletRequest request, Model model) {
        String email = request.getParameter("email");

        try {
            userService.resendVerificationEmail(request, email);
            model.addAttribute("message", "Письмо с подтверждением отправлено повторно. Проверьте вашу почту.");
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
        } catch (Exception e) {
            model.addAttribute("error", "Ошибка при отправке письма: " + e.getMessage());
        }

        return "auth/resend-verification";
    }


    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "auth/forgot-password_form";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        try {
            userService.makeResetPasswordToken(request);
            model.addAttribute("message", "We have sent a reset password link to your email. Please check.");
        } catch (UsernameNotFoundException | UnsupportedEncodingException ex) {
            model.addAttribute("error", ex.getMessage());
        } catch (MessagingException ex) {
            model.addAttribute("error", "Error while sending email");
        }
        return "auth/forgot-password_form";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(

            @RequestParam String token,

            Model model

    ) {
        try {
            userService.getUserByResetPasswordToken(token);
            model.addAttribute("token", token);
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", "Invalid token");
        }
        return "auth/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");
        try {
            UserDto user = userService.getUserByResetPasswordToken(token);
            userService.updatePassword(user.getId(), password);
            model.addAttribute("message", "You have successfully changed your password.");
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("message", "Invalid Token");
        }
        return "auth/message";
    }

    @GetMapping("/select-user-type")
    public String selectUserTypePage(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        String email = authentication.getName();
        var user = userService.getUserByEmail(email);

        if (user != null && user.getType() != null) {
            return "redirect:/";
        }

        var userTypes = translationService.getUserTypesByLanguage();
        model.addAttribute("userTypes", userTypes);
        model.addAttribute("user", user);
        model.addAttribute("email", email);

        return "auth/select-user-type";
    }

    @PostMapping("/select-user-type")
    public String selectUserType(@RequestParam("userTypeId") Integer userTypeId,
                                 Authentication authentication,
                                 RedirectAttributes redirectAttributes) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        try {
            String email = authentication.getName();
            userService.setUserType(email, userTypeId);

            redirectAttributes.addFlashAttribute("message",
                    "Тип пользователя успешно выбран!");
            return "redirect:/";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Ошибка при выборе типа пользователя: " + e.getMessage());
            return "redirect:/auth/select-user-type";
        }
    }
}
