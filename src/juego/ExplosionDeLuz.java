package juego;

import java.awt.Image;
import javax.swing.ImageIcon;

public class ExplosionDeLuz extends Habilidad {
    private int duracion = 30; // frames visibles
    private int frameVida = 0;

    @Override
    protected void cargarSprites() {
        sprites = new Image[4];
        sprites[0] = new ImageIcon(getClass().getResource("/imagenes/explosionDeLuz/1.png")).getImage();
        sprites[1] = new ImageIcon(getClass().getResource("/imagenes/explosionDeLuz/2.png")).getImage();
        sprites[2] = new ImageIcon(getClass().getResource("/imagenes/explosionDeLuz/3.png")).getImage();
        sprites[3] = new ImageIcon(getClass().getResource("/imagenes/explosionDeLuz/4.png")).getImage();
    }

    @Override
    public void activar(double origenX, double origenY, double destinoX, double destinoY) {
        this.x = destinoX;
        this.y = destinoY;
        this.frameActual = 0;
        this.contadorFrames = 0;
        this.frameVida = 0;
        this.activa = true;
    }

    @Override
    public void actualizarAnimacion() {
        if (activa) {
            frameVida++;
            contadorFrames++;
            if (contadorFrames % 5 == 0) {
                frameActual = (frameActual + 1) % sprites.length;
            }

            if (frameVida > duracion) {
                desactivar();
            }
        }
    }
    
    public double getX() { return this.x; }
    public double getY() { return this.y; }
    public void desactivar() { this.activa = false; }
    public double getRadio() { return 50; }
}