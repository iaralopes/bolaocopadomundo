<?php
    // Inclui o arquivo com o sistema de seguran�a
    require_once ("seguranca.php");
    
    // Verifica se um formulário foi enviado
    if ($_SERVER ['REQUEST_METHOD'] == 'POST') {
    
        if (isset($_POST['action']) && $_POST ['action'] == 'logout') {
            logout();
        } else if (isset($_POST['action'])  && $_POST ['action'] == 'timeout') {
            $tst = expulsaVerificandoTimeout ();
            echo json_encode($tst);
        } else {
            $usuario = (isset ( $_POST ['usuario'] )) ? $_POST ['usuario'] : '';
            $senha = (isset ( $_POST ['senha'] )) ? $_POST ['senha'] : '';
            
            // Utiliza uma função criada no seguranca.php pra validar os dados digitados
            $validacao = validaUsuario ( $usuario, $senha );
            if ($validacao) {
                header ( "Location: administracao.php" );
            }else{
                header ( "Location: login.php" );
            }
        }
    } else {
        $usrData = getUserData ();
        echo $usrData;
    }
?>