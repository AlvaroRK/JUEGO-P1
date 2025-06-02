package juego;

import java.awt.*;
import javax.swing.*;

import entorno.Entorno;
import entorno.InterfaceJuego;
import juego.MenuLateral.Habilidad;

public class Juego extends InterfaceJuego {

    // === Atributos ===
    private Entorno entorno;
    private Image fondo;
    private MenuLateral menu;
    private boolean gameOver = false;

    private Personaje personaje;
    private Enemigo[] murcielagos;
    private Obstaculo[] rocas;

    private BolaDeFuego bola;
    private BurbujaProtectora burbuja;
    private ExplosionDeLuz luz;

    private boolean dañoBolaAplicado = false;
    private boolean dañoLuzAplicado = false;
    
    private int enemigosEliminados = 0;

    // === Constructor ===
    public Juego() {
        entorno = new Entorno(this, "Proyecto para TP 7", 800, 600);
        fondo = new ImageIcon(getClass().getResource("/imagenes/FondoTierra2D.png")).getImage();
        menu = new MenuLateral();
        personaje = new Personaje(300, 300);

        murcielagos = new Enemigo[10];
        for (int i = 0; i < murcielagos.length; i++)
            murcielagos[i] = generarMurcielagoAfuera();

        rocas = new Obstaculo[] {
            new Obstaculo(150, 200),
            new Obstaculo(150, 400),
            new Obstaculo(370, 540),
            new Obstaculo(450, 150),
            new Obstaculo(460, 390)
        };

        bola = new BolaDeFuego();
        burbuja = new BurbujaProtectora();
        luz = new ExplosionDeLuz();

        entorno.iniciar();
    }

    // === Loop principal ===
    public void tick() {
        entorno.dibujarImagen(fondo, 400, 300, 0);
        dibujarEscenario();

        controlarMovimiento();
        personaje.recuperarEnergia();

        actualizarEnemigos();
        controlarClicksYHabilidades();
        actualizarHabilidades();
        dibujarUI();

        if (personaje.estaMuerto() || enemigosEliminados >= 50)
            gameOver = true;
        if (gameOver) {
            mostrarGameOver();
            return;
        }

    }

    // === Escenario y UI ===
    private void dibujarEscenario() {
        for (Obstaculo roca : rocas)
            roca.dibujar(entorno);
        personaje.dibujar(entorno);
    }

    private void mostrarGameOver() {
        // Dibuja fondo negro por encima de todo
        entorno.dibujarRectangulo(400, 300, 800, 600, 0, Color.BLACK);

        // Texto
        entorno.cambiarFont("Arial", 32, Color.WHITE);
        if (enemigosEliminados >= 50) {
            entorno.escribirTexto("¡VICTORIA!", 300, 300);
            entorno.escribirTexto("Derrotaste a 50 enemigos", 250, 340);
        } else {
            entorno.escribirTexto("¡DERROTA!", 300, 300);
            entorno.escribirTexto("Gondolf murió", 290, 340);
        }
    }

    private void dibujarUI() {
        menu.dibujar(entorno,
                     personaje.getVida(),
                     personaje.getVidaMax(),
                     personaje.getEnergia(),
                     personaje.getEnergiaMax());
        menu.manejarEntrada(entorno);
        
        entorno.cambiarFont("Arial", 20, Color.WHITE);
        entorno.escribirTexto("Enemigos eliminados: " + enemigosEliminados, 220, 20);
    }

    // === Movimiento del personaje ===
    private void controlarMovimiento() {
        if (entorno.estaPresionada('a')) personaje.moverIzquierda(rocas);
        if (entorno.estaPresionada('d')) personaje.moverDerecha(rocas);
        if (entorno.estaPresionada('w')) personaje.moverArriba(rocas);
        if (entorno.estaPresionada('s')) personaje.moverAbajo(rocas);
    }

