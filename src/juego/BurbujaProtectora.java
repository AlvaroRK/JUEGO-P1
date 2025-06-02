package juego;

import java.awt.Image;
import javax.swing.ImageIcon;
import entorno.Entorno;

public class BurbujaProtectora extends Habilidad {
    private Image[] explosionSprites;
    private int frameExplosion = 0;
    private boolean explotando = false;
    private long tiempoActivacion;

    public BurbujaProtectora() {
        super();
        cargarExplosionSprites();
    }

    @Override
    protected void cargarSprites() {
        sprites = new Image[1];
        sprites[0] = new ImageIcon(getClass().getResource("/imagenes/BurbujaProtectora/1.png")).getImage();
    }

    private void cargarExplosionSprites() {
        explosionSprites = new Image[5];
        for (int i = 0; i < explosionSprites.length; i++) {
            explosionSprites[i] = new ImageIcon(getClass().getResource("/imagenes/BurbujaProtectora/" + (i + 1) + ".png")).getImage();
        }
    }

    @Override
    public void activar(double origenX, double origenY, double destinoX, double destinoY) {
        this.x = origenX;
        this.y = origenY;
        this.frameActual = 0;
        this.contadorFrames = 0;
        this.activa = true;
        this.explotando = false;
        this.tiempoActivacion = System.currentTimeMillis();
    }

    public void seguirPersonaje(double jugadorX, double jugadorY) {
        if (activa) {
            this.x = jugadorX;
            this.y = jugadorY;

            if (System.currentTimeMillis() - tiempoActivacion >= 5000) {
                activa = false;
                explotando = true;
                frameExplosion = 0;
                contadorFrames = 0;
            }
        }
    }

    @Override
    public void actualizarAnimacion() {
        if (activa) {
            super.actualizarAnimacion();
        } else if (explotando) {
            contadorFrames++;
            if (contadorFrames % 5 == 0) {
                frameExplosion++;
                if (frameExplosion >= explosionSprites.length) {
                    explotando = false;
                }
            }
        }
    }

    @Override
    public void dibujar(Entorno entorno) {
        if (activa) {
            entorno.dibujarImagen(sprites[frameActual], x, y, 0, 1.1);
        } else if (explotando && frameExplosion < explosionSprites.length) {
            entorno.dibujarImagen(explosionSprites[frameExplosion], x, y, 0, 1.1);
        }
    }

    public boolean protege() {
        return activa;
    }

    public boolean estaExplotando() {
        return explotando;
    }
}