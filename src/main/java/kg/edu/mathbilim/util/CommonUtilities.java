package kg.edu.mathbilim.util;

import jakarta.servlet.http.HttpServletRequest;

public class CommonUtilities {
    private CommonUtilities() {
    }
    public static String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
