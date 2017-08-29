<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*" %>
<%@ page import="org.springframework.data.redis.core.ValueOperations" %>
<%@ page import="haron.aimshoot.vo.*" %>
<%
ValueOperations<String,String> msgOps_Profile = (ValueOperations<String,String>)request.getAttribute("msgOps");
LangVO langVo_Profile = (LangVO)request.getAttribute("langVo");
AuthVO authVo_Profile = (AuthVO)request.getAttribute("authVo");
%>
treeView

<script id="text/javascript">
/**
var serviceRoot = "http://demos.kendoui.com/service";
homogeneous = new kendo.data.HierarchicalDataSource({
  transport: {
    read: {
      url: serviceRoot + "/Employees",
      dataType: "jsonp"
    }
  },
  schema: {
    model: {
      id: "EmployeeId",
      hasChildren: "HasEmployees"
    }
  }
});

var treeView = $("#treeview").kendoTreeView({
  dataSource: homogeneous,
  dataTextField: "FullName"
}).data("kendoTreeView");
var $element = treeView.element;
treeView.one("dataBound", function(e){
  this.dataSource.data().forEach(function(node){
      node.set("expanded", true);
  });
});
*/


function addCategory(user_no){
	alert("IN-addCategory111111111");

	/**
	var treeView = $("#treeview").data("kendoTreeView");
 var dataSource = treeView.dataSource;
 treeView.select($("#treeView").find("category_name").first());
 var category = $("#treeView").find("category_name").first();
     alert("55: dataSource:" + dataSource + "category:" + category + ", item item:" + item);
    //  var selectitem = treeView.findByUid(getitem.uid);

   var tv = $('#treeview').data('kendoTreeView'),
    selected = tv.select(),
    item = tv.dataItem(selected);
   if (item) {
	   alert('Selected item: ' + item.Name + ' : ' + item.Id + ' (uid: ' + item.uid + ')');
	 } else {
	   alert('Nothing selected item:' + item);
	 }
   */
   	
	var category_name = $("#category_name").val();
	var category_cnt = parseInt($("#category_cnt").val()) + 1;
	var category_no = parseInt(user_no) + category_cnt;
	var treeView;
	var selectedCategory;
	var newCategory;
	var cateogry;
	
	if(category_name.length > 0){
		
		treeView = $("#treeview").data("kendoTreeView");
		selectedCategory = treeView.select();
		cateogry = treeView.dataItem(selectedCategory);
		
		if(cateogry){
			category_cnt = parseInt($("#category_cnt").val()) + 1;
			category_no = parseInt(user_no) + category_cnt;
			$("#category_cnt").val(category_cnt);
			
			newCategory = treeView.append( {category_name:category_name, category_no:category_no, items:[]}, selectedCategory);
			treeView.select(newCategory);
			 $("#category_name").val('');
			
		}else{
			alert("追加カテゴリを選択してください。");
		}
	}else{
		alert("入力してください。");
	}	
}

function deleteCategory(){
	alert("IN-deleteCategory");
	var treeView = $("#treeview").data("kendoTreeView");
	var $parent = treeView.parent(treeView.select());
	    treeView.remove(treeView.select());
	    treeView.select($parent);
}

function updateCategory(){
	alert("IN-updateCategory");
	var treeviewDataSource = $("#treeview").data("kendoTreeView").dataSource.view();
	var category_list = JSON.stringify(treeviewDataSource);
	
	alert("category_list:" + category_list);
	$('#category_list').val(category_list);	
	execAction('updateCategory');	
}

var $element;
function tree_drag(e) {
	  console.log("Drag", e.sourceNode, "over", e.dropTarget);
}


function categoryModification(){
	alert("IN-categoryModification");
	var treeView = $("#treeview").data("kendoTreeView");
	var selected = treeView.select();
	var item = treeView.dataItem(selected);
	var node = treeView.findByUid(item.uid);
	
//	var currentText = selected.text();
//	 selected.item(node, "zzz");

//	item.categ6ory_name("aaaaa");
//	 selected.text(node, "zzz");
/**
var dataItem = treeView.dataSource.data()[0].items[1];
    dataItem.set("text", "Thrones");    
	 item.set("text", "ggg");
		alert("888 selected:" + selected + ", currentText:" + currentText + ", item:" + item.category_name + ", node:" + node);
*/
		
		// var currentText = $("#treeviewFiles").data("kendoTreeView").select().find('span.k-in').first().text();
	
}

