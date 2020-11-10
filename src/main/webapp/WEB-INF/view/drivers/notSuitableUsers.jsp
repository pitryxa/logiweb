<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="No suitable users"/>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<main class="flex-shrink-0">
    <section class="container form-content" id="content">
        <div class="row" id="nameSection">
            <div class="col">
                <h4 class="text-center text-danger pb-3">No suitable users!</h4>

                <p class="text-center text-cadetblue">
                    <button type="button" onclick="history.back();" class="btn btn-danger mx-3">Back</button>
                </p>
            </div>
        </div>
    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>