<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<html>
<head>
    <script src="js/jquery.min.js"></script>
    <style>
        #menuBox{
            width:1000px;
        }
        #menuBox li{
            list-style:none;
            margin-top:4px;
            cursor:default ;
        }
        #menuBox div{
            border-bottom:1px solid #ccc ;
        }

        #menuBox span{
            display:block;
            width:200px;
            height:16px;
            float:right;

        }
    </style>
    <script>
        //网页加载时，加载功能数据
        $(function(){
            $.ajax({
                url:'fnList.do',
                data:{},
                type:'get',
                success:function(fnList){
                    //使用递归展示数据
                    fnList = fnList.jsonObject;
                    showLevelFnList(fnList,$('#menuBox')) ;
                    $(function () {
                        let rno = parseInt($("#rno").val());
                        $.post("getFnos.do",{rno:rno},function (result) {
                            result = result.jsonObject;
                            console.log(result);
                            $("#menuBox :checkbox").each(function (index,element) {
                                for (let i = 0; i <result.length ; i++) {
                                    console.log($(element).val());
                                    if(result[i]==$(element).val()){
                                        $(element).prop("checked",true);
                                    }
                                }

                            })
                        })
                    })

                    function showLevelFnList(fns,$position) {
                        var ul = $('<ul>'); // document.createElement('ul');
                        $position.append(ul);   // position.appendChild(ul);
                        for (var i = 0; i < fns.length; i++) {
                            var fn = fns[i];
                            var li = $('<li>');
                            ul.append(li);
                            var div = $('<div>');
                            li.append(div);

                            div.append('<input type="checkbox" value="' + fn.fno + '"/> ');
                            div.append(fn.fname);
                            div.append('<span>' + fn.ftarget + '</span>');
                            div.append('<span>' + fn.fhref + '</span>');
                            div.append('<span>' + (fn.flag == 1 ? '<font color="green">菜单</font>' : '<font color="red">按钮</font>') + '</span>');
                            if (fn.children && fn.children.length > 0) {
                                //有子菜单
                                showLevelFnList(fn.children, li);
                            }
                        }
                    }
                    //数据装载完毕
                    //为菜单列表增加双击展开合并操作
                    $('#menuBox > ul:gt(0) div').dblclick(function(){
                        // div   ul
                        $(this).next('ul').toggle(600);
                    });

                    //给多选框绑定事件
                    $(function () {
                        $("#menuBox :checkbox").click(function () {
                            //定义2个方法向上递归向下递归
                            //三种情况1.子类被选中 找到父类选中状态 2.父类被选中找到子类选中
                            //3.多个子类被选中

                            //向上
                            if(this.checked){//如果当前元素被选中 里面的都是选中时候的情况
                                findPar($(this));
                                function findPar($cur) {
                                    //子元素选中 父元素必须选中
                                    //递归选中当前菜单的子级菜单
                                    //                div   li          ul        div
                                    var div = $cur.parent().parent().parent().prev("div");
                                    if(div.length>0){//说明有父元素
                                        var input = div.children("input");
                                        input.prop("checked",true);
                                        findPar(input);
                                    }
                                }
                                //往下递归 父元素选中子元素才可以选中
                                findSon($(this));
                                function  findSon($cur){
                                    var ul = $cur.parent().parent().children(); //找到ul
                                    if(ul.length>0){//说明有子元素
                                        var inputs = ul.children().children().children("input");
                                        inputs.prop("checked",true);
                                        findSon(inputs);
                                    }

                                }
                            }else{//当前元素被取消
                                    cancalPar($(this));
                                    function cancalPar($cur) {
                                        //这里只取消父集 子级在下个方法里面取消
                                        //取消子集的时候判断下是否还有其他子集选中
                                        let div = $cur.parent().parent().parent().prev("div");
                                        if(div.length>0){
                                            //                  div   li
                                            let lis = $cur.parent().parent().siblings("li");
                                            let inputs = lis.children().children("input");
                                            let b = false;
                                            console.log(lis,inputs);
                                            inputs.each(function (index,element) {
                                                if($(element).prop("checked")){
                                                    b = true;
                                                }
                                            })
                                            if(!b){
                                                let input = div.children("input");
                                                input.prop("checked",false);
                                                cancalPar(input);
                                            }

                                        }

                                    }
                                    cancalSon($(this));
                                    function  cancalSon($cur){//取消子级
                                        let ul = $cur.parent().parent().children("ul");
                                        if(ul.length>0){
                                            let inputs = ul.children().children().children("input");
                                            inputs.attr("checked",false);
                                            cancalSon(inputs);
                                        }
                                    }
                            }




                        })
                    })
                    //给保存按钮绑定事件
                    $(function () {
                        let fnos = "";
                        let rno = parseInt($("#rno").val());
                        $("#submit").click(function () {
                            if(!confirm("确认是否提交?")){
                                return;
                            }
                            $("#menuBox input:checked").each(function (index,element) {
                                fnos+=$(element).val()+",";
                            })
                            $.post("setFn.do",{rno:rno,fnos:fnos},function (result) {
                                alert(result);
                                //用户进入的时候帮用户选中
                            })
                        })
                    })


                },
                dataType:'json'
            });
        });



    </script>
</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");
%>
<h2 >为【${param.rname}】角色分配功能</h2>
<button style="margin-left: 40px" id="submit">保存</button>
<input type="hidden" id="rno" value="${param.rno}" />
<div id="menuBox">
    <ul>
        <li style="border:0;font-weight: bold;">
            <div>
                菜单名
                <span>显示位置</span>
                <span>功能请求</span>
                <span>功能类型</span>
            </div>
        </li>
    </ul>
    <!--
               <ul>
                   <li>
                       <div><input checkbox />菜单名 <span></span> <span></span> <span></span> </div>
                       <ul>
                           <li><div><inpput checkbox checked=true/></div></li>
                           <li><div><input checkbox checked=true/></div></li>
                           <li><div><input checkbox checked=true/></div></li>
                       </ul>
                   </li>
                   <li><div>菜单名 <span></span> <span></span> <span></span> </div></li>
                   <li><div>菜单名 <span></span> <span></span> <span></span> </div></li>
               </ul>
               -->


</div>
</body>
</html>
