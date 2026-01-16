package com.mx_curso;

import javax.swing.*;
import java.awt.*;

public class PanelEstructuras extends JPanel {
    JTextArea txtInfo;
    public PanelEstructuras() {
        setLayout(new BorderLayout());
        JButton btnTest = new JButton("Probar Rendimiento Pila y Cola (O(1))");
        add(btnTest, BorderLayout.NORTH);

        txtInfo = new JTextArea();
        add(new JScrollPane(txtInfo), BorderLayout.CENTER);

        btnTest.addActionListener(e -> {
            int operaciones = 100000;
            MiPila pila = new MiPila(operaciones);
            MiCola cola = new MiCola(operaciones);

            long tPilaPush, tPilaPop, tColaEnq, tColaDeq;

            long inicio = System.nanoTime();
            for(int i=0; i<operaciones; i++) pila.push(i);
            tPilaPush = System.nanoTime() - inicio;

            inicio = System.nanoTime();
            for(int i=0; i<operaciones; i++) pila.pop();
            tPilaPop = System.nanoTime() - inicio;

            inicio = System.nanoTime();
            for(int i=0; i<operaciones; i++) cola.enqueue(i);
            tColaEnq = System.nanoTime() - inicio;

            inicio = System.nanoTime();
            for(int i=0; i<operaciones; i++) cola.dequeue();
            tColaDeq = System.nanoTime() - inicio;

            String res = "Promedio de tiempo por operación (100,000 ops):\n";
            res += "Pila PUSH: " + (tPilaPush/operaciones) + " ns/op\n";
            res += "Pila POP:  " + (tPilaPop/operaciones) + " ns/op\n";
            res += "Cola ENQUEUE: " + (tColaEnq/operaciones) + " ns/op\n";
            res += "Cola DEQUEUE: " + (tColaDeq/operaciones) + " ns/op\n";

            txtInfo.setText(res);
        });
    }
}