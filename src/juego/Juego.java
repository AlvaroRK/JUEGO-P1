package juego;

import java.awt.*;
import javax.swing.*;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego{
	private Entorno entorno;
	
	private Image fondo;
	private MenuLateral menu;
	private Personaje personaje;
	
	private Obstaculo[] rocas;
	
	Juego(){
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		
		fondo = new ImageIcon(getClass().getResource("/imagenes/FondoTierra2D.png")).getImage();
		personaje = new Personaje(300,300);
		menu = new MenuLateral();
		
		rocas = new Obstaculo[] {
				new Obstaculo(150, 200),
			    new Obstaculo(150, 400),
			    new Obstaculo(370, 540),
			    new Obstaculo(450, 150),
			    new Obstaculo(460, 400)
		};
		
		this.entorno.iniciar();
	}

	public void tick(){
		entorno.dibujarImagen(fondo, 400, 300, 0);
		
		for (Obstaculo roca : rocas) {
		    roca.dibujar(entorno);
		}
		
//		MOVIMIENTO
		if (entorno.estaPresionada('a')) {
	        personaje.moverIzquierda();
	    }
	    if (entorno.estaPresionada('d')) {
	        personaje.moverDerecha();
	    }
	    if (entorno.estaPresionada('w')) {
	        personaje.moverArriba();
	    }
	    if (entorno.estaPresionada('s')) {
	        personaje.moverAbajo();
	    }
		
//	    HABILIDADES
		if (entorno.estaPresionada('1')) {
	        menu.activarIconoBola();
	    }
		if (entorno.estaPresionada('2')) {
	        menu.activarIconoExplosion();
	    }
		if (entorno.estaPresionada('3')) {
	        menu.activarIconoBurbuja();
	    }
		
		personaje.dibujar(entorno);
		
		menu.dibujar(entorno);
		menu.manejarEntrada(entorno);
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args){
		Juego juego = new Juego();
	}
}