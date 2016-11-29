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
package edu.upc.moo.util;

import edu.upc.moo.gui.Ventana;
import edu.upc.moo.movil.ObjetoMovil;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GestorObjetos {
    
    public static final GestorObjetos INSTANCIA = new GestorObjetos();
    
    private Set<ObjetoMovil> objetos;
    
    private GestorObjetos() {
        objetos = new HashSet<ObjetoMovil>();
    }
            
    public void anyadir(ObjetoMovil o) {
        objetos.add(o);
    }
    
    public void eliminar(ObjetoMovil o) {
        objetos.remove(o);
    }
    
    public void moverYDibujarTodo(Ventana v) {
        // Para evitar la "concurrent Modification Exception", recorremos una copia de
        // la lista que se modificará
        ObjetoMovil[] copia = getArray();
        for(ObjetoMovil o : copia) {
            o.moverYDibujar(v);
        }
    }
    
    public ObjetoMovil[] getArray() {
        ObjetoMovil[] arr = new ObjetoMovil[objetos.size()];
        return objetos.toArray(arr);
    }
}
