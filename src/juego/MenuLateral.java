package juego;

import java.awt.*;
import javax.swing.*;

import entorno.Entorno;

public class MenuLateral {
	
	private Image iconoBurbuja;
	private Image iconoBola;
	private Image iconoExplosion;
	private boolean mostrarIconoBurbuja;
	private boolean mostrarIconoBola;
	private boolean mostrarIconoExplosion;
	
	public MenuLateral() {
	    this.iconoBurbuja = new ImageIcon(getClass().getResource("/imagenes/BurbujaProtectora.png")).getImage();
	    this.iconoBola = new ImageIcon(getClass().getResource("/imagenes/BolaDeFuego.png")).getImage();
	    this.iconoExplosion = new ImageIcon(getClass().getResource("/imagenes/ExplosionDeLuz.png")).getImage();
	    this.mostrarIconoBurbuja = false;
	    this.mostrarIconoBola = false;
	    this.mostrarIconoExplosion = false;
	}
	
	public void activarIconoBola() {
		this.mostrarIconoBurbuja = false;
		this.mostrarIconoExplosion = false;
		this.mostrarIconoBola = true;
	}
	
	
	
	public void activarIconoExplosion() {
		this.mostrarIconoBurbuja = false;
		this.mostrarIconoBola = false;
		this.mostrarIconoExplosion = true;
	}
	
	public void activarIconoBurbuja() {
		this.mostrarIconoBola = false;
	    this.mostrarIconoExplosion = false;
	    this.mostrarIconoBurbuja = true;
	}
	
	public void dibujar(Entorno entorno) {
        // Dibujar fondo del menú
        entorno.dibujarRectangulo(700, 300, 200, 600, 0, Color.DARK_GRAY);

        // Botón 1
        entorno.dibujarRectangulo(700, 260, 160, 40, 0, Color.GRAY);
        entorno.cambiarFont("Arial", 16, Color.WHITE);
        entorno.escribirTexto("Bola de fuego", 650, 265);

        // Botón 2
        entorno.dibujarRectangulo(700, 320, 160, 40, 0, Color.GRAY);
        entorno.cambiarFont("Arial", 16, Color.WHITE);
        entorno.escribirTexto("Explosion de luz", 645, 325);

        // Botón 3
        entorno.dibujarRectangulo(700,380, 160, 40, 0, Color.GRAY);
        entorno.cambiarFont("Arial", 16, Color.WHITE);
        entorno.escribirTexto("Burbuja protectora", 635, 385);
        
        // Botón SALIR        
        entorno.dibujarRectangulo(740, 570, 80, 40, 0, Color.RED);
        entorno.cambiarFont("Arial", 18, Color.WHITE);
        entorno.escribirTexto("Salir", 720, 575);
        
        // VIDA
        entorno.dibujarRectangulo(700, 420, 160, 10, 0, Color.RED);
        // ENERGIA
        entorno.dibujarRectangulo(700, 445, 160, 10, 0, Color.BLUE);
        
        // Cuadrado de Habilidades
        entorno.dibujarRectangulo(700, 120, 140, 140, 0, Color.GRAY);  
        if (mostrarIconoBola) {
        	entorno.dibujarImagen(iconoBola, 700, 120, 0, 2);
        	entorno.dibujarRectangulo(700, 260, 160, 40, 0, Color.ORANGE);
        	entorno.cambiarFont("Arial", 16, Color.BLACK);
            entorno.escribirTexto("Bola de fuego", 650, 265);
        }
        
        if (mostrarIconoExplosion) {
        	entorno.dibujarImagen(iconoExplosion, 700, 120, 0, 2);
        	entorno.dibujarRectangulo(700, 320, 160, 40, 0, Color.ORANGE);
        	entorno.cambiarFont("Arial", 16, Color.BLACK);
            entorno.escribirTexto("Explosion de luz", 645, 325);
        }
        
        if (mostrarIconoBurbuja) {
        	entorno.dibujarImagen(iconoBurbuja, 700, 120, 0, 2);
        	entorno.dibujarRectangulo(700,380, 160, 40, 0, Color.ORANGE);
        	entorno.cambiarFont("Arial", 16, Color.BLACK);
            entorno.escribirTexto("Burbuja protectora", 635, 385);
        }
    }
	
    public void manejarEntrada(Entorno entorno) {
        if (entorno.estaPresionada('1')) {
            System.out.println("Bola de fuego");
        }
        if (entorno.estaPresionada('2')) {
            System.out.println("Explosion de luz");
        }
        if (entorno.estaPresionada('3')) {
            System.out.println("Burbuja protectora");
        }
        if (entorno.estaPresionada('n')) {
            System.out.println("Salir");
            System.exit(0);
        }
    }
}