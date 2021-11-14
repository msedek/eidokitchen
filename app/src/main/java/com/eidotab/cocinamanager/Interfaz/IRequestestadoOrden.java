package com.eidotab.cocinamanager.Interfaz;


import com.eidotab.cocinamanager.Model.Estadoorden;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IRequestestadoOrden {


    @PUT("api/estadoorden/{id}")
    Call<Estadoorden> updateEstadoorden(@Path("id") String estadoordenId, @Body Estadoorden estadoorden);




}
