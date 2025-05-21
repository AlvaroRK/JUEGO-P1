package juego;

import entorno.Entorno;
import java.awt.*;
import javax.swing.*;

public class Obstaculo {
	private double x,y;
	private Image imagen;
	private int alto, ancho;
	
	public Obstaculo(double x, double y) {
		this.x = x;
		this.y = y;
		this.alto = 64;
		this.ancho = 64;
		this.imagen = new ImageIcon(getClass().getResource("/imagenes/RocaObs.png")).getImage();
	}
	
	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y,0);
	}
	
	public double getX() {return x;}
	public double getY() {return y;}
	public double getAncho() {return ancho;}
	public double getAlto() {return alto;}
}
