<?php
    include ("../backend/ClassPessoa.php");
    $class = new ClassPessoa();
	
    $json = file_get_contents('php://input');
    $dados = json_decode($json, true);

    if($class->inserirPessoas($dados)){
        echo true;
    }else{
        echo false;
    }
?>