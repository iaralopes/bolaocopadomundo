<?php

class ClassEstadio {
    public function buscarEstadios(){
        include_once ("ClassDB.php");
        $db = new ClassDB();
		
        $conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
        if ($conn->connect_error){
            return "Erro";
        }
        
        $sql = "SELECT id, nome, local FROM tbl_estadios ORDER BY nome";
        $result = $conn->query($sql);
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $id = $row["id"];
                $nome = utf8_encode($row["nome"]);
                $local = utf8_encode($row["local"]);
                
                $resultado[] = array(
                    "id" => $id,
                    "nome" => $nome,
                    "local" => $local
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