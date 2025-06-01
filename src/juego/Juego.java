package juego;

import java.awt.*;
import javax.swing.*;
import entorno.Entorno;
import entorno.InterfaceJuego;
import juego.MenuLateral.Habilidad;

public class Juego extends InterfaceJuego {
	private Entorno entorno;
	private Image fondo;
	private MenuLateral menu;
	private boolean gameOver = false;
	private Personaje personaje;
	private Enemigo[] murcielagos;
	private Obstaculo[] rocas;

	Juego() {
		this.entorno = new Entorno(this, "Proyecto para TP 7", 800, 600);

		fondo = new ImageIcon(getClass().getResource("/imagenes/FondoTierra2D.png")).getImage();
		menu = new MenuLateral();
		personaje = new Personaje(300, 300);
		murcielagos = new Enemigo[5];
		for (int i = 0; i < murcielagos.length; i++) {
			murcielagos[i] = generarMurcielagoAfuera();
		}

		rocas = new Obstaculo[] {
				new Obstaculo(150, 200),
				new Obstaculo(150, 400),
				new Obstaculo(370, 540),
				new Obstaculo(450, 150),
				new Obstaculo(460, 390)
		};

		this.entorno.iniciar();
	}

	private Enemigo generarMurcielagoAfuera() {
		double x, y;
		int lado = (int) (Math.random() * 4);
		switch (lado) {
			case 0: x = -50; y = Math.random() * 600; break;
			case 1: x = 850; y = Math.random() * 600; break;
			case 2: x = Math.random() * 800; y = -50; break;
			default: x = Math.random() * 800; y = 650; break;
		}
		return new Enemigo(x, y);
	}

	public void tick() {
		entorno.dibujarImagen(fondo, 400, 300, 0);

		for (Obstaculo roca : rocas) {
			roca.dibujar(entorno);
		}

		// Movimiento
		if (entorno.estaPresionada('a')) personaje.moverIzquierda(rocas);
		if (entorno.estaPresionada('d')) personaje.moverDerecha(rocas);
		if (entorno.estaPresionada('w')) personaje.moverArriba(rocas);
		if (entorno.estaPresionada('s')) personaje.moverAbajo(rocas);

		// Habilidades
		if (entorno.estaPresionada('1')) menu.activarHabilidad(Habilidad.BOLA);
		if (entorno.estaPresionada('2')) menu.activarHabilidad(Habilidad.EXPLOSION);
		if (entorno.estaPresionada('3')) menu.activarHabilidad(Habilidad.BURBUJA);

		personaje.dibujar(entorno);

		// Enemigos
		for (Enemigo m : murcielagos) {
			m.moverHacia(personaje.getX(), personaje.getY());
			m.dibujar(entorno);

			double dx = personaje.getX() - m.getX();
			double dy = personaje.getY() - m.getY();
			double distancia = Math.sqrt(dx * dx + dy * dy);

			if (distancia < 30 && m.puedePegar()) {
				personaje.recibirDaño(3);
			}
		}

		if (personaje.estaMuerto()) gameOver = true;

		menu.dibujar(entorno, personaje.getVida(), personaje.getVidaMax());
		menu.manejarEntrada(entorno);

		if (gameOver) {
			entorno.cambiarFont("Arial", 32, Color.WHITE);
			entorno.escribirTexto("¡GAME OVER!", 250, 300);
		}
	}

	public static void main(String[] args) {
		new Juego();
	}
}