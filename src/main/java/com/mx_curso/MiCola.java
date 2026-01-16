package com.mx_curso;

public class MiCola {
    private int[] datos;
    private int frente, finalCola, tamaño;

    public MiCola(int capacidad) {
        datos = new int[capacidad];
        frente = 0;
        tamaño = 0;
        finalCola = -1;
    }
    public void enqueue(int item) {
        if (tamaño < datos.length) {
            finalCola = (finalCola + 1) % datos.length;
            datos[finalCola] = item;
            tamaño++;
        }
    }
    public int dequeue() {
        if (tamaño > 0) {
            int item = datos[frente];
            frente = (frente + 1) % datos.length;
            tamaño--;
            return item;
        }
        return -1;
    }
}