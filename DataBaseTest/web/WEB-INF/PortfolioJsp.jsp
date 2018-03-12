<%-- 
    Document   : PortfolioJsp
    Created on : 2018-mar-10, 17:09:23
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

<body>
    <nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
        <a class="navbar-brand" href="#"><img style="max-width:150px; margin-top: 0px; margin-bottom: -10px; margin-right: 10px;"
                                                          src="resources/css/images/coinfull.png"></a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarsExampleDefault">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="TraderServlet">Trader</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="PortfolioServlet">My portfolio</a>
                </li>
                 <li class="nav-item">
                    <a class="nav-link" href="suggestionsForm.html">Report Bugs</a>
                </li>
            </ul>
            <form class="form-inline my-2 my-lg-0">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="SignOutServlet">${SignIn}<span class="sr-only">(current)</span></a>
                    </li>
                </ul>
            </form>
        </div>
    </nav>

    <div class="nonbootcontainer2">
        <div class="row">
            <main role="main" class="container"><div class="chartjs-size-monitor" style="position: absolute; left: 0px; top: 0px; right: 0px; bottom: 0px; overflow: hidden; pointer-events: none; visibility: hidden; z-index: -1;"><div class="chartjs-size-monitor-expand" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:1000000px;height:1000000px;left:0;top:0"></div></div><div class="chartjs-size-monitor-shrink" style="position:absolute;left:0;top:0;right:0;bottom:0;overflow:hidden;pointer-events:none;visibility:hidden;z-index:-1;"><div style="position:absolute;width:200%;height:200%;left:0; top:0"></div></div></div>
                <div class="nonbootcontainer2">
                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap pb-2 mb-3 align-items-center border-bottom">
                        <h1 class="h2">Weekly change</h1>
                        <div class="btn-toolbar mb-2 mb-md-0">

                        </div>
                    </div>

                    <canvas class="my-4 chartjs-render-monitor" id="myChart" width="800" height="200" style="display: block; height: 874px; width: 2071px;"></canvas>

                    <h2>Stock portfolio</h2>
                    <div class="table-responsive">
                        <table class="table table-striped table-sm">
                            <thead>
                                <tr>
                                    <th>Stock</th>
                                    <th>Price (USD)</th>
                                    <th>Change</th>
                                    <th>%</th>
                                    <th>Owned amount</th>
                                    <th>Market value</th>
                                    <th>Total change</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${message}
                            </tbody>
                        </table>
                    </div>
                </div>

            </main>
        </div>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script>window.jQuery || document.write('<script src="../../../../assets/js/vendor/jquery-slim.min.js"><\/script>')</script>
    <script src="../../../../assets/js/vendor/popper.min.js"></script>
    <script src="../../../../dist/js/bootstrap.min.js"></script>

    <!-- Icons -->
    <script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
    <script>
                feather.replace()
    </script>

    <!-- Graphs -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.1/Chart.min.js"></script>
    <script>
                var ctx = document.getElementById("myChart");
                var myChart = new Chart(ctx, {
                    type: 'line',
                    data: {
                        ${labels},
                        datasets: [{
                                ${data},
                                lineTension: 0,
                                backgroundColor: 'transparent',
                                borderColor: '#007bff',
                                borderWidth: 4,
                                pointBackgroundColor: '#007bff'
                            }]
                    },
                    options: {
                        scales: {
                            yAxes: [{
                                    ticks: {
                                        beginAtZero: false
                                    }
                                }]
                        },
                        legend: {
                            display: false,
                        }
                    }
                });
    </script>


</body>