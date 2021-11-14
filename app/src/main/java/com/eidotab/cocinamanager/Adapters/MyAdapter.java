package com.eidotab.cocinamanager.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eidotab.cocinamanager.Model.DataRoot;
import com.eidotab.cocinamanager.Model.DoubleIpbstado;
import com.eidotab.cocinamanager.R;
import com.eidotab.cocinamanager.SQlite.DBHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.TimeZone;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH>
{
    private ArrayList<DataRoot> rir;
    private DBHelper myDB;
    private Context mContext;
    private TextView lbl_comandas;
    private TextView swapper;

    public MyAdapter(Context context, TextView txt_comandas, ArrayList<DataRoot> rir, DBHelper myDB, TextView swapper)
    {
        this.lbl_comandas = txt_comandas;
        this.swapper = swapper;
        this.mContext = context;
        this.myDB = myDB;
        this.rir = rir;
    }

    @NonNull
    @Override
    public MyAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);

        return new MyAdapter.VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.VH holder, int position)
    {
        final DataRoot elem = rir.get(position);
        holder.cuentaplato = elem.getOrden().size();
        holder.setAdapter(position);
        holder.setData(elem);
    }

    @Override
    public int getItemCount()
    {

        return rir.size();

    }

    public class VH extends RecyclerView.ViewHolder
    {

        RecyclerView mRecyclerview2;
        MyAdapter2 adp;

        TimeZone tz;
        DateFormat df;

        CardView elpadre;

        private TextView txt_comanda;
        private TextView txt_mesa;
        private TextView txt_hora;
        private TextView torden;


        private Button btn_cerrar;

/*        private LinearLayout le;

        private ScrollView sc;
        private ScrollView si;

        private LinearLayout lecon;
        private LinearLayout leing;*/


        // private TextView canordert;

        private int cuentaplato;


        @SuppressLint("SimpleDateFormat")
        public VH(View itemView)
        {
            super(itemView);

            Calendar cal = Calendar.getInstance();
            tz = cal.getTimeZone();
            df = new SimpleDateFormat("h:mm a");
            df.setTimeZone(tz);

            txt_comanda    = itemView.findViewById(R.id.txt_comanda);
            txt_mesa       = itemView.findViewById(R.id.txt_mesa);
            torden         = itemView.findViewById(R.id.torden);
            btn_cerrar     = itemView.findViewById(R.id.btn_cerrar);
            txt_hora       = itemView.findViewById(R.id.txt_hora);
            // canordert      = itemView.findViewById(R.id.canordert);
            elpadre        = itemView.findViewById(R.id.elpadre);
            mRecyclerview2 = itemView.findViewById(R.id.mRecyclerview2);
            cuentaplato = 0;


            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            //float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
            //float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
            float dpWidth = displayMetrics.widthPixels;

            float margen = (float) (dpWidth * 0.01); //era 0.04

            int ancho = (int) dpWidth + (int)margen;

            int copantalla = 4;

            elpadre.setLayoutParams(new LinearLayout.LayoutParams( (ancho / copantalla), ViewGroup.LayoutParams.MATCH_PARENT));


        }


        @SuppressLint("SetTextI18n")
        public void setData(final DataRoot dataRoot) {

            String date = df.format(dataRoot.getFechaorden());

            txt_hora.setText("Hora de la Orden: " + date);

            if (getAdapterPosition()<10)
            {
                txt_comanda.setText("0" + getAdapterPosition()+ "");
            }
            else
            {
                txt_comanda.setText(getAdapterPosition()+ "");
            }

            dataRoot.setNro_comanda(txt_comanda.getText().toString()); //TODO SETEANDO LA DATA QUE VA PARA LA DB QUE ES LA PROCESADA POR EL ADAPTER

            txt_mesa.setText(dataRoot.getMesa() + "");
            torden.setText(dataRoot.getEstado_orden() + "");//para el proceso de PUT

            btn_cerrar.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View view) {
                    btn_cerrar.setEnabled(false);
                    //btn_cerrar.setBackground(mContext.getResources().getDrawable(R.drawable.offbtn,null));
                    btn_cerrar.setBackground(ResourcesCompat.getDrawable(mContext.getResources(), R.drawable.offbtn, null));

                    dataRoot.setEstado_orden("alisto");

                    Integer cancomanda = Integer.parseInt(lbl_comandas.getText().toString());
                    if (cancomanda > 0)
                    {
                        cancomanda--;
                        lbl_comandas.setText(cancomanda + "");
                    }

                    myDB.updateComanda(dataRoot);



                    Collections.sort(rir, new DoubleIpbstado());
                    notifyDataSetChanged();
                    swapper.setText("true");

                    //TODO ACA YA ENVIAR A LA BASE DE DATOS DE CAJA

                }
            });
        }

        private void setAdapter(int position)
        {
            adp =  new MyAdapter2(mContext,txt_mesa, btn_cerrar, cuentaplato, rir.get(position).getOrden(),myDB,rir, position);
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerview2.setLayoutManager(linearLayoutManager);
            mRecyclerview2.setAdapter(adp);
        }

    }
}


