<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Produit</title>
    <jsp:include page="/WEB-INF/views/header.jsp"/>
</head>
<body class="${cookie.theme.value == 'dark' ? 'dark' : ''}">
    <h1>${empty produit.id ? 'Nouveau produit' : 'Modifier le produit'}</h1>

    <form method="post" action="${ctx}/produits">
        <%-- Champ caché id : uniquement en modification --%>
        <c:if test="${not empty produit.id}">
            <input type="hidden" name="id" value="${produit.id}">
        </c:if>

        <label for="nom">Nom</label>
        <input type="text" id="nom" name="nom" value="<c:out value='${produit.nom}'/>">
        <c:if test="${not empty erreurs.nom}">
            <p class="erreur"><c:out value="${erreurs.nom}"/></p>
        </c:if>

        <label for="description">Description</label>
        <textarea id="description" name="description" rows="3"><c:out value="${produit.description}"/></textarea>
        <c:if test="${not empty erreurs.description}">
            <p class="erreur"><c:out value="${erreurs.description}"/></p>
        </c:if>

        <label for="prix">Prix</label>
        <input type="text" id="prix" name="prix" value="<c:out value='${prixSaisi}'/>">
        <c:if test="${not empty erreurs.prix}">
            <p class="erreur"><c:out value="${erreurs.prix}"/></p>
        </c:if>

        <label for="stock">Stock</label>
        <input type="text" id="stock" name="stock" value="<c:out value='${stockSaisi}'/>">
        <c:if test="${not empty erreurs.stock}">
            <p class="erreur"><c:out value="${erreurs.stock}"/></p>
        </c:if>

        <p>
            <button type="submit">Enregistrer</button>
            <a href="${ctx}/produits">Annuler</a>
        </p>
    </form>
</body>
</html>
