package mandelbrot;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Arrays;

public class MandelbrotComputer implements Computer {
	
	public static int ITERATIONS = 500; 
	
	/**
	 * Result pixels.
	 */
	private int[] points;
	/**
	 * where should pixels be printed
	 */
	private Plotter plotter;
	private int maxIterations;
	
	private int width;
	private int height;	
	// ice pallete
	private int [][] palette = {
      {  0,   0,   0}, {  0,   0,   0}, {  0,   0,   8}, {  0,   0,  16},
      {  0,   0,  24}, {  0,   0,  32}, {  0,   0,  40}, {  0,   0,  48},
      {  0,   0,  56}, {  0,   0,  64}, {  0,   0,  72}, {  0,   0,  80},
      {  0,   0,  88}, {  0,   0,  96}, {  0,   0, 104}, {  0,   0, 112},
      {  0,   0, 120}, {  0,   0, 128}, {  0,   0, 136}, {  0,   0, 144},
      {  0,   0, 152}, {  0,   0, 160}, {  0,   0, 168}, {  0,   0, 176},
      {  0,   0, 184}, {  0,   0, 192}, {  0,   0, 200}, {  0,   0, 208},
      {  0,   0, 216}, {  0,   0, 224}, {  0,   0, 232}, {  0,   0, 240},
      {  0,   0, 252}, {  0,   4, 252}, {  0,  12, 252}, {  0,  20, 252},
      {  0,  28, 252}, {  0,  36, 252}, {  0,  44, 252}, {  0,  52, 252},
      {  0,  60, 252}, {  0,  68, 252}, {  0,  76, 252}, {  0,  84, 252},
      {  0,  92, 252}, {  0, 100, 252}, {  0, 108, 252}, {  0, 116, 252},
      {  0, 124, 252}, {  0, 132, 252}, {  0, 140, 252}, {  0, 148, 252},
      {  0, 156, 252}, {  0, 164, 252}, {  0, 172, 252}, {  0, 180, 252},
      {  0, 188, 252}, {  0, 196, 252}, {  0, 204, 252}, {  0, 212, 252},
      {  0, 220, 252}, {  0, 228, 252}, {  0, 236, 252}, {  0, 244, 252},
      {  0, 252, 252}, {  0, 252, 252}, {  0, 248, 252}, {  0, 244, 252},
      {  0, 240, 252}, {  0, 236, 252}, {  0, 232, 252}, {  0, 228, 252},
      {  0, 224, 252}, {  0, 220, 252}, {  0, 216, 252}, {  0, 212, 252},
      {  0, 208, 252}, {  0, 204, 252}, {  0, 200, 252}, {  0, 196, 252},
      {  0, 192, 252}, {  0, 188, 252}, {  0, 184, 252}, {  0, 180, 252},
      {  0, 176, 252}, {  0, 172, 252}, {  0, 168, 252}, {  0, 164, 252},
      {  0, 160, 252}, {  0, 156, 252}, {  0, 152, 252}, {  0, 148, 252},
      {  0, 144, 252}, {  0, 140, 252}, {  0, 136, 252}, {  0, 132, 252},
      {  0, 128, 252}, {  0, 124, 252}, {  0, 120, 252}, {  0, 116, 252},
      {  0, 112, 252}, {  0, 108, 252}, {  0, 104, 252}, {  0, 100, 252},
      {  0,  96, 252}, {  0,  92, 252}, {  0,  88, 252}, {  0,  84, 252},
      {  0,  80, 252}, {  0,  76, 252}, {  0,  72, 252}, {  0,  68, 252},
      {  0,  64, 252}, {  0,  60, 252}, {  0,  56, 252}, {  0,  52, 252},
      {  0,  48, 252}, {  0,  44, 252}, {  0,  40, 252}, {  0,  36, 252},
      {  0,  32, 252}, {  0,  28, 252}, {  0,  24, 252}, {  0,  20, 252},
      {  0,  16, 252}, {  0,  12, 252}, {  0,   8, 252}, {  0,   4, 252},
      {  0,   0, 252}, {  0,   0, 252}, {  0,  12, 252}, {  0,  28, 252},
      {  0,  44, 252}, {  0,  60, 252}, {  0,  76, 252}, {  0,  92, 252},
      {  0, 108, 252}, {  0, 124, 252}, {  0, 140, 252}, {  0, 156, 252},
      {  0, 172, 252}, {  0, 188, 252}, {  0, 204, 252}, {  0, 220, 252},
      {  0, 236, 252}, {  0, 232, 252}, {  4, 228, 252}, {  4, 224, 252},
      {  8, 220, 252}, { 12, 212, 252}, { 12, 208, 252}, { 16, 204, 252},
      { 20, 200, 252}, { 20, 192, 252}, { 24, 188, 252}, { 28, 184, 252},
      { 28, 180, 252}, { 32, 176, 252}, { 36, 168, 252}, { 36, 164, 252},
      { 40, 160, 252}, { 44, 156, 252}, { 44, 148, 252}, { 48, 144, 252},
      { 52, 140, 252}, { 52, 136, 252}, { 56, 128, 252}, { 60, 124, 252},
      { 60, 120, 252}, { 64, 116, 252}, { 68, 112, 252}, { 68, 104, 252},
      { 72, 100, 252}, { 76,  96, 252}, { 76,  92, 252}, { 80,  84, 252},
      { 84,  80, 252}, { 84,  76, 252}, { 88,  72, 252}, { 92,  64, 252},
      { 92,  60, 252}, { 96,  56, 252}, {100,  52, 252}, {100,  48, 252},
      {104,  40, 252}, {108,  36, 252}, {108,  32, 252}, {112,  28, 252},
      {116,  20, 252}, {116,  16, 252}, {120,  12, 252}, {124,   8, 252},
      {128,   0, 252}, {128,   0, 252}, {124,   0, 248}, {124,   0, 244},
      {120,   0, 240}, {120,   0, 236}, {116,   0, 232}, {116,   0, 228},
      {112,   0, 224}, {112,   0, 220}, {108,   0, 216}, {108,   0, 212},
      {104,   0, 208}, {104,   0, 204}, {100,   0, 200}, {100,   0, 196},
      { 96,   0, 192}, { 96,   0, 188}, { 92,   0, 184}, { 92,   0, 180},
      { 88,   0, 176}, { 88,   0, 172}, { 84,   0, 168}, { 84,   0, 164},
      { 80,   0, 160}, { 80,   0, 156}, { 76,   0, 152}, { 76,   0, 148},
      { 72,   0, 144}, { 72,   0, 140}, { 68,   0, 136}, { 68,   0, 132},
      { 64,   0, 128}, { 64,   0, 124}, { 60,   0, 120}, { 60,   0, 116},
      { 56,   0, 112}, { 56,   0, 108}, { 52,   0, 104}, { 52,   0, 100},
      { 48,   0,  96}, { 48,   0,  92}, { 44,   0,  88}, { 44,   0,  84},
      { 40,   0,  80}, { 40,   0,  76}, { 36,   0,  72}, { 36,   0,  68},
      { 32,   0,  64}, { 32,   0,  60}, { 28,   0,  56}, { 28,   0,  52},
      { 24,   0,  48}, { 24,   0,  44}, { 20,   0,  40}, { 20,   0,  36},
      { 16,   0,  32}, { 16,   0,  28}, { 12,   0,  24}, { 12,   0,  20},
      {  8,   0,  16}, {  8,   0,  12}, {  4,   0,   8}, {  4,   0,   4}
  };

