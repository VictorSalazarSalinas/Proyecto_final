package com.mx_curso;

import javax.swing.*;
import java.awt.*;

public class PanelBusqueda extends JPanel {
    JTextArea txtInfo;
    LienzoGrafica panelGrafica; // Agregamos la gráfica
    JButton btnEscalabilidad;

    public PanelBusqueda() {
        setLayout(new BorderLayout());

        btnEscalabilidad = new JButton("Comparar Escalabilidad (10^3 a 10^5)");
        add(btnEscalabilidad, BorderLayout.NORTH);

        panelGrafica = new LienzoGrafica();
        add(panelGrafica, BorderLayout.CENTER);

        txtInfo = new JTextArea(6, 40);
        txtInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(txtInfo), BorderLayout.SOUTH);

        btnEscalabilidad.addActionListener(e -> {
            txtInfo.setText("Iniciando prueba de búsqueda...\n");
            new Thread(() -> {
                int[] tamanios = {1000,5000, 10000, 100000};
                long[] tSecuencial = new long[tamanios.length];
                long[] tBinaria = new long[tamanios.length];
                String[] etiquetas = new String[tamanios.length];

                for (int i = 0; i < tamanios.length; i++) {
                    int N = tamanios[i];
                    etiquetas[i] = "10^" + (int)Math.log10(N);
                    int[] datos = GeneradorDatos.generarOrdenado(N);
                    int objetivo = -1; // Peor caso: elemento no existe

                    // Medir Secuencial
                    long inicio = System.nanoTime();
                    Algoritmos.busquedaSecuencial(datos, objetivo);
                    tSecuencial[i] = System.nanoTime() - inicio;

                    // Medir Binaria
                    inicio = System.nanoTime();
                    Algoritmos.busquedaBinaria(datos, objetivo);
                    tBinaria[i] = System.nanoTime() - inicio;

                    txtInfo.append("N=" + N + " | Secuencial: " + tSecuencial[i] + "ns | Binaria: " + tBinaria[i] + "ns\n");

                    // Actualizar gráfica tipo LINEAS
                    panelGrafica.setDatos(tSecuencial, tBinaria, etiquetas, "LINEAS", "Secuencial", "Binaria");
                }
            }).start();
        });
    }
}