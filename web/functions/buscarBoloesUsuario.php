<?php
    include ("../backend/ClassBolao.php");
    $class = new ClassBolao();
	
    $id = $_GET ['id'];

    $resultado = $class->buscarBoloesUsuario($id);
    echo json_encode($resultado);
?>