<?php

class ClassJogo {
    public function buscarJogos(){
        include_once ("ClassDB.php");
        $db = new ClassDB();
		
        $conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
        if ($conn->connect_error){
            return "Erro";
        }
        
        $sql = "SELECT j.id, j.horario, sc.nome as selecao_casa, j.gols_casa, sv.nome as selecao_visitante, j.gols_visitante,
                        j.num_rodada, e.nome as estadio, e.local FROM tbl_jogos j 
							JOIN tbl_selecoes sc ON j.id_selecao_casa = sc.id 
							JOIN tbl_selecoes sv ON j.id_selecao_visitante = sv.id
							JOIN tbl_estadios e ON j.id_estadios = e.id
                    WHERE j.horario > DATE_ADD(NOW(), INTERVAL -2 DAY)
                    ORDER BY j.horario;";
        $result = $conn->query($sql);
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $id = $row["id"];
                $horario = $row["horario"];
                $selecao_casa = utf8_encode($row["selecao_casa"]);
                $gols_casa = $row["gols_casa"];
                $selecao_visitante = utf8_encode($row["selecao_visitante"]);
                $gols_visitante = $row["gols_visitante"];
                $num_rodada = $row["num_rodada"];
                $estadio = utf8_encode($row["estadio"]);
                $local = utf8_encode($row["local"]);
                
                $resultado[] = array(
                    "id" => $id,
                    "horario" => $horario,
                    "selecao_casa" => $selecao_casa,
                    "gols_casa" => $gols_casa,
                    "selecao_visitante" => $selecao_visitante,
                    "gols_visitante" => $gols_visitante,
                    "num_rodada" => $num_rodada,
                    "estadio" => $estadio,
                    "local" => $local
                );
            }
            return $resultado;
        }
        else {
            return 0;
        }
    }
    
    public function cadastrarJogo($jogo){
        include_once ("ClassDB.php");
        $db = new ClassDB();
        
        $conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
        if ($conn->connect_error) {
            return "Erro";
        }
        
        $sql = "INSERT INTO tbl_jogos VALUES(null, '".$jogo['horario']."', ".$jogo['id_selecao_casa'].", null,".$jogo['id_selecao_visitante'].", null, '".$jogo['rodada']."', ".$jogo['id_estadio'].");";
        
        $result = $conn->query($sql);
        if($result){
            return true;
        }else{
            return false;
        }
    }
    
    public function salvarResultado($jogo){
        include_once ("ClassDB.php");
        $db = new ClassDB();
        
        $conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
        if ($conn->connect_error) {
            return "Erro";
        }
        
        $sql = "UPDATE tbl_jogos SET gols_casa = ".$jogo['gols_casa'].", gols_visitante = ".$jogo['gols_visitante']." WHERE id = ".$jogo['id'].";";
        $result = $conn->query($sql);
        if($result){
            return true;
        }else{
            return false;
        }
    }

    public function excluirJogo($id) {
        include_once ("ClassDB.php");
        $db = new ClassDB();
        
        $conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
        if ($conn->connect_error) {
            return "Erro";
        }
        
        $sql = "DELETE FROM tbl_jogos WHERE id = ".$id['id'].";";
        $result = $conn->query($sql);
        if($result){
            return true;
        }else{
            return false;
        }
    }
    
}

?>