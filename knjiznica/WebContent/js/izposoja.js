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
	var div = document.getElementById("gradivaDIV");
	var d = new Date();
	var n = d.getTime(); 
	
	var noviDiv = document.createElement("DIV"); 
	noviDiv.id = "id"+n;
	
	var x = document.getElementsByName("gradivaSelect")[0];
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
	var div = document.getElementById("gradivaDIV");
	var d = new Date();
	var n = d.getTime(); 
	
	var noviDiv = document.createElement("DIV"); 
	noviDiv.id = "id"+n;
	
	noviDiv.innerHTML='Gradivo <input type="number" name="gradivaInput" class="textbox"  />';
	
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

function izbrisi(id){
	var div = document.getElementById(id);
	div.remove();
}