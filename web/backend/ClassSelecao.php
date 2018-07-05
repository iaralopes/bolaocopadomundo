<?php

class ClassSelecao {
    public function buscarSelecoes(){
        include_once ("ClassDB.php");
        $db = new ClassDB();
		
        $conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
        if ($conn->connect_error){
            return "Erro";
        }
        
        $sql = "SELECT id, nome, grupo FROM tbl_selecoes ORDER BY grupo, nome";
        $result = $conn->query($sql);
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $id = $row["id"];
                $nome = utf8_encode($row["nome"]);
                $grupo = utf8_encode($row["grupo"]);
                
                $resultado[] = array(
                    "id" => $id,
                    "nome" => $nome,
                    "grupo" => $grupo
                );
            }
            return $resultado;
        }
        else {
            return 0;
        }
    }

}

?>