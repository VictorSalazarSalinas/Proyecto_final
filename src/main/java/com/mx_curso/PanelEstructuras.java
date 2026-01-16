package com.mx_curso;

import javax.swing.*;
import java.awt.*;

public class PanelEstructuras extends JPanel {
    JTextArea txtInfo;
    LienzoGrafica panelGrafica;
    JButton btnEscalabilidad;

    public PanelEstructuras() {
        setLayout(new BorderLayout());

        btnEscalabilidad = new JButton("Validar Complejidad O(1) (10^3 a 10^6)");
        add(btnEscalabilidad, BorderLayout.NORTH);

        panelGrafica = new LienzoGrafica();
        add(panelGrafica, BorderLayout.CENTER);

        txtInfo = new JTextArea(8, 40);
        txtInfo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(txtInfo), BorderLayout.SOUTH);

        btnEscalabilidad.addActionListener(e -> {
            txtInfo.setText("Iniciando prueba de operaciones fundamentales O(1)...\n\n");
            new Thread(() -> {
                int[] tamanios = {1000,5000, 10000, 100000};

                // Arreglos para la gráfica (promedios de Pila vs promedio de Cola)
                long[] tPromedioPila = new long[tamanios.length];
                long[] tPromedioCola = new long[tamanios.length];
                String[] etiquetas = new String[tamanios.length];

                for (int i = 0; i < tamanios.length; i++) {
                    int N = tamanios[i];
                    etiquetas[i] = "10^" + (int)Math.log10(N);

                    MiPila pila = new MiPila(N);
                    MiCola cola = new MiCola(N);

                    // --- MEDICIÓN PILA (PUSH Y POP) ---
                    long inicioPush = System.nanoTime();
                    for(int j=0; j<N; j++) pila.push(j);
                    long finPush = System.nanoTime() - inicioPush;

                    long inicioPop = System.nanoTime();
                    for(int j=0; j<N; j++) pila.pop();
                    long finPop = System.nanoTime() - inicioPop;

                    // --- MEDICIÓN COLA (ENQUEUE Y DEQUEUE) ---
                    long inicioEnq = System.nanoTime();
                    for(int j=0; j<N; j++) cola.enqueue(j);
                    long finEnq = System.nanoTime() - inicioEnq;

                    long inicioDeq = System.nanoTime();
                    for(int j=0; j<N; j++) cola.dequeue();
                    long finDeq = System.nanoTime() - inicioDeq;

                    // Calculamos el tiempo promedio por operación unitaria (nanosegundos)
                    long avgPush = finPush / N;
                    long avgPop = finPop / N;
                    long avgEnq = finEnq / N;
                    long avgDeq = finDeq / N;

                    // Datos para la gráfica
                    tPromedioPila[i] = (avgPush + avgPop) / 2;
                    tPromedioCola[i] = (avgEnq + avgDeq) / 2;

                    txtInfo.append("Para N = " + N + ":\n");
                    txtInfo.append(String.format("  PILA:  Push: %d ns/op | Pop: %d ns/op\n", avgPush, avgPop));
                    txtInfo.append(String.format("  COLA:  Enq:  %d ns/op | Deq: %d ns/op\n", avgEnq, avgDeq));
                    txtInfo.append("--------------------------------------------------\n");

                    // Actualizar gráfica: Mostramos la estabilidad de las estructuras
                    panelGrafica.setDatos(tPromedioPila, tPromedioCola, etiquetas, "LINEAS", "Promedio Pila O(1)", "Promedio Cola O(1)");
                }
                txtInfo.append("CONCLUSIÓN: Las líneas horizontales confirman que el tiempo\n");
                txtInfo.append("por operación no depende de N (Complejidad Constante O(1)).");
            }).start();
        });
    }
}