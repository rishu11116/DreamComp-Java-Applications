import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DreamCompRemBal extends HttpServlet {
	
	double orgPrincipal;
	double intRate;
	double payment;
	double numPayments;
	int payPerYear;
	NumberFormat nf;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		String remBalanceStr="";
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		nf=NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String amountStr=request.getParameter("amount");
		String paymentStr=request.getParameter("payment");
		String rateStr=request.getParameter("rate");
		String numPaymentsStr=request.getParameter("numPayments");
		String termsStr=request.getParameter("terms");
		try {
			if(amountStr!=null && paymentStr!=null && rateStr!=null && numPaymentsStr!=null && termsStr!=null) {
				orgPrincipal=Double.parseDouble(amountStr);
				payment=Double.parseDouble(paymentStr);
				intRate=Double.parseDouble(rateStr)/100;
				numPayments=Double.parseDouble(numPaymentsStr);
				payPerYear=Integer.parseInt(termsStr);
				remBalanceStr=nf.format(compute());
			}
			else {
				amountStr="";
				rateStr="";
				paymentStr="";
				termsStr="";
				numPaymentsStr="";
				remBalanceStr="";
			}
		} catch (NumberFormatException e) {
			
		}
		pw.print("<html><body> <left>"+"<form name=\"Form1\""+" action=\"http://127.0.0.1:8080/"+
		"examples/servlet/DreamCompRemBal\">"+"<B>Enter the Original Pricipal value:</B>"+
				" <input type=textbox name=\"amount\""+" size=12 value=\"");
		pw.print(amountStr+"\">");
		pw.print("<BR><B>Enter the Regular Payment Amount:</B>"+" <input type=textbox name=\"payment\""+" size=12 value=\"");
		pw.println(paymentStr+"\">");
		pw.print("<BR><B>Enter the interest rate:</B>"+" <input type=textbox name=\"rate\""+" size=12 value=\"");
		pw.print(rateStr+"\">");
		pw.print("<BR><B>Enter the number of payments per year:</B>"+" <input type=textbox name=\"terms\""+" size=12 value=\"");
		pw.print(termsStr+"\">");
		pw.print("<BR><B>Enter the number of payments made:</B>"+" <input type=textbox name=\"numPayments\""+" size=12 value=\"");
		pw.print(numPaymentsStr+"\">");
		pw.print("<BR><B>Remaining Balance Value:</B>"+" <input READONLY type=textbox"+" name=\"payment\" size=12 value=\"");
		pw.println(remBalanceStr+"\">");
		pw.print("<BR><P><input type=submit value=\"Submit\">");
		pw.println("</form></body></html>");
	}
	
	double compute() {
		double bal=orgPrincipal;
		double rate=intRate/payPerYear;
		for(int i=0;i<numPayments;i++)
			bal-=payment-(bal*rate);
		return bal;
	}
}