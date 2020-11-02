<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Start order"/>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<main class="flex-shrink-0">
    <section class="container" id="content">
        <div class="row border-bottom" id="nameSection">
            <div class="col">
                <h4 class="text-center text-cadetblue">${title}</h4>
            </div>
        </div>

        <form action="${contextPath}/driver/start-order" method="post">
            <div class="row form-group">
                <div class="col my-3">
                    <h5 class="text-center text-success pb-2">Are you sure you want to get started?</h5>
                    <p class="text-center">
                        <button type="submit" class="btn btn-success mx-2">Get started</button>
                        <button type="button" onclick="history.back();" class="btn btn-secondary mx-2">Cancel</button>
                    </p>
                </div>
            </div>
        </form>
    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>