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
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException; 
import java.util.Vector;
import java.io.BufferedReader;

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
    private SoundClip yay;    // Objeto AudioClip
    private SoundClip buuu;    // Objeto AudioClip
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
    private int vidas = 5;
    private int score = 0;
    private boolean pausa = false;
    private boolean clic = false; //para saber cuando hace clic
    private boolean up,down,right,left; //movimiento de teclado
    private boolean pchoco; // bool pelota choco
    private double angulo; // angulo de la pelota
    private double px,py; // posicion de la pelota con formula
    private int intentos;
    private double velocidadx,velocidady;
    private double gravedad = 9.8;
    private double tiempo;
    private boolean btiempo;
    private boolean instrucciones = false;
    private boolean inst = false;
    private boolean pclic = false;
    
    //variables para el manejo de archivos
    private Vector vec;    // Objeto vector para agregar el puntaje.
    private String nombreArchivo;    //Nombre del archivo.
    private String[] arr;    //Arreglo del archivo divido.
    
    private boolean guardar = false; //bool para saber si se quiere guardar el juego
    private boolean cargar = false; //bool para saber si se quiere cargar el juego
    
    
    //Variables de control de tiempo de la animación
    private long tiempoActual;
    private long tiempoInicial;
    
    public tarea() {
        init();
        start();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    public void init() {
        
        nombreArchivo = "Puntaje.txt";
        vec = new Vector();
        
        
        direccion = 0;
        this.setSize(1000, 700);
        URL eURL = this.getClass().getResource("Imagenes/bola.png");
        int dposy = getHeight() / 2 + getHeight() / 8;
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
        yay = new SoundClip("Sonidos/yay.wav");
        buuu = new SoundClip("Sonidos/buuu.wav");
        teleport = new SoundClip("Sonidos/teleport.wav");
 
        elefante = new ImageIcon(Toolkit.getDefaultToolkit().getImage(eURL));
        ancho = elefante.getIconWidth();
        alto = elefante.getIconHeight();
        //ancho2 = caja.getIconWidth();
        // alto2 = caja.getIconHeight();
        addMouseListener(this);
        addMouseMotionListener(this); 
        
        // variable de la pelota
        angulo = (int) ((Math.random() * (60 - 45)) + 45);
 
        //movimiento de pelota en x
        velocidad = (int) ((Math.random() * (6 - 4)) + 4);
        
        
        
        //movimiento de pelota en y
        //py = -5 + 1 * tiempoActual;
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
        
        
         while (vidas > 0) {
            if (!pausa) {
                actualiza();
                checaColision();
                repaint(); // Se actualiza el <code>Applet</code> repintando el contenido.
            }
            try {
                // El thread se duerme.
                Thread.sleep(20);
            } catch (InterruptedException ex) {
                System.out.println("Error en " + ex.toString());
            }

        }

    }
    
    public void actualiza() {
        
         
        // tiempo de jframe
        if(btiempo)
        {
           tiempo += .020; 
        }
       
        if(intentos == 3)
        {
            vidas--;
            intentos = 0;
            velocidad += 1;
        }
        
        if(!pausa)
        {
            if(clic)
            {
            
                velocidadx =  velocidad * (Math.sin(Math.toRadians(angulo)));
        
                //velocidad de y
                velocidady = -velocidad + 2.5 * tiempo;
            
                //boolean para tiempo
                btiempo = true;
            
                //actualziacion de posicion
                pelota.setPosX(pelota.getPosX() + (int) velocidadx);
                pelota.setPosY(pelota.getPosY() + (int) velocidady);
            } 
        }
        
        
        
        
        
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
        
        
        
        
        //limita el movimiento de la caja 
        if(caja.getPosX() <= getWidth()/3)
        {
            caja.setPosX(getWidth()/3);
        }
        if(caja.getPosX() + caja.getAncho() >= getWidth())
        {
            caja.setPosX(getWidth() - caja.getAncho());
        }
    
 }
        
        
    
 
    public void checaColision() {
        
       
        if(pelota.getPosX() + pelota.getAncho() >= getWidth() || pelota.getPosX() <= 0)
        {
            pchoco = true;
        }
        
        
         if (pelota.getPosX() + pelota.getAncho() >= getWidth()) {
            int posrX = (int) (0);    // posicion en x es un cuarto del applet
            int posrY = (int) (getHeight() / 2 + getHeight() / 8);    // posicion en y es un cuarto del applet
            pelota.setPosX(posrX);
            pelota.setPosY(posrY);
            btiempo = false;
            tiempo = 0;
            clic = false;
            // variable de la pelota
            angulo = (int) ((Math.random() * (60 - 45)) + 45);
 
            //movimiento de pelota en x
            velocidad = (int) ((Math.random() * (6 - 4)) + 4);
            
            // sonido de buuu
            buuu.play();
        }
        if (pelota.getPosY() + pelota.getAlto() >= getHeight()) {
            int posrX = (int) (0);    // posicion en x es un cuarto del applet
            int posrY = (int) (getHeight() / 2 + getHeight() / 8);    // posicion en y es un cuarto del applet
            pelota.setPosX(posrX);
            pelota.setPosY(posrY);
            intentos++;
            btiempo = false;
            tiempo = 0;
            clic = false;
            // variable de la pelota
            angulo = (int) ((Math.random() * (60 - 45)) + 45);
 
            //movimiento de pelota en x
            velocidad = (int) ((Math.random() * (6 - 4)) + 4);
            
            // sonido de buuu
            buuu.play();
        }
        
         if (pelota.intersecta(caja) && (pelota.getPosY() + pelota.getAlto() - 5) < caja.getPosY()) {
            int posrX = (int) (0);    // posicion en x es un cuarto del applet
            int posrY = (int) (getHeight() / 2 + getHeight() / 8);    // posicion en y es un cuarto del applet
            pelota.setPosX(posrX);
            pelota.setPosY(posrY);
            score += 2;
            btiempo = false;
            tiempo = 0;
            clic = false;
            // variable de la pelota
            angulo = (int) ((Math.random() * (60 - 45)) + 45);
 
            //movimiento de pelota en x
            velocidad = (int) ((Math.random() * (6 - 4)) + 4);
            //sonido de yay
            yay.play();
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
        g.drawString("Vidas: " + vidas, 15, 50);
        g.setColor(Color.black);
        g.drawString("Score: " + score, 70, 50); 
        g.setColor(Color.black);
        g.drawString("Intentos: " + intentos, 140, 50);
        
        /*if (pausa) {
                    g.setColor(Color.white);
                    g.drawString(caja.getpausado(), caja.getPosX() + caja.getAncho() / 3, caja.getPosY() + caja.getAlto() / 2);
                }
        */
                if (instrucciones ) {
                
               g.setColor(Color.black);
               g.drawString("INSTRUCCIONES: Para empezar el juego dar click a la pelota. Intenta cachar", 200, 200); 
                    g.drawString(    "la pelota con la caja. Mueve la caja con las flechas IZQ y DER", 200, 212); 
                     g.drawString(   "Para pausar el juego presiona 'p' ", 200, 225); 
                     g.drawString(   "Para guardar el juego presiona 'g'", 200, 238); 
                    g.drawString(    "Para cargar el juego presiona 'c'  ", 200, 250); 
            }
 
    }
    /**
     * Metodo que lee a informacion de un archivo y lo agrega a un vector.
     *
     * @throws IOException
     */
    public void leeArchivo()  {
          try
          {
                BufferedReader fileIn;
                try {
                        fileIn = new BufferedReader(new FileReader(nombreArchivo));
                } catch (FileNotFoundException e){
                        File archivo = new File(nombreArchivo);
                        PrintWriter fileOut = new PrintWriter(archivo);
                        fileOut.println("50.0,50.0,45.0,.02,5,.02,50,50,200,200");
                        fileOut.close();
                        fileIn = new BufferedReader(new FileReader(nombreArchivo));
                }
                String dato = fileIn.readLine();
                arr = dato.split (",");
                velocidadx = (Double.parseDouble(arr[0]));
                velocidady = (Double.parseDouble(arr[1]));
                angulo = (Double.parseDouble(arr[2]));
                tiempo = (Double.parseDouble(arr[3]));
                vidas = (Integer.parseInt(arr[4]));
                //dificultad = (Double.parseDouble(arr[5]));
                pelota.setPosX((Integer.parseInt(arr[5])));
                pelota.setPosY((Integer.parseInt(arr[6])));
                caja.setPosX((Integer.parseInt(arr[7])));
                caja.setPosY((Integer.parseInt(arr[8])));
                //perdida = (Integer.parseInt(arr[10]));
                //pico = (Boolean.parseBoolean(arr[11]));
                
                fileIn.close();
          }
          catch(IOException ioe) {
              velocidadx = 0;
              velocidady = 0;
              angulo = 0;
              tiempo = 0;
              vidas = 0;
              //dificultad = 0;
              pelota.setPosX(0);
              pelota.setPosY(0);
              caja.setPosX(0);
              caja.setPosY(0);
              //perdida = 0;
              //pico = false;
              
              
          }
        }
    
    /**
     * Metodo que agrega la informacion del vector al archivo.
     *
     * @throws IOException
     */
    public void grabaArchivo() throws IOException{
        
        //if(guardar) {
            
            
            PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));
            fileOut.println(""+velocidadx+","+velocidady+","+angulo+","+tiempo+","+vidas+","+pelota.getPosX()+","+pelota.getPosY()+","+caja.getPosX()+","+caja.getPosY());
            fileOut.close();
           
           // guardar = false;
        //}
   	
    }
    
    
    
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_P) //Presiono tecla P
        {    
            pausa = !pausa;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_I) //Presiono tecla I
        {    
            instrucciones = !instrucciones;
            //pausa = !pausa;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_G) //Presiono tecla G
        {  
            try{
			//leeArchivo();    //lee el contenido del archivo
			//vec.add(new Puntaje(nombre,score));    //Agrega el contenido del nuevo puntaje al vector.
			grabaArchivo();    //Graba el vector en el archivo.
		}catch(IOException i){
			System.out.println("Error en " + i.toString());
		}
            //guardar = true;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_C) //Presiono tecla C
        {   
            leeArchivo();
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
       
         if(!pausa)
         {
             x1 = e.getX();
        y1 = e.getY();
        if(pelota.tiene(x1, y1))
        {
            clic = true;
        }
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
