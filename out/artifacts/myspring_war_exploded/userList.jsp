<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <style>
        ul,li{
            margin:0;
            padding:0;
            list-style:none;
        }
        .btnBox{
            margin-left:10%;
        }
        .btnBox li{
            float:left;
            margin-right:8px;
            margin-bottom:8px;
        }
        .btnBox input{
            width:100px;
        }


        .pageBox{
            float:right;
            margin-right:10%;
            margin-top:10px;
        }
        .pageBox li{
            float:left;
            margin-left:8px;
        }
        .mode{
            top: 0;
            bottom: 0;
            right: 0;
            left: 0;
            position: absolute;
            background: #000;
            opacity: .5;
            display: none;

        }
        .flex{
            width: 400px;
            height: 200px;
            background: #008c8c;
            position: absolute;
            z-index: 99;
            box-shadow: black 5px 5px;
            left: 50%;
            top: 50%;
            transform: translate(-50%,-50%);
            display: none;
            text-align: center;
            margin: auto;
        }
        .active{
            display: block;
        }
        .list{
            display: flex;
            flex-direction: column;
            align-content: space-around;
            margin: 0 110px;
        }
        .list button{
            width:100px;
            margin-top: 15px;

        }
    </style>

    <script>
        //网页加载时，执行的操作
        window.onload = function(){
            var maxPage = document.getElementById('maxPage') ;
            var pageSelectBtn = document.getElementById('pageSelectBtn') ;
            for(var i=1;i<=maxPage.value;i++){
                pageSelectBtn.innerHTML += '<option>'+i+'</option>';
            }
            pageSelectBtn.value = document.getElementById("page").value;
//--------------------------------------------------------

            //发送请求
            function loadData(){
                let unoVal = document.getElementById("unoBtn").value;
                let unameVal = document.getElementById("unameBtn").value;
                let sexVal = document.getElementById("selectSex").value;
                let showVal = document.getElementById("showPage").value;
                let curVal = document.getElementById("pageSelectBtn").value;
                console.log(unoVal,unameVal,sexVal,showVal,curVal);
                let url = "userList.do?uno="+unoVal+"&uname="+unameVal+"&sex="+sexVal+"&page="+curVal+"&row="+showVal;
                console.log(url);
                location.href = url;
            }
            //绑定事件 6个
            //查询按钮
            let query = document.getElementById("query");
            query.onclick = function () {
                loadData();
            }
            //清除按钮
            let clearbtn = document.getElementById("clearbtn");
            clearbtn.onclick = function () {
                document.getElementById("unoBtn").value="";
                document.getElementById("unameBtn").value="";
                //清空其他查询条件
                loadData();
            }

            //展示多少条
            let showPage = document.getElementById("showPage");
            showPage.value = document.getElementById("row").value;
            showPage.onchange = function () {
                    pageSelectBtn.value = 1;
                    loadData();
            }
            //跳转页面按钮
            pageSelectBtn.onchange = function () {
                    document.getElementById("pageSelectBtn").value = this.value;
                    loadData();
            }
            //上一页
            let prev = document.getElementById("prev");
            let next = document.getElementById("next");
            prev.onchange = function () {
                if(pageSelectBtn.value==1){
                    return;
                }
                pageSelectBtn.value = parseInt(pageSelectBtn.value)-1;
                loadData();
            }
            next.onchange = function () {
                if(pageSelectBtn.value == maxPage.value){
                    return;
                }
                pageSelectBtn.value= parseInt(pageSelectBtn.value)+1;
                loadData()
            }
            //性别初始化
            let sex = document.getElementById("selectSex");
            sex.value = document.getElementById("sex").value;

            //绑定新增事件
            let create = document.getElementById("create");
            create.onclick = function () {
                location.href = "userAdd.jsp";
            }

            //控制批量选择
            document.getElementById("selectAll").onclick = function () {
                        //获取所有输入框元素
                var eles = document.getElementsByClassName("input");
                    for (let i = 0; i <eles.length ; i++) {
                        eles[i].checked = "checked";
                    }
            }

            //批量删除
            document.getElementById("deletes").onclick = function () {
                //
                let arr = "";
                var eles = document.getElementsByClassName("input");
                for (let i = 0; i <eles.length ; i++) {
                   if(eles[i].checked==true){
                       arr+=eles[i].value + ",";
                   }
                }
                if(arr.length==0){
                    alert("请选择需要删除的记录");
                }
                var f = confirm("是否需要删除选中的项?");
                if(f==false){
                    return;
                }
                location.href = "delects.do?arr="+arr;
            }
            //批量导入
            document.getElementById("inputAll").onclick = function () {
                    //点击出现弹窗
                    document.getElementsByClassName("mode")[0].classList.add("active");
                    document.getElementsByClassName("flex")[0].classList.add("active");

            }
            //点击模型层样式隐藏
            document.getElementsByClassName("mode")[0].onclick = function () {
                document.getElementsByClassName("mode")[0].classList.remove("active");
                document.getElementsByClassName("flex")[0].classList.remove("active");
            }
            //给模板下载绑定事件
            document.getElementById("template").onclick = function () {
                location.href="uploadTem.do";
            }
            //批量导出
            document.getElementById("exportAll").onclick = function () {
                location.href = "export.do";
            }

        }




        //给删除事件
        function del(id) {
            location.href="deleteUser.do?uno="+id;
        }
        //给编辑按钮绑定事件
        function edit(num) {
            console.log("编辑事件"+num);
            //不能直接跳转到页面 先去控制层查询下
            location.href="edit.do?uno="+num;
        }
    </script>
