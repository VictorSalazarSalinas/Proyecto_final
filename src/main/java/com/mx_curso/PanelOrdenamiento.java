package com.mx_curso;

import javax.swing.*;
import java.awt.*;

public class PanelOrdenamiento extends JPanel {
    JTextArea txtResultados;
    LienzoGrafica panelGrafica;
    JButton btnCompararEscenarios, btnEscalabilidad;

    public PanelOrdenamiento() {
        setLayout(new BorderLayout());

        JPanel controles = new JPanel();
        btnCompararEscenarios = new JButton("1. Comparar Escenarios (N fijo)");
        btnEscalabilidad = new JButton("2. Ver Escalabilidad (Crecimiento)");
        controles.add(btnCompararEscenarios);
        controles.add(btnEscalabilidad);
        add(controles, BorderLayout.NORTH);

        txtResultados = new JTextArea(5, 40);
        add(new JScrollPane(txtResultados), BorderLayout.SOUTH);

        panelGrafica = new LienzoGrafica();
        add(panelGrafica, BorderLayout.CENTER);

        btnCompararEscenarios.addActionListener(e -> {
            txtResultados.setText("Ejecutando comparación de escenarios con N=10,000...\n");
            new Thread(() -> {
                int N = 10000;
                long[] tiemposBurbuja = new long[3];
                long[] tiemposQuick = new long[3];

                int[] datos = GeneradorDatos.generarOrdenado(N);
                tiemposBurbuja[0] = medirBurbuja(datos.clone());
                tiemposQuick[0] = medirQuick(datos.clone());

                datos = GeneradorDatos.generarAleatorio(N);
                tiemposBurbuja[1] = medirBurbuja(datos.clone());
                tiemposQuick[1] = medirQuick(datos.clone());

                datos = GeneradorDatos.generarInverso(N);
                tiemposBurbuja[2] = medirBurbuja(datos.clone());
                tiemposQuick[2] = medirQuick(datos.clone());

                actualizarTexto(tiemposBurbuja, tiemposQuick, new String[]{"Mejor", "Prom", "Peor"});
                panelGrafica.setDatos(tiemposBurbuja, tiemposQuick, new String[]{"Mejor", "Promedio", "Peor"}, "BARRAS", "Burbuja", "QuickSort");
            }).start();
        });

        btnEscalabilidad.addActionListener(e -> {
            txtResultados.setText("Ejecutando prueba de escalabilidad...\n");
            new Thread(() -> {
                int[] tamanios = {1000, 5000, 10000, 20000};
                long[] tBurbuja = new long[tamanios.length];
                long[] tQuick = new long[tamanios.length];
                String[] etiquetas = new String[tamanios.length];

                for(int i=0; i<tamanios.length; i++) {
                    int N = tamanios[i];
                    etiquetas[i] = "N=" + N;
                    int[] datos = GeneradorDatos.generarAleatorio(N);
                    tBurbuja[i] = medirBurbuja(datos.clone());
                    tQuick[i] = medirQuick(datos.clone());
                }
                panelGrafica.setDatos(tBurbuja, tQuick, etiquetas, "LINEAS", "Burbuja", "QuickSort");
            }).start();
        });
    }

    private long medirBurbuja(int[] arr) {
        long inicio = System.nanoTime();
        Algoritmos.bubbleSort(arr);
        return System.nanoTime() - inicio;
    }

    private long medirQuick(int[] arr) {
        long inicio = System.nanoTime();
        Algoritmos.quickSort(arr, 0, arr.length - 1);
        return System.nanoTime() - inicio;
    }

    private void actualizarTexto(long[] t1, long[] t2, String[] casos) {
        StringBuilder sb = new StringBuilder("Resultados (nanosegundos):\n");
        for(int i=0; i<3; i++) {
            sb.append(casos[i]).append(" -> Burbuja: ").append(t1[i]).append(" | QuickSort: ").append(t2[i]).append("\n");
        }
        txtResultados.append(sb.toString());
    }
}