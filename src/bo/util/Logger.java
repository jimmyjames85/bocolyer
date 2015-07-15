package bo.util;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

public class Logger
{
	@SuppressWarnings("rawtypes")
	private Class loggingClass;
	private static boolean debug = true;
	private static boolean warn = true;
	private static String LOGFILE_LOC = "/home/jmtappe/bocolyer.log";

	public static Logger thisLogger = new Logger(Logger.class);

	private static String getDateNow()
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getDefault());
		return df.format(new Date());

	}

	public static void pageVisit(HttpServletRequest request)
	{
		pageVisit(request, "");
	}

	public static void pageVisit(HttpServletRequest request, String msg)
	{
		String out = "\n========================================================";
		out += "\n Accessed on: " + getDateNow();
		out += "\n" + msg;
		out += "\n Host: " + request.getRemoteHost();
		out += "\n Addr: " + request.getRemoteAddr();
		out += "\n Port: " + request.getRemotePort();
		out += "\n\n";
		thisLogger.log(out);
	}

	@SuppressWarnings("rawtypes")
	public Logger(Class clazz)
	{
		this.loggingClass = clazz;
	}

	private void log(Object x, String method)
	{
		String msg = "[" + loggingClass.getSimpleName() + ":" + method + "]" + x.toString();
		log(msg);
	}

	private void log(String msg)
	{
		
	
		File logFile = new File(LOGFILE_LOC);
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(logFile, true);

			byte bytes[] = msg.getBytes();
			for (byte b : bytes)
				fw.append((char) b);
			fw.append('\n');
			fw.append('\n');

		}
		catch (Exception e)
		{

		}
		finally
		{
			IOUtils.closeQuietly(fw);

		}

	}

	public void debug(Exception e)
	{
		log(stackTraceToString(e), "DEBUG");
	}

	public static String stackTraceToString(Exception e)
	{
		String stackTraceStr = "";
		StackTraceElement[] stackTrace = e.getStackTrace();
		for (int i = 0; i < stackTrace.length; i++)
		{
			StackTraceElement cur = stackTrace[i];
			stackTraceStr += cur.toString() + "\n";

		}
		return stackTraceStr;
	}

	public void debug(Object x)
	{
		if (debug)
			log(x, "DEBUG");
	};

	public void debug()
	{
		if (debug)
			log("", "DEBUG");
	};

	public void warn(Object x)
	{
		if (debug || warn)
			log(x, "WARN");
	};

	public void warn()
	{
		if (debug || warn)
			log("", "WARN");
	};

}
