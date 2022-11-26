package com.progavanz2.tpfinal_rompecabezas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class AdminDb  extends SQLiteOpenHelper{

    Context context;
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME = "Rompecabezas-Final.db";

    public AdminDb(@Nullable Context context, SQLiteDatabase.CursorFactory factory ) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("Create table ganadores (dni integer primary key, nombre text, pasos int, timer int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int version, int version2) {
        db.execSQL("drop table if exists ganadores");
        db.execSQL("Create table ganadores (dni integer primary key, nombre text, pasos integer, timer integer)");
    }


    public Cursor llenarLista(){
        ArrayList<String> lista= new ArrayList<>();
        SQLiteDatabase database= this.getWritableDatabase();
        Cursor data=database.rawQuery("SELECT * FROM ganadores ORDER BY pasos DESC", null);
        return data;

    }

    public boolean buscarYActualizar(int d, int nuevoPasos, int nuevoTimer)
    {
        ContentValues registro= new ContentValues();
        SQLiteDatabase db= this.getWritableDatabase();
        boolean loEncontro=false;
        String q="SELECT pasos, timer FROM ganadores WHERE dni="+"'"+d+"'";
        Cursor cursor= db.rawQuery(q,null);
        if(cursor.moveToFirst()) {

            int pasosGuardados= cursor.getInt(0);

            if (nuevoPasos<=pasosGuardados)
            {
                db.execSQL("UPDATE ganadores SET pasos="+nuevoPasos+", timer="+nuevoTimer+" WHERE dni="+"'"+d+"'");
                db.close();
            }

            loEncontro=true;
        }
        return loEncontro;
    }



}
