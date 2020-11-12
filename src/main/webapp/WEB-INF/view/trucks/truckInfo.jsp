<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Truck info"/>

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

        <div class="row  border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="text-right py-2 pr-2 text-cadetblue my-2">Id</p>
            </div>
            <div class="col-6"><p class="py-2 my-2">${truck.id}</p></div>
        </div>
        <div class="row  border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="text-right py-2 pr-2 text-cadetblue my-2">Reg. number</p>
            </div>
            <div class="col-6"><p class="py-2 my-2">${truck.regNumber}</p></div>
        </div>
        <div class="row  border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="text-right py-2 pr-2 text-cadetblue my-2">Shift size</p>
            </div>
            <div class="col-6"><p class="py-2 my-2">${truck.shiftSize}</p></div>
        </div>
        <div class="row  border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="text-right py-2 pr-2 text-cadetblue my-2">Capacity (t)</p>
            </div>
            <div class="col-6"><p class="py-2 my-2">${truck.capacity}</p></div>
        </div>
        <div class="row  border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="text-right py-2 pr-2 text-cadetblue my-2">Condition status</p>
            </div>
            <div class="col-6"><p class="py-2 my-2">${truck.conditionStatus}</p></div>
        </div>
        <div class="row  border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="text-right py-2 pr-2 text-cadetblue my-2">Work status</p>
            </div>
            <div class="col-6"><p class="py-2 my-2">${truck.workStatus}</p></div>
        </div>
        <div class="row  border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="text-right py-2 pr-2 text-cadetblue my-2">City</p>
            </div>
            <div class="col-6"><p class="py-2 my-2">${truck.city}</p></div>
        </div>
        <div class="row  border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="text-right py-2 pr-2 text-cadetblue my-2">Order</p>
            </div>
            <div class="col-6">
                <p class="py-2 my-2">
                    <c:if test="${truck.orderId == null}">NONE</c:if>
                    <c:if test="${truck.orderId != null}">
                        <a href="${contextPath}/officer/orders/${truck.orderId}" class="m-0">
                            <c:out value="${truck.orderId}"/>
                        </a>
                    </c:if>
                </p>
            </div>
        </div>
        <div class="row  border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="text-right py-2 pr-2 text-cadetblue my-2">Drivers</p>
            </div>
            <div class="col-6 py-3">
                <c:if test="${truck.drivers == null}">
                    <c:out value="NONE"/>
                </c:if>

                <c:if test="${truck.drivers != null}">
                    <c:forEach var="driver" items="${truck.drivers}">
                        <p class="m-0">
                            <a href="${contextPath}/officer/drivers/${driver.id}" class="m-0">
                                <c:out value="${driver.user.firstName} ${driver.user.lastName} (${driver.personalNumber})"/>
                            </a>
                        </p>
                    </c:forEach>
                </c:if>
            </div>
        </div>

        <div class="d-flex justify-content-center mt-3">
            <a href="${contextPath}/officer/trucks/edit/${truck.id}" class="btn btn-success mx-2">Edit</a>
            <a href="${contextPath}/officer/trucks/delete/${truck.id}" class="btn btn-danger mx-2">Delete</a>
            <button type="button" onclick="history.back();" class="btn btn-secondary mx-2">Back</button>
        </div>
    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>