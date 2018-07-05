<?php
	class ClassAposta {
		public function buscarApostasConcluidas($id_usuario, $id_bolao){
			include_once ("ClassDB.php");
			$db = new ClassDB();
			
			$conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
			if ($conn->connect_error){
				return "Erro";
			}
			
			$sql = "SELECT ap.id AS id_aposta, ap.gols_casa AS aposta_gols_casa, ap.gols_visitante AS aposta_gols_visitante,
						jo.id AS id_jogo, jo.horario AS jogo_horario, jo.num_rodada AS jogo_num_rodada,
						sc.id AS id_selecao_casa, sc.nome AS selecao_casa_nome, sc.grupo AS selecao_casa_grupo, jo.gols_casa AS jogo_gols_casa, 
						sv.id AS id_selecao_visitante, sv.nome AS selecao_visitante_nome, sv.grupo AS selecao_visitante_grupo, jo.gols_visitante AS jogo_gols_visitante,
						es.id AS id_estadio, es.nome AS estadio_nome, es.local AS estadio_local 
					FROM tbl_apostas ap 
						JOIN tbl_jogos jo ON ap.id_jogo = jo.id
						JOIN tbl_selecoes sc ON jo.id_selecao_casa = sc.id
						JOIN tbl_selecoes sv ON jo.id_selecao_visitante = sv.id
						JOIN tbl_estadios es ON jo.id_estadios = es.id
						JOIN tbl_membros_bolao mb ON ap.id_membro = mb.id_membro
					WHERE mb.id_pessoa = ".$id_usuario."
						AND mb.id_bolao = ".$id_bolao."
						AND jo.horario < horarioPalpites()
					ORDER BY jo.horario DESC;";
			
			$result = $conn->query($sql);
			if ($result->num_rows > 0) {
				while($row = $result->fetch_assoc()) {
					$id_aposta = $row["id_aposta"];
					$aposta_gols_casa = $row["aposta_gols_casa"];
					$aposta_gols_visitante = $row["aposta_gols_visitante"];
					
					$selecao_casa = array(
						"id" => $row["id_selecao_casa"],
						"nome" => utf8_encode($row["selecao_casa_nome"]),
						"grupo" => $row["selecao_casa_grupo"]
					);
					
					$selecao_visitante = array(
						"id" => $row["id_selecao_visitante"],
						"nome" => utf8_encode($row["selecao_visitante_nome"]),
						"grupo" => $row["selecao_visitante_grupo"]
					);
					$estadio = array(
						"id" => $row["id_estadio"],
						"nome" => utf8_encode($row["estadio_nome"]),
						"local" => utf8_encode($row["estadio_local"])
					);
					
					$jogo = array(
						"id" => $row["id_jogo"],
						"horario" => $row["jogo_horario"],
						"num_rodada" => $row["jogo_num_rodada"],
						"selecao_casa" => $selecao_casa,
						"gols_casa" => $row["jogo_gols_casa"],
						"selecao_visitante" => $selecao_visitante,
						"gols_visitante" => $row["jogo_gols_visitante"],
						"estadio" => $estadio
					);
					
					$resultado[] = array(
						"id" => $id_aposta,
						"gols_casa" => $aposta_gols_casa,
						"gols_visitante" => $aposta_gols_visitante,
						"jogo" => $jogo
					);
				}
				return $resultado;
			}
			else {
				return 0;
			}
		}
		
		public function buscarApostasPendentes($id_usuario, $id_bolao){
			include_once ("ClassDB.php");
			$db = new ClassDB();
			
			$conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
			if ($conn->connect_error){
				return "Erro";
			}
			
			$sql = "SELECT ap.id AS id_aposta, ap.gols_casa AS aposta_gols_casa, ap.gols_visitante AS aposta_gols_visitante, 
						jo.id AS id_jogo, jo.horario AS jogo_horario, jo.num_rodada AS jogo_num_rodada,
						sc.id AS id_selecao_casa, sc.nome AS selecao_casa_nome, sc.grupo AS selecao_casa_grupo, jo.gols_casa AS jogo_gols_casa, 
						sv.id AS id_selecao_visitante, sv.nome AS selecao_visitante_nome, sv.grupo AS selecao_visitante_grupo, jo.gols_visitante AS jogo_gols_visitante,
						es.id AS id_estadio, es.nome AS estadio_nome, es.local AS estadio_local 
					FROM tbl_apostas ap 
						JOIN tbl_jogos jo ON ap.id_jogo = jo.id
						JOIN tbl_selecoes sc ON jo.id_selecao_casa = sc.id
						JOIN tbl_selecoes sv ON jo.id_selecao_visitante = sv.id
						JOIN tbl_estadios es ON jo.id_estadios = es.id
						JOIN tbl_membros_bolao mb ON ap.id_membro = mb.id_membro
					WHERE mb.id_pessoa = ".$id_usuario."
						AND mb.id_bolao = ".$id_bolao."
						AND jo.gols_casa IS NULL
						AND jo.gols_visitante IS NULL
						AND jo.horario > horarioPalpites()
					ORDER BY jo.horario;";
			
			$result = $conn->query($sql);
			if ($result->num_rows > 0) {
				while($row = $result->fetch_assoc()) {
					$id_aposta = $row["id_aposta"];
					$aposta_gols_casa = $row["aposta_gols_casa"];
					$aposta_gols_visitante = $row["aposta_gols_visitante"];
					
					$selecao_casa = array(
						"id" => $row["id_selecao_casa"],
						"nome" => utf8_encode($row["selecao_casa_nome"]),
						"grupo" => $row["selecao_casa_grupo"]
					);
					
					$selecao_visitante = array(
						"id" => $row["id_selecao_visitante"],
						"nome" => utf8_encode($row["selecao_visitante_nome"]),
						"grupo" => $row["selecao_visitante_grupo"]
					);
					$estadio = array(
						"id" => $row["id_estadio"],
						"nome" => utf8_encode($row["estadio_nome"]),
						"local" => utf8_encode($row["estadio_local"])
					);
					
					$jogo = array(
						"id" => $row["id_jogo"],
						"horario" => $row["jogo_horario"],
						"num_rodada" => $row["jogo_num_rodada"],
						"selecao_casa" => $selecao_casa,
						"gols_casa" => $row["jogo_gols_casa"],
						"selecao_visitante" => $selecao_visitante,
						"gols_visitante" => $row["jogo_gols_visitante"],
						"estadio" => $estadio
					);
					
					$resultado[] = array(
						"id" => $id_aposta,
						"gols_casa" => $aposta_gols_casa,
						"gols_visitante" => $aposta_gols_visitante,
						"jogo" => $jogo
					);
				}
				return $resultado;
			}
			else {
				return 0;
			}
		}
		
		public function buscarRankBolao($id_bolao){
			include_once ("ClassDB.php");
			$db = new ClassDB();
			
			$conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
			if ($conn->connect_error){
				return "Erro";
			}
			
			$sql = "SELECT ap.id AS id_aposta, ap.gols_casa AS aposta_gols_casa, ap.gols_visitante AS aposta_gols_visitante, 
						 jo.gols_casa AS jogo_gols_casa, jo.gols_visitante AS jogo_gols_visitante,
						 pe.id AS id_pessoa, pe.nome AS nome_pessoa
					FROM tbl_apostas ap 
						JOIN tbl_jogos jo ON ap.id_jogo = jo.id
						JOIN tbl_membros_bolao mb ON ap.id_membro = mb.id_membro
						JOIN tbl_pessoas pe ON mb.id_pessoa = pe.id
					WHERE mb.id_bolao = ".$id_bolao."
						AND jo.gols_casa IS NOT NULL
						AND jo.gols_visitante IS NOT NULL
						AND jo.horario < horarioPalpites()
					ORDER BY jo.horario;";
			
			$result = $conn->query($sql);
			if ($result->num_rows > 0) {
				while($row = $result->fetch_assoc()) {
					$id_aposta = $row["id_aposta"];
					$aposta_gols_casa = $row["aposta_gols_casa"];
					$aposta_gols_visitante = $row["aposta_gols_visitante"];
					$jogo = array(
						"gols_casa" => $row["jogo_gols_casa"],
						"gols_visitante" => $row["jogo_gols_visitante"],
					);
					$membro = array(
						"id" => $row["id_pessoa"],
						"nome" => utf8_encode($row["nome_pessoa"]),
					);
					
					$resultado[] = array(
						"id" => $id_aposta,
						"gols_casa" => $aposta_gols_casa,
						"gols_visitante" => $aposta_gols_visitante,
						"jogo" => $jogo,
						"membro" => $membro
					);
				}
				return $resultado;
			}
			else {
				return 0;
			}
		}
		
		public function atualizarAposta($dados){
			include_once ("ClassDB.php");
			$db = new ClassDB();
			
			$conn = new mysqli($db->servername, $db->username, $db->password, $db->dbname);
			if ($conn->connect_error){
				return "Erro";
			}

			foreach ($dados as &$aposta) {
				$sql = "UPDATE tbl_apostas
							SET gols_casa = ".$aposta['gols_casa'].", gols_visitante = ".$aposta['gols_visitante']."
							WHERE id = ".$aposta['id'].";";
				$result = $conn->query($sql);
				if(!$result){
					return false;
				}
			}
			return true;
		}
		
    }
?>