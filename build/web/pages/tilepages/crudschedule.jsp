<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<link href="<c:url value='/css/main.css'/>" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="<c:url value='/script/pageUtil.js'/>"></script>
<!-- jQuery -->
<script type="text/javascript" src="<c:url value='/script/table/dependents/jquery/jquery.min.js'/>"></script>
<!-- bootstrap -->
<script type="text/javascript" src="<c:url value='/script/table/dependents/bootstrap/js/bootstrap.min.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/script/table/dependents/bootstrap/css/bootstrap.min.css'/>" />
<!--[if lt IE 9]>
	<script src="<c:url value='/script/table/dependents/bootstrap/plugins/ie/html5shiv.js'/>"></script>
	<script src="<c:url value='/script/table/dependents/bootstrap/plugins/ie/respond.js'/>"></script>
<![endif]-->
<!--[if lt IE 8]>
	<script src="<c:url value='/script/table/dependents/bootstrap/plugins/ie/json2.js'/>"></script>
<![endif]-->
 
<link rel="stylesheet" type="text/css" href="<c:url value='/script/table/dependents/fontAwesome/css/font-awesome.min.css'/>" media="all" />
<!-- DLShouWen Grid -->
<script type="text/javascript" src="<c:url value='/script/table/dlshouwen.grid.js'/>"></script>
<script type="text/javascript" src="<c:url value='/script/table/i18n/en.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/script/table/dlshouwen.grid.css'/>" />
<!-- datePicker -->
<script type="text/javascript" src="<c:url value='/script/table/dependents/datePicker/WdatePicker.js'/>" defer="defer"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/script/table/dependents/datePicker/skin/WdatePicker.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/script/table/dependents/datePicker/skin/default/datepicker.css'/>" />
<script>
var programSlotList=${programSlots};
var allYears=${allYears};
var selectedYear=${selectedYear};
var selectedWeek="${selectedWeek}";
var grid;
$(init);

function init(){
    if(programSlotList!=null){
        initGrid();
    }else{
        $("#gridContainerDiv").append("No Result!");
        $("#gridContainerDiv").css("margin-left","20px");
         $("#gridContainerDiv").css("font-size","large");
    }
    
    initSearchArea();
    $("#searchScheduleBtn").bind("click",searchExec);
    
    $("#createScheduleBtn").bind("click",function(){
        window.location.href="<%=request.getContextPath()%>/nocturne/addEditSchedule";
    })
    $("#copyScheduleBtn").bind("click",function(){
        if($("#copyGroup").css("display")=="none"){
           $("#copyGroup").css("display","block");
        }else{
            $("#copyGroup").css("display","none");
        }
    });
    
    $("#submitCopyScheduleBtn").bind("click",copyScheduleExec);
}

function initSearchArea(){
    for(var index in allYears){
        var yearString=allYears[index];
        $("#yearSelect").append("<option value='"+yearString+"'>"+yearString+"</option>");
    }
    var myDate=new Date(); 
    var currentYear=myDate.getFullYear(); 
    for(var year=currentYear;year<currentYear+100;++year){
        var yearString=year+"";
         $("#yearTo").append("<option value='"+yearString+"'>"+yearString+"</option>");
    }
   
    $("#yearSelect").val(selectedYear);
    $("#weekInputTxt").val(selectedWeek);
}

function dateBeforeCurrentTime(checkDate){
    	var myDate=new Date();

	var y=myDate.getFullYear();
	var m=myDate.getMonth()+1;

	m=m>9?m:"0"+m;

	var d=myDate.getDate();

	d=d>9?d:"0"+d;
        var currentDate="";
        currentDate+=y+"-"+m+"-"+d;
    return currentDate>checkDate;
}
function searchExec(){
   // alert("search");
   var year=$("#yearSelect").val();
   var week=$("#weekInputTxt").val();
   
   window.location.href="<%=request.getContextPath()%>/nocturne/crudSchedule?year="+year+"&week="+week;
}

