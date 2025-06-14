<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <html>
<head>
    <title>Product List</title>
    <style>
        /* Basic styling for readability */
        body {
            font-family: sans-serif;
        }
        table {
            border-collapse: collapse;
            width: 80%;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
        }
        .pagination {
            margin-top: 20px;
        }
        .pagination a {
            margin: 0 5px;
            text-decoration: none;
            padding: 5px 10px;
            border: 1px solid #ddd;
            color: #337ab7;
        }
        .pagination a.active {
            background-color: #337ab7;
            color: white;
            border: 1px solid #337ab7;
        }
        .pagination a:hover:not(.active) {
            background-color: #ddd;
        }
    </style>
</head>

<body>

    <h2>Product List</h2>
    <a href="${pageContext.request.contextPath}/product/createProduct.jsp">Add new product</a>
    <a href="${pageContext.request.contextPath}/product/editProduct.jsp">Edit product</a>
        
    <%-- Configuration for Pagination --%>
    <c:set var="pageSize" value="10" />
    <c:set var="currentPage" value="${empty param.page ? 1 : param.page}" /> <%-- More robust check for page parameter --%>
    <c:set var="totalProducts" value="${fn:length(products)}" />

    <%-- Calculate total pages --%>
    <c:choose>
        <c:when test="${totalProducts == 0}">
            <c:set var="totalPages" value="0" />
        </c:when>
        <c:otherwise>
            <c:set var="totalPages" value="${(totalProducts + pageSize - 1) / pageSize}" /> <%-- More concise way to calculate total pages --%>
        </c:otherwise>
    </c:choose>


    <%-- Calculate start and end indices for the current page --%>
    <c:set var="start" value="${(currentPage - 1) * pageSize}" />
    <c:set var="end" value="${start + pageSize -1}" /> <%-- Adjusted end for inclusive check with status.index --%>


    <c:if test="${totalProducts > 0}">
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Description</th>
                    <th>Stock</th>
                </tr>
            </thead>
            <tbody>
                <%-- Iterate over the products for the current page --%>
                <c:forEach var="product" items="${products}" varStatus="status">
                    <c:if test="${status.index >= start && status.index <= end}">
                        <tr>
                            <td>${product.id}</td>
                            <td>${product.name}</td>
                            <td>${product.price}</td>
                            <td>${product.description}</td>
                            <td>${product.stock}</td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>

        <div class="pagination">
            <%-- Previous Page Link --%>
            <c:if test="${currentPage > 1}">
                <a href="products?page=${currentPage - 1}">Previous</a>
            </c:if>

            <%-- Page Number Links --%>
            <c:forEach var="i" begin="1" end="${totalPages}">
                <a href="products?page=${i}" class="${i == currentPage ? 'active' : ''}">${i}</a>
            </c:forEach>

            <%-- Next Page Link --%>
            <c:if test="${currentPage < totalPages}">
                <a href="products?page=${currentPage + 1}">Next</a>
            </c:if>
        </div>

    </c:if>

    <c:if test="${totalProducts == 0}">
        <p>No products found.</p>
    </c:if>

</body>
</html>