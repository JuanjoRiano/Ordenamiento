package ejercicio1;

import java.util.Random;
import java.util.LinkedList;

class Venta {
    int codigoProducto;
    int cantidadVendida;
    double precioVenta;
    
    public Venta(int codigoProducto, int cantidadVendida, double precioVenta) {
        this.codigoProducto = codigoProducto;
        this.cantidadVendida = cantidadVendida;
        this.precioVenta = precioVenta;
    }
}

public class Main {
    
    static class ListaVentas {
        LinkedList<Venta> ventas;
        
        public ListaVentas() {
            this.ventas = new LinkedList<>();
        }
        
        /**
         * Añade una nueva venta a la lista
         */
        public void agregarVenta(int codigoProducto, int cantidadVendida, double precioVenta) {
            Venta nuevaVenta = new Venta(codigoProducto, cantidadVendida, precioVenta);
            ventas.add(nuevaVenta);
        }
    }
    
    /**
     * Pobla la lista con datos aleatorios
     * @param lista Lista a poblar
     */
    public static void poblarDatos(ListaVentas lista) {
        Random rand = new Random();
        // Generar un conjunto de códigos de productos (algunos se repetirán)
        int[] codigosProductos = new int[20];
        for (int i = 0; i < codigosProductos.length; i++) {
            // Generar códigos de 4 dígitos (entre 1000 y 9999)
            codigosProductos[i] = 1000 + rand.nextInt(9000);
        }
        
        // Generar 50 ventas aleatorias
        for (int i = 0; i < 50; i++) {
            int codigoProducto = codigosProductos[rand.nextInt(codigosProductos.length)];
            int cantidadVendida = 1 + rand.nextInt(20); // Entre 1 y 20 unidades
            double precioVenta = 10000 + rand.nextDouble() * 90000; // Entre 10000 y 100000
            precioVenta = Math.round(precioVenta * 100.0) / 100.0; // Redondear a 2 decimales
            
            lista.agregarVenta(codigoProducto, cantidadVendida, precioVenta);
        }
    }
    
    /**
     * Muestra los datos de las ventas por consola
     * @param lista Lista a mostrar
     */
    public static void mostrarDatos(ListaVentas lista) {
        if (lista.ventas.isEmpty()) {
            System.out.println("La lista está vacía");
            return;
        }
        
        System.out.println("===================================================");
        System.out.println("CÓDIGO\tCANTIDAD\tPRECIO");
        System.out.println("===================================================");
        
        for (Venta venta : lista.ventas) {
            System.out.printf("%d\t%d\t\t%.2f\n", 
                venta.codigoProducto, 
                venta.cantidadVendida, 
                venta.precioVenta);
        }
        System.out.println("===================================================");
    }
    
    /**
     * Totaliza la cantidad y promedia el precio de venta por cada producto
     * @param listaEntrada Lista con las ventas individuales
     * @param listaSalida Lista donde se almacenarán los resultados totalizados
     */
    public static void sumaVentas(ListaVentas listaEntrada, ListaVentas listaSalida) {
        if (listaEntrada.ventas.isEmpty()) {
            return;
        }
        
        // Utilizamos una clase auxiliar para almacenar productos totalizados
        class ProductoTotalizado {
            int codigo;
            int cantidadTotal;
            double sumaPrecio;
            int cantidadVentas; // Para calcular el promedio
            
            ProductoTotalizado(int codigo) {
                this.codigo = codigo;
                this.cantidadTotal = 0;
                this.sumaPrecio = 0;
                this.cantidadVentas = 0;
            }
        }
        
        // Crear un arreglo temporal para almacenar los productos totalizados
        ProductoTotalizado[] productosTotalizados = new ProductoTotalizado[1000]; // Tamaño grande para asegurar espacio
        int contadorProductos = 0;
        
        // Recorrer la lista de entrada
        for (Venta venta : listaEntrada.ventas) {
            // Buscar si el producto ya existe en nuestro arreglo de totalizados
            boolean encontrado = false;
            for (int i = 0; i < contadorProductos; i++) {
                if (productosTotalizados[i].codigo == venta.codigoProducto) {
                    // Producto encontrado, actualizar datos
                    productosTotalizados[i].cantidadTotal += venta.cantidadVendida;
                    productosTotalizados[i].sumaPrecio += venta.precioVenta;
                    productosTotalizados[i].cantidadVentas++;
                    encontrado = true;
                    break;
                }
            }
            
            // Si no se encontró, agregar nuevo producto al arreglo
            if (!encontrado) {
                ProductoTotalizado nuevoProducto = new ProductoTotalizado(venta.codigoProducto);
                nuevoProducto.cantidadTotal = venta.cantidadVendida;
                nuevoProducto.sumaPrecio = venta.precioVenta;
                nuevoProducto.cantidadVentas = 1;
                productosTotalizados[contadorProductos] = nuevoProducto;
                contadorProductos++;
            }
        }
        
        // Agregar los productos totalizados a la lista de salida
        for (int i = 0; i < contadorProductos; i++) {
            ProductoTotalizado pt = productosTotalizados[i];
            double precioPromedio = pt.sumaPrecio / pt.cantidadVentas;
            // Redondear a 2 decimales
            precioPromedio = Math.round(precioPromedio * 100.0) / 100.0;
            
            listaSalida.agregarVenta(pt.codigo, pt.cantidadTotal, precioPromedio);
        }
    }
    
    /**
     * Función principal para probar el TDA
     */
    public static void main(String[] args) {
        // Declarar las listas de entrada y salida
        ListaVentas listaEntrada = new ListaVentas();
        ListaVentas listaSalida = new ListaVentas();
        
        // Poblar la lista de entrada con datos aleatorios
        poblarDatos(listaEntrada);
        
        // Mostrar los datos de la lista de entrada
        System.out.println("\nLISTA DE VENTAS ORIGINALES:");
        mostrarDatos(listaEntrada);
        
        // Totalizar las ventas
        sumaVentas(listaEntrada, listaSalida);
        
        // Mostrar los datos de la lista de salida (ventas totalizadas)
        System.out.println("\nLISTA DE VENTAS TOTALIZADAS:");
        mostrarDatos(listaSalida);
    }
}