package com.job.munidenuncias;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListadoActivity extends AppCompatActivity {

    private static final String TAG = ListadoActivity.class.getSimpleName();

    private RecyclerView denunciasList;
    private static final int REGISTER_FORM_REQUEST = 100;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);


        //Intent del login
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        denunciasList = (RecyclerView) findViewById(R.id.recyclerview);
        denunciasList.setLayoutManager(new LinearLayoutManager(this));

        denunciasList.setAdapter(new DenunciasAdapter(this));

        initialize();
    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<List<Denuncia>> call = service.getDenuncias();

        call.enqueue(new Callback<List<Denuncia>>() {
            @Override
            public void onResponse(Call<List<Denuncia>> call, Response<List<Denuncia>> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        List<Denuncia> denuncias = response.body();
                        Log.d(TAG, "denuncias: " + denuncias);

                        DenunciasAdapter adapter = (DenunciasAdapter) denunciasList.getAdapter();
                        adapter.setDenuncias(denuncias);
                        adapter.notifyDataSetChanged();

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(ListadoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<List<Denuncia>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(ListadoActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    public void showRegister(View view){

        //Intent para el registro
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("id", id);

        startActivityForResult(intent,REGISTER_FORM_REQUEST);
       // startActivityForResult(new Intent(this, RegisterActivity.class), REGISTER_FORM_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REGISTER_FORM_REQUEST) {
            // refresh data
            initialize();
        }
    }

}

