package juego;


import java.awt.*;
import javax.swing.*;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego{
	private Entorno entorno;
	private Image fondo;
	
	
	private Obstaculo[] rocas;
	
	Juego(){
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		
		fondo = new ImageIcon(getClass().getResource("/imagenes/FondoTierra2D.png")).getImage();
		this.entorno.iniciar();
		
		rocas = new Obstaculo[] {
				new Obstaculo(150, 200),
			    new Obstaculo(150, 400),
			    new Obstaculo(370, 540),
			    new Obstaculo(450, 150),
			    new Obstaculo(460, 400)
		};
	}

	public void tick(){
		entorno.dibujarImagen(fondo, 400, 300, 0);
		
		for (Obstaculo roca : rocas) {
		    roca.dibujar(entorno);
		}
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args){
		Juego juego = new Juego();
	}
}
