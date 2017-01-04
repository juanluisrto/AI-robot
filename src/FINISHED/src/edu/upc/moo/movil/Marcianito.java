package edu.upc.moo.movil;


import edu.upc.moo.MarcianitosMain;
import edu.upc.moo.fisica.Colisionable;
import edu.upc.moo.gui.Ventana;
import edu.upc.moo.util.GestorObjetos;
import java.awt.Color;
import java.util.HashSet;

/*------------------------------------------------------------------------------
 * Este código está distribuido bajo una licencia del tipo BEER-WARE.
 * -----------------------------------------------------------------------------
 * Mario Macías Lloret escribió este archivo. Teniendo esto en cuenta,
 * puedes hacer lo que quieras con él: modificarlo, redistribuirlo, venderlo,
 * etc, aunque siempre deberás indicar la autoría original en tu código.
 * Además, si algún día nos encontramos por la calle y piensas que este código
 * te ha sido de utilidad, estás obligado a invitarme a una cerveza (a ser
 * posible, de las buenas) como recompensa por mi contribución.
 * -----------------------------------------------------------------------------
 */
public class Marcianito extends Colisionable implements ObjetoMovil {
    public static final double SALTO_BAJADA = 0.5;
    public static final double VELOCIDAD_INICIAL = 0.3;
    public static final int FRECUENCIA_DISPARO = 50;
    
    public static final double LONG_RADIO = 1;
    
    private double x,y;
    
    private double velocidad;
    private Nave nave;
        
    public Marcianito(double x, double y, Nave nave) {
        this.x = x;
        this.y = y;
        this.nave = nave;
        if(MarcianitosMain.RND.nextBoolean()) {
            velocidad = VELOCIDAD_INICIAL;
        } else {
            velocidad = -VELOCIDAD_INICIAL;
        }
    }
    
    public void moverYDibujar(Ventana ventana) {
        x += velocidad;
        if(x > MarcianitosMain.TAMANYO_EJES_VISION || x < -MarcianitosMain.TAMANYO_EJES_VISION) {
            velocidad = -velocidad;
            y -= SALTO_BAJADA;
        }
        if(MarcianitosMain.RND.nextInt(FRECUENCIA_DISPARO) == 0) {
            GestorObjetos.INSTANCIA.anyadir(new DisparoEnemigo(this.x, this.y, nave));
        }

        ventana.dibujaCirculo(x, y, LONG_RADIO, Color.green);
        ventana.dibujaCirculo(x-0.3, y+0.3, 0.2, Color.BLACK);
        ventana.dibujaCirculo(x+0.3, y+0.3, 0.2, Color.BLACK);
    }   
    
        @Override
    public double getOX() {
        return x;
    }

    @Override
    public double getOY() {
        return y;
    }

    @Override
    public double getR() {
        return LONG_RADIO;
    }
}
