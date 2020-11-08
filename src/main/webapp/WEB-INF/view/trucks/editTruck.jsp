<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Edit truck"/>

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
                    <h5 class="text-center text-danger">Truck cannot be edited while it is executing an order!</h5>
                </div>
            </div>
        </c:if>

        <c:if test="${truck.workStatus == 'FREE'}">
            <form action="${contextPath}/officer/trucks/edit" method="post">
                <input type="hidden" name="id" value="${truck.id}">
                <input type="hidden" name="workStatus" value="${truck.workStatus}">

                <div class="form-group row">
                    <label for="regNumber" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Reg.
                        number</label>
                    <input type="text" name="regNumber" id="regNumber" value="${truck.regNumber}"
                           class="form-control col-sm-10">
                </div>
                <div class="form-group row">
                    <label for="shiftSize" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Shift
                        size</label>
                    <select name="shiftSize" id="shiftSize" class="form-control col-sm-10">
                        <option value="2"
                                <c:if test="${2 == truck.shiftSize}"><c:out value="selected"/></c:if>>
                            2
                        </option>
                        <option value="3"
                                <c:if test="${3 == truck.shiftSize}"><c:out value="selected"/></c:if>>
                            3
                        </option>
                    </select>
                </div>
                <div class="form-group row">
                    <label for="capacity"
                           class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Capacity</label>
                    <input type="number" step="1" min="1" name="capacity" id="capacity" value="${truck.capacity}"
                           class="form-control col-sm-10" required>
                </div>
                <div class="form-group row">
                    <label for="conditionStatus"
                           class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Status</label>
                    <select name="conditionStatus" id="conditionStatus" class="form-control col-sm-10">
                        <c:forEach var="status" items="${statusArray}">
                            <c:if test="${status != 'DISABLED'}">
                                <option value="${status}"
                                        <c:if test="${status == truck.conditionStatus}"><c:out
                                                value="selected"/></c:if>>
                                        ${status}
                                </option>
                            </c:if>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group row">
                    <label for="city" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">City</label>
                    <select name="city" id="city" class="form-control col-sm-10">
                        <c:forEach var="city" items="${cityList}">
                            <option value="${city.name}"
                                    <c:if test="${city.name == truck.city}"><c:out value="selected"/></c:if>>
                                    ${city.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="d-flex justify-content-center">
                    <button type="submit" class="btn btn-success mlr10" onclick="return validateTruck()">${title}</button>
                    <button type="button" onclick="history.back();" class="btn btn-danger mlr10">Cancel</button>
                </div>
            </form>
        </c:if>

    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>