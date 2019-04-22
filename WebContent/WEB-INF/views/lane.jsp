<%@page import="cn.emg.poibatcheditor.common.POIMainAttrnameEnum"%>
<%@page import="cn.emg.poibatcheditor.common.POITagAttrnameEnum"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>车道查询</title>
<meta charset="UTF-8" />
<meta name="robots" content="none">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" CONTENT="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<link href="resources/jquery-ui-1.12.1.custom/jquery-ui.min.css" rel="stylesheet">
<link href="resources/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet" />
<link href="resources/bootstrap-table-1.11.1/bootstrap-table.min.css" rel="stylesheet">
<link href="resources/css/css.css" rel="stylesheet" />

<script src="resources/jquery/jquery-3.2.1.min.js"></script>
<script src="resources/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
<script src="resources/js/zealot.js"></script>
<script src="resources/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<script src="resources/bootstrap-table-1.11.1/bootstrap-table.min.js"></script>
<script src="resources/bootstrap-table-1.11.1/locale/bootstrap-table-zh-CN.js"></script>

<%
	StringBuilder poiTagAttrnameMap = new StringBuilder("({");
	StringBuilder poiTagAttrnameKeys = new StringBuilder();
	for (POITagAttrnameEnum item : POITagAttrnameEnum.values()) {
		if (item.equals(POITagAttrnameEnum.unkown))
			continue;
		
		poiTagAttrnameMap.append("'");
		poiTagAttrnameMap.append(item.getValue().trim());
		poiTagAttrnameMap.append("':'");
		poiTagAttrnameMap.append(item.getDes().trim());
		poiTagAttrnameMap.append("',");
		
		poiTagAttrnameKeys.append(item.getValue().trim());
		poiTagAttrnameKeys.append(",");
	}
	
	poiTagAttrnameMap.deleteCharAt(poiTagAttrnameMap.length() - 1);
	poiTagAttrnameKeys.deleteCharAt(poiTagAttrnameKeys.length() - 1);
	poiTagAttrnameMap.append("})");
%>

