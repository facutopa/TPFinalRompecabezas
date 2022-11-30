package com.progavanz2.tpfinal_rompecabezas;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class RegistrarGanador extends AppCompatActivity {

    private Button buttonRegistrar;
    private EditText etNom;
    private TextView txtViewPasos;
    private int pasos;
    private int dni;
    private EditText etDni;
    private int timer;
    private TextView txtViewTimer;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganador);

        pasos = getIntent().getIntExtra("Pasos",pasos);
        timer = getIntent().getIntExtra("Timer",timer);

        etNom= (EditText) findViewById(R.id.editTextNombre);
        etNom.setVisibility(View.VISIBLE);

        etDni= (EditText) findViewById(R.id.editTextDNI);
        etNom.setVisibility(View.VISIBLE);

        txtViewPasos = (TextView) findViewById(R.id.textView_ResultadoPasos);
        txtViewPasos.setVisibility(View.VISIBLE);
        txtViewPasos.setText(Integer.toString(pasos));

        txtViewTimer = (TextView) findViewById(R.id.textView_ResultadoTimer);
        txtViewTimer.setVisibility(View.VISIBLE);

        int seg = timer %60;
        int hor = timer / 3600;
        int min = (timer - hor *3600) /60;

        txtViewTimer.setText(String.format("%02d:%02d:%02d",hor, min, seg));

        buttonRegistrar = (Button) findViewById(R.id.buttonRegistrar);
        buttonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dni=(Integer.parseInt(etDni.getText().toString()));
                guardarPuntaje();
            }
        });


    }

    public void guardarPuntaje() {
        AdminDb admin= new AdminDb(this,null);
        SQLiteDatabase db= admin.getWritableDatabase();

        boolean loEncontro = admin.buscarYActualizar(dni, pasos, timer);

        if(loEncontro)
        {
            Toast.makeText(RegistrarGanador.this, "Score actualizado",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(RegistrarGanador.this, MainActivity.class);
            startActivity(i);

        }
        else
        {
            String usuario = etNom.getText().toString();
            if(!usuario.isEmpty())
            {

                ContentValues registro= new ContentValues();
                registro.put("dni", dni);
                registro.put("nombre", usuario);
                registro.put("pasos", pasos);
                registro.put("timer", timer);
                db.insert("ganadores", null, registro);
                db.close();

                Toast.makeText(RegistrarGanador.this, "Usuario agregado",Toast.LENGTH_LONG).show();

                Intent i = new Intent(RegistrarGanador.this, MainActivity.class);
                startActivity(i);//se sale
            }
            else
            {
                Toast.makeText(RegistrarGanador.this, "Debe ingresar un nombre de usuario",Toast.LENGTH_LONG).show();
            }
        }
    }
}
