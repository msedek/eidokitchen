/*
package com.eidotab.cocinamanager.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eidotab.cocinamanager.Model.DataRoot;
import com.eidotab.cocinamanager.R;
import com.eidotab.cocinamanager.SQlite.DBHelper;

import java.util.ArrayList;

public class MyAdapters extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //public ArrayList<DataRoot> Rir;
    //private DBHelper myDB;

    public static final int COMANDA_TYPE = 0;
    public static final int PLATO_TYPE = 1;

    private ArrayList<DataRoot> mRir;
    private Context mContext;
    private TextView hatimer;
    private TextView txcomandas;
    private TextView txentracola;
    private TextView txfondocola;
    private TextView txpostreacola;
    private TextView tswapper;

    public MyAdapters(Context context, TextView habtimer, TextView txt_comandas, TextView txt_entracola, TextView txt_fondocola, TextView txt_postrecola, TextView swapper, ArrayList<DataRoot>Rir)
    {

        this.hatimer = habtimer;
        this.txcomandas = txt_comandas;
        this.txentracola = txt_entracola;
        this.txfondocola = txt_fondocola;
        this.txpostreacola = txt_postrecola;
        this.tswapper = swapper;
        this.mContext = context;
        //myDB = DBHelper.GetDBHelper(mContext);
        this.mRir = Rir;
       // Rir = new ArrayList<>();

    }
    
    
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case COMANDA_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
                return new ComandaViewHolder(view);
            case PLATO_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_plato, parent, false);
                return new PlatoViewHolder(view);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        DataRoot object = mRir.get(position);
        if (object != null) {
            switch (object.getType())
            {
                case COMANDA_TYPE:

                    */
/*((ComandaViewHolder) holder).mTitle.setText(object.getName());*//*



                    break;

                case PLATO_TYPE:

                    */
/*((PlatoViewHolder) holder).mTitle.setText(object.getName());
                    ((PlatoViewHolder) holder).mDescription.setText(object.getDescription());*//*


                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mRir == null)
            return 0;
        return mRir.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mRir != null) {
            DataRoot object = mRir.get(position);
            if (object != null) {
                return object.getType();
            }
        }
        return 0;
    }

    public static class ComandaViewHolder extends RecyclerView.ViewHolder {



        public ComandaViewHolder(View itemView) {
            super(itemView);

        }
    }

    public static class PlatoViewHolder extends RecyclerView.ViewHolder {



        public PlatoViewHolder(View itemView) {
            super(itemView);

        }
    }
}*/