	/**
	 * current zoom selection rectangle
	 */
	private Rectangle zoomRect;
	
	/**
	 * Initial boundaries of mandelbrot rendering
	 */
	double x0, y0;
	double zoom;
	
	public MandelbrotComputer(int width, int height) {
		this.width = width;
	  this.height = height;
		points = new int[width*height];
	}
	
	public MandelbrotComputer() {
		this(Plotter.DIMENSION.width, Plotter.DIMENSION.height);
		maxIterations = ITERATIONS;
		reset();
	}
	
	public void setMaxIterations(int value) {
		maxIterations = value;
	}
	
	/**
	 * Change color palette. Not used currently.
	 */
	public void setPalete(Color[] colorPalette) {
		palette = new int[colorPalette.length][3];
		for (int i = 0; i < palette.length; i++) {
			palette[i][0] = colorPalette[i].getRed();
			palette[i][1] = colorPalette[i].getGreen();
			palette[i][2] = colorPalette[i].getBlue();
		}
	}
	
	/**
	 * make zoom selection actual
	 */
	public void setZoom(Rectangle rect) {
		zoomRect = rect;
	}
	
	
	public void setPlotter(Plotter plotter) {
		this.plotter = plotter;
	}
	
	// reset to initial state for rendering
	public void reset() {
		zoom = 1.0;
		x0 = 0.0;
		y0 = 0.0;
		zoomRect = null;
	}
	
	
	/** 
	 *  Prints Mandelbrot set on Plotter
	 *  @see Plotter
	 */
	@Override
	public void compute() {		
		// we have zoom selection
		if (zoomRect != null) {
		  x0 += zoom * zoomRect.x / width;
		  y0 += zoom * zoomRect.y / height;
		  zoom *= Math.max(((double) zoomRect.width )/ width, ((double) zoomRect.height )/ height);
		}
		// go through all pixels and set their colors
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				// calculation of constanc C from Zn+1 = Zn^2 + C
				double re = 2.5 * ( x * zoom / width + x0)  - 2.0;
			  double im = 1.25 - 2.5 * (y * zoom / height + y0);

				int iteration = getIterationNumber(re, im);
				Color color = determineColor(iteration);
				setColor(x, y, color);	
			}
		}
				
		plotter.update(points);
		return;
	}
	
  /**
   * Zn+1 = Zn^2 + c
   * @param re C[re]
   * @param im C[im]
   * @return 0 if maximum number of iteration was reached or actual iteration value
   */
	private int getIterationNumber(double re, double im) {
		int value = 0;
    double zx = 0.0, zy = 0.0, zx2 = 0.0, zy2 = 0.0;
    while (value < maxIterations && zx2 + zy2 < 4.0) {
      zy = 2.0 * zx * zy + im;
      zx = zx2 - zy2 + re;
      zx2 = zx * zx;
      zy2 = zy * zy;
      value++;
    }
    if (value == maxIterations) 
    	return 0;
    
    return value;
  }
	
	/**
	 * Escape time coloring algorithm
	 * @param i
	 * @return
	 */
	private Color determineColor(int i) {
		// inside mandelbrot
		if (i == 0) {
			return Color.BLACK;
		}
		// outside
		else {			
			i = i % palette.length;
			return new Color(palette[i][0],palette[i][1],palette[i][2]);		
		}		
	}
	
	/**
	 * Set specified color to given one dimensional array of pixels
	 * 
	 * @param x 
	 * @param y
	 * @param color
	 */
	private void setColor(int x, int y, Color color) {		
		points[x+y*width] = 0xFFFFFF & color.getRGB();		
	}
	

}
