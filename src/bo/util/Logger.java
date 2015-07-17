package bo.util;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

public class Logger
{
	@SuppressWarnings("rawtypes")
	private Class loggingClass;
	private static boolean debug = false;
	private static boolean warn = false;
	private static boolean info = true;

	public static final String FS = System.getProperty("file.separator");
	private static final String PROPERTY_FILE_LOC = ".." + FS + "context.properties"; //This should be in the WEB-INF folder
	private static final String PROPERTY_LOG_LOCATION = "log.file.location";


	private File logFile ;
	public static Logger thisLogger = new Logger(Logger.class);

	private static String getDateNow()
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		df.setTimeZone(TimeZone.getDefault());
		return df.format(new Date());

	}

	public File getLogFile()
	{
		return logFile;
	}

	public static void pageVisit(HttpServletRequest request)
	{
		pageVisit(request, "");
	}

	public static void pageVisit(HttpServletRequest request, String msg)
	{
		String remoteAddr = request.getRemoteAddr();
		if(remoteAddr!=null)
			remoteAddr = remoteAddr.trim();

		if("192.168.0.2".equals(remoteAddr))
			return;

		if("127.0.0.1".equals(remoteAddr))
			return;


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
		Properties props = FileHelper.getDatabaseProperties(PROPERTY_FILE_LOC);
		logFile = new File(props.getProperty(PROPERTY_LOG_LOCATION));
	}

	private void log(Object x, String method)
	{
		String msg = "[" + loggingClass.getSimpleName() + ":" + method + "]" + x.toString();
		log(msg);
	}

	private void log(String msg)
	{
		FileWriter fw = null;
		try
		{
			if(!logFile.exists())
				logFile.createNewFile();

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
