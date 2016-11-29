package edu.upc.moo.gui;

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

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * <p>Esta clase oculta toda la complejidad que hay detrás de la creación de ventanas,
 * lectura de las teclas de control, dibujo de primitivas gráficas, gestión de ratón, etc... La mayor
 * parte del código de esta clase es muy difícil de entender sólo con los conocimientos
 * de teoría de la asignatura MOO. La gracia de la programación Orientada a Objetos es
 * que no necesitas entenderlo para poder usar la clase; simplemente puedes limitarte
 * a entender las llamadas a sus métodos públicos y constructores. Si eres un machacas
 * y quieres aprender cosas por tu propia satisfacción, siéntete libre de investigar
 * cada línea de código, intentar modificarlo o ampliarlo.</p>
 *
 * <p>El modo de funcionamiento del dibujo es el siguiente: todo lo que se dibuje 
 * (mediante las funciones escribeTexto, dibujaCirculo, etc...), se irá dibujando en un lienzo oculto.
 * Una vez se ha acabado de dibujar el lienzo, se llamará al método "actualizaFotograma",
 * que mostrará el lienzo en la pantalla.</p>
 *
 * @author Mario Macías http://mario.site.ac.upc.edu
 */
public class Ventana {
    /**
     * Indica el número de fotogramas por segundo. Es decir, el máximo de veces
     * que se puede mostrar el lienzo por pantalla en un segundo.
     */
    private static final long FOTOGRAMAS_SEGUNDO = 30;

    //dos lienzos para el double buffer
    private Image lienzo ;
    
    private Graphics2D fg;

    private JFrame marcoVentana = null;

    private boolean up = false, down = false, left = false, right = false, space = false, escPulsado;
    private AffineTransform camara;

    private double camX, camY, campoVisionH, campoVisionV;
    private boolean dibujaCoordenadas = false;
    
    private double lienzoAnchura, lienzoAltura;
    private double aspectRatioH, aspectRatioV;
    
    private double ratonX, ratonY;
    private boolean ratonPulsado;
    private Color colorFondo = Color.black;
    /**
     * Crea una nueva ventana.<br/>
     * <b>NOTA</b>: Si se cierra la ventana con el ratón, el programa acabará.
     * @param titulo El texto que aparecerá en la barra de título de la ventana. 
     */
    public Ventana(String titulo) {
        this(titulo,640,480);
    }
    /**
     * Crea una nueva ventana.<br/>
     * <b>NOTA</b>: Si se cierra la ventana con el ratón, el programa acabará.
     * @param titulo El texto que aparecerá en la barra de título de la ventana.
     * @param ancho Anchura de la ventana en píxels
     * @param alto Altura de la ventana en píxels
     */
    public Ventana(String titulo, int ancho, int alto) {
        final JPanel pantalla = new JPanel();
        marcoVentana = new JFrame(titulo) {
                @Override
                public void paint(Graphics g) {
                    //super.paint(g);
                    pantalla.getGraphics().drawImage(lienzo, 0, 0, null);
                }
            };
        LectorRaton lr = new LectorRaton();
        pantalla.addMouseListener(lr);
        pantalla.addMouseMotionListener(lr);
        marcoVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marcoVentana.addWindowListener(new ControladorVentana());
        marcoVentana.setSize(ancho, alto);
        marcoVentana.setResizable(false);
        marcoVentana.setContentPane(pantalla);
        marcoVentana.setVisible(true);
        
        Rectangle bounds = pantalla.getBounds();
        lienzoAnchura = bounds.width;
        lienzoAltura = bounds.height;
        lienzo = new BufferedImage((int)lienzoAnchura, (int)lienzoAltura, BufferedImage.TYPE_INT_RGB);
        fg = (Graphics2D)lienzo.getGraphics();                
        
        camara = new AffineTransform(0,0,1,0,0,1);
        setCamara(0, 0, 20);
        fg.setTransform(camara);
        
        //borrarLienzoOculto();
        marcoVentana.addKeyListener(new LectorTeclas());

    }
    
