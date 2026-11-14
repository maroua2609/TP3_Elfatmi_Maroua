package ma.app.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.app.model.Produit;
import ma.app.model.ProduitDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/produits")
public class ProduitServlet extends HttpServlet {

    private static final String LIST_VIEW = "/WEB-INF/views/produits/list.jsp";
    private static final String FORM_VIEW = "/WEB-INF/views/produits/form.jsp";

    private ProduitDAO dao;

    @Override
    public void init() {
        dao = (ProduitDAO) getServletContext().getAttribute("produitDAO");
    }

    // ------------------------------------------------------------------ GET

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        if (action == null || action.isEmpty() || "list".equals(action)) {
            lister(req, resp);

        } else if ("new".equals(action)) {
            req.setAttribute("produit", new Produit());
            req.setAttribute("prixSaisi", "");
            req.setAttribute("stockSaisi", "0");
            req.getRequestDispatcher(FORM_VIEW).forward(req, resp);

        } else if ("edit".equals(action)) {
            Long id = parseId(req.getParameter("id"));
            Produit p = dao.findById(id);
            if (p == null) {              // id invalide ou inconnu
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            req.setAttribute("produit", p);
            req.setAttribute("prixSaisi", p.getPrix() == null ? "" : p.getPrix().toPlainString());
            req.setAttribute("stockSaisi", String.valueOf(p.getStock()));
            req.getRequestDispatcher(FORM_VIEW).forward(req, resp);

        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void lister(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Produit> produits = dao.findAll();

        // BONUS : recherche par nom, insensible à la casse
        String q = req.getParameter("q");
        if (q != null && !q.trim().isEmpty()) {
            String needle = q.trim().toLowerCase(Locale.ROOT);
            produits = produits.stream()
                    .filter(p -> p.getNom() != null && p.getNom().toLowerCase(Locale.ROOT).contains(needle))
                    .collect(Collectors.toList());
            req.setAttribute("q", q.trim());
        }

        req.setAttribute("produits", produits);
        req.getRequestDispatcher(LIST_VIEW).forward(req, resp);
    }

    // ----------------------------------------------------------------- POST

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        // ---- Suppression
        if ("delete".equals(action)) {
            Long id = parseId(req.getParameter("id"));
            Produit p = dao.findById(id);
            if (p == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            dao.delete(id);
            req.getSession().setAttribute("flash", "Produit « " + p.getNom() + " » supprimé.");
            resp.sendRedirect(req.getContextPath() + "/produits");
            return;
        }

        // ---- Création / modification : lecture des champs
        String idStr = trim(req.getParameter("id"));
        String nom = trim(req.getParameter("nom"));
        String description = trim(req.getParameter("description"));
        String prixStr = trim(req.getParameter("prix"));
        String stockStr = trim(req.getParameter("stock"));

        Long id = null;
        if (!idStr.isEmpty()) {
            id = parseId(idStr);
            if (dao.findById(id) == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }

        // ---- Validation
        Map<String, String> erreurs = new LinkedHashMap<>();

        if (nom.isEmpty()) {
            erreurs.put("nom", "Le nom est obligatoire.");
        } else if (nom.length() < 2 || nom.length() > 50) {
            erreurs.put("nom", "Le nom doit contenir entre 2 et 50 caractères.");
        }

        BigDecimal prix = null;
        if (prixStr.isEmpty()) {
            erreurs.put("prix", "Le prix est obligatoire.");
        } else {
            try {
                prix = new BigDecimal(prixStr.replace(',', '.'));
                if (prix.signum() <= 0) {
                    erreurs.put("prix", "Le prix doit être strictement positif.");
                }
            } catch (NumberFormatException e) {
                erreurs.put("prix", "Le prix doit être un nombre décimal.");
            }
        }

        int stock = 0;
        if (!stockStr.isEmpty()) {
            try {
                stock = Integer.parseInt(stockStr);
                if (stock < 0) {
                    erreurs.put("stock", "Le stock doit être un entier supérieur ou égal à 0.");
                }
            } catch (NumberFormatException e) {
                erreurs.put("stock", "Le stock doit être un nombre entier.");
            }
        }

        if (description.length() > 255) {
            erreurs.put("description", "La description ne doit pas dépasser 255 caractères.");
        }

        Produit produit = new Produit(id, nom, description, prix, stock);

        // ---- Erreurs : on ré-affiche le formulaire (FORWARD) avec les valeurs saisies
        if (!erreurs.isEmpty()) {
            req.setAttribute("erreurs", erreurs);
            req.setAttribute("produit", produit);
            req.setAttribute("prixSaisi", prixStr);
            req.setAttribute("stockSaisi", stockStr);
            req.getRequestDispatcher(FORM_VIEW).forward(req, resp);
            return;
        }

        // ---- Succès : sauvegarde + message flash + REDIRECT (pattern PRG)
        dao.save(produit);
        req.getSession().setAttribute("flash", "Produit « " + nom + " » enregistré.");
        resp.sendRedirect(req.getContextPath() + "/produits");
    }

    // -------------------------------------------------------------- Utilitaires

    private static String trim(String s) {
        return s == null ? "" : s.trim();
    }

    /** Retourne null si l'id est absent ou invalide. */
    private static Long parseId(String s) {
        try {
            return s == null ? null : Long.valueOf(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
