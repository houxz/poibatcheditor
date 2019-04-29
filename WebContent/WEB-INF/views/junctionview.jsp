<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>路口放大图查询</title>
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
<script
	src="resources/bootstrap-table-1.11.1/extensions/filter-control/bootstrap-table-filter-control.min.js"></script>
<script src="resources/bootstrap-table-1.11.1/locale/bootstrap-table-zh-CN.js"></script>

<script type="text/javascript">
	var admincodes = window.localStorage.POIBATCHEDITOR_admincodes;
	if(!admincodes) {
		admincodes = new Array();
	} else {
		admincodes = admincodes.split(',');
	}

	$(document).ready(function() {
		$.zealot.getHead();
		
		$('[data-toggle="junctionviews"]').bootstrapTable({
			locale : 'zh-CN',
			onPageChange : getJunctionviews
		});
		
		$('[data-toggle="itemAreas"]').bootstrapTable({
			locale : 'zh-CN',
			onCheck : function(row) {
				var adaid = String(row.adaid);
				var index = admincodes.indexOf(adaid);
				if (index < 0) {
					admincodes.push(adaid);
				}
				window.localStorage.POIBATCHEDITOR_admincodes = admincodes.join(",");
			},
			onUncheck : function(row) {
				var adaid = String(row.adaid);
				var index = admincodes.indexOf(adaid);
				if (index >= 0) {
					admincodes.splice(index, 1);
				}
				window.localStorage.POIBATCHEDITOR_admincodes = admincodes.join(",");
			},
			onCheckAll : function(rows) {
				if (!rows || rows.length <= 0)
					return;
				
				for (var i in rows) {
					var adaid = String(rows[i].adaid);
					var index = admincodes.indexOf(adaid);
					if (index < 0) {
						admincodes.push(adaid);
					}
				}
				
				window.localStorage.POIBATCHEDITOR_admincodes = admincodes.join(",");
			},
			onUncheckAll : function(rows) {
				if (!rows || rows.length <= 0)
					return;
				
				for (var i in rows) {
					var adaid = String(rows[i].adaid);
					var index = admincodes.indexOf(adaid);
					if (index >= 0) {
						admincodes.splice(index, 1);
					}
				}
				
				window.localStorage.POIBATCHEDITOR_admincodes = admincodes.join(",");
			}
		});
		
		$('#searchModel').on('show.bs.modal',function() {
			var myField = document.getElementById("codeTextArea");
			myField.focus();
		});
		
	});
	
	function checkboxFormat(value, row, index) {
		if (admincodes.indexOf(row.adaid) >= 0 || admincodes.indexOf(String(row.adaid)) >= 0)
			return true;
		else
			return false;
	}
	
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
	
	function getJunctionviews() {
		var tabIndex = $("ul#myTab li.active").index();
		if (tabIndex == 0) {
			if (!admincodes || admincodes.length <= 0) {
				$.zealot.showMsgLabel("alert", "请勾选区划");
				return;
			}
			
			$.zealot.showMsgBox("info", "查询中...");
			$('[data-toggle="junctionviews"]').bootstrapTable("showLoading");
			
			var params = {};
			params["columns"] = "";
			params["code"] = admincodes.join(",");
			params["number"] = $('[data-toggle="junctionviews"]').bootstrapTable('getOptions').pageNumber;
			params["size"] = $('[data-toggle="junctionviews"]').bootstrapTable('getOptions').pageSize;

			jQuery.post("./junctionview.web", {
				"action" : "getJunctionviewByAdminCodes",
				"params" : JSON.stringify(params)
			}, function(json) {
				if (json.result && json.result > 0) {
					$('[data-toggle="junctionviews"]').bootstrapTable("hideLoading");
					$('[data-toggle="junctionviews"]').bootstrapTable("load", json);
					$('#searchModel').modal('hide');
				} else {
					$.zealot.showMsgLabel("alert", json.resultMsg);
				}
				$.zealot.showMsgBox("close");
			}, "json");
		} else if (tabIndex == 1) {
			var myField = document.getElementById("codeTextArea");
			var code = myField.value;
			
			if (!code) {
				$.zealot.showMsgLabel("alert", "请配置查询条件");
				myField.focus();
				return;
			}
			
			$.zealot.showMsgBox("info", "查询中...");
			$('[data-toggle="junctionviews"]').bootstrapTable("showLoading");
			
			var params = {};
			params["columns"] = "";
			params["code"] = code;
			params["number"] = $('[data-toggle="junctionviews"]').bootstrapTable('getOptions').pageNumber;
			params["size"] = $('[data-toggle="junctionviews"]').bootstrapTable('getOptions').pageSize;
			

			jQuery.post("./junctionview.web", {
				"action" : "getJunctionview",
				"params" : JSON.stringify(params)
			}, function(json) {
				if (json.result && json.result > 0) {
					$('[data-toggle="junctionviews"]').bootstrapTable("hideLoading");
					$('[data-toggle="junctionviews"]').bootstrapTable("load", json);
					$('#searchModel').modal('hide');
				} else {
					$.zealot.showMsgLabel("alert", json.resultMsg);
				}
				$.zealot.showMsgBox("close");
			}, "json");
		} else {
			return;
		}
	}
	
