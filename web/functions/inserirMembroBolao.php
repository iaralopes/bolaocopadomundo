<?php
    include ("../backend/ClassBolao.php");
    $class = new ClassBolao();
    
    $json = file_get_contents('php://input');
    $dados = json_decode($json, true);

    echo $class->inserirMembroBolao($dados);
?>