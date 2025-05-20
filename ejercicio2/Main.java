package ejercicio2;

import java.util.Random;

public class Main {
    // Constantes
    private static final int MAX_EMPLEADOS = 10;
    private static final int SUELDO_BASICO = 0;
    private static final int DEDUCCIONES = 1;
    private static final int NETO_PAGAR = 2;
    
    // Variables
    private int[] identificaciones;
    private String[] nombres;
    private double[][] datosNomina;
    private int cantidadEmpleados;
    
    // Constructor
    public Main() {
        identificaciones = new int[MAX_EMPLEADOS];
        nombres = new String[MAX_EMPLEADOS];
        datosNomina = new double[MAX_EMPLEADOS][3]; 
        cantidadEmpleados = 0;
    }
    
    public void poblarDatos() {
        Random random = new Random();
        String[] nombresDisponibles = {"Juan", "Ana", "Carlos", "María", "Pedro", "Laura", "Damaris", 
                                      "Albita", "Pedrito", "Juanita", "Pachita", "Ricardo"};
        
        cantidadEmpleados = MAX_EMPLEADOS;
        
        for (int i = 0; i < cantidadEmpleados; i++) {

        	identificaciones[i] = 1000 + random.nextInt(9000);
            
            nombres[i] = nombresDisponibles[random.nextInt(nombresDisponibles.length)];
            
            datosNomina[i][SUELDO_BASICO] = 1000000 + random.nextInt(3000001);
            
            double porcentajeDeduccion = 0.1 + (random.nextDouble() * 0.1);
            datosNomina[i][DEDUCCIONES] = Math.round(datosNomina[i][SUELDO_BASICO] * porcentajeDeduccion);
            
            datosNomina[i][NETO_PAGAR] = datosNomina[i][SUELDO_BASICO] - datosNomina[i][DEDUCCIONES];
        }
    }
    
    public void ordenarPorIdentificacion() {
        for (int i = 0; i < cantidadEmpleados - 1; i++) {
            int minIndex = i;
            
            for (int j = i + 1; j < cantidadEmpleados; j++) {
                if (identificaciones[j] < identificaciones[minIndex]) {
                    minIndex = j;
                }
            }
            
            // Intercambiar elementos si se encontró un mínimo diferente
            if (minIndex != i) {
                // Intercambiar identificaciones
                int tempId = identificaciones[i];
                identificaciones[i] = identificaciones[minIndex];
                identificaciones[minIndex] = tempId;
                
                // Intercambiar nombres
                String tempNombre = nombres[i];
                nombres[i] = nombres[minIndex];
                nombres[minIndex] = tempNombre;
                
                // Intercambiar datos de nómina
                for (int k = 0; k < 3; k++) {
                    double tempDato = datosNomina[i][k];
                    datosNomina[i][k] = datosNomina[minIndex][k];
                    datosNomina[minIndex][k] = tempDato;
                }
            }
        }
    }
    
    public int buscarEmpleado(int identificacion) {
        int inicio = 0;
        int fin = cantidadEmpleados - 1;
        
        while (inicio <= fin) {
            int medio = inicio + (fin - inicio) / 2;
            
            // Si la identificación está en el medio
            if (identificaciones[medio] == identificacion) {
                return medio;
            }
            
            // Si la identificación es mayor, buscar en la mitad derecha
            if (identificaciones[medio] < identificacion) {
                inicio = medio + 1;
            } else {
                // Si la identificación es menor, buscar en la mitad izquierda
                fin = medio - 1;
            }
        }
        
        return -1;
    }
    
    // Función para ordenar por nombre usando QuickSort
    public void ordenarPorNombre() {
        quickSort(0, cantidadEmpleados - 1);
    }
    
    // Implementación del algoritmo QuickSort
    private void quickSort(int inicio, int fin) {
        if (inicio < fin) {
            int indicePivote = particion(inicio, fin);
            
            // Ordenar recursivamente los elementos antes y después del pivote
            quickSort(inicio, indicePivote - 1);
            quickSort(indicePivote + 1, fin);
        }
    }
    
