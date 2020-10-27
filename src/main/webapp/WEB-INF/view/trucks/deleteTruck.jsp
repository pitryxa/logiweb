<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Delete truck"/>

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

        <c:if test="${truck.workStatus != 'FREE'}">
            <div class="row">
                <div class="col">
                    <h5 class="text-center text-danger">Truck cannot be deleted while it is executing an order!</h5>
                </div>
            </div>
        </c:if>

        <c:if test="${truck.workStatus == 'FREE'}">
            <form action="/officer/trucks/delete" method="post">
                <input type="hidden" name="id" value="${truck.id}">
                <input type="hidden" name="workStatus" value="${truck.workStatus}">
                <div class="form-group row">
                    <label for="regNumber" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Reg.
                        number</label>
                    <input type="text" name="regNumber" id="regNumber" value="${truck.regNumber}"
                           class="form-control col-sm-10" readonly>
                </div>
                <div class="form-group row">
                    <label for="shiftSize" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Shift
                        size</label>
                    <input type="text" name="shiftSize" id="shiftSize" value="${truck.shiftSize}"
                           class="form-control col-sm-10" readonly>
                </div>
                <div class="form-group row">
                    <label for="capacity"
                           class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Capacity</label>
                    <input type="text" name="capacity" id="capacity" value="${truck.capacity}"
                           class="form-control col-sm-10" readonly>
                </div>
                <div class="form-group row">
                    <label for="conditionStatus"
                           class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Status</label>
                    <input type="text" name="conditionStatus" id="conditionStatus" value="${truck.conditionStatus}"
                           class="form-control col-sm-10" readonly>
                </div>
                <div class="form-group row">
                    <label for="drivers" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Drivers</label>
                    <c:if test="${truck.drivers == null}">
                        <input type="text" id="drivers" value="NONE" class="form-control col-sm-10" readonly>
                    </c:if>
                    <c:if test="${truck.drivers != null}">
                        <input type="text" id="drivers"
                               value="<c:forEach var="driver" items="${truck.drivers}">${driver.user.firstName} ${driver.user.lastName} (${driver.id}); </c:forEach>"
                               class="form-control col-sm-10" readonly>
                    </c:if>
                </div>
                <div class="form-group row">
                    <label for="city" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">City</label>
                    <input type="text" name="city" id="city" value="${truck.city}" class="form-control col-sm-10"
                           readonly>
                </div>
                <div class="d-flex justify-content-center">
                    <button type="submit" class="btn btn-success mx-2">${title}</button>
                    <button type="button" onclick="history.back();" class="btn btn-danger mlr10">Back</button>
                </div>
            </form>
        </c:if>
    </section>
</main>

<jsp:include page="../common/footer.jsp"/>