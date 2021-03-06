<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Driver info"/>

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
                <p class="text-right py-2 pr-2 text-cadetblue my-2">Personal number</p>
            </div>
            <div class="col-6"><p class="py-2 my-2">${driver.personalNumber}</p></div>
        </div>
        <div class="row  border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="text-right py-2 pr-2 text-cadetblue my-2">First name</p>
            </div>
            <div class="col-6"><p class="py-2 my-2">${driver.user.firstName}</p></div>
        </div>
        <div class="row  border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="text-right py-2 pr-2 text-cadetblue my-2">Last name</p>
            </div>
            <div class="col-6"><p class="py-2 my-2">${driver.user.lastName}</p></div>
        </div>
        <div class="row  border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="text-right py-2 pr-2 text-cadetblue my-2">Hours worked</p>
            </div>
            <div class="col-6"><p class="py-2 my-2">${driver.workHours}</p></div>
        </div>
        <div class="row  border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="text-right py-2 pr-2 text-cadetblue my-2">Status</p>
            </div>
            <div class="col-6"><p class="py-2 my-2">${driver.status}</p></div>
        </div>
        <div class="row  border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="text-right py-2 pr-2 text-cadetblue my-2">Truck</p>
            </div>
            <div class="col-6">
                <p class="py-2 my-2">
                    <c:if test="${driver.truck == null}">NONE</c:if>
                    <c:if test="${driver.truck != null}">
                        <a href="${contextPath}/officer/trucks/${driver.truck.id}" class="m-0">
                            <c:out value="${driver.truck.regNumber}"/>
                        </a>
                    </c:if>
                </p>
            </div>
        </div>
        <div class="row  border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="text-right py-2 pr-2 text-cadetblue my-2">Order</p>
            </div>
            <div class="col-6">
                <p class="py-2 my-2">
                    <c:if test="${driver.orderId == null}">NONE</c:if>
                    <c:if test="${driver.orderId != null}">
                        <a href="${contextPath}/officer/orders/${driver.orderId}" class="m-0">
                            <c:out value="${driver.orderId}"/>
                        </a>
                    </c:if>
                </p>
            </div>
        </div>
        <div class="row  border-bottom">
            <div class="col-3 font-weight-bold ml-auto ">
                <p class="text-right py-2 pr-2 text-cadetblue my-2">City</p>
            </div>
            <div class="col-6"><p class="py-2 my-2">${driver.city}</p></div>
        </div>

        <div class="d-flex justify-content-center mt-3">
            <a href="${contextPath}/officer/drivers/edit/${driver.id}" class="btn btn-success mx-2">Edit</a>
            <a href="${contextPath}/officer/drivers/delete/${driver.id}" class="btn btn-danger mx-2">Delete</a>
            <button type="button" onclick="history.back();" class="btn btn-secondary mx-2">Back</button>
        </div>
    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>