<?php 
require_once 'connection.php';
session_start();
$email=$_POST['email'];
$pass=$_POST['pass'];

try {
    if(connect()){ /* Valido la conexion para poder hacer la consulta a la base de datos*/
        $query=$conn->prepare("SELECT * FROM users WHERE email=? AND pass=?"); /* Esta es la consulta */
        $query->bind_param('ss',$email,$pass); /* Sentencia preparada */
        $query->execute(); /* Ejecuto la sentencia preparada*/ 
        $result = $query->get_result(); /* Devuelve un conjunto de resultados para consultas SELECT select, o false para otras consultas DML o en caso de fallo */
        if ($row = $result->fetch_assoc()) { /* Devuelve un array asociativo de strings que representa a la fila obtenida del conjunto de resultados, donde cada clave del array representa el nombre de una de las columnas de éste; o null si no hubieran más filas en dicho conjunto de resultados */
            echo json_encode($row,JSON_UNESCAPED_UNICODE);   /* Devuelve un string con la representación JSON de value */  
        }
        /* cerrar la conexión */
        $query->close();
        $conn->close();
    }else {
        echo "No se puede conectar";
    }
} catch (\Throwable $th) { /* Capturo el error */
    echo $th;
}
?>