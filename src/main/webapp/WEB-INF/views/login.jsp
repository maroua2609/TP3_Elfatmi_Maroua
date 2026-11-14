<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <title>Connexion</title>
    <jsp:include page="/WEB-INF/views/header.jsp"/>
</head>
<body class="${cookie.theme.value == 'dark' ? 'dark' : ''}">
    <h1>Connexion</h1>

    <c:if test="${not empty erreur}">
        <div class="alerte"><c:out value="${erreur}"/></div>
    </c:if>

    <form method="post" action="${ctx}/login">
        <label for="username">Identifiant</label>
        <input type="text" id="username" name="username" value="<c:out value='${login}'/>" required>

        <label for="password">Mot de passe</label>
        <input type="password" id="password" name="password" required>

        <p><button type="submit">Se connecter</button></p>
    </form>
</body>
</html>
