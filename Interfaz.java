package SistemaTienda;

/**
 * Interface que contiene un método que se emplea en la clase Pagar para comprobar sus cajas de texto
 */
public interface Interfaz {
    boolean camposVacios(String n, String e, String t, String nac_d,String nac_m, String nac_a, String d, String tarjeta, String cad, String cvv);
}
