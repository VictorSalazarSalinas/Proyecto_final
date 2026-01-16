package com.mx_curso;

import javax.swing.*;
import java.awt.*;

public class PanelBusqueda extends JPanel {
    JTextArea txtInfo;
    public PanelBusqueda() {
        setLayout(new BorderLayout());
        JButton btnCorrer = new JButton("Comparar Búsqueda Secuencial vs Binaria (N=1,000,000)");
        add(btnCorrer, BorderLayout.NORTH);

        txtInfo = new JTextArea();
        txtInfo.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(txtInfo), BorderLayout.CENTER);

        btnCorrer.addActionListener(e -> {
            int N = 1000000;
            int[] datos = GeneradorDatos.generarOrdenado(N);
            int objetivo = -1;

            long inicio = System.nanoTime();
            Algoritmos.busquedaSecuencial(datos, objetivo);
            long tSec = System.nanoTime() - inicio;

            inicio = System.nanoTime();
            Algoritmos.busquedaBinaria(datos, objetivo);
            long tBin = System.nanoTime() - inicio;

            String reporte = "RESULTADOS BÚSQUEDA (N=" + N + ", Elemento no existente):\n\n";
            reporte += "Búsqueda Secuencial (O(n)): " + tSec + " ns\n";
            reporte += "Búsqueda Binaria (O(log n)): " + tBin + " ns\n";
            reporte += "\nConclusión: La búsqueda binaria es drásticamente más rápida.";
            txtInfo.setText(reporte);
        });
    }
}