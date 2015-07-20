<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <link rel="stylesheet" href="../css/bo.css">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create An Account</title>
</head>
<body>
<div id="header">
    <%@ include file="header.jsp" %>
</div>
<script src="../scripts/session.js"></script>
<script src="../scripts/jquery-2.1.1.js"></script>

<div class="topDiv">
    <table>
        <tr>
            <td>Username</td>
            <td><input type="text" id="username" onkeyup="checkAvailability(event);"></td>
            <td><div id="userAvailability"></div></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input type="password" id="password1"></td>
            <td></td>
        </tr>
        <tr>
            <td>Re-Enter Password</td>
            <td><input type="password" id="password2"></td>
            <td><div id="passwordCheck"></div></td>
        </tr>

        <tr>
            <td>First Name</td>
            <td><input type="text" id="firstName"></td>
            <td></td>
        </tr>

        <tr>
            <td>Last Name</td>
            <td><input type="text" id="lastName"></td>
            <td></td>
        </tr>

        <tr>
            <td>Email Address</td>
            <td><input type="text" id="email"></td>
            <td></td>
        </tr>

        <tr>
            <td></td>
            <td></td>
            <td><input id="createButton" type="button" value="Create Account" onclick="doCreateAccount();"></td>
        </tr>
    </table>

</div>
</body>
<script>
    function doCreateAccount()
    {
        var username  = $('#username').val();
        var password1 = $('#password1').val();
        var password2 = $('#password2').val();
        var email     = $('#email').val();
        var firstName = $('#firstName').val();
        var lastName  = $('#lastName').val();

        if (password1 == password2)
        {
            $('#passwordCheck').html("");
            createUser(username, password1, email, firstName, lastName, function (response)
            {
                var json = JSON.parse(response);
                if (json.jsontype == "user")
                {
                    userLogin(json.username, json.password, function (u)
                    {
                        var user = JSON.parse(u);
                        //set sessionId
                        setCookie("s", user.sessionId, 1, "/");
                        //LOGIN redirect to ContentManager
                        window.location = "contentEditor.jsp";
                    });

                }
                else
                {
                    alert(response);
                }

            });
        }
        else
            $('#passwordCheck').html("Passwords don't match!!!");
    }

    function checkAvailability(e)
    {
        //wait for username to be set
        setTimeout(function ()
        {
        }, 500);
        var username = $("#username").val();

        doesUserExist(username, function (response)
        {
            var json = JSON.parse(response);
            if (json.jsontype == "userExists" && json.exists)
            {
                $("#userAvailability").html(username + " is already taken.");
                $('#createButton').attr("disabled", true);
            }
            else
            {
                $("#userAvailability").html("");
                $('#createButton').attr("disabled", false);

            }
        });
    }
    $(document).ready(function ()
    {
        $('#username').focus();
    });
</script>

</html>