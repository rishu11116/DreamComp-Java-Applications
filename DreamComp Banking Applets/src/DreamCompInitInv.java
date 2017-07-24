import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;

/*
   <applet code="DreamCompInitInv" width=420 height="240">
   </applet>
 */

public class DreamCompInitInv extends JApplet implements ActionListener{
	
	JTextField targetText,initialText,periodText,rateText,compText;
	JButton doIt;
	double targetValue;
	double rateOfRet;
	double numYears;
	int compPerYear;
	NumberFormat nf;
	
	public void init() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					makeGUI();
				}
			});
		} catch(Exception exc) {
			System.out.println("Can't create GUI because of "+exc);
		}
	}
	
	public void makeGUI() {
		GridBagLayout gbag=new GridBagLayout();
		GridBagConstraints gbc=new GridBagConstraints();
		setLayout(gbag);
		
		JLabel heading=new JLabel("Initial Investment needed for a Desired Future Value");
		JLabel targetLab=new JLabel("Desired Future Value ");
		JLabel periodLab=new JLabel("Years ");
		JLabel rateLab=new JLabel("Rate of Return ");
		JLabel compLab=new JLabel("Compounding Periods per Year ");
		JLabel initialLab=new JLabel("Initial Investment Required ");
		targetText=new JTextField(10);
		periodText=new JTextField(10);
		initialText=new JTextField(10);
		rateText=new JTextField(10);
		compText=new JTextField(10);
		initialText.setEditable(false);
		doIt=new JButton("Compute");
		gbc.weighty=1.0;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbc.anchor=GridBagConstraints.NORTH;
		gbag.setConstraints(heading,gbc);
		gbc.anchor=GridBagConstraints.EAST;
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(targetLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(targetText,gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(periodLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(periodText,gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(rateLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(rateText,gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(compLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(compText,gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(initialLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(initialText,gbc);
		
		gbc.anchor=GridBagConstraints.CENTER;
		gbag.setConstraints(doIt,gbc);
		add(heading);
		add(targetLab);
		add(targetText);
		add(periodLab);
		add(periodText);
		add(rateLab);
		add(rateText);
		add(compLab);
		add(compText);
		add(initialLab);
		add(initialText);
		add(doIt);
		
		targetText.addActionListener(this);
		periodText.addActionListener(this);
		rateText.addActionListener(this);
		compText.addActionListener(this);
		doIt.addActionListener(this);
		
		nf=NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
	}
	
	public void actionPerformed(ActionEvent ae) {
		double result=0.0;
		String targetStr=targetText.getText();
		String periodStr=periodText.getText();
		String rateStr=rateText.getText();
		String compStr=compText.getText();
		try {
			if(targetStr.length()!=0 && periodStr.length()!=0 && rateStr.length()!=0 && compStr.length()!=0) {
				targetValue=Double.parseDouble(targetStr);
				numYears=Double.parseDouble(periodStr);
				rateOfRet=Double.parseDouble(rateStr)/100;
				compPerYear=Integer.parseInt(compStr);
				result=compute();
				initialText.setText(nf.format(result));
				showStatus("");
			}
			else {
				showStatus("One or more entries missing");
				initialText.setText("");
			}
		} catch(NumberFormatException exc) {
			showStatus("Invalid Data");
			initialText.setText("");
		}
	}
	double compute() {
		double b,e;
		b=(1+rateOfRet/compPerYear);
		e=compPerYear*numYears;
		return targetValue/Math.pow(b,e);
	}
}