import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;

/*
   <applet code="DreamCompRemBal" width=420 height="240">
   </applet>
 */

public class DreamCompRemBal extends JApplet implements ActionListener{
	
	JTextField orgPText,paymentText,numPayText,rateText,payPerYearText,remBalText;
	JButton doIt;
	double orgPrincipal;
	double payment;
	double numPayments;
	double intRate;
	int payPerYear;
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
		
		JLabel heading=new JLabel("Remaining Loan Balance");
		JLabel orgPLab=new JLabel("Original Principal ");
		JLabel paymentLab=new JLabel("Amount of Payment ");
		JLabel numPayLab=new JLabel("Number of Payments Made ");
		JLabel rateLab=new JLabel("Interest Rate ");
		JLabel payPerYearLab=new JLabel("Number of Payments per Year ");
		JLabel remBalLab=new JLabel("Remaining Balance ");
		orgPText=new JTextField(10);
		paymentText=new JTextField(10);
		numPayText=new JTextField(10);
		rateText=new JTextField(10);
		payPerYearText=new JTextField(10);
		remBalText=new JTextField(10);
		remBalText.setEditable(false);
		doIt=new JButton("Compute");
		gbc.weighty=1.0;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbc.anchor=GridBagConstraints.NORTH;
		gbag.setConstraints(heading,gbc);
		gbc.anchor=GridBagConstraints.EAST;
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(orgPLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(orgPText,gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(paymentLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(paymentText,gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(numPayLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(numPayText,gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(rateLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(rateText,gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(remBalLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(remBalText,gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(payPerYearLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(payPerYearText,gbc);
		
		gbc.anchor=GridBagConstraints.CENTER;
		gbag.setConstraints(doIt,gbc);
		add(heading);
		add(orgPLab);
		add(orgPText);
		add(paymentLab);
		add(paymentText);
		add(numPayLab);
		add(numPayText);
		add(rateLab);
		add(rateText);
		add(payPerYearLab);
		add(payPerYearText);
		add(remBalLab);
		add(remBalText);
		add(doIt);
		
		orgPText.addActionListener(this);
		paymentText.addActionListener(this);
		numPayText.addActionListener(this);
		rateText.addActionListener(this);
		payPerYearText.addActionListener(this);
		doIt.addActionListener(this);
		
		nf=NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
	}
	
	public void actionPerformed(ActionEvent ae) {
		double result=0.0;
		String orgPStr=orgPText.getText();
		String paymentStr=paymentText.getText();
		String numPayStr=numPayText.getText();
		String rateStr=rateText.getText();
		String payPerYearStr=payPerYearText.getText();
		try {
			if(orgPStr.length()!=0 && paymentStr.length()!=0 && rateStr.length()!=0 && numPayStr.length()!=0 && payPerYearStr.length()!=0) {
				orgPrincipal=Double.parseDouble(orgPStr);
				payment=Double.parseDouble(paymentStr);
				numPayments=Double.parseDouble(numPayStr);
				intRate=Double.parseDouble(rateStr)/100;
				payPerYear=Integer.parseInt(payPerYearStr);
				result=compute();
				remBalText.setText(nf.format(result));
				showStatus("");
			}
			else {
				showStatus("One or more entries missing");
				remBalText.setText("");
			}
		} catch(NumberFormatException exc) {
			showStatus("Invalid Data");
			remBalText.setText("");
		}
	}
	double compute() {
		double bal=orgPrincipal;
		double rate=intRate/payPerYear;
		for(int i=0;i<numPayments;i++)
			bal-=payment-(bal*rate);
		return bal;
	}
}