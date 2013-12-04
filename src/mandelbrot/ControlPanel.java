package mandelbrot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.omg.CORBA.portable.ValueBase;

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
		spnm.setMaximum(MandelbrotComputer.ITERATIONS*2);
		spnm.setMinimum(1);
		spnm.setStepSize(1);
		spnm.setValue(MandelbrotComputer.ITERATIONS);
		
		spIterations.setModel(spnm);
		spIterations.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				setIterNumber();							
			}
		});
		add(new JLabel("Iterations:"));
		add(spIterations, "wrap, align right, grow, aligny center");
				
		startButton = new JButton("Draw");
		add(startButton, "grow");
		startButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				startComputation();				
			}
		});
		
		resetButton = new JButton("Reset");
		add(resetButton, "grow");
		resetButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				computer.reset();
				computer.compute();				
			}
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
	
	
	private void startComputation() {
		if (computer != null && plotter != null) {
		   computer.compute();	
		}
	}
	
	private void setIterNumber() {
		int iterations = (Integer) spIterations.getValue();
		computer.setMaxIterations(iterations);
	}

}
