
package com.eidotab.cocinamanager.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class DataRoot implements Serializable, Comparable<DataRoot>
{

/*    public static final int COMANDA_TYPE = 0;
    public static final int PLATO_TYPE = 1;*/

    private String _id;
    private String mesa;
    //private String modificador;
    private String nro_comanda;
    private String estado_orden;
    private String estado_pcuenta;
    private String estado_pago;
    private ArrayList<Itemorder> orden;
    private Date fechaorden;
    private Integer ipb;
    //private Boolean verf = false;
    //private int pbase;
    //private int mType;


/*    public DataRoot(String _id, String mesa, String nro_comanda, String estado_orden, String estado_pcuenta, String estado_pago, Date fechaorden, ArrayList<Itemorder> orden, int type)
    {
        this._mid = _id;
        this.mmesa = mesa;
        this.mnro_comanda = nro_comanda;
        this.mestado_orden = estado_orden;
        this.mestado_pcuenta = estado_pcuenta;
        this.mestado_pago = estado_pago;
        this.mfechaorden = fechaorden;
        this.morden = orden;
        this.mType = type;
    }*/

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getNro_comanda() {
        return nro_comanda;
    }

    public void setNro_comanda(String nro_comanda) {
        this.nro_comanda = nro_comanda;
    }

    public String getEstado_orden() {
        return estado_orden;
    }

    public void setEstado_orden(String estado_orden) {
        this.estado_orden = estado_orden;
    }

    public String getEstado_pcuenta() {
        return estado_pcuenta;
    }

    public void setEstado_pcuenta(String estado_pcuenta) {
        this.estado_pcuenta = estado_pcuenta;
    }

    public String getEstado_pago() {
        return estado_pago;
    }

    public void setEstado_pago(String estado_pago) {
        this.estado_pago = estado_pago;
    }

    public Date getFechaorden() {
        return fechaorden;
    }

    public void setFechaorden(Date fechaorden) {
        this.fechaorden = fechaorden;
    }

    @Override
    public int compareTo(DataRoot other) {
        return fechaorden.compareTo(other.fechaorden);
    }

    public ArrayList<Itemorder> getOrden() {
        return orden;
    }

    public void setOrden(ArrayList<Itemorder> orden) {
        this.orden = orden;
    }

    @Override
    public boolean equals(Object anObject)
    {
        if (!(anObject instanceof DataRoot)) {
            return false;
        }
        DataRoot otherDataroot = (DataRoot) anObject;

/*        Calendar datereci = Calendar.getInstance();
        Calendar datepantalla = Calendar.getInstance();
        Long diff;*/

        if (otherDataroot.getMesa().equals(getMesa())) return true;
        //if(((DataRoot) anObject).getAdjetivo().equals(otherDataroot.getAdjetivo())) return true;
        //if(((Ejemplo) anObject).getDescri().equals(otherDataroot.getDescri())) return true;

/*        datereci.setTime(otherDataroot.getFechaorden());
        long milireci = datereci.getTimeInMillis();
        datepantalla.setTime(getFechaorden());
        long milipantalla = datepantalla.getTimeInMillis();
        diff = milipantalla - milireci;
        if(diff <= 59 * 1000) return true;*/

        return false;

    }

/*    public Integer getIpb() {
        return ipb;
    }

    public void setIpb(Integer ipb) {
        this.ipb = ipb;
    }

    public String getModificador() {
        return modificador;
    }

    public void setModificador(String modificador) {
        this.modificador = modificador;
    }

    public Boolean getVerf() {
        return verf;
    }

    public void setVerf(Boolean verf) {
        this.verf = verf;
    }*/

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Integer getIpb() {
        return ipb;
    }

    public void setIpb(Integer ipb) {
        this.ipb = ipb;
    }

/*    public int getType() {
        return mType;
    }

    public void setType(int mType) {
        this.mType = mType;
    }*/

/*    public int getPbase() {
        return pbase;
    }

    public void setPbase(int pbase) {
        this.pbase = pbase;
    }*/
}