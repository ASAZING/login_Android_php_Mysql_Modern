package com.asazing.loginui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "Error Ip";
    EditText inputNameR, inputLastNameR, inputEmailR, inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        changeStatusBarColor();
        inputNameR = findViewById(R.id.inputNameRegister);
        inputLastNameR = findViewById(R.id.inputLastNameRegister);
        inputEmailR = findViewById(R.id.inputEmailRegister);
        inputPassword = findViewById(R.id.inputPasswordRegister);
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color, getTheme()));
        }
    }
    public void onLoginClick(View view){
        startActivity(new Intent(this,MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    public void onRegisterClick(View view){
        if (!isEmptyText(inputNameR) && !isEmptyText(inputLastNameR) && !isEmptyText(inputEmailR) && !isEmptyText(inputPassword)) {
            if (isEmail(inputEmailR.getText().toString())){
                register("http://192.168.1.13/api/login/register.php");
            }
        }else{
            Toast.makeText(RegisterActivity.this, "Ingrese Todos Los Campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void register(String URL){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Se registro Correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(RegisterActivity.this, "Error al Registrar", Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener(){
            public void onErrorResponse(VolleyError error){
                Toast.makeText(RegisterActivity.this, "Error :" + error, Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", inputNameR.getText().toString());
                params.put("lastname", inputLastNameR.getText().toString());
                params.put("email", inputEmailR.getText().toString());
                params.put("pass", inputPassword.getText().toString());
                params.put("ip", getIP());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    //metodo para validar si es un email
    public  boolean isEmail(String email) {
        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        } else {
            Toast.makeText(RegisterActivity.this, "Ingrese un correo valido", Toast.LENGTH_SHORT).show();
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
    public static String getIP(){
        List<InetAddress> addrs;
        String address = "";
        try{
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for(NetworkInterface intf : interfaces){
                addrs = Collections.list(intf.getInetAddresses());
                for(InetAddress addr : addrs){
                    if(!addr.isLoopbackAddress() && addr instanceof Inet4Address){
                        address = addr.getHostAddress().toUpperCase(new Locale("es", "MX"));
                    }
                }
            }
        }catch (Exception e){
            Log.w(TAG, "Ex getting IP value " + e.getMessage());
        }
        return address;
    }

}