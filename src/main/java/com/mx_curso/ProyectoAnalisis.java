package com.mx_curso;

import javax.swing.*;

public class ProyectoAnalisis extends JFrame {

    public ProyectoAnalisis() {
        setTitle("Análisis de Rendimiento Algorítmico - Proyecto Final");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Ordenamiento (Burbuja vs QuickSort)", new PanelOrdenamiento());
        tabs.addTab("Búsqueda (Secuencial vs Binaria)", new PanelBusqueda());
        tabs.addTab("Pilas y Colas", new PanelEstructuras());

        add(tabs);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ProyectoAnalisis().setVisible(true);
        });
    }
}