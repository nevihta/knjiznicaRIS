/**
 * 
 */
function unhide(divID, otherDivId) {
	var item = document.getElementById(divID);
	if (item) {
	        item.className=(item.className=='hidden')?'unhidden':'hidden';
	    }
	    document.getElementById(otherDivId).className = 'hidden';
}