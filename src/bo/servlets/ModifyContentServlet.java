package bo.servlets;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bo.ContentManager;
import bo.users.User;
import bo.users.UserManager;
import bo.users.session.SessionId;

import com.google.gson.stream.JsonWriter;

public class ModifyContentServlet extends HttpServlet
{
	public static final String ACTION_UPDATE = "update";
	public static final String ACTION_RETRIEVE = "retrieve";
	public static final String COOKIE_SESSION_ID = "s";

	public static final String ACTION = "action";
	public static final String SESSIONID = "sid";
	public static final String CONTENT_ID = "contentId";
	public static final String CONTENT_VALUE = "contentValue";
	public static final String PRIVILEGE_CONTENT_MANAGER = "CONTENT_MANAGER";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.getOutputStream().println("Unsupported request method");
	}

	private Map<String, String> cookieArrayToMap(Cookie[] arr)
	{
		HashMap<String, String> ret = new HashMap<String, String>();

		for (Cookie cookie : arr)
			ret.put(cookie.getName(), cookie.getValue());

		return ret;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		SessionId sessionId = null;
		String contentId = null;
		String contentValue = null;
		String action = null;

		Map<String, String> cookieMap = cookieArrayToMap(request.getCookies());
		ServletOutputStream os = response.getOutputStream();
		Map<String, String[]> parmMap = request.getParameterMap();
		Set<String> parms = parmMap.keySet();
		Iterator<String> itr = parms.iterator();
		while (itr.hasNext())
		{
			String curParm = itr.next();
			String curVal = parmMap.get(curParm)[0];

			System.out.println(curParm + "=" + curVal);

			if (curParm.equals(CONTENT_ID))
				contentId = curVal;
			else if (curParm.equals(CONTENT_VALUE))
				contentValue = curVal;
			else if (curParm.equals(ACTION))
				action = curVal;
		}

		String jOut = null;

		try
		{

			sessionId = new SessionId(cookieMap.get(COOKIE_SESSION_ID));
			User requestingUser = UserManager.getUser(sessionId);

			if (requestingUser==null || requestingUser.getPermissions()==null ||requestingUser.getPermissions().indexOf(PRIVILEGE_CONTENT_MANAGER) < 0)
				throw new Exception("Unprivileged account");

			if (ACTION_UPDATE.equals(action))
				jOut = updateContent(contentId, contentValue);
			else if (ACTION_RETRIEVE.equals(action))
				jOut = retrieveContent(contentId);
			else
			{
				String err = "Unsupported action query : " + action;
				throw new UnsupportedOperationException(err);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			StringWriter out = new StringWriter();
			JsonWriter json = new JsonWriter(out);
			json.beginObject();
			json.name("jsontype").value("error");
			json.name("message").value(e.getMessage());
			String stackTraceStr = "";
			StackTraceElement[] stackTrace = e.getStackTrace();
			for (int i = 0; i < stackTrace.length; i++)
			{
				StackTraceElement cur = stackTrace[i];
				stackTraceStr += cur.toString() + "\n";

			}
			json.name("stacktrace").value(stackTraceStr);
			json.endObject();
			json.close();

			jOut = out.toString();
		}
		os.print(jOut);
	}

	private String updateContent(String contentId, String contentValue) throws IOException
	{
		if (contentId == null)
			throw new UnsupportedOperationException("ContentId cannot be null");
		if (contentValue == null)
			throw new UnsupportedOperationException("ContentValue cannot be null");

		ContentManager.updateContent(contentId, contentValue);

		return createContentJsonString(contentId, contentValue);
	}

	private String retrieveContent(String contentId) throws IOException
	{
		if (contentId == null)
			throw new UnsupportedOperationException("ContentId cannot be null");

		String contentValue = ContentManager.retrieveContent(contentId);
		if (contentValue == null)
			contentValue = "";

		return createContentJsonString(contentId, contentValue);
	}

	private String createContentJsonString(String contentId, String contentValue) throws IOException
	{
		StringWriter out = new StringWriter();
		JsonWriter json = new JsonWriter(out);
		json.beginObject();
		json.name("jsontype").value("content");
		json.name("contentId").value(contentId);
		json.name("contentValue").value(contentValue);
		json.endObject();
		json.close();
		return out.toString();
	}

}
