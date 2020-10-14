<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Wrong amount drivers"/>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<main class="flex-shrink-0">
    <section class="container form-content" id="content">
        <div class="row" id="nameSection">
            <div class="col">
                <h4 class="text-center text-danger pb-3">${title} for order. You must
                    choose ${shiftSize} drivers!</h4>

                <p class="text-center text-cadetblue"><a href="${contextPath}/officer/orders/add-drivers"
                                                         class="btn btn-info">Back to DRIVERS choice</a></p>
                <p class="text-center text-cadetblue"><a href="${contextPath}/officer/orders/add-truck"
                                                         class="btn btn-info">Back to TRUCK choice</a></p>
                <p class="text-center text-cadetblue"><a href="${contextPath}/officer/orders/add-cargo"
                                                         class="btn btn-info">Back to CARGO choice</a></p>
            </div>
        </div>
    </section>
</main>

<jsp:include page="../common/footer.jsp"/>