    private int particion(int inicio, int fin) {
        // Seleccionar el elemento del extremo derecho como pivote
        String pivote = nombres[fin];
        int i = inicio - 1;
        
        for (int j = inicio; j < fin; j++) {
            // Si el elemento actual es menor o igual que el pivote
            if (nombres[j].compareTo(pivote) <= 0) {
                i++;
                
                // Intercambiar elementos
                intercambiar(i, j);
            }
        }
        
        // Intercambiar el pivote con el elemento en la posición (i + 1)
        intercambiar(i + 1, fin);
        
        return i + 1;
    }
    
    private void intercambiar(int i, int j) {
        // Intercambiar identificaciones
        int tempId = identificaciones[i];
        identificaciones[i] = identificaciones[j];
        identificaciones[j] = tempId;
        
        // Intercambiar nombres
        String tempNombre = nombres[i];
        nombres[i] = nombres[j];
        nombres[j] = tempNombre;
        
        // Intercambiar datos de nómina
        for (int k = 0; k < 3; k++) {
            double tempDato = datosNomina[i][k];
            datosNomina[i][k] = datosNomina[j][k];
            datosNomina[j][k] = tempDato;
        }
    }
    
    public void mostrarDatos() {
        System.out.println("\n=== NÓMINA DE EMPLEADOS (ORDENADOS POR NOMBRE) ===");
        System.out.printf("%-12s %-15s %-15s %-15s %-15s\n", 
                        "ID", "NOMBRE", "SUELDO BÁSICO", "DEDUCCIONES", "NETO A PAGAR");
        System.out.println("----------------------------------------------------------------");
        
        for (int i = 0; i < cantidadEmpleados; i++) {
            System.out.printf("%-12d %-15s $%-14.2f $%-14.2f $%-14.2f\n", 
                            identificaciones[i], 
                            nombres[i], 
                            datosNomina[i][SUELDO_BASICO], 
                            datosNomina[i][DEDUCCIONES], 
                            datosNomina[i][NETO_PAGAR]);
        }
    }
    
    public void mostrarEmpleado(int posicion) {
        if (posicion >= 0 && posicion < cantidadEmpleados) {
            System.out.println("\n=== DATOS DEL EMPLEADO ===");
            System.out.println("Identificación: " + identificaciones[posicion]);
            System.out.println("Nombre: " + nombres[posicion]);
            System.out.printf("Sueldo Básico: $%.2f\n", datosNomina[posicion][SUELDO_BASICO]);
            System.out.printf("Deducciones: $%.2f\n", datosNomina[posicion][DEDUCCIONES]);
            System.out.printf("Neto a Pagar: $%.2f\n", datosNomina[posicion][NETO_PAGAR]);
        } else {
            System.out.println("Empleado no encontrado");
        }
    }
    
    public static void main(String[] args) {
        Main nomina = new Main();
        
        nomina.poblarDatos();
        
        System.out.println("DATOS INICIALES:");
        nomina.mostrarDatos();
        
        nomina.ordenarPorIdentificacion();
        System.out.println("\nDATOS ORDENADOS POR IDENTIFICACIÓN:");
        nomina.mostrarDatos();
        
        int idBuscar = nomina.identificaciones[3]; 
        System.out.println("\nBuscando empleado con ID: " + idBuscar);
        int posEmpleado = nomina.buscarEmpleado(idBuscar);
        nomina.mostrarEmpleado(posEmpleado);
        
        int idNoExiste = 9999;
        System.out.println("\nBuscando empleado con ID: " + idNoExiste);
        posEmpleado = nomina.buscarEmpleado(idNoExiste);
        if (posEmpleado == -1) {
            System.out.println("Empleado no encontrado");
        } else {
            nomina.mostrarEmpleado(posEmpleado);
        }
        
        nomina.ordenarPorNombre();
        
        System.out.println("\nDATOS ORDENADOS POR NOMBRE:");
        nomina.mostrarDatos();
    }
}
