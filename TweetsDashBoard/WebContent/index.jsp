<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@page import="org.json.JSONObject"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Tweets DashBoard</title>
<link rel="stylesheet"
	href="http://code.jquery.com/mobile/1.3.0/jquery.mobile-1.3.0.min.css" />
<script src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
<script
	src="http://code.jquery.com/mobile/1.3.0/jquery.mobile-1.3.0.min.js"></script>
	<script src="js/raphael.2.1.0.min.js"></script>
    <script src="js/justgage.1.0.1.min.js"></script>
    <script src="js/myJustGage.js"></script>
    
    <style>
      
      #g1, #g2, #g3, #g4, #g5, #g6, #g7, #g8 {
        width:100px; height:80px;
        display: inline-block;
        margin: 1em;
      }
      
    </style>
    
    <script language="javascript" type="text/javascript">
      var g1max, g2max, g3max,g4max;
      var g1, g2, g3, g4;
      var g5max, g6max, g7max,g8max;
      var g5, g6, g7, g8;
      var limitmax=100;
      var limitsentiment=4;
      window.onload = function(){
    	
    	g1max=1000; 
    	g2max=1000;
    	g3max=1000;
    	g4max=1000;
    	g5min=-4; 
        g6min=-4;
        g7min=-4;
        g8min=-4;
    	g5max=4; 
        g6max=4;
        g7max=4;
        g8max=4;
        
        g1 = new JustGage({
          id: "g1", 
          value: 0, 
          min: 0,
          max: g1max,
          title: "tweets per munite",
          valueFontColor: "#999999",
          label: ""
        });
        
        g2 = new JustGage({
          id: "g2", 
          value: 0, 
          min: 0,
          max: g2max,
          title: "tweets per hour",
          valueFontColor: "#999999",
          label: ""
        });
        
        g3 = new JustGage({
          id: "g3", 
          value: 0, 
          min: 0,
          max: g3max,
          title: "tweets per day",
          valueFontColor: "#999999",
          label: ""
        });
        
        g4 = new JustGage({
          id: "g4", 
          value: 0, 
          min: 0,
          max: g4max,
          title: "tweets per month",
          valueFontColor: "#999999",
          label: ""
        });
        
        g5 = new JustGage({
            id: "g5", 
            value: 0, 
            min: g5min,
            max: g5max,
            levelColors: percentColorsSentiment,
            valueFontColor: "#999999",
            title: "tweets minute score",
            label: ""
          });
          
          g6 = new JustGage({
            id: "g6", 
            value: 0, 
            min: g6min,
            max: g6max,
            levelColors: percentColorsSentiment,
            title: "tweets hour score",
            valueFontColor: "#999999",
            label: ""
          });
          
          g7 = new JustGage({
            id: "g7", 
            value: 0, 
            min: g7min,
            max: g7max,
            levelColors: percentColorsSentiment,
            title: "tweets day score",
            valueFontColor: "#999999",
            label: ""
          });
          
          g8 = new JustGage({
            id: "g8", 
            value: 0, 
            min: g8min,
            max: g8max,
            levelColors: percentColorsSentiment,
            title: "tweets month score",
            valueFontColor: "#999999",
            label: ""
          });
      };
   
      function setPic(){
    	  if(minute<0) minute=0;
    	  if(hour<0) hour=0;
    	  if(day<0) day=0;
    	  if(month<0) month=0;
    	  if(minute>(g1max/6*5)){
    		  g1max=g1max*2;
    		  g1.refreshMax(g1max);
    	  }
    	  if(minute<g1max/6&&g1max>limitmax){
    		  g1max=(g1max/2<limitmax)?limitmax:g1max/2;
    		  g1.refreshMax(g1max);
    	  }
          g1.refresh(minute);
          if(hour>(g2max/6*5)){
              g2max=g2max*2;
              g2.refreshMax(g2max);
          }
          if(hour<g2max/6&&g2max>limitmax){
              g2max=(g2max/2<limitmax)?limitmax:g2max/2;
              g2.refreshMax(g2max);
          }
          g2.refresh(hour); 
          if(day>(g3max/6*5)){
        	  g3max=g3max*2;
              g3.refreshMax(g3max);
          }
          if(day<g3max/6&&g3max>limitmax){
              g3max=(g3max/2<limitmax)?limitmax:g3max/2;
              g3.refreshMax(g3max);
          }
          g3.refresh(day);
          if(month>(g4max/6*5)){
              g4max=g4max*2;
              g4.refreshMax(g4max);
          }
          if(month<g4max/6&&g4max>limitmax){
              g4max=(g4max/2<limitmax)?limitmax:g4max/2;
              g4.refreshMax(g4max);
          }
          g4.refresh(month);
          if(Math.abs(minutescore)>(g5max/6*5)){
        	  g5max=g5max*2;
        	  g5min=-g5max;
        	  g5.refreshMax(g5max);
        	  g5.refreshMin(g5min);
          }
          if(Math.abs(minutescore)<(g5max/4)&&g5max>limitsentiment){
              g5max=(g5max/2<limitsentiment)?limitsentiment:g5max/2;
              g5min=-g5max;
              g5.refreshMax(g5max);
              g5.refreshMin(g5min);
          }
          g5.refresh(Math.round(minutescore*100)/100);
          if(Math.abs(hourscore)>(g6max/6*5)){
              g6max=g6max*2;
              g6min=-g6max;
              g6.refreshMax(g6max);
              g6.refreshMin(g6min);
          }
          if(Math.abs(hourscore)<(g6max/4)&&g6max>limitsentiment){
              g6max=(g6max/2<limitsentiment)?limitsentiment:g6max/2;
              g6min=-g6max;
              g6.refreshMax(g6max);
              g6.refreshMin(g6min);
          }
          g6.refresh(Math.round(hourscore*100)/100);
          if(Math.abs(dayscore)>(g7max/6*5)){
              g7max=g7max*2;
              g7min=-g7max;
              g7.refreshMax(g6max);
              g7.refreshMin(g6min);
          }
          if(Math.abs(dayscore)<(g7max/4)&&g7max>limitsentiment){
              g7max=(g7max/2<limitsentiment)?limitsentiment:g7max/2;
              g7min=-g7max;
              g7.refreshMax(g7max);
              g7.refreshMin(g7min);
          }
          g7.refresh(Math.round(dayscore*100)/100);
          if(Math.abs(monthscore)>(g8max/6*5)){
              g8max=g8max*2;
              g8min=-g8max;
              g8.refreshMax(g8max);
              g8.refreshMin(g8min);
          }
          if(Math.abs(monthscore)<(g8max/4)&&g8max>limitsentiment){
              g8max=(g8max/2<limitsentiment)?limitsentiment:g8max/2;
              g8min=-g8max;
              g8.refreshMax(g8max);
              g8.refreshMin(g8min);
          }
          g8.refresh(Math.round(monthscore*100)/100);
          
      }
      
	var interval = 1000;
	var day = 0;
	var hour = 0;
	var minute = 0;
	var month = 0;
	var strJSON = "";
	var obj = null;
	var url = "GetTweetsServlet?request=speed";
	var version = "";
    var message="";
    var messagedetail=new Array();
    var negmessage="";
    var negmessagedetail=new Array();
    var usermessage="";
    var userdetail=new Array();
    var refresh=0;
    var strSpeed="";
    var monthscore;
    var dayscore;
    var minutescore;
    var hourscore;
    function drawSpeed(divname){
    	var div = document.getElementById(divname);
        div.innerHTML = strSpeed;
    }
	function getJson() {
		$.ajax({
			url : "GetTweetsServlet?request=speed",
			type : "POST",
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			data : "{}",
			success : function(json) {
				month = json.month;
				if (month<0){
					month=0;
				}
				day = json.day;
				if (day<0){
					day=0;
                }
				minute = json.minute;
				if (minute<0){
					minute=0;
                }
				hour = json.hour;
				if (hour<0){
					hour=0;
                }
				monthscore=json.monthSentiment;
                dayscore=json.daySentiment;
                minutescore=json.minuteSentiment;
                hourscore=json.hourSentiment;

			},
			error : function(x, e) {
				month = 0;
				day = 0;
				minute = 0;
				hour = 0;
				monthscore=0;
			    dayscore=0;
			    minutescore=0;
			    hourscore=0;
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
	function getNegTop10(i) {
        $.ajax({
            url : "GetTweetsServlet?request=negtop10&id="+i,
            type : "POST",
            dataType : "json",
            contentType : "application/json; charset=utf-8",
            data : "",
            success : function(json) {
                negmessage=json.message;
                negmessagedetail[i]=negmessage;
            },
            error : function(x, e) {
            },
            complete : function(x) {
            }
        });
    }
	function getUser(i) {
        $.ajax({
            url : "GetTweetsServlet?request=hotUser&id="+i,
            type : "POST",
            dataType : "json",
            contentType : "application/json; charset=utf-8",
            data : "",
            success : function(json) {
            	usermessage=json.user;
            	userdetail[i]=usermessage;
            },
            error : function(x, e) {
            },
            complete : function(x) {
            }
        });
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
			cc.innerHTML=(i+1)+'. '+messagedetail[i];
		}
	}
	function ShowNegTop10(divname,i){
		var cc = document.getElementById(divname);
        
            cc.innerHTML=(i+1)+'. '+negmessagedetail[i];
        
	}
    function showUser(divname,i) {
        
        var cc = document.getElementById(divname);        
            cc.innerHTML=(i+1)+'. '+userdetail[i];
        
    }

	window.setInterval(function() {
		display();
	       

	}, interval);
</script>
<meta name="viewport"
	content="width=device-width,inital-scale=1, user-scalable=no">
</head>
<body>
<% boolean slider=true;
boolean graph=true;
boolean listlayout=true;
    if (request.getParameter("slider")!=null){
    if(request.getParameter("slider").equals("off"))
        slider=false;
        }
    if (request.getParameter("graph")!=null){
        if(request.getParameter("graph").equals("off"))
            graph=false;
            }
    if (request.getParameter("listlayout")!=null){
        if(request.getParameter("listlayout").equals("off"))
            listlayout=false;
            }
    if (!slider){ %>
 <script type="text/javascript">
 function display() {
     getJson();
     getTweets();
     for(var i=0;i<10;i++){
         getTop10(i);
         getNegTop10(i);
         ShowTop10("news"+i,i);
         ShowNegTop10("negnews"+i,i);
         getUser(i);
         showUser("user"+i,i);
     }
     ShowTop10('version',1);
     setPic();
 }</script>
 <%}else{ %>
 <script type="text/javascript">
 function display() {
     getJson();
     getTweets();
     for(var i=0;i<10;i++){
         getTop10(i);
         getNegTop10(i);
         ShowTop10("news"+i,i);
         ShowNegTop10("negnews"+i,i);
         getUser(i);
         showUser("user"+i,i);
     }
     ShowCount('Minute');
     ShowCount('Month');
     ShowCount('Day');
     ShowCount('Hour');
     ShowTop10('version',1);
     setPic();
 }</script>
 <%} %>
	<div data-role="page" data-theme="a">
		<div data-role="header" data-position="fixed">
			<h1>Tweets DashBoard</h1>
			<a href="#page2" data-icon="gear" data-rel="dialog"
			data-transition="pop">settings</a>
		</div>
		<div data-role="content">
		<%if(graph){ %>
			<section class="ui-grid-d" data-divider-theme="b">
			<div class="ui-block-a" align="center"  >
			     <div id="g4"></div>
			     <% if(slider){%>
				<div id="Month">loading...</div>
				<%}%>
			</div>
			<div class="ui-block-b" align="center">
			     <div id="g3"></div>
			     <% if(slider){%>
				<div id="Day">loading...</div>
				<%}%>
			</div>
			<div class="ui-block-c" align="center">
			     <div id="g2"></div>
			     <% if(slider){%>
				 <div id="Hour">loading...</div>
				 <%}%>
			</div>
			<div class="ui-block-d" align="center">
			     <div id="g1"></div>
			     <% if(slider){%>
				 <div id="Minute">loading...</div>
				 <%}%>
			</div>
			<div class="ui-block-a" align="center"  >
                 <div id="g8"></div>
                 <% if(slider){%>
                <div id="MonthScore">Month Sentiment</div>
                <%}%>
            </div>
            <div class="ui-block-b" align="center">
                 <div id="g7"></div>
                 <% if(slider){%>
                <div id="DaySocre">Day Sentiment</div>
                <%}%>
            </div>
            <div class="ui-block-c" align="center">
                 <div id="g6"></div>
                 <% if(slider){%>
                 <div id="HourSocre">Hour Sentiment</div>
                 <%}%>
            </div>
            <div class="ui-block-d" align="center">
                 <div id="g5"></div>
                 <% if(slider){%>
                 <div id="MinuteSocre">Minute Sentiment</div>
                 <%}%>
            </div>
			</section>
			<%}else{%>
			<section class="ui-grid-a" data-divider-theme="b">
            <div class="ui-block-a" align="center"  >
                 <div id="g4"></div>
                 <% if(slider){%>
                <div id="Month">loading...</div>
                <%}%>
            </div>
            <div class="ui-block-b" align="center"  >
                 <div id="g8"></div>
                 <% if(slider){%>
                <div id="MonthScore">Month Sentiment</div>
                <%}%>
            </div>
            <div class="ui-block-a" align="center">
                 <div id="g3"></div>
                 <% if(slider){%>
                <div id="Day">loading...</div>
                <%}%>
            </div>
            <div class="ui-block-b" align="center">
                 <div id="g7"></div>
                 <% if(slider){%>
                <div id="DaySocre">Day Sentiment</div>
                <%}%>
            </div>
            <div class="ui-block-a" align="center">
                 <div id="g2"></div>
                 <% if(slider){%>
                 <div id="Hour">loading...</div>
                 <%}%>
            </div>
            <div class="ui-block-b" align="center">
                 <div id="g6"></div>
                 <% if(slider){%>
                 <div id="HourSocre">Hour Sentiment</div>
                 <%}%>
            </div>
            <div class="ui-block-a" align="center">
                 <div id="g1"></div>
                 <% if(slider){%>
                 <div id="Minute">loading...</div>
                 <%}%>
            </div>
            
            
            
            <div class="ui-block-b" align="center">
                 <div id="g5"></div>
                 <% if(slider){%>
                 <div id="MinuteSocre">Minute Sentiment</div>
                 <%}%>
            </div>
            </section>
			<%}
			if(listlayout){%>
			<section class="ui-grid-a" data-divider-theme="b">
			<div class="ui-block-a" align="center"  >
			<ul data-role="listview" data-inset="true" data-diider-theme="d"
				data-filter="false" data-theme="b" style="margin-right:1em;"
				data-split-theme="d">
				<li>Top Positive 10 Tweets
					<span class="ui-li-count" id=version>Loading..</span>
				</li>
				<%
				    for (int i = 0; i < 10; i++) {
				%>
				<li data-role="fieldcontain">
				    <a href="GetTweetsServlet?request=tweets&id=<%=i%>" style="max-width:85%">
					   <div id=news<%=i%> >loading</div></a>
					   <a href="GetTweetsServlet?request=tweets&id=<%=i%>"></a>
					   </li>
				<%
				    }
				%>
			</ul>
			</div>
			<div class="ui-block-b" align="center" >
			<ul data-role="listview" data-inset="true" data-diider-theme="d"
                data-filter="false" data-theme="e">
                <li>Top Negative 10 Tweets
                </li>
                <%
                    for (int i = 0; i < 10; i++) {
                %>
                <li><a href="GetTweetsServlet?request=negtweets&id=<%=i%>" style="max-width:85%">
                        <div id=negnews<%=i%>>loading</div></a>
                        <a href="GetTweetsServlet?request=negtweets&id=<%=i%>"></a>
                       </li>
                <%
                    }
                %>
            </ul>
            </div>
            </section>
            <%}else{%>
            
            <div align="center"  >
            <ul data-role="listview" data-inset="true" data-diider-theme="d"
                data-filter="false" data-theme="b"
                data-split-theme="d">
                <li>Top Positive 10 Tweets
                    <span class="ui-li-count" id=version>Loading..</span>
                </li>
                <%
                    for (int i = 0; i < 10; i++) {
                %>
                <li data-role="fieldcontain">
                    <a href="GetTweetsServlet?request=tweets&id=<%=i%>" style="max-width:85%">
                       <div id=news<%=i%> >loading</div></a>
                       <a href="GetTweetsServlet?request=tweets&id=<%=i%>"></a>
                       </li>
                <%
                    }
                %>
            </ul>
            </div>
            <div align="center" >
            <ul data-role="listview" data-inset="true" data-diider-theme="d"
                data-filter="false" data-theme="e">
                <li>Top Negative 10 Tweets
                </li>
                <%
                    for (int i = 0; i < 10; i++) {
                %>
                <li><a href="GetTweetsServlet?request=negtweets&id=<%=i%>" style="max-width:85%">
                        <div id=negnews<%=i%>>loading</div></a>
                         <a href="GetTweetsServlet?request=negtweets&id=<%=i%>"></a>
                       </li>
                <%
                    }
                %>
            </ul>
            </div>
            </section>
            <%}%>
			<ul data-role="listview" data-inset="true" data-diider-theme="d">
				<li>Hostest Users</a></li>
				<%
				    for (int i = 0; i < 10; i++) {
				%>
				<li><a href="GetTweetsServlet?request=user&id=<%=i%>">
				    <div id=user<%=i%>>loading</div></a>
				</li>
				<%
				    }
				%>
			</ul>

		</div>

		<div data-role="footer" data-position="fixed">
			<h1>
				DashBoard version 1.0
			</h1>
		</div>
	</div>
    <div data-role="page" id="page2" data-theme="a" data-add-back-btn="true">
    <div data-role="header">
    <h1>Settings</h1>
    </div>
    <div data-role="content">
        <div class="content-primary">
            <form action="index.jsp" method="get" data-ajax="false">
                <ul data-role="listview">
                    <li data-role="fieldcontain">
                        <fieldset data-role="controlgroup" data-type="horizontal">
                            <legend>Graph Layout:</legend>
                            <input type="radio" name="graph" id="radio-choice-c" value="on" checked="checked" />
                            <label for="radio-choice-c">Grid 4*2</label>
                            <input type="radio" name="graph" id="radio-choice-d" value="off" />
                            <label for="radio-choice-d">List 2*4 </label>
                        </fieldset>
                    </li>
                    <li data-role="fieldcontain">
                        <fieldset data-role="controlgroup" data-type="horizontal">
                            <legend>List Layout:</legend>
                            <input type="radio" name="listlayout" id="radio-choice-e" value="on" checked="checked" />
                            <label for="radio-choice-e">vertical</label>
                            <input type="radio" name="listlayout" id="radio-choice-f" value="off" />
                            <label for="radio-choice-f">Horizontal</label>
                        </fieldset>
                    </li>
                    <li data-role="fieldcontain">
                        <label for="slider2">Graph Words:</label>
                            <select name="slider" id="slider2" data-role="slider">
                                <option value="off">Off</option>  
                                <option value="on" selected="selected">On</option>                                 
                        </select>
                    </li>
                    <li data-role="fieldcontain">
                        <fieldset class="ui-grid-a">
                            <div class="ui-block-a"><button type="reset" data-theme="a">Reset</button></div>
                            <div class="ui-block-b"><button type="submit" data-theme="a">Submit</button></div>
                        </fieldset>
                    </li>
                </ul>
            </form>
        </div>
    </div></div>

</body>
</html>