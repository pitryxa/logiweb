<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Driver personal card"/>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<main class="flex-shrink-0">
<%--    <h4 class="text-center text-cadetblue mt-3">${title}</h4>--%>
<%--    <h5 class="text-center text-success mt-3">No assigned orders now</h5>--%>

    <section class="container form-content" id="content">
        <div class="row" id="nameSection">
            <div class="col">
                <h4 class="text-center text-cadetblue">${title}</h4>
            </div>
        </div>
<%--        <div class="row border-bottom">--%>
<%--            <div class="col">--%>
<%--                <h5 class="text-center text-success pb-3">No assigned orders now</h5>--%>
<%--            </div>--%>
<%--        </div>--%>

        <div class="row border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="py-2 pr-2 text-cadetblue my-2">Name</p>
            </div>
            <div class="col-6">
                <p class="py-2 my-2"><c:out value="${currentDriver.user.firstName} ${currentDriver.user.lastName}"/></p>
            </div>
        </div>
        <div class="row border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="py-2 pr-2 text-cadetblue my-2">Personal number</p>
            </div>
            <div class="col-6">
                <p class="py-2 my-2"><c:out value="${currentDriver.personalNumber}"/></p>
            </div>
        </div>
        <div class="row border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="py-2 pr-2 text-cadetblue my-2">Status</p>
            </div>
            <div class="col-6">
                <p class="py-2 my-2"><c:out value="${currentDriver.status}"/></p>
            </div>
        </div>
        <div class="row border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="py-2 pr-2 text-cadetblue my-2">Worked hours in current month</p>
            </div>
            <div class="col-6">
                <p class="py-2 my-2"><c:out value="${currentDriver.workHours}"/></p>
            </div>
        </div>





    </section>
</main>

<jsp:include page="../common/footer.jsp"/>