<%-- 
    Document   : setupsch
    Created on : 2016-9-27, 21:56:28
    Author     : hippo
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value='/script/bootstrap/css/bootstrap.css'/>" rel="stylesheet">
<link href="<c:url value='/script/datePicker/css/bootstrap-datepicker.css'/>" rel="stylesheet">
<link href="<c:url value='/script/Date/css/bootstrap-datepaginator.css'/>" rel="stylesheet">
<script type="text/javascript" src="<c:url value='/script/pageUtil.js'/>"></script>
<script src="<c:url value='/script/Jquery/jquery.min.js'/>"></script>
<script src="<c:url value='/script/moment.js'/>"></script>
<script src="<c:url value='/script/datePicker/js/bootstrap-datepicker.js'/>"></script>
<script src="<c:url value='/script/Date/js/bootstrap-datepaginator.js'/>"></script>
<script>
var allPresenters=${allPresenters};
var allProducers=${allProducers};
var currentDate="${currentDate}";
var selectedDate="";
var allPrograms=${allPrograms};
var resultData=null;
var isInsert=${insert};
var originalData=${originalData};
function init(){
    initAllSelect();
    setBtnBind();
    if(!isInsert){
        initContents();
    }else{
        selectedDate=currentDate;
        initDatePicker(currentDate,selectedDate);
    }
   
}

function setBtnBind(){
    $("#submitBtn").bind("click",submitExec);
    $("#clearBtn").bind("click",init);
}
function initAllSelect(){
     $("#presenterSelector").empty();
    for(var index in allPresenters){
        var presenter=allPresenters[index];
        $("#presenterSelector").append("<option value='"+presenter.id+"'>"+presenter.name+"</option>");
    }
    $("#producerSelector").empty();
    for(var index in allProducers){
        var producer=allProducers[index];
        $("#producerSelector").append("<option value='"+producer.id+"'>"+producer.name+"</option>");
    }
    $("#startHour").empty();
    $("#durationHour").empty();
    for(var h=0;h<25;++h){
        var hourString="";
        if(h<10){
            hourString="0"+h;
        }else{
            hourString=h+"";
        }
        $("#startHour").append("<option value='"+hourString+"'>"+hourString+"</option>");
        $("#durationHour").append("<option value='"+hourString+"'>"+hourString+"</option>");
    }
    $("#programSelector").empty();
     for(var index in allPrograms){
        var program=allPrograms[index];
        $("#programSelector").append("<option value='"+program+"'>"+program+"</option>");
    }
    initMS("startMinute");
   // initMS("startSecond");
    initMS("durationMinute");
    //initMS("durationSecond");
    
}

function initMS(selector){
    $("#"+selector).empty();
    $("#"+selector).append("<option value='00'>00</option>");
    $("#"+selector).append("<option value='30'>30</option>");
    /*
    for(var n=0;n<24;++n){
        var number=n*30;
        var numString="";
        if(n<10){
            numString="0"+n;
        }else{
            numString=n+"";
        }
        $("#"+selector).append("<option value='"+numString+"'>"+numString+"</option>");
       
    }*/
}

function initContents(){
    initDatePicker(currentDate,originalData.dateOfProgram);
    selectedDate=originalData.dateOfProgram;
    $("#presenterSelector").val(originalData.presenter);
    $("#producerSelector").val(originalData.producer);
    $("#programSelector").val(originalData.programName);
    var startTime=new String(originalData.startTime);
    var startTimeList=startTime.split(":");
    $("#startHour").val(startTimeList[0]);
    $("#startMinute").val(startTimeList[1]);
    var duration=new String(originalData.duration);
    var durationList=duration.split(":");
    $("#durationHour").val(durationList[0]);
    $("#durationMinute").val(durationList[1]);
    
}

function initDatePicker(startDateStr,selectedDateStr){
    var options = {
        startDate:startDateStr,
        startDateFormat:'YYYY-MM-DD',
        selectedDate: selectedDateStr,
        selectedDateFormat:  'YYYY-MM-DD',
         onSelectedDateChanged: function(event, date) {
        // Your logic goes here
        selectedDate=date._i;
    }
    };
    $('#datePicker').datepaginator(options);
}
function submitExec(){
    var startTime=getVal("startHour")+":"+getVal("startMinute")+":00";
    var duration=getVal("durationHour")+":"+getVal("durationMinute")+":00";
    var program=getVal("programSelector");
    var presenter=getVal("presenterSelector");
    var producer=getVal("producerSelector");
    var jsonObj={};
    jsonObj.startTime=startTime;
    jsonObj.dateOfProgram=selectedDate;
    jsonObj.duration=duration;
    jsonObj.programName=program;
    jsonObj.presenter=presenter;
    jsonObj.producer=producer;
    if(!isInsert){
        jsonObj.id=originalData.id;
    }
   // jsonObj.isInsert=isInsert;
    var jsonStr=json2String(jsonObj);
    setFrame(jsonStr,isInsert);
}

function setFrame(jsonStr,isInsert){
    var url="<%=request.getContextPath()%>/nocturne/enterschedule?isInsert="+isInsert+"&jsonStr="+jsonStr;
    console.info(url);
    $("#frame").empty();
    $("#frame").append("<iframe src='"+url+"'></iframe>")
}
$(init);
</script>
<style>
#label{
    margin-left: 20px;
    font-size: large;
    margin-top: 10px;
}
#buttonContainer{
    width: 140px;
    margin-left: auto;
    margin-right: auto;
    margin-top: 20px;
    margin-bottom: 10px;
}
</style>
<title>JSP Page</title>
</head>
<body>
    <div id="mainContainer">
        <span id='label'>Select Date:</span>
        <div id="dateGroup" style="width: 1130px;">
            <div id="datePicker"></div>
        </div>
          <hr>
        <span id='label'>Select User:</span>
        <div id="userGroup" style="margin-left: 20px;margin-top: 10px;">
            <span>Presenter:</span><select id="presenterSelector"></select>
            <span>Producer:</span><select id="producerSelector"></select>
        </div>
          <hr>
        <span id='label'>Select Program:</span>
        <div id="programGroup" style="margin-left: 20px;margin-top: 10px;">
            <span>Program:</span><select id="programSelector"></select>
        </div>
              <hr>
        <div id="timeGroup">
            <span id='label'>Start Time:</span>
            <div id="startTimeSelectGroup" style="margin-left: 20px;margin-top: 10px;">
                <span>Hour:</span><select id="startHour"><option>24</option></select>
                <span>Minute:</span><select id="startMinute"><option>24</option></select>
            </div>
              <hr>
             <span id='label'>Duration:</span>
            <div id="DurationSelectGroup" style="margin-left: 20px;margin-top: 10px;">
                <span>Hour:</span><select id="durationHour"><option>59</option></select>
                <span>Minute:</span><select id="durationMinute"><option>59</option></select>
            </div>
            
        </div>
    </div>
      <hr>
    <div id="buttonContainer">
        <button id="submitBtn" type="button" class="btn btn-primary">Submit</button>
        <button id="clearBtn" type="button" class="btn btn-default">Reset</button>
    </div>
      <div id="frame" style="display: none">
          
      </div>
</body>
</html>
