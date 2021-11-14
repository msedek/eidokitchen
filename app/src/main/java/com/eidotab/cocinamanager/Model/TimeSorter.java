package com.eidotab.cocinamanager.Model;


import java.util.Comparator;

public class TimeSorter implements Comparator<DataRoot>
{

    public int compare(DataRoot one, DataRoot another){
    return one.getFechaorden().compareTo(another.getFechaorden());
}


}