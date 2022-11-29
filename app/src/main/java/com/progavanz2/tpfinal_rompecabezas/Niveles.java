package com.progavanz2.tpfinal_rompecabezas;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Niveles extends AppCompatActivity
{
    private Button btnJugar;
    private RadioGroup grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        grupo = (RadioGroup) findViewById(R.id.radioGroupNiveles);

        //Boton para ir a Jugar
        btnJugar = (Button) findViewById(R.id.botonJugar);
        btnJugar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if(grupo.getCheckedRadioButtonId() == R.id.radioButton_facil)
                {
                    Intent intent = new Intent(Niveles.this, Juego.class);
                    intent.putExtra("Nivel", 0);
                    startActivity(intent);
                }
                else
                {
                    if(grupo.getCheckedRadioButtonId() == R.id.radioButton_medio)
                    {
                        Intent intent = new Intent(Niveles.this, Juego.class);
                        intent.putExtra("Nivel", 1);
                        startActivity(intent);
                    }
                    else
                    {
                        if(grupo.getCheckedRadioButtonId() == R.id.radioButton_dificil)
                        {
                            Intent intent = new Intent(Niveles.this, Juego.class);
                            intent.putExtra("Nivel", 2);
                            startActivity(intent);
                        }
                        else
                        {
                                Intent intent = new Intent(Niveles.this, Juego.class);
                                intent.putExtra("Nivel", 3);
                                startActivity(intent);
                        }
                    }
                }


            }
        });
    }

}
