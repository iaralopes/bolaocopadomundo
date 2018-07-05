<?php
  include ("seguranca.php");
	protegePagina ();
  include ("menu.php");
?>
<html>
<head>
<meta charset="UTF-8">

</head>
<body class="bg-light" onBeforeUnload="loadOut();">

	<div class="container">
		<div class="row">
			<div class="col-md-12 p-3">
				<h4 style="display: inline-block;"><b>Jogos</b></h4>
        <button type="button" class="btn btn-info btn" data-toggle="modal"
                data-target="#novoJogoModal" style="display: inline-block; float: right; display: block;">
            Novo Jogo
        </button>
				<table class="table table-hover table-striped table-bordered" id="jogos_table">
					<thead class="thead-inverse">
						<tr>
							<th scope="col">Horário</th>
							<th scope="col">Jogo</th>
							<th scope="col">Rodada</th>
							<th scope="col">Local</th>
							<th scope="col"></th>
							<th scope="col"></th>
						</tr>
					</thead>
					<tbody>
						<!-- repetição javascrit -->
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>

	<?php
		include ("rodape.php");
	?>
	<script type="text/javascript" src="js/jogos.js"></script>

</html>
<!-- MODAL CADASTRO DE JOGO -->
<div id="novoJogoModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Cadastro de Jogo</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="horarioNovoJogo">Horário:</label>
                  <input type="text" class="form-control" id="horarioNovoJogo">
                </div>
                <div class="col-md-6 mb-3">
                  <label for="rodadaNovoJogo">Rodada:</label>
                  <select class="form-control" id="rodadaNovoJogo">
                    <option value="0"></option>
                    <option value="1">1ª Rodada - Fase de Grupos</option>
                    <option value="2">2ª Rodada - Fase de Grupos</option>
                    <option value="3">3ª Rodada - Fase de Grupos</option>
                    <option value="O">Oitavas de Final</option>
                    <option value="Q">Quartas de Final</option>
                    <option value="S">Semifinal</option>
                    <option value="D">Disputa do 3º Lugar</option>
                    <option value="F">Final</option>
                  </select>
                </div>
              </div>
              <div class="row">
                <div class="form-group col-md-6 mb-3">
                   <label for="selecaoCasaNovoJogo">Seleção Casa:</label>
                  <select class="form-control" id="selecaoCasaNovoJogo">
                    <option value="0"></option>    
                  </select>
                </div>
                <div class="form-group col-md-6 mb-3">
                   <label for="selecaoVisitanteNovoJogo">Seleção Visitante:</label>
                  <select class="form-control" id="selecaoVisitanteNovoJogo">
                    <option value="0"></option>    
                  </select>
                </div>
              </div>
              <div class="form-group">
                <label for="estadioNovoJogo">Estádio:</label>
                <select class="form-control" id="estadioNovoJogo">
                  <option value="0"></option>    
                </select>
               </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="salvarNovoJogo">Salvar</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>

    </div>
</div>
<!-- MODAL LANÇAR RESULTADO -->
<div id="resultadoModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Lançar Resultado do Jogo</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
              <div class="row">
                <div class="col-md-6 mb-3">
                    <label id="horarioResultado">Horário:</label>
                </div>
                <div class="col-md-6 mb-3">
                    <label id="estadioResultado">Estádio:</label>
                </div>
              </div>
              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="casaResultado" id="nomeCasaResultado">Seleção Casa:</label>
                  <input type="number" class="form-control" id="casaResultado">
                </div>
                <div class="col-md-6 mb-3">
                  <label for="visitanteResultado" id="nomeVisitanteResultado">Seleção Casa:</label>
                  <input type="number" class="form-control" id="visitanteResultado">
                </div>
              </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="salvarResultadoJogo">Salvar</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>

    </div>
</div>