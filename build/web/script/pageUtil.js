DEBUG_MODE=true;



var pageUtil={};
	pageUtil.actionName=null;
	pageUtil.url=null;
	
var ReturnData={};
	ReturnData.success="SUCCESS";
	ReturnData.failure="FAILURE";
	ReturnData.error="ERROR";

var console=window.console;
if(typeof(window.console)=="undefined"||!window.console||window.console==null||!DEBUG_MODE){
	console=disableDebugInfo();
}

//禁用所有调试信息
function disableDebugInfo(){
		console={};
		console.debug = function(){};
		console.info = function(){};
		console.warn = function(){};
		console.error = function(){};
		console.assert = function(){};
		console.dir = function(){};
		console.dirxml = function(){};
		console.trace = function(){};
		console.group = function(){};
		console.groupEnd = function(){};
		console.time = function(){};
		console.timeEnd = function(){};
		console.profile = function(){};
		console.profileEnd = function(){};
		console.count = function(){};
		return console;
		
	}


//将变量值写入控制台调试
function debug(info){
	if(!DEBUG_MODE){
		return;
	}
	if(typeof(window.console)=="undefined"||!window.console||window.console==null){
		console=disableDebugInfo();
	}
	if(info==null||!info||typeof(info)=="undefined"){
		error("Debug info is null or undefined!");
		return;
	}
	if(typeof(info)=="string"||typeof(info)=="number"||typeof(info)=="boolean"){
		console.info(info);
	}else{
		console.dir(info);
	}
	
}
//输出错误信息
function error(info){
	if(!DEBUG_MODE){
		return;
	}
	if(typeof(window.console)=="undefined"||!window.console||window.console==null){
		console=disableDebugInfo();
	}
	if(info==null||!info||typeof(info)=="undefined"){
		console.error("ERROR info is null or undefined!");
		return;
	}
	if(typeof(info)=="string"||typeof(info)=="number"||typeof(info)=="boolean"){
		console.error(info);
	}else{
		console.dir(info);
	}
}

//拼接后台地址字符串
function makeActionString(functionName,postParam,actionName){
	if(!checkJquery()){
		return;
	}
	if(pageUtil.url==null||pageUtil.actionName==null){
		return null;
	}
	var url=null;
	if(!actionName||actionName==null||typeof(actionName)=="undefined"||actionName==""){
		url=pageUtil.url+"/"+pageUtil.actionName+"/";
	}else{
		url=pageUtil.url+"/"+actionName+"/";
	}
	
	if(typeof(postParam)=="undefined"||!postParam){
		return url+functionName+".action";
	}else{
		if(typeof(postParam)=="string"){
			return url+functionName+".action?"+postParam;
		}
		
		if(typeof(postParam)=="object"){
			var actionString=url+functionName+".action?";
			var index=0;
			for(var key in postParam){
				if(index!=0){
					actionString+="&";
				}
				var value=postParam[key];
				actionString+=key+"="+value;
				++index;
			}
			return actionString;
		}
		error("Param Error While making Action String");
		
		return null;
	}
	
}

