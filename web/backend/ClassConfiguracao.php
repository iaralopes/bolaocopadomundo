<?php

class ClassConfiguracao {
    public function buscarConfiguracoes(){
        include_once ("ClassDB.php");
        $db = new ClassDB();
		
        $conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
        if ($conn->connect_error){
            return "Erro";
        }
        
        $sql = "SELECT id, descricao, valor FROM tbl_configuracao;";
        $result = $conn->query($sql);
        if ($result->num_rows > 0) {
            while($row = $result->fetch_assoc()) {
                $id = $row["id"];
                $descricao = utf8_encode($row["descricao"]);
                $valor = $row["valor"];
                
                $resultado[] = array(
                    "id" => $id,
                    "descricao" => $descricao,
                    "valor" => $valor
                );
            }
            return $resultado;
        }
        else {
            return 0;
        }
    }
    
    public function salvarConfiguracoes($configuracoes){
        include_once ("ClassDB.php");
        $db = new ClassDB();
		
        $conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
        if ($conn->connect_error){
            return "Erro";
        }
        
        $sql = "UPDATE tbl_configuracao SET valor = '".$configuracoes['horas-palpites']."' WHERE descricao = 'horas-palpites' ";
        $sql2 = "UPDATE tbl_configuracao SET valor = '".$configuracoes['acerto-cheio']."' WHERE descricao = 'acerto-cheio' ";
        $sql3 = "UPDATE tbl_configuracao SET valor = '".$configuracoes['acerto-ganhador']."' WHERE descricao = 'acerto-ganhador' ";
        $sql4 = "UPDATE tbl_configuracao SET valor = '".$configuracoes['acerto-diferenca']."' WHERE descricao = 'acerto-diferenca' ";
        $sql5 = "UPDATE tbl_configuracao SET valor = '".$configuracoes['erro']."' WHERE descricao = 'erro' ";

        if($conn->query($sql) && $conn->query($sql2) && $conn->query($sql3) && $conn->query($sql4) && $conn->query($sql5)){
            return true;
        }
        return false;
    }
    
}

?>