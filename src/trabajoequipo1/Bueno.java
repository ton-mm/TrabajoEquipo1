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

import java.awt.Image;

public class Bueno extends Base{
    
    /**
	 * Metodo constructor que hereda los atributos de la clase <code>Base</code>.
	 * @param posX es la <code>posiscion en x</code> del objeto elefante.
	 * @param posY es el <code>posiscion en y</code> del objeto elefante.
	 * @param image es la <code>imagen</code> del objeto elefante.
	 */
         
        private static final String desaparece = "DESAPARECE";
        private static final String pausado = "PAUSADO";  
        
       /* public getpausado()
        {
            return pausado;
        }
        */
    
	public Bueno(int posX,int posY,Image image)
        {
		super(posX,posY,image);	
	}
        
        public boolean tiene (int posX,int posY)
        {
            return getPerimetro().contains(posX, posY);
        }
    
}
