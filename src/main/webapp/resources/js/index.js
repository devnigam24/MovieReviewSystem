function openModal(idName) {
    $('#'+idName).openModal();
}
function openBelowModal(e){
	$(e.parentElement).next().openModal();
}

function openReviewBox(e){
	$(e).next().attr('class',"bottom-sheet");
}

$(document).ready(function(){
	$('select').material_select();
	
	$('select').each(function(){
	    $(this).change(function(){
	        var myAction = $(this).parent().parent().parent().parent().parent().attr('action');
	        myAction = myAction + '?movieRating=' + $(this).val();
	        $(this).parent().parent().parent().parent().parent().attr('action',myAction);
	    });
	});
});

function attachReviewValue(e){
	var thisForm = e.parentNode.parentNode;
	var review = thisForm.childNodes[3].childNodes[0].value.trim();
	$(thisForm).attr('action',$(thisForm).attr('action') + "?review="+review);
}