<%-- 
    Document   : index
    Created on : 19 sept. 2019, 10:37:20
    Author     : JAA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!------ Include the above in your HEAD tag ---------->

<!doctype html>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <!-- Fonts -->
        <link rel="dns-prefetch" href="https://fonts.gstatic.com">
        <link href="https://fonts.googleapis.com/css?family=Raleway:300,400,600" rel="stylesheet" type="text/css">

        <link rel="stylesheet" href="css/style.css">

        <link rel="icon" href="Favicon.png">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    </head>
    <body>

        <nav class="navbar navbar-expand-lg navbar-light navbar-laravel"></nav>



        <main class="my-form">
            <div class="cotainer">
                <form action="Controleur" name = "detailsFom" method="POST">
                    <div class="row justify-content-center">
                        <div class="col-md-8">
                            <div class="card">
                                <div class="card-header">Détails du membre ${singleUser.nom}</div>
                                <div class="card-body">

                                    <div class="form-group row">
                                        <label for="nom" class="col-md-2 col-form-label text-md-right"><b>Nom</b></label>
                                        <div class="col-md-8">
                                            <input type="text" id="nom" class="form-control" name="nom" placeholder="${singleUser.nom}">
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label for="prenom" class="col-md-2 col-form-label text-md-right"><b>Prénom</b></label>
                                        <div class="col-md-8">
                                            <input type="text" id="prenom" class="form-control" name="prenom" placeholder="${singleUser.prenom}">
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label for="tel_dom" class="col-md-2 col-form-label text-md-right"><b>Tél dom</b></label>
                                        <div class="col-md-8">
                                            <input type="text" id="tel_dom" class="form-control" name="tel_dom" placeholder="${singleUser.telDom}">
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label for="tel_mob" class="col-md-2 col-form-label text-md-right"><b>Tél mob</b></label>
                                        <div class="col-md-8">
                                            <input type="text" id="tel_mob" class="form-control" name="tel_mob" placeholder="${singleUser.telPort}">
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label for="tel_pro" class="col-md-2 col-form-label text-md-right"><b>Tél pro</b></label>
                                        <div class="col-md-8">
                                            <input type="text" id="tel_pro" class="form-control" name="tel_pro" placeholder="${singleUser.telPro}">
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label for="adresse" class="col-md-2 col-form-label text-md-right"><b>Adresse</b></label>
                                        <div class="col-md-3">
                                            <input type="text" id="adresse" class="form-control" name="adresse" placeholder="${singleUser.adresse}">
                                        </div>
                                        <label for="codePostal" class="col-md-2 col-form-label text-md-right"><b>Code Postal</b></label>
                                        <div class="col-md-3">
                                            <input type="text" id="codePostal" class="form-control" name="codePostal" placeholder="${singleUser.codePostal}">
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label for="ville" class="col-md-2 col-form-label text-md-right"><b>Ville</b></label>
                                        <div class="col-md-3">
                                            <input type="text" id="ville" class="form-control" name="ville" placeholder="${singleUser.ville}">
                                        </div>
                                        <label for="email" class="col-md-2 col-form-label text-md-right"><b>Adresse e-mail</b></label>
                                        <div class="col-md-3">
                                            <input type="text" id="email" class="form-control" name="email"placeholder="${singleUser.email}">
                                        </div>
                                    </div>

                                    <div class="col-md-6 offset-md-7">
                                        <button type="submit" class="btn btn-primary">Modifier</button>
                                        <button type="button" class="btn btn-light">Voir liste</button>
                                    </div>

                                </div>

                            </div>
                        </div>
                    </div>
            </div>
        </form>
    </div>

</main>







</body>
</html>