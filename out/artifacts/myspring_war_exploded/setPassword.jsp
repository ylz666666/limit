<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2022/7/18
  Time: 18:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改密码</title>
    <style>
        ul,li,input{
            display: block;
            list-style-type: none;
            margin: 0;
            padding:0;
        }
        h2{
            margin: 0;
        }
        #form{
            display: flex;
        }
        ul li{
            margin: 20px 0;
        }
        #wrap{
            margin: 100px auto;

        }
        #left{
            margin:0 15px;
        }
        form{
            width: 300px;
            border: 1px solid #fff;
            padding: 20px;
            background: #008c8c;
            margin: auto;
        }

    </style>
</head>
<body>
<div id="wrap">
    <form align="center" action="setPassword.do" method="post">
        <h2 align="center">修改密码</h2>
        <div id="form">
            <ul id="left">
                <li>原密码:</li>
                <li>新密码:</li>
                <li>确认密码:</li>
            </ul>
            <ul id="right">
                <li><input type="password" name="opass"></li>
                <li><input type="password" name="npass"></li>
                <li><input type="password" name="upass"></li>
            </ul>
        </div>
        <button>保存</button>
    </form>
</div>

</body>
</html>
