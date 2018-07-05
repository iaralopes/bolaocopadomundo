<?php
	class ClassBolao {
		public function buscarBoloesUsuario($id){
			include_once ("ClassDB.php");
			$db = new ClassDB();
			
			$conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
			if ($conn->connect_error){
				return "Erro";
			}
			
			$sql = "SELECT DISTINCT b.id, b.nome, b.num_ganhadores, b.id_criador from tbl_bolao b JOIN tbl_membros_bolao mb ON b.id = mb.id_bolao WHERE mb.id_pessoa = ".$id." OR b.id_criador = ".$id." ORDER BY b.nome";
			$result = $conn->query($sql);
			if ($result->num_rows > 0) {
				while($row = $result->fetch_assoc()) {
					$id = $row["id"];
					$nome = utf8_encode($row["nome"]);
					$num_ganhadores = $row["num_ganhadores"];
					$id_criador = $row["id_criador"];
					
					$resultado[] = array(
						"id" => $id,
						"nome" => $nome,
						"num_ganhadores" => $num_ganhadores,
						"id_criador" => $id_criador
					);
				}
				return $resultado;
			}
			else {
				return 0;
			}
		}

		public function inserirBolao($dados){
			include_once ("ClassDB.php");
			$db = new ClassDB();
			
			$conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
			$conn->set_charset("utf8");
			if ($conn->connect_error){
				return "Erro";
			}	
			$sql = "INSERT INTO tbl_bolao VALUES (null, '".$dados['nome']."', ".$dados['num_ganhadores'].", ".$dados['id_criador'].");";
			$result = $conn->query($sql);
			if($result){
				$sql2 = "INSERT INTO tbl_membros_bolao VALUES(null, (SELECT MAX(id) FROM tbl_bolao), ".$dados['id_criador'].");";
				$result = $conn->query($sql2);
				if($result){
					return true;
				}
			}
			return false;
		}
		
		public function membrosBolao($id){
			include_once ("ClassDB.php");
			$db = new ClassDB();
			
			$conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
			if ($conn->connect_error){
				return "Erro";
			}
			
			$sql = "SELECT p.id, p.nome, p.email, p.facebook FROM tbl_pessoas p JOIN tbl_membros_bolao mb ON p.id = mb.id_pessoa WHERE mb.id_bolao = ".$id." ORDER BY p.nome; ";
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
		
		public function inserirMembroBolao($dados){
			include_once ("ClassDB.php");
			$db = new ClassDB();
			
			$conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
			if ($conn->connect_error){
				return "Erro";
			}
			
			$sql = "INSERT INTO tbl_membros_bolao VALUES(null, ".$dados['id_bolao'].", (SELECT MAX(id) FROM tbl_pessoas WHERE email = '".$dados['email']."') );";
			
			$result = $conn->query($sql);
			if($result){
				return true;
			}
			return false;
		}
		
		public function removerMembroBolao($dados){
			include_once ("ClassDB.php");
			$db = new ClassDB();
			
			$conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
			if ($conn->connect_error){
				return "Erro";
			}
			
			$sql = "DELETE FROM tbl_membros_bolao WHERE id_bolao = ".$dados['id_bolao']." AND id_pessoa = ".$dados['id_pessoa']." ;";
			
			$result = $conn->query($sql);
			if($result){
				return true;
			}
			return false;
		}
	}

?>