package com.progavanz2.tpfinal_rompecabezas;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class Scores extends AppCompatActivity
{
    Button btnVolverPrincipal;
    ArrayList<Ganadores> puntajeList;
    Cursor data;
    Ganadores punt;
    TwoColumn_ListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        ListView lv = (ListView) findViewById(R.id.listaPunt);

        ArrayList<Scores> list;
        AdminDb admin= new AdminDb(this, null);
        puntajeList = new ArrayList<>();

        data = admin.llenarLista();  // crear getListContents

        int numRows = data.getCount();
        if (numRows == 0) {
            Toast.makeText(this, "No hay datos cargados", Toast.LENGTH_LONG).show();
        } else {
            int i = 0;
            while (data.moveToNext()) {
                punt = new Ganadores(data.getInt(0), data.getString(1), data.getInt(2), data.getString(3));
                puntajeList.add(i, punt);
            }
            if (!puntajeList.isEmpty()) {
                adapter = new TwoColumn_ListAdapter(this, R.layout.single_item, puntajeList);

                lv.setAdapter(adapter);

            }
        }
    }
}