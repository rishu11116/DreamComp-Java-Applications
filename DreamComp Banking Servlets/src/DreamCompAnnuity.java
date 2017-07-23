import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DreamCompAnnuity extends HttpServlet {
	
	double desWithdrawal;
	double intRate;
	double numYears;
	int payPerYear;
	NumberFormat nf;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		String initInvestStr="";
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		nf=NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String desWithdrawalStr=request.getParameter("desWithdrawal");
		String periodStr=request.getParameter("period");
		String rateStr=request.getParameter("rate");
		String termsStr=request.getParameter("terms");
		try {
			if(desWithdrawalStr!=null && periodStr!=null && rateStr!=null && termsStr!=null) {
				desWithdrawal=Double.parseDouble(desWithdrawalStr);
				numYears=Double.parseDouble(periodStr);
				intRate=Double.parseDouble(rateStr)/100;
				payPerYear=Integer.parseInt(termsStr);
				initInvestStr=nf.format(compute());
			}
			else {
				desWithdrawalStr="";
				rateStr="";
				periodStr="";
				termsStr="";
				initInvestStr="";
			}
		} catch (NumberFormatException e) {
			
		}
		pw.print("<html><body> <left>"+"<form name=\"Form1\""+" action=\"http://127.0.0.1:8080/"+
		"examples/servlet/DreamCompAnnuity\">"+"<B>Enter the desired value of Regular Withdrawal:</B>"+
				" <input type=textbox name=\"desWithdrawal\""+" size=12 value=\"");
		pw.print(desWithdrawalStr+"\">");
		pw.print("<BR><B>Enter the term in years:</B>"+" <input type=textbox name=\"period\""+" size=12 value=\"");
		pw.println(periodStr+"\">");
		pw.print("<BR><B>Enter the return rate:</B>"+" <input type=textbox name=\"rate\""+" size=12 value=\"");
		pw.print(rateStr+"\">");
		pw.print("<BR><B>Enter the number of withdrawals per year:</B>"+" <input type=textbox name=\"terms\""+" size=12 value=\"");
		pw.print(termsStr+"\">");
		pw.print("<BR><B>Initial Investment Required:</B>"+" <input READONLY type=textbox"+" name=\"payment\" size=12 value=\"");
		pw.println(initInvestStr+"\">");
		pw.print("<BR><P><input type=submit value=\"Submit\">");
		pw.println("</form></body></html>");
	}
	
	double compute() {
		double b,e;
		double t1,t2;
		t1=(desWithdrawal*payPerYear)/intRate;
		e=payPerYear*numYears;
		b=(1+intRate/payPerYear);
		t2=1-(1/Math.pow(b,e));
		return t1*t2;
	}
}