    /**
     * Cambia las coordenadas y el campo de visi&oacute;n de la c&aacute;mara.
     * @param centroX Posici&oacute;n X donde apunta el centro de la c&aacute;mara
     * @param centroY Posici&oacute;n Y donde apunta el centro de la c&aacute;mara
     * @param tamanyoCampoVision El tama&ntilde; del eje menor que se ver&aacute; en pantalla (hay que contar que al no ser la ventana completamente cuadrada, se ver&aacute; m&aacute;s parte de un eje que del otro)
     */
    public final void setCamara(double centroX, double centroY, double tamanyoCampoVision) {
        camX = centroX; camY = centroY;
        
        if(lienzoAnchura > lienzoAltura) {
            aspectRatioH = lienzoAnchura / lienzoAltura;
            aspectRatioV = 1;
        } else {
            aspectRatioH = 1;
            aspectRatioV = lienzoAltura / lienzoAnchura;
        }
        campoVisionH = (tamanyoCampoVision * aspectRatioH);
        campoVisionV = (tamanyoCampoVision * aspectRatioV);
                        
        double min = Math.min(lienzoAnchura / tamanyoCampoVision, lienzoAltura / tamanyoCampoVision);
        camara.setToIdentity();
        camara.translate(lienzoAnchura/2, lienzoAltura/2);
        camara.scale(min, -min);
        camara.translate(-centroX, -centroY);
        
        fg.setTransform(camara);
        
    }
    
    /**
     * Comprueba si la flecha "Arriba" del cursor está pulsada o no.
     * @return true si está pulsada. false en caso contrario.
     */
    public boolean isPulsadoArriba() {
        return up;
    }
    /**
     * Comprueba si la flecha "Abajo" del cursor está pulsada o no.
     * @return true si está pulsada. false en caso contrario.
     */
    public boolean isPulsadoAbajo() {
        return down;
    }
    /**
     * Comprueba si la flecha "Izquierda" del cursor está pulsada o no.
     * @return true si está pulsada. false en caso contrario.
     */
    public boolean isPulsadoIzquierda() {
        return left;
    }
    /**
     * Comprueba si la flecha "Derecha" del cursor está pulsada o no.
     * @return true si está pulsada. false en caso contrario.
     */
    public boolean isPulsadoDerecha() {
        return right;
    }
    
    /**
     * Comprueba si la tecla "Escape" está pulsada o no.
     * @return  true si Escape está pulsado. False en caso contrario.
     */
    public boolean isPulsadoEscape() {
        boolean esc = escPulsado;
        escPulsado = false;
        return esc;
    }
    
    /**
     * Comprueba si la barra espaciadora está pulsada o no.
     * <b>NOTA:</b> a diferencia de los cursores, la barra espaciadora debe
     * soltarse y volver a pulsarse para que la función devuelva "true" dos veces.
     * @return true si está pulsada. false en caso contrario.
     */
    public boolean isPulsadoEspacio() {
        if(space) {
            space = false;
            return true;
        } else {
            return false;
        }
    }

    /** 
     * Muestra una cuadrícula con las coordenadas de la escena. Esta funcionalidad
     * puede ser útil en la etapa de desarrollo del programa, para ayudarnos
     * a colocar los diferentes objetos en pantalla.
     *
     * <p>También puedes habilitar o deshabilitar la cuadrícula pulsando la tecla
     * F1 cuando la ventana está abierta y actualizándose.
     * 
     * @param dibujaCoordenadas true si se quiere mostrar la cuadricula; false en caso contrario
     */
    public void setDibujaCoordenadas(boolean dibujaCoordenadas) {
        this.dibujaCoordenadas = dibujaCoordenadas;
    }    
    
    private void dibujaCoordenadas() {
        Stroke last = fg.getStroke();        

        float cv = (float)Math.min(campoVisionH,campoVisionV);
        Stroke flojo = new BasicStroke((float)(0.5/cv), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[] { 1.0f/cv, 5.0f/cv }, 0.0f);
        Stroke medio = new BasicStroke((float)(1/cv), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[] { 4.0f/cv, 5.0f/cv }, 0.0f);
        Stroke gordo = new BasicStroke((float)(1.3/cv), BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[] { 6f/cv, 2f/cv }, 0.0f);
        fg.setColor(Color.white);
        Line2D.Double l = new Line2D.Double();
        for(int x = (int) (camX - campoVisionH / 2) ; x <= (int) (camX + campoVisionH / 2) ; x++) {
            if(x % 10 == 0) {
                fg.setStroke(gordo);
                escribeTexto(String.valueOf(x), x + 0.1, camY + campoVisionV / 2 - 1.1, 1, Color.white);

            } else if(x % 5 == 0) {
                fg.setStroke(medio);
            } else {
                fg.setStroke(flojo);
            }
            l.setLine(x, camY - campoVisionV / 2, x, camY + campoVisionV / 2);            
            fg.draw(l);
            //fg.drawLine((int)x, (int)( camY - campoVisionV / 2), (int)x, (int)(camY + campoVisionV / 2));
        }   
        for(int y = (int) (camY - campoVisionV / 2) ; y <= (int) (camY + campoVisionV / 2) ; y++) {
            if(y % 10 == 0) {
                fg.setStroke(gordo);
                escribeTexto(String.valueOf(y),  camX - campoVisionH / 2 + 0.1, y + 0.1, 1, Color.white);
            } else if(y % 5 == 0) {
                fg.setStroke(medio);
            } else {
                fg.setStroke(flojo);
            }
            l.setLine(camX - campoVisionH / 2, y, camX + campoVisionH / 2, y);            
            fg.draw(l);
            //fg.drawLine((int)x, (int)( camY - campoVisionV / 2), (int)x, (int)(camY + campoVisionV / 2));
        }            
        fg.setStroke(last);
    }

