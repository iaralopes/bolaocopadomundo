<?php
  include ("seguranca.php");
	protegePagina ();
  include ("menu.php");
?>
<!DOCTYPE html>
<html>
<head>
  <!-- Inserido pelo arquivo menu.php -->
</head>

<body class="bg-light">
  <div class="py-4">
    <div class="container">
      <div class="row">
        <div class="text-center col-md-12 mx-auto">
          <h2><b>Configurações Bolão</b></h2>
        </div>
      </div>
    </div>
  </div>
  <div class="container">
    <div class="row">
      <div class="col-md-12 order-md-1">
        <div class="mb-3">
          <label for="horas-palpites">Horas para lançar palpites antes do jogo:</label>
          <input type="number" class="form-control" id="horas-palpites" placeholder="Horas">
        </div>
        <hr class="mb-4">
        <h4><b>Pontuações</b></h4>
        <div class="mb-3">
          <label for="acerto-cheio">Acerto em cheio do resultado:</label>
          <input type="number" class="form-control" id="acerto-cheio" placeholder="Pontos">
        </div>
        <div class="mb-3">
          <label for="acerto-diferenca">Acerto diferença de gols:</label>
          <input type="number" class="form-control" id="acerto-diferenca" placeholder="Pontos">
        </div>
        <div class="mb-3">
          <label for="acerto-ganhador">Acerto ganhador ou empate:</label>
          <input type="number" class="form-control" id="acerto-ganhador" placeholder="Pontos">
        </div>
        <div class="mb-3">
          <label for="erro">Erro do resultado:</label>
          <input type="number" class="form-control" id="erro" placeholder="Pontos">
        </div>
        <hr class="mb-4">
        <button class="btn btn-primary btn-lg btn-block" id="salvar-configuracao">Salvar Configuração</button>
      </div>
    </div>
  </div>
</body>

  <?php
    include ("rodape.php");
  ?>
  <script type="text/javascript" src="js/configuracao.js"></script>
  
</html>