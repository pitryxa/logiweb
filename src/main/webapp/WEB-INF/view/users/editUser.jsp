<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="title" value="Edit user"/>

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

        <form action="${contextPath}/admin/users/edit" method="post">
            <input type="hidden" name="id" value="${user.id}">
            <input type="hidden" name="currentRole" value="${user.role}">
            <%--            <input type="hidden" name="password" value="${user.password}">--%>
            <div class="form-group row">
                <label for="firstName" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Firstname</label>
                <input type="text" name="firstName" id="firstName" value="${user.firstName}"
                       class="form-control col-sm-10">
            </div>
            <div class="form-group row">
                <label for="lastName" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Lastname</label>
                <input type="text" name="lastName" id="lastName" value="${user.lastName}"
                       class="form-control col-sm-10">
            </div>
            <div class="form-group row">
                <label for="email" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Email</label>
                <input type="text" name="email" id="email" value="${user.email}" class="form-control col-sm-10"
                       readonly>
            </div>
            <div class="form-group row">
                <label for="role" class="text-cadetblue col-form-label col-sm-2 font-weight-bold">Role</label>
                <c:if test="${isRoleUnselectable}">
                    <input type="text" name="role" id="role" value="${user.role}" class="form-control col-sm-10"
                           readonly>
                </c:if>
                <c:if test="${!isRoleUnselectable}">
                    <select name="role" id="role" class="form-control col-sm-10">
                        <c:forEach var="role" items="${roles}">
                            <option value="${role}"
                                    <c:if test="${role == user.role}"><c:out value="selected"/></c:if>>
                                    ${role}
                            </option>
                        </c:forEach>
                    </select>
                </c:if>
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