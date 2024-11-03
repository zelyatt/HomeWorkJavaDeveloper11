package org.example;


import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @Override
    public void init() {
        ServletContext servletContext = getServletContext();
        TemplateConfig templateConfig = new TemplateConfig();
        servletContext.setAttribute("templateEngine", templateConfig.getTemplateEngine());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String timezone = req.getParameter("timezone");

        if (timezone == null || timezone.isEmpty()) {
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("lastTimezone")) {
                        timezone = cookie.getValue();
                        break;
                    }
                }
            }
        }

        if (timezone == null || timezone.isEmpty()) {
            timezone = "UTC";
        }

        ZoneId zoneId;
        try {
            zoneId = ZoneId.of(timezone);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
            return;
        }

        LocalDateTime currentTime = LocalDateTime.now(zoneId);
        String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " " + timezone;

        Cookie lastTimezoneCookie = new Cookie("lastTimezone", timezone);
        lastTimezoneCookie.setMaxAge(60 * 60 * 24);
        resp.addCookie(lastTimezoneCookie);

        WebContext context = new WebContext(req, resp, getServletContext());
        context.setVariable("currentTime", formattedTime);
        context.setVariable("timezone", timezone);

        templateEngine.process("time", context, resp.getWriter());
    }
}
