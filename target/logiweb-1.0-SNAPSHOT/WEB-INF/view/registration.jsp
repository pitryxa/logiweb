<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Registration"/>

<jsp:include page="common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<div class="container">

    <form:form method="POST" modelAttribute="userForm" class="form-signin">
        <h2 class="text-center text-cadetblue">Registration</h2>

        <spring:bind path="firstName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input id="FirstName" type="text" path="firstName" class="form-control" placeholder="First Name"
                            autofocus="true"></form:input>
                <form:errors id="FirstNameVal" path="firstName"></form:errors>
            </div>
        </spring:bind>
        <spring:bind path="lastName">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input id="LastName" type="text" path="lastName" class="form-control"
                            placeholder="Last Name"></form:input>
                <form:errors path="lastName"></form:errors>
            </div>
        </spring:bind>
        <spring:bind path="email">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input id="Email" type="text" path="email" class="form-control" placeholder="Email"></form:input>
                <form:errors path="email"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" id="Password" path="password" class="form-control"
                            placeholder="Password"></form:input>
                <form:errors path="password"></form:errors>
            </div>
        </spring:bind>

        <spring:bind path="confirmPassword">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" id="ConfirmPassword" path="confirmPassword" class="form-control"
                            placeholder="Confirm your password"></form:input>
                <form:errors path="confirmPassword"></form:errors>
            </div>
        </spring:bind>

        <%--<spring:bind path="identKey">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" id="IdentKey" path="identKey" class="form-control"
                            placeholder="Identification key"></form:input>
                <form:errors path="identKey"></form:errors>
            </div>
        </spring:bind>--%>
        <button class="btn btn-lg btn-info btn-block btn-signin" type="submit">Registration</button>
        <h4 class="text-center lnk-color"><a href="${contextPath}/login">Sign In</a></h4>
    </form:form>

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