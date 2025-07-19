package kg.edu.mathbilim.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;
@Component
@RequiredArgsConstructor
public class CustomLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String langParam = request.getParameter("lang");
        if (langParam != null) {
            return Locale.forLanguageTag(langParam);
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("lang")) {
                    return Locale.forLanguageTag(cookie.getValue());
                }
            }
        }
        return Locale.forLanguageTag("ru");
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        Cookie cookie = new Cookie("lang", locale.getLanguage());
        cookie.setMaxAge(365 * 24 * 60 * 60);
        cookie.setPath("/");
//        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

}

