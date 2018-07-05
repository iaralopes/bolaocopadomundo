<?php
    include ("../backend/ClassConfiguracao.php");
    $class = new ClassConfiguracao();
	
    $resultado = $class->buscarConfiguracoes();
    echo json_encode($resultado);
?>