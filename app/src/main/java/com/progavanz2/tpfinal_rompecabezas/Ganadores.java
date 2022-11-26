package com.progavanz2.tpfinal_rompecabezas;


public class Ganadores
{
    private String nombre;
    private int dni;
    private int pasos;
    private String timer;

    public Ganadores(int dni, String n, int p, String t)
    {
        this.dni=dni;
        this.nombre=n;
        this.pasos = p;
        this.timer = t;
    }

    public int getPasos() {
        return pasos;
    }

    public String getNombre(){
        return nombre;
    }

    public int getDni() {
        return dni;
    }

    public String getTimer(){return timer;}
}
