<%-- 
    Document   : createProduct
    Created on : 1 Jun 2025, 18:43:50
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Add a new product</h2>
        <form action="createProduct" method="post">
            
            <input type="number" name="id" id="id" value="${product.id}" />
            
            <label for="name">Product name</label>
            <input type="text" name="name" id="name" required/>
            
            <label for="price">Price:</label>
            <input type="number" step="0.01" name="price" id="price" required/>
            
            <label>Description:</label>
            <textarea name="description" rows="4"></textarea>
            
            <label for="stock">Stock:</label>
            <input type="number" name="stock" id="stock" required />

            <label for="importDate">Import Date(yyyy-MM-dd):</label>
            <input type="date" name="importDate" id="importDate" />

            <input type="submit" value="Add product" />

        </form>
    </body>
</html>
