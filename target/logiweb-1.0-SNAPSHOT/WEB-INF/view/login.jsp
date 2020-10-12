<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Login"/>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<div class="container">

    <form method="POST" action="${contextPath}/login" class="form-signin">
        <h2 class="text-center text-cadetblue">Enter email and password</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <input name="email" type="email" class="form-control" placeholder="Email"
                   autofocus="true"/>
            <input name="password" type="password" class="form-control" placeholder="Password"/>
            <span>${error}</span>

            <button class="btn btn-lg btn-info btn-block btn-signin" type="submit">Sign In</button>
            <h4 class="text-center lnk-color"><a href="${contextPath}/registration">Registration</a></h4>
        </div>

    </form>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js" type="text/javascript"></script>

<jsp:include page="common/footer.jsp"/>