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

        <c:if test="${order.status == 'PREPARED'}">
            <div class="row border-bottom">
                <div class="col my-3">
                    <h5 class="text-center text-success pb-2">You have assigned order</h5>
                    <p class="text-center"><a href="${contextPath}/driver/start-order" class="btn btn-lg btn-success">Get
                        started</a></p>
                </div>
            </div>
        </c:if>

        <c:if test="${order.status != 'PREPARED'}">
            <form action="${contextPath}/driver/change-status" method="post" class="m-0">
                <div class="row border-bottom">
                    <div class="col-2 font-weight-bold ml-auto ">
                        <p class="py-2 pr-2 text-cadetblue my-2">Your status</p>
                    </div>
                    <div class="col-10">
                        <p class="py-2 my-2 ">
                            <c:out value="${currentDriver.status}"/>

                            <c:if test="${currentDriver.status == 'ACTIVE_DRIVER'}">
                                <c:if test="${currentWaypoint.operation != 'NONE'}">
                                    <button name="status" value="LOAD_UNLOAD" type="submit"
                                            class="btn btn-success btn-sm mx-2">Start load/unload
                                    </button>
                                </c:if>
                                <button name="status" value="SECOND_DRIVER" type="submit"
                                        class="btn btn-danger btn-sm mx-2">Stop driving
                                </button>
                            </c:if>

                            <c:if test="${currentDriver.status == 'SECOND_DRIVER'}">
                                <c:if test="${currentWaypoint.operation != 'NONE'}">
                                    <button name="status" value="LOAD_UNLOAD" type="submit"
                                            class="btn btn-success btn-sm mx-2">Start load/unload
                                    </button>
                                </c:if>
                                <button name="status" value="ACTIVE_DRIVER" type="submit"
                                        class="btn btn-success btn-sm mx-2">
                                    Start driving
                                </button>
                            </c:if>

                            <c:if test="${currentDriver.status == 'LOAD_UNLOAD'}">
                                <button name="status" value="ACTIVE_DRIVER" type="submit"
                                        class="btn btn-success btn-sm mx-2">
                                    Start driving
                                </button>
                                <button name="status" value="SECOND_DRIVER" type="submit"
                                        class="btn btn-danger btn-sm mx-2">Stop load/unload
                                </button>
                            </c:if>


                                <%--                            <a href="${contextPath}/driver/change-status" class="btn btn-sm btn-info ml-3">Change</a>--%>
                        </p>
                    </div>
                </div>
            </form>


        </c:if>

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
                <p class="py-2 pr-2 text-cadetblue my-2">Truck</p>
            </div>
            <div class="col-sm-10">
                <p class="py-2 my-2"><c:out value="${order.truck.regNumber}"/></p>
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
                        <c:if test="${driver.personalNumber != currentDriver.personalNumber}">
                            <tr>
                                <td><c:out value="${driver.personalNumber}"/></td>
                                <td><c:out value="${driver.user.firstName} ${driver.user.lastName}"/></td>
                                <td><c:out value="${driver.status}"/></td>
                            </tr>
                        </c:if>
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
                            <c:if test="${order.status != 'PREPARED'}">
                                <td class="align-middle">
                                    <c:if test="${isActionsActive == 'true'}">
                                        <c:set var="isActionsActive" value="false"/>
                                        <a href="${contextPath}/driver/done-waypoint/${waypoint.id}"
                                           class="btn btn-success btn-sm">DONE</a>
                                    </c:if>
                                </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>


    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>