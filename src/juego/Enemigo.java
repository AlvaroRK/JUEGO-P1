package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {
	double x,y;
	double velocidad = 1.5;
	Image[] sprites;
	int frameActual = 0;
	int contadorFrames = 0;
	
	public Enemigo(double x, double y) {
		this.x = x;
		this.y = y;
		
		sprites = new Image[4];
		sprites[0] = Herramientas.cargarImagen("imagenes/Bat1.png");
		sprites[1] = Herramientas.cargarImagen("imagenes/Bat2.png");
		sprites[2] = Herramientas.cargarImagen("imagenes/Bat3.png");
		sprites[3] = Herramientas.cargarImagen("imagenes/Bat4.png");
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(sprites[frameActual], x, y, 0, 0.5);
		
		contadorFrames++;
		if (contadorFrames % 10 == 0) {
			frameActual = (frameActual+1)%2;
			contadorFrames = 0;
		}
	}
	
	public void moverHacia(double jugadorX, double jugadorY) {
        double dx = jugadorX - x;
        double dy = jugadorY - y;
        double distancia = Math.sqrt(dx * dx + dy * dy);

        if (distancia != 0) {
            x += velocidad * dx / distancia;
            y += velocidad * dy / distancia;
        }
    }
}
	
