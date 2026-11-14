<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>404 - Introuvable</title>
    <jsp:include page="/WEB-INF/views/header.jsp"/>
</head>
<body class="${cookie.theme.value == 'dark' ? 'dark' : ''}">
    <h1>Erreur 404</h1>
    <p>La ressource demandée est introuvable.</p>
    <p><a href="${ctx}/dashboard">Retour au tableau de bord</a></p>
</body>
</html>
