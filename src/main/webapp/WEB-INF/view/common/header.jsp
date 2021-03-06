<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="en" class="h-100">
<head>
    <link rel="stylesheet" href="${param.contextPath}/resources/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
          integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">
    <link rel="stylesheet" href="${param.contextPath}/resources/css/bootstrap-table.min.css" type="text/css">
    <link rel="stylesheet" href="${param.contextPath}/resources/css/bootstrap-table-filter-control.min.css"
          type="text/css">
    <link rel="stylesheet" href="${param.contextPath}/resources/css/common.css" type="text/css">
    <title>${param.title}</title>
</head>
<body class="d-flex flex-column h-100">
<header class="container-fluid" id="header">
    <div class="row">
        <div class="col-4" id="logo">
            <a href="${param.contextPath}/">Logistic company</a>
        </div>

        <div class="col-6" id="menu">
            <ul class="nav justify-content-center">

                <sec:authorize access="hasRole('ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link" href="${param.contextPath}/admin/users">Users</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${param.contextPath}/admin/city">Cities</a>
                    </li>
                </sec:authorize>

                <sec:authorize access="hasRole('MANAGER')">
                    <li class="nav-item">
                        <a class="nav-link active" href="${param.contextPath}/officer/orders">Orders</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${param.contextPath}/officer/trucks">Trucks</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${param.contextPath}/officer/drivers">Drivers</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${param.contextPath}/officer/cargo">Cargo</a>
                    </li>
                </sec:authorize>

                <sec:authorize access="hasRole('DRIVER')">
                    <li class="nav-item">
                        <a class="nav-link" href="${param.contextPath}/driver">Personal card</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${param.contextPath}/driver/order">Order</a>
                    </li>
                </sec:authorize>

            </ul>
        </div>

        <div class="col-2" id="login">
            <sec:authorize access="!isAuthenticated()"><a href="${param.contextPath}/login"
                                                          class="nav-link"></sec:authorize>
            <sec:authorize access="isAuthenticated()"><a href="${param.contextPath}/logout"
                                                         class="nav-link"></sec:authorize>

                <svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-person" fill="currentColor"
                     xmlns="http://www.w3.org/2000/svg">
                    <path fill-rule="evenodd"
                          d="M10 5a2 2 0 1 1-4 0 2 2 0 0 1 4 0zM8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6zm6 5c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4zm-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10c-2.29 0-3.516.68-4.168 1.332-.678.678-.83 1.418-.832 1.664h10z"/>
                </svg>
                <span>
                    <sec:authorize access="!isAuthenticated()">
                        Sign in
                    </sec:authorize>
                    <sec:authorize access="isAuthenticated()">
                        Sign out
                    </sec:authorize>
                </span>
            </a>
        </div>
    </div>
</header>