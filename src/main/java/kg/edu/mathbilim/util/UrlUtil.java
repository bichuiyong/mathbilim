package kg.edu.mathbilim.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlUtil {

    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    public static String getBaseURL(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() +
                (request.getServerPort() != 80 && request.getServerPort() != 443 ?
                        ":" + request.getServerPort() : "");
    }
}
