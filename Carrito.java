package SistemaTienda;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * Clase donde se muestra la ventana correspondiente al carrito del usuario.
 * Y corresponde con la parte de "Vista" de nuestro MVC
 */

public class Carrito extends JFrame {
    private final JLayeredPane panel = new JLayeredPane();
    private int ancho = 150;
    private int alto = 150;

    public Carrito(){
        InitComponents();
    }

    /**
     * Método principal que coloca de manera adecuada toda la interfaz del carrito para su correcto funcionamiento
     */
    public void InitComponents(){

        fondo();
        botones();

        LeerEscribirBD cesta = new LeerEscribirBD();
        List<java.util.List<String>> vec = cesta.leerRopaCarrito();
        List<Blob> blob = cesta.leerImagenCarrito();

        int x = 100;
        int y = 350;

        for(int i=0;i<vec.size();i++){

            if(y<=550){

                try{
                    byte[] aux = blob.get(i).getBytes(1,(int) blob.get(i).length());

                    BufferedImage img;

                    try{
                        img = ImageIO.read(new ByteArrayInputStream(aux));

                        ImageIcon logo = new ImageIcon(img);
                        JLabel etiquetaImagen = new JLabel();
                        etiquetaImagen.setName("borrar");
                        etiquetaImagen.setBounds(x, y, ancho, alto);
                        x+=200;
                        if(x==900){
                            x=100;
                            y+=200;
                        }
                        etiquetaImagen.setIcon(new ImageIcon(logo.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH)));

                        panel.add(etiquetaImagen, JLayeredPane.PALETTE_LAYER);

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }

        int p = 1030;
        int q = 300;
        int coordX = 1030;
        int coordY = 300;

        JLabel total = new JLabel("Total:");
        total.setFont(new Font("Georgia Pro", Font.PLAIN, 25));
        total.setBounds(coordX,coordY+210,150,30);

        double cont = 0;

        for(int i=0;i<vec.size();i++){

            if(q<=450){
                if(i<vec.size()-1){
                    JLabel precio = new JLabel(vec.get(i).get(2) + " + ");
                    cont+=Double.parseDouble(vec.get(i).get(2));
                    precio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 25));
                    precio.setBounds(p,q,90,60);
                    p+=85;
                    if(p==1455){
                        p=1030;
                        q+=50;
                    }
                    panel.add(precio,JLayeredPane.PALETTE_LAYER);

                }else{
                    JLabel precio = new JLabel(vec.get(i).get(2));
                    cont+=Double.parseDouble(vec.get(i).get(2));
                    precio.setFont(new Font("Mongolian Baiti", Font.PLAIN, 25));
                    precio.setBounds(p,q,90,60);
                    if(p==1455){
                        p=1030;
                        q+=50;
                    }
                    panel.add(precio,JLayeredPane.PALETTE_LAYER);
                }
            }
        }

        JLabel suma = new JLabel(cont + " €");
        suma.setFont(new Font("Georgia Pro", Font.PLAIN, 25));
        suma.setBounds(coordX+345,coordY+210,100,30);

        panel.add(total,JLayeredPane.PALETTE_LAYER);
        panel.add(suma,JLayeredPane.PALETTE_LAYER);

        panel.revalidate();
        panel.repaint();

        setVisible(true);
        setSize(1500,1000);
        setLocationRelativeTo(null);
        setResizable(false);

        add(panel);
    }

    /**
     * Método que coloca la imagen de fondo de la interfaz del carrito del usuario
     */
    public void fondo(){
        LeerEscribirBD x = new LeerEscribirBD();
        List<Blob> blob = x.leerFondos();

        try{
            byte[] aux = blob.get(1).getBytes(1,(int) blob.get(1).length());

            BufferedImage img;

            try{
                img = ImageIO.read(new ByteArrayInputStream(aux));

                ImageIcon logo = new ImageIcon(img);
                JLabel etiquetaImagen = new JLabel();
                etiquetaImagen.setBounds(0, 0, 1485, 800);
                etiquetaImagen.setIcon(new ImageIcon(logo.getImage().getScaledInstance(1485, 800, Image.SCALE_SMOOTH)));

                panel.add(etiquetaImagen, JLayeredPane.DEFAULT_LAYER);

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método que coloca los botones adecuados para esta ventana
     */
    public void botones(){
        JButton comprar = new JButton("Comprar ahora");
        comprar.setBounds(1030,560,430,50);

        comprar.addActionListener(finalizarCompra);

        panel.add(comprar, JLayeredPane.PALETTE_LAYER);
    }

    /**
     * Evento que llama a la clase "Pagar" para realizar el pago final del usuario
     */

    ActionListener finalizarCompra = e -> {
        dispose();
        new Pagar();
    };
}
