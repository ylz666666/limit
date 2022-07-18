<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<head>
  <title>登录页面</title>
  <style>
    .body{
      width: 800px;
      height: 300px;
      margin: 100px auto;
      background-color: #999;
      background: url("xpic365.jpg") no-repeat ;
      background-size: cover;

    }
    .form{
      display: flex;
      flex-wrap: wrap;
      flex-direction:column;
      align-items: center;
      padding: 50px;
      font-size: 20px;
    }
    .items{
      margin: 30px;
    }
    .item{
      margin:0  10px;
      vertical-align: middle;
    }
    .button{
      font-size: 18px;
      border: 1px solid #999;
      border-radius: 10px;
      padding: 2px;
      width: 60px;
      margin-left:20px ;
    }

  </style>

</head>
<body>
<div class="body">
  <form align="center" action="login.do" class="form" method="post">
    <div style="font-weight: bold">用 户 登 录</div>
    <span style="color: red">${param.flag==9?"账号名或密码不正确":""}</span>
    <div class="items"><span class="item">账号</span><input name="uname" type="text" placeholder="请输入账号" required></div>
    <div class="items"><span class="item">密码</span><input name="upass" type="password" placeholder="请输入密码" required></div>
    <button class="button">登 录</button>
  </form>
</div>
</body>
