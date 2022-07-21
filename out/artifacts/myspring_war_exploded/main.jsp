<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style>
        html,body{
            margin: 0;
            padding: 0;
            width: 100%;
            height: 100%;
        }
        .body{
            width: 100%;
            height: 100%;
        }
        .top{
            margin: auto 0;
            width: 100%;
            height: 20%;
            background-color: #ccc;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .left{
            width: 15%;
            height: 80%;
            float: left;
            background-color: #eee;
        }
        .right{
            width: 85%;
            height: 80%;
            float: left;

        }
        li{
            list-style: none;
            line-height: 35px;
        }
        .title{
            font-size: 30px;
            font-weight: 700;
        }
        .user{
            font-weight: 500;
            font-size: 24px;
            padding-right:80px;
        }
        a{
            text-decoration:none;
        }
    </style>
    <script>
        window.onload = function () {
            document.getElementById("logout").onclick = function () {
                var b = confirm("是否需要注销账号");
                if(b){
                    location.href = "logout.do";
                }
            }
            // document.getElementById("setPass").onclick = function () {
            //     location.href = "setPassword.jsp";
            // }
        }

    </script>
</head>
<body>
<div class="body">
    <div class="top">
        <div class="title">XXXXX人员信息管理系统</div>
        <div class="user">欢迎 ${sessionScope.get("loginUser").getUname()} 进入 <a id="logout">[注销]</a></div>
    </div>
    <div class="left">
        <ul>
            <li>
                <dl>
                    <dt>权限管理</dt>
                    <dd><a href="userList.do" target="right">用户管理</a></dd>
                    <dd><a href="roleList.jsp" target="right">角色管理</a></dd>
                    <dd><a href="fnList.jsp" target="right">功能管理</a></dd>
                </dl>
            </li>
            <li>
                <dl>
                    <dt>基本信息管理</dt>
                    <dd>商品管理</dd>
                    <dd>供应商管理</dd>
                    <dd>库房管理</dd>
                </dl>
            </li>
            <li>
                经营管理
            </li>
            <li>
                库存管理
            </li>
            <li>
                财务管理
            </li>
            <li>
                系统管理
                <ul>
                    <li id="setPass"><a href="setPassword.jsp">修改密码</a></li>
                </ul>
            </li>
        </ul>
    </div>
    <div class="right">
        <iframe  name="right" width="100%" height="100%" frameborder="0"></iframe>
    </div>
</div>
</body>
</html>