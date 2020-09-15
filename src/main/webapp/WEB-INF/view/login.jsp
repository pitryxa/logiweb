<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"></script>
<%--    <style>--%>
<%--        section {--%>
<%--            margin: 20px;--%>
<%--        }--%>

<%--        h2 {--%>
<%--            margin-bottom: 20px;--%>
<%--        }--%>
<%--    </style>--%>
    <link rel="stylesheet" href="${contextPath}/resources/css/common.css" type="text/css">

    <title>Logistic company</title>
</head>
<body>

<div class="container">

    <form method="POST" action="${contextPath}/login" class="form-signin">
        <h2 class="text-center">Sign In</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <input name="email" type="email" class="form-control" placeholder="Email"
                   autofocus="true"/>
            <input name="password" type="password" class="form-control" placeholder="Password"/>
            <span>${error}</span>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign In</button>
            <h3 class="text-center"><a href="${contextPath}/registration">Sign Up</a></h3>
        </div>

    </form>

</div>


<section>
    <h2>Logistic company</h2>
    <p><a href="/cargo">Cargo</a></p>
    <p><a href="/trucks">Trucks</a></p>
    <p><a href="/drivers">Drivers</a></p>
    <p><a href="/orders">Orders</a></p>
</section>
</body>
</html>
