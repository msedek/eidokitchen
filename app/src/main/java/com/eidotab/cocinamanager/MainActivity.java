package com.eidotab.cocinamanager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.eidotab.cocinamanager.Adapters.MyAdapter;
import com.eidotab.cocinamanager.Interfaz.IRequestComanda;
import com.eidotab.cocinamanager.Model.DataRoot;
import com.eidotab.cocinamanager.Model.DoubleIpbstado;
import com.eidotab.cocinamanager.Model.DoubleSorter;
import com.eidotab.cocinamanager.Model.Itemorder;
import com.eidotab.cocinamanager.Model.MesaSorter;
import com.eidotab.cocinamanager.Model.Tablet;
import com.eidotab.cocinamanager.SQlite.DBHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{

    //region Variables
    Boolean controlcmuerta;
    Boolean habilitado;

    ImageView   ledtx;
    ImageButton btn_salir;
    ImageButton gps;

    Switch modoco;

    Button bntablet;

    EditText entablet;

    int velocidad;

    TextView txt_comanda;
    TextView txt_mesa;
    TextView tid;
    TextView torden;
    TextView swapper;
    TextView lblidset;
    TextView txt_comandas;
    TextView txt_entracola;
    TextView txt_fondocola;
    TextView txt_postrecola;
    TextView habtimertxt;
    TextView lbl_modoco;

    Integer borrado;
    Integer contaentra;
    Integer contafondo;
    Integer contaposts;

    LinearLayout lyfondo;
    ArrayList<DataRoot> rir;

    DBHelper myDB;

    RecyclerView recyclerView;


    boolean primera;

    MyAdapter adapter;

    // boolean sereordeno = false;


    TextClock tcl;

    int contar;

    Handler mHandler;

    Boolean red;

    String connect;

    boolean metoca;


    boolean modo;
    //endregion

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);


        //region Inicializar
        myDB = DBHelper.GetDBHelper(this);

        modoco         = findViewById(R.id.modoco);
        lblidset       = findViewById(R.id.lblidset);
        txt_comanda    = findViewById(R.id.txt_comanda);
        txt_mesa       = findViewById(R.id.txt_mesa);
        tid            = findViewById(R.id.tid);
        torden         = findViewById(R.id.torden);
        txt_comandas   = findViewById(R.id.txt_comandas);
        txt_entracola  = findViewById(R.id.txt_entracola);
        txt_fondocola  = findViewById(R.id.txt_fondocola);
        txt_postrecola = findViewById(R.id.txt_postrecola);
        lbl_modoco     = findViewById(R.id.lbl_modoco);
        swapper        = findViewById(R.id.swapper);
        habtimertxt    = findViewById(R.id.habtimertxt);

        btn_salir      = findViewById(R.id.btn_salir);
        gps            = findViewById(R.id.gps);
        ledtx          = findViewById(R.id.ledtx);

        lyfondo        = findViewById(R.id.lyfondo);

        tcl            = findViewById(R.id.tcl);

        bntablet       = findViewById(R.id.bntablet);

        entablet       = findViewById(R.id.entablet);

        recyclerView   = findViewById(R.id.recyclerview);

        rir = new ArrayList<>();

        velocidad = 1000; //VELOCIDAD DE LECTURA DEL SERVER

        habilitado = false;

        habilitado = myDB.listTablet().getNro_tablet() != null;

        modoco.setVisibility(View.VISIBLE);

        borrado = 0;

        connect = "";
        red = true;

        modo = false;

        metoca = false;

        controlcmuerta = false;

        swapper.setText("false");
        tcl.setFormat12Hour(null);
        tcl.setFormat24Hour("HH:mm");
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/digital-7.ttf");
        tcl.setTypeface(tf);

        //endregion

        //region Asignacion de numero de tablet
        if(myDB.listTablet().getNro_tablet()== null)
        {
            habtimertxt.setText("false");
            mostrarMensaje("Ingrese Número de Tablet");
            entablet.setVisibility(View.VISIBLE);
            bntablet.setVisibility(View.VISIBLE);

            modoco.setVisibility(View.INVISIBLE);



            bntablet.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    //RESTRINGIR EL NUMERO DE MESA A LA CANTIDAD REAL DEL CLIENTE
                    String compa = entablet.getText() + "";
                    if(compa.equals(""))
                    {
                        mostrarMensaje("Ingrese Número de Tablet");
                    }
                    else
                    {
                        Tablet tablet = new Tablet();
                        tablet.setNro_tablet(entablet.getText() + "");
                        myDB.addTablet(tablet);
                        entablet.setVisibility(View.INVISIBLE);
                        bntablet.setVisibility(View.INVISIBLE);

                        modoco.setVisibility(View.VISIBLE);


                        getWindow().getDecorView().setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                                        | View.SYSTEM_UI_FLAG_IMMERSIVE);


                        habilitado = true;
                        setAdapter();

                        habtimertxt.setText("true");
                    }
                }
            });
        }
        else
        {
            habilitado = true;
        }
        //endregion

        //region Automatico
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if(habtimertxt.getText().equals("true"))
                {
                    if (modo)
                    {
                        metoca = false;

                        mostrarMensaje("NO SE DETECTA OTRA TABLET DE COCINA VOLVER A MODO MONOCOCINA");


/*                        loadretrofitturnos();

                        Turno turno = myDB.listTurno();

                        Tablet tablet = myDB.listTablet();

                        int val = 0;

                        if (turno.getActivo() != null)
                        {
                            val = Integer.parseInt(turno.getActivo());

                        }

                        int val2 = Integer.parseInt(tablet.getNro_tablet());

                        metoca = val == val2;*/  //TODO LOGICA PARA MULTITABLET
                    }
                    else
                    {
                        metoca = true;
                    }

                    if (metoca)
                    {
                        habtimertxt.setText("false");

                        Boolean vacio = rir == null;

                        if (!vacio)
                        {

                            if(rir.size() > 0)
                            {
                                setCantidad(rir);
                            }

                            if(red)
                            {
                                if ((contar % 2) == 0) //par
                                {
                                    //  Drawable drawable = getApplicationContext().getResources().getDrawable(R.drawable.led, null);
                                    Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.led, null);

                                    ledtx.setImageDrawable(drawable);
                                }
                                else
                                {
                                    // Drawable drawable = getApplicationContext().getResources().getDrawable(R.drawable.led2, null);
                                    Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.led2, null);

                                    ledtx.setImageDrawable(drawable);
                                }
                            }
                            else
                            {
                                if ((contar % 2) == 0) //par
                                {
                                    //Drawable drawable = getApplicationContext().getResources().getDrawable(R.drawable.led3, null);
                                    Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.led3, null);

                                    ledtx.setImageDrawable(drawable);
                                }
                                else
                                {
                                    // Drawable drawable = getApplicationContext().getResources().getDrawable(R.drawable.led2, null);
                                    Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.led2, null);

                                    ledtx.setImageDrawable(drawable);
                                }
                            }

                            loadRetrofitComanda();
                        }
                    }
                }// LO QUE SE EJECUTA ACA DENTRO SE EJECUTA SIEMPRE Y CUANDO LA ETIQUETA SEA TRUE

                mHandler.postDelayed(this, velocidad);
            }
        },0);
        //endregion

        btn_salir.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Quit();

            }
        });

        modoco.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                modo = b;
                if(b)
                {
                    lbl_modoco.setText("MMTC");
                }
                else
                {
                    lbl_modoco.setText("MMNC");
                }

            }
        });



        gps.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                recyclerView.scrollToPosition(0);

                for (int i = 0; i < rir.size(); i++)
                {

                    String scrolleo = rir.get(i).getEstado_orden();

                    if(scrolleo.equals("pendiente"))
                    {
                        int posic = i + 3;

                        if(posic >= rir.size())
                        {
                            posic = rir.size() - 1;
                        }

                        final int posi = posic;

                        recyclerView.postDelayed(new Runnable()
                        {

                            @Override
                            public void run()
                            {

                                recyclerView.scrollToPosition(posi);

                            }
                        }, 50);

                        break;
                    }
                }
            }
        });
    }

    private ArrayList<DataRoot>filtrocate(ArrayList<DataRoot>recibido)
    {
        //TODO VERIFICAR SI LA COMANDA LA LEYO BAR, SI LA LEYO BAR, ELIMINAR, SI NO MARCAR.

        for (int i = 0; i < recibido.size(); i++)
        {
            deleteRetrofitComanda(recibido.get(i).get_id());

            for (int j = 0; j <recibido.get(i).getOrden().size() ; j++)
            {
                String categoria = recibido.get(i).getOrden().get(j).getCategoria().toLowerCase();
                if(categoria.equals("bebidas")) //ACA CAMBIAR POR LA O LAS CATEGORIAS A FILTRAR
                {
                    recibido.get(i).getOrden().remove(j);
                }
            }

            if(recibido.get(i).getOrden().size() == 0)
            {
                recibido.remove(i);
                i = -1;
            }
        }

        return recibido;
    }

