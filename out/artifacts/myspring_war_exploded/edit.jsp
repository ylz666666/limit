<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>编辑</title>
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
<form  action="updateOne.do"  width="80%" method="post">
    <div class="body">
        <h1>用户新建</h1>
        <input type="hidden" name="uno" value="${requestScope.user.uno}">
        <li>
            <span> 用户名:</span>
            <input name="uname" type="text" required value="${requestScope.user.uname}">
        </li>

<%--        <li>--%>
<%--            <span>密码:</span>--%>
<%--            <input name="upass" type="password" required>--%>
<%--        </li>--%>

        <li>
            <span>真实姓名:</span>
            <input name="truename" type="text" required value="${requestScope.user.truename}">
        </li>

        <li>
            <span>年龄:</span>
            <input name="age" type="number" required value="${requestScope.user.age}">
        </li>

        <li class="fan">
            <span class="fans">性别:${requestScope.user.sex}</span>
            <div>
                <c:choose>
                    <c:when test="${requestScope.user.sex =='男'}">
                        <label><input type="radio" name="sex" value="男" checked="checked"/>男</label>
                        <label><input type="radio" name="sex" value="女"/>女</label>
                    </c:when>
                    <c:when test="${requestScope.user.sex =='女'}">
                        <label><input type="radio" name="sex" value="男" />男</label>
                        <label><input type="radio" name="sex" value="女" checked="checked" />女</label>
                    </c:when>
                </c:choose>

            </div>
        </li>

        <li>
            <button>保存</button>
        </li>
    </div>

</form>
</body>
</html>