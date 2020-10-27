<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Edit driver"/>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<main class="flex-shrink-0">
    <section class="container form-content" id="content">
        <div class="row" id="nameSection">
            <div class="col">
                <h4 class="text-center text-cadetblue">${title}</h4>
            </div>
        </div>

        <c:if test="${driver.status != 'RECREATION'}">
            <div class="row">
                <div class="col">
                    <h5 class="text-center text-danger">Driver cannot be edited while he is executing an order!</h5>
                </div>
            </div>
        </c:if>

        <c:if test="${driver.status == 'RECREATION'}">
            <form action="${contextPath}/officer/drivers/edit" method="post">
                <input type="hidden" name="id" value="${driver.id}">
                <input type="hidden" name="userId" value="${driver.user.id}">
                <input type="hidden" name="truckId" value="${driver.truck.id}">
                <div class="form-group row">
                    <label for="personalNumber" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Personal
                        number</label>
                    <input type="text" name="personalNumber" id="personalNumber" value="${driver.personalNumber}"
                           class="form-control col-sm-10">
                </div>
                <div class="form-group row">
                    <label for="firstName" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">First
                        name</label>
                    <input type="text" name="firstName" id="firstName" value="${driver.user.firstName}"
                           class="form-control col-sm-10" readonly>
                </div>
                <div class="form-group row">
                    <label for="lastName" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Last
                        name</label>
                    <input type="text" name="lastName" id="lastName" value="${driver.user.lastName}"
                           class="form-control col-sm-10" readonly>
                </div>
                <div class="form-group row">
                    <label for="workHours" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Work
                        hours</label>
                    <input type="text" name="workHours" id="workHours" value="${driver.workHours}"
                           class="form-control col-sm-10">
                </div>
                <div class="form-group row">
                    <label for="truck" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Truck</label>
                    <c:if test="${driver.truck == null}">
                        <input type="text" name="truck" id="truck" value="NONE" class="form-control col-sm-10" readonly>
                    </c:if>
                    <c:if test="${driver.truck != null}">
                        <input type="text" id="truck" value="${driver.truck.regNumber}"
                               class="form-control col-sm-10" readonly>
                    </c:if>
                </div>
                <div class="form-group row">
                    <label for="status" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Status</label>
                    <input type="text" name="status" id="status" value="${driver.status}" class="form-control col-sm-10"
                           readonly>
                </div>
                <div class="form-group row">
                    <label for="city" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">City</label>
                    <input type="text" name="city" id="city" value="${driver.city}" class="form-control col-sm-10"
                           readonly>
                </div>

                <div class="d-flex justify-content-center">
                    <button type="submit" class="btn btn-success mlr10">${title}</button>
                    <button type="button" onclick="history.back();" class="btn btn-danger mlr10">Cancel</button>
                </div>
            </form>
        </c:if>

    </section>
</main>

<jsp:include page="../common/footer.jsp"/>