//与后台进行数据交互（异步）
function asyncDataFromBack(callback,functionName,param,actionName){
	if(!checkJquery()){
		return;
	}
	if(functionName==null||functionName==""){

		error("function name is null");
		return ReturnData.error;
	}
	if(typeof(param)=="undefined"||!param||param==null){
		param={};
	}
	if(!actionName||actionName==null||typeof(actionName)=="undefined"||actionName==""){
		actionName=null;
	}
	var actionString=null;
	actionString=makeActionString(functionName,null,actionName);
	debug(actionString);
	if(actionString==null){
		error("the Action String is null");
		return ReturnData.error;
	}
	if(!isVarValid(callback)){
		callback=function(data){
			if(isVarValid(data)){
				debug(data);
			}
		};
	}
	$.ajax({
		type:"POST",
		async:true,  //默认true,异步
		data:param,
		url:actionString,
		success:function(data){ 		
			callback(data);
			debug(data);
		},
	    error:function(data){
	    	error("async Ajax Failure:");
	    	debug(data);
	    	callback(ReturnData.failure); 
	    }
	});
}
//与后台进行数据交互（同步）
function getDataFromBack(functionName,param,actionName){
	if(!checkJquery()){
		return;
	}
	if(functionName==null||functionName==""){

		error("function name is null");
		return "Error";
	}
	if(typeof(param)=="undefined"||!param||param==null){
		param={};
	}
	if(!actionName||actionName==null||typeof(actionName)=="undefined"||actionName==""){
		actionName=null;
	}
	var actionString=null;
	actionString=makeActionString(functionName,null,actionName);
	debug(actionString);
	if(actionString==null){
		error("the Action String is null");
		return "Error";
	}
	var returnData=null;
	$.ajax({
		type:"POST",
		async:false,  //默认true,异步
		data:param,
		url:actionString,
		success:function(data){ 		
			returnData=data;
		},
	    error:function(data){
	    	error("Ajax Failure:");
	    	debug(data);
	    	returnData=ReturnData.failure; 
	    }
	});
	return returnData;
}

//将jsonString转化为JSON对象
function parseJson(jsonString){
	if(!checkJquery()){
		return ReturnData.failure;
	}
	if(jsonString==null||typeof(jsonString)=="undefined"||!jsonString){
		error("jsonString is null!Parse String2Json failed");
		return ReturnData.error;
	}
	if(typeof(jsonString)=="object"){
		return jsonString;
	}
	if(typeof(jsonString)=="string"){
		return $.parseJSON(jsonString);
	}
	
	error("type undefined, Parse String2Json failed");
	return ReturnData.failure;
}

