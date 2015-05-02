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

function dodajSeznam(){
	var div = document.getElementById("avtorjiDIV");
	var d = new Date();
	var n = d.getTime(); 
	
	var noviDiv = document.createElement("DIV"); 
	noviDiv.id = "id"+n;
	
	var x = document.getElementsByName("avtorjiSelect")[0];
	var clone= div.getElementsByTagName('select')[0].cloneNode(true);
	noviDiv.appendChild(clone);
	
	var brisanje =  document.createElement('a');
	brisanje.innerHTML = "Izbrisi";
	brisanje.onclick = function() {
          noviDiv.remove();
    }
	
	noviDiv.appendChild(brisanje);
	var br = document.createElement("BR");
	noviDiv.appendChild(br);
	div.appendChild(noviDiv);
}

function dodajNovo(){
	var div = document.getElementById("avtorjiDIV");
	var d = new Date();
	var n = d.getTime(); 
	
	var noviDiv = document.createElement("DIV"); 
	noviDiv.id = "id"+n;
	
	noviDiv.innerHTML='Ime <input type="text" name="avtorjiImeInput" class="textbox"  /> Priimek <input type="text" name="avtorjiPriimekInput" class="textbox"  />';
	
	var brisanje =  document.createElement('a');
	brisanje.innerHTML = "Izbriši";
	brisanje.onclick = function() {
          noviDiv.remove();
    }
	
	noviDiv.appendChild(brisanje);
	var br = document.createElement("BR");
	noviDiv.appendChild(br);
	div.appendChild(noviDiv);
}