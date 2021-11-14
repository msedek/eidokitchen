package com.eidotab.cocinamanager.Model;


import java.util.Comparator;

public class EstadoSorter implements Comparator<DataRoot>
{
    public int compare(DataRoot one, DataRoot another)
    {
        return one.getEstado_orden().compareTo(another.getEstado_orden());
    }
}