function modifyExec(id){
    window.location.href="<%=request.getContextPath()%>/nocturne/addEditSchedule?psId="+id;
}
function deleteExec(id){
  var r=confirm("Confirm to delete?")
  if (r==true){
    window.location.href="<%=request.getContextPath()%>/nocturne/deleteSchedule?id="+id;
  }
    
 }
function initGrid(){
     var gridColumns= [
	{id:'dateOfProgram', title:'Date', type:'string', columnClass:'text-center'},
        {id:'programName', title:'Program Name', type:'string', columnClass:'text-center'},
	{id:'duration', title:'Duration', type:'string', columnClass:'text-center'},
	{id:'presenter', title:'Presenter', type:'string', columnClass:'text-center'},
	{id:'producer', title:'Producer', type:'string', columnClass:'text-center'},
        {id:'startTime', title:'Start Time', type:'string', columnClass:'text-center'},
        {id:'operation', title:'Operations', type:'string', columnClass:'text-center', resolution:function(value, record, column, grid, dataNo, columnNo){
		var content = '';
                if(!dateBeforeCurrentTime(record.dateOfProgram)){
                    content += '<button class="btn btn-xs btn-default" onclick="modifyExec('+record.id+');"><i class="fa fa-edit"></i>  Modify</button>';
                    content += '  ';
                }
		content += '<button class="btn btn-xs btn-danger" onclick="deleteExec('+record.id+');"><i class="fa fa-trash-o"></i>  Delete</button>';
		return content;
	}}
    ];
    var gridOption = {
	lang : 'zh-cn',
	ajaxLoad : false,
	exportFileName : 'Program Slots List',
	datas : programSlotList,
	columns : gridColumns,
	gridContainer : 'gridContainerDiv',
	toolbarContainer : 'gridToolBarContainerDiv',
	tools : '',
	pageSize : 10,
	pageSizeLimit : [10, 20, 50]
    };
    grid = $.fn.dlshouwen.grid.init(gridOption);
    grid.load();
}

function copyScheduleExec(){
   var yearFrom=$("#yearSelect").val();
   var weekFrom=$("#weekInputTxt").val();
   var yearTo=$("#yearTo").val();
   var weekTo=$("#weekTo").val();
   window.location.href="<%=request.getContextPath()%>/nocturne/copySchedule?yearFrom="+yearFrom+"&weekFrom="+weekFrom+
                         "&yearTo="+yearTo+"&weekTo="+weekTo;
}
</script>
<style type="text/css">
    #weekInputTxt{
        width:30px;
    }   
      #weekTo{
        width:30px;
    }  
</style>
<fmt:setBundle basename="ApplicationResources" />
<title> <fmt:message key="title.crudrp"/> </title>
    </head>
    <body>
        <h1>Maintain Schedule</h1>
        <div id="operationGroup" style="margin-left: 15px;">
            <span>Operations:</span>
            <div id="createScheduleBtn" class="btn btn-xs btn-default" ><i class="fa fa-plus"></i>CreateSchedule</div>
          
        </div>
        </div>
        <hr>
        <div id="searchGroup" style="margin-left: 15px;">
            <span>Year:</span><select id="yearSelect" class="select"></select>
            <span>Week:</span><input id="weekInputTxt" type="text"/>
            <div id="searchScheduleBtn" class="btn btn-xs btn-default" ><i class="fa fa-search"></i>Search</div>
              <div id="copyScheduleBtn" class="btn btn-xs btn-default" ><i class="fa fa-repeat"></i>CopySchedule</div>
              <div id="copyGroup" style="display:none">
            <span>YearTo:</span><select id="yearTo" class="select"></select>
            <span>WeekTo:</span><input id="weekTo" type="text"/>
            <div id="submitCopyScheduleBtn" class="btn btn-xs btn-default" >Submit</div>
        </div>
         <hr>
        <div id="gridContainerDiv" class="dlshouwen-grid-container"></div>
        <div id="gridToolBarContainerDiv" class="dlshouwen-grid-toolbar-container"></div>
    </body>
</html>
