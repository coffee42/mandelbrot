package mandelbrot;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import net.miginfocom.swing.MigLayout;

public class ControlPanel extends JPanel {
	
	private JButton startButton;
	private JButton resetButton;
	private Computer computer;
	private Plotter plotter;
	private JSpinner spIterations;
	
	public ControlPanel() {
		MigLayout layout = new MigLayout();
		setLayout(layout);
		
		spIterations = new JSpinner();
		SpinnerNumberModel spnm = new SpinnerNumberModel();
		spnm.setMaximum(MandelbrotComputer.ITERATIONS * 2);
		spnm.setMinimum(1);
		spnm.setStepSize(1);
		spnm.setValue(MandelbrotComputer.ITERATIONS);
		
		spIterations.setModel(spnm);
		spIterations.addChangeListener((e) -> setIterNumber());
		add(new JLabel("Iterations:"));
		add(spIterations, "wrap, align right, grow, aligny center");
				
		startButton = new JButton("Draw");
		add(startButton, "grow");
		startButton.addActionListener((e) -> startComputation());				
		
		resetButton = new JButton("Reset");
		add(resetButton, "grow");
		resetButton.addActionListener((e) -> 
		 	{
				computer.reset();
				computer.compute();				
			});

		setBorder(BorderFactory.createEtchedBorder());
	}
	
	public void setComputer(Computer computer) {
		this.computer = computer;
	}
	
	public void setPlotter(Plotter plotter) {
		this.plotter = plotter;
		plotter.setComputer(computer);
	}
		
	public void startComputation() {
		if (computer != null && plotter != null) {
		   int[] pixels = computer.compute();
		   plotter.update(pixels); 
		}
	}
	
	private void setIterNumber() {
		int iterations = (Integer) spIterations.getValue();
		computer.setMaxIterations(iterations);
	}

}
