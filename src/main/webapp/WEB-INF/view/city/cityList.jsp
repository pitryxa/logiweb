<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="City List"/>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<main class="flex-shrink-0">
    <section class="container-fluid" id="content">
        <div class="row" id="nameSection">
            <div class="col">
                <h4 class="text-cadetblue">${title}</h4>
            </div>
        </div>
        <a href="${contextPath}/admin/city/add" class="btn btn-info mr-3">Add city</a>
        <a href="${contextPath}/admin/distance/add" class="btn btn-info">Add distance</a>
        <br>
        <br>
        <table id="table"
               data-toggle="table"
               data-filter-control="true"
               data-pagination="true"
               data-page-list="[10, 25, 50, 100, All]"
               class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th data-field="count" data-sortable="true">#</th>
                <th data-field="id" data-sortable="true">ID</th>
                <th data-field="name" data-filter-control="input" data-sortable="true">Name</th>
            </tr>
            </thead>

            <c:forEach var="city" items="${cityList}" varStatus="counter">
                <tr>
                    <td>${counter.count}</td>
                    <td>${city.id}</td>
                    <td>${city.name}</td>
                </tr>
            </c:forEach>
        </table>

        <a href="${contextPath}/city/add" class="btn btn-info mr-3">Add city</a>
        <a href="${contextPath}/city/add" class="btn btn-info">Add distance</a>
    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>