function selected(){
	var treeView = $("#treeview").data("kendoTreeView");
	var selected = treeView.select();
	var item = treeView.dataItem(selected);
//	treeview.bind("drag", tree_drag());
	alert("treeView:" + treeView + "item:" + item + ", itemID:" + item.ID + ", category_name:" + item.category_name);
	item.set("category_name", "zzzzz");
	
	var treeviewDataSource = $("#treeview").data("kendoTreeView").dataSource.view();
	alert("treeviewDataSource:" + JSON.stringify(treeviewDataSource));
	/**
	var newItem = treeView.append( {category_name:"aaa3", category_no:"no-aaa1", 
 	   items:[
 	          
	          {category_name:"aaa3-1", ID:"7"},
	          {category_name:"aaa3-2", ID:"8"},
	          {category_name:"aaa3-3", ID:"9"},
   ]}, selected);
	    treeView.select(newItem);
	   */ 
	   

}	

function categoryListView(){
 alert("IN-categoryListView");
	
	// var categoryList = $('#categoryList').val();
	// var categoryList= JSON.parse($('#categoryList').val());
//	alert("IN-categoryListView:" + categoryList);
//	var jsontext = '{"firstname":"Jesper","surname":"Aaberg","phone":["555-0100","555-0120"]}';
//	var jsontext = '[{"category_name":"aaa","category_no":"10","items":[{"category_name":"aaa1","category_no":"11","items":[{"category_name":"aaa1-1","category_no":"11"}]}]}]';
	var jsontext = $('#category_list').val();
	
	
//	var jsontext = '[{category_name:"aaa",category_no:"10", items:[{category_name:"aaa1", category_no:"no-aaa1", items:[{category_name:"aaa1-1", ID:"1"},{category_name:"aaa1-2", ID:"2"},{category_name:"aaa1-3", ID:"3"}]},{category_name:"aaa2", category_no:"no-aaa1", items:[{category_name:"aaa2-1", ID:"4"}, {category_name:"aaa2-2", ID:"5"}, {category_name:"aaa2-3", ID:"6"}]}, {category_name:"aaa3", category_no:"no-aaa1", items:[{category_name:"aaa3-1", ID:"7"}, {category_name:"aaa3-2", ID:"8"}, {category_name:"aaa3-3", ID:"9"}]}]}]';
//	var jsontext = '{"category_name":"aaa","category_no":"10","items":[{"category_name":"aaa1","category_no":"no-aaa1",items:[{"category_name":"aaa1-1"}]}';
	var category_list = JSON.parse(jsontext);
	
//	alert("22jsontext:" + jsontext);
//	alert("categoryList):" +  $('#categoryList').val());
//	var categoryList = [{category_name:"aaa",category_no:"10", items:[{category_name:"aaa1", category_no:"no-aaa1", items:[{category_name:"aaa1-1", ID:"1"},{category_name:"aaa1-2", ID:"2"},{category_name:"aaa1-3", ID:"3"}]},{category_name:"aaa2", category_no:"no-aaa1", items:[{category_name:"aaa2-1", ID:"4"}, {category_name:"aaa2-2", ID:"5"}, {category_name:"aaa2-3", ID:"6"}]}, {category_name:"aaa3", category_no:"no-aaa1", items:[{category_name:"aaa3-1", ID:"7"}, {category_name:"aaa3-2", ID:"8"}, {category_name:"aaa3-3", ID:"9"}]}]}];
	
	 var treeView = $("#treeview").kendoTreeView({
  	   dragAndDrop: true,
         dataSource   :category_list,
         dataTextField:["category_name"]
     }).data("kendoTreeView");
     treeView.expand(".k-item");
     

}

function testJquery(){
	alert("IN-testJquery");	
	

	var content = [
	               {
	            	   category_name:"aaa",
	            	   category_no:"10",
	                   items :[
	                       {category_name:"aaa1", category_no:"no-aaa1", 
	                    	   items:[
	                    	          {category_name:"aaa1-1", ID:"1"},
	                    	          {category_name:"aaa1-2", ID:"2"},
	                    	          {category_name:"aaa1-3", ID:"3"},
	                       ]},
	                       {category_name:"aaa2", category_no:"no-aaa1", 
	                    	   items:[
	                    	          {category_name:"aaa2-1", ID:"4"},
	                    	          {category_name:"aaa2-2", ID:"5"},
	                    	          {category_name:"aaa2-3", ID:"6"},
	                       ]},
	                       {category_name:"aaa3", category_no:"no-aaa1", 
	                    	   items:[
	                    	          {category_name:"aaa3-1", ID:"7"},
	                    	          {category_name:"aaa3-2", ID:"8"},
	                    	          {category_name:"aaa3-3", ID:"9"},
	                       ]}	                       
	                   ]
	               }	               
	           ];

	
	           var treeView = $("#treeview").kendoTreeView({
	        	   dragAndDrop: true,
	               dataSource   :content,
	               dataTextField:["category_name"]
	           }).data("kendoTreeView");
	           treeView.expand(".k-item");
	           
	           $element = treeView.element;
	           var treeViewElement = $element.data("kendoTreeView");
	           
	           /**
	           var id = 1;
	           var data = {FullName: "New Node"};
	           var treeViewaa = $element.data("kendoTreeView");
	           var getitem = treeViewaa.dataSource.get(id);
	           var selectitem = treeViewaa.findByUid(getitem.uid);
	           newItem = treeViewaa.append(data, selectitem);
	           treeViewaa.select(newItem);
	           */
	           // element = treeview.element;
	
/**
	     $("#treeview").kendoTreeView({
		  dragAndDrop: true,
		  dataSource: [
		    { text: "foo", items: [
		      { text: "bar" },{text:"zzz"}
		    ] }
		  ],
		  dataTextField: "Name",
		  dataValueField: "Id"
		});
	*/
/**
	var serviceRoot = "http://demos.kendoui.com/service";
	homogeneous = new kendo.data.HierarchicalDataSource({
	  transport: {
	    read: {
	      url: serviceRoot + "/Employees",
	      dataType: "jsonp"
	    }
	  },
	  schema: {
	    model: {
	      id: "EmployeeId",
	      hasChildren: "HasEmployees"
	    }
	  }
	});
		
	var treeView = $("#treeview").kendoTreeView({
	  dataSource: homogeneous,
	  dataTextField: "FullName"
	}).data("kendoTreeView");
	var $element = treeView.element;
	treeView.one("dataBound", function(e){
	  this.dataSource.data().forEach(function(node){
	      node.set("expanded", true);
	  });
	});
*/
}
	
/*
Drag&Drop

	$("#treeview").kendoTreeView({
	  dragAndDrop: true,
	  dataSource: [
	    { text: "foo", items: [
	      { text: "bar" }
	    ] }
	  ]
	});
	
	var treeview = $("#treeview").data("kendoTreeView");
	treeview.bind("drag", tree_drag);
*/
	
function addNode(){
	  addToTree(1, {FullName: "New Node"});
}

function addToTree(id, data) {   // This works just fine.
	
	alert("111 IN-addToTree id:" + id + ", data:" + data + ", element:" + element);
    var treeView = $element.data("kendoTreeView");
	alert("111 IN-addToTree id:" + id + ", data:" + data);
    
    var getitem = treeView.dataSource.get(id);
    var selectitem = treeView.findByUid(getitem.uid);
    newItem = treeView.append(data, selectitem);
    treeView.select(newItem);
}

function removeFromTree () {
	var treeView = $("#treeview").data("kendoTreeView");
    var $parent = treeView.parent(treeView.select());
    treeView.remove(treeView.select());
    treeView.select($parent);    
}

</script>
カテゴリ設定


  <button type="button" onclick="testJquery()">TestJquery</button>
  <button type="button" onclick="selected()">Selected</button>

  <input type="text" id="category_name">
  <button type="button" onclick="addCategory('${userVo.user_no}')">Add node</button>
  <button type="button" onclick="deleteCategory()">Remove node</button>
  
  <div id="treeview"></div>  
  
<table>
	<tr>
		<td>
			<input type="button" value="<%= msgOps_Profile.get("save_" + langVo_Profile.getLang_type())%>" onclick="updateCategory();">			
		</td>
	</tr>
	<tr>
		<td>
			<input type="button" value="<%= msgOps_Profile.get("add_" + langVo_Profile.getLang_type())%>" onclick="categoryAdd();">
			<input type="button" value="<%= msgOps_Profile.get("modification_" + langVo_Profile.getLang_type())%>" onclick="categoryModification();">
			<input type="button" value="<%= msgOps_Profile.get("delete_" + langVo_Profile.getLang_type())%>" onclick="categoryDelete();">
		</td>
	</tr>
	<tr>
		<td>
			<div class="category">
				<select id="category1" name="category1" size="30" style="pt;width:200px" onclick="cateogryParent('category1', 'category2');">
				</select>
				<select id="category2"  name="category2" size="30" style="pt;width:200px" onclick="cateogryParent('category2', 'category3');">
				</select>
				<select id="category3" name="category3" size="30" style="pt;width:200px" MULTIPLE>
				</select>
				</div>		
			</td>
	</tr>
</table>


