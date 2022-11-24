package com.progavanz2.tpfinal_rompecabezas;

public class Ganadores
{
    private int intentos;
    private String nombre;
    private int dni;

    public Ganadores(int dni, String n, int p)
    {
        intentos=p;
        nombre=n;
        this.dni=dni;
    }

    public int getScore() {
        return intentos;
    }

    public String getNombre(){
        return nombre;
    }

    public int getDni() {
        return dni;
    }
}
