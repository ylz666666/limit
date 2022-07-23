<%@ page import="java.net.URLEncoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>角色管理</title>
    <style>
        ul,li{
            list-style-type: none;
            margin: 0;
            padding: 0;
        }
        #wrap{
            width: 80%;
            margin:auto ;
            text-align: center;
        }
        li{
            float: left;
            margin-right: 12px;
        }
        #footer{
            float: right;
        }
        #head li{
            margin-bottom: 10px;
        }

    </style>
    <script>
        window.onload = function () {
            let xml = new XMLHttpRequest();
            //获取参数
            let rno = document.getElementById("rno")
            let rname = document.getElementById("rname")
            let rdes = document.getElementById("rdes")
            let page = document.getElementById("page")
            let row = document.getElementById("row")
            let tbody = document.getElementById("tbody");
            let query = document.getElementById("query");
            let empty = document.getElementById("empty");
            let pageVal ;
            let rowVal ;
            let maxPage;
            ajax();
            function ajax(){
                 let rnoVal = rno.value;
                 let rnameVal = rname.value;
                 let rdesVal = rdes.value;
                 rowVal = row.value;
                 pageVal = page.value;
                xml.open("post","roleList.do?rno="+rnoVal+"&rname="+rnameVal+"&rdes="+rdesVal+"&page="+pageVal+"&row="+rowVal,true);
                xml.onreadystatechange = function(){
                    if(xml.readyState==4&&xml.status==200){
                        callback(xml.responseText);
                    }
                }
                xml.send();
            }
            function callback(result) {
                result = JSON.parse(result);
                let roleList = result.jsonObject.list;
                if(!roleList){
                    let str = `<tr><td colspan="6">没有任何数据</td></tr>`;
                    tbody.innerHTML = str;
                }
                maxPage = result.jsonObject.maxPage;
                maxPage = Math.max(1,maxPage);//不能显示为0;
                let pages = "";
                for (let i = 1; i <= maxPage; i++) {
                    pages+=`<option value=\${i}>\${i}</option>`;
                }
                page.innerHTML = pages;
                page.value = pageVal;
                let value ="";
                for (let i = 0; i <roleList.length; i++) {
                     value += `<tr>
                        <td>\${roleList[i].rno}</td>
                        <td>\${roleList[i].rname}</td>
                        <td>\${roleList[i].description}</td>
                         <td><a href="">编辑</a> |
                        <a href="">删除</a>| <a href="setFn.jsp?rno=\${roleList[i].rno}&rname=\${roleList[i].rname}">分配功能</a></td>

                    </tr>`
                }
                tbody.innerHTML = value;
            }
            //给查询按钮绑定事件
            query.onclick = function () {
                page.value = 1;//页数变成1
                loadData();
            }
            //给清空查询绑定事件
            empty.onclick = function () {
                rno.value = "";
                rname.value="";
                rdes.value = "";
                page.value=1;
                loadData();
            }

            //给显示多少条绑定事件
            row.onchange = function () {
                    page.value = 1;//页数变成1
                    loadData();
            }
            //给显示第几页绑定事件
            page.onchange = function () {
                // document.getElementById("page").value = this.value;
                loadData();
            }
            //给上一页绑定事件
            let prev = document.getElementById("prev");
            prev.onclick = function () {
                if(page.value==1){
                    return;
                }
                page.value--;
                loadData();
            }
            //给下一页绑定事件
            let next = document.getElementById("next")
            next.onclick = function () {
                if(page.value==maxPage){
                    return;
                }
                page.value++;
                loadData();
            }
            function  loadData() {
                //继续发送请求
                ajax();
            }
        }
    </script>
</head>
<body>
    <div id="wrap">
        <h2>角色列表</h2>
            <ul id="head">
                <li><input id="rno" type="text" name="rno" placeholder="请输入角色编号"></li>
                <li><input id="rname" type="text" name="rname" placeholder="请输入角色名称"></li>
                <li><input id="rdes" type="text" name="rdes" placeholder="请输入角色描述"></li>
                <li><button id="query">查询</button></li>
                <li><button id="empty">清空查询</button></li>
            </ul>
        <form action="" method="post" align="center">
            <table align="center" width="100%" border="1">
                <thead>
                <tr>
                    <th>角色编号</th>
                    <th>角色名称</th>
                    <th>角色描述</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody align="center" id="tbody">

                </tbody>
            </table>
        </form>

            <ul id="footer">
                <li>
                    显示
                    <select name="row" id="row">
                        <option value="5">5</option>
                        <option value="10">10</option>
                        <option value="15">15</option>
                        <option value="20">20</option>
                    </select>
                    条
                </li>
                <li>
                    显示
                    <select name="page" id="page">
                        <option value="1">1</option>
                    </select>页
                </li>
                <li><button id="prev">上一页</button></li>
                <li><button id="next">下一页</button></li>
            </ul>
    </div>
</body>
</html>
