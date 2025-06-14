<%-- 
    Document   : productList
    Created on : 1 Jun 2025, 18:43:39
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Edit Product</title>
    </head>
    <body>
        <h2>Edit Product</h2>
        <form action="${pageContext.request.contextPath}/product/editProduct" method="post">
            <input type="hidden" name="id" value="${product.id}" />

            <label for="name">Name:</label>
            <input type="text" name="name" id="name" value="${product.name}" required />

            <label for="price">Price:</label>
            <input type="number" step="0.01" name="price" id="price" value="${product.price}" required />

            <label>Description:</label>
            <textarea name="description" rows="4">${product.description}</textarea>

            <label for="stock">Stock:</label>
            <input type="number" name="stock" id="stock" value="${product.stock}" required />

            <label for="importDate">Import Date:</label>
            <input type="date" name="importDate" id="importDate" value="${product.importDate}" />

            <input type="submit" value="Update Product" />
        </form>
    </body>
</html>
