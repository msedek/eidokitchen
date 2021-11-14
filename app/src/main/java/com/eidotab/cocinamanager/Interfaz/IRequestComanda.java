package com.eidotab.cocinamanager.Interfaz;


import com.eidotab.cocinamanager.Model.DataRoot;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface IRequestComanda {



    @GET("api/comanda/{id}")
    Call<DataRoot> getJSONComandasid(@Path("id") String id);


    @GET("api/comandas")
    Call<ArrayList<DataRoot>> getJSONComandas();

    @POST("api/comandas")
    Call<DataRoot> addComanda(@Body DataRoot dataRoot);

    @PUT("api/comandas/{id}")
    Call<DataRoot> updateComanda(@Path("id") String comandaId, @Body DataRoot dataRoot);

    @DELETE("api/comandas/{id}")
    Call<DataRoot> deleteComanda(@Path("id") String comandaId);
}


