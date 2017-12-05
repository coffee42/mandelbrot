package mandelbrot;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;

public class Mandelbrot extends JFrame{
	
	private MigLayout mainLayout;
	private Plotter plotter;
	private ControlPanel control;

	private Mandelbrot() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			SwingUtilities.invokeAndWait(() -> {
				initLayout();
				pack();
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void initLayout() {
		mainLayout = new MigLayout("nocache", "[600!, fill][grow, fill]", "[600, fill]");
		Container container = getContentPane();
		container.setLayout(mainLayout);		
		plotter = new Plotter();
		Computer computer = new MandelbrotComputer();
		control = new ControlPanel();
		control.setComputer(computer);
		control.setPlotter(plotter);
		container.add(plotter);
		container.add(control);
		control.startComputation();
	}
	
	public static void main(String[] args) {
		Mandelbrot mandelbrot = new Mandelbrot();
		mandelbrot.setVisible(true);		
	}

}
