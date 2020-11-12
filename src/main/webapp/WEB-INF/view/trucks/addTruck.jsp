<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Add truck"/>

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

        <form action="${contextPath}/officer/trucks/add" method="post">
            <div class="form-group row">
                <label for="regNumber" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Reg. number</label>
                <input type="text" name="regNumber" id="regNumber" class="form-control col-sm-10">
            </div>
            <div class="form-group row">
                <label for="shiftSize" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Shift size</label>
                <select name="shiftSize" id="shiftSize" class="form-control col-sm-10">
                    <option value="2" selected>2</option>
                    <option value="3">3</option>
                </select>
            </div>
            <div class="form-group row">
                <label for="capacity" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Capacity (t)</label>
                <input type="number" step="1" min="1" name="capacity" id="capacity" class="form-control col-sm-10" required>
            </div>
            <div class="form-group row">
                <label for="city" class=" text-cadetblue col-form-label col-sm-2 font-weight-bold">City</label>
                <select name="city" id="city" class="form-control col-sm-10">
                    <c:forEach var="city" items="${cityList}">
                        <option value="${city.name}">${city.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="d-flex justify-content-center">
                <button type="submit" class="btn btn-success mlr10" onclick="return validateTruck()">${title}</button>
                <button type="button" onclick="history.back();" class="btn btn-danger mlr10">Cancel</button>
            </div>
        </form>
    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>