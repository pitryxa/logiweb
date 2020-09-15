<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: boldarev
  Date: 07.09.2020
  Time: 13:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV"
            crossorigin="anonymous"></script>
    <style>
        section {
            margin: 20px;
        }
        h2{
            margin-bottom: 20px;
        }
    </style>
    <title>Edit Cargo</title>
</head>
<body>
<section>
    <h2>Edit cargo #${cargo.id}</h2>

    <form action="/cargo/edit" method="post">
        <input type="hidden" name="id" value="${cargo.id}">
        <div class="form-group">
            <label for="name" class="control-label">Name</label>
<%--            <div class="col-xs-10">--%>
                <input type="text" name="name" id="name" value="${cargo.name}">
<%--            </div>--%>
        </div>
        <div class="form-group">
            <label for="weight" class="control-label">Weight</label>
<%--            <div class="col-xs-10">--%>
                <input type="text" name="weight" id="weight" value="${cargo.weight}">
<%--            </div>--%>
        </div>
        <div class="form-group">
            <label for="status" class="control-label">Status</label>
<%--            <div class="col-xs-10">--%>
                <select name="status" id="status">
                    <option value="PREPARED"
                            <c:if test="${cargo.status == 'PREPARED'}"><c:out value = "selected"/></c:if>>
                            PREPARED
                    </option>
                    <option value="SHIPPED"
                            <c:if test="${cargo.status == 'SHIPPED'}"><c:out value = "selected"/></c:if>>
                            SHIPPED
                    </option>
                    <option value="DELIVERED"
                            <c:if test="${cargo.status == 'DELIVERED'}"><c:out value = "selected"/></c:if>>
                            DELIVERED
                    </option>
                </select>
<%--            </div>--%>
        </div>
        <button type="submit" class="btn btn-success">Edit cargo</button>
        <button type="button" onclick="history.back();" class="btn btn-light">Cancel</button>
    </form>
</section>


</body>
</html>
