<?php
	class ClassPessoa {
		
		public function inserirPessoas($dados){
			include_once ("ClassDB.php");
			$db = new ClassDB();
			
			$conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
			$conn->set_charset("utf8");
			if ($conn->connect_error){
				return "Erro";
			}
			
			$sql = "INSERT INTO tbl_pessoas VALUES (null, '".$dados['nome']."', '".$dados['email']."', '".$dados['senha']."', ".$dados['facebook'].");";
			
			$result = $conn->query($sql);
			
			if($result){
				return true;
			}else{
				return false;
			}
		}
		
		public function validarLogin($dados){
			include_once ("ClassDB.php");
			$db = new ClassDB();
			
			$conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
			if ($conn->connect_error){
				return "Erro";
			}
			
			$sql = "SELECT id, nome, email, facebook FROM tbl_pessoas WHERE email = '".utf8_encode($dados['email'])."' AND senha = '".utf8_encode($dados['senha'])."' AND facebook = '".$dados['facebook']."' ;";
			$result = $conn->query($sql);
			
			if ($result->num_rows > 0){
				while($row = $result->fetch_assoc()) {
					$id = $row["id"];
					$nome = utf8_encode($row["nome"]);
					$email = utf8_encode($row["email"]);
					$facebook = $row["facebook"];
					
					$resultado[] = array(
						"id" => $id,
						"nome" => $nome,
						"email" => $email,
						"facebook" => $facebook
					);
				}
				return $resultado;
			}else{
				return 0;
			}
		}
		
		public function validarLoginFacebook($dados){
			include_once ("ClassDB.php");
			$db = new ClassDB();
			
			$conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
			$conn->set_charset("utf8");
			if ($conn->connect_error){
				return "Erro";
			}
			
			$sql = "SELECT id, nome, email, facebook FROM tbl_pessoas WHERE email = '".$dados['email']."' ;";
			$result = $conn->query($sql);
			
			if ($result->num_rows > 0){
				while($row = $result->fetch_assoc()) {
					$id = $row["id"];
					$nome = utf8_encode($row["nome"]);
					$email = utf8_encode($row["email"]);
					$facebook = $row["facebook"];
					
					$resultado[] = array(
						"id" => $id,
						"nome" => $nome,
						"email" => $email,
						"facebook" => $facebook
					);
				}
				return $resultado;
			}else{
				$sql2 = "INSERT INTO tbl_pessoas VALUES (null, '".$dados['nome']."', '".$dados['email']."', null, ".$dados['facebook'].");";
				
				$result2 = $conn->query($sql2);
				if($result2){
					return $this->validarLoginFacebook($dados);
				}
				return 0;
			}
		}
		
	}
?>