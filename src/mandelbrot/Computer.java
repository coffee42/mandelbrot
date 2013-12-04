package mandelbrot;

import java.awt.Color;
import java.awt.Rectangle;

public interface Computer {

	public void compute();
	
	public void setPlotter(Plotter plotter);
	
	public void setPalete(Color[] palete);
	
	public void setZoom(Rectangle rect);
	
	public void reset();	
	
	public void setMaxIterations(int value);

}