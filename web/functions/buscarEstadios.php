<?php
    include ("../backend/ClassEstadio.php");
    $class = new ClassEstadio();
	
    $resultado = $class->buscarEstadios();
    echo json_encode($resultado);
?>