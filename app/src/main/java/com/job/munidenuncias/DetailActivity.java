package com.job.munidenuncias;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    private Integer id;

    private ImageView fotoImage;
    private TextView nombreText;
    private TextView detallesText;
    private TextView precioText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        fotoImage = (ImageView)findViewById(R.id.foto_image);
        nombreText = (TextView)findViewById(R.id.nombre_text);
        detallesText = (TextView)findViewById(R.id.detalles_text);
        precioText = (TextView)findViewById(R.id.precio_text);

        id = getIntent().getExtras().getInt("ID");
        Log.e(TAG, "id:" + id);

        initialize();
    }

    private void initialize() {

        ApiService service = ApiServiceGenerator.createService(ApiService.class);

        Call<Denuncia> call = service.showDenuncia(id);

        call.enqueue(new Callback<Denuncia>() {
            @Override
            public void onResponse(Call<Denuncia> call, Response<Denuncia> response) {
                try {

                    int statusCode = response.code();
                    Log.d(TAG, "HTTP status code: " + statusCode);

                    if (response.isSuccessful()) {

                        Denuncia denuncia = response.body();
                        Log.d(TAG, "denuncia: " + denuncia);

                        String url = ApiService.API_BASE_URL + "/images/" + denuncia.getImagen();
                        Picasso.with(DetailActivity.this).load(url).into(fotoImage);

                        nombreText.setText(denuncia.getTitulo());
                        detallesText.setText(denuncia.getDetalles());
                        precioText.setText("Por:" + denuncia.getUsuarios_id());

                    } else {
                        Log.e(TAG, "onError: " + response.errorBody().string());
                        throw new Exception("Error en el servicio");
                    }

                } catch (Throwable t) {
                    try {
                        Log.e(TAG, "onThrowable: " + t.toString(), t);
                        Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }catch (Throwable x){}
                }
            }

            @Override
            public void onFailure(Call<Denuncia> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

}

