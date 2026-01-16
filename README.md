Análisis de Rendimiento Algorítmico y Estructuras de Datos

Este proyecto es una herramienta integral desarrollada en Java para la 
medición, visualización y análisis de la complejidad algorítmica (O grande). Permite comparar algoritmos de ordenamiento, búsqueda 
y operaciones en estructuras lineales mediante pruebas de escalabilidad con volúmenes de datos desde 10^3 hasta 10^5.

1. Arquitectura del Software
   
    El software sigue el patrón de diseño Modular y Basado en Componentes. Se ha priorizado la separación de responsabilidades para garantizar que la lógica de cálculo sea independiente de la representación visual.

Capas del Sistema:

    Capa de Lógica (Algoritmos): Contiene las implementaciones puras de ordenamiento, búsqueda y manejo de nodos.
    Capa de Servicio (Generadores y Medición): Encargada de la creación de escenarios (mejor, promedio, peor caso) y la captura de tiempos en nanosegundos.
    Capa de Presentación (GUI): Desarrollada en Swing, gestiona la interacción con el usuario y la renderización de gráficos en tiempo real.

2. Estructura Modular y Comunicación

   
El proyecto se divide en módulos desacoplados que se comunican de forma jerárquica:
  Módulos Principales:
  
    ProyectoAnalisis.java (Orquestador): Es el punto de entrada. Gestiona el contenedor principal (JTabbedPane) y ensambla los paneles especializados.
    Algoritmos.java & GeneradorDatos.java: Módulos estáticos que proveen las herramientas de procesamiento y la materia prima (arreglos de datos).    
    Paneles Especializados (PanelOrdenamiento, PanelBusqueda, PanelEstructuras): Cada uno actúa como un controlador independiente. Capturan la entrada del usuario, invocan la lógica y envían los resultados al módulo de graficación.
    LienzoGrafica.java (Motor Visual): Un componente personalizado que hereda de JPanel. Recibe arreglos de datos primitivos y los traduce a coordenadas cartesianas para dibujar barras o líneas de tendencia.
    
4. Decisiones de Diseño
   
   
Para cumplir con los objetivos académicos y técnicos, se tomaron las siguientes decisiones:

    Uso de System.nanoTime(): Se eligió sobre currentTimeMillis() debido a que los algoritmos eficientes (como QuickSort o Búsqueda Binaria) ejecutan operaciones en fracciones de milisegundo que serían invisibles en una escala menor.
    Validación de O(1): En el módulo de estructuras, se decidió medir el tiempo promedio por operación (Tiempo Total / N). Esta decisión permite demostrar que, aunque el volumen de datos crezca, el costo de un push o enqueue se mantiene constante.

    
5. Estructura del Repositorio

             ├── src/
        │   ├── ProyectoAnalisis.java     # Clase Principal
        │   ├── Algoritmos.java           # Lógica de Ordenamiento y Búsqueda
        │   ├── GeneradorDatos.java       # Generación de escenarios (Random, Ordenado, Inverso)
        │   ├── LienzoGrafica.java        # Motor de renderizado de gráficas
        │   ├── PanelOrdenamiento.java    # UI para análisis de Burbuja vs QuickSort
        │   ├── PanelBusqueda.java        # UI para análisis Secuencial vs Binaria
        │   ├── PanelEstructuras.java     # UI para validación de Pilas y Colas
        │   ├── MiPila.java               # Estructura de datos lineal (LIFO)
        │   └── MiCola.java               # Estructura de datos lineal (FIFO)
        └── README.md
  


6. Requisitos de Ejecución
  
        JDK: 8 o superior.
        Compilación: javac *.java
        Ejecución: java ProyectoAnalisis
