package juego;


import java.awt.*;
import javax.swing.*;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego{
	private Entorno entorno;
	private Image fondo;
	
	Juego(){
		this.entorno = new Entorno(this, "Proyecto para TP", 800, 600);
		
		fondo = new ImageIcon(getClass().getResource("/imagenes/FondoTierra2D.png")).getImage();
		this.entorno.iniciar();
	}

	public void tick(){
		entorno.dibujarImagen(fondo, 400, 300, 0);
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args){
		Juego juego = new Juego();
	}
}