</script>
</head>
<body>
	<div class="container">
		<div id="headdiv"></div>
		<div class="row" style="margin-top: 60px;">
			<table id="junctionviewlist"
				data-side-pagination="server"
				data-pagination="true" data-page-list="[10, 20, 50, 100]" data-page-size="10"
				data-toggle="junctionviews"
				data-height="720"
				data-align='center'>
				<thead>
					<tr>
						<th data-field="junctionview_id">路口放大图ID
							<button type="button" class="btn btn-default btn-xs" id="btSearchModel" data-toggle="modal" title="查询" data-target="#searchModel">
							    <span class="glyphicon glyphicon-search"></span>
							</button>
						</th>
						<th data-field="confirm_uid">编辑人</th>
						<th data-field="manualcheck_uid">校正人</th>
						<th data-field="updatetime" data-formatter="$.zealot.getLocalTime">updatetime</th>
						<th data-field="confirm">confirm</th>
						<th data-field="autocheck">autocheck</th>
						<th data-field="manualcheck">manualcheck</th>
						<th data-field="optype">optype</th>
						<th data-field="confirm_timestamp" data-formatter="$.zealot.getLocalTime">confirm_timestamp</th>
						<th data-field="autocheck_timestamp" data-formatter="$.zealot.getLocalTime">autocheck_timestamp</th>
						<th data-field="manualcheck_timestamp" data-formatter="$.zealot.getLocalTime">manualcheck_timestamp</th>
						<th data-field="owner">所属行政区划代码</th>
						<th data-field="isdel">isdel</th>
						<th data-field="ver" data-formatter="$.zealot.getLocalTime">ver</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<div class="modal fade bs-example-modal-lg" id="searchModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
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
					<ul id="myTab" class="nav nav-tabs">
						<li class="active"><a href="#preset" data-toggle="tab">区划查询</a></li>
						<li><a href="#customize" data-toggle="tab">自定义</a></li>
					</ul>
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane fade in active" id="preset">
							<div>
								<table id="itemAreaslist"
									data-url="./admincode.web?atn=getAdminCodes"
									data-side-pagination="server" data-filter-control="true"
									data-pagination="true" data-page-list="[10, 20, 50, 100]" data-page-size="10"
									data-search-on-enter-key='true' data-align='center'
									data-toggle="itemAreas" data-click-to-select="true"
									data-height="510">
									<thead>
										<tr>
											<th data-field="state" data-checkbox="true" data-formatter="checkboxFormat"></th>
											<th data-field="adaid" data-filter-control="input"
												data-filter-control-placeholder="">adaid</th>
											<th data-field="featcode" data-filter-control="input"
												data-filter-control-placeholder="">featcode</th>
											<th data-field="namec" data-filter-control="input"
												data-filter-control-placeholder="">namec</th>
											<th data-field="owner" data-filter-control="input"
												data-filter-control-placeholder="">owner</th>
										</tr>
									</thead>
								</table>
							</div>
						</div>
						<div class="tab-pane fade" id="customize">
							<div class="form-group">
								<textarea class="form-control" id="codeTextArea" rows="16" style="resize:none"></textarea>
							</div>
							<button type="button" class="btn btn-default"onclick="codeempty();">&nbsp;&nbsp;清空&nbsp;&nbsp;</button>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" onclick="getJunctionviews();">查询</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>