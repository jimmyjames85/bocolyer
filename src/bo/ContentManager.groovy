package bo

import bo.users.User
import bo.users.UserManager
import bo.users.session.SessionId
import bo.util.DBAccessor

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.websocket.Session

/**
 * Created by jim on 7/14/15.*/
class ContentManager
{
	private static final String CONTENT_MANAGER = "CONTENT_MANAGER";
	private static final String CONTENT_TABLE = "content";
	private static final String CONTENT_ID = "id";
	private static final String CONTENT_VALUE = "content";


	protected static String[] buildUpdateContentQuery(String contentId, String contentValue)
	{
		String[] q = new String[3];
		q[0] = "REPLACE INTO " + CONTENT_TABLE + " ( ";
		q[0] += CONTENT_ID + " , ";
		q[0] += CONTENT_VALUE + " )";
		q[0] += "VALUES (?,?)";
		q[1] = contentId;
		q[2] = contentValue;
		return q;
	}


	protected static String[] buildRetrieveContentQuery(String contentId)
	{
		String[] q = new String[2];
		q[0] = "SELECT " + CONTENT_VALUE + " FROM " + CONTENT_TABLE + " WHERE ";
		q[0] += CONTENT_ID + "=?";
		q[1] = contentId;
		return q;
	}

	protected static String[] buildRetrieveContentIdsQuery()
	{
		String[] q = new String[1];
		q[0] = "SELECT " + CONTENT_ID + " FROM " + CONTENT_TABLE + " WHERE " + CONTENT_ID + " LIKE 'main.page%'"
		return q;
	}

	private static Map cookieArrayToMap(Cookie[] cookieArr)
	{
		Map map = [:]
		cookieArr.each {map.put(it.getName(), it.getValue())}
		return map;
	}

	public static boolean validateContentUser(HttpServletRequest request)
	{
		def cookieMap = cookieArrayToMap(request.getCookies())
		def permissions = UserManager.getUser(new SessionId(cookieMap?.s?.toString()))?.getPermissions()?.split(";")?.toList()
		return permissions.contains(CONTENT_MANAGER)
	}

	public static List getContentIds()
	{
		List ret = []
		List<Map<String, Object>> results = DBAccessor.sendQuery(buildRetrieveContentIdsQuery())
		results.each {
			if (results.last() != it)
				ret.add(it?.get(CONTENT_ID)?.toString())
		}

		if (ret.size() > 0)
			results?.removeElement(ret?.last())

		return ret
	}

	public static void updateContent(String contentId, String contentValue)
	{
		DBAccessor.sendQuery(buildUpdateContentQuery(contentId, contentValue));
	}

	public static String retrieveContent(String contentId)
	{
		String ret = null;
		List<Map<String, Object>> results = DBAccessor.sendQuery(buildRetrieveContentQuery(contentId));
		if (results != null)
			ret = results.get(0).get(CONTENT_VALUE).toString();
		return ret;
	}

}

