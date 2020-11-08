<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Order List"/>

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
        <a href="${contextPath}/officer/orders/add-cargo" class="lnk-color">Add order</a>
        <br>
        <br>
        <table id="table"
               data-toggle="table"
               data-filter-control="true"
               data-pagination="true"
               data-page-size="2"
               data-page-list="[2, 10, 25, 50, 100, All]"
               class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th data-field="count" data-sortable="true">#</th>
                <th data-field="number" data-filter-control="input" data-sortable="true">Number</th>
                <th data-field="status" data-filter-control="select" data-sortable="true">Status</th>
                <th data-field="waypoints" data-filter-control="input" data-sortable="true">Waypoints</th>
                <th data-field="truck" data-filter-control="input" data-sortable="true">Truck</th>
                <th data-field="drivers" data-filter-control="input" data-sortable="true">Drivers</th>
            </tr>
            </thead>

            <c:forEach var="order" items="${orders}" varStatus="counter">
                <tr>
                    <td>${counter.count}</td>
                    <td>${order.id}</td>
                    <td>${order.status}</td>
                    <td>
                        <table class="table table-sm table-nostriped">
                            <tr>
                                <th>#</th>
                                <th>City</th>
                                <th>Operation</th>
                                <th>Cargo</th>
                                <th>Weight, kg</th>
                            </tr>

                            <tbody>
                            <c:set var="trClass" value="table-success"/>

                            <c:forEach var="waypoint" items="${order.waypoints}">
                                <c:if test="${waypoint.status == 'UNDONE'}">
                                    <c:if test="${trClass != 'table-success'}">
                                        <c:set var="trClass" value="table-danger"/>
                                    </c:if>
                                    <c:if test="${trClass == 'table-success'}">
                                        <c:set var="trClass" value="table-warning font-weight-bold"/>
                                    </c:if>
                                </c:if>

                                <tr class="${trClass}">
                                    <td><c:out value="${waypoint.sequentialNumber}"/></td>
                                    <td><c:out value="${waypoint.city}"/></td>
                                    <td><c:out value="${waypoint.operation}"/></td>
                                    <td><c:out value="${waypoint.cargo.name}"/></td>
                                    <td>
                                        <c:if test="${waypoint.operation != 'NONE'}">
                                            <c:out value="${waypoint.cargo.weight}"/>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </td>
                    <td>
                        <a href="${contextPath}/officer/trucks/${order.truck.id}" class="m-0">
                            <c:out value="${order.truck.regNumber}"/>
                        </a>
                    </td>
                    <td>
                        <c:forEach var="driver" items="${order.drivers}">
                            <p class="mb-1">
                                <a href="${contextPath}/officer/drivers/${driver.id}">
                                    <c:out value="${driver.user.firstName} ${driver.user.lastName} (${driver.personalNumber})"/>
                                </a>
                            </p>
                        </c:forEach>
                    </td>

                </tr>
            </c:forEach>
        </table>

        <a href="${contextPath}/officer/orders/add-cargo" class="lnk-color">Add order</a>
    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>