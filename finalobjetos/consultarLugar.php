<?php
include('functions.php'); 
$id=$_GET["idCuadra"];

if ($resultset = getSQLResultSet("SELECT * FROM `calle` WHERE idCuadra='$id' and vacio=0 limit 1;") ) {
	
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
		
    	
    	}

}
?>
