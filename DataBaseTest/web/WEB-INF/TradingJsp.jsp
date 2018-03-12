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
                    <a class="nav-link" href="suggestionsForm.html">Report Bug</a>
                </li>
            </ul>

            <form class="form-search row bg-dark mr-sm-0" method="post" action="TraderServlet" >
                <input name="searchVal" id="searchValu" class="form-control mr-sm-2 col-sm-8" placeholder="Search..." required="" autofocus="">
                <button class="btn btn-secondary my-2 my-sm-0 col-sm-3" type="submit">Search </button>
                <p id ="searchBut"></p>
            </form>

            <form class="form-inline my-2 my-lg-0">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="SignOutServlet">${SignIn}<span class="sr-only">(current)</span></a>
                    </li>
                </ul>
            </form>
        </div>
    </nav>

    <main role="main">

        <div class="album py-5 bg-light">
            <div class="container">
                <br>
                <br>
                <br>

                <!--module display-->
                ${message}
            </div>
        </div>
    </div>

</main>



${graphs}

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script>window.jQuery || document.write('<script src="../../../../assets/js/vendor/jquery-slim.min.js"><\/script>')</script>
<script src="../../../../assets/js/vendor/popper.min.js"></script>
<script src="../../../../dist/js/bootstrap.min.js"></script>

<!-- Icons -->
<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
<script>
    feather.replace()
</script>


</body>