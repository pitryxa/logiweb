<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Add distance"/>

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

        <form action="${contextPath}/admin/distance/add" method="post">
            <div class="form-group row">
                <label for="city-from" class=" text-cadetblue col-form-label col-sm-2 font-weight-bold">From</label>
                <select name="cityFrom" id="city-from" class="form-control col-sm-10">
                    <c:forEach var="city" items="${cities}">
                        <option value="${city.name}">${city.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group row">
                <label for="city-to" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">To</label>
                <select name="cityTo" id="city-to" class="form-control col-sm-10">
                    <c:forEach var="city" items="${cities}">
                        <option value="${city.name}">${city.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group row">
                <label for="distance" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Distance (km)</label>
                <input type="number" step="1" min="1" name="distance" id="distance" class="form-control col-sm-10"
                       required>
            </div>

            <div class="d-flex justify-content-center">
                <button type="submit" class="btn btn-success mx-3" onclick="return validateDistance()">${title}</button>
                <button type="button" onclick="history.back();" class="btn btn-danger mx-3">Cancel</button>
            </div>
        </form>
    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>
