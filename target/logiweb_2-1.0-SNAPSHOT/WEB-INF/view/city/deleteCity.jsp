<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Delete city"/>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<main class="flex-shrink-0">
    <section class="container-fluid" id="content">
        <div class="row" id="nameSection">
            <div class="col">
                <h4>${title} #${city.id}</h4>
            </div>
        </div>

        <form action="/city/delete" method="post">
            <input type="hidden" name="id" value="${city.id}">
            <div class="form-group row">
                <label for="name" class="col-form-label col-sm-2 col-xl-1">Name</label>
                <input type="text" name="name" id="name" value="${city.name}" class="form-control-plaintext col-xl-5 col-sm-10" readonly>
            </div>
            <button type="submit" class="btn btn-success">Delete city</button>
            <button type="button" onclick="history.back();" class="btn btn-danger">Cancel</button>
        </form>
    </section>
</main>

<jsp:include page="../common/footer.jsp"/>