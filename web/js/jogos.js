$( document ).ready(function() {
    carregarJogos();
    carregarEstadios();
    carregarSelecoes();
    
    $("#horarioNovoJogo").mask('00:00 00/00/0000');
    
    $("#salvarNovoJogo").click(function() {
        if(validaCampos()) {
            if(validaSelecoes()){
                if(validaSelecoesRodada()){
                    cadastrarJogo();    
                }else{
                    sweetAlert("Oops...", "Combinação de seleções e rodada não aceitável.", "error");
                }
            }else{
                sweetAlert("Oops...", "Seleções não podem ser iguais.", "error");
            }
        }else {
            sweetAlert("Oops...", "Faltando um ou mais campos.", "error");
        }
    });
    
    $("#salvarResultadoJogo").click(function() {
        if(validaResultado()) {
            if($("#casaResultado").val() >= 0 && $("#visitanteResultado").val() >= 0){
                salvarResultado();
            }else{
                sweetAlert("Oops...", "Resultado não aceito.", "error");
            }
        }else {
            sweetAlert("Oops...", "Faltando um ou mais campos.", "error");
        }
    });
    
    
});

var json = [];
var jogoSelecionado;

function carregarJogos() {
    $("#jogos_table > tbody").html("");
    $.get(
        'http://www.esbolao.hospedagemdesites.ws/functions/buscarJogos.php'
    ).success(function(resp){
        json = $.parseJSON(resp);
        
        $(json).map(function(i) {
            
            if(json[i]['gols_casa'] == null){
                resultado = "<b>  __ X __  </b>";
            }else{
                resultado = "<b>  " + json[i]['gols_casa'] + " X " + json[i]['gols_visitante'] + "  </b>";
            }
            
            var myRow = "<tr><td>" + formatarData(json[i]['horario']) + "</td><td>" + json[i]['selecao_casa'] + resultado + json[i]['selecao_visitante']
            + "</td><td>" + carregarRodada(json[i]['num_rodada']) + "</td><td>" + json[i]['estadio'] + "(" + json[i]['local'] + ")"
            + "</td><td><a href=javascript:void(0); onclick=lancarResultadoJogo(" + i + ");><i class=\"fa fa-check\" aria-hidden=\"true\"></i></a>"
            + "</td><td><a href=javascript:void(0); onclick=excluirJogo("+ json[i]['id'] +");><i class=\"fa fa-trash\" aria-hidden=\"true\"></i></a></td></tr>";
            $('#jogos_table tbody').append(myRow);
        });
    });
}

function cadastrarJogo(){
    
    hora = $("#horarioNovoJogo").val().split(" ")[0];
    dia = $("#horarioNovoJogo").val().split(" ")[1];
    horario = dia.split("/")[2] + "-"+ dia.split("/")[1] + "-" + dia.split("/")[0] + " " + hora + ":00";
    
    var data = {
                "horario": horario,
                "id_selecao_casa": $("#selecaoCasaNovoJogo").val(),
                "id_selecao_visitante": $("#selecaoVisitanteNovoJogo").val(),
                "rodada": $("#rodadaNovoJogo").val(),
                "id_estadio": $("#estadioNovoJogo").val()
                };

    jQuery.ajax({
            url: "functions/cadastrarJogo.php",
            type: "POST",
            data: data,
            success: function(resp){
            if(resp){
                swal({
                title: "Jogo cadastrado com sucesso!",
                type: "success",
                showCancelButton: false,
                closeOnConfirm: true
                },function(){
                    $("#novoJogoModal").modal('hide');
                    limparCampos();
                    carregarJogos();
                });
            }else{
                swal("Ocorreu algum erro.", "", "error");
            }
        }
    }).error(function() {
        swal("Ocorreu algum erro.", "", "error");
    });
}

function lancarResultadoJogo(i){
    limparCampos();
    jogoSelecionado = "";
    jogoSelecionado = json[i];
    carregarInfoJogo(i);
    $("#resultadoModal").modal('show');
}

