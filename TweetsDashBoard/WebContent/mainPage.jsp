<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<%@page import="org.json.JSONObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Tweets DashBoard</title>

<script language="javascript" type="text/javascript">
    var interval = 1000;
    var day = 0;
    var hour = 0;
    var minute = 0;
    var month = 0;
    var strJSON = "";
    var obj = null;
    var url = "\TweetsDashBoard\GetJsonServlet";
    var version = "";
    var message="";
    var messagedetail=new Array();
    var refresh=0;
    var strSpeed="";
    function drawDot(x,y,color,size){
        
        strSpeed+= "<table border='0' cellspacing=0 cellpadding=0><tr>"+
                  "<td style='position: absolute; left: "+
                  (x)+
                  "; top: "+
                  (y)+
                  ";background-color: "+
                  color+
                  "'width="+size+
                  " height="+
                  size+
                  "></td></tr></table>"; 
    }
    function drawLine(x1,y1,x2,y2,color,size){
        var i;
        var r=Math.floor(Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)));
        var theta=Math.atan((x2-x1)/(y2-y1));
        if(((y2-y1)<0&&(x2-x1)>0)||((y2-y1)<0&&(x2-x1)<0))
            theta=Math.PI+theta;
        var dx=Math.sin(theta);
        var dy=Math.cos(theta);
        for(i=0;i<r;i++){
            drawDot(x1+i*dx,y1+i*dy,color,size);
        }
    }
    function drawPict(){
        strSpeed="";
        drawLine(0,0,1000,100,'red',1);
        //drawLine(10,10,80,50,'green',1);
        drawSpeed('month');
    }
    function drawSpeed(divname){
        var div = document.getElementById(divname);
        div.innerHTML = strSpeed;
    }
    function getJson() {
        $.ajax({
            url : "GetJsonServlet",
            type : "POST",
            dataType : "json",
            contentType : "application/json; charset=utf-8",
            data : "{}",
            success : function(json) {
                month = json.month;
                day = json.day;
                minute = json.minute;
                hour = json.hour;

            },
            error : function(x, e) {
                month = 0;
                day = 0;
                minute = 0;
                hour = 0;
            },
            complete : function(x) {
            }
        });
    }
    function getTweets() {
        $.ajax({
            url : "GetTweetsServlet?request=version",
            type : "POST",
            dataType : "json",
            contentType : "application/json; charset=utf-8",
            data : "{}",
            success : function(json) {
                version = json.version;
            },
            error : function(x, e) {
            },
            complete : function(x) {
            }
        });
    }
    function getTop10(i) {
        $.ajax({
            url : "GetTweetsServlet?request=top10&id="+i,
            type : "POST",
            dataType : "json",
            contentType : "application/json; charset=utf-8",
            data : "",
            success : function(json) {
                message=json.message;
                messagedetail[i]=message;
            },
            error : function(x, e) {
            },
            complete : function(x) {
            }
        });
    }
    function display() {
        getJson();
        getTweets();
        for(var i=0;i<10;i++){
            getTop10(i);
            ShowTop10("news"+i,i);
        }
        ShowCount('Minute');
        ShowCount('Month');
        ShowCount('Day');
        ShowCount('Hour');
        ShowTop10('version');
        drawPict();
    }
    function ShowCount(divname) {
        var cc = document.getElementById(divname);
        if (divname == 'Minute') {
            cc.innerHTML = minute + " per " + divname;
        } else if (divname == 'Hour') {
            cc.innerHTML = hour + " per " + divname;
        } else if (divname == 'Day') {
            cc.innerHTML = day + " per " + divname;
        } else if (divname == 'Month') {
            cc.innerHTML = month + " per " + divname;
        }
    }
    function ShowTop10(divname,i) {
        
        var cc = document.getElementById(divname);
        if (divname == 'version') {
            cc.innerHTML = version;
        }else{
            cc.innerHTML=messagedetail[i];
        }
    }

    window.setInterval(function() {
        display();
    }, interval);
</script>
<meta name="viewport"
    content="width=device-width,inital-scale=1, user-scalable=no">
</head>
<body>

    <div >
        <div>
            <h1>Tweets DashBoard</h1>
        </div>
        <div >
            <div>
            <div class="ui-block-a" align="center"  >
                <div id="month" style="height:100px"></div>
                <div id="Month">loading...</div>
            </div>
            <div class="ui-block-b" align="center">
                <div id="day" style="height:100px"></div>
                <div id="Day">loading...</div>
            </div>
            <div class="ui-block-a" align="center">
                 <div id="hour" style="height:100px"></div>
                 <div id="Hour">loading...</div>
            </div>
            <div class="ui-block-b" align="center">
                 <div id="minute" style="height:100px"></div>
                 <div id="Minute">loading...</div>
            </div>
            </div>
            <ul>
                <li>Top 10 Tweets - version.
                    <div id=version>Loading..</div>
                </li>
                <%
                    for (int i = 0; i < 10; i++) {
                %>
                <li><a href="\TweetsDashBoard\GetTweetsServlet?id=<%=i%>"><%=i+1%>. 
                        <div id=news<%=i%>>loading</div></a></li>
                <%
                    }
                %>
            </ul>
            <ul>
                <li>Hostest Users</li>
                <%
                    for (int i = 0; i < 10; i++) {
                %>
                <li>loading</li>
                <%
                    }
                %>
            </ul>

        </div>

        <div>
            <h1>
                <a href="index.jsp">DashBoard For Mobile</a>
            </h1>
        </div>
    </div>


</body>
</html>