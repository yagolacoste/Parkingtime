<?php include ('functions.php');
$latitud=$_GET['latitud'];
$longitud=$_GET['longitud'];
$id=$_GET['idCuadra'];

ejecutarSQLCommand("update  calle set vacio=1 where latitud='$latitud' and longitud='$longitud';  
update cuadra set cantidad=cantidad-1 where idCuadra='$id';");
 ?>
