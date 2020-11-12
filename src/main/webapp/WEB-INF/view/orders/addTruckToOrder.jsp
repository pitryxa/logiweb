<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Add truck"/>

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

        <form action="${contextPath}/officer/orders/add-truck" method="post">
            <div class="form-group row">
                <label class=" text-cadetblue col-form-label col-sm-2 font-weight-bold">Cargoes</label>
                <div class="col-sm-10 p-0">
                    <textarea class="form-control" rows="7" style="width: 100%; background-color: white;"
                              readonly><c:forEach var="cargo"
                                                  items="${cargoes}">${cargo.name} (${cargo.weight} kg) from ${cargo.cityFrom} to ${cargo.cityTo}&#013;&#010;</c:forEach></textarea>
                </div>
            </div>

            <div class="form-group row">
                <label for="truck" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Truck</label>
                <select name="truck" id="truck" class="form-control col-sm-10">
                    <c:forEach var="truck" items="${trucks}">
                        <option value="${truck.id}">${truck.regNumber} (${truck.capacity} t, ${truck.shiftSize} drivers)
                            from ${truck.city}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="d-flex justify-content-center">
                <button type="submit" class="btn btn-success mlr10">${title}</button>
                <a href="${contextPath}/officer/orders/add-cargo" class="btn btn-danger mlr10">Cancel</a>
            </div>
        </form>
    </section>
</main>

<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>