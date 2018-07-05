$( document ).ready(function() {
    carregarConfiguracao();

    $( "#salvar-configuracao" ).click(function() {
        if(validaCampos()) {
            if(validaPontos()){
                salvarConfiguracao();
            }else {
                sweetAlert("Oops...", "Combinação de pontuação não aceita.(A pontuação de acerto em cheio deve ser a maior, seguida do acerto de diferença de resultado e assim em diante)", "error");
            }
        }else {
            sweetAlert("Oops...", "Faltando um ou mais campos.", "error");
        }
    });
    
    $("#horas-palpites").change(function () { if($("#horas-palpites").val() > 23){$("#horas-palpites").val(23);} if($("#horas-palpites").val() < 1){$("#horas-palpites").val(1);} });
    $("#acerto-cheio").change(function () { if($("#acerto-cheio").val() > 15){$("#acerto-cheio").val(15);} if($("#acerto-cheio").val() < 0){$("#acerto-cheio").val(0);} });
    $("#acerto-diferenca").change(function () { if($("#acerto-diferenca").val() > 15){$("#acerto-diferenca").val(15);} if($("#acerto-diferenca").val() < 0){$("#acerto-diferenca").val(0);} });
    $("#acerto-ganhador").change(function () { if($("#acerto-ganhador").val() > 15){$("#acerto-ganhador").val(15);} if($("#acerto-ganhador").val() < 0){$("#acerto-ganhador").val(0);} });
    $("#erro").change(function () { if($("#erro").val() > 15){$("#erro").val(15);} if($("#erro").val() < 0){$("#erro").val(0);} });

});

var json = [];

function carregarConfiguracao() {
    $.get(
        "http://www.esbolao.hospedagemdesites.ws/functions/buscarConfiguracoes.php"
    ).success(function(resp){
        json = $.parseJSON(resp);
        
        $(json).map(function(i) {
            $("#" + json[i]['descricao']).val(json[i]['valor']);
        });
    }).error(function() {
        swal("Ocorreu algum erro.", "", "error");
    });
}

function salvarConfiguracao() {
    var data = {
                "horas-palpites": $("#horas-palpites").val(),
                "acerto-cheio": $("#acerto-cheio").val(),
                "acerto-ganhador": $("#acerto-ganhador").val(),
                "acerto-diferenca": $("#acerto-diferenca").val(),
                "erro": $("#erro").val()
                };

    jQuery.ajax({
            url: "http://www.esbolao.hospedagemdesites.ws/functions/salvarConfiguracoes.php",
            type: "POST",
            data: data,
            success: function(resp){
            if(resp){
                swal("Salvo com sucesso.", "", "success");
            }else{
                swal("Ocorreu algum erro.", "", "error");
            }
        }
    }).error(function() {
        swal("Ocorreu algum erro.", "", "error");
    });
}

function validaCampos() {
    var validado = true;
    if($("#horas-palpites").val() == ""){
        $("#horas-palpites").addClass("is-invalid");
        validado = false;
    }else{
        $("#horas-palpites").removeClass("is-invalid");
    }
    if($("#acerto-cheio").val() == ""){
        $("#acerto-cheio").addClass("is-invalid");
        validado = false;
    }else{
        $("#acerto-cheio").removeClass("is-invalid");
    }
    if($("#acerto-ganhador").val() == ""){
        $("#acerto-ganhador").addClass("is-invalid");
        validado = false;
    }else{
        $("#acerto-ganhador").removeClass("is-invalid");
    }
    if($("#acerto-diferenca").val() == ""){
        $("#acerto-diferenca").addClass("is-invalid");
        validado = false;
    }else{
        $("#acerto-diferenca").removeClass("is-invalid");
    }
    if($("#erro").val() == ""){
        $("#erro").addClass("is-invalid");
        validado = false;
    }else{
        $("#erro").removeClass("is-invalid");
    }
    return validado;
}

function validaPontos() {
    if($("#acerto-cheio").val() > $("#acerto-diferenca").val() && $("#acerto-diferenca").val() > $("#acerto-ganhador").val() && $("#acerto-ganhador").val() > $("#erro").val()){
        return true;
    }
    return false;
}