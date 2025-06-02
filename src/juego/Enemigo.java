package juego;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class Enemigo {
    // --- Atributos ---
    private double x, y;
    private final double velocidad = 1.5;

    private Image[] sprites;
    private int frameActual = 0;
    private int contadorFrames = 0;

    private long ultimoGolpe = 0;
    private final long intervaloGolpe = 500;
    
    private int vida;
    private int vidaMax;

    // --- Constructor ---
    public Enemigo(double x, double y) {
        this.x = x;
        this.y = y;
        
        this.vidaMax = 10; // Cambiá este valor si querés que tenga más o menos vida
        this.vida = vidaMax;

        sprites = new Image[4];
        sprites[0] = Herramientas.cargarImagen("imagenes/Bat1.png");
        sprites[1] = Herramientas.cargarImagen("imagenes/Bat2.png");
        sprites[2] = Herramientas.cargarImagen("imagenes/Bat3.png");
        sprites[3] = Herramientas.cargarImagen("imagenes/Bat4.png");
    }

    // --- Dibujo y animación ---
    public void dibujar(Entorno entorno) {
        entorno.dibujarImagen(sprites[frameActual], x, y, 0, 0.5);

        // Barra de vida encima del enemigo
        if (vida > 0) {
            int anchoMax = 30;
            int alto = 5;
            double porcentaje = (double) vida / vidaMax;
            int anchoVida = (int)(anchoMax * porcentaje);

            entorno.dibujarRectangulo(x, y - 25, anchoMax, alto, 0, java.awt.Color.GRAY);
            entorno.dibujarRectangulo(x - (anchoMax - anchoVida)/2, y - 25, anchoVida, alto, 0, java.awt.Color.RED);
        }

        contadorFrames++;
        if (contadorFrames % 10 == 0) {
            frameActual = (frameActual + 1) % 2;
            contadorFrames = 0;
        }
    }

    // --- Movimiento hacia el personaje ---
    public void moverHacia(double jugadorX, double jugadorY) {
        double dx = jugadorX - x;
        double dy = jugadorY - y;
        double distancia = Math.sqrt(dx * dx + dy * dy);

        if (distancia != 0) {
            x += velocidad * dx / distancia;
            y += velocidad * dy / distancia;
        }
    }

    // --- Verificación de ataque ---
    public boolean puedePegar(BurbujaProtectora burbuja) {
        if (burbuja != null && burbuja.protege()) {
            return false; // Protegido por burbuja
        }

        long ahora = System.currentTimeMillis();
        if (ahora - ultimoGolpe >= intervaloGolpe) {
            ultimoGolpe = ahora;
            return true;
        }

        return false;
    }

    // --- Getters ---
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    public void recibirDaño(int cantidad) {
        this.vida -= cantidad;
        if (this.vida < 0) {
            this.vida = 0;
        }
    }

    public boolean estaMuerto() {
        return this.vida <= 0;
    }

    public int getVida() {
        return this.vida;
    }
}
