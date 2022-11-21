package com.progavanz2.tpfinal_rompecabezas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnJugar;
    Button btnScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Boton para ir a Jugar
        btnJugar = (Button) findViewById(R.id.buttonJuga);
        btnJugar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Juego.class);
                startActivity(intent);
            }
        });

        btnScores= (Button) findViewById(R.id.buttonScore);
        btnScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, Scores.class);
                startActivity(intent2);
            }
        });
    }
}