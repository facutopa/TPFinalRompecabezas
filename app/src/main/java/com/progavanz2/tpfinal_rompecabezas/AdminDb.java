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
        db.execSQL("Create table ganadores (dni integer primary key, nombre text, intentos int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int version, int version2) {
        db.execSQL("drop table if exists ganadores");
        db.execSQL("Create table ganadores (dni integer primary key, nombre text, intentos integer)");
    }


    public Cursor llenarLista(){
        ArrayList<String> lista= new ArrayList<>();
        SQLiteDatabase database= this.getWritableDatabase();
        Cursor data=database.rawQuery("SELECT * FROM ganadores ORDER BY intentos DESC", null);
        return data;

    }

    public boolean buscarYActualizar(int d, int nuevoIntento)
    {
        ContentValues registro= new ContentValues();
        SQLiteDatabase db= this.getWritableDatabase();
        boolean loEncontro=false;
        String q="SELECT intentos FROM ganadores WHERE dni="+"'"+d+"'";
        Cursor cursor= db.rawQuery(q,null);
        if(cursor.moveToFirst()) {

            int intentosGuardados= cursor.getInt(0);

            if (nuevoIntento<=intentosGuardados)
            {
                db.execSQL("UPDATE ganadores SET intentos="+nuevoIntento+" WHERE dni="+"'"+d+"'");
                db.close();
            }

            loEncontro=true;
        }
        return loEncontro;
    }



}
