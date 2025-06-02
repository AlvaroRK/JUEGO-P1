package juego;

import java.awt.Image;
import javax.swing.ImageIcon;
import entorno.Entorno;

public class BolaDeFuego extends Habilidad {
    private Image[] spritesExplosión;
    private double destinoX, destinoY;
    private boolean explotando = false;
    private double angulo;

    @Override
    protected void cargarSprites() {
        // Sprites de la bola
        sprites = new Image[4];
        sprites[0] = new ImageIcon(getClass().getResource("/imagenes/bolaDeFuego/1.png")).getImage();
        sprites[1] = new ImageIcon(getClass().getResource("/imagenes/bolaDeFuego/2.png")).getImage();
        sprites[2] = new ImageIcon(getClass().getResource("/imagenes/bolaDeFuego/3.png")).getImage();
        sprites[3] = new ImageIcon(getClass().getResource("/imagenes/bolaDeFuego/4.png")).getImage();

        // Sprites de la explosión
        spritesExplosión = new Image[7];
        for (int i = 0; i < 7; i++) {
            spritesExplosión[i] = new ImageIcon(getClass().getResource("/imagenes/BolaDeFuego/" + (i + 5) + ".png")).getImage();
        }
    }

    @Override
    public void activar(double origenX, double origenY, double destinoX, double destinoY) {
        super.activar(origenX, origenY, destinoX, destinoY);
        this.destinoX = destinoX;
        this.destinoY = destinoY;
        this.explotando = false;
        this.angulo = Math.atan2(dy, dx) - Math.PI / 2;
    }

    @Override
    public void mover() {
        if (activa && !explotando) {
            x += dx;
            y += dy;

            double distancia = Math.sqrt(Math.pow(destinoX - x, 2) + Math.pow(destinoY - y, 2));
            if (distancia < 10) {
                explotando = true;
                frameActual = 0;
                contadorFrames = 0;
            }
        }
    }
    
    public boolean estaExplotando() {
        return this.explotando;
    }

    @Override
    public void actualizarAnimacion() {
        if (!activa) return;

        contadorFrames++;
        if (contadorFrames % 5 == 0) {
            frameActual++;

            if (explotando) {
                if (frameActual >= spritesExplosión.length) {
                    desactivar();
                    explotando = false;
                    frameActual = 0;
                }
            } else {
                if (frameActual >= sprites.length) {
                    frameActual = 0;
                }
            }
        }
    }

    @Override
    public void dibujar(Entorno entorno) {
        if (!activa) return;

        if (explotando) {
            entorno.dibujarImagen(spritesExplosión[frameActual], x, y, 0, 1.5);
        } else {
            entorno.dibujarImagen(sprites[frameActual], x, y, angulo, 1.2);
        }
    }
    
    public double getX() { return this.x; }
    public double getY() { return this.y; }
    public void desactivar() { this.activa = false; }
    public double getRadio() {
        return 30; // o el valor que consideres para el área de daño
    }
}