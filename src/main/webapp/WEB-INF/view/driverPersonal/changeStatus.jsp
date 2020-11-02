<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Change status"/>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<main class="flex-shrink-0">
    <%--    <h4 class="text-center text-cadetblue mt-3">${title}</h4>--%>
    <%--    <h5 class="text-center text-success mt-3">No assigned orders now</h5>--%>

    <section class="container form-content" id="content">
        <div class="row" id="nameSection">
            <div class="col">
                <h4 class="text-center text-cadetblue">${title}</h4>
            </div>
        </div>

        <form action="${contextPath}/driver/change-status" method="post">

            <div class="form-group row">
                <label for="status" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Status</label>
                <select name="status" id="status" class="form-control col-sm-10">
                    <c:forEach var="status" items="${statuses}">
                        <option value="${status}">${status}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="d-flex justify-content-center">
                <button type="submit" class="btn btn-success mlr10">${title}</button>
                <button type="button" onclick="history.back();" class="btn btn-danger mlr10">Cancel</button>
            </div>
        </form>

    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>