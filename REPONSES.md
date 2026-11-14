# TP 3 : Du Servlet au MVC — Réponses

**EL FATMI / maroua :** ........................................  
**Groupe :** GINF2A

---

### Question 1 : Pourquoi un `forward` quand le login échoue, mais un `sendRedirect` quand il réussit ?

Quand le login échoue, on utilise `forward` parce qu'il permet de transférer la requête vers la page `login.jsp` sans créer une nouvelle requête. Ainsi, les informations comme le login saisi et le message d'erreur restent disponibles et peuvent être affichées dans la page.

Quand le login réussit, on utilise `sendRedirect` pour envoyer l'utilisateur vers la page `/dashboard`. Cela permet de changer l'URL dans le navigateur et d'éviter que l'utilisateur renvoie le formulaire de connexion en actualisant la page avec F5.

### Question 2 : Que se passe-t-il avec F5 juste après l'ajout d'un produit ?

**a) Si `doPost` fait un forward vers la liste :**

La dernière requête reste une requête POST. Si l'utilisateur appuie sur F5, le navigateur peut lui demander de renvoyer le formulaire. Si le formulaire est renvoyé, le produit sera ajouté une deuxième fois, ce qui crée un doublon.

**b) Si `doPost` fait un redirect :**

Après l'ajout du produit, le navigateur est redirigé vers une page avec une requête GET. Si l'utilisateur appuie sur F5, la liste est simplement actualisée et le produit ne sera pas ajouté une deuxième fois.

Cette méthode s'appelle le pattern **PRG (Post / Redirect / Get)**.

### Question 3 : Que se passe-t-il si on saisit `<script>alert('XSS')</script>` comme nom de produit ?

Si on utilise `${produit.nom}`, le contenu est affiché directement sans échappement automatique du HTML. Le navigateur peut donc interpréter la balise script et exécuter le code JavaScript. Cela représente une faille de sécurité appelée **XSS (Cross-Site Scripting)**.

Par contre, si on utilise `<c:out value="${produit.nom}"/>`, les caractères spéciaux HTML sont échappés. Le navigateur affiche alors le contenu comme un simple texte au lieu d'exécuter le script.

**Conclusion :** il faut toujours échapper les données saisies par les utilisateurs avant de les afficher dans une page web pour éviter les attaques XSS.

### Question 4 : Quel est l'avantage d'un filtre par rapport à la vérification dans chaque Servlet ? Citer deux autres usages classiques.

Un filtre permet de centraliser la vérification des accès dans une seule classe au lieu de répéter le même code dans chaque Servlet.

Ses principaux avantages sont :
- **Centralisation :** la vérification est faite dans un seul endroit.
- **Sécurité :** on évite d'oublier de protéger une Servlet.
- **Maintenance :** il est plus facile de modifier les règles d'accès.
- **Réutilisation :** plusieurs pages peuvent utiliser le même filtre.

Deux autres utilisations classiques des filtres sont :
1. La journalisation des requêtes (logs), pour enregistrer les accès des utilisateurs.
2. La gestion de l'encodage des caractères, par exemple avec `setCharacterEncoding("UTF-8")`.

