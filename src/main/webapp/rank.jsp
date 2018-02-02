<%--
  Created by IntelliJ IDEA.
  User: xing
  Date: 2018/1/30
  Time: 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>明日竞赛排名</title>
    <script type="text/javascript" src="js/jquery-2.1.0.min.js"></script>
    <script>
        function getData(){
            $.ajax({
                url:"rankpage.op",
                data:"page=1&size=10",
                type:"get",
                success:function(data){
                    console.log(data);
                    if(data.code==200){
                        $("#sp1").append("总页数："+data.data.totalPages);
                        for(i in data.data.content){
                            var obj=data.data.content[i];
                            $("#sp1").append("<br/>"+obj.score);
                        }
                    }else{
                        alert(data.msg);
                    }

                }

            });
        }

    </script>

</head>
<body>
<div>
    <h1>新增排名信息</h1>
   <form action="rankadd.op" method="post">
       <input placeholder="请输入组好" name="groupNo"><br/>
       <input placeholder="请输入项目名称" name="projectName"><br/>
       <input placeholder="请输入分数" name="score"><br/>
       <input type="submit" value="新增排名">
   </form>
</div>
<hr/>
<a href="/index.op">定时任务开启</a>
<div>
    <input type="button" onclick="getData()" value="刷数据">
    <table>
        <thead>
        <tr>
            <th>序号</th>
            <th>分数</th>
            <th>zuhao</th>
            <th>项目名称</th>
            <th>操作</th>

        </tr>

        </thead>

    </table>
    <span id="sp1"></span>

</div>




</body>
</html>
