package mandelbrot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JPanel;

/**
 * Component on which is MandelBrot set printed
 * 
 */
public class Plotter extends JPanel {
	
	public static final Dimension DIMENSION = new Dimension(600,600);
	
	private BufferedImage bufferedImage;
	// pixel array from bufferedImage
	private int[] pixels;
	private MouseListener mouseListener;
	private Rectangle selection;
	private Computer computer;
    
	public Plotter() {
		setSize(DIMENSION);
		setMaximumSize(DIMENSION);
		setMinimumSize(DIMENSION);
		setBackground(Color.white);
		bufferedImage = new BufferedImage(DIMENSION.width, DIMENSION.height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) bufferedImage.getRaster().getDataBuffer()).getData();
		mouseListener = new MouseListener();
		addMouseListener(mouseListener);
		addMouseMotionListener(mouseListener);
    initPixels();
	}
	
	/**
	 * Set Computer reference
	 * @param computer
	 */
	public void setComputer(Computer computer) {
		this.computer = computer;
	}
	
	/**
	 * Init all pixels to white color.
	 */
	private void initPixels() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0xFFFFFF;
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		int x = getWidth();
		int y = getHeight();
		g2.drawImage(bufferedImage, 0, 0, x, y, null);		
		if (selection != null) {	
			g2.setColor(Color.white);
			g2.draw(selection);
		}
		g2.dispose();
	}
	
	/**
	 * Used by Computer to draw set
	 * @param newImage colored mandelBrotSet in pixels
	 */
	public void update(int[] newImage) {
		System.arraycopy(newImage, 0, pixels, 0, pixels.length);
		repaint();
	}
	
	@Override
	public int getHeight() {
		return DIMENSION.height;
	}
	
	@Override
	public int getWidth() {
		return DIMENSION.width;
	}
	
	class MouseListener extends MouseAdapter {
		
		int x1, y1, x2, y2;

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println(e.getButton());
			if (e.getButton() == MouseEvent.BUTTON3)
			computer.reset();
			computer.compute();					
		}

		@Override
		// start points of zoom selection
		public void mousePressed(MouseEvent e) {
    	 x1 = e.getX();
			 y1 = e.getY();			
		}

		@Override
		// selection done
		public void mouseReleased(MouseEvent e) {
		  computer.setZoom(selection);			  
	  }
		
		@Override
		// update selection and paint it.
		public void mouseDragged(MouseEvent e) {
			x2 = e.getX();
			y2 = e.getY();
			int width = Math.abs(x1 - x2); 
		  int height = Math.abs(y1 - y2);
		  int x = Math.min(x1, x2);
		  int y = Math.min(y1, y2);
			selection = new Rectangle(x, y, width, height); 
			repaint();
		}
	}
	
}
