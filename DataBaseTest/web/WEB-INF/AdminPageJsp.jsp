<%-- 
    Document   : AdminPageJsp
    Created on : 2018-mar-11, 00:12:50
    Author     : fredr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../../../favicon.ico">

    <title>Crypto Trader</title>

    <!-- Bootstrap core CSS -->
    <link href="resources/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="resources/css/non-bootcss.css" rel="stylesheet">
</head>

<body class="bg-dark text-light">
    <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
        <a class="navbar-brand" href="homepage.html"><img style="max-width:150px; margin-top: 0px; margin-bottom: -10px; margin-right: 10px;"
                                                          src="resources/css/images/coinfull.png"></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsExampleDefault">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="homepage.html">LEAVE ADMIN MODE<span class="sr-only">(current)</span></a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="nonbootcontainer2">
        <div class="row">
            <main role="main" class="container"><div class="chartjs-size-monitor" style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;"><div class="chartjs-size-monitor-expand" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div></div><div class="chartjs-size-monitor-shrink" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:200%;height:200%;left:0; top:0"></div></div></div>
                <div class="nonbootcontainer2">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap pb-2 mb-3 align-items-center border-bottom">
                        <form class="form-control" novalidate="" method="post" action="AdminPageServlet">
                            <label for="addNewCurrency">Add new currency:</label>
                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <input name="name" type="text" class="form-control" id="currencyName" placeholder="Currency name" value="" required="">
                                    <div class="invalid-feedback">
                                        Currency name is required.
                                    </div>
                                </div>
                                <div class="col-md-6 mb-3">
                                    <input name="value" type="Number" class="form-control" id="currencyValue" placeholder="Currency value" value="" required="">
                                    <div class="invalid-feedback">
                                        Currency value is required.
                                    </div>
                                </div>
                            </div>
                            <hr class="mb-4">
                            <button class="btn btn-lg btn-primary btn-block" type="submit">Add currency</button>
                        </form>
                    </div>

                    <h2>Statistics </h2>
                    <div class="table-responsive text-light">
                        <table class="table table-striped table-sm">
                            <thead>
                                <tr>
                                    <th>Number of users</th>
                                    <th>Total balance</th>
                                    <th>Average balance</th>
                                    <th>|</th>
                                    <th>Number of currencies</th>
                                </tr>
                            </thead>
                            <tbody>
                                    ${message}
                            </tbody>
                        </table>
                    </div>
                </div>
        </div>

    </div>

</body>

</html>