    /**
     * Cierra la ventana.
     */
    public void cerrar() {
        marcoVentana.dispose();
    }

    private int ultimoTamanyo = 0;
    private Font ultimaFuente = new Font(Font.SANS_SERIF,Font.PLAIN,12);;
    /**
     * Escribe un texto por pantalla.
     * @param texto El texto a escribir.
     * @param x Coordenada izquierda del inicio del texto.
     * @param y Coordenada superior del inicio del texto.
     * @param medidaFuente Tamaño de la fuente, en píxels.
     * @param color Color del texto.
     */
    public void escribeTexto(String texto, double x, double y, int medidaFuente, Color color) {
        camara.scale(1, -1);
        fg.setTransform(camara);
        fg.setColor(color);
        if(ultimoTamanyo != medidaFuente) {
            ultimaFuente = new Font(Font.SANS_SERIF,Font.PLAIN,medidaFuente);
        }
        fg.setFont(ultimaFuente);
        fg.drawString(texto, (float)x, (float)-y);
        camara.scale(1, -1);
        fg.setTransform(camara);
    }

    /**
     * Dibuja un triángulo, dadas tres coordenadas en píxeles y un color.
     * @param x1,y1 Coordenadas x,y del primer punto.
     * @param x2,y2 Coordenadas x,y del segundo punto.
     * @param x3,y3 Coordenadas x,y del tercer punto.
     * @param color Color del triángulo.
     */
    public void dibujaTriangulo(double x1, double y1, double x2, double y2, double x3, double y3, Color color){
        fg.setColor(color);        
        Path2D.Double t = new Path2D.Double();
        
        t.moveTo(x1, y1);
        t.lineTo(x2, y2);
        t.lineTo(x3, y3);
        t.lineTo(x1, y1);
        fg.fill(t);        
    }
    
