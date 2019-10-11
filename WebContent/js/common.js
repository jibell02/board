/**
 * 
 */
// $objs : $("input[name]")이 넘어오도록 할 것임.
// count > 0 : return false // 에러가 있으면
// count == 0 : return true // 에러가 없으면
function checkInputEmpty($objs){
	var count = 0;
	$objs.each(function(i, obj){
		if($(obj).val() == ""){
			$(obj).next().css("display", "inline");
			count++;
		}
	})
	if(count > 0){ // 에러가 있으면 전송을 막음
		return false;
	}
	return true; // input 태그 중에 빈 태그가 없을 때
}