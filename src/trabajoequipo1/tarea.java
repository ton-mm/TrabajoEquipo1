/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trabajoequipo1;

/**
 *
 * @author santoscr92
 */

import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class tarea extends JFrame implements Runnable, KeyListener,MouseListener,MouseMotionListener {
 
 private static final long serialVersionUID = 1L;
    // Se declaran las variables.
    private int direccion;    // Direccion del elefante
    private int velocidad,aceleracion; // determinado por vidas
    private final int MIN = -6;    //Minimo al generar un numero al azar.
    private final int MAX = 7;    //Maximo al generar un numero al azar.
    private Image dbImage;      // Imagen a proyectar  
    private Image gameover;
    private Graphics dbg;       // Objeto grafico
    private SoundClip sonido;    // Objeto AudioClip
    private SoundClip rat;    // Objeto AudioClip
    private SoundClip bomb;    //Objeto AudioClip
    private SoundClip teleport;
    private Bueno pelota;    // Objeto de la clase Bueno
    private Malo caja;    //Objeto de la clase Malo
    private int ancho;  //Ancho del elefante
    private int alto;   //Alto del elefante
    private ImageIcon elefante; // Imagen del elefante.
    private int x1; // posicion del mouse en x
    private int y1; // posicion del mouse en y
    private int x_pos;
    private int y_pos;
    private int vidas = 6;
    private int score = 0;
    private boolean pausa = false;
    private boolean clic = false; //para saber cuando hace clic
    private boolean up,down,right,left; //movimiento de teclado
    private boolean pchoco; // bool pelota choco
    private double angulo; // angulo de la pelota
    private double px,py; // posicion de la pelota con formula
    
    
    //Variables de control de tiempo de la animación
    private long tiempoActual;
    private long tiempoInicial;
    
    public tarea() {
        init();
        start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    public void init() {
        direccion = 0;
        this.setSize(900, 700);
        URL eURL = this.getClass().getResource("Imagenes/bola.png");
        int dposy = getHeight() / 2;
        pelota = new Bueno(15, dposy , Toolkit.getDefaultToolkit().getImage(eURL));
        //pelota.setPosX((int) (getWidth()/2));
        //pelota.setPosY(getHeight());
        int posrX =  getWidth()/2 ;    //posision x es tres cuartos del applet
        int posrY =   getHeight() ;  //posision y es tres cuartos del applet
        URL rURL = this.getClass().getResource("Imagenes/caja.png");
        caja = new Malo(posrX, posrY, Toolkit.getDefaultToolkit().getImage(rURL));
        caja.setPosX(caja.getPosX() - caja.getAncho());
        caja.setPosY(caja.getPosY() - caja.getAlto());
        setBackground(Color.white);
        addKeyListener(this);
        URL goURL = this.getClass().getResource("Imagenes/perder.png");
        gameover = Toolkit.getDefaultToolkit().getImage(goURL);
       
        //Se cargan los sonidos.
        sonido = new SoundClip("Sonidos/mice.wav");
        bomb = new SoundClip("Sonidos/Explosion.wav");
        teleport = new SoundClip("Sonidos/teleport.wav");
 
        
        elefante = new ImageIcon(Toolkit.getDefaultToolkit().getImage(eURL));
        ancho = elefante.getIconWidth();
        alto = elefante.getIconHeight();
        //ancho2 = caja.getIconWidth();
        // alto2 = caja.getIconHeight();
        addMouseListener(this);
        addMouseMotionListener(this);  
        
        // variable de la pelota
        angulo = (int)((Math.random() * (85 - 35)) + 35);
        
        
        //movimiento de pelota
        velocidad = (int) ((Math.random() * (7 - 3)) + 3);
        px =  velocidad * (Math.cos(angulo));
        
        
  
    }
 
    public void start() {
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    /**
     *
     */
    @Override
    public void run() {
        //Guarda el tiempo actual del sistema 
        tiempoActual = System.currentTimeMillis();
        
        while (vidas > 0) {
            actualiza();
            checaColision();
            repaint();    // Se actualiza el <code>Applet</code> repintando el contenido.
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }
 
        }
    }
    
    public void actualiza() {
        
         //Determina el tiempo que ha transcurrido desde que el Applet 
         //inicio su ejecución
         long tiempoTranscurrido=System.currentTimeMillis() - tiempoActual;
         //Guarda el tiempo actual
         tiempoActual += tiempoTranscurrido;
        
        if(left)
        {
            caja.setPosX(caja.getPosX() - 5);
            left = false;
        }
        else if(right)
        {
            caja.setPosX(caja.getPosX() + 5);
            right = false;
        }
        
        //si choca con la ventana se invierte la velocidad en X
        if(pchoco)
        {
            px *= -1;
            pchoco = false;
        }
        
        //py = velocidad * pelota.getPosY() * tiempoTranscurrido - (.98 * tiempoTranscurrido) * Math.pow(px, px)
        
        //actualiza la posicion de la pelota
        pelota.setPosX(pelota.getPosX() + (int) px);
        
        }
        
        
    
 
    public void checaColision() {
        
       /* 
        if (pelota.getPosX() + pelota.getAncho() > getWidth()) {
            pelota.setPosX(getWidth() - pelota.getAncho());
        }
        else if (pelota.getPosX() < 0) {
            pelota.setPosX(0);
        }
        else if (pelota.getPosY() + pelota.getAlto() > getHeight()) {
            pelota.setPosY(getHeight() - pelota.getAlto());
        }
        else if (pelota.getPosY() < 0) {
            pelota.setPosY(0);
        }
        */
        
        if(pelota.getPosX() + pelota.getAncho() >= getWidth() || pelota.getPosX() <= 0)
        {
            pchoco = true;
        }
        
        
    }
 
    public void paint(Graphics g) {
        // Inicializan el DoubleBuffer
        if (dbImage == null) 
        {
            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }
 
        // Actualiza la imagen de fondo.
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
 
        // Actualiza el Foreground.
        dbg.setColor(getForeground());
        paint1(dbg);
 
        // Dibuja la imagen actualizada
        g.drawImage(dbImage, 0, 0, this);
    }
    
    public void paint1(Graphics g) {
        if (vidas>0){
        if (pelota != null && caja != null) {
            //Dibuja la imagen en la posicion actualizada
            g.drawImage(pelota.getImagenI(), pelota.getPosX(), pelota.getPosY(), this);
            g.drawImage(caja.getImagenI(), caja.getPosX(), caja.getPosY(), this);
 
        } else {
            //Da un mensaje mientras se carga el dibujo
            g.drawString("No se cargo la imagen..", 20, 20);
        }
        }
        else{
            g.drawImage(gameover, 400, 150, this);
        }
        
        //
        g.drawImage(caja.getImagenI(), caja.getPosX(), caja.getPosY(), this);
        
        
        g.setColor(Color.black);
        //g.drawString("Vidas: " + vidas, 10, 20);
        g.drawString("Score: " + score, 70, 50); 
    }
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) //Presiono flecha arriba
        {    
            pausa = !pausa;
        }
       
        
        if (e.getKeyCode() == KeyEvent.VK_UP) //Presiono flecha arriba
        {    
            up = true;
	} 
        else if (e.getKeyCode() == KeyEvent.VK_DOWN) //Presiono flecha abajo
                {    
                    down = true;
		} 
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) //Presiono flecha izquierda
                {    
			left = true;
		} 
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) //Presiono flecha derecha
                {    
			right = true;
		}
        
         
    }
 
    /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    @Override
    public void keyTyped(KeyEvent e) {
 
    }
 
    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    @Override
    public void keyReleased(KeyEvent e) {
 
    }
 
     public void mouseClicked(MouseEvent e) {
        x1 = e.getX();
        y1 = e.getY();
        if(pelota.tiene(x1, y1))
        {
            clic = true;
        }
        
    }
 
    @Override
    public void mouseEntered(MouseEvent e) {
    }
 
    @Override
    public void mouseExited(MouseEvent e) {
    }
 
    @Override
    public void mousePressed(MouseEvent e) {
 
    }
 
    @Override
    public void mouseReleased(MouseEvent e) {
    }
 
    @Override
    public void mouseMoved(MouseEvent e) {
    }
 
    @Override
    public void mouseDragged(MouseEvent e) {
        
    }
    
        
    
}