    /**
     * Dibuja un segmento de línea entre los puntos (x1,y2) y (x2, y2), con un grosor y un color dados.
     * @param x1,y1 Coordenadas x,y del primer punto 
     * @param x2,y2 Coordenadas x,y del segundo punto
     * @param grosor Grosor de la línea
     * @param color Color de la línea
     */
    public void dibujaLinea(double x1, double y1, double x2, double y2, double grosor, Color color) {
        Stroke last = fg.getStroke();        
        fg.setColor(color);
        fg.setStroke(new BasicStroke((float)grosor,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
        Line2D.Double l = new Line2D.Double();
        l.setLine(x1, y1, x2, y2);
        fg.draw(l);
        fg.setStroke(last);
    }
    /**
     * Dibuja un rectángulo en pantalla, dadas las coordenadas de su esquina superior izquierda,
     * su anchura y su altura.
     * 
     * @param izquierda Coordenada del lado más a la izquierda del rectángulo.
     * @param arriba Coordenada del lado superior del rectángulo.
     * @param ancho Anchura del rectángulo, en pixels.
     * @param alto Altura del rectángulo, en píxels.
     * @param color Color del rectángulo.
     */
    public void dibujaRectangulo(double izquierda, double arriba, double ancho, double alto, Color color) {
        fg.setColor(color);
        fg.fill(new Rectangle2D.Double(izquierda, arriba - alto, ancho, alto));
    }

    /**
     * Dibuja un círculo por pantalla.
     * @param centroX Coordenada X del centro del círculo (en píxels).
     * @param centroY Coordenada Y del centro del círculo (en píxels).
     * @param radio Radio del círculo, en píxels.
     * @param color Color del círculo.
     */
    public void dibujaCirculo(double centroX, double centroY, double radio, Color color) {
        fg.setColor(color);
        fg.fill(new Ellipse2D.Double((centroX - radio), (centroY - radio), (radio*2),(radio*2)));
    }

    /**
     * Muestra el fotograma actual por pantalla. Además, crea un nuevo fotograma oculto
     * sobre el que se irá dibujando, y que se mostrará en la siguiente llamada al método
     * actualizaFotograma().
     */
    public void actualizaFotograma() {
        //dibuja cuadricula
        if(dibujaCoordenadas) dibujaCoordenadas();
        
        // muestra el buffer
        marcoVentana.repaint();

        espera();
        
        //borra el buffer
        fg.setTransform(identity);        
        fg.setColor(colorFondo);
        fg.fillRect(0, 0, (int)lienzoAnchura, (int)lienzoAltura);
        fg.setTransform(camara);
        

        
    }

    /**
     * Cambia el color del fondo de la ventana.
     * 
     * @param colorFondo Color del fondo de la ventana.
     */
    public void setColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
    }
    
    
    
    private AffineTransform identity = new AffineTransform();

    private long lastFrameTime = 0;

    private void espera() {         
        long now = System.currentTimeMillis();
        try {
            long sleepTime = (1000 / FOTOGRAMAS_SEGUNDO) - (now - lastFrameTime);
            if(sleepTime <= 0) {
                Thread.yield();
            } else {
                Thread.sleep(sleepTime);
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Ventana.class.getName()).log(Level.SEVERE, null, ex);
        }
        lastFrameTime = System.currentTimeMillis();
    }

    private class ControladorVentana implements WindowListener {
        public void windowClosed(WindowEvent e) {
        }
        public void windowActivated(WindowEvent e) {}
        public void windowClosing(WindowEvent e) {
            cerrar();
        }
        public void windowDeactivated(WindowEvent e) {}
        public void windowDeiconified(WindowEvent e) {}
        public void windowIconified(WindowEvent e) {}
        public void windowOpened(WindowEvent e) {}
    }

    private class LectorTeclas implements KeyListener {
        private boolean spaceReleased = true;

        public void keyTyped(KeyEvent e) {
    
        }

        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    up = true;
                    break;
                case KeyEvent.VK_DOWN:
                    down = true;
                    break;
                case KeyEvent.VK_LEFT:
                    left = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    right = true;
                    break;
                case KeyEvent.VK_SPACE:
                    if (spaceReleased) {
                        space = true;
                    }
                    spaceReleased = false;
                    break;
                case KeyEvent.VK_ESCAPE:
                    escPulsado = true;
                    break;
                case KeyEvent.VK_F1:
                    dibujaCoordenadas = !dibujaCoordenadas;
                    break;
            }
        }

        public void keyReleased(KeyEvent e) {
            switch(e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    up = false;
                    break;
                case KeyEvent.VK_DOWN:
                    down = false;
                    break;
                case KeyEvent.VK_LEFT:
                    left = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    right = false;
                    break;
                case KeyEvent.VK_SPACE:
                    spaceReleased = true;
                    space = false;
                    break;
            }
        }

    }

    /**
     * Devuelve true si el bot&oacute;n izquierdo del rat&oacute;n est&aacute; pulsado.
     * @return true si el bot&oacute;n izquierdo del rat&oacute;n est&aacute; pulsado.
     */
    public boolean isRatonPulsado() {
        if(ratonPulsado) {
            ratonPulsado = false;
            return true;
        }
        return false;
    }

    /**
     * Devuelve la coordenada X del rat&oacute;n
     * @return la coordenada X del rat&oacute;n
     */
    public double getRatonX() {
        return mouseEventPos == null ? 0 :
                ((double)mouseEventPos.getX() * campoVisionH) / lienzoAnchura - campoVisionH / 2 + camX;
    }

    /**
     * Devuelve la coordenada Y del rat&oacute;n
     * @return la coordenada Y del rat&oacute;n
     */    
    public double getRatonY() {
        return mouseEventPos == null ? 0 :
                - ((double)mouseEventPos.getY() * campoVisionV) / lienzoAltura + campoVisionV / 2 + camY;
    }
    
    
    private MouseEvent mouseEventPos = null;
    private class LectorRaton implements MouseListener, MouseMotionListener {
        @Override 
        public void mousePressed(MouseEvent me) {
            ratonPulsado = true;
        }
        @Override
        public void mouseReleased(MouseEvent me) {
            ratonPulsado = false;
        }
        @Override
        public void mouseMoved(MouseEvent me) {
            mouseEventPos = me;
        }
        @Override public void mouseDragged(MouseEvent me) {
            mouseEventPos = me;
        }
        
        @Override public void mouseEntered(MouseEvent me) { }
        @Override public void mouseExited(MouseEvent me) { }
        @Override public void mouseClicked(MouseEvent me) {}
    }
}
