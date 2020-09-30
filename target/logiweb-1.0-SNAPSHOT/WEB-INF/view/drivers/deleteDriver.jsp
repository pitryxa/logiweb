<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Delete driver"/>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<main class="flex-shrink-0">
    <section class="container form-content" id="content">
        <div class="row" id="nameSection">
            <div class="col">
                <h4 class="text-center text-cadetblue">${title} #${driver.id}</h4>
            </div>
        </div>

        <form action="/officer/drivers/delete" method="post">
            <input type="hidden" name="id" value="${driver.id}">
            <input type="hidden" name="user-id" value="${driver.user.id}">
            <div class="form-group row">
                <label for="firstname" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">First name</label>
                <input type="text" name="firstName" id="firstname" value="${driver.user.firstName}" class="form-control-plaintext col-sm-10" readonly>
            </div>
            <div class="form-group row">
                <label for="lastname" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Last name</label>
                <input type="text" name="lastName" id="lastname" value="${driver.user.lastName}" class="form-control-plaintext col-sm-10" readonly>
            </div>
            <div class="form-group row">
                <label for="workHours" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Hours worked</label>
                <input type="text" name="workHours" id="workHours" value="${driver.workHours}" class="form-control-plaintext col-sm-10" readonly>
            </div>
            <div class="form-group row">
                <label for="status" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Status</label>
                <input type="text" name="status" id="status" value="${driver.status}" class="form-control-plaintext col-sm-10" readonly>
            </div>
            <div class="form-group row">
                <label for="truck" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Truck</label>
                <c:if test="${driver.truck == null}">
                    <input type="text" id="truck" value="NONE" class="form-control-plaintext col-sm-10" readonly>
                </c:if>
                <c:if test="${driver.truck != null}">
                    <input type="text" id="truck" value="${driver.truck.regNumber} (${driver.truck.id})" class="form-control-plaintext col-sm-10" readonly>
                </c:if>
            </div>
            <div class="form-group row">
                <label for="city" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">City</label>
                <input type="text" name="city" id="city" value="${driver.city}" class="form-control-plaintext col-sm-10" readonly>
            </div>
            <div class="d-flex justify-content-center">
                <button type="submit" class="btn btn-success mx-2">${title}</button>
                <button type="button" onclick="history.back();" class="btn btn-danger mlr10">Back</button>
            </div>
        </form>
    </section>
</main>

<jsp:include page="../common/footer.jsp"/>