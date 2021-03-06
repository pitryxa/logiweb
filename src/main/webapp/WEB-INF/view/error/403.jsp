<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="403"/>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<section id="container">
    <div class="row mt-3">
        <div class="col-lg-6 col-lg-offset-3">
            <div>
                <h1><span class="">Access denied</span><br><span class="color">You don't have enough rights </span><br/>
                </h1>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <h3><a href="<c:url value="/admin/users"/>">List of users</a></h3>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_MANAGER')">
                    <h3><a href="<c:url value="/officer/orders"/>">List of orders</a></h3>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_DRIVER')">
                    <h3><a href="<c:url value="/driver"/>">To the your account page</a></h3>
                </sec:authorize>
            </div>
        </div>
    </div>
</section>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>