package p012;

import java.awt.Image;
import javax.swing.ImageIcon;
//TODO Transform the code to be used safely in a concurrent context.  
public class Ball {
       //TODO  Find an archive named Ball.png 
	private String Ball = "Ball.png"; 

	private double x,y,dx,dy;
	private double v,fi;
	private Image image;
	private final int IMG_TAM_X,IMG_TAM_Y;

	public Ball() {
		
		ImageIcon ii = new ImageIcon(this.getClass().getResource(Ball));
		image = ii.getImage();
		
		//TODO Depend of image size
		IMG_TAM_X = ii.getIconWidth();
		IMG_TAM_Y = ii.getIconHeight();
			
		x = Billiards.Width/4-16;
		y = Billiards.Height/2-16;
		v = 5;
		fi =  Math.random() * Math.PI * 2;
	}

	public synchronized void move() {
		setV(getV()*Math.exp(-getV()/1000));
		dx = getV()*Math.cos(getFi());
		dy = getV()*Math.sin(getFi());
		if (Math.abs(dx) < 1 && Math.abs(dy) < 1) {
			dx = 0;
			dy = 0;
		}
		setX(getX()+dx);   
		//y += dy;
		setY(getY()+dy);
		
		reflect();
		
		//TODO Check postcondition
				
		assert getY()>Board.TOPBOARD; 
 		assert getY()<Board.BOTTOMBOARD;
		assert getX()>Board.LEFTBOARD;
 		assert getX()<Board.RIGHTBOARD;
 		
 		
	}

	public synchronized void reflect() {
		if (Math.abs(getX() + IMG_TAM_X - Board.RIGHTBOARD) <  Math.abs(dx)) {
			setFi(Math.PI - getFi());
		}
		if (Math.abs(getY() + IMG_TAM_Y - Board.BOTTOMBOARD) <  Math.abs(dy)) {
			setFi(-getFi());
		}
		if (Math.abs(getX() - Board.LEFTBOARD) <  Math.abs(dx)) {
			//fi = Math.PI - fi;
			setFi(Math.PI - getFi());
		}
		if (Math.abs(getY() - Board.TOPBOARD) <  Math.abs(dy)) {
			setFi(-getFi());
		}
		
		
			
		assert getY()>Board.TOPBOARD;
 		assert getY()<Board.BOTTOMBOARD;
		assert getX()>Board.LEFTBOARD;
 		assert getX()<Board.RIGHTBOARD;
		
	}

	public synchronized int getX() {
		return (int)x;
	}
	
	public synchronized int getY() {
		return (int)y;
	}
	
	public synchronized double getFi() {
		return (double)fi;
	}
	public synchronized double getV() {
		return (double)v;
	}

	public synchronized double getdr() {
		return Math.sqrt(dx*dx+dy*dy);
	}

	public synchronized void setX(double x) {
		this.x = x;
	}

	public synchronized void setY(double y) {
		this.y = y;
	}
	
	public synchronized void setFi(double fi) {
		this.fi = fi;
	}
	
	public synchronized void setV(double v) {
		this.v = v;
	}
	
	

	public Image getImage() {
		return image;
	}

}

