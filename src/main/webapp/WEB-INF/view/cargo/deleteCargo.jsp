<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Delete cargo"/>

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
        <c:if test="${cargo.status != 'PREPARED'}">
            <div class="row">
                <div class="col">
                    <h5 class="text-center text-danger">The cargo cannot be deleted because it has been shipped or
                        delivered!
                    </h5>
                </div>
            </div>
        </c:if>

        <c:if test="${cargo.status == 'PREPARED'}">
            <form action="${contextPath}/officer/cargo/delete" method="post">
                <input type="hidden" name="id" value="${cargo.id}">
                <div class="form-group row">
                    <label for="name" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Name</label>
                    <input type="text" name="name" id="name" value="${cargo.name}"
                           class="form-control-plaintext col-sm-10"
                           readonly>
                </div>
                <div class="form-group row">
                    <label for="weight" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Weight</label>
                    <input type="text" name="weight" id="weight" value="${cargo.weight}"
                           class="form-control-plaintext col-sm-10" readonly>
                </div>
                <div class="form-group row">
                    <label for="status" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Status</label>
                    <input type="text" name="status" id="status" value="${cargo.status}"
                           class="form-control-plaintext col-sm-10" readonly>
                </div>
                <div class="form-group row">
                    <label for="city-from" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">From</label>
                    <input type="text" name="cityFrom" id="city-from" value="${cargo.cityFrom}"
                           class="form-control-plaintext col-sm-10" readonly>
                </div>
                <div class="form-group row">
                    <label for="city-to" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">To</label>
                    <input type="text" name="cityTo" id="city-to" value="${cargo.cityTo}"
                           class="form-control-plaintext col-sm-10" readonly>
                </div>
                <div class="d-flex justify-content-center">
                    <button type="submit" class="btn btn-success mlr10">${title}</button>
                    <button type="button" onclick="history.back();" class="btn btn-danger mlr10">Cancel</button>
                </div>
            </form>
        </c:if>
    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>