function carregarInfoJogo(i){
    horario = formatarData(jogoSelecionado['horario']);
    $("#horarioResultado").html("Horário: " + horario);
    $("#estadioResultado").html("Local: " + jogoSelecionado['estadio'] + "(" + jogoSelecionado['local'] + ")");
    $("#nomeCasaResultado").html(jogoSelecionado['selecao_casa']);
    $("#nomeVisitanteResultado").html(jogoSelecionado['selecao_visitante']);
    if(jogoSelecionado['gols_casa'] != null){
        $("#casaResultado").val(jogoSelecionado['gols_casa']);
        $("#visitanteResultado").val(jogoSelecionado['gols_visitante']);
    }
}

function salvarResultado(){
    
    var data = {
                "id": jogoSelecionado['id'],
                "gols_casa": $("#casaResultado").val(),
                "gols_visitante": $("#visitanteResultado").val()
                };

    jQuery.ajax({
            url: "http://www.esbolao.hospedagemdesites.ws/functions/salvarResultado.php",
            type: "POST",
            data: data,
            success: function(resp){
            if(resp){
                swal({
                title: "Resultado cadastrado com sucesso!",
                type: "success",
                showCancelButton: false,
                closeOnConfirm: true
                },function(){
                    $("#resultadoModal").modal('hide');
                    limparCampos();
                    carregarJogos();
                });
            }else{
                swal("Ocorreu algum erro.", "", "error");
            }
        }
    }).error(function() {
        swal("Ocorreu algum erro.", "", "error");
    });
}

function excluirJogo(id) {
    swal({
        title: "Você tem certeza que deseja apagar esse jogo?",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Sim, Apagar!",
        closeOnConfirm: false
    },
    function(){
        $.ajax({
            type: 'POST',
            url: 'http://www.esbolao.hospedagemdesites.ws/functions/excluirJogo.php?id=' + id, 
            success: function(resp){
                if(resp){
                    swal({
                        title: "Apagado!",
                        text: "Jogo foi apagado.",
                        type: "success",
                        showCancelButton: false,
                        closeOnConfirm: true
                        },function(){
                            carregarJogos();
                    });
                }else{
                    swal({
                        title: "Erro!",
                        text: "Erro ao apagar jogo.",
                        type: "warning",
                        showCancelButton: false,
                        closeOnConfirm: true
                        },function(){
                            window.location.replace("administracao.php");
                    });
                }
            } 
        });
    });
}

function carregarSelecoes() {
    $.get(
        'functions/buscarSelecoes.php'   
    ).success(function(resp){
        json = $.parseJSON(resp);
        var options = [];
        $(json).map(function(i) {
            options.push('<option value="' + json[i]["id"] + '"' + '>' + json[i]["nome"] + " (Grupo: " + json[i]["grupo"] + ")" + '</option>');
        })
        $('#selecaoVisitanteNovoJogo').append(options.join(''));
        $('#selecaoCasaNovoJogo').append(options.join(''));
    });
}

function carregarEstadios() {
    $.get(
        'functions/buscarEstadios.php'   
    ).success(function(resp){
        json = $.parseJSON(resp);
        var options = [];
        $(json).map(function(i) {
            options.push('<option value="' + json[i]["id"] + '"' + '>' + json[i]["nome"] + " (" + json[i]["local"] + ")" + '</option>');
        })
        $('#estadioNovoJogo').append(options.join(''));
    });
}

function validaCampos(){
    var validado = true;
    if($("#horarioNovoJogo").val() == "" || !validarHorario($("#horarioNovoJogo").val())){
        $("#horarioNovoJogo").addClass("is-invalid");
        validado = false;
    }else{
        $("#horarioNovoJogo").removeClass("is-invalid");
    }
    if($("#selecaoCasaNovoJogo").val() == "0"){
        $("#selecaoCasaNovoJogo").addClass("is-invalid");
        validado = false;
    }else{
        $("#selecaoCasaNovoJogo").removeClass("is-invalid");
    }
    if($("#selecaoVisitanteNovoJogo").val() == "0"){
        $("#selecaoVisitanteNovoJogo").addClass("is-invalid");
        validado = false;
    }else{
        $("#selecaoVisitanteNovoJogo").removeClass("is-invalid");
    }
    if($("#rodadaNovoJogo").val() == "0"){
        $("#rodadaNovoJogo").addClass("is-invalid");
        validado = false;
    }else{
        $("#rodadaNovoJogo").removeClass("is-invalid");
    }
    if($("#estadioNovoJogo").val() == "0"){
        $("#estadioNovoJogo").addClass("is-invalid");
        validado = false;
    }else{
        $("#estadioNovoJogo").removeClass("is-invalid");
    }
    return validado;
}

