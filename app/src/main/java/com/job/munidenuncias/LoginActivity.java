package com.job.munidenuncias;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = (EditText) findViewById(R.id.user);
        passwordInput = (EditText) findViewById(R.id.pass);
    }

    public void callLogin(View view) {

        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (username.isEmpty() && password.isEmpty()) {
            Toast.makeText(this, "Complete campos", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Usuario> call = null;

        call = service.validarLogin(username, password);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                try {


                    if (response.isSuccessful()) {

                        Usuario usuario = response.body();
                        //Log.d(TAG, "responseMessage: " + responseMessage);
                        String user = usuario.getUsername();
                        int id = usuario.getId();
                       // startActivity(new Intent(LoginActivity.this, ListadoActivity.class));
                        Intent intent2 = new Intent(LoginActivity.this, ListadoActivity.class);
                        intent2.putExtra("id", id);

                        //Intent intent = new Intent(LoginActivity.this, DenunciasAdapter.class);
                        //intent.putExtra("user", user);

                        //startActivity(intent);
                        startActivity(intent2);
                        finish();

                    } else {
                        //Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        //Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    } catch (Throwable x) {
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
              //  Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    public void callRegister(View view){
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
    }
}
