import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;

/*
   <applet code="DreamCompMaxWithdrawal" width=420 height="240">
   </applet>
 */

public class DreamCompMaxWithdrawal extends JApplet implements ActionListener{
	
	JTextField orgPText,maxWDText,periodText,rateText,numWDText;
	JButton doIt;
	double principal;
	double rateOfRet;
	double numYears;
	int numPerYear;
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
		
		JLabel heading=new JLabel("Maximum Regular Withdrawals");
		JLabel orgPLab=new JLabel("Original Principal ");
		JLabel periodLab=new JLabel("Years ");
		JLabel rateLab=new JLabel("Rate of Return ");
		JLabel numWDLab=new JLabel("Number of Withdrawals per Year ");
		JLabel maxWDLab=new JLabel("Maximum Withdrawal ");
		orgPText=new JTextField(10);
		periodText=new JTextField(10);
		maxWDText=new JTextField(10);
		rateText=new JTextField(10);
		numWDText=new JTextField(10);
		maxWDText.setEditable(false);
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
		gbag.setConstraints(periodLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(periodText,gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(rateLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(rateText,gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(numWDLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(numWDText,gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(maxWDLab,gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(maxWDText,gbc);
		
		gbc.anchor=GridBagConstraints.CENTER;
		gbag.setConstraints(doIt,gbc);
		add(heading);
		add(orgPLab);
		add(orgPText);
		add(periodLab);
		add(periodText);
		add(rateLab);
		add(rateText);
		add(numWDLab);
		add(numWDText);
		add(maxWDLab);
		add(maxWDText);
		add(doIt);
		
		orgPText.addActionListener(this);
		periodText.addActionListener(this);
		rateText.addActionListener(this);
		numWDText.addActionListener(this);
		doIt.addActionListener(this);
		
		nf=NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
	}
	
	public void actionPerformed(ActionEvent ae) {
		double result=0.0;
		String orgPStr=orgPText.getText();
		String periodStr=periodText.getText();
		String rateStr=rateText.getText();
		String numWDStr=numWDText.getText();
		try {
			if(orgPStr.length()!=0 && periodStr.length()!=0 && rateStr.length()!=0 && numWDStr.length()!=0) {
				principal=Double.parseDouble(orgPStr);
				numYears=Double.parseDouble(periodStr);
				rateOfRet=Double.parseDouble(rateStr)/100;
				numPerYear=Integer.parseInt(numWDStr);
				result=compute();
				maxWDText.setText(nf.format(result));
				showStatus("");
			}
			else {
				showStatus("One or more entries missing");
				maxWDText.setText("");
			}
		} catch(NumberFormatException exc) {
			showStatus("Invalid Data");
			maxWDText.setText("");
		}
	}
	double compute() {
		double b,e;
		double t1,t2;
		t1=rateOfRet/numPerYear;
		b=1+t1;
		e=numPerYear*numYears;
		t2=Math.pow(b,e)-1;
		return principal*(t1/t2+t1);
	}
}