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
package edu.upc.moo.fisica;

/**
 *
 * @author mmacias
 */
public abstract class Colisionable {
    public abstract double getOX();
    public abstract double getOY();
    public abstract double getR();
    
    public boolean colisiona(Colisionable otro) {
        double dx = getOX() - otro.getOX();
        double dy = getOY() - otro.getOY();
        return Math.sqrt(dx*dx+dy*dy) < (getR() + otro.getR());
    }
}
