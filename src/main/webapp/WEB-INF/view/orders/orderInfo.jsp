<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Order"/>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<main class="flex-shrink-0">
    <section class="container" id="content">
        <div class="row border-bottom" id="nameSection">
            <div class="col">
                <h4 class="text-center text-cadetblue">${title}</h4>
            </div>
        </div>

        <div class="row border-bottom">
            <div class="col-sm-2 font-weight-bold ml-auto ">
                <p class="py-2 pr-2 text-cadetblue my-2">Order number</p>
            </div>
            <div class="col-sm-10">
                <p class="py-2 my-2"><c:out value="${order.id}"/></p>
            </div>
        </div>
        <div class="row border-bottom">
            <div class="col-sm-2 font-weight-bold ml-auto ">
                <p class="py-2 pr-2 text-cadetblue my-2">Distance</p>
            </div>
            <div class="col-sm-10">
                <p class="py-2 my-2"><c:out value="${order.distance}"/></p>
            </div>
        </div>
        <div class="row border-bottom">
            <div class="col-sm-2 font-weight-bold ml-auto ">
                <p class="py-2 pr-2 text-cadetblue my-2">Truck</p>
            </div>
            <div class="col-sm-10">
                <p class="py-2 my-2">
                    <a href="${contextPath}/officer/trucks/${order.truck.id}" class="m-0">
                        <c:out value="${order.truck.regNumber}"/>
                    </a>
                </p>
            </div>
        </div>
        <div class="row border-bottom">
            <div class="col-md-2 font-weight-bold ml-auto ">
                <p class="py-1 pr-2 text-cadetblue my-2">Drivers</p>
            </div>
            <div class="col-md-10">
                <table class="table-borderless table-sm py-2 my-2">
                    <tbody>
                    <c:forEach var="driver" items="${order.drivers}">
                        <tr>
                            <td>
                                <a href="${contextPath}/officer/drivers/${driver.id}" class="m-0">
                                    <c:out value="${driver.personalNumber}"/>
                                </a>
                            </td>
                            <td><c:out value="${driver.user.firstName} ${driver.user.lastName}"/></td>

                            <c:if test="${order.status != 'DONE'}">
                                <td><c:out value="${driver.status}"/></td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row border-bottom">
            <div class="col-xl-2 font-weight-bold ml-auto ">
                <p class="py-1 pr-2 text-cadetblue my-2">Waypoints</p>
            </div>
            <div class="col-xl-10">
                <table class="table table-sm py-2 my-2">
                    <tbody>
                    <c:set var="trClass" value="table-success"/>
                    <c:set var="isActionsActive" value="false"/>
                    <c:forEach var="waypoint" items="${order.waypoints}">
                        <c:if test="${waypoint.status == 'UNDONE'}">
                            <c:if test="${trClass != 'table-success'}">
                                <c:set var="trClass" value="table-danger"/>
                            </c:if>
                            <c:if test="${trClass == 'table-success'}">
                                <c:set var="trClass" value="table-warning font-weight-bold"/>
                                <c:set var="isActionsActive" value="true"/>
                            </c:if>
                        </c:if>


                        <tr class="${trClass}">
                            <td class="align-middle"><c:out value="${waypoint.sequentialNumber}"/></td>
                            <td class="align-middle"><c:out value="${waypoint.city}"/></td>
                                <%--                                    <td><c:out value="${waypoint.status}"/></td>--%>
                            <td class="align-middle"><c:out value="${waypoint.operation}"/></td>
                            <td class="align-middle"><c:out value="${waypoint.cargo.name}"/></td>
                            <td class="align-middle">
                                <c:if test="${waypoint.operation != 'NONE'}">
                                    <c:out value="${waypoint.cargo.weight} kg"/>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="d-flex justify-content-center mt-3">
            <button type="button" onclick="history.back();" class="btn btn-secondary mx-2">Back</button>
        </div>


    </section>

</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>