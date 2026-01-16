package com.mx_curso;
public class MiPila {
    private int[] datos;
    private int top;

    public MiPila(int capacidad) {
        datos = new int[capacidad];
        top = -1;
    }
    public void push(int x) {
        if (top < datos.length - 1) datos[++top] = x;
    }
    public int pop() {
        if (top >= 0) return datos[top--];
        return -1;
    }
}
