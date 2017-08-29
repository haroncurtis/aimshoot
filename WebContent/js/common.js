
function setVal(ojID, val){
	if(val != null){
		document.getElementById(ojID).value = val;
	}
}

function getVal(ojID){
	return document.getElementById(ojID).value; 
}

function initView(ojID, val, action_code, msg_value){
	setVal('view_name' ,val);
	if(action_code != 1){
		alert(msg_value);
	}
}


/**
 * Action実行
 */
function execAction(actionName){
	var form = document[getVal("view_name")];
	
	form.action = actionName + ".action";
	form.submit();
}

function encrypt(publicKeyModulus, publicKeyExponent){
	var view_name = getVal("view_name");
	var form = document[view_name];
	var user_id = form.user_id;
	var user_pwd = form.user_pwd;
	var user_repwd = form.user_repwd;
	var encryptUser_id = document.getElementById("encryptUser_id");
	var encryptUser_pwd = document.getElementById("encryptUser_pwd");
	var encryptUser_repwd = document.getElementById("encryptUser_repwd");
	var user_change_pwd = null;
	var encryptUser_change_pwd = null;
	
	var rsa = new RSAKey();
	rsa.setPublic(publicKeyModulus, publicKeyExponent);

	switch(view_name){
	case "joinView": if(user_repwd.value != ""){encryptUser_repwd.value = rsa.encrypt(user_repwd.value);}  break;
	case "accountSettingView":
		user_change_pwd = form.user_change_pwd;
		encryptUser_change_pwd =  form.encryptUser_change_pwd;
		if(user_change_pwd.value != ""){encryptUser_change_pwd.value = rsa.encrypt(user_change_pwd.value);}  
		if(user_repwd.value != ""){encryptUser_repwd.value = rsa.encrypt(user_repwd.value);}  break;
		break;
	}
	
	if(view_name != "accountSettingView"){
		if(user_id.value != ""){encryptUser_id.value = rsa.encrypt(user_id.value);}
	}
	if(user_pwd.value != ""){encryptUser_pwd.value = rsa.encrypt(user_pwd.value);}
}