</head>

<body>
<input type="hidden" id="maxPage" value="${requestScope.pageInfo.maxPage}" />
<input type="hidden" id="uno" value="${requestScope.uno}" />
<input type="hidden" id="row" value="${requestScope.row}" />
<input type="hidden" id="page" value="${requestScope.page}" />
<input type="hidden" id="sex" value="${requestScope.sex}" />
<h2 align="center">用户列表</h2>
<ul class="btnBox">
    <li><input id="unoBtn" placeholder="输入编号" value="${requestScope.uno}"/></li>
    <li><input id="unameBtn" placeholder="输入名称" value="${requestScope.uname}"/></li>
    <li>

                <select id="selectSex">
                    <option value="">=性别=</option>
                    <option>男</option>
                    <option>女</option>
                </select>
    </li>

    <li><button id="query" type="button">查询</button></li>
    <li><button id="clearbtn" type="button">清空查询</button></li>
    <li><button id="create" type="button">新增用户</button></li>
    <li><button id="deletes" type="button">批量删除</button></li>
    <li><button id="inputAll" type="button">批量导入</button></li>
    <li><button id="exportAll" type="button">批量导出</button></li>
</ul>
<table width="80%" border="1" align="center" cellspacing="0">
    <thead>
    <tr>
        <th><button id="selectAll">批量操作</button></th>
        <th>用户编号</th>
        <th>用户名</th>
        <th>真实姓名</th>
        <th>年龄</th>
        <th>性别</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:choose>
        <c:when test="${requestScope.pageInfo.list.size() == 0}">
            <tr align="center">
                <td colspan="6">没有任何记录</td>
            </tr>
        </c:when>
        <c:otherwise>
            <c:forEach items="${requestScope.pageInfo.list}" var="user" >
                <tr align="center">
                    <td><input class="input" name="checked" value="${user.uno}" type="checkbox"></td>
                    <td>${user.uno}</td>
                    <td>${user.uname}</td>
                    <td>${user.truename}</td>
                    <td>${user.age}</td>
                    <td>${user.sex}</td>
                    <td>
                        <a id="edit" href="javascript:edit(${user.uno})">编辑</a> |
                        <a id="delete" href="javascript:del(${user.uno})">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </c:otherwise>
    </c:choose>
    </tbody>
</table>

<ul class="pageBox">
    <li>
        显示
        <select id="showPage">
            <option selected>5</option>
            <option>10</option>
            <option>15</option>
            <option>20</option>
        </select>
        条
    </li>
    <li>
        跳转
        <select id="pageSelectBtn"></select>
        页
    </li>
    <li><button type="button" id="prev">上一页</button></li>
    <li><button type="button" id="next">下一页</button></li>
</ul>
<div class="mode "></div>
<div class="flex ">
    <form class="list"  method="post" action="importUsers.do" enctype="multipart/form-data">
        <h2>导入用户</h2>
           <input  type="file" name="excel">
            <button type="button" id="template" >模板下载</button>
            <button type="submit">保存</button>
    </form>
</div>
</body>
</html>

