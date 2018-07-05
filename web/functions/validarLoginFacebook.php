<?php
    include ("../backend/ClassPessoa.php");
    $class = new ClassPessoa();
	
    $json = file_get_contents('php://input');
    $dados = json_decode($json, true);

    $return = $class->validarLoginFacebook($dados);

    echo json_encode($return);

?>