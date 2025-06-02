package juego;

import java.awt.Image;
import entorno.Entorno;

public abstract class Habilidad {
    // --- Atributos ---
    protected Image[] sprites;
    protected int frameActual = 0;
    protected int contadorFrames = 0;

    protected boolean activa = false;
    protected double x, y;
    protected double dx, dy;
    protected double velocidad = 5;

    // --- Constructor ---
    public Habilidad() {
        cargarSprites();
    }

    // --- Métodos abstractos ---
    protected abstract void cargarSprites();

    // --- Métodos públicos ---
    public void activar(double origenX, double origenY, double destinoX, double destinoY) {
        this.x = origenX;
        this.y = origenY;
        this.frameActual = 0;
        this.contadorFrames = 0;
        this.activa = true;

        // Calcular dirección normalizada
        double distancia = Math.sqrt(Math.pow(destinoX - origenX, 2) + Math.pow(destinoY - origenY, 2));
        this.dx = velocidad * (destinoX - origenX) / distancia;
        this.dy = velocidad * (destinoY - origenY) / distancia;
    }

    public void mover() {
        if (activa) {
            x += dx;
            y += dy;
        }
    }

    public void actualizarAnimacion() {
        if (activa) {
            contadorFrames++;
            if (contadorFrames % 5 == 0) {
                frameActual = (frameActual + 1) % sprites.length;
            }
        }
    }

    public void dibujar(Entorno entorno) {
        if (activa && sprites != null && sprites.length > 0) {
            entorno.dibujarImagen(sprites[frameActual], x, y, 0);
        }
    }

    public boolean estaActiva() {
        return activa;
    }

    public void desactivar() {
        activa = false;
    }
}
