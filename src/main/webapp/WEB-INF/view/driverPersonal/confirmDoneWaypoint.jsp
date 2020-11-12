<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Confirm done waypoint"/>

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

        <form action="${contextPath}/driver/done-waypoint/" method="post">
            <input type="hidden" name="id" value="${waypoint.id}">
            <input type="hidden" name="orderId" value="${waypoint.orderId}">

            <div class="row border-bottom">
                <div class="col-3 font-weight-bold ml-auto ">
                    <p class="py-2 pr-2 text-cadetblue my-2">Sequential number</p>
                </div>
                <div class="col-6">
                    <p class="py-2 my-2"><c:out value="${waypoint.sequentialNumber}"/></p>
                </div>
            </div>

            <div class="row border-bottom">
                <div class="col-3 font-weight-bold ml-auto ">
                    <p class="py-2 pr-2 text-cadetblue my-2">City</p>
                </div>
                <div class="col-6">
                    <p class="py-2 my-2"><c:out value="${waypoint.city}"/></p>
                </div>
            </div>

            <div class="row border-bottom">
                <div class="col-3 font-weight-bold ml-auto ">
                    <p class="py-2 pr-2 text-cadetblue my-2">Cargo</p>
                </div>
                <div class="col-6">
                    <c:if test="${waypoint.cargo == null}">
                        <p class="py-2 my-2">NONE</p>
                    </c:if>

                    <c:if test="${waypoint.cargo != null}">
                        <p class="py-2 my-2"><c:out value="${waypoint.cargo.name} (${waypoint.cargo.weight} kg)"/></p>
                    </c:if>
                </div>
            </div>

            <div class="row border-bottom">
                <div class="col-3 font-weight-bold ml-auto ">
                    <p class="py-2 pr-2 text-cadetblue my-2">Operation</p>
                </div>
                <div class="col-6">
                    <p class="py-2 my-2"><c:out value="${waypoint.operation}"/></p>
                </div>
            </div>

            <div class="d-flex justify-content-center mt-4">
                <button type="submit" class="btn btn-success mx-2">Done</button>
                <a href="${contextPath}/driver/order" class="btn btn-danger mx-2">Cancel</a>
            </div>
        </form>
    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>