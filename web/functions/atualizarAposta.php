<?php
    include ("../backend/ClassAposta.php");
    $class = new ClassAposta();
	
    $json = file_get_contents('php://input');
    $dados = json_decode($json, true);

    echo $class->atualizarAposta($dados);
?>