<?php
    include ("../backend/ClassJogo.php");
    $class = new ClassJogo();
    
    $jogo = $_REQUEST;
 
    if($class->salvarResultado($jogo)){
        $result = true;
        echo $result;
    }else{
        $result = false;
        echo $result;
    }
    
?>