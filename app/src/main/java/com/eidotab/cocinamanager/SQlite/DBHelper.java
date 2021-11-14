package com.eidotab.cocinamanager.SQlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.eidotab.cocinamanager.Model.DataRoot;
import com.eidotab.cocinamanager.Model.Tablet;
import com.eidotab.cocinamanager.Model.Turno;
import com.google.gson.Gson;
import java.io.File;
import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "basecocinabeta00.db";
    private static final int DATABASE_VERSION = 3;
    private String DB_PATH = null;

    public static DBHelper GetDBHelper(Context context)
    {
        DBHelper dbHelper = new DBHelper(context.getApplicationContext());

        if (!dbHelper.isDataBaseExist())
        {
            dbHelper.deleteAllComanda();
            dbHelper.deleteAllTablet();
            dbHelper.createDataBase();
            dbHelper.deleteAllTurno();
        }
        return dbHelper;
    }

    private DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
    }

    private void createDataBase()
    {
        boolean isExist = isDataBaseExist();

        if (!isExist)
        {
            this.getReadableDatabase();

            onCreate(this.getWritableDatabase());
        }
    }

    private boolean isDataBaseExist()
    {
        File file = new File(DB_PATH + DATABASE_NAME);

        return file.exists();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // Escribir la estructura de la bd: Tablas, ...
        db.execSQL(" CREATE TABLE comanda (_id TEXT primary key, jcomanda  TEXT); ");
        db.execSQL(" CREATE TABLE tablet  (nrtab TEXT primary key, jtablet TEXT); ");
        db.execSQL(" CREATE TABLE turno (_id TEXT primary key, jturno TEXT); ");
        
        // ....
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Escribir las modificaciones en la bd.
        db.execSQL(" DROP TABLE IF EXISTS comanda; ");
        db.execSQL(" DROP TABLE IF EXISTS tablet; ");
        db.execSQL(" DROP TABLE IF EXISTS turno; ");
        onCreate(db);
    }


    /* IMPLEMENTACIÓN: MÉTODOS CRUD */

    /* TABLA: comanda */

    private static final String TABLE_NAME_COMANDA = "comanda";

    private static final String TABLE_NAME_TABLET = "tablet";

    private static final String TABLE_NAME_TURNO = "turno";

    public boolean addComanda(DataRoot comanda)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(comanda);
        contentValues.put("_id", comanda.get_id());
        contentValues.put("jcomanda", json);
        db.insert(TABLE_NAME_COMANDA, null, contentValues);

        return true;
    }

    public boolean addTurno(Turno turno)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(turno);
        contentValues.put("_id", turno.getId());
        contentValues.put("jturno", json);
        db.insert(TABLE_NAME_TURNO, null, contentValues);
        return true;
    }



    public boolean addTablet(Tablet tablet)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(tablet);
        contentValues.put("nrtab", tablet.getNro_tablet());
        contentValues.put("jtablet", json);
        db.insert(TABLE_NAME_TABLET, null, contentValues);
        return true;
    }


    public boolean updateComanda(DataRoot comanda)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(comanda);
        contentValues.put("_id", comanda.get_id());
        contentValues.put("jcomanda", json);
        db.update(TABLE_NAME_COMANDA, contentValues, "_id = ?",
                new String[]{ comanda.get_id() });

        return true;
    }


/*
    public boolean updateTablet(Tablet tablet)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Gson gson = new Gson();
        String json = gson.toJson(tablet);
        contentValues.put("nrtab", tablet.getNro_tablet());
        contentValues.put("jtablet", json);
        db.update(TABLE_NAME_TABLET, contentValues, "nrtab = ?",
                new String[]{ tablet.getNro_tablet() });

        return true;
    }*/


    public boolean deleteComanda(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_COMANDA, "_id = ?",
                new String[]{ id });

        return true;
    }


/*
    public boolean deleteTablet(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_TABLET, "nrtab = ?",
                new String[]{ id });

        return true;
    }
*/

    private boolean deleteAllComanda()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_COMANDA, "",
                new String[]{  });

        return true;
    }


    private boolean deleteAllTablet()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_TABLET, "",
                new String[]{  });

        return true;
    }

    public boolean deleteAllTurno()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME_TURNO, "",
                new String[]{  });

        return true;
    }

/*    public DataRoot getComanda(String id)
    {
        DataRoot comanda = new DataRoot();

        SQLiteDatabase db = this.getReadableDatabase();

        Gson gson = new Gson();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_COMANDA + " WHERE _id = '" + id + "'", null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {

            String sacadata = (cursor.getString(cursor.getColumnIndex("jcomanda")));

            comanda  = gson.fromJson(sacadata, DataRoot.class);

            cursor.moveToNext();
        }
        cursor.close();
        return comanda;
    }*/


   /* public Tablet getTablet(String nutab)
    {
        Tablet tablet = new Tablet();

        SQLiteDatabase db = this.getReadableDatabase();

        Gson gson = new Gson();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_TABLET + " WHERE nrtab= '" + nutab + "'", null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {

            String sacadata = (cursor.getString(cursor.getColumnIndex("jtablet")));

            tablet = gson.fromJson(sacadata, Tablet.class);

            cursor.moveToNext();
        }
        cursor.close();
        return tablet;
    }*/

    public ArrayList<DataRoot> listComandas()
    {
        ArrayList<DataRoot> lista = new ArrayList<>();

        //DataRoot comanda;

        Gson gson = new Gson();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_COMANDA, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            DataRoot comandas;

            String sacadata = (cursor.getString(cursor.getColumnIndex("jcomanda")));

            comandas  = gson.fromJson(sacadata, DataRoot.class);

            lista.add(comandas);

            cursor.moveToNext();
        }

        cursor.close();
        return lista;
    }

    public Turno listTurno()
    {
        Turno turno = new Turno();

        Turno turn;

        Gson gson = new Gson();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_TURNO, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            String sacadata = (cursor.getString(cursor.getColumnIndex("jturno")));

            turn  = gson.fromJson(sacadata, Turno.class);

            turno = (turn);

            cursor.moveToNext();
        }

        cursor.close();
        return turno;
    }




    public Tablet listTablet()
    {
        Tablet tablet = new Tablet();

        Tablet tabla;

        Gson gson = new Gson();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(" SELECT * FROM " + TABLE_NAME_TABLET, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast())
        {
            //tabla = new Tablet();

            String sacadata = (cursor.getString(cursor.getColumnIndex("jtablet")));

            tabla  = gson.fromJson(sacadata, Tablet.class);

            tablet = (tabla);

            cursor.moveToNext();
        }
        cursor.close();
        return tablet;
    }


}
