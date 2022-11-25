package com.progavanz2.tpfinal_rompecabezas;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

public class Juego extends AppCompatActivity
{
    private int vacioX=2;
    private int vacioY=2;
    private RelativeLayout grupo;
    private Button[][] botones;
    private int[] tiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        cargarVista();
        cargarNumeros();
        generaNumeros();
        cargarDataAVistas();
    }

    private void cargarDataAVistas(){
        vacioX = 2;
        vacioY = 2;
        for (int i = 0; i < grupo.getChildCount()-1; i++)
        {
            botones[i/4][i%4].setText(String.valueOf(tiles[i]));
            botones[i/4][i%4].setBackgroundResource(android.R.drawable.btn_default);
        }
        botones[vacioX][vacioY].setText("");
        botones[vacioX][vacioY].setBackgroundColor(ContextCompat.getColor(this, R.color.white));
    }

    private void generaNumeros(){
        int n=15;
        Random random = new Random();
        while(n>1){
            int randomNum = random.nextInt(n--);
            int temp = tiles[randomNum];
            tiles[randomNum] = tiles[n];
            tiles[n] = temp;
        }
        if(!esSolucionable())
            generaNumeros();
    }

    private boolean esSolucionable(){
        int cuentoInversiones = 0;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < i; j++)
            {
                if(tiles[j]>tiles[i])
                    cuentoInversiones++;

            }
        }
        return cuentoInversiones%2 == 0;
    }

    private void cargarNumeros(){
        tiles = new int[16];
        for (int i = 0; i < grupo.getChildCount()-1; i++)
        {
            tiles[i] = i+1;

            
        }

    }

    private void cargarVista(){
        grupo = findViewById(R.id.grupo);
        botones = new Button[4][4];

        for (int i = 0; i<grupo.getChildCount();i++){
            botones[i/4][i%4] = (Button) grupo.getChildAt(i);
        }
    }

    public void buttonClick(View view){
        Button button = (Button) view;
        int x = button.getTag().toString().charAt(0)-'0';
        int y = button.getTag().toString().charAt(1)-'0';

        if((Math.abs(vacioX-x)==1 && vacioY==y)||(Math.abs(vacioY-y)==1&&vacioX==x)){
            botones[vacioX][vacioY].setText(button.getText().toString());
            botones[vacioX][vacioY].setBackgroundResource(android.R.drawable.btn_default);
            button.setText("");
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
            vacioX = x;
            vacioY = y;
            chequeaVictoria();
        }
    }

    private void chequeaVictoria(){
        boolean esGanador = false;
        if (vacioX == 3 && vacioY == 3)
        {
            for (int i = 0; i < grupo.getChildCount(); i++)
            {
                if(botones[i/4][i%4].getText().toString().equals(String.valueOf(i+1))){
                    esGanador = true;
                }else
                {
                    esGanador = false;
                    break;
                }



            }
        }
        if (esGanador){
            Toast.makeText(this, "Ganaste!", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < grupo.getChildCount(); i++)
            {
                botones[i/4][i%4].setClickable(false);
            }
        }
    }


}
