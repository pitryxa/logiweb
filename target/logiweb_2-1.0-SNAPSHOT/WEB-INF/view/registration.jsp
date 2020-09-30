<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Registration"/>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<div class="container">

<%--    <form:--%>

<%--    <form method="POST" action="${contextPath}/login" class="form-signin">--%>
<%--        <h2 class="text-center text-cadetblue">Sign In</h2>--%>

<%--        <div class="form-group ${error != null ? 'has-error' : ''}">--%>
<%--            <span>${message}</span>--%>
<%--            <input name="email" type="email" class="form-control" placeholder="Email"--%>
<%--                   autofocus="true"/>--%>
<%--            <input name="password" type="password" class="form-control" placeholder="Password"/>--%>
<%--            <span>${error}</span>--%>

<%--            <button class="btn btn-lg btn-info btn-block btn-signin" type="submit">Sign In</button>--%>
<%--            <h3 class="text-center"><a href="${contextPath}/registration" class="lnk-color">Sign Up</a></h3>--%>
<%--        </div>--%>

<%--    </form>--%>

</div>

<jsp:include page="common/footer.jsp"/>