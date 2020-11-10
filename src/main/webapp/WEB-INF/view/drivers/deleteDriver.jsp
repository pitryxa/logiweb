<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Delete driver"/>

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

        <c:if test="${driver.status != 'RECREATION'}">
            <div class="row">
                <div class="col">
                    <h5 class="text-center text-danger">Driver cannot be deleted while he is executing an order!</h5>
                </div>
            </div>
        </c:if>

        <c:if test="${driver.status == 'RECREATION'}">
            <form action="${contextPath}/officer/drivers/delete" method="post">
                <input type="hidden" name="id" value="${driver.id}">
                <input type="hidden" name="userId" value="${driver.user.id}">
                <input type="hidden" name="truckId" value="${driver.truck.id}">

                <div class="form-group row border-bottom">
                    <div class="col-3 font-weight-bold ml-auto ">
                        <label for="personalNumber" class="float-right py-2 pr-2 text-cadetblue my-2">Personal
                            number</label>
                    </div>
                    <div class="col-6">
                        <input type="text" name="personalNumber" id="personalNumber" value="${driver.personalNumber}"
                               class="form-control-plaintext py-2 my-2" readonly>
                    </div>
                </div>
                <div class="form-group row border-bottom">
                    <div class="col-3 font-weight-bold ml-auto ">
                        <label for="firstname" class="float-right py-2 pr-2 text-cadetblue my-2">First name</label>
                    </div>
                    <div class="col-6">
                        <input type="text" name="firstname" id="firstname" value="${driver.user.firstName}"
                               class="form-control-plaintext py-2 my-2" readonly>
                    </div>
                </div>
                <div class="form-group row border-bottom">
                    <div class="col-3 font-weight-bold ml-auto ">
                        <label for="lastname" class="float-right py-2 pr-2 text-cadetblue my-2">Last name</label>
                    </div>
                    <div class="col-6">
                        <input type="text" name="lastname" id="lastname" value="${driver.user.lastName}"
                               class="form-control-plaintext py-2 my-2" readonly>
                    </div>
                </div>
                <div class="form-group row border-bottom">
                    <div class="col-3 font-weight-bold ml-auto ">
                        <label for="workHours" class="float-right py-2 pr-2 text-cadetblue my-2">Hours worked</label>
                    </div>
                    <div class="col-6">
                        <input type="text" name="workHours" id="workHours" value="${driver.workHours}"
                               class="form-control-plaintext py-2 my-2" readonly>
                    </div>
                </div>
                <div class="form-group row border-bottom">
                    <div class="col-3 font-weight-bold ml-auto ">
                        <label for="status" class="float-right py-2 pr-2 text-cadetblue my-2">Status</label>
                    </div>
                    <div class="col-6">
                        <input type="text" name="status" id="status" value="${driver.status}"
                               class="form-control-plaintext py-2 my-2" readonly>
                    </div>
                </div>
                <div class="form-group row border-bottom">
                    <div class="col-3 font-weight-bold ml-auto ">
                        <label for="truck" class="float-right py-2 pr-2 text-cadetblue my-2">Truck</label>
                    </div>
                    <div class="col-6">
                        <c:if test="${driver.truck == null}">
                            <input type="text" name="truck" id="truck" value="NONE"
                                   class="form-control-plaintext py-2 my-2" readonly>
                        </c:if>
                        <c:if test="${driver.truck != null}">
                            <input type="text" name="truck" id="truck" value="${driver.truck.regNumber}"
                                   class="form-control-plaintext py-2 my-2" readonly>
                        </c:if>
                    </div>
                </div>
                <div class="form-group row border-bottom">
                    <div class="col-3 font-weight-bold ml-auto ">
                        <label for="city" class="float-right py-2 pr-2 text-cadetblue my-2">City</label>
                    </div>
                    <div class="col-6">
                        <input type="text" name="city" id="city" value="${driver.city}"
                               class="form-control-plaintext py-2 my-2" readonly>
                    </div>
                </div>

                <div class="d-flex justify-content-center mt-3">
                    <button type="submit" class="btn btn-success mx-2">Delete</button>
                    <button type="button" onclick="history.back();" class="btn btn-danger mx-2">Cancel</button>
                </div>
            </form>
        </c:if>
    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>