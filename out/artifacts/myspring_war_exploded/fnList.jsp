<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" language="java" %>
<html>
<head>
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
        #menuBox input{
            width:100px;
        }

        #menuBox div#active{
            background:#008c8c ;
        }
    </style>
    <script>
        window.onload = function(){
            loadData();
        }

        function loadData(){
            //ajax 查询所有的功能信息
            var xhr = new XMLHttpRequest();

            xhr.open('post','fnList.do',true);

            xhr.onreadystatechange = function(){
                if(xhr.readyState == 4 && xhr.status == 200){
                    doBack(xhr.responseText) ;
                }
            }

            xhr.send();

            function doBack(result){
                // 显示功能列表
                var fnList = eval('('+result+')') ;
                fnList = fnList.jsonObject;
                var menuBox =  document.getElementById("menuBox") ;
                showFn(fnList ,menuBox ); //将第一层(根)菜单，显示在指定的div中

                //将某一层fns菜单，显示在指定的position位置
                function showFn(fns , position){
                    var ul = document.createElement("ul");
                    position.appendChild(ul);
                    for(var i=0;i<fns.length;i++){
                        var fn = fns[i];
                        var li = document.createElement("li");
                        ul.appendChild(li) ;

                        var div = document.createElement("div");
                        li.appendChild(div);
                        var flag = fn.flag==1?'<font color="green">菜单</font>':'<font color="#ff4500">按钮</font>';

                        div.fno=fn.fno;
                        div.fname=fn.fname ;
                        div.fhref=fn.fhref ;
                        div.ftarget=fn.ftarget ;
                        div.flag = fn.flag ;

                        div.innerHTML = fn.fname+'<span>'+fn.ftarget+'</span><span>'+fn.fhref+'</span><span>'+flag+'</span>' ;

                        //-----当前菜单自己显示完毕，但还可能有子菜单、子按钮---
                        if(fn.children && fn.children.length > 0){
                            //有子内容
                            showFn(fn.children,li);
                        }
                    }
                }

                //列表展示完毕，想要增加一个点击父级菜单实现展开合并的操作
                var menuBox = document.getElementById('menuBox') ;
                var divs = menuBox.getElementsByTagName('div');
                for(var i=0;i<divs.length;i++){
                    var d = divs[i] ;
                    d.ondblclick = function(){
                        var nextUl = this.nextElementSibling ;
                        if(nextUl){
                            //有相邻的ul标签，有子菜单，展开或者合并
                            if(nextUl.style.display == 'none'){
                                nextUl.style.display = 'block' ;
                            }else{
                                nextUl.style.display = 'none' ;
                            }
                        }
                    }
                }

                //点击网页空白处，右键开启新建主功能
                document.oncontextmenu = function(){
                    return addRootFn() ;
                }

                //点击新建主功能按钮时，开启新建主功能
                var addRootFnBtn = document.getElementById('addRootFnBtn');
                addRootFnBtn.onclick = function(){
                    addRootFn();
                }

                //每一个菜单项增加一个右键新建子功能操作
                var menuBox = document.getElementById('menuBox') ;
                var divs = menuBox.getElementsByTagName('div');
                for(var i=0;i<divs.length;i++){
                    var div = divs[i];
                    div.oncontextmenu = function(ev){
                        return  addChildFn(this,ev);
                    }
                }

                //为每一个菜单增加单击选中效果
                var menuBox = document.getElementById('menuBox') ;
                var divs = menuBox.getElementsByTagName("div");
                for(var i=0;i<divs.length;i++){
                    var div = divs[i];
                    div.onclick = function(){
                        var active = document.getElementById("active");
                        if(active){
                            active.id = '' ;
                        }
                        this.id = "active" ;
                    }
                }

                //为删除按钮增加点击操作
                var deleteBtn = document.getElementById('deleteBtn');
                deleteBtn.onclick = function(){
                    //   div
                    var active = document.getElementById('active') ;
                    if(!active){
                        alert('请选择要删除的功能');
                        return ;
                    }


                    var ul = active.nextElementSibling ;
                    if(ul && ul.children.length > 0){
                        //有子功能，不能删除
                        alert('请先删除子功能');
                        return ;
                    }

                    if( confirm('是否确认删除') ){
                        //ajax 请求删除记录
                        var xhr = new XMLHttpRequest() ;

                        xhr.open('get','fnDelete.do?fno='+active.fno,true);

                        xhr.onreadystatechange = function(){
                            if(xhr.readyState == 4 && xhr.status == 200){
                                doBack(xhr.responseText);
                            }
                        };

                        xhr.send();

                        function doBack(result){
                            alert('删除成功');
                            var menuBox = document.getElementById("menuBox");
                            var uls = menuBox.children ;
                            var ul = uls[uls.length-1] ;
                            menuBox.removeChild(ul) ;
                            loadData();
                        }
                    }
                }

                //为编辑按钮增加点击操作
                var editBtn = document.getElementById('editBtn');
                editBtn.onclick = function(){
                    //   div
                    var active = document.getElementById('active');
                    if(!active){
                        alert('请选择要编辑修改的功能');
                        return ;
                    }
                    var fname = document.getElementById('fname') ;
                    if(fname){
                        alert('还有未完成的操作');
                        return false ;
                    }

                    var oldHtml = active.innerHTML ; //装载编辑之前显示的文本内容

                    active.innerHTML = '<input id="fname" placeholder="功能名称" value="'+active.fname+'" />' +
                        '<span><input id="ftarget" placeholder="显示位置" value="'+active.ftarget+'" /><button id="editBtn2" style="margin-left:2px;">修改</button><button id="cancelBtn" style="margin-left:2px;">取消</button></span>' +
                        '<span><input id="fhref" placeholder="功能请求" value="'+active.fhref+'" /></span>';
                    if(active.flag == 1){
                        active.innerHTML+='<span><select id="flag" ><option value="1">菜单</option><option value="2">按钮</option></select></span>';
                    }else{
                        active.innerHTML+='<span><select id="flag" ><option value="1">菜单</option><option selected="selected" value="2">按钮</option></select></span>';
                    }

                    var cancelBtn = document.getElementById('cancelBtn');
                    cancelBtn.onclick = function(){
                        active.innerHTML = oldHtml ;
                    }

                    var editBtn = document.getElementById('editBtn2') ;
                    editBtn.onclick = function(){
                        var fname = document.getElementById('fname').value ;
                        var flag = document.getElementById('flag').value ;
                        var fhref = document.getElementById('fhref').value ;
                        var ftarget = document.getElementById('ftarget').value ;
                        var fno = active.fno ;


                        //ajax 发送保存功能的请求
                        var xhr = new XMLHttpRequest() ;

                        xhr.open("post","fnUpdate.do",true);

                        xhr.onreadystatechange = function(){
                            if(xhr.readyState == 4 && xhr.status == 200){
                                doBack(xhr.responseText);
                            }
                        }
                        xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                        xhr.send('fname='+fname+'&fhref='+fhref+'&flag='+flag+'&ftarget='+ftarget+'&fno='+fno+'') ;

                        function doBack(result){
                            alert('修改成功');
                            var menuBox = document.getElementById("menuBox");
                            var uls = menuBox.children ;
                            var ul = uls[uls.length-1] ;
                            menuBox.removeChild(ul) ;
                            loadData();
                        }
                    }

                }
            }
        }

        function addChildFn(parent,ev){
            ev.cancelBubble = true ; //阻止冒泡

            var spans = parent.getElementsByTagName('span');
            var last_span = spans[spans.length-1] ;
            if(last_span.innerHTML.indexOf("按钮") != -1){
                //当前右键的功能是一个按钮，不能增加子功能
                alert('不能为按钮增加子功能');
                return false ;
            }

            var ul = parent.nextElementSibling ; //相邻的ul，装载着子菜单信息
            if(ul == null){
                ul = document.createElement('ul') ;
                //div    li
                parent.parentNode.appendChild(ul);
            }

            addFn(ul,parent.fno);


            return false ;
        }

        function addRootFn(){

            var menuBox = document.getElementById('menuBox') ;
            var uls = menuBox.children ; //*
            var ul = uls[uls.length-1] ;

            addFn(ul,-1);

            return false ;
        }

        function addFn(ul,pno){
            var fname = document.getElementById('fname') ;
            if(fname){
                alert('还有未完成的操作');
                return false ;
            }

            var li = document.createElement('li') ;
            ul.appendChild(li) ;

            var div = document.createElement('div') ;
            li.appendChild(div);

            div.innerHTML = '<input id="fname" placeholder="功能名称" />' +
                '<span><input id="ftarget" placeholder="显示位置" /><button id="addBtn" style="margin-left:2px;">保存</button><button id="cancelBtn" style="margin-left:2px;">取消</button></span>' +
                '<span><input id="fhref" placeholder="功能请求" /></span>' +
                '<span><select id="flag" ><option value="1">菜单</option><option value="2">按钮</option></select></span>';


            //为保存和取消两个按钮增加点击事件
            var cancelBtn = document.getElementById('cancelBtn');
            cancelBtn.onclick = function(){
                ul.removeChild(li) ;
            }

            var addBtn = document.getElementById('addBtn');
            addBtn.onclick = function(){
                var fname = document.getElementById('fname').value ;
                var flag = document.getElementById('flag').value ;
                var fhref = document.getElementById('fhref').value ;
                var ftarget = document.getElementById('ftarget').value ;
                //var pno = parent.fno ;


                //ajax 发送保存功能的请求
                var xhr = new XMLHttpRequest() ;

                xhr.open("post","addOne.do",true);

                xhr.onreadystatechange = function(){
                    if(xhr.readyState == 4 && xhr.status == 200){
                        doBack(xhr.responseText);
                    }
                }
                xhr.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                xhr.send('fname='+fname+'&fhref='+fhref+'&flag='+flag+'&ftarget='+ftarget+'&pno='+pno+'') ;

                function doBack(result){
                    alert('保存成功');
                    var menuBox = document.getElementById("menuBox");
                    var uls = menuBox.children ;
                    var ul = uls[uls.length-1] ;
                    menuBox.removeChild(ul) ;
                    loadData();
                }

            }
        }
    </script>
</head>
<body>
<div id="menuBox">
    <ul>
        <li>
            <button id="addRootFnBtn">新建主功能</button>
            <button>新建子功能</button>
            <button id="deleteBtn">删除功能</button>
            <button id="editBtn">编辑功能</button>
        </li>
    </ul>
    <ul>
        <li style="border:0;font-weight: bold;">
            <div>
                菜单名称
                <span>显示位置</span>
                <span>功能请求</span>
                <span>功能类型</span>
            </div>
        </li>
    </ul>

</div>
</body>
</html>
