<?php
    class ClassAdministrador {
        public function validaUsuario($varLogin, $varPassword) {
            if($varLogin == "admin" && $varPassword == "admin") {
                $arrResult = array("logincode" => 1, "nome" => "Administrador", 
                                        "nome_exibicao" => "Administrador", "login" => "admin");
            }else if($this->authenticate($varLogin, $varPassword)){
                    $arrUsuarioInfo = $this->usuario($varLogin);	
                    $varUsuarioLogin = $arrUsuarioInfo[0]['login'][0];
                    $varUsuarioNome = $arrUsuarioInfo[0]['nome'][0];
                    $explodeUsuarioNome = explode(" ", $varUsuarioNome);
                    $varUsuarioNomeExibicao = $explodeUsuarioNome[0];
                    
                    $arrResult = array("logincode" => 1, "nome" => $varUsuarioNome, 
                                        "nome_exibicao" => $varUsuarioNomeExibicao, "login" => $varLogin, "permissions" => "adm");
                }else{
                    $arrResult = array("logincode" => 0, "nome" => "NULL", "nome_exibicao" => "NULL", "login" => "NULL");
                }
            return $arrResult;
        }
        
        public function usuario($varLogin) {
            include_once ("ClassDB.php");
            $db = new ClassDB();
            
            $conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
    
            if ($conn->connect_error) {
                return "Erro";
            }
            
            $sql = "SELECT * FROM tbl_administradores WHERE login='".$varLogin."'";
            
            $result = $conn->query($sql);
            if ($result->num_rows > 0) {
                while($row = $result->fetch_assoc()) {
                    $login = $row["login"];
                    $nome = utf8_encode($row["nome"]);
                    
                    $resultado[] = array(
                        "login" => $login,
                        "nome" => $nome
                    );
                }
                return $resultado;
            }
            else {
                return 0;
            }
        }
        
        public function authenticate($varLogin, $varPassword) {
            include_once ("ClassDB.php");
            $db = new ClassDB();
            
            $conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
    
            if ($conn->connect_error) {
                return "Erro";
            }
            $sql = "SELECT * FROM tbl_administradores WHERE login='".$varLogin."' AND senha='".md5($varPassword)."' ";
            $result = $conn->query($sql);
            if ($result->num_rows > 0) {
                return true;
            }
            else{
                return false;
            }
        }
    }
?>