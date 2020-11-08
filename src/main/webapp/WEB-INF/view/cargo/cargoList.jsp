<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Cargo List"/>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<main class="flex-shrink-0">
    <div class="container-fluid" id="content">
        <div class="row" id="nameSection">
            <div class="col">
                <h4 class="text-cadetblue">${title}</h4>
            </div>
        </div>
        <a href="${contextPath}/officer/cargo/add" class="lnk-color">Add cargo</a>
        <br>
        <br>

        <div id="toolbar"></div>
        <table id="table"
               data-toggle="table"
               data-filter-control="true"
               data-pagination="true"
               data-page-list="[10, 25, 50, 100, All]"
        <%--               data-page-size = "8"--%>
               class="table table-striped">
            <thead class="thead-dark">
            <tr>
                <th data-field="count" data-sortable="true">#</th>
                <th data-field="numberId" data-sortable="true">ID</th>
                <th data-field="name" data-filter-control="input" data-sortable="true">Name</th>
                <th data-field="weight" data-filter-control="input" data-sortable="true">Weight</th>
                <th data-field="status" data-filter-control="select" data-sortable="true">Status</th>
                <th data-field="cityFrom" data-filter-control="select" data-sortable="true">From</th>
                <th data-field="cityTo" data-filter-control="select" data-sortable="true">To</th>
                <th data-field="order" data-filter-control="input" data-sortable="true">Order</th>
                <th data-field="action">Action</th>
            </tr>
            </thead>

            <c:forEach var="cargo" items="${cargoList}" varStatus="counter">
                <tr>
                    <td>${counter.count}</td>
                    <td>${cargo.id}</td>
                    <td>${cargo.name}</td>
                    <td>${cargo.weight}</td>
                    <td>${cargo.status}</td>
                    <td>${cargo.cityFrom}</td>
                    <td>${cargo.cityTo}</td>
                    <td>
                        <c:if test="${cargo.orderId != null}">
                            <a href="${contextPath}/officer/orders/${cargo.orderId}" class="m-0">
                                <c:out value="${cargo.orderId}"/>
                            </a>
                        </c:if>
                        <c:if test="${cargo.orderId == null}">
                            <c:out value="NONE"/>
                        </c:if>
                    </td>
                    <td class="action">
                        <a href="${contextPath}/officer/cargo/edit/${cargo.id}" class="text-decoration-none"
                           title="edit">
                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-pencil-square"
                                 fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                <path fill-rule="evenodd"
                                      d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                            </svg>
                        </a>
                        <a href="${contextPath}/officer/cargo/delete/${cargo.id}" class="text-decoration-none"
                           title="delete">
                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-trash" fill="currentColor"
                                 xmlns="http://www.w3.org/2000/svg">
                                <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                                <path fill-rule="evenodd"
                                      d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4L4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                            </svg>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <a href="${contextPath}/officer/cargo/add" class="lnk-color">Add cargo</a>
    </div>
</main>


<jsp:include page="../common/footer.jsp">
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>