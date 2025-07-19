package kg.edu.mathbilim.controller.mvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kg.edu.mathbilim.service.impl.UserServiceImpl;
import kg.edu.mathbilim.service.impl.auth.AuthUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class TelegramAuthController {
    private final UserServiceImpl userService;
    @Value("${spring.telegram.bot-token}")
    private String BOT_TOKEN;
    private final AuthUserDetailsService authUserDetailsService;

    @GetMapping("/auth/telegram/callback")
    public String handleTelegramCallback(@RequestParam Map<String, String> params, HttpServletResponse response, HttpServletRequest request) throws IOException {
        if (verifyTelegramAuth(params)) {
            String telegramId = params.get("id");
            String firstName = params.get("first_name");
            String lastName = params.get("last_name");

            if (!userService.existsTelegramUser(telegramId)) {
                userService.createTelegramUser(Long.parseLong(telegramId), firstName, lastName);
            }

            UserDetails user = authUserDetailsService.loadUserByTelegram(telegramId);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            return "redirect:/";
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return "redirect:/auth/login";
        }
    }

    private boolean verifyTelegramAuth(Map<String, String> params) {
        String hash = params.get("hash");
        Map<String, String> data = new TreeMap<>(params);
        data.remove("hash");

        String dataCheckString = data.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining("\n"));

        try {
            byte[] secretKey = MessageDigest.getInstance("SHA-256").digest(BOT_TOKEN.getBytes());
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secretKey, "HmacSHA256"));
            byte[] hmac = mac.doFinal(dataCheckString.getBytes());

            String calculatedHash = bytesToHex(hmac);
            return calculatedHash.equals(hash);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
