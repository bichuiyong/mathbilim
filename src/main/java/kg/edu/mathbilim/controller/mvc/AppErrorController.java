package kg.edu.mathbilim.controller.mvc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object sc = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Integer status = (sc instanceof Integer) ? (Integer) sc : null;

        model.addAttribute("status", status);
        model.addAttribute("reason", request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
        model.addAttribute("details", new Object() {
            public final String serverName = request.getServerName();
            public final String requestURL = String.valueOf(request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI));
            public final int serverPort = request.getServerPort();
            public final String requestId = request.getHeader("X-Request-Id");
        });

        if (status != null) {
            if (status == 403) return "error/403";
            if (status == 404) return "error/404";
            if (status >= 500) return "error/5xx";
        }
        return "error/error";
    }
}
