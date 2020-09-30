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
        <div class="row border-bottom" id="nameSection">
            <div class="col">
                <h4 class="text-center text-cadetblue">${title}</h4>
            </div>
        </div>

        <form action="/officer/drivers/edit" method="post">
            <input type="hidden" name="id" value="${driver.id}">
            <input type="hidden" name="userId" value="${driver.user.id}">
            <div class="form-group row">
                <label for="firstName" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">First name</label>
                <input type="text" name="firstName" id="firstName" value="${driver.user.firstName}" class="form-control col-sm-10">
            </div>
            <div class="form-group row">
                <label for="lastName" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Last name</label>
                <input type="text" name="lastName" id="lastName" value="${driver.user.lastName}" class="form-control col-sm-10">
            </div>
            <div class="form-group row">
                <label for="shiftSize" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Shift size</label>
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
                <label for="capacity" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Capacity</label>
                <input type="text" name="capacity" id="capacity" value="${truck.capacity}" class="form-control col-sm-10">
            </div>
            <div class="form-group row">
                <label for="status" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Status</label>
                <select name="status" id="status" class="form-control col-sm-10">
                    <c:forEach var="status" items="${statusArray}">
                        <option value="${status}"
                                <c:if test="${status == truck.status}"><c:out value="selected"/></c:if>>
                                ${status}
                        </option>
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
                <button type="submit" class="btn btn-success mlr10">${title}</button>
                <button type="button" onclick="history.back();" class="btn btn-danger mlr10">Cancel</button>
            </div>
        </form>
    </section>
</main>

<jsp:include page="../common/footer.jsp"/>