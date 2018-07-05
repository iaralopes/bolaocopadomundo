<?php
    include ("../backend/ClassConfiguracao.php");
    $class = new ClassConfiguracao();
    
    $configuracoes = $_REQUEST;
 
    if($class->salvarConfiguracoes($configuracoes)){
        $result = true;
        echo $result;
    }else{
        $result = false;
        echo $result;
    }
    
?>