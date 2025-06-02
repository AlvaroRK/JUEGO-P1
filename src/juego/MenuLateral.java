package juego;

import java.awt.*;
import javax.swing.*;
import entorno.Entorno;

public class MenuLateral {

    // --- Enum para las habilidades ---
    public enum Habilidad {
        NINGUNA, BOLA, EXPLOSION, BURBUJA
    }

    // --- Atributos ---
    private Image iconoBurbuja;
    private Image iconoBola;
    private Image iconoExplosion;
    private Habilidad habilidadActiva;

    // --- Constructor ---
    public MenuLateral() {
        this.iconoBurbuja = new ImageIcon(getClass().getResource("/imagenes/BurbujaProtectora.png")).getImage();
        this.iconoBola = new ImageIcon(getClass().getResource("/imagenes/BolaDeFuego.png")).getImage();
        this.iconoExplosion = new ImageIcon(getClass().getResource("/imagenes/ExplosionDeLuz.png")).getImage();
        this.habilidadActiva = Habilidad.NINGUNA;
    }
    
    public Habilidad getHabilidadActiva() {
        return this.habilidadActiva;
    }
    
    // --- Setear habilidad activa ---
    public void setHabilidadActiva(Habilidad hab) {
        this.habilidadActiva = hab;
    }

    
    // Boton de habilidad con mouse
    public void manejarClick(double mouseX, double mouseY) {
        // Verificamos que el clic esté dentro del menú lateral (lado derecho)
        if (mouseX > 620 && mouseX < 780) {
            if (mouseY > 240 && mouseY < 280) {
                setHabilidadActiva(Habilidad.BOLA);
            } else if (mouseY > 300 && mouseY < 340) {
                setHabilidadActiva(Habilidad.EXPLOSION);
            } else if (mouseY > 360 && mouseY < 400) {
                setHabilidadActiva(Habilidad.BURBUJA);
            } else if (mouseY > 550 && mouseY < 590) { // Botón Salir
                System.exit(0);
            }
        }
    }

    // --- Dibujar menú lateral ---
    public void dibujar(Entorno entorno, int vida, int vidaMax, int energia, int energiaMax) {
        // Fondo menú
        entorno.dibujarRectangulo(700, 300, 200, 600, 0, Color.DARK_GRAY);

        // Botones de habilidad
        String[] nombres = { "Bola de fuego", "Explosion de luz", "Burbuja protectora" };
        int[] posicionesY = { 260, 320, 380 };
        int[] posicionesX = { 650, 645, 635 };

        for (int i = 0; i < nombres.length; i++) {
            entorno.dibujarRectangulo(700, posicionesY[i], 160, 40, 0, Color.GRAY);
            entorno.cambiarFont("Arial", 16, Color.WHITE);
            entorno.escribirTexto(nombres[i], posicionesX[i], posicionesY[i] + 5);
        }

        // Botón salir
        entorno.dibujarRectangulo(740, 570, 80, 40, 0, Color.RED);
        entorno.cambiarFont("Arial", 18, Color.WHITE);
        entorno.escribirTexto("Salir", 720, 575);

        // Barra de vida
        int anchoMax = 160;
        int alto = 20;
        int xInicio = 620;
        int yVida = 420;

        double porcentaje = (double) vida / vidaMax;
        int anchoVida = (int) (anchoMax * porcentaje);

        entorno.dibujarRectangulo(xInicio + anchoMax / 2, yVida, anchoMax, alto, 0, Color.GRAY);

        if (vida > 0) {
            entorno.dibujarRectangulo(xInicio + anchoVida / 2, yVida, anchoVida, alto, 0, Color.RED);
        }
        entorno.cambiarFont("Arial", 14, Color.WHITE);
        entorno.escribirTexto("Vida: " + vida + "/" + vidaMax, xInicio + 35, yVida + 4);

     // --- Barra de energía ---
        int yEnergia = 450;
        int altoEnergia = 20;

        double porcentajeEnergia = (double) energia / energiaMax;
        int anchoEnergia = (int) (anchoMax * porcentajeEnergia);

        // Fondo gris
        entorno.dibujarRectangulo(xInicio + anchoMax / 2, yEnergia, anchoMax, altoEnergia, 0, Color.GRAY);

        // Barra azul (energía)
        if (energia > 0) {
            entorno.dibujarRectangulo(xInicio + anchoEnergia / 2, yEnergia, anchoEnergia, altoEnergia, 0, Color.CYAN);
        }

        // Texto de energía
        entorno.cambiarFont("Arial", 14, Color.WHITE);
        entorno.escribirTexto("Energía: " + energia + "/" + energiaMax, xInicio + 25, yEnergia + 5);

        // Cuadro habilidad activa
        entorno.dibujarRectangulo(700, 120, 140, 140, 0, Color.GRAY);

        // Dibujo del icono de habilidad activa y resaltar botón
        switch (habilidadActiva) {
            case BOLA:
                entorno.dibujarImagen(iconoBola, 700, 120, 0, 2);
                resaltarBoton(entorno, 260, "Bola de fuego", 650, 265);
                break;
            case EXPLOSION:
                entorno.dibujarImagen(iconoExplosion, 700, 120, 0, 2);
                resaltarBoton(entorno, 320, "Explosion de luz", 645, 325);
                break;
            case BURBUJA:
                entorno.dibujarImagen(iconoBurbuja, 700, 120, 0, 2);
                resaltarBoton(entorno, 380, "Burbuja protectora", 635, 385);
                break;
            default:
                break;
        }
    }

    // --- Método para resaltar el botón seleccionado ---
    private void resaltarBoton(Entorno entorno, int y, String texto, int xTexto, int yTexto) {
        entorno.dibujarRectangulo(700, y, 160, 40, 0, Color.ORANGE);
        entorno.cambiarFont("Arial", 16, Color.BLACK);
        entorno.escribirTexto(texto, xTexto, yTexto);
    }

    // --- Manejo de entrada para salir ---
    public void manejarEntrada(Entorno entorno) {
        if (entorno.estaPresionada('n')) {
            System.out.println("Salir");
            System.exit(0);
        }
    }
}
