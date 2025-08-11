package SistemaTienda;

import javax.swing.*;

/**
 * Clase abstracta empleada para usar su método distintas veces en las clases de "Cliente" y "Admin".
 */

public abstract class Usuario{
    /**
     * Método abstracto que consiste en la validación de las credenciales de un cliente o administrador
     * @param x caja de texto empleada para contener el nombre del cliente o admin
     * @param y caja de texto empleada para contener la contraseña del cliente o admin
     * @return devuelve un true si la información del usuario/admin existe en la base de datos o retorna false en caso contrario
     */
    public abstract boolean validar(JTextField x, JTextField y);
}
