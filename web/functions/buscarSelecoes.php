<?php
    include ("../backend/ClassSelecao.php");
    $class = new ClassSelecao();
	
    $resultado = $class->buscarSelecoes();
    echo json_encode($resultado);
?>