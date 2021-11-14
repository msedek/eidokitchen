package com.eidotab.cocinamanager.Interfaz;

import com.eidotab.cocinamanager.Model.Nrocomanda;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface IRequestSeteos {
/*
    @GET("api/seteos/{id}")
    Call<Seteadores> getJSONSeteos(@Path("id") String id);

    @PUT("api/seteos/{id}")
    Call<Seteadores> updateSeteadores(@Path("id") String seteoId, @Body Seteadores seteador);*/

    @PUT("api/estadocomanda/{id}")
    Call<Nrocomanda> updateNrocomanda(@Path("id") String seteoId, @Body Nrocomanda nrocomanda);


/*
    @GET("/api/comandas")
    Call<ArrayList<Comanda>> getJSONComandas();

    @POST("/comandas")
    Call<Comanda> addComanda(@Body Comanda comanda);



    @DELETE("/comandas/{id}")
    Call<Comanda> deleteComanda(@Path("id") String comandaId);


*/


}
