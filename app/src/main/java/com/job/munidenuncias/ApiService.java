package com.job.munidenuncias;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {

    String API_BASE_URL = "https://denuncias-api-jobcantaro.c9users.io/";

    @GET("api/v1/denuncias")
    Call<List<Denuncia>> getDenuncias();

    @FormUrlEncoded
    @POST("/api/v1/denuncias")
    Call<ResponseMessage> createDenuncia(@Field("nombre") String nombre,
                                         @Field("usuario_id") int usuario_id,
                                         @Field("lat") String lat,
                                         @Field("lon") String lon,
                                         @Field("detalles") String detalles);
    @Multipart
    @POST("/api/v1/denuncias")
    Call<ResponseMessage> createDenunciaWithImage(
            @Part("titulo") RequestBody titulo,
            @Part("usuarios_id") RequestBody usuarios_id,
            @Part("lat") RequestBody lat,
            @Part("lon") RequestBody lon,
            @Part("detalles") RequestBody detalles,
            @Part MultipartBody.Part imagen
    );

    @DELETE("/api/v1/denuncias/{id}")
    Call<ResponseMessage> destroyDenuncia(@Path("id") Integer id);

    @GET("api/v1/denuncias/{id}")
    Call<Denuncia> showDenuncia(@Path("id") Integer id);


    //Para el usuario
    @FormUrlEncoded
    @POST("/api/v1/login")
    Call<Usuario> validarLogin(@Field("username") String username,
                               @Field("password") String password);

    @GET("api/v1/usuarios")
    Call<List<Usuario>> getUsuarios();

    @FormUrlEncoded
    @POST("/api/v1/userregister")
    Call<ResponseMessage> createUser(@Field("username") String username,
                                         @Field("password") String password,
                                         @Field("correo") String correo);
}

