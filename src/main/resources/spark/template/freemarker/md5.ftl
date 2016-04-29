<script src="/js/md5.js"></script>
<script>
$(document).ready(function() {

  $('form').submit(function(e) {
	var fieldPwd = $('#pwd');
    var strVal = fieldPwd.val();
    var strMD5 = CryptoJS.MD5(strVal).toString();
    fieldPwd.val( strMD5 );
    fieldPwd = $('#pwdConfirmation');
    strVal = fieldPwd.val();
    strMD5 = CryptoJS.MD5(strVal).toString();
    fieldPwd.val( strMD5 );
    return true;
  });
});
</script>