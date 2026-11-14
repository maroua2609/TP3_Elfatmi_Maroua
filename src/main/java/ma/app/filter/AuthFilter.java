package ma.app.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/dashboard", "/produits", "/produits/*"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        boolean connecte = session != null && session.getAttribute("user") != null;

        if (connecte) {
            chain.doFilter(request, response);
            return;
        }

        // BONUS : mémoriser l'URL demandée (GET uniquement) pour y revenir après connexion
        if ("GET".equals(req.getMethod())) {
            String url = req.getServletPath();
            if (req.getPathInfo() != null) {
                url += req.getPathInfo();
            }
            if (req.getQueryString() != null) {
                url += "?" + req.getQueryString();
            }
            req.getSession(true).setAttribute("redirectAfterLogin", url);
        }

        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
