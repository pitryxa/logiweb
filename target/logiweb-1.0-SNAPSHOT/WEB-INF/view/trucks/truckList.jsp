<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Truck List"/>

<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="${title}"/>
    <jsp:param name="contextPath" value="${contextPath}"/>
</jsp:include>

<main class="flex-shrink-0">
    <section class="container-fluid" id="content">
        <div class="row" id="nameSection">
            <div class="col">
                <h4 class="text-cadetblue">${title}</h4>
            </div>
        </div>
        <a href="/officer/trucks/add" class="lnk-color">Add truck</a>
        <br>
        <br>
        <table class="table table-striped">
            <%--    <caption>Cargo List</caption>--%>
            <thead class="thead-dark">
            <tr>
                <th>ID</th>
                <th>Reg. number</th>
                <th>Shift size</th>
                <th>Capacity</th>
                <th>Status</th>
                <th>City</th>
                <th>Drivers</th>
                <th>Action</th>
            </tr>
            </thead>

            <c:forEach var="truck" items="${trucks}">
                <tr>
                    <td>${truck.id}</td>
                    <td>${truck.regNumber}</td>
                    <td>${truck.shiftSize}</td>
                    <td>${truck.capacity}</td>
                    <td>${truck.status}</td>
                    <td>${truck.city}</td>
                    
                    <td>
                        <c:if test="${truck.drivers == null}">NONE</c:if>
                        <c:if test="${truck.drivers != null}">
                            <c:forEach var="driver" items="${truck.drivers}">
                                <p class="m-0"><a href="/officer/drivers/${driver.id}">${driver.user.firstName} ${driver.user.lastName} (${driver.id})</a></p>
                            </c:forEach>
                        </c:if>
                    </td>
                    
                    
                    <td class="action">
                        <a href="/officer/trucks/edit/${truck.id}" class="text-decoration-none" title="edit">
                            <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-pencil-square"
                                 fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                <path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456l-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/>
                                <path fill-rule="evenodd"
                                      d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5v11z"/>
                            </svg>
                        </a>
                        <a href="/officer/trucks/delete/${truck.id}" class="text-decoration-none" title="delete">
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

        <a href="/officer/trucks/add" class="lnk-color">Add truck</a>
    </section>
</main>

<jsp:include page="../common/footer.jsp"/>