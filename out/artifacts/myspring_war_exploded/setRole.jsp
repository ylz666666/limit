
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="js/jquery.min.js"></script>
    <style>
        ul,li{
            list-style: none;
            margin: 0;
            padding: 0;
        }
        #wrap{
            display: flex;
            justify-content: center;
            align-items: center;
            position: relative;
            top:40%;
            transform:translateY(-50%);
        }
        #unlink,#link{
            border: 1px solid #000000;
            margin-right: 50px;
            height: 400px;
            text-align: center;
        }
        #unlink li,#link li{
            margin-top: 10px;
        }
        #mid{
            margin-right: 50px;
            display: flex;
            flex-direction: column;
        }
        #mid button{
            margin-top: 20px;
        }
    </style>
    <script>
        $(function () {
            //调用ajax方法
            $.ajax({
                url:"findUnlink.do",//找到已分配的数据
                type:"post",
                data:{"uno":$("#uno").val()},
                success:function (roles) {
                    roles = roles.jsonObject;
                    //获取dom元素
                    for (let i = 0; i < roles.length; i++) {
                        $("#unlink ul").append(`<li rno="\${roles[i].rno}">\${roles[i].rname}</li>`);
                    }
                    addLeft();
                },
                dataType:"json"
            });
            //加载完成给每个元素绑定事件

        })
        $(function () {
            //调用ajax方法
            $.ajax({
                url:"findlink.do",//找到已分配的数据
                type:"post",
                data:{"uno":$("#uno").val()},
                success:function (roles) {
                    roles = roles.jsonObject;
                    //获取dom元素
                    for (let i = 0; i < roles.length; i++) {
                        $("#link ul").append(`<li rno="\${roles[i].rno}">\${roles[i].rname}</li>`);
                    }
                    addRight();
                },
                dataType:"json"
            });

        })

        $(function () {
            $("#right").click(function () {
                    $("#unlink li:gt(0)").appendTo($("#link ul"));
                    addRight();
                }
            );
        })
        $(function () {
            $("#left").click(function () {
                $("#link li:gt(0)").appendTo($("#unlink ul"));
                addLeft();
            });

        })

        //给左边每个列表绑定元素
        function addLeft() {
            $("#listLeft li:gt(0)").off("dblclick").dblclick(function () {
                //被点击的元素移动到右边
                $(this).appendTo($("#listRight"));
                addRight()
            });
        }

        //给右边每个列表绑定元素
        function addRight() {
            $("#listRight li:gt(0)").off("dblclick").dblclick(function () {
                $(this).appendTo($("#listLeft"));
                addLeft()
            })

        }
       //给按钮添加事件
        $(function () {
                $("#submit").click(function () {
                    if(!confirm("确定是否要保存")){
                        return;
                    }
                    //保存按钮
                    let rnos = "";
                    $("#listRight li:gt(0)").each(function (index,element) {
                            rnos+=$(element).attr("rno")+",";
                    })
                        $.ajax({
                            url:"setRole.do",
                            data:{uno:$("#uno").val(),rnos:rnos},//参数传uno rno
                            type:"post",
                            success(result){
                               alert(result);
                            },
                        })
                })
        })
    </script>
</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");
%>
<h2 style="text-align: center">${param.get("truename")}分配角色</h2>
<input id="uno" type="hidden" value="${param.get("uno")}">
<div style="text-align: center"><button id="submit">保存</button></div>
     <div id="wrap">
         <div id="unlink">
            <ul id="listLeft"><li>未分配角色列表</li></ul>
         </div>
         <div id="mid">
             <button id="right">&gt;&gt;</button>
             <button id="left">&lt;&lt;</button>
         </div>
         <div id="link">
            <ul id="listRight"><li>已分配角色列表</li></ul>
         </div>
     </div>
</body>
</html>
