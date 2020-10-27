<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="date" class="java.util.Date" />

<footer class="footer mt-auto py-3">
    <div class="container d-flex justify-content-center">
        <span class="text-light">Copyright &#169; <fmt:formatDate value="${date}" pattern="yyyy" /> Logistic company</span>
    </div>
</footer>

<script src="${param.contextPath}/resources/js/jquery-3.5.1.min.js"></script>
<script src="${param.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
<script src="${param.contextPath}/resources/js/bootstrap-table.min.js"></script>
<script src="${param.contextPath}/resources/js/bootstrap-table-filter-control.min.js"></script>
<script src="${param.contextPath}/resources/js/sweetalert.min.js"></script>
<script src="${param.contextPath}/resources/js/validate.js"></script>

<script>
    $(function (){
        $('#table').bootstrapTable();
    });
</script>

</body>
</html>