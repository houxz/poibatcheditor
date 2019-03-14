<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>成果导出</title>
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
<link href="resources/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css" rel="stylesheet">

<script src="resources/jquery/jquery-3.2.1.min.js"></script>
<script src="resources/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
<script src="resources/js/zealot.js"></script>
<script src="resources/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<script src="resources/bootstrap-table-1.11.1/bootstrap-table.min.js"></script>
<script src="resources/bootstrap-table-1.11.1/locale/bootstrap-table-zh-CN.js"></script>
<script type="text/javascript" src="resources/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="resources/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$.zealot.getHead();
		$('[data-toggle="pois"]').bootstrapTable({
			locale : 'zh-CN'
		});
		$('#timestart').datetimepicker({
			autoclose : true,
			language : 'zh-CN'
		});
		$('#timeend').datetimepicker({
			autoclose : true,
			language : 'zh-CN'
		});
	});
	
	function getPOIs() {
		var processname = $("#processname").val();
		var taskid = $("#taskid").val();
		
		if (!processname && !taskid) {
			$.zealot.showMsgLabel("alert", "请输入项目名称或任务编号");
			$("#processname").focus();
			return;
		}
		
		$('[data-toggle="pois"]').bootstrapTable("showLoading");
		
		var timestart = $("#timestart").val();
		var timeend = $("#timeend").val();
		
		var params = {};
		params["processname"] = processname;
		params["taskid"] = taskid;
		params["timestart"] = timestart;
		params["timeend"] = timeend;

		jQuery.post("./export.web", {
			"action" : "getPOIs",
			"params" : JSON.stringify(params)
		}, function(json) {
			if (json.result && json.result > 0) {
				$('[data-toggle="pois"]').bootstrapTable("hideLoading");
				$('[data-toggle="pois"]').bootstrapTable("load", json.rows);
			} else {
				$.zealot.showMsgLabel("alert", json.resultMsg);
			}
		}, "json");
	}
	
	function check() {
		var processname = $("#processname").val();
		var taskid = $("#taskid").val();
		
		if (!processname && !taskid) {
			$.zealot.showMsgLabel("alert", "请输入项目名称或任务编号");
			$("#processname").focus();
			return false;
		}
		
		return true;
	}
	
</script>
</head>
<body>
	<div class="container">
		<div id="headdiv"></div>
		<div style="margin-top: 60px; margin-bottom: 10px;">
			<form method="post" action="./exportPOIs.web" id="exportPOIsForm" onsubmit="return check();">
				<div class="input-group" style="width: 100%;">
					<span class="input-group-addon">项目名称：</span>
					<input id="processname" name="processname" type="text" class="form-control" placeholder="请输入项目名称" style="width: 130px;"
						onkeypress="if (event.keyCode==13) { getPOIs(); }">
					<span class="input-group-addon">任务编号：</span>
					<input id="taskid" name="taskid" type="text" class="form-control" placeholder="请输入任务编号" style="width: 130px;"
						onkeypress="if (event.keyCode==13) { getPOIs(); }">
					<span class="input-group-addon">最后修改时间：</span>
				    <input id="timestart" name="timestart" type="text" class="form-control" placeholder="请输入开始时间" style="width: 140px;">
					<span class="input-group-addon">-</span>
					<input id="timeend" name="timeend" type="text" class="form-control" placeholder="请输入结束时间" style="width: 140px;">
					<span class="input-group-btn" style="width: 40%;">
						<button class="btn btn-default" type="button" onclick="getPOIs();">查看筛选结果</button>
						<button class="btn btn-default" type="button" onclick="$('#exportPOIsForm').submit();">导出POI</button>
					</span>
				</div>
			</form>
		</div>
		<div class="row">
			<table id="poilist"
				data-pagination="true"
				data-page-list="All"
				data-page-size="All"
				data-toggle="pois"
				data-height="714"
				data-align='center'>
				<thead>
					<tr>
						<th data-field="processid">项目编号</th>
						<th data-field="processname">项目名称</th>
						<th data-field="taskid">任务编号</th>
						<th data-field="srctaskid">原始任务编号</th>
						<th data-field="srctaskname">原始任务名称</th>
						<th data-field="oid">oid</th>
						<th data-field="featcode">featcode</th>
						<th data-field="sortcode">sortcode</th>
						<th data-field="namec">namec</th>
						<th data-field="tel">tel</th>
						<th data-field="address4">address4</th>
						<th data-field="address5">address5</th>
						<th data-field="address6">address6</th>
						<th data-field="address7">address7</th>
						<th data-field="address8">address8</th>
						<th data-field="lon">lon</th>
						<th data-field="lat">lat</th>
						<th data-field="editname">制作人员</th>
						<th data-field="checkname">校正人员</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>