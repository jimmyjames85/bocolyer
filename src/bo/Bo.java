package bo;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Bo extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected static int totalIntegers = 0;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html; charset=UTF-8");
		ServletOutputStream os = response.getOutputStream();
		Date curDate = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
		os.print("<br><H1>Bo Daniel Colyer</H1><br><img src=\"imgs/Bo.jpg\"><br>");
		os.print("The Server Date and Time is " + formatter.format(curDate));

		os.print("<br>This page has been viewed " + getTotalIntegers() + " times");
		
		
		//	The <br> tag works only because we set the content above to html
		os.close();
	}

	public static String getTotalIntegers()
	{
		return "" + ++totalIntegers;
	}
}


