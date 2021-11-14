package com.eidotab.cocinamanager.Model;

import java.util.Comparator;



public class IpbSorter implements Comparator<DataRoot>
{

    public int compare(DataRoot one, DataRoot another){
        return one.getIpb().compareTo(another.getIpb());
    }


}
