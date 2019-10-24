<%-- 
    Document   : bienvenue
    Created on : 19 sept. 2019, 10:40:26
    Author     : JAA
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto|Varela+Round|Open+Sans">
<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>



<div class="container">
    <form action="Controleur" name = "listEmployeForm" method="POST">
        <div class="table-wrapper">
            <div class="table-title">
                <div class="row">
                    <div class="col-sm-8"><h2>Liste des employés</h2></div>
                </div>
            </div>
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th>Sél</th>
                        <th>NOM</th>
                        <th>PRENOM</th>
                        <th>TEL DOMICILE</th>
                        <th>TEL PORTABLE</th>
                        <th>TEL PRO</th>
                        <th>ADRESSE</th>
                        <th>CODE POSTAL</th>
                        <th>VILLE</th>
                        <th>EMAIL</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listeEmplKey}" var="employe">
                    <tr>
                        <td><input type="radio" class="form-check-input" id="sel" name="sel" value="${employe.id}"></td>
                        <td>${employe.nom}</td>
                        <td>${employe.prenom}</td>
                        <td>${employe.telDom}</td>
                        <td>${employe.telPort}</td>
                        <td>${employe.telPro}</td>
                        <td>${employe.adresse}</td>
                        <td>${employe.codePostal}</td>
                        <td>${employe.ville}</td>
                        <td>${employe.email}</td>
                    </tr> 
                    </c:forEach>
                </tbody>
            </table>            
            <div class="col-sm-4">                
                <button type="button" name="action" value="delete" class="btn btn-primary"> Supprimer </button>                
                <button type="submit" name="action" value="details" class="btn btn-primary"> Détails </button>
                <button type="submit" name="action" value ="add" class="btn btn-light"> Ajouter </button>
            </div>
        </div>
    </form>
    </div>    