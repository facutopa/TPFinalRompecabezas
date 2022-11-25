package com.progavanz2.tpfinal_rompecabezas;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import java.util.Random;

public class Juego extends AppCompatActivity
{
    private int vacioX=3;
    private int vacioY=3;
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
        vacioX = 3;
        vacioY = 3;
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


}
