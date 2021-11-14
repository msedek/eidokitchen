package com.eidotab.cocinamanager.Model;


import java.util.Comparator;

public class DoubleIpbstado implements Comparator<DataRoot>
{

    public int compare(DataRoot cmd1, DataRoot cmd2)
    {

        int stringResult = cmd1.getEstado_orden().compareTo(cmd2.getEstado_orden());
        if (stringResult == 0)
        {
            // Strings are equal, sort by date
            return cmd1.getIpb().compareTo(cmd2.getIpb());
        }
        else
        {

            return stringResult;
        }
    }


}
