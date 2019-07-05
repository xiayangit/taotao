<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	 <a class="easyui-linkbutton" onclick="impl()">添加索引</a>
</div>

<script type="text/javascript">

function impl(item){
	$.post("/index/impl",function(data){
		if(data.status == 200){
			$.messager.alert('导入索引库成功!');
		}else{
			$.messager.alert('导入索引库失败!');
		}
	});
}
</script>