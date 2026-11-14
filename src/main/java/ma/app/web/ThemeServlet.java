package ma.app.web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * BONUS : reprend le cookie "theme" du TP 2, sans produire de HTML.
 * /theme?value=dark  ou  /theme?value=light
 */
@WebServlet("/theme")
public class ThemeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String value = "dark".equals(req.getParameter("value")) ? "dark" : "light";

        Cookie cookie = new Cookie("theme", value);
        cookie.setMaxAge(30 * 24 * 60 * 60); // 30 jours
        cookie.setPath("/");
        resp.addCookie(cookie);

        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }
}
