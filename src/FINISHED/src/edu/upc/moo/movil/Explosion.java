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

import edu.upc.moo.gui.Ventana;
import edu.upc.moo.util.GestorObjetos;
import java.awt.Color;

/**
 *
 * @author mmacias
 */
public class Explosion implements ObjetoMovil {

    private double x, y;
    private double radio;
    private final static double MAX_RADIO = 1.5;
    private final static double VELOCIDAD_EXPANSION = 0.3;

    public Explosion(double x, double y) {
        this.x = x;
        this.y = y;
        radio = 0;
    }
    
    
    @Override
    public void moverYDibujar(Ventana v) {
        radio += VELOCIDAD_EXPANSION;
        v.dibujaCirculo(x, y, radio, Color.YELLOW);
        if(radio > MAX_RADIO) {
            GestorObjetos.INSTANCIA.eliminar(this);
        }
    }
        
}
