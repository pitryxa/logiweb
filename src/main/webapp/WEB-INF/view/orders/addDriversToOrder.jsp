<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Add drivers"/>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<main class="flex-shrink-0">
    <section class="container form-content" id="content">
        <div class="row" id="nameSection">
            <div class="col">
                <h4 class="text-center text-cadetblue">${title} to order</h4>
            </div>
        </div>

        <form action="${contextPath}/officer/orders/add-drivers" method="post">
            <div class="form-group row">
                <label class=" text-cadetblue col-form-label col-sm-2 font-weight-bold">Cargoes</label>
                <div class="col-sm-10 p-0">
                    <textarea class="form-control"
                              rows="7"
                              style="width: 100%; background-color: white;"
                              readonly><c:forEach var="cargo"
                                                  items="${cargoes}">${cargo.name} (${cargo.weight} kg) from ${cargo.cityFrom} to ${cargo.cityTo}&#013;&#010;</c:forEach></textarea>
                </div>
            </div>

            <div class="form-group row">
                <label class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Truck</label>
                <div class="col-sm-10 p-0">
                    <input type="text"
                           value="${truck.regNumber} (${truck.capacity} t, ${truck.shiftSize} drivers) from ${truck.city}"
                           class="form-control"
                           readonly
                           style="background-color: white">
                </div>
            </div>

            <div class="form-group row">
                <label class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Route</label>
                <div class="col-sm-10 p-0">
                    <table class="table table-sm table-bordered" style="background-color: white">
                        <thead>
                        <tr>
                            <th>#</th>
                            <th>City</th>
                            <th>Operation</th>
                            <th>Cargo</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="waypoint" items="${route.waypoints}" varStatus="counterOuter">
                            <c:if test="${waypoint.cargoes.size() == 0}">
                                <tr>
                                    <td><c:out value="${counterOuter.count}"/></td>
                                    <td colspan="3"><c:out value="${waypoint.city.name}"/></td>
                                </tr>
                            </c:if>

                            <c:forEach var="cargo" items="${waypoint.cargoes}" varStatus="counterInner">
                                <tr>
                                    <c:if test="${counterInner.count == 1}">
                                        <td rowspan="${waypoint.cargoes.size()}"><c:out
                                                value="${counterOuter.count}"/></td>
                                        <td rowspan="${waypoint.cargoes.size()}"><c:out
                                                value="${waypoint.city.name}"/></td>
                                    </c:if>
                                    <td><c:out value="${cargo.value}"/></td>
                                    <td><c:out value="${cargo.key.name}"/></td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                        </tbody>
                    </table>

                </div>
            </div>

            <div class="form-group row">
                <label for="drivers" class=" text-cadetblue col-form-label col-sm-2 font-weight-bold">Drivers</label>
                <select name="drivers" id="drivers" multiple size="5" class="form-control col-sm-10">
                    <c:forEach var="driver" items="${drivers}">
                        <option value="${driver.id}">${driver.user.firstName} ${driver.user.lastName} (${driver.id})
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="d-flex justify-content-center">
                <button type="submit" class="btn btn-success mlr10">${title}</button>
                <a href="${contextPath}/officer/orders/add-truck" class="btn btn-danger mlr10">Cancel</a>
                <%--                <button type="button" onclick="history.back();" class="btn btn-danger mlr10">Cancel</button>--%>
            </div>

        </form>
    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>