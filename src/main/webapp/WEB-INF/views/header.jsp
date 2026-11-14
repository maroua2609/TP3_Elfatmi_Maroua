<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- BONUS : fragment commun inclus dans le <head> de chaque page (jsp:include) --%>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
    body { font-family: Arial, sans-serif; margin: 2rem auto; max-width: 800px; padding: 0 1rem;
           background: #fff; color: #222; }
    body.dark { background: #1e1e1e; color: #eee; }
    body.dark a { color: #8ab4f8; }
    table { border-collapse: collapse; width: 100%; margin: 1rem 0; }
    th, td { border: 1px solid #999; padding: .5rem; text-align: left; }
    th { background: #0a4f8f; color: #fff; }
    input, textarea { padding: .4rem; width: 100%; box-sizing: border-box; }
    label { display: block; margin-top: .8rem; font-weight: bold; }
    .erreur { color: #c00; font-size: .9rem; margin: .2rem 0 0; }
    .flash { background: #e6f4ea; color: #1e6b34; padding: .6rem 1rem; border-radius: 4px; margin: 1rem 0; }
    .alerte { background: #fde8e8; color: #a00; padding: .6rem 1rem; border-radius: 4px; margin: 1rem 0; }
    .stock-zero { color: red; font-weight: bold; }
    .inline { display: inline; }
</style>
