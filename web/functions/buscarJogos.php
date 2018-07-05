<?php
    include ("../backend/ClassJogo.php");
    $class = new ClassJogo();
	
    $resultado = $class->buscarJogos();
    echo json_encode($resultado);
?>