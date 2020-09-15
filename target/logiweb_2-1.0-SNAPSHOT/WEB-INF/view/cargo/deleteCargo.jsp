<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: boldarev
  Date: 07.09.2020
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"></script>
    <style>
        section {
            margin: 20px;
        }

        h2 {
            margin-bottom: 20px;
        }
    </style>
    <title>Add Cargo</title>
</head>
<body>
<section>
    <h2>Delete cargo #${cargo.id}</h2>
    <p>Confirm deleting cargo</p>

    <form action="/cargo/delete" method="post">
        <input type="hidden" name="id" value="${cargo.id}">
        <input type="hidden" name="name" value="${cargo.name}">
        <input type="hidden" name="weight" value="${cargo.weight}">
        <input type="hidden" name="status" value="${cargo.status}">

        <button type="submit" class="btn btn-success">Delete cargo</button>
        <button type="button" onclick="history.back();" class="btn btn-light">Cancel</button>
    </form>
</section>


</body>
</html>
