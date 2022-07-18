
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        ul,li{
            list-style: none;
        }
        form{
            align-content: center;
            text-align: center;
            margin-top: 120px;
        }
        .body{
            width: 30%;
            margin: auto;
            background-color: #008c8c;
            padding: 10px;
        }
        input:not([name="sex"]){
            float: right;
        }
        li{
            margin: 20px;
        }

        .fan{
            display: flex;
            justify-content: flex-start;
        }
        .fans{
            margin:0 55px;
        }

    </style>
</head>
<body>
<form  action="addUser.do"  width="80%" method="post">
    <div class="body">
        <h1>用户新建</h1>
        <li>
            <span> 用户名:</span>
            <input name="uname" type="text" required>
        </li>

        <li>
            <span>密码:</span>
            <input name="upass" type="password" required>
        </li>

        <li>
            <span>真实姓名:</span>
            <input name="truename" type="text" required>
        </li>

        <li>
            <span>年龄:</span>
            <input name="age" type="number" required>
        </li>

        <li class="fan">
            <span class="fans">性别:</span>
            <div>
                <label for="sex"><input name="sex" type="radio" value="男">男</label>
                <label for="sex"><input name="sex" type="radio" value="女">女</label>
            </div>

        </li>

        <li>
            <button>保存</button>
        </li>
    </div>

</form>
</body>
</html>
