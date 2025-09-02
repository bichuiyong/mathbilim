package kg.edu.mathbilim.components;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.ZoneId;

@Component
public class TimeZoneFilter implements Filter {

    private static final ThreadLocal<ZoneId> userZone = new ThreadLocal<>();
    private static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Bishkek");

    public static ZoneId getUserZone() {
        return userZone.get() != null ? userZone.get() : DEFAULT_ZONE;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        ZoneId zone = DEFAULT_ZONE;

        if (httpRequest.getCookies() != null) {
            for (Cookie cookie : httpRequest.getCookies()) {
                if ("timezone".equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isBlank()) {
                    try {
                        zone = ZoneId.of(cookie.getValue());
                    } catch (Exception ignored) {
                        zone = DEFAULT_ZONE;
                    }
                    break;
                }
            }
        }

        userZone.set(zone);

        try {
            chain.doFilter(request, response);
        } finally {
            userZone.remove();
        }
    }
}
