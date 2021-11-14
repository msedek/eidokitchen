package com.eidotab.cocinamanager.Adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eidotab.cocinamanager.Interfaz.IRequestMensaje;
import com.eidotab.cocinamanager.Model.DataRoot;
import com.eidotab.cocinamanager.Model.Itemorder;
import com.eidotab.cocinamanager.Model.Mensaje;
import com.eidotab.cocinamanager.R;
import com.eidotab.cocinamanager.SQlite.DBHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.VH>
{
    private Context mContexts;
    private ArrayList<Itemorder> data2;
    private TextView txt_mesa;
    private Button btn_cerrar;
    private Integer cuentapla;
    private DBHelper myDB;
    private ArrayList<DataRoot> rir;
    private int posi;

    public MyAdapter2(Context context, TextView txt_mesa,Button btn_cerrar, Integer cuentaplato, ArrayList<Itemorder> data2, DBHelper myDB, ArrayList<DataRoot> rir, int position)
    {
        this.mContexts = context;
        this.data2 = data2;
        this.txt_mesa = txt_mesa;
        this.btn_cerrar = btn_cerrar;
        this.cuentapla = cuentaplato;
        this.myDB = myDB;
        this.rir = rir;
        this.posi = position;
    }

    @Override
    public MyAdapter2.VH onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_plato, parent, false);


        return new MyAdapter2.VH(v);
    }

    @Override
    public void onBindViewHolder(final MyAdapter2.VH holder, int position)
    {
        final Itemorder rData = data2.get(position);

        holder.setData(rData);
    }

    @Override
    public int getItemCount()
    {

        return data2.size();

    }

    private void sendmensaje(Mensaje mensaje)
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mContexts.getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRequestMensaje request = retrofit.create(IRequestMensaje.class);

        Call<Mensaje> call = request.addMensaje(mensaje);

        call.enqueue(new Callback<Mensaje>()
        {
            @Override
            public void onResponse(@NonNull Call<Mensaje> call, @NonNull Response<Mensaje> response)
            {
                Log.i("RETROFIT", "Envió mensaje a mesero");

            }

            @Override
            public void onFailure(@NonNull Call<Mensaje> call, @NonNull Throwable t)
            {

                Log.d("Error agregar pedido " , t.getMessage());
            }
        });
    }

/*    private void showmessage(String mensaje)
    {
        final Toast toast = Toast.makeText(mContexts, mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 800);

    }*/

    public class VH extends RecyclerView.ViewHolder
    {
        private TextView te;
        private Button btn_plato;
        private LinearLayout lyo_adic;
        private LinearLayout lyo_ingre;
        private LinearLayout lyo_contor;

        public VH(View itemView)
        {
            super(itemView);

            lyo_contor     = itemView.findViewById(R.id.lyo_contor);
            btn_plato      = itemView.findViewById(R.id.btn_plato);
            lyo_adic       = itemView.findViewById(R.id.lyo_adic);
            lyo_ingre      = itemView.findViewById(R.id.lyo_ingre);

        }

        @SuppressLint("ObsoleteSdkInt")
        public void setData(final Itemorder rData)
        {
            btn_plato.setText(rData.getNombre_plato());



            if(rData.getEstadoitem().equals("listo"))
            {
                cuentapla--;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                {

                    btn_plato.setBackground(mContexts.getResources().getDrawable(R.drawable.offbtn, null));

                }
                else
                {

                    btn_plato.setBackground(mContexts.getResources().getDrawable(R.drawable.offbtn));

                }


                btn_plato.setEnabled(false);

                boolean act = cuentapla == 0;

                Log.i("cuentaplato", cuentapla + "");

                if(act && rir.get(posi).getEstado_orden().equals("pendiente"))
                {
                    btn_cerrar.setEnabled(act);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    {

                        btn_cerrar.setBackground(mContexts.getResources().getDrawable(R.drawable.cerrarbtn, null));

                    }
                    else
                    {

                        btn_cerrar.setBackground(mContexts.getResources().getDrawable(R.drawable.cerrarbtn));

                    }

                }


            }

            if(rData.getEstadoitem().equals("pendiente"))
            {
                btn_plato.setEnabled(true);

                String filtro = rData.getCategoria();     // ACA SE CONFIGURA BAR O COCINA O AMBOS

                switch(filtro)
                {
                    case "entradas":


                        btn_plato.setBackground(mContexts.getResources().getDrawable(R.drawable.entradasbtnbb, null));


                        break;
                    case "fondos":



                        btn_plato.setBackground(mContexts.getResources().getDrawable(R.drawable.fondosbtnbb, null));



                        break;
                    case "postres":


                        btn_plato.setBackground(mContexts.getResources().getDrawable(R.drawable.postresbtnbb, null));



                        break;
                    case "guarniciones":


                        btn_plato.setBackground(mContexts.getResources().getDrawable(R.drawable.entradasbtnbb, null));




                        break;
                    case "menú infantil":


                        btn_plato.setBackground(mContexts.getResources().getDrawable(R.drawable.fondosbtnbb, null));



                        break;
                    case "bebidas":
                        // btn_plato.setBackground(mContext.getResources().getDrawable(R.drawable., null));
                        break;
                }
            }

            if(rData.getEstadoitem().equals("tomado"))
            {


                btn_plato.setBackground(mContexts.getResources().getDrawable(R.drawable.tomadobtn, null));


                btn_plato.setEnabled(true);
            }

            btn_plato.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {

                    if(rData.getEstadoitem().equals("pendiente"))
                    {


                        btn_plato.setBackground(mContexts.getResources().getDrawable(R.drawable.tomadobtn, null));


                        rData.setEstadoitem("tomado");

                        myDB.updateComanda(rir.get(posi));

                    }
                    else
                    {
                        if(rData.getEstadoitem().equals("tomado"))
                        {


                            btn_plato.setBackground(mContexts.getResources().getDrawable(R.drawable.offbtn, null));


                            rData.setEstadoitem("listo");
                            btn_plato.setEnabled(false);
                            cuentapla--;

                            boolean act = cuentapla == 0;

                            Log.i("cuentaplato", cuentapla + "");

                            if(act)
                            {
                                btn_cerrar.setEnabled(act);



                                btn_cerrar.setBackground(mContexts.getResources().getDrawable(R.drawable.cerrarbtn, null));


                            }

                            Mensaje mensaje = new Mensaje();
                            mensaje.setEstadomensaje("PENDIENTE");
                            mensaje.setRemitente("COCINA/" + txt_mesa.getText());
                            mensaje.setTexto(btn_plato.getText() + " " + txt_mesa.getText());
                            sendmensaje(mensaje);



                            myDB.updateComanda(rir.get(posi));

                        }
                    }
                }
            });

            if (rData.getContornos().size() > 0 || rData.getIngredientes().size() > 0 )
            {
                crearcontor(rData.getContornos(), rData.getIngredientes());
            }
        }

        @SuppressLint("SetTextI18n")
        private void crearcontor(ArrayList<String>contornogen, ArrayList<String>ingregen)
        {
            lyo_contor.setVisibility(View.VISIBLE);

            for(String contorno : contornogen)
            {
                te = new TextView(mContexts);
                te.setText("S." + contorno);
                te.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                te.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                lyo_ingre.addView(te);
            }

            for(String ingredi : ingregen)
            {
                te = new TextView(mContexts);
                te.setText("A." + ingredi);
                te.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                te.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                lyo_adic.addView(te);
            }
        }
    }
}


