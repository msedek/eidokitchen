package com.eidotab.cocinamanager.Interfaz;


import com.eidotab.cocinamanager.Model.DataRoot;
import com.eidotab.cocinamanager.Model.Itemorder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IRequestOrden {


    @PUT("api/ordenes/{id}")
    Call< DataRoot> updateOrden(@Path("id") String ordenId, @Body DataRoot orden);



}
