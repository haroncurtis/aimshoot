
function categoryAdd(){
	alert("IN-categoryAdd");
	
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
		
	
	/**
	var text = '{ "name":"John", "birth":"1986-12-14", "city":"New York"}';
	var obj = JSON.parse(text, function (categoryList, value) {
	    if (key == "birth") {
	       alert("value:" + value);
	    } else {
	        alert("else value:" + value);
	    }});
	 */
	
	
/**
	for (var i = 0; i < categoryList.length; i++) {
		for (var j = 0; j < categoryList[i]["0"].length; j++) {
			alert("value:" + categoryList[i]["0"][j]);
		};
	};
	*/
	/**
	var category_child1 = document.getElementById('category_child1');
	
	var option = document.createElement('option');
	option.setAttribute('value', 'value属性に入れる値');
	option.innerHTML = '要素に入れる文字列';
	category_child1.appendChild(option);
	*/
	
}





function logout(msg){
	var confirmFlg = confirm(msg);
	if(confirmFlg){
		execAction('logout');
	}
}

/**
 * flg = 0: プロファイル編集
 * flg = 1: プロファイル取消
 */
function profileEdita(flg, emailVal, call1Val, call2Val){
	var profileEdit = document.getElementById("profileEdit");
	var profileCancel = document.getElementById("profileCancel");
	var profileConfirm = document.getElementById("profileConfirm");
	var email = document.getElementById("email");
	var call1 = document.getElementById("call1");
	var call2 = document.getElementById("call2");

	if(flg == 0){
		profileEdit.style.display = "none";
		profileCancel.style.display = "block";
		profileConfirm.style.display = "block";
		
		email.readOnly = false;
		call1.readOnly = false;
		call2.readOnly = false;
		
	}else{
		profileEdit.style.display = "block";
		profileCancel.style.display = "none";
		profileConfirm.style.display = "none";
		
		email.readOnly = true;
		call1.readOnly = true;
		call2.readOnly = true;
		
		setVal("email", emailVal);
		setVal("call1", call1Val);
		setVal("call2", call2Val);
	}
	
	/**
	var profileEdit = document.getElementById("profileEdit");
	var profileCancel = document.getElementById("profileCancel");
	var profileConfirm = document.getElementById("profileConfirm");

	if(flg){
		profileEdit.style.display = "none";
		profileEdit.style.display = "block";
		profileEdit.style.display = "block";
	}else{

		setVal("email", email);
		setVal("call1", call1);
		setVal("call2", call2);
		
		profileEdit.style.display = "block";
		profileCancel.style.display = "none";
		profileConfirm.style.display = "none";
	}
	*/
}


function cateogryParent(categoryID1, categoryID2){
	
	var categoryList = getVal("categoryList");
	var category_parent = null;
	var category_parentNo = 0;
	var category3;
	var category_child = document.getElementById(categoryID2);
	var categoryAR = JSON.parse(categoryList);
	
	if(categoryID1 != 0){
		category_parent = document.getElementById(categoryID1);
		category_parentNo = category_parent.options[category_parent.selectedIndex].value;
	}
	
	if(categoryID2 == "category2"){
		category3 = document.getElementById("category3");
		while(category3.hasChildNodes()){
			category3.removeChild(category3.firstChild);
		}	
	}
	
	while(category_child.hasChildNodes()){
		category_child.removeChild(category_child.firstChild);
	}
	
	for(var i = 0; i < categoryAR[categoryID2].length; i++){
		if(categoryAR[categoryID2][i].category_parent == category_parentNo){
			option = document.createElement('option');
			option.value = categoryAR[categoryID2][i].category_no;
			option.innerHTML = categoryAR[categoryID2][i].category_name;
			category_child.appendChild(option);	
		}
	}
}