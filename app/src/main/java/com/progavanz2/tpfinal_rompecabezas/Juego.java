package com.progavanz2.tpfinal_rompecabezas;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Juego extends AppCompatActivity
{
    private int vacioX=2;
    private int vacioY=2;
    private RelativeLayout grupo;
    private Button[][] botones;
    private int[] tiles;
    private TextView textoViewPasos;
    private int contadorPasos=0;
    private TextView textoViewTimer;
    private Timer timer;
    private int contadorTimer = 0;
    private Button botonReinicio;
    private boolean correElTiempo;
    private Button botonRegistroGanador;


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
            botones[i/3][i%3].setText(String.valueOf(tiles[i]));
            botones[i/3][i%3].setBackgroundResource(android.R.drawable.btn_default);
        }
        botones[vacioX][vacioY].setText("");
        botones[vacioX][vacioY].setBackgroundColor(ContextCompat.getColor(this, R.color.white));
    }

    private void generaNumeros(){
        int n=8;
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
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < i; j++)
            {
                if(tiles[j]>tiles[i])
                    cuentoInversiones++;

            }
        }
        return cuentoInversiones%2 == 0;
    }

    private void cargarNumeros(){
        tiles = new int[9];
        for (int i = 0; i < grupo.getChildCount()-1; i++)
        {
            tiles[i] = i+1;

            
        }

    }
    private void cargoTimer(){
        correElTiempo = true;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run()
            {
                contadorTimer++;
                setTime(contadorTimer);

            }
        }, 1000, 1000);
    }

    private void setTime(int contadorTimer)
    {
        int seg = contadorTimer %60;
        int hor = contadorTimer / 3600;
        int min = (contadorTimer - hor *3600) /60;

        textoViewTimer.setText(String.format("Tiempo: %02d:%02d:%02d",hor, min, seg));
    }

    private void cargarVista(){
        grupo = findViewById(R.id.grupo);
        textoViewPasos = findViewById(R.id.textView_Pasos);
        textoViewTimer = findViewById(R.id.textView_Timer);
        botonReinicio = findViewById(R.id.botonReinicio);

        cargoTimer();
        botones = new Button[3][3];

        for (int i = 0; i<grupo.getChildCount();i++){
            botones[i/3][i%3] = (Button) grupo.getChildAt(i);
        }

        botonReinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (correElTiempo)
                {
                    timer.cancel();
                    botonReinicio.setText("Reanudar");
                    correElTiempo = false;
                    for (int i = 0; i < grupo.getChildCount(); i++) {
                        botones[i / 3][i % 3].setClickable(false);
                    }
                }else
                    {
                        cargoTimer();
                        botonReinicio.setText("Parar tiempo");
                        for (int i = 0; i < grupo.getChildCount(); i++) {
                            botones[i / 3][i % 3].setClickable(true);
                        }

                    }
            }
        });
    }

    public void clickBoton(View view){
        Button button = (Button) view;
        int x = button.getTag().toString().charAt(0)-'0';
        int y = button.getTag().toString().charAt(1)-'0';

        if((Math.abs(vacioX-x)==1 && vacioY==y)||(Math.abs(vacioY-y)==1&&vacioX==x)){
            botones[vacioX][vacioY].setText(button.getText().toString());
            botones[vacioX][vacioY].setBackgroundResource(android.R.drawable.btn_default);
            button.setText("");
            vacioX = x;
            vacioY = y;
            contadorPasos++;
            textoViewPasos.setText("Pasos: "+contadorPasos);
            chequeaVictoria();
        }
    }

    private void chequeaVictoria(){
        boolean esGanador = false;
        if (vacioX == 2 && vacioY == 2)
        {
            for (int i = 0; i < grupo.getChildCount()-1; i++)
            {
                if(botones[i/3][i%3].getText().toString().equals(String.valueOf(i+1))){
                    esGanador = true;
                }else
                {
                    esGanador = false;
                    break;
                }
            }
        }
        if (esGanador){
            Toast.makeText(Juego.this, "Ganaste!\nPasos: "+contadorPasos, Toast.LENGTH_LONG).show();
            for (int i = 0; i < grupo.getChildCount(); i++)
            {
                botones[i/3][i%3].setClickable(false);
            }
            timer.cancel();
            botonReinicio.setClickable(false);
        }
    }


}
