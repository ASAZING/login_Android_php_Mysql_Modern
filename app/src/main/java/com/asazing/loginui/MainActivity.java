package com.asazing.loginui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText inputEmail, inputPass; // Declaro las varibles para los Editext de la pagina de login

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide(); // Oculto la ActionBar
        inputEmail = findViewById(R.id.inputEmailLogin); // Le asigono un elemento a la variable inputEmail
        inputPass = findViewById(R.id.inputPasswordLogin); // Le asigono un elemento a la variable inputPass
    }

    public void onLoginClick(View View){ // Metodo para lanzar Activity registro de usuario
        startActivity(new Intent(this,RegisterActivity.class)); // inicio la actividad
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay); // Doy animacion al cambio de actividad
    }

    public void onLoginAuth(View View){ // Metodo para la autentificacion de usuario y contraseña
        if (!isEmptyText(inputEmail) && !isEmptyText(inputPass)) { // Valido que los campos no esten bacios
            if (isEmail(inputEmail.getText().toString())){
                auth("http://192.168.1.13/api/login/auth.php"); // LLamo al metodo auth y le envio la url del api
            }

        }else{ // Si alguno de los dos campos esta bacio lanzara el Toast con el siguiente mensaje
            Toast.makeText(MainActivity.this, "Ingrese Todos Los Campos", Toast.LENGTH_SHORT).show();
        }
    }


    private void auth(String URL){ // Metodo de autentificacion que recibe la URL del api
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() { // Solicito una respuesta en String Mediante La URL Proporcionada
            @Override
            public void onResponse(String response) { // Metodo de solicitud personalizada
                    if(!response.isEmpty()){
                        Intent intent= new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(MainActivity.this, "Usuario o contraseña INCORRECTA", Toast.LENGTH_SHORT).show();
                    }
            }
        }, new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){ // Respuesta ante error en la conexion
                System.out.println("Error : " + error.toString() );
                Toast.makeText(MainActivity.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError { // Metodo para obtener los parametros d
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", inputEmail.getText().toString());
                params.put("pass", inputPass.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest); // Agrego la solicitud a RequestQueu
    }

    //metodo para validar si es un email
    public  boolean isEmail(String email) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            Toast.makeText(MainActivity.this, "Ingrese un correo valido", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    //metodo para validar si editext esta vacio
    public  boolean isEmptyText(EditText str){
        String values = str.getText().toString().trim();
        if(TextUtils.isEmpty(values)){
            str.setError("Campo Requerido");
            str.requestFocus();
            return true;
        }
        else{
            return false;
        }
    }


    }
