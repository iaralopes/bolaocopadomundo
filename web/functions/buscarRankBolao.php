<?php
    include ("../backend/ClassAposta.php");
    include ("../backend/ClassConfiguracao.php");
    
    $classApostas = new ClassAposta();
    $classConfiguracao = new ClassConfiguracao();
	
    $id_bolao = $_GET ['id_bolao'];

    $apostas = $classApostas->buscarRankBolao($id_bolao);	
    $configuracoes = $classConfiguracao->buscarConfiguracoes();
    if($apostas != 0 && $configuracoes != 0){
        $resultado[] = array(
            "apostas" => $apostas,
            "configs" => $configuracoes
        );   
        echo json_encode($resultado);
    }else{
        echo json_encode(0);
    }
?>