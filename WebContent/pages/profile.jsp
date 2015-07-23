<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" href="../css/bo.css">
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Profile</title>
</head>
<body>
<div id="header">
    <%@ include file="header.jsp" %>
</div>
<div class="topDiv">

    <table>
        <tr>
            <td>Username</td>
            <td><input type="text" id="tbUsername" disabled="disabled" style="background: transparent;"></td>
        </tr>
        <tr>
            <td>First Name</td>
            <td><input type="text" id="tbFirstName"></td>
        </tr>
        <tr>
            <td>Last Name</td>
            <td><input type="text" id="tbLastName"></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input type="text" id="tbEmail"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="button" value="Update Info" id="btnChange" onclick="updateInfo()" disabled="disabled"></td>
        </tr>
    </table>


    <!-- <%--   <div id="friendList"></div>
    <button id="btnAddFriend" type=button onclick="addFriend()" disabled="disabled">Add Friend</button>
    <button id="btnDropFriend" type=button onclick="dropFriend()" disabled="disabled">Drop Friend</button>
    <div id="message"></div>
     --%> -->

</div>
</body>

<script>
    var currentUser;

    function populateUser(jsonString)
    {
        var json = JSON.parse(jsonString);
        if (json.jsontype == "user")
        {
            currentUser = json;
            $('#btnChange').attr("disabled", false);
            $('#btnLogout').attr("disabled", false);
            $('#btnAddFriend').attr("disabled", false);
            $('#btnDropFriend').attr("disabled", false);
            $('#tbUsername').val(json.username);
            $('#tbFirstName').val(json.firstName);
            $('#tbLastName').val(json.lastName);
            $('#tbEmail').val(json.email);
            var friends = json.friends;
            var out     = "FriendsList:<br><br>";
            for (var i = 0; i < friends.length; i++)
                out += friends[i] + "<br>"

            $('#friendList').html(out);
        }
    }

    function addFriend()
    {
        var friendName = prompt("Enter new friend name:");

        sendAddFriend(friendName, function (json)
        {
            getCurrentUser(populateUser);
        });
    }

    function dropFriend()
    {
        var friendName = prompt("Enter ex-friend name:");

        sendDropFriend(friendName, function (json)
        {
            getCurrentUser(populateUser);
        });
    }

    $(document).ready(function ()
    {
        $('#tbFirstName').focus();
        getCurrentUser(populateUser);
    });

    function updateInfo()
    {
        if (currentUser != "undefined")
        {
            currentUser.firstName = $('#tbFirstName').val();
            currentUser.lastName  = $('#tbLastName').val();
            currentUser.email     = $('#tbEmail').val();

            updateUserInfo(currentUser, function (response)
            {
                var json = JSON.parse(response);
                if (json.jsontype == "user")
                {
                    alert("Succes!!");
                }
                else
                {
                    var msg = json.message;
                    if (json.stacktrace != "undefined")
                    {
                        msg += "<br>Stack trace<pre>" + json.stacktrace + "</pre><br>";
                    }

                    if (json.jsontype == "error")
                    {
                        $('#message').html(msg);
                        alert("Error: " + json.message);
                    }
                }

                //Reload
                window.location = window.location

            });

        }

    }
</script>
</html>