/*
package com.eidotab.cocinamanager.Model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class DummyData {
    public static List<DataRoot> getData() {

        ArrayList<Itemorder>orden = new ArrayList<>();

        for (int i = 0; i < 1; i++) {

            Itemorder itemorder = new Itemorder();
            itemorder.setEstadoitem("pendiente");
            itemorder.setCategoria("entradas");
            itemorder.setNombre_plato("Sapo");

        }

        String dateString = "2017-02-25T23:09:00.123Z";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        List<DataRoot> list = new ArrayList<>();
        list.add(new DataRoot("123456","","","pendiente","pendiente","pendiente",convertedDate,null,DataRoot.COMANDA_TYPE));
        list.add(new DataRoot(null,null,null,null,null,null,null,orden,DataRoot.PLATO_TYPE));
        return list;
    }
}



/**/