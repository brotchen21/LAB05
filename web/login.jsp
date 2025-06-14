<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="text"], input[type="password"] {
            width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box;
        }
        .btn { padding: 6px 12px; font-size: 14px; cursor: pointer; border-radius: 4px; }
        .btn-primary { color: #fff; background-color: #337ab7; border-color: #2e6da4; }
        .error { color: red; }
    </style>
</head>
<body>
    <h1>Login</h1>
    <% if (request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <form action="login" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" value="${rememberedUser}"<% 
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("username".equals(cookie.getName())) {
                            out.print(cookie.getValue());
                            break;
                        }
                    }
                }
            %>" required>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" value="${rememberedPass}"<% 
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("password".equals(cookie.getName())) {
                            out.print(cookie.getValue());
                            break;
                        }
                    }
                }
            %>" required>
        </div>
        <div class="form-group">
            <label>
                <input type="checkbox" name="rememberMe"
                       <c:if test="${not empty rememberedUser and not empty rememberedPass}">checked</c:if>
                       > Remember Me
            </label>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary">Login</button>
        </div>
    </form>
</body>
</html>