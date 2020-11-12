<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Add cargo"/>

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

        <form action="${contextPath}/officer/orders/add-cargo" method="post">
            <div class="form-group row">
                <label for="cargo" class=" text-cadetblue col-form-label col-sm-2 font-weight-bold">Add cargo</label>
                <select name="cargoes" id="cargo" multiple size="10" class="form-control col-sm-10" required
                        style="resize: both">
                    <c:forEach var="cargo" items="${cargoes}">
                        <option value="${cargo.id}">${cargo.name} (${cargo.weight} kg) from ${cargo.cityFrom}
                            to ${cargo.cityTo}</option>
                    </c:forEach>
                </select>

            </div>

            <div class="d-flex justify-content-center">
                <button type="submit" class="btn btn-success mlr10">${title}</button>
                <a href="${contextPath}/officer/orders" class="btn btn-danger mlr10">Cancel</a>
            </div>


        </form>
    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>