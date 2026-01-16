package com.mx_curso;

import javax.swing.*;
import java.awt.*;

public class LienzoGrafica extends JPanel {
    private long[] datos1; // Datos Algoritmo 1
    private long[] datos2; // Datos Algoritmo 2
    private String[] etiquetas;
    private String tipo; // "BARRAS" o "LINEAS"
    private String nombreA1, nombreA2;

    public LienzoGrafica() {
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    public void setDatos(long[] d1, long[] d2, String[] etiq, String tipo, String n1, String n2) {
        this.datos1 = d1;
        this.datos2 = d2;
        this.etiquetas = etiq;
        this.tipo = tipo;
        this.nombreA1 = n1;
        this.nombreA2 = n2;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (datos1 == null || datos1.length == 0) {
            g.drawString("Ejecuta la prueba para ver la gráfica.", 50, 50);
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        int padding = 50;
        int graphH = h - 2 * padding;

        long maxVal = 0;
        for (long v : datos1) if (v > maxVal) maxVal = v;
        if (datos2 != null) for (long v : datos2) if (v > maxVal) maxVal = v;
        if (maxVal == 0) maxVal = 1;

        if ("BARRAS".equals(tipo)) {
            int numGrupos = etiquetas.length;
            int anchoBarra = (w - 2 * padding) / (numGrupos * 3);

            for (int i = 0; i < numGrupos; i++) {
                int xBase = padding + i * (anchoBarra * 3 + 20);

                int alto1 = (int) ((double) datos1[i] / maxVal * graphH);
                g2.setColor(new Color(220, 50, 50));
                g2.fillRect(xBase, h - padding - alto1, anchoBarra, alto1);
                g2.setColor(Color.BLACK);
                g2.drawRect(xBase, h - padding - alto1, anchoBarra, alto1);

                int alto2 = (int) ((double) datos2[i] / maxVal * graphH);
                g2.setColor(new Color(50, 100, 220));
                g2.fillRect(xBase + anchoBarra, h - padding - alto2, anchoBarra, alto2);
                g2.setColor(Color.BLACK);
                g2.drawRect(xBase + anchoBarra, h - padding - alto2, anchoBarra, alto2);

                g2.drawString(etiquetas[i], xBase, h - padding + 15);
            }
            // Leyenda
            g2.setColor(new Color(220, 50, 50)); g2.fillRect(padding, 10, 10, 10);
            g2.setColor(Color.BLACK); g2.drawString(nombreA1, padding + 15, 20);
            g2.setColor(new Color(50, 100, 220)); g2.fillRect(padding + 150, 10, 10, 10);
            g2.setColor(Color.BLACK); g2.drawString(nombreA2, padding + 165, 20);
        }
        else if ("LINEAS".equals(tipo)) {
            int numPuntos = etiquetas.length;
            int xStep = (w - 2 * padding) / (numPuntos - 1);

            g2.drawLine(padding, h - padding, w - padding, h - padding);
            g2.drawLine(padding, padding, padding, h - padding);

            for (int i = 0; i < numPuntos; i++) {
                int x = padding + i * xStep;

                int y1 = h - padding - (int)((double)datos1[i] / maxVal * graphH);
                g2.setColor(Color.RED);
                g2.fillOval(x - 3, y1 - 3, 6, 6);
                if (i > 0) {
                    int xPrev = padding + (i-1) * xStep;
                    int yPrev = h - padding - (int)((double)datos1[i-1] / maxVal * graphH);
                    g2.drawLine(xPrev, yPrev, x, y1);
                }

                int y2 = h - padding - (int)((double)datos2[i] / maxVal * graphH);
                g2.setColor(Color.BLUE);
                g2.fillOval(x - 3, y2 - 3, 6, 6);
                if (i > 0) {
                    int xPrev = padding + (i-1) * xStep;
                    int yPrev = h - padding - (int)((double)datos2[i-1] / maxVal * graphH);
                    g2.drawLine(xPrev, yPrev, x, y2);
                }

                g2.setColor(Color.BLACK);
                g2.drawString(etiquetas[i], x - 10, h - padding + 20);
            }
            g2.setColor(Color.RED); g2.drawString(nombreA1 + " (O n^2)", w - 150, 20);
            g2.setColor(Color.BLUE); g2.drawString(nombreA2 + " (O n log n)", w - 150, 40);
        }
    }
}