<script type="text/javascript">
	var allTagColumns = "<%=poiTagAttrnameKeys.toString() %>".split(',');
	var poiTagAttrnameMap = eval("<%=poiTagAttrnameMap.toString() %>");
	var defaultColumns = allTagColumns;
	
	var columns = window.localStorage.POIBATCHEDITOR_columns;
	if(!columns) {
		columns = defaultColumns;
		window.localStorage.POIBATCHEDITOR_columns = columns;
	} else {
		columns = columns.split(',');
	}
	
	function refreshColumns() {
		$(".thColumnsOn").remove();
		columns.forEach(function(column){
			var html = new Array();
			html.push('<th class="thColumnsOn" data-field="' + column + '">' + poiTagAttrnameMap[column] + '</th>');
			var obj = $("#poilist>thead>tr:eq(0)");
			$(obj).append(html.join(''));
		});
	}
	
	function addColumn(column) {
		$("#divcolumn_" + column).remove();
		var html = new Array();
		html.push('<div class="input-group columnOn" id="divcolumn_' + column + '" onclick="removeColumn(\'' + column + '\');">');
		html.push('   <span class="input-group-addon">' + poiTagAttrnameMap[column] + '</span><span class="input-group-addon">');
		html.push('       <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>');
		html.push('   </span>');
		html.push(' </div>');
		
		$("#divcolumnson").append(html.join(''));
	}
	
	function removeColumn(column) {
		$("#divcolumn_" + column).remove();
		var html = new Array();
		html.push('<div class="input-group columnOff" id="divcolumn_' + column + '" onclick="addColumn(\'' + column + '\');">');
		html.push('   <span class="input-group-addon">' + poiTagAttrnameMap[column] + '</span><span class="input-group-addon">');
		html.push('       <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>');
		html.push('   </span>');
		html.push(' </div>');
		
		$("#divcolumnsoff").append(html.join(''));
	}
	
	function saveColumns() {
		var col = new Array();
		$(".columnOn").each(function(index, ele) {
			var id = $(ele).attr("id");
			var column = id.replace(/divcolumn_/, "");
			col.push(column);
		});
		columns = col;
		window.localStorage.POIBATCHEDITOR_columns = columns;
		$("input#columns").val(columns.join(","));
		
		$('#poilist').bootstrapTable("destroy");
		refreshColumns();
		$('#poilist').bootstrapTable({locale : 'zh-CN'});
		
		getPOIs();
	}
	
	$(document).ready(function() {
		$.zealot.getHead();
		
		refreshColumns();
		
		$('[data-toggle="pois"]').bootstrapTable({
			locale : 'zh-CN'
		});
		
		$('#columnConfigModel').on('show.bs.modal',function() {
			$("#divcolumnson").empty();
			$("#divcolumnsoff").empty();
			
			_allTagColumns = allTagColumns.concat();
			
			columns.forEach(function(column){
				var html = new Array();
				html.push('<div class="input-group columnOn" id="divcolumn_' + column + '" onclick="removeColumn(\'' + column + '\');">');
				html.push('   <span class="input-group-addon">' + poiTagAttrnameMap[column] + '</span><span class="input-group-addon">');
				html.push('       <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>');
				html.push('   </span>');
				html.push(' </div>');
				
				$("#divcolumnson").append(html.join(''));
				
				_allTagColumns.splice($.inArray(column, _allTagColumns), 1);
			});
			
			_allTagColumns.forEach(function(column){
				var html = new Array();
				html.push('<div class="input-group columnOff" id="divcolumn_' + column + '" onclick="addColumn(\'' + column + '\');">');
				html.push('   <span class="input-group-addon">' + poiTagAttrnameMap[column] + '</span><span class="input-group-addon">');
				html.push('       <span class="glyphicon glyphicon-plus" aria-hidden="true"></span>');
				html.push('   </span>');
				html.push(' </div>');
				
				$("#divcolumnsoff").append(html.join(''));
			});
		});
		
		$('#searchModel').on('show.bs.modal',function() {
			var myField = document.getElementById("codeTextArea");
			myField.focus();
		});
		
		$("input#columns").val(columns.join(","));
		
		$("#codeTextArea").blur(function(){
			var code = $("#codeTextArea").val();
			$("input#code").val(code);
		});
		$("#pwd").blur(function(){
			var pwd = $("#pwd").val();
			$("input#password").val(pwd);
		});
	});
	
	function codeempty(){
		var myField = document.getElementById("codeTextArea");
		myField.value = '';
		myField.focus();
	}
	
	function codein(str){
		str = str + " ";
		var myField = document.getElementById("codeTextArea");
		if (document.selection) {
			myField.focus();
			sel = document.selection.createRange();
			sel.text = str;
			sel.select();
		} else if (myField.selectionStart || myField.selectionStart == '0') {
			var startPos = myField.selectionStart;
			var endPos = myField.selectionEnd;
			var restoreTop = myField.scrollTop;
			myField.value = myField.value.substring(0, startPos) + str + myField.value.substring(endPos, myField.value.length);
			if (restoreTop > 0) {
			  	myField.scrollTop = restoreTop;
			}
			myField.focus();
			myField.selectionStart = startPos + str.length;
			myField.selectionEnd = startPos + str.length;
		} else {
		  	myField.value += str;
		  	myField.focus();
		}
	}
	
	function getPOIs() {
		var myField = document.getElementById("codeTextArea");
		var code = myField.value;
		
		if (!code) {
			$.zealot.showMsgLabel("alert", "请配置查询条件");
			myField.focus();
			return;
		}
		
		$.zealot.showMsgBox("info", "查询中...");
		$('[data-toggle="pois"]').bootstrapTable("showLoading");
		
		var params = {};
		params["columns"] = columns.join(",");
		params["code"] = code;

		jQuery.post("./lane.web", {
			"action" : "getLanes",
			"params" : JSON.stringify(params)
		}, function(json) {
			if (json.result && json.result > 0) {
				$('[data-toggle="pois"]').bootstrapTable("hideLoading");
				$('[data-toggle="pois"]').bootstrapTable("load", json.rows);
			} else {
				$.zealot.showMsgLabel("alert", json.resultMsg);
			}
			$.zealot.showMsgBox("close");
		}, "json");
	}
	
	function check() {
		var myField = document.getElementById("codeTextArea");
		var code = myField.value;
		
		if (!code) {
			$.zealot.showMsgLabel("alert", "请配置查询条件");
			myField.focus();
			return false;
		}
		
		return true;
	}
	
