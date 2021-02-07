<?php 
require_once 'connection.php';
$name=$_POST['name'];
$lastname=$_POST['lastname'];
$email=$_POST['email'];
$pass=$_POST['pass'];
$ip=$_POST['ip'];

try {
    if(connect()){
        $query=$conn->prepare("SELECT * FROM users WHERE email=?"); /* Esta es la consulta */
        $query->bind_param('s',$email); /* Sentencia preparada */
        $query->execute(); /* Ejecuto la sentencia preparada*/ 
        $result = $query->get_result(); /* Devuelve un conjunto de resultados para consultas SELECT select, o false para otras consultas DML o en caso de fallo */
        if ($result->fetch_assoc() == 0) { /* Valido que no exista el correo en la base de datos que queiro registrar */
            $query =$conn->prepare("INSERT INTO users (name_user, lastname_user, email, pass, ip) VALUES (?, ?, ?, ?, ?)");
            $query->bind_param('sssss',$name, $lastname,$email,$pass, $ip);
            if($query->execute()){
                echo "Datos ingresados correctamente";
            }else {
                die("Error al insertar datos");
            }
            $query->close();
            $conn->close();
        }else {
            return false;
            die("El Email ya existe");
        }
        $query->close();
        $conn->close();
    }else{
        echo "No se puede conectar";
    }
} catch (\Throwable $th) {
    echo $th; 
}

?>
