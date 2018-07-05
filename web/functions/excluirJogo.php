<?php
    include ("../backend/ClassJogo.php");
    $class = new ClassJogo();
	
    $id = $_REQUEST;
    
    if($class->excluirJogo($id)){
        $result = true;
        echo $result;
    }else{
        $result = false;
        echo $result;
    }
?>