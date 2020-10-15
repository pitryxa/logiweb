<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="date" class="java.util.Date" />
<footer class="footer mt-auto py-3">
    <div class="container d-flex justify-content-center">
        <span class="text-light">Copyright &#169; <fmt:formatDate value="${date}" pattern="yyyy" /> Logistic company</span>
    </div>
</footer>
</body>
</html>