</script>
</head>
<body>
	<div class="container">
		<div id="headdiv"></div>
		<div class="row" style="margin-top: 60px;">
			<table id="poilist"
				data-pagination="true"
				data-page-list="All"
				data-page-size="All"
				data-toggle="pois"
				data-height="720"
				data-align='center'>
				<thead>
					<tr>
						<th data-field="oid">POI永久ID
							<button type="button" class="btn btn-default btn-xs" id="btSearchModel" data-toggle="modal" title="查询" data-target="#searchModel">
							    <span class="glyphicon glyphicon-search"></span>
							</button>
							<button type="button" class="btn btn-default btn-xs" id="btExportModel" data-toggle="modal" title="导出" data-target="#exportModel">
							    <span class="glyphicon glyphicon-floppy-save"></span>
							</button>
							<button type="button" class="btn btn-default btn-xs" id="btColumnConfigModel" data-toggle="modal" title="编辑展示列" data-target="#columnConfigModel">
							    <span class="glyphicon glyphicon-cog"></span>
							</button>
						</th>
						<th data-field="namec">中文正式名称</th>
						<th data-field="confirm_uid">编辑人</th>
						<th data-field="manualcheck_uid">校正人</th>
						<th data-field="confirm">confirm</th>
						<th data-field="autocheck">autocheck</th>
						<th data-field="manualcheck">manualcheck</th>
						<th data-field="optype">optype</th>
						<th data-field="updatetime">updatetime</th>
						<th data-field="projectid">projectid</th>
						<th data-field="owner">所属行政区划代码</th>
						<th data-field="isdel">isdel</th>
						<th data-field="featcode">poi对象类型代码</th>
						<th data-field="sortcode">poi系列代码</th>
						<th data-field="newfeatcode">新poi对象类型代码</th>
						<th data-field="newsortcode">新poi系列代码</th>
						<th data-field="x">x坐标</th>
						<th data-field="y">y坐标</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div class="modal fade" id="searchModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<strong>查询条件配置</strong>
					</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<textarea class="form-control" id="codeTextArea" rows="16" style="resize:none"></textarea>
					</div>
					<div class="btn-group dropup ">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							选择属性 <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<c:set var="poiAttrnameEnums" value="<%=POIMainAttrnameEnum.values()%>"/>
							<c:forEach items="${poiAttrnameEnums }" var="poiAttrnameEnum">
								<c:if test="${!poiAttrnameEnum.equals(POIMainAttrnameEnum.unkown) }">
									<li><a href="#" onclick="codein('${poiAttrnameEnum.getValue() }');">${poiAttrnameEnum.getDes() }</a></li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
					<div class="btn-group dropup ">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							选择逻辑运算符 <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#" onclick="codein('and');">与</a></li>
							<li><a href="#" onclick="codein('or');">或</a></li>
							<li><a href="#" onclick="codein('xor');">异或</a></li>
						</ul>
					</div>
					<div class="btn-group dropup ">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							选择关系运算符 <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#" onclick="codein('=');">等于</a></li>
							<li><a href="#" onclick="codein('!=');">不等于</a></li>
							<li><a href="#" onclick="codein('<');">小于</a></li>
							<li><a href="#" onclick="codein('<=');">小于等于</a></li>
							<li><a href="#" onclick="codein('>');">大于</a></li>
							<li><a href="#" onclick="codein('>=');">大于等于</a></li>
							<li><a href="#" onclick="codein('is null');">为空</a></li>
							<li><a href="#" onclick="codein('is not null');">不为空</a></li>
							<!-- <li><a href="#" onclick="codein('like');">包含</a></li>
							<li><a href="#" onclick="codein('not like');">不包含</a></li> -->
						</ul>
					</div>
					<button type="button" class="btn btn-default" onclick="codein('(');">&nbsp;&nbsp;(&nbsp;&nbsp;</button>
					<button type="button" class="btn btn-default"onclick="codein(')');">&nbsp;&nbsp;)&nbsp;&nbsp;</button>
					<button type="button" class="btn btn-default"onclick="codeempty();">&nbsp;&nbsp;清空&nbsp;&nbsp;</button>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="getPOIs();">查询</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade bs-example-modal-lg" id="columnConfigModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<strong>编辑展示列</strong>
					</h4>
				</div>
				<div class="modal-body">
					<div id="divcolumnson" style="display: table-cell;"></div>
					<hr style="margin: 6px;">
					<div id="divcolumnsoff" style="display: table-cell;"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="saveColumns();">保存</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="exportModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						<strong>请输入导出密码</strong>
					</h4>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<input class="form-control" type="password" id="pwd">
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal" onclick="$('#exportPOIsForm').submit();">导出</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	<div style="display: none;">
		<form method="post" action="./exportPOIs.web" id="exportPOIsForm" onsubmit="return check();">
		    <input id="columns" name="columns" type="hidden">
			<input id="code" name="code" type="hidden">
			<input id="password" name="password" type="hidden">
		</form>
	</div>
</body>
</html>