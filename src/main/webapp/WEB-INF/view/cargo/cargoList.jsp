<%--
  Created by IntelliJ IDEA.
  User: boldarev
  Date: 04.09.2020
  Time: 16:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<style>--%>
<%--    table, th, td {--%>
<%--        border: black;--%>
<%--    }--%>
<%--    th,td {--%>
<%--        padding-left: 100px;--%>
<%--    }--%>
<%--</style>--%>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"></script>
    <style>
        section{
            margin: 20px;
        }
    </style>
    <title>Cargo List</title>
</head>
<body>
<section>
    <h2>Cargo List</h2>
    <a href="/add-cargo">Add cargo</a>
    <br>
    <br>
    <table class="table">
        <%--    <caption>Cargo List</caption>--%>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Weight</th>
            <th>Status</th>
            <th>Action</th>
        </tr>

        <c:forEach var="cargo" items="${cargoList}">
            <tr>
                <td>${cargo.id}</td>
                <td>${cargo.name}</td>
                <td>${cargo.weight}</td>
                <td>${cargo.status}</td>
                <td>
                    <a href="/edit-cargo/${cargo.id}">Edit</a>
                    <a href="/delete-cargo/${cargo.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>

    <a href="/add-cargo">Add cargo</a>
</section>
</body>
</html>
