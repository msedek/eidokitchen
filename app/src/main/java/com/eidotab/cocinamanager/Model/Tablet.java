package com.eidotab.cocinamanager.Model;

import java.io.Serializable;

public class Tablet implements Serializable
{
    private String nro_tablet;
   // private String nro_mesa;
   // private String nro_mesero;

    public String getNro_tablet() {
        return nro_tablet;
    }

    public void setNro_tablet(String nro_tablet) {
        this.nro_tablet = nro_tablet;
    }

}
