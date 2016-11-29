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
package edu.upc.moo.movil;

import edu.upc.moo.fisica.Colisionable;
import edu.upc.moo.gui.Ventana;
import edu.upc.moo.util.GestorObjetos;
import java.awt.Color;
import java.util.Random;

/**
 *
 * @author mmacias
 */
public class DisparoEnemigo extends Colisionable implements ObjetoMovil {
      public static final double RADIO = 0.3;
    public static final double MAX_VELOCIDAD_BASE = 0.6;
    public static final double MIN_VELOCIDAD_BASE = 0.2;
    private static final Random RND = new Random();
    
    private static final double MAX_DISTANCIA = -12;
    
    private double x, y;
    private double vx, vy;
    private Nave nave;
    
    public DisparoEnemigo(double x, double y, Nave nave) {
        this.nave = nave;
        this.x = x;
        this.y = y;        
        double angle = Math.atan((nave.getOY()-y)/(nave.getOX()-x));
        double velocidadBase = MIN_VELOCIDAD_BASE + RND.nextDouble() * (MAX_VELOCIDAD_BASE - MIN_VELOCIDAD_BASE);
        vx =  velocidadBase * Math.cos(angle);
        vy = velocidadBase * Math.sin(angle);  
        if((nave.getOX()-x) < 0) {
            vx = -vx;
            vy = -vy;
        }
    }
    
    public void moverYDibujar(Ventana v) {
        x += vx;
        y += vy;
        v.dibujaCirculo(x, y, RADIO, Color.red);
        if(y < MAX_DISTANCIA) {
            GestorObjetos.INSTANCIA.eliminar(this);        
        }
        if(colisiona(nave)) {
            nave.setDestruida(true);
        }
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
        return RADIO;
    }
}
