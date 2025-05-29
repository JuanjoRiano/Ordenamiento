package ejercicio1;

import java.util.Random;
import java.util.LinkedList;
import java.util.Collections;
import java.util.Comparator;

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

        public void agregarVenta(int codigoProducto, int cantidadVendida, double precioVenta) {
            Venta nuevaVenta = new Venta(codigoProducto, cantidadVendida, precioVenta);
            ventas.add(nuevaVenta);
        }
    }

    public static void poblarDatos(ListaVentas lista) {
        Random rand = new Random();

        int[] codigosProductos = new int[20];
        for (int i = 0; i < codigosProductos.length; i++) {
            codigosProductos[i] = 1000 + rand.nextInt(9000);
        }
        
        for (int i = 0; i < 50; i++) {
            int codigoProducto = codigosProductos[rand.nextInt(codigosProductos.length)];
            int cantidadVendida = 1 + rand.nextInt(20); // Entre 1 y 20 unidades
            double precioVenta = 10000 + rand.nextDouble() * 90000; // Entre 10000 y 100000
            precioVenta = Math.round(precioVenta * 100.0) / 100.0; // Redondear a 2 decimales
            
            lista.agregarVenta(codigoProducto, cantidadVendida, precioVenta);
        }
    }

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

    public static void sumaVentas(ListaVentas listaEntrada, ListaVentas listaSalida) {
        if (listaEntrada.ventas.isEmpty()) {
            return;
        }
        
        class ProductoTotalizado {
            int codigo;
            int cantidadTotal;
            double sumaPrecio;
            int cantidadVentas; 
            
            ProductoTotalizado(int codigo) {
                this.codigo = codigo;
                this.cantidadTotal = 0;
                this.sumaPrecio = 0;
                this.cantidadVentas = 0;
            }
        }
        
        ProductoTotalizado[] productosTotalizados = new ProductoTotalizado[1000];
        int contadorProductos = 0;
        
        for (Venta venta : listaEntrada.ventas) {
            boolean encontrado = false;
            for (int i = 0; i < contadorProductos; i++) {
                if (productosTotalizados[i].codigo == venta.codigoProducto) {
                    productosTotalizados[i].cantidadTotal += venta.cantidadVendida;
                    productosTotalizados[i].sumaPrecio += venta.precioVenta;
                    productosTotalizados[i].cantidadVentas++;
                    encontrado = true;
                    break;
                }
            }
            
            if (!encontrado) {
                ProductoTotalizado nuevoProducto = new ProductoTotalizado(venta.codigoProducto);
                nuevoProducto.cantidadTotal = venta.cantidadVendida;
                nuevoProducto.sumaPrecio = venta.precioVenta;
                nuevoProducto.cantidadVentas = 1;
                productosTotalizados[contadorProductos] = nuevoProducto;
                contadorProductos++;
            }
        }
        
        for (int i = 0; i < contadorProductos; i++) {
            ProductoTotalizado pt = productosTotalizados[i];
            double precioPromedio = pt.sumaPrecio / pt.cantidadVentas;
            precioPromedio = Math.round(precioPromedio * 100.0) / 100.0;
            
            listaSalida.agregarVenta(pt.codigo, pt.cantidadTotal, precioPromedio);
        }
    }

    public static void ordenarPorCodigo(ListaVentas lista) {
        Collections.sort(lista.ventas, new Comparator<Venta>() {
            public int compare(Venta v1, Venta v2) {
                return Integer.compare(v1.codigoProducto, v2.codigoProducto);
            }
        });
    }

    public static void main(String[] args) {

        ListaVentas listaEntrada = new ListaVentas();
        ListaVentas listaSalida = new ListaVentas();
        
        poblarDatos(listaEntrada);
        
        System.out.println("\nLISTA DE VENTAS ORIGINALES:");
        mostrarDatos(listaEntrada);
        
        sumaVentas(listaEntrada, listaSalida);

        ordenarPorCodigo(listaSalida);
        
        System.out.println("\nLISTA DE VENTAS TOTALIZADAS (ORDENADA):");
        mostrarDatos(listaSalida);
    }
}
