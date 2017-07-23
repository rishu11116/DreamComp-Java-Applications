import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class DreamCompFutVal extends HttpServlet {
	
	double principal;
	double intRate;
	double numYears;
	int payPerYear;
	NumberFormat nf;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		String futInvestStr="";
		response.setContentType("text/html");
		PrintWriter pw=response.getWriter();
		nf=NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		nf.setMaximumFractionDigits(2);
		String amountStr=request.getParameter("amount");
		String periodStr=request.getParameter("period");
		String rateStr=request.getParameter("rate");
		String termsStr=request.getParameter("terms");
		try {
			if(amountStr!=null && periodStr!=null && rateStr!=null && termsStr!=null) {
				principal=Double.parseDouble(amountStr);
				numYears=Double.parseDouble(periodStr);
				intRate=Double.parseDouble(rateStr)/100;
				payPerYear=Integer.parseInt(termsStr);
				futInvestStr=nf.format(compute());
			}
			else {
				amountStr="";
				rateStr="";
				periodStr="";
				termsStr="";
				futInvestStr="";
			}
		} catch (NumberFormatException e) {
			
		}
		pw.print("<html><body> <left>"+"<form name=\"Form1\""+" action=\"http://127.0.0.1:8080/"+
		"examples/servlet/DreamCompFutVal\">"+"<B>Enter the value of the initial investment:</B>"+
				" <input type=textbox name=\"amount\""+" size=12 value=\"");
		pw.print(amountStr+"\">");
		pw.print("<BR><B>Enter the term in years:</B>"+" <input type=textbox name=\"period\""+" size=12 value=\"");
		pw.println(periodStr+"\">");
		pw.print("<BR><B>Enter the return rate:</B>"+" <input type=textbox name=\"rate\""+" size=12 value=\"");
		pw.print(rateStr+"\">");
		pw.print("<BR><B>Enter the number of compounding periods per year:</B>"+" <input type=textbox name=\"terms\""+" size=12 value=\"");
		pw.print(termsStr+"\">");
		pw.print("<BR><B>Future value of Investment:</B>"+" <input READONLY type=textbox"+" name=\"payment\" size=12 value=\"");
		pw.println(futInvestStr+"\">");
		pw.print("<BR><P><input type=submit value=\"Submit\">");
		pw.println("</form></body></html>");
	}
	
	double compute() {
		double b,e;
		e=payPerYear*numYears;
		b=(1+intRate/payPerYear);
		return principal*Math.pow(b,e);
	}
}