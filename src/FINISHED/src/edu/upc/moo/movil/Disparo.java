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
import java.util.Iterator;

public class Disparo extends Colisionable implements ObjetoMovil {
    public static final double RADIO = 0.3;
    public static final double VELOCIDAD = 0.8;
    
    private static final double MAX_DISTANCIA = 10;
    
    private double x, y;
    
    public Disparo(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void moverYDibujar(Ventana v) {
        y += VELOCIDAD;
        v.dibujaCirculo(x, y, RADIO, Color.yellow);

        for(ObjetoMovil om : GestorObjetos.INSTANCIA.getArray()) {
            if(om instanceof Marcianito) {
                if(colisiona((Colisionable)om)) {
                    GestorObjetos.INSTANCIA.eliminar(this);
                    GestorObjetos.INSTANCIA.eliminar(om);
                    GestorObjetos.INSTANCIA.anyadir(
                            new Explosion(((Colisionable)om).getOX(),
                                    ((Colisionable)om).getOY()));
                }
            }
        }
        
        if(y > MAX_DISTANCIA) {
            GestorObjetos.INSTANCIA.eliminar(this);
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
