<?php 

$conn = new mysqli("localhost", "root", "", "loginphp"); /* Conexion a la base de datos */


function connect(){ /* Funcion para verificar la coneccion a la base de datos */
    if ($conn->connect_errno) { 
        return false;
    }else {
        return true;
    }
}

?>