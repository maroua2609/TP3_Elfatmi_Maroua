package ma.app.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String VIEW = "/WEB-INF/views/login.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Déjà connecté ? -> directement au tableau de bord
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Vérification simplifiée (comme au TP 2)
        if ("admin".equals(username) && "password".equals(password)) {
            HttpSession session = req.getSession();
            req.changeSessionId(); // protection contre la fixation de session
            session.setAttribute("user", username);
            session.setMaxInactiveInterval(30 * 60); // 30 min

            // BONUS : retour à l'URL initialement demandée
            String next = (String) session.getAttribute("redirectAfterLogin");
            session.removeAttribute("redirectAfterLogin");
            if (next != null && next.startsWith("/") && !next.startsWith("//")) {
                resp.sendRedirect(req.getContextPath() + next);
            } else {
                resp.sendRedirect(req.getContextPath() + "/dashboard");
            }
        } else {
            // Échec : FORWARD (même requête) pour conserver l'erreur et le login saisi
            req.setAttribute("erreur", "Identifiant ou mot de passe incorrect.");
            req.setAttribute("login", username);
            req.getRequestDispatcher(VIEW).forward(req, resp);
        }
    }
}