/*    private void loadretrofitturnos()
    {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final IRequestMensaje request = retrofit.create(IRequestMensaje.class);

        Call<ArrayList<Turno>> call = request.getJSONTurnos();

        call.enqueue(new Callback<ArrayList<Turno>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Turno>> call, @NonNull Response<ArrayList<Turno>> response)
            {
                ArrayList<Turno> list = new ArrayList<>();

                //  mostrarMensaje("AJA Y ENTONCES");

                for(Turno turno : response.body())
                {
                    list.add(turno);
                }

                myDB.deleteAllTurno();

                for(Turno entra : list)
                {
                    myDB.addTurno(entra);
                }


            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<Turno>> call, @NonNull Throwable t)
            {


            }
        });

    }*/

    @SuppressLint("SetTextI18n")
    private void loadRetrofitComanda()
    {
        habtimertxt.setText("false");

        connect = "";

        contar++;

        if(contar > 3)
        {
            contar = 0;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final IRequestComanda request = retrofit.create(IRequestComanda.class);

        Call<ArrayList<DataRoot>> call = request.getJSONComandas();

        call.enqueue(new Callback<ArrayList<DataRoot>>()
        {
            @Override
            public void onResponse(@NonNull Call<ArrayList<DataRoot>> call, @NonNull Response<ArrayList<DataRoot>> response)
            {

                //<editor-fold desc="...VARIABLE QUE CONTROLA EL COLOR DEL LED CUANDO HAY RED">
                if (connect.equals(""))
                {

                    red = true;

                }
                //</editor-fold>

                ArrayList<DataRoot> recibido = response.body();

                if(modo)
                {
                    Log.i("hacer este method", "onResponse: ");

                }
                else
                {
                    //MODO MONOCOCINA

                    /*movida(rir);*/

                    assert recibido != null;
                    if(recibido.size() > 0)
                    {

                        ArrayList<DataRoot> filtro = filtrocate(recibido);

                        primera = rir.size() == 0 && filtro.size() > 0;

                        if(primera)
                        {
                            rir.add(filtro.get(0));
                            //guardar.add(filtro.get(0));
                            rir.get(0).setIpb(0);
                            myDB.addComanda(filtro.get(0));
                            adapter.notifyItemInserted(0);
                            filtro.remove(0);
                        }

                        if (filtro.size() > 0)
                        {
                            String estrada = json(filtro);

                            ArrayList<DataRoot> nuevas = filt(estrada);
                            ArrayList<DataRoot> entrantes = filt(estrada);

                            entrantes.retainAll(rir);

                            Collections.sort(entrantes, new MesaSorter());
                            if (swapper.getText().toString().equals("false"))
                            {
                                Collections.sort(rir, new DoubleSorter());
                            }
                            else
                            {
                                swapper.setText("false");
                            }



                            for (int i = 0; i < entrantes.size(); i++)
                            {
                                for(int j = 0; j < rir.size(); j++)
                                {
                                    if(!entrantes.get(i).equals(rir.get(j))) continue;

                                    int futuro = j + 1 ;

                                    if (futuro < rir.size())
                                    {
                                        if(entrantes.get(i).equals(rir.get(futuro))) continue;
                                    }

                                    boolean eshora = hora(entrantes.get(i).getFechaorden(),rir.get(j).getFechaorden() );

                                    if(eshora)
                                    {
                                        // int conmutador = rir.get(j).getIpb();
                                        //posiupdate.add(conmutador);
                                        ArrayList<Itemorder> opantalla = rir.get(j).getOrden();
                                        rir.get(j).setEstado_orden("pendiente");
                                        opantalla.addAll(entrantes.get(i).getOrden());
                                        myDB.updateComanda(rir.get(j));
                                        Collections.sort(rir, new DoubleIpbstado());
                                        adapter.notifyDataSetChanged();
                                        //adapter.notifyItemChanged(rir.get(j).getIpb());
                                        entrantes.remove(i);
                                        i = -1;
                                        break;
                                    }
                                    else
                                    {
                                        entrantes.get(i).setIpb(rir.size());
                                        rir.add(entrantes.get(i));
                                        //guardar.add(entrantes.get(i));
                                        myDB.addComanda(entrantes.get(i));
                                        Collections.sort(rir, new DoubleIpbstado());
                                        adapter.notifyItemInserted(rir.size() - 1);
                                        entrantes.remove(i);
                                        i = -1;
                                        break;
                                    }
                                }
                            }

                            nuevas.removeAll(rir);
                            for(DataRoot salvar: nuevas)
                            {
                                salvar.setIpb(rir.size());
                                rir.add(salvar);
                                myDB.addComanda(salvar);
                                //guardar.add(salvar);
                                Collections.sort(rir, new DoubleIpbstado());
                                adapter.notifyItemInserted(rir.size());
                            }

                            filtro.clear();
                            nuevas.clear();
                            entrantes.clear();
                        }
                        recibido.clear();
                    }
                }

                habtimertxt.setText("true");
            }
            @Override
            public void onFailure(@NonNull Call<ArrayList<DataRoot>> call, @NonNull Throwable t)
            {
                if(red)
                {
                    red = false;

                }
                mostrarMensaje("PROBLEMAS CON LA RED WIFI");
                Log.d("Error", t.getMessage());
                habtimertxt.setText("true");
                connect = t.getMessage();

            }
        });
    }

    @NonNull
    private Boolean hora(Date f1, Date f2)
    {
        Calendar datereci = Calendar.getInstance();
        Calendar datepantalla = Calendar.getInstance();
        Long diff;

        datereci.setTime(f1);
        long milireci = datereci.getTimeInMillis();
        datepantalla.setTime(f2);
        long milipantalla = datepantalla.getTimeInMillis();
        diff = milireci - milipantalla;

        return diff <= 59 * 1000;
    }

    private String json(ArrayList<DataRoot>filtro)
    {
        Gson gson = new Gson();
        return gson.toJson(filtro);
    }

    private ArrayList<DataRoot> filt(String json)
    {
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<DataRoot>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    @SuppressLint("SetTextI18n")
    private void setCantidad(ArrayList<DataRoot> cantidad) // PROCEDIMIENTO PARA COLOCAR CANTIDADES A LAS ETIQUETAS
    {

        ArrayList<DataRoot> limpo = new ArrayList<>();

        for (DataRoot limpiar : cantidad)
        {
            if(limpiar.getEstado_orden().equals("pendiente"))
            {
                limpo.add(limpiar);
            }
        }

        txt_comandas.setText(limpo.size() + "");

        contaentra = 0;
        contafondo = 0;
        contaposts = 0;

        for (DataRoot comanda: limpo)
        {
            for (Itemorder item : comanda.getOrden())
            {
                String tipo = item.getCategoria().toLowerCase();

                if ((tipo.equals("entradas") || tipo.equals("guarniciones")) && item.getEstadoitem().equals("pendiente"))
                {
                    contaentra++;
                }
                if ((tipo.equals("fondos") || tipo.equals("menú infantil")) && item.getEstadoitem().equals("pendiente"))
                {
                    contafondo++;
                }
                if (tipo.equals("postres") && item.getEstadoitem().equals("pendiente"))
                {
                    contaposts++;
                }
            }
        }

        txt_entracola.setText(contaentra  + "");
        txt_fondocola.setText(contafondo  + "");
        txt_postrecola.setText(contaposts + "");

    }

    private void deleteRetrofitComanda(String ida)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRequestComanda request = retrofit.create(IRequestComanda.class);

        Call<DataRoot> call = request.deleteComanda(ida);

        call.enqueue(new Callback<DataRoot>()
        {
            @Override
            public void onResponse(@NonNull Call<DataRoot> call, @NonNull Response<DataRoot> response)
            {
            }

            @Override
            public void onFailure(@NonNull Call<DataRoot> call, @NonNull Throwable t)
            {
                Log.d("Error " , "eliminando comanda");
            }

        });
    }

    private void mostrarMensaje(String mensaje)
    {
        final Toast toast = Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 200);
    }

    private void setAdapter()
    {
        adapter =  new MyAdapter(this, txt_comandas, rir, myDB, swapper);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        boolean haydata = myDB.listComandas().size() > 0;


        if(haydata)
        {
            ArrayList<DataRoot> rec = myDB.listComandas();

            for(DataRoot salvado : rec)
            {
                rir.add(salvado);
                adapter.notifyItemInserted(rec.indexOf(salvado));
            }

        }

        setCantidad(rir);
        Collections.sort(rir, new DoubleIpbstado());
        adapter.notifyDataSetChanged();
        gps.performClick();
    }

    private void stopRunnable()
    {

        mHandler.removeCallbacksAndMessages(null);

    }

    @SuppressLint("SetTextI18n")
    protected void Quit()
    {
        habtimertxt.setText("false");
        mostrarDialogo();
    }

    private void mostrarDialogo()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Deseas reiniciar la base de datos del DEMO?")
                .setTitle("Alerta")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener()  {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogos", "Confirmación Aceptada.");
                        baseborrado();
                        dialog.cancel();
                        stopRunnable();
                        finish();

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("Dialogos", "Confirmación Cancelada.");
                        dialog.cancel();
                        habtimertxt.setText("true");
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBackPressed()
    {
        // Do Here what ever you want do on back press;
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
/*        borrado++;

        if(borrado == 3)
        {
            habtimertxt.setText("false");
            mostrarDialogo("Alerta", "Deseas reiniciar la base de datos del DEMO?");
            borrado = 0;
        }*/
    }

    private void baseborrado()
    {
        ArrayList<DataRoot> borrado = myDB.listComandas();
        for (DataRoot borrar : borrado)
        {
            myDB.deleteComanda(borrar.get_id());

        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume()
    {
        super.onResume();
        UiChangeListener();
        //myDB.deleteAllComanda(); PARA ELIMINAR ERRORES EN LA BD INTERNA
        habilitado = myDB.listTablet().getNro_tablet() != null;
        if(habilitado)// SE ADIGNO NUMERO A LA BASE DE DATOS DEL TABLET
        {
            setAdapter();
            habtimertxt.setText("true");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onPause()
    {
        super.onPause();
        habtimertxt.setText("false");
        rir.clear();
        if(adapter != null)
            adapter.notifyDataSetChanged();
    }

    public void UiChangeListener()
    {
        final View decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener (new View.OnSystemUiVisibilityChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

                }
            }
        });
    }

}


/*    private void updateRetrofitTurno(String id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.iptab))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IRequestMensaje request = retrofit.create(IRequestMensaje.class);

        Turno turno = new Turno();
        turno.setActivo("2");

        Call<Turno> call = request.updateTurno(id, turno);

        call.enqueue(new Callback<Turno>()
        {
            @Override
            public void onResponse(@NonNull Call<Turno> call, @NonNull Response<Turno> response)
            {
                Log.i("Update turno", "Se Actualizo la turno Correctamente");
            }

            @Override
            public void onFailure(@NonNull Call<Turno> call, @NonNull Throwable t)
            {
                Log.d("Error actualizacion turno " , t.getMessage());
            }
        });
    }*/