function json2String(obj) {
	var THIS = this;
	if(!isVarValid(obj)){
		error("The obj to be stringfied is null or undefined");
		return null;
	}
	switch (typeof (obj)) {
		case 'string':
			return '"' + obj.replace(/(["\\])/g, '\\$1') + '"';
		case 'array':
			return '[' + obj.map(THIS.json2String).join(',') + ']';
		case 'object':
			if (obj instanceof Array) {
				var strArr = [];
	
				var len = obj.length;
	
				for ( var i = 0; i < len; i++) {
					strArr.push(THIS.json2String(obj[i]));
				}
	
				return '[' + strArr.join(',') + ']';
			} else if (obj == null) {
	
				return 'null';
			} else {
	
				var string = [];
	
				for ( var property in obj)
					string.push(THIS.json2String(property) + ':'
							+ THIS.json2String(obj[property]));
	
				return '{' + string.join(',') + '}';
			}
		case 'number':
			return obj;
		default:
			return obj;
	}
}

// 创建一个超链接标签
function mkATag(href,id,className,content,style){
	var tagObj={};
	if(!isVarValid(href)){
		return "";
	}
	tagObj.tagName="a";
	if(isVarValid(id)){
		tagObj.id=id;
	}
	if(isVarValid(className)){
		tagObj.className=className;
	}
	if(isVarValid(content)){
		tagObj.content=content;
	}
	if(isVarValid(style)){
		tagObj.style=style;
	}
	return makeHTMLTagByObj(tagObj);
}

function mkDIVTag(id,className,content,style){
	return mkSimplHTMLTag("div",id,className,content,style);
	
}
//生成一个简单的HTML标签块
function mkSimplHTMLTag(tagName,id,className,content,style){
	var tagObj={};
	
	if(!isVarValid(tagName)){
		error("HTML Tag Name Is Null!");
		return "";	
	}
	tagObj.tagName=tagName;
		
	if(isVarValid(className)){
		tagObj.className=className;
	}
	
	if(isVarValid(id)){
		tagObj.id=id;
	}
	
	if(isVarValid(content)){
		tagObj.content=content;
	}
	if(isVarValid(style)){
		tagObj.style=style;
	}
	return makeHTMLTagByObj(tagObj);
}


function makeStyleString(styleObj){
	var styleString="";
	if(!isVarValid(styleObj)){
		error("the styleObj is null!");
		return;
	}
	if(typeof(styleObj)=="string"){
		return styleObj;
	}
	if(typeof(styleObj)=="object"){
		for(var key in styleObj){
			styleString=key+":"+styleObj[key]+";";
		}
	}
	
	return styleString;
}
//根据传入的对象来构建HTML标签
function makeHTMLTagByObj(tagObj){
	var tagString="";
	if(!isVarValid(tagObj)){
		error("the TagObj isNull!");
		return tagString;
	}
	if(typeof(tagObj)=="string"){
		debug("The tag Attribute is Empty?");
		tagString="<"+tagObj+"></"+tagObj+">";
		return tagString;
	}
	if(typeof(tagObj)=="object"){
		if(!isVarValid(tagObj.tagName)){
			error("The tagObj do not have a tag name!");
			return tagString;
		}else{
			tagString="<"+tagObj.tagName;
			for(var key in tagObj){
				if(key=="tagName"||key=="content"){
					continue;
				}
				if(key=="className"){
					tagString=tagString+" class='"+tagObj[key]+"'";
					continue;
				}
				if(key=="style"){
					tagString=tagString+" style='"+makeStyleString(tagObj[key])+"'";
					continue;
				}
				tagString=tagString+" "+key+"='"+tagObj[key]+"'";
			}
			tagString+=">";
			if(isVarValid(tagObj.content)){
				tagString+=tagObj.content;
			}
			tagString+="</"+tagObj.tagName+">";
			debug(tagString);
			return tagString;
		}
	}
	return tagString;
}

//检查变量合法性
function isVarValid(param){
	if(typeof(param)=="undefined"||!param||param==null){
		return false;
	}else{
		return true;
	}
}
//检查JQuery是否可用
function checkJquery(){
	if(typeof(jQuery)=="undefined"||jQuery==null||!jQuery){
		error("JQUERY ERROR!");
		return false;
	}else{
		return true;
	}
}

function getTimeString(){
	var myDate=new Date(); //得到时间对象

	var y=myDate.getFullYear(); //获取年
	var m=myDate.getMonth()+1; //获取月

	m=m>9?m:"0"+m; //如果月份小于10,则在前面加0补充为两位数字

	var d=myDate.getDate(); //获取日

	//d=d>9?d:"0"+d; //如果天数小于10,则在前面加0补充为两位数字

	var h=myDate.getHours(); //获取小时

	h=h>9?h:"0"+h; //如果小时数字小于10,则在前面加0补充为两位数字

	var M=myDate.getMinutes(); //获取分

	M=M>9?M:"0"+M; //如果分钟小于10,则在前面加0补充为两位数字

	var s=myDate.getSeconds(); //获取秒

	s=s>9?s:"0"+s; //如果秒数小于10,则在前面加0补充为两位数字

	var NowTime=y+"年"+m+"月"+d+"日"+" "+h+"点"+M+" 分"+s+" 秒"; 
	return NowTime;
}

function copyClipBoard(copyText) { 

	try{
		if (window.clipboardData) {
			window.clipboardData.setData("Text", copyText);
		} else {
			var flashcopier = 'flashcopier';
			if (!document.getElementById(flashcopier)) {
				var divholder = document.createElement('div');
				divholder.id = flashcopier;
				document.body.appendChild(divholder);
			}
			document.getElementById(flashcopier).innerHTML = '';
			var divinfo = "<embed src="+pageUtil.url+"/js/_clipboard.swf' FlashVars='clipboard="
					+ encodeURIComponent(copyText)
					+ '" width="0" height="0" type="application/x-shockwave-flash"></embed>';
			document.getElementById(flashcopier).innerHTML = divinfo;
		}
		return ReturnData.success;
	}catch(e){
		debug(e);
		return ReturnData.failure;
	}
	
} 



function getVal(selector){
    return $("#"+selector).val();
}

function setVal(selector,value){
    $("#"+selector).val(value);
}

function setSelectedText(selector,text){
     $("#"+selector+" option[text='"+text+"']").attr("selected", true)
}

function getSelectedText(selector){
    return $("#"+selector).find("option:selected").text();
}


