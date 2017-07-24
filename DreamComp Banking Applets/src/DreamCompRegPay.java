import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;

/*
   <applet code="DreamCompRegPay" width=420 height="240">
   </applet>
 */

public class DreamCompRegPay extends JApplet implements ActionListener{
	
	JTextField amountText,periodText,rateText,payPerYearText,paymentText;
	JButton doIt;
	double principal;
	double intRate;
	double numYears;
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
		JLabel heading=new JLabel("Monthly Loan Payments");
		JLabel amountLab=new JLabel("Principal ");
		JLabel periodLab=new JLabel("Years ");
		JLabel rateLab=new JLabel("Interest Rate ");
		JLabel payPerYearLab=new JLabel("Number of Payments per Year ");
		JLabel paymentLab=new JLabel("Monthly Payments ");
		amountText=new JTextField(10);
		periodText=new JTextField(10);
		rateText=new JTextField(10);
		payPerYearText=new JTextField(10);
		paymentText=new JTextField(10);
		paymentText.setEditable(false);
		doIt=new JButton("Compute");
		gbc.weighty=1.0;
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbc.anchor=GridBagConstraints.NORTH;
		gbag.setConstraints(heading,gbc);
		gbc.anchor=GridBagConstraints.EAST;
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(amountLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(amountText,gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(periodLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(periodText,gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(rateLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(rateText,gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(payPerYearLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(payPerYearText,gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(paymentLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(paymentText,gbc);
		
		gbc.anchor=GridBagConstraints.CENTER;
		gbag.setConstraints(doIt,gbc);
		add(heading);
		add(amountLab);
		add(amountText);
		add(periodLab);
		add(periodText);
		add(rateLab);
		add(rateText);
		add(payPerYearLab);
		add(payPerYearText);
		add(paymentLab);
		add(paymentText);
		add(doIt);
		
		amountText.addActionListener(this);
		periodText.addActionListener(this);
		rateText.addActionListener(this);
		payPerYearText.addActionListener(this);
		doIt.addActionListener(this);
		
		nf=NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
	}
	
	public void actionPerformed(ActionEvent ae) {
		double result=0.0;
		String amountStr=amountText.getText();
		String periodStr=periodText.getText();
		String rateStr=rateText.getText();
		String payPerYearStr=payPerYearText.getText();
		try {
			if(amountStr.length()!=0 && periodStr.length()!=0 && rateStr.length()!=0 && payPerYearStr.length()!=0) {
				principal=Double.parseDouble(amountStr);
				numYears=Double.parseDouble(periodStr);
				intRate=Double.parseDouble(rateStr)/100;
				payPerYear=Integer.parseInt(payPerYearStr);
				result=compute();
				paymentText.setText(nf.format(result));
				showStatus("");
			}
			else {
				showStatus("One or more entries missing");
				paymentText.setText("");
			}
		} catch(NumberFormatException exc) {
			showStatus("Invalid Data");
			paymentText.setText("");
		}
	}
	double compute() {
		double numer;
		double denom;
		double b,e;
		numer=intRate*principal/payPerYear;
		e=-(payPerYear*numYears);
		b=(intRate/payPerYear)+1.0;
		denom=1.0-Math.pow(b,e);
		return numer/denom;
	}
}