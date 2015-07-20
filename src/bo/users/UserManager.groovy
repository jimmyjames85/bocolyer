package bo.users

import bo.users.session.SessionId;
import bo.util.DBAccessor;

public class UserManager
{

    private static final String USERS_TABLE = "users";
    private static final String USERS_FIRSTNAME = "firstName";
    private static final String USERS_LASTNAME = "lastName";
    private static final String USERS_USERNAME = "username";
    private static final String USERS_PASSWORD = "password";
    private static final String USERS_EMAIL = "email";
    private static final String USERS_PERMISSIONS = "permissions";

    private static final String SESSIONS_TABLE = "sessions";
    private static String SESSIONS_SESSION_ID = "sessionId";

    protected static String[] buildAddUserQuery(User user)
    {
        String[] q = new String[6];
        q[0] = "INSERT INTO " + USERS_TABLE + " ( ";
        q[0] += USERS_USERNAME + " , ";
        q[0] += USERS_PASSWORD + " , ";
        q[0] += USERS_FIRSTNAME + " , ";
        q[0] += USERS_LASTNAME + " , ";
        q[0] += USERS_EMAIL + ") ";

        q[0] += "VALUES ( ?,?,?,?,?)";
        q[1] = user.getUsername().toLowerCase();
        q[2] = user.getPassword();
        q[3] = user.getFirstName();
        q[4] = user.getLastName();
        q[5] = user.getEmail();
        return q;
    }

    /**
     * Trys to add the user to the database
     *
     * @param newUser
     */
    public static void addUser(User newUser) throws InvalidUserException
    {
        newUser.validateUser();
        DBAccessor.sendQuery(buildAddUserQuery(newUser));
    }

    protected static String[] buildChangePasswordQuery(String username, String currentPassword, String newPassword)
    {
        String[] q = new String[4];

        q[0] = "UPDATE " + USERS_TABLE + " SET ";
        q[0] += USERS_PASSWORD + "= ? ";
        q[0] += "WHERE " + USERS_USERNAME + " = ? AND ";
        q[0] += USERS_PASSWORD + " = ?";

        q[1] = newPassword;
        q[2] = username;
        q[3] = currentPassword;

        return q;

    }

    /**
     * Changes the password for the user if currentPassword is correct
     *
     * @param username
     * @param currentPassword
     *            - used to verify against the username
     * @param newPassword
     *            - new password to change to
     * @throws UserNotFoundException
     * @throws InvalidPasswordException
     * @throws InvalidUserException
     */
    public
    static void changeUserPassword(String username, String currentPassword, String newPassword) throws UserNotFoundException, InvalidPasswordException, InvalidUserException
    {
        if (username != null)
            username = username.toLowerCase();
        getUserViaUsername(username);
        DBAccessor.sendQuery(buildChangePasswordQuery(username, currentPassword, newPassword));
    }

    protected
    static String[] buildUpdateUserQuery(User updateUser) throws UserNotFoundException, InvalidPasswordException, InvalidUserException
    {
        String[] q = new String[5];

        // use username and password from updateUser to get curUser
        User curUser = UserManager.getUserViaUsername(updateUser.getUsername(), updateUser.getPassword());

        curUser.setFirstName(updateUser.getFirstName());
        curUser.setLastName(updateUser.getLastName());
        curUser.setEmail(updateUser.getEmail());

        q[0] = "UPDATE " + USERS_TABLE + " SET ";
        q[0] += USERS_FIRSTNAME + "=?,";
        q[0] += USERS_LASTNAME + "=?,";
        q[0] += USERS_EMAIL + "=?";
        q[0] += " WHERE " + USERS_USERNAME + "=?";

        q[1] = curUser.getFirstName();
        q[2] = curUser.getLastName();
        q[3] = curUser.getEmail();
        q[4] = curUser.getUsername().toLowerCase();
        return q;
    }

    /**
     * Updates the user information to match that of updateUser as long the
     * username/password combination are correct
     *
     * @param updateUser
     * @throws InvalidUserException
     */
    public static void updateUser(User updateUser) throws InvalidUserException
    {
        try
        {
            updateUser.validateUser();
            DBAccessor.sendQuery(buildUpdateUserQuery(updateUser));
        }
        catch (UserNotFoundException | InvalidPasswordException e)
        {
            throw new InvalidUserException(e.getMessage());
        }
    }

    protected static String[] buildGetUserQuery(String username)
    {
        if (username != null)
            username = username.toLowerCase();
        String[] q = new String[2];
        q[0] = "SELECT * FROM " + USERS_TABLE + " WHERE ";
        q[0] += USERS_USERNAME + "=?";
        q[1] = username;
        return q;
    }

    /**
     * returns a user object populated with the information associated with
     * username provided the username/password combination are correct
     *
     * @param username
     * @param password
     * @return
     * @throws UserNotFoundException
     * @throws InvalidPasswordException
     * @throws InvalidUserException
     */
    public
    static User getUser(String username, String password) throws UserNotFoundException, InvalidPasswordException, InvalidUserException
    {

        if (username != null)
            username = username.toLowerCase();

        User user = getUserViaUsername(username);
        if (!user.getPassword().equals(password))
            throw new InvalidPasswordException("Incorrect password");

        return user;
    }

    protected static String[] buildGetFriendListQuery(String username)
    {
        if (username != null)
            username = username.toLowerCase();
        String[] q = new String[2];

        q[0] = "SELECT * FROM " + USERS_TABLE + " WHERE ";
        q[0] += USERS_USERNAME + "=?";
        q[1] = username;
        return q;
    }
/*
	public static boolean addFriend(String username, String friendUsername)
	{
		if (!UserManager.doesUserExist(friendUsername) || username == null || username.equalsIgnoreCase((friendUsername.toUpperCase())))
			return false;

		List<String> list = getFriendList(username);

		String friendListString = "";
		for (int i = 0; i < list.size(); i++)
		{
			if (!list.get(i).equalsIgnoreCase(friendUsername))
				friendListString += list.get(i) + ";";
		}

		friendListString += friendUsername;
		updateFriendList(username, friendListString);
		return true;
	}

	public static boolean removeFriend(String username, String exFriendUsername)
	{
		exFriendUsername = exFriendUsername.toUpperCase();

		List<String> list = getFriendList(username);
		String friendListString = "";
		boolean found = false;
		for (int i = 0; i < list.size(); i++)
		{
			if (!list.get(i).toUpperCase().equals(exFriendUsername))
				friendListString += list.get(i) + ";";
			else
				found = true;
		}
		if (found)
			updateFriendList(username, friendListString);

		return found;
	}

	private static void updateFriendList(String username, String friendListString)
	{
		if(username!=null)
			username = username.toLowerCase();
		DBAccessor.sendQuery(buildUpdateFriendListQuery(username, friendListString));
	}

	protected static String[] buildUpdateFriendListQuery(String username, String friendList)
	{
		if(username!=null)
			username = username.toLowerCase();
		String q[] = new String[3];
		q[0] = "UPDATE " + USERS_TABLE + " SET friends=? WHERE ";
		q[0] += USERS_USERNAME + "=?";
		q[1] = friendList;
		q[2] = username;
		return q;
	}

	public static List<String> getFriendList(String username)
	{
		if(username!=null)
			username = username.toLowerCase();
		ArrayList<String> ret = new ArrayList<String>();
		List<Map<String, Object>> results = DBAccessor.sendQuery(buildGetFriendListQuery(username));

		if (results != null && results.get(0).get("friends") != null)
		{
			String friendsList = results.get(0).get("friends").toString();
			String[] arr = friendsList.split(";");
			for (int i = 0; i < arr.length; i++)
				if (arr[i].length() > 0)
					ret.add(arr[i]);
		}
		return ret;
	}
*/

    protected static String[] buildGetUserViaSessionIdQuery(SessionId sid)
    {

        String[] q = new String[2];

        q[0] = "SELECT " + USERS_USERNAME + " FROM " + SESSIONS_TABLE + " WHERE ";
        q[0] += SESSIONS_SESSION_ID + "=?";
        q[1] = sid.toString();
        return q;
    }

    /**
     * returns the user that has the SessionId sid
     *
     * @param sid
     * @return
     * @throws UserNotFoundException
     * @throws InvalidUserException
     */
    public static User getUser(SessionId sid) throws UserNotFoundException, InvalidUserException
    {
        println "SID = $sid"
        if (sid == null)
            throw new InvalidUserException("Please login.");

        User user = null;
        List<Map<String, Object>> results = DBAccessor.sendQuery(buildGetUserViaSessionIdQuery(sid));
        if (results != null)
        {
            Map<String, Object> row = results.get(0);// Only should have one
            String username = (String) row.get(USERS_USERNAME);
            user = getUserViaUsername(username);
        }
        if (user == null)
            throw new UserNotFoundException("No user found for sessionId");

        return user;
    }

    /**
     * returns true if username exists in the database. Otherwise false. This is
     * userful for the account creation page.
     *
     * @param username
     * @return
     */
    public static boolean doesUserExist(String username)
    {
        boolean ret = false;
        try
        {
            getUserViaUsername(username);
            ret = true;
        }
        catch (UserNotFoundException | InvalidUserException e)
        {
            ret = false;
        }
        return ret;
    }

    private static User getUserViaUsername(String username) throws UserNotFoundException, InvalidUserException
    {
        username = username?.toLowerCase();

        User user = new User(username);
        user.validateUsername();

        List<Map<String, Object>> results = DBAccessor.sendQuery(buildGetUserQuery(username));
        if (results == null)
            throw new UserNotFoundException("No such user: " + username);

        Map<String, Object> row = results.get(0);


        Object data = row.get(USERS_USERNAME);
        if (data != null)
            user.setUsername(data.toString());

        data = row.get(USERS_PASSWORD);
        if (data != null)
            user.setPassword(data.toString());

        data = row.get(USERS_FIRSTNAME);
        if (data != null)
            user.setFirstName(data.toString());

        data = row.get(USERS_LASTNAME);
        if (data != null)
            user.setLastName(data.toString());

        data = row.get(USERS_EMAIL);
        if (data != null)
            user.setEmail(data.toString());

        data = row.get(USERS_PERMISSIONS);
        if (data != null)
            user.setPermissions(data.toString());


        return user;
    }

    public static void main(String[] args)
    {

        User user = UserManager.getUser(new SessionId("761108746752656070589283692200633367281437433168526"))
        println user.username

    }

}
