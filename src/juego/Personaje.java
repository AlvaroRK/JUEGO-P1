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
    
//    COLISION ROCAS
    private boolean puedeMoverA(double nuevoX, double nuevoY, Obstaculo[] rocas) {
        for (Obstaculo roca : rocas) {
            double dx = Math.abs(nuevoX - roca.getX());
            double dy = Math.abs(nuevoY - roca.getY());
            double distanciaMinX = (PERSONAJE_ANCHO + roca.getAncho()) / 2.2;
            double distanciaMinY = (PERSONAJE_ALTO + roca.getAlto()) / 2.2;

            if (dx < distanciaMinX && dy < distanciaMinY) {
                return false; // hay colisión
            }
        }
        return true; // no hay colisión
    }
    
    
    public void moverIzquierda(Obstaculo[] rocas) {
        double nuevoX = x - VELOCIDAD;
        if (nuevoX - PERSONAJE_ANCHO / 2 > 0 && puedeMoverA(nuevoX, y, rocas)) {
            x = nuevoX;
            direccion = "izquierda";
        }
    }

    public void moverDerecha(Obstaculo[] rocas) {
        double nuevoX = x + VELOCIDAD;
        if (nuevoX + PERSONAJE_ANCHO / 2 < VENTANA_JUEGO_ANCHO && puedeMoverA(nuevoX, y, rocas)) {
            x = nuevoX;
            direccion = "derecha";
        }
    }

    public void moverArriba(Obstaculo[] rocas) {
        double nuevoY = y - VELOCIDAD;
        if (nuevoY - PERSONAJE_ALTO / 2 > 0 && puedeMoverA(x, nuevoY, rocas)) {
            y = nuevoY;
            direccion = "arriba";
        }
    }

    public void moverAbajo(Obstaculo[] rocas) {
        double nuevoY = y + VELOCIDAD;
        if (nuevoY + PERSONAJE_ALTO / 2 < VENTANA_JUEGO_ALTO && puedeMoverA(x, nuevoY, rocas)) {
            y = nuevoY;
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
    
    
//    GETTERS
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
}
