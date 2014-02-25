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

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Rectangle;

public class Base {
        private int posX;    //posicion en x.       
	private int posY;	//posicion en y.
	private ImageIcon icono;    //icono.
	
	/**
	 * Metodo constructor usado para crear el objeto
	 * @param posX es la <code>posicion en x</code> del objeto.
	 * @param posY es la <code>posicion en y</code> del objeto.
	 * @param image es la <code>imagen</code> del objeto.
	 */
	public Base(int posX, int posY ,Image image) {
		this.posX=posX;
		this.posY=posY;
		icono = new ImageIcon(image);
	}
	
	/**
	 * Metodo modificador usado para cambiar la posicion en x del objeto 
	 * @param posX es la <code>posicion en x</code> del objeto.
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}
	
	/**
	 * Metodo de acceso que regresa la posicion en x del objeto 
	 * @return posX es la <code>posicion en x</code> del objeto.
	 */
	public int getPosX() {
		return posX;
	}
	
	/**
	 * Metodo modificador usado para cambiar la posicion en y del objeto 
	 * @param posY es la <code>posicion en y</code> del objeto.
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}
	
	/**
	 * Metodo de acceso que regresa la posicion en y del objeto 
	 * @return posY es la <code>posicion en y</code> del objeto.
	 */
	public int getPosY() {
		return posY;
	}
	
	/**
	 * Metodo modificador usado para cambiar el icono del objeto 
	 * @param icono es el <code>icono</code> del objeto.
	 */
	public void setImageIcon(ImageIcon icono) {
		this.icono = icono;
	}
	
	/**
	 * Metodo de acceso que regresa el icono del objeto 
	 * @return icono es el <code>icono</code> del objeto.
	 */
	public ImageIcon getImageIcon() {
		return icono;
	}
	
	/**
	 * Metodo de acceso que regresa el ancho del icono 
	 * @return un objeto de la clase <code>ImageIcon</code> que es el ancho del icono.
	 */
	public int getAncho() {
		return icono.getIconWidth();
                // return (new ImageIcon(animacion.getImagen()).getIconWidth();
	}
	
	/**
	 * Metodo de acceso que regresa el alto del icono 
	 * @return un objeto de la clase <code>ImageIcon</code> que es el alto del icono.
	 */
	public int getAlto() {
		return icono.getIconHeight();
	}
	
	/**
	 * Metodo de acceso que regresa la imagen del icono 
	 * @return un objeto de la clase <code>Image</code> que es la imagen del icono.
	 */
	public Image getImagenI() {
		return icono.getImage();
	}
	
	/**
	 * Metodo de acceso que regresa un nuevo rectangulo
	 * @return un objeto de la clase <code>Rectangle</code> que es el perimetro 
	 * del rectangulo
	 */
	public Rectangle getPerimetro(){
		return new Rectangle(getPosX(),getPosY(),getAncho(),getAlto());
	}
	
	/**
	 * Checa si el objeto <code>Base</code> intersecta a otro <code>Base</code>
	 *
     * @param obj
	 * @return un valor boleano <code>true</code> si lo intersecta <code>false</code>
	 * en caso contrario
	 */
	public boolean intersecta(Base obj){
		return getPerimetro().intersects(obj.getPerimetro());
	}

    /**
     *
     * @param obj
     * @return
     */
    /*
        public boolean tiene (Base obj){
            return getPerimetro().contains(posX, posY);
        }
    */
}
