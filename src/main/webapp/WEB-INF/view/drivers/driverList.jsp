<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Driver List"/>

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
        <a href="${contextPath}/officer/drivers/add" class="btn btn-info">Add driver</a>
        <br>
        <br>
        <table id="table"
               data-toggle="table"
               data-filter-control="true"
               data-pagination="true"
               class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th data-field="count" data-sortable="true">#</th>
                <th data-field="personalNumber" data-filter-control="input" data-sortable="true">Personal number</th>
                <th data-field="firstName" data-filter-control="input" data-sortable="true">First name</th>
                <th data-field="lastName" data-filter-control="input" data-sortable="true">Last name</th>
                <th data-field="hours" data-filter-control="input" data-sortable="true">Hours worked</th>
                <th data-field="status" data-filter-control="select" data-sortable="true">Status</th>
                <th data-field="truck" data-filter-control="input" data-sortable="true">Truck</th>
                <th data-field="order" data-filter-control="input" data-sortable="true">Order</th>
                <th data-field="city" data-filter-control="select" data-sortable="true">City</th>
                <th data-field="action">Action</th>
            </tr>
            </thead>

            <c:forEach var="driver" items="${driverList}" varStatus="counter">
                <tr>
                    <td>${counter.count}</td>
                    <td>${driver.personalNumber}</td>
                    <td>${driver.user.firstName}</td>
                    <td>${driver.user.lastName}</td>
                    <td>${driver.workHours}</td>
                    <td>${driver.status}</td>
                    <td>
                        <c:if test="${driver.truck == null}">
                            <c:out value="NONE"/>
                        </c:if>
                        <c:if test="${driver.truck != null}">
                            <a href="${contextPath}/officer/trucks/${driver.truck.id}" class="m-0">
                                <c:out value="${driver.truck.regNumber}"/>
                            </a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${driver.orderId == null}">
                            <c:out value="NONE"/>
                        </c:if>
                        <c:if test="${driver.orderId != null}">
                            <a href="${contextPath}/officer/orders/${driver.orderId}" class="m-0">
                                <c:out value="${driver.orderId}"/>
                            </a>
                        </c:if>
                    </td>

                    <td>${driver.city}</td>
                    <td class="action">
                        <a href="${contextPath}/officer/drivers/edit/${driver.id}"
                           class="text-decoration-none" title="edit">
                            <svg width="1em" height="1em" viewBox="0 0 16 16"
                                 class="bi bi-pencil-square"
                                 fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                <path fill-rule="evenodd"
                                      d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                            </svg>
                        </a>

                        <c:if test="${driver.status != 'DISABLED'}">
                            <a href="${contextPath}/officer/drivers/delete/${driver.id}" class="text-decoration-none"
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

                        <c:if test="${driver.status == 'DISABLED'}">
                            <a href="${contextPath}/officer/drivers/enable/${driver.id}" class="text-decoration-none"
                               title="enable">
                                <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-unlock"
                                     fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                    <path fill-rule="evenodd"
                                          d="M9.655 8H2.333c-.264 0-.398.068-.471.121a.73.73 0 0 0-.224.296 1.626 1.626 0 0 0-.138.59V14c0 .342.076.531.14.635.064.106.151.18.256.237a1.122 1.122 0 0 0 .436.127l.013.001h7.322c.264 0 .398-.068.471-.121a.73.73 0 0 0 .224-.296 1.627 1.627 0 0 0 .138-.59V9c0-.342-.076-.531-.14-.635a.658.658 0 0 0-.255-.237A1.122 1.122 0 0 0 9.655 8zm.012-1H2.333C.5 7 .5 9 .5 9v5c0 2 1.833 2 1.833 2h7.334c1.833 0 1.833-2 1.833-2V9c0-2-1.833-2-1.833-2zM8.5 4a3.5 3.5 0 1 1 7 0v3h-1V4a2.5 2.5 0 0 0-5 0v3h-1V4z"/>
                                </svg>
                            </a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <a href="${contextPath}/officer/drivers/add" class="btn btn-info">Add driver</a>
    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>