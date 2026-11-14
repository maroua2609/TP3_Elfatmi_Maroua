<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Tableau de bord</title>
    <jsp:include page="/WEB-INF/views/header.jsp"/>
</head>
<body class="${cookie.theme.value == 'dark' ? 'dark' : ''}">
    <h1>Bienvenue <c:out value="${user}"/></h1>

    <ul>
        <li><a href="${ctx}/produits">Gestion des produits</a></li>
        <li><a href="${ctx}/logout">Déconnexion</a></li>
    </ul>

    <p>
        Thème :
        <a href="${ctx}/theme?value=light">clair</a> |
        <a href="${ctx}/theme?value=dark">sombre</a>
    </p>
</body>
</html>
