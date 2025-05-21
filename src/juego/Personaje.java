package juego;

import java.awt.Image;
import javax.swing.ImageIcon;

import entorno.Entorno;
import static juego.Constantes.*;

public class Personaje {
	private double x;
    private double y;
    private Image magoFrente, magoArriba, magoDerecha, magoIzquierda;
    private String direccion;
    
    public Personaje(double x, double y) {
		this.x = x;
		this.y = y;
		
		magoFrente = new ImageIcon(getClass().getResource("/imagenes/MagoAbj.png")).getImage();
		magoArriba = new ImageIcon(getClass().getResource("/imagenes/MagoArr.png")).getImage();
		magoDerecha = new ImageIcon(getClass().getResource("/imagenes/MagoDer.png")).getImage();
		magoIzquierda = new ImageIcon(getClass().getResource("/imagenes/MagoIzq.png")).getImage();
		
		direccion = "abajo";
	}
    
    public void moverIzquierda() {
    	if (x - PERSONAJE_ANCHO / 2 - VELOCIDAD > 0) {
            x -= VELOCIDAD;
            direccion = "izquierda";
        }
    }
    
    public void moverDerecha() {
    	if (x + PERSONAJE_ANCHO / 2 + VELOCIDAD < VENTANA_JUEGO_ANCHO) {
            x += VELOCIDAD;
            direccion = "derecha";
        }
    }
    
    public void moverArriba() {
    	if (y - PERSONAJE_ALTO / 2 - VELOCIDAD > 0) {
            y -= VELOCIDAD;
            direccion = "arriba";
        }
    }
    
    public void moverAbajo() {
    	if (y + PERSONAJE_ALTO / 2 + VELOCIDAD < VENTANA_JUEGO_ALTO) {
            y += VELOCIDAD;
            direccion = "abajo";
        }
    }
    
    
    public void dibujar(Entorno entorno) {
        Image imagen;

        switch (direccion) {
            case "arriba":
                imagen = magoArriba;
                break;
            case "izquierda":
                imagen = magoIzquierda;
                break;
            case "derecha":
                imagen = magoDerecha;
                break;
            case "abajo":
            default:
                imagen = magoFrente;
                break;
        }

        entorno.dibujarImagen(imagen, x, y, 0);
    }
    
}
