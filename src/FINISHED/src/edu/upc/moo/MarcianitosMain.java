package edu.upc.moo;

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

import edu.upc.moo.movil.Marcianito;
import edu.upc.moo.movil.Nave;
import edu.upc.moo.gui.Ventana;
import edu.upc.moo.util.GestorObjetos;
import java.awt.Color;
import java.util.HashSet;
import java.util.Random;

public class MarcianitosMain {
    
    public static final double TAMANYO_EJES_VISION = 10;
    public static final int ANCHO_VENTANA_PIXELS = 500;
    public static final int ALTO_VENTANA_PIXELS = 500;
    
    public static final double X_INICIAL_NAVE = 0;
    public static final double Y_INICIAL_NAVE = -9;

    public static final double Y_INICIAL_MARCIANOS = 9;

    public static final double ESPACIO_VERTICAL_ENTRE_MARCIANOS = 2;
    
    public static final int MARCIANOS_INICIALES = 15;
    public static final int FRECUENCIA_APARICION = 30;
    
    public static final Random RND = new Random();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Ventana ventana = new Ventana("Marcianitos!", ANCHO_VENTANA_PIXELS,ALTO_VENTANA_PIXELS);
               
        Nave nave = new Nave(X_INICIAL_NAVE, Y_INICIAL_NAVE);
        GestorObjetos.INSTANCIA.anyadir(nave);
        
        int fotogramas = 0;
        while(!ventana.isPulsadoEscape() && !nave.isDestruida()) {
            if(fotogramas % FRECUENCIA_APARICION == 0) {
                GestorObjetos.INSTANCIA.anyadir(new Marcianito(
                            RND.nextDouble()*2*TAMANYO_EJES_VISION-TAMANYO_EJES_VISION,
                            Y_INICIAL_MARCIANOS, nave));
            }
            GestorObjetos.INSTANCIA.moverYDibujarTodo(ventana);
            ventana.actualizaFotograma();
            fotogramas++;
        }
        
        while(!ventana.isPulsadoEscape()) {
            ventana.escribeTexto("GAME OVER", -5, 0, 2, Color.white );
            ventana.actualizaFotograma();
        }
        
        ventana.cerrar();
        
        
    }
    
}
