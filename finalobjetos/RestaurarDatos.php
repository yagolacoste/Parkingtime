<?php
include('functions.php'); 
$lat=$_GET["latitud"];
$lng=$_GET["longitud"];
$id=$_GET["idCuadra"];

ejecutarSQLCommand("update calle set vacio=0 where latitud='$lat' and longitud='$lng'; 
update cuadra set cantidad=cantidad+1 where idCuadra='$id';");
?>