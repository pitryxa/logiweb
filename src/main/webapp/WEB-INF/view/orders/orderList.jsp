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
        <a href="${contextPath}/officer/orders/add-cargo" class="btn btn-info">Add order</a>
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
                <th data-field="distance" data-sortable="true">Distance</th>
                <th data-field="waypoints" data-filter-control="input" data-sortable="true">Waypoints</th>
                <th data-field="truck" data-filter-control="input" data-sortable="true">Truck</th>
                <th data-field="drivers" data-filter-control="input" data-sortable="true">Drivers</th>
                <th data-field="actions">Actions</th>
            </tr>
            </thead>

            <c:forEach var="order" items="${orders}" varStatus="counter">
                <tr>
                    <td>${counter.count}</td>
                    <td>${order.id}</td>
                    <td>${order.status}</td>
                    <td>${order.distance}</td>
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
                    <td class="action">
                        <a href="${contextPath}/officer/orders/${order.id}" class="text-decoration-none"
                           title="info">
                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-info-square" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                <path fill-rule="evenodd" d="M14 1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
                                <path fill-rule="evenodd" d="M14 1H2a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
                                <path d="M8.93 6.588l-2.29.287-.082.38.45.083c.294.07.352.176.288.469l-.738 3.468c-.194.897.105 1.319.808 1.319.545 0 1.178-.252 1.465-.598l.088-.416c-.2.176-.492.246-.686.246-.275 0-.375-.193-.304-.533L8.93 6.588z"/>
                                <circle cx="8" cy="4.5" r="1"/>
                            </svg>
                        </a>

                        <c:if test="${order.status == 'PREPARED'}">
                            <a href="${contextPath}/officer/orders/delete/${order.id}" class="text-decoration-none"
                               title="delete">
                                <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-trash"
                                     fill="currentColor"
                                     xmlns="http://www.w3.org/2000/svg">
                                    <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                                    <path fill-rule="evenodd"
                                          d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                                </svg>
                            </a>
                        </c:if>
                    </td>

                </tr>
            </c:forEach>
        </table>

        <a href="${contextPath}/officer/orders/add-cargo" class="btn btn-info">Add order</a>
    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>