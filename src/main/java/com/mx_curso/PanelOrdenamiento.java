package com.mx_curso;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class PanelOrdenamiento extends JPanel {
    JTextArea txtResultados;
    LienzoGrafica panelGrafica;
    JButton btnCompararEscenarios, btnEscalabilidad, btnManual;
    JTextField txtEntradaManual;

    public PanelOrdenamiento() {
        setLayout(new BorderLayout());

        // --- PANEL SUPERIOR (CONTROLES) ---
        JPanel panelSuperior = new JPanel(new GridLayout(2, 1));

        // Fila 1: Botones Automáticos
        JPanel panelBotonesAuto = new JPanel();
        btnCompararEscenarios = new JButton("1. Comparar Escenarios (N fijo)");
        btnEscalabilidad = new JButton("2. Ver Escalabilidad (10^3 a 10^5)"); // Etiqueta actualizada
        panelBotonesAuto.add(btnCompararEscenarios);
        panelBotonesAuto.add(btnEscalabilidad);

        // Fila 2: Entrada Manual
        JPanel panelManual = new JPanel();
        panelManual.add(new JLabel("Datos manuales (separados por coma):"));
        txtEntradaManual = new JTextField(20);
        txtEntradaManual.setText("50, 12, 5, 90, 1, 33, 100, 4");
        btnManual = new JButton("Probar mis datos");
        panelManual.add(txtEntradaManual);
        panelManual.add(btnManual);

        panelSuperior.add(panelBotonesAuto);
        panelSuperior.add(panelManual);

        add(panelSuperior, BorderLayout.NORTH);

        // AREA DE RESULTADOS
        txtResultados = new JTextArea(8, 40); // Un poco más alto para ver más logs
        add(new JScrollPane(txtResultados), BorderLayout.SOUTH);

        // GRAFICA
        panelGrafica = new LienzoGrafica();
        add(panelGrafica, BorderLayout.CENTER);

        //  LISTENERS

        // ACCIÓN 1: COMPARACIÓN DE ESCENARIOS
        btnCompararEscenarios.addActionListener(e -> {
            txtResultados.setText("Ejecutando comparación de escenarios con N=10,000...\n");
            new Thread(() -> {
                int N = 10000;
                long[] tBurbuja = new long[3];
                long[] tQuick = new long[3];

                int[] datos = GeneradorDatos.generarOrdenado(N);
                tBurbuja[0] = medirBurbuja(datos.clone());
                tQuick[0] = medirQuick(datos.clone());

                datos = GeneradorDatos.generarAleatorio(N);
                tBurbuja[1] = medirBurbuja(datos.clone());
                tQuick[1] = medirQuick(datos.clone());

                datos = GeneradorDatos.generarInverso(N);
                tBurbuja[2] = medirBurbuja(datos.clone());
                tQuick[2] = medirQuick(datos.clone());

                actualizarTexto(tBurbuja, tQuick, new String[]{"Mejor", "Prom", "Peor"});
                panelGrafica.setDatos(tBurbuja, tQuick, new String[]{"Mejor", "Promedio", "Peor"}, "BARRAS", "Burbuja", "QuickSort");
            }).start();
        });

        // ACCIÓN 2: ESCALABILIDAD (10^3 a 10^5)
        btnEscalabilidad.addActionListener(e -> {
            txtResultados.setText("Iniciando prueba de escalabilidad (N=1,000 a N=100,000)...\n");


            new Thread(() -> {
                // Potencias de 10
                int[] tamanios = {1000,5000, 10000, 100000};
                long[] tBurbuja = new long[tamanios.length];
                long[] tQuick = new long[tamanios.length];
                String[] etiquetas = new String[tamanios.length];

                for(int i=0; i<tamanios.length; i++) {
                    int N = tamanios[i];
                    etiquetas[i] = "10^" + (int)Math.log10(N); // Genera etiquetas 10^3

                    int[] datos = GeneradorDatos.generarAleatorio(N);

                    // Medir QuickSort (Siempre rápido)
                    tQuick[i] = medirQuick(datos.clone());



                    tBurbuja[i] = medirBurbuja(datos.clone());
                    txtResultados.append("N=" + N + " -> Burbuja: " + tBurbuja[i] + "ns | Quick: " + tQuick[i] + "ns\n");

                    // Actualizar gráfica en tiempo real
                    panelGrafica.setDatos(tBurbuja, tQuick, etiquetas, "LINEAS", "Burbuja", "QuickSort");
                }
                txtResultados.append("\nPrueba finalizada.");
            }).start();
        });

        // ACCIÓN 3: MANUAL
        btnManual.addActionListener(e -> {
            String texto = txtEntradaManual.getText();
            try {
                String[] partes = texto.split(",");
                int[] datosManuales = new int[partes.length];
                for (int i = 0; i < partes.length; i++) {
                    datosManuales[i] = Integer.parseInt(partes[i].trim());
                }

                txtResultados.setText("Procesando datos manuales (N=" + datosManuales.length + ")...\n");
                long tB = medirBurbuja(datosManuales.clone());
                long tQ = medirQuick(datosManuales.clone());

                txtResultados.append("Tus datos: " + Arrays.toString(datosManuales) + "\n");
                txtResultados.append("Tiempo Burbuja: " + tB + " ns\n");
                txtResultados.append("Tiempo QuickSort: " + tQ + " ns\n");

                long[] arrB = {tB};
                long[] arrQ = {tQ};
                String[] etiqueta = {"Tu Caso"};
                panelGrafica.setDatos(arrB, arrQ, etiqueta, "BARRAS", "Burbuja", "QuickSort");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error en datos: " + ex.getMessage());
            }
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
        for(int i=0; i<casos.length; i++) {
            sb.append(casos[i]).append(" -> Burbuja: ").append(t1[i]).append(" | QuickSort: ").append(t2[i]).append("\n");
        }
        txtResultados.append(sb.toString());
    }
}