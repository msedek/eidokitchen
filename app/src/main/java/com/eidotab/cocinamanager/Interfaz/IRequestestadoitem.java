package com.eidotab.cocinamanager.Interfaz;


import com.eidotab.cocinamanager.Model.Estadoitem;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IRequestestadoitem {


/*    @GET("api/estadoitems")
    Call<ArrayList<Seteadores>> getJSONEstadoitem();*/

    @PUT("api/estadoitems/{id}")
    Call<Estadoitem> updateEstadoitem(@Path("id") String estadoitemId, @Body Estadoitem estadoitem);


}
