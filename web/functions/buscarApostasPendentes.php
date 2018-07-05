<?php
    include ("../backend/ClassAposta.php");
    $class = new ClassAposta();
	
    $id_pessoa = $_GET ['id_pessoa'];
    $id_bolao = $_GET ['id_bolao'];

    $resultado = $class->buscarApostasPendentes($id_pessoa, $id_bolao);
    echo json_encode($resultado);
?>