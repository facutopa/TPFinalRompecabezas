package com.progavanz2.tpfinal_rompecabezas;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
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
    private int niveles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        niveles = getIntent().getIntExtra("Nivel",niveles);

        cargarVista();
        cargarNumeros();
        generaNumeros();
        cargarDataAVistas();
    }

    private void cargarVista(){
        grupo = findViewById(R.id.grupo);
        textoViewPasos = findViewById(R.id.textView_Pasos);
        textoViewTimer = findViewById(R.id.textView_Timer);
        botonReinicio = findViewById(R.id.botonReinicio);

        botonRegistroGanador = (Button) findViewById(R.id.botonRegistro);
        botonRegistroGanador.setVisibility(View.INVISIBLE);

        cargoTimer();
        botones = new Button[3][3];

        for (int i = 0; i<grupo.getChildCount();i++){ //getChildCount retorna la cantidad de, en este caso, botones = 9
            botones[i/3][i%3] = (Button) grupo.getChildAt(i); //getChildAt retorna la posicion en el grupo para cada boton
        }

        botonReinicio.setOnClickListener(new View.OnClickListener() { //determino la funcion del boton Reinicio
            @Override
            public void onClick(View v) {
                if (correElTiempo) //si esta corriendo el tiempo, lo detiene e inhabilita seguir moviendo piezas
                {
                    timer.cancel();
                    botonReinicio.setText("Reanudar");
                    correElTiempo = false;
                    for (int i = 0; i < grupo.getChildCount(); i++) {
                        botones[i / 3][i % 3].setClickable(false);
                    }
                }else
                {
                    cargoTimer(); //si el timer esta detenido, lo reanuda para continuar
                    botonReinicio.setText("Parar tiempo");
                    for (int i = 0; i < grupo.getChildCount(); i++) {
                        botones[i / 3][i % 3].setClickable(true);
                    }

                }
            }
        });
    }

    private void cargarNumeros(){ //cargo los numeros de cada boton a excepcion del ultimo, al tener 9 botones, lo hago para 8.
        tiles = new int[9];
        for (int i = 0; i < grupo.getChildCount()-1; i++)
        {
            tiles[i] = i+1;
        }

    }

    private void generaNumeros(){ //Carga de manera aleatoria con la clase Random el orden inicial de los numeros en el array de tiles
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

    private void cargarDataAVistas(){
        vacioX = 2;
        vacioY = 2;
        //Ciclo que llena los botones con los datos generados previamente en los metodos (cargaNumero y generarNumeros)
        //En el array de tiles se guardan los numeros generados aleatoriamente
        //Con dicho array completo los botones.
        for (int i = 0; i < grupo.getChildCount()-1; i++)
        {
            botones[i/3][i%3].setText(String.valueOf(tiles[i]));
            botones[i/3][i%3].setBackgroundResource(android.R.drawable.btn_default);
        }
        botones[vacioX][vacioY].setText("");
        botones[vacioX][vacioY].setBackgroundColor(ContextCompat.getColor(this, R.color.white));
    }



    private boolean esSolucionable(){
        //la teoria indica que "No es posible resolver una instancia de 8 tiles si el número de inversiones es impar en el estado de entrada."
        //que es una inversion? Un par de fichas forman una inversión si los valores de las fichas están en orden inverso al de su aparición en el estado objetivo.
        //lo que hacemos en la lógica es verificar el orden y en base a eso definimos si suma una inversion o no entre 2 botones.
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


    private void cargoTimer(){ //Cargo el Timer para iniciar el conteo
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

    private void setTime(int contTimer) //Seteo el timer para mostrarlo de manera amigable en un formato HH:MM:SS
    {
        int seg = contTimer %60;
        int hor = contTimer / 3600;
        int min = (contTimer - hor *3600) /60;

        textoViewTimer.setText(String.format("Tiempo: %02d:%02d:%02d",hor, min, seg));
    }



    public void clickBoton(View view){
        Button button = (Button) view;
        int x = button.getTag().toString().charAt(0)-'0';
        int y = button.getTag().toString().charAt(1)-'0';

        int hor = contadorTimer / 3600;
        int min = (contadorTimer - hor *3600) /60;


        if(niveles == 1 && min>=5)
        {
            timer.cancel();
            botonReinicio.setClickable(false);
            Toast.makeText(Juego.this, "Perdiste! Superaste el tiempo de la dificultad Media.", Toast.LENGTH_LONG).show();
            for (int i = 0; i < grupo.getChildCount(); i++)
            {
                botones[i/3][i%3].setClickable(false);
            }

        }
        else
        {
            if(niveles == 2 && min>=3)
            {
                timer.cancel();
                botonReinicio.setClickable(false);
                Toast.makeText(Juego.this, "Perdiste! Superaste el tiempo de la dificultad Dificil", Toast.LENGTH_LONG).show();
                for (int i = 0; i < grupo.getChildCount(); i++)
                {
                    botones[i/3][i%3].setClickable(false);
                }

            }
            else
            {
                if(niveles == 3 && min>=1)
                {
                    timer.cancel();
                    botonReinicio.setClickable(false);
                    Toast.makeText(Juego.this, "Perdiste! Superaste el tiempo de la dificultad Super Dificil", Toast.LENGTH_LONG).show();
                    for (int i = 0; i < grupo.getChildCount(); i++)
                    {
                        botones[i/3][i%3].setClickable(false);
                    }

                }
                else
                {
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
            }
        }
    }

    private void chequeaVictoria(){
        boolean esGanador = false;
        if (vacioX == 2 && vacioY == 2)
        {
            for (int i = 0; i < grupo.getChildCount()-1; i++)
            { //recorre los botones y compara la matriz de botones con i+1 (inicia en 1)
                if(botones[i/3][i%3].getText().toString().equals(String.valueOf(i+1)))
                {
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
            botonRegistroGanador.setVisibility(View.VISIBLE);

            botonRegistroGanador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(Juego.this, RegistrarGanador.class);
                    intent.putExtra("Pasos", contadorPasos);
                    intent.putExtra("Timer", contadorTimer);
                    startActivity(intent);
                }
            });
        }
    }


}
