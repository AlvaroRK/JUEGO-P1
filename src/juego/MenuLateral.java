package juego;

import java.awt.*;
import javax.swing.*;
import entorno.Entorno;

public class MenuLateral {

	public enum Habilidad {
		NINGUNA, BOLA, EXPLOSION, BURBUJA
	}

	private Image iconoBurbuja;
	private Image iconoBola;
	private Image iconoExplosion;
	private Habilidad habilidadActiva;

	public MenuLateral() {
		this.iconoBurbuja = new ImageIcon(getClass().getResource("/imagenes/BurbujaProtectora.png")).getImage();
		this.iconoBola = new ImageIcon(getClass().getResource("/imagenes/BolaDeFuego.png")).getImage();
		this.iconoExplosion = new ImageIcon(getClass().getResource("/imagenes/ExplosionDeLuz.png")).getImage();
		this.habilidadActiva = Habilidad.NINGUNA;
	}

	public void activarHabilidad(Habilidad h) {
		this.habilidadActiva = h;
	}

	public void dibujar(Entorno entorno, int vida, int vidaMax) {
		// Fondo menú
		entorno.dibujarRectangulo(700, 300, 200, 600, 0, Color.DARK_GRAY);

		// Botones de habilidad
		String[] nombres = { "Bola de fuego", "Explosion de luz", "Burbuja protectora" };
		int[] posicionesY = { 260, 320, 380 };
		int[] posicionesXTexto = { 650, 645, 635 };

		for (int i = 0; i < nombres.length; i++) {
			entorno.dibujarRectangulo(700, posicionesY[i], 160, 40, 0, Color.GRAY);
			entorno.cambiarFont("Arial", 16, Color.WHITE);
			entorno.escribirTexto(nombres[i], posicionesXTexto[i], posicionesY[i] + 5);
		}

		// Botón salir
		entorno.dibujarRectangulo(740, 570, 80, 40, 0, Color.RED);
		entorno.cambiarFont("Arial", 18, Color.WHITE);
		entorno.escribirTexto("Salir", 720, 575);

		// Barra de vida
		int anchoMax = 160;
		int alto = 20;
		int xInicio = 620;
		int y = 420;

		double porcentaje = (double) vida / vidaMax;
		int anchoVida = (int) (anchoMax * porcentaje);
		entorno.dibujarRectangulo(xInicio + anchoMax / 2, y, anchoMax, alto, 0, Color.GRAY);

		if (vida > 0) {
			entorno.dibujarRectangulo(xInicio + anchoVida / 2, y, anchoVida, alto, 0, Color.RED);
		}
		entorno.cambiarFont("Arial", 14, Color.WHITE);
		entorno.escribirTexto("Vida: " + vida + "/" + vidaMax, xInicio + 35, y + 4.5);

		// Energía
		entorno.dibujarRectangulo(700, 445, 160, 10, 0, Color.BLUE);

		// Cuadro habilidad
		entorno.dibujarRectangulo(700, 120, 140, 140, 0, Color.GRAY);

		// Dibujo de habilidad activa
		switch (habilidadActiva) {
			case BOLA:
				entorno.dibujarImagen(iconoBola, 700, 120, 0, 2);
				resaltarBoton(entorno, 260, "Bola de fuego", 650, 265);
				break;
			case EXPLOSION:
				entorno.dibujarImagen(iconoExplosion, 700, 120, 0, 2);
				resaltarBoton(entorno, 320, "Explosion de luz", 645, 325);
				break;
			case BURBUJA:
				entorno.dibujarImagen(iconoBurbuja, 700, 120, 0, 2);
				resaltarBoton(entorno, 380, "Burbuja protectora", 635, 385);
				break;
			default:
				break;
		}
	}

	private void resaltarBoton(Entorno entorno, int y, String texto, int xTexto, int yTexto) {
		entorno.dibujarRectangulo(700, y, 160, 40, 0, Color.ORANGE);
		entorno.cambiarFont("Arial", 16, Color.BLACK);
		entorno.escribirTexto(texto, xTexto, yTexto);
	}

	public void manejarEntrada(Entorno entorno) {
		if (entorno.estaPresionada('n')) {
			System.out.println("Salir");
			System.exit(0);
		}
	}
}
