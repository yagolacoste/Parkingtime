<?php
include('functions.php'); 
$c1=$_GET["calle1"];
$c2=$_GET["calle2"];
$c3=$_GET["calle3"];

if ($resultset = getSQLResultSet("SELECT *
from cuadra
where cantidad>0 and idCuadra in (select idCuadra from calle where calle1 like '$c1' and calle2 like '$c2' and vacio=0 UNION 
select idCuadra from calle where calle1 like '$c2' and calle2 like '$c1' and vacio=0  UNION 
select idCuadra from calle where calle1 like '$c1' and calle2 like '$c3' and vacio=0 UNION  
select idCuadra from calle where calle1 like '$c3' and calle2 like '$c1' and vacio=0 UNION
select idCuadra from calle where calle3 like '$c1' and vacio=0 )") ) {
	
    	while ($row = $resultset->fetch_array(MYSQLI_NUM)) {
    	echo json_encode($row);
		
    	
    	}

}
?>