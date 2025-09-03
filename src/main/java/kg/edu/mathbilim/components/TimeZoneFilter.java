package kg.edu.mathbilim.components;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.ZoneId;

@Component
public class TimeZoneFilter extends OncePerRequestFilter {
    private static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Bishkek");

    public static ZoneId getUserZone(HttpServletRequest request) {
        ZoneId zone = (ZoneId) request.getAttribute("userZone");
        return zone != null ? zone : DEFAULT_ZONE;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        ZoneId zone = DEFAULT_ZONE;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("timezone".equals(cookie.getName()) && cookie.getValue() != null && !cookie.getValue().isBlank()) {
                    try {
                        zone = ZoneId.of(cookie.getValue());
                    } catch (Exception e) {
                        zone = DEFAULT_ZONE;
                    }
                    break;
                }
            }
        } else {
            logger.warn("No cookies found, using default timezone: {}");
        }

        request.setAttribute("userZone", zone);
        chain.doFilter(request, response);
    }
}