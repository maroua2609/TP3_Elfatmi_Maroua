<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Produits</title>
    <jsp:include page="/WEB-INF/views/header.jsp"/>
</head>
<body class="${cookie.theme.value == 'dark' ? 'dark' : ''}">
    <h1>Catalogue de produits</h1>

    <%-- Message flash : affiché une seule fois, puis retiré de la session --%>
    <c:if test="${not empty sessionScope.flash}">
        <div class="flash"><c:out value="${sessionScope.flash}"/></div>
        <c:remove var="flash" scope="session"/>
    </c:if>

    <%-- BONUS : recherche par nom --%>
    <form method="get" action="${ctx}/produits">
        <input type="text" name="q" placeholder="Rechercher par nom..." value="<c:out value='${q}'/>"
               style="width:70%">
        <button type="submit">Rechercher</button>
    </form>

    <p><a href="${ctx}/produits?action=new">Ajouter un produit</a></p>

    <c:if test="${empty produits}">
        <p>Aucun produit.</p>
    </c:if>

    <c:if test="${not empty produits}">
        <table>
            <tr>
                <th>Nom</th><th>Description</th><th>Prix</th><th>Stock</th><th>Actions</th>
            </tr>
            <c:forEach var="p" items="${produits}">
                <tr>
                    <td><c:out value="${p.nom}"/></td>
                    <td><c:out value="${p.description}"/></td>
                    <td><fmt:formatNumber value="${p.prix}" minFractionDigits="2" maxFractionDigits="2"/> MAD</td>
                    <td>
                        <c:choose>
                            <c:when test="${p.stock == 0}">
                                <span class="stock-zero">0</span>
                            </c:when>
                            <c:otherwise>${p.stock}</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="${ctx}/produits?action=edit&amp;id=${p.id}">Éditer</a>
                        <form class="inline" method="post" action="${ctx}/produits?action=delete"
                              onsubmit="return confirm('Supprimer ce produit ?');">
                            <input type="hidden" name="id" value="${p.id}">
                            <button type="submit">Supprimer</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <a href="${ctx}/dashboard">Retour au tableau de bord</a>
</body>
</html>