    // === Enemigos ===
    private void actualizarEnemigos() {
        for (int i = 0; i < murcielagos.length; i++) {
            Enemigo m = murcielagos[i];
            if (m == null) continue;

            m.moverHacia(personaje.getX(), personaje.getY());
            m.dibujar(entorno);

            double dx = personaje.getX() - m.getX();
            double dy = personaje.getY() - m.getY();
            double distancia = Math.sqrt(dx * dx + dy * dy);

            if (distancia < 30 && m.puedePegar(burbuja)) {
                personaje.recibirDaño(3);
                murcielagos[i] = generarMurcielagoAfuera();  // Reaparece otro
                continue; // Saltamos el resto del código, no suma eliminación
            }

            if (bola.estaActiva()) {
                double distBola = Math.hypot(bola.getX() - m.getX(), bola.getY() - m.getY());
                if (distBola < 30) {
                    m.recibirDaño(1);
                    if (m.estaMuerto()) {
                        enemigosEliminados++;
                        murcielagos[i] = generarMurcielagoAfuera();
                        personaje.agregarEnergia(10);
                    }
                }
            }

            if (luz.estaActiva()) {
                double distLuz = Math.hypot(luz.getX() - m.getX(), luz.getY() - m.getY());
                if (distLuz < 50) {
                    m.recibirDaño(1);
                    if (m.estaMuerto()) {
                        enemigosEliminados++;
                        murcielagos[i] = generarMurcielagoAfuera();
                        personaje.agregarEnergia(10);
                    }
                }
            }
        }
    }

    private Enemigo generarMurcielagoAfuera() {
        double x, y;
        int lado = (int) (Math.random() * 4);

        switch (lado) {
            case 0: x = -50;  y = Math.random() * 600; break;
            case 1: x = 850;  y = Math.random() * 600; break;
            case 2: x = Math.random() * 800; y = -50; break;
            default: x = Math.random() * 800; y = 650; break;
        }

        return new Enemigo(x, y);
    }

    // === Habilidades ===
    private void controlarClicksYHabilidades() {
        if (entorno.sePresionoBoton(entorno.BOTON_IZQUIERDO)) {
            int mx = entorno.mouseX();
            int my = entorno.mouseY();

            if (estaEnMenu(mx, my))
                menu.manejarClick(mx, my);
            else
                dispararHabilidad(mx, my);
        }

        if (entorno.sePresiono('1')) menu.setHabilidadActiva(Habilidad.BOLA);
        if (entorno.sePresiono('2')) menu.setHabilidadActiva(Habilidad.EXPLOSION);
        if (entorno.sePresiono('3')) menu.setHabilidadActiva(Habilidad.BURBUJA);
    }

    private boolean estaEnMenu(int x, int y) {
        return x >= 600 && x <= 800;
    }

    private void dispararHabilidad(int mouseX, int mouseY) {
        Habilidad seleccionada = menu.getHabilidadActiva();

        switch (seleccionada) {
            case BOLA:
                if (!bola.estaActiva())
                    bola.activar(personaje.getX(), personaje.getY(), mouseX, mouseY);
                break;

            case EXPLOSION:
                if (!luz.estaActiva() && personaje.consumirEnergia(30))
                    luz.activar(personaje.getX(), personaje.getY(), mouseX, mouseY);
                break;

            case BURBUJA:
                if (!burbuja.estaActiva() && !burbuja.estaExplotando() && personaje.consumirEnergia(20))
                    burbuja.activar(personaje.getX(), personaje.getY(), 0, 0);
                break;
                
            case NINGUNA:
                // No hacer nada si no hay habilidad seleccionada
                break;
        }
    }

    private void actualizarHabilidades() {
        // Bola de fuego
        if (bola.estaActiva()) {
            bola.mover();
            bola.actualizarAnimacion();
            bola.dibujar(entorno);

            if (bola.estaExplotando() && !dañoBolaAplicado) {
                aplicarDañoArea(bola.getX(), bola.getY(), bola.getRadio(), 3);
                dañoBolaAplicado = true;
            }
            if (!bola.estaActiva())
                dañoBolaAplicado = false;
        }

        // Explosión de luz
        if (luz.estaActiva()) {
            luz.actualizarAnimacion();
            luz.dibujar(entorno);

            if (!dañoLuzAplicado) {
                aplicarDañoArea(luz.getX(), luz.getY(), luz.getRadio(), 5);
                dañoLuzAplicado = true;
            }
            if (!luz.estaActiva())
                dañoLuzAplicado = false;
        }

        // Burbuja protectora
        burbuja.seguirPersonaje(personaje.getX(), personaje.getY());
        burbuja.actualizarAnimacion();
        burbuja.dibujar(entorno);
    }

    private void aplicarDañoArea(double cx, double cy, double radio, int daño) {
        for (int i = 0; i < murcielagos.length; i++) {
            Enemigo m = murcielagos[i];
            double dx = m.getX() - cx;
            double dy = m.getY() - cy;
            double distancia = Math.sqrt(dx * dx + dy * dy);
            if (distancia <= radio) {
                m.recibirDaño(daño);
                if (m.estaMuerto()) {
                    enemigosEliminados++;
                    murcielagos[i] = generarMurcielagoAfuera();
                }
            }
        }
    }

    // === Main ===
    public static void main(String[] args) {
        new Juego();
    }
}
