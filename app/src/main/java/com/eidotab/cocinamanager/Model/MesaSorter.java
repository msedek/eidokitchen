package com.eidotab.cocinamanager.Model;


import java.util.Comparator;

public class MesaSorter implements Comparator<DataRoot>
{

        public int compare(DataRoot one, DataRoot another){
            return one.getMesa().compareTo(another.getMesa());
        }

}