function validaSelecoes(){
    if($("#selecaoCasaNovoJogo").val() == $("#selecaoVisitanteNovoJogo").val()){
        $("#selecaoCasaNovoJogo").addClass("is-invalid");
        $("#selecaoVisitanteNovoJogo").addClass("is-invalid");
        return false;
    }else{
        $("#selecaoCasaNovoJogo").removeClass("is-invalid");
        $("#selecaoVisitanteNovoJogo").removeClass("is-invalid");
        return true;
    }
}

function validaSelecoesRodada(){
    casa = $("#selecaoCasaNovoJogo option:selected").text();
    visitante = $("#selecaoVisitanteNovoJogo option:selected").text();
    if((($("#rodadaNovoJogo").val() == "1" || $("#rodadaNovoJogo").val() == "2" || $("#rodadaNovoJogo").val() == "3")
       && casa.split(" ")[casa.split(" ").length-1] == visitante.split(" ")[visitante.split(" ").length-1])
       || ($("#rodadaNovoJogo").val() == "O" || $("#rodadaNovoJogo").val() == "Q" || $("#rodadaNovoJogo").val() == "S"
             || $("#rodadaNovoJogo").val() == "D" || $("#rodadaNovoJogo").val() == "F")){
        $("#selecaoCasaNovoJogo").removeClass("is-invalid");
        $("#selecaoVisitanteNovoJogo").removeClass("is-invalid");
        $("#rodadaNovoJogo").removeClass("is-invalid");
        return true;
    }
    $("#selecaoCasaNovoJogo").addClass("is-invalid");
    $("#selecaoVisitanteNovoJogo").addClass("is-invalid");
    $("#rodadaNovoJogo").addClass("is-invalid");
    return false;
}

function validaResultado(){
    var validado = true;
    if($("#casaResultado").val() == ""){
        $("#casaResultado").addClass("is-invalid");
        validado = false;
    }else{
        $("#casaResultado").removeClass("is-invalid");
    }
    if($("#visitanteResultado").val() == ""){
        $("#visitanteResultado").addClass("is-invalid");
        validado = false;
    }else{
        $("#visitanteResultado").removeClass("is-invalid");
    }
    return validado;
}

function validarHorario(horario){
    if(horario.includes(":") && horario.includes("/")){
        hora = horario.split(" ")[0].split(":")[0];
        minuto = horario.split(" ")[0].split(":")[1];
        dia = horario.split(" ")[1].split("/")[0];
        mes = horario.split(" ")[1].split("/")[1];
        ano = horario.split(" ")[1].split("/")[2];
        if(hora >= 0 && hora < 24 && minuto >= 0 && minuto < 60 && ano > 2017 && mes > 0 && mes < 13 && dia > 0 && dia <= diasMes(mes, ano)){
            return true;
        }
    }
    return false;
}

function limparCampos(){
    $("#casaResultado").val("");
    $("#visitanteResultado").val("");
    $("#horarioNovoJogo").val("");
    $("#selecaoCasaNovoJogo").val("");
    $("#selecaoVisitanteNovoJogo").val("");
    $("#rodadaNovoJogo").val("");
    $("#estadioNovoJogo").val("");
}

function carregarRodada(r){
    switch (r) {
        case '1':
            return "1ª Rodada";
        case '2':
            return "1ª Rodada";
        case '3':
            return "1ª Rodada";
        case 'O':
            return "Oitavas de Final";
        case 'Q':
            return "Quartas de Final";
        case 'S':
            return "Semifinal";
        case 'D':
            return "Disputa 3º Lugar";
        case 'F':
            return "Final";
    }
}

function diasMes(mes, ano){
    if(mes == 2){
        return (ano % 4 == 0 && y % 100) || ano % 400 == 0 ? 29 : 28;
    }else if(mes == 4 || mes == 6 || mes == 9 || mes == 11){
        return 30;
    }else{
        return 31;
    }
}

function formatarData(horario){
    data = horario.split(" ")[0];
    hora = horario.split(" ")[1];
    data = data.split("-")[2] + "/" + data.split("-")[1];
    hora = hora.split(":")[0] + ":" + hora.split(":")[1];
    
    return hora + " " + data;
}