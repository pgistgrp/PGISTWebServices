var newMinx = 0;
var newMiny = 0;
var newMaxx = 0;
var newMaxy = 0;
var boxMinx = 0;
var boxMiny = 0;
var boxMaxx = 0;
var boxMaxy = 0;

var mapClickAsRecenter = true;
var mapBoxAsZoom = true;
var allowRubberband = true;
var allowMapClick = true;
var state = "pan";  // zoomOut, pan

// Global vars to save mouse position
var mouseX=0;
var mouseY=0;
var x1=0;
var y1=0;
var x2=0;
var y2=0;
var zminx=0;
var zmaxx=0;
var zmaxy=0;
var zminy=0;

var mapX = 0; 
var mapY = 0; 
var zoomBoxWidth = 2;

//SEARCHLITE var state = "zoom"; // "pan"

var zooming=false;
var panning=false;
var bottomBorderHeight = 13;

// setup test for Nav 4.0
var isIE = false;
var isNav = (navigator.appName.indexOf("Netscape")>=0);
var isNav4 = false;
var isIE4 = false;
var is5up = false;
var isMac = false;
var isWin = false;


if (isNav) {
	
	if (parseFloat(navigator.appVersion)<5) {
		isNav4=true;
		//alert("Netscape 4.x or older");
	} else {
		is5up = true;
	}
} else {
	isIE4=true;
	isIE=true;
	if (navigator.appVersion.indexOf("MSIE 5")>0) {
		isIE4 = false;
		is5up = true;
		//alert("IE5");
	}
}	

if (navigator.userAgent.indexOf("Win") >= 0) 
	isWin = true;
else
	isMac = true;

//***********************************************	
//***************** FUNCTIONS *******************
//***********************************************

// check for mouseup
function chkMouseUp(e) { 
	if (zooming || panning) {
		if (mouseX<0)
		 	mouseX = 0;
		if (mouseX>iWidth)
			mouseX = iWidth;
		if (mouseY<0)
			mouseY = 0;
		if (mouseY>iHeight)
			mouseY = iHeight;
		mapTool(e);
	}
}

function getImageXY(e) {
	//if (document.layers) {
	if (isNav) {
		mouseX=e.pageX;
		mouseY=e.pageY;
	} else {
		mouseX=event.clientX + document.body.scrollLeft;
		mouseY=event.clientY + document.body.scrollTop;
	}
	// subtract offsets from page left and right
	mouseX = mouseX-hspc;
	mouseY = mouseY-vspc;
}	

// convert mouse click xy's into map coordinates
function getMapXY(xIn,yIn) {

	mouseX = xIn;
	var pixelX = (maxx-minx) / iWidth;
	mapX = pixelX * mouseX + minx;
	mouseY = iHeight - yIn;
	var pixelY = (maxy-miny) / iHeight;
	mapY = pixelY * mouseY + miny;
}

// get the coords at mouse position
function getMouse(e) {
 
	window.status="";
	getImageXY(e);
	if (zooming) {
		if (mouseX<0)
		 	mouseX = 0;
		if (mouseX>iWidth)
			mouseX = iWidth;
		if (mouseY<0)
			mouseY = 0;
		if (mouseY>iHeight-bottomBorderHeight)
			mouseY = iHeight-bottomBorderHeight;
		x2=mouseX;
		y2=mouseY;
		setClip();
		return false;
	} else if (panning) {
		x2=mouseX;
		y2=mouseY;
		panMouse();	
		return false;
	} else 
    	return true;
	return true;
}

function hideZoomBox() {
	hideLayer("zoomBoxTop");
	hideLayer("zoomBoxLeft");
	hideLayer("zoomBoxRight");
	hideLayer("zoomBoxBottom");
}

// perform appropriate action with mapTool
function mapTool (e) {

	getImageXY(e);
	
	  // Deal with the possibility of an
	  // "identify" first, since it is a
	  // little different than the other
	  // states (doesn't require a mouse
	  // up event).  
	  	
	
	  // If we made it to here, it's a 
	  // navigational click and not an
	  // identify.
	
	if ((!zooming) && (!panning) && 
		(mouseX >= 0) && (mouseX <= iWidth) && 
		(mouseY >= 0) && (mouseY <= iHeight)) {
		if (state == "pan")
			startPan(e);
		else 
			startZoomBox(e);
			
		return false;
	} else if (zooming) {
		getMouse(e);
		stopZoomBox(e);
	} else if (panning) {
		getMouse(e);
		stopPan(e);
	}
	return true;
}

// move map image with mouse
function panMouse() {
	var xMove = x2-x1;
	var yMove = y2-y1;
	var cLeft = -xMove;
	var cTop = -yMove;
	var cRight = iWidth;
	var cBottom = iHeight;
	if (xMove>0) {
		cLeft = 0;
		cRight = iWidth - xMove;
	}
	if (yMove>0) {
		cTop = 0;
		cBottom = iHeight - yMove;
	}
	clipLayer("theMap",cLeft,cTop,cRight,cBottom);
	moveLayer("theMap",xMove+hspc,yMove+vspc);

	return false;
}

// recenter map is the default option
function recenter(e) {

	// otherwise we don't have these layers
	if (allowRubberband)
		hideZoomBox();
		
	getMapXY(mouseX,mouseY);
	if (mapClickAsRecenter) {
		var widthHalf = Math.abs(maxx - minx) / 2;
		var heightHalf = Math.abs(maxy - miny) / 2;
		newMinx = mapX - widthHalf;
		newMaxx = mapX + widthHalf;
		newMaxy = mapY + heightHalf;
		newMiny = mapY - heightHalf;

		refreshMap(); 
	} else
		customMapClick(mapX,mapY);
}

function resetLayer(){
	//document.getElementById("theImage").src = "";
	moveLayer("theMap",hspc,vspc);
	clipLayer("theMap",hspc,vspc,(parseInt(iWidth)+2),(parseInt(iHeight)+2));
	//document.getElementById("theImage") = imgSwap;
}

var imgSwap = null;
function refreshMap() {
  //hideZoomBox();
  var newCenterX = x1 - x2 + iWidth / 2;
  var newCenterY = y1 - y2 + iHeight / 2;
  //document.location = "testmap.jsp?toDo=2&map.x=" + newCenterX.toString()
  //			+ "&map.y=" + newCenterY.toString();
  var imgurl = "getMap?w=640&h=480&nv=pan&x0=" + x1
  	+"&y0=" + y1 + "&x1=" + x2 + "&y1=" + y2;
	
  if(scenarioids != "")imgurl += "&sids=" + scenarioids;
  
  document.getElementById("theImage").onload = resetLayer;
  document.getElementById("theImage").src = imgurl;
  document.getElementById("imageurldisp").value = imgurl;
  
    //content = '<img id="theImage" src="mimemap?so=pgist_cip&w=640&h=480&x0=946186&y0=-125778&x1=1809428&y1=589282"' + iWidth + ' height=' + iHeight + '>';
    //createLayer("theMap",hspc,vspc,(parseInt(iWidth)+2),(parseInt(iHeight)+2),true,content);

  //document.location =  
                      //"&MinX="+newMinx.toString()+
                      //"&MinY="+newMiny.toString()+
                      //"&MaxX="+newMaxx.toString()+
                      //"&MaxY="+newMaxy.toString();
  //NOTE: calling hideLayer("loadLayer") causes
  //      the "Loading" image to disappear 
  //      immediately.
}
// clip zoom box layer to mouse coords
function setClip() {	

	if (x1>x2) {
		zmaxx=x1;
		zminx=x2;
	} else {
		zminx=x1;
		zmaxx=x2;
	}
	if (y1>y2) {
		zminy=y1;
		zmaxy=y2;
	} else {
		zmaxy=y1;
		zminy=y2;
	}
	
	if ((x1 != x2) && (y1 != y2)) {
		clipLayer("zoomBoxTop",zminx,zmaxy,zmaxx,zmaxy+zoomBoxWidth);
		clipLayer("zoomBoxLeft",zminx,zmaxy,zminx+zoomBoxWidth,zminy);
		clipLayer("zoomBoxRight",zmaxx-zoomBoxWidth,zmaxy,zmaxx,zminy);
		clipLayer("zoomBoxBottom",zminx,zminy-zoomBoxWidth,zmaxx,zminy);
	}
}

function setExtent(_minx,_miny,_maxx,_maxy) {
	minx = _minx;
	miny = _miny;
	maxx = _maxx;
	maxy = _maxy;
}

function setZoomBoxColor(color) {
	setLayerBackgroundColor("zoomBoxTop", color);
	setLayerBackgroundColor("zoomBoxLeft", color);
	setLayerBackgroundColor("zoomBoxRight", color);
	setLayerBackgroundColor("zoomBoxBottom", color);
}

function setZoomBoxSettings() {

	// Set up event capture for mouse movement
	if (isNav && is5up) {
		document.captureEvents(Event.MOUSEMOVE);
		document.captureEvents(Event.MOUSEDOWN);
		document.captureEvents(Event.MOUSEUP);
		document.onmousemove = getMouse;
		document.onmousedown = mapTool;
		document.onmouseup = chkMouseUp;
	} else if (isNav4) {
		// otherwise the buttons don't work
		getLayer("theTop").captureEvents(Event.MOUSEMOVE);
		getLayer("theTop").captureEvents(Event.MOUSEDOWN);
		getLayer("theTop").captureEvents(Event.MOUSEUP);
		getLayer("theTop").onmousemove = getMouse;
		getLayer("theTop").onmousedown = mapTool;
		getLayer("theTop").onmouseup = chkMouseUp;
	} else {
		document.onmousemove = getMouse;
		document.onmousedown = mapTool;
		document.onmouseup = chkMouseUp;
	}
}
	
function setZoomBoxWidth(size) {
	zoomBoxWidth = size;
}

function showZoomBox() {
	showLayer("zoomBoxTop");
	showLayer("zoomBoxLeft");
	showLayer("zoomBoxRight");
	showLayer("zoomBoxBottom");
}

// start pan.... image will move
function startPan(e) {

	moveLayer("theMap",hspc,vspc);

	getImageXY(e);
	// keep it within the MapImage
	if ((mouseX<iWidth) && (mouseY<iHeight)) {
		if (panning) {
			stopPan(e);
		} else {
			x1=mouseX;
			y1=mouseY
			x2=x1+1;
			y2=y1+1;
			panning=true;
		}
	}
	return false;

}

// start zoom in.... box displayed
function startZoomBox(e) {

	getImageXY(e);	

	if (!allowRubberband) {
		stopZoomBox(e);
	} else {	
		// keep it within the MapImage
		if ((mouseX<iWidth) && (mouseY<iHeight-bottomBorderHeight)) {
			if (!zooming) {
				x1=mouseX;
				y1=mouseY;
				x2=x1+1;
				y2=y1+1;
				zooming=true;
				clipLayer("zoomBoxTop",x1,y1,x2,y2);
				clipLayer("zoomBoxLeft",x1,y1,x2,y2);
				clipLayer("zoomBoxRight",x1,y1,x2,y2);
				clipLayer("zoomBoxBottom",x1,y1,x2,y2);
				showZoomBox();
			}
		} else {
			if (zooming) {
				stopZoomBox(e);
			}
		}
	}
	return false;	
}

// stop moving image.... pan 
function stopPan(e) {

	if ((Math.abs(x2-x1) < 2) && (Math.abs(y2-y1) < 2)) {
		// the move is too small
		recenter(e);
	} else  {
		window.scrollTo(0,0);
		panning=false;
		var width = Math.abs(maxx - minx);
		var height = Math.abs(maxy - miny);
		var tempLeft=minx;
		var tempRight=maxx;
		var tempTop=maxy;
		var tempBottom=miny;
		var ixOffset = x2-x1;
		var iyOffset = y1-y2;
		pixelX = width / iWidth;
		var theY = iHeight - zmaxy;
		pixelY = height / iHeight;
		var xOffset = pixelX * ixOffset;
		var yOffset = pixelY * iyOffset;
		newMaxy = maxy - yOffset;
		newMaxx = maxx - xOffset;
		newMinx = minx - xOffset;
		newMiny = miny - yOffset;

		// AP specific setting
		attributeUpdate = false;
		
		refreshMap();
	}
		
	return true;
	
}

// stop zoom box display... zoom in
function stopZoomBox(e) {
	zooming=false;
	if ((zmaxx <zminx+2) && (zmaxy < zminy+2)) {
		// if the zoom box is too small
		recenter(e);
	} else {
		var width = Math.abs(maxx - minx);
		var height = Math.abs(maxy - miny);
		var pixelX = width / iWidth;
		var theY = iHeight - zmaxy;
		var pixelY = height / iHeight;
		newMaxy = pixelY * theY + miny;
		newMaxx = pixelX * zmaxx + minx;
		newMinx = pixelX * zminx + minx;
		theY = iHeight - zminy;
		pixelY = height / iHeight;
		newMiny = pixelY * theY + miny;

		if (mapBoxAsZoom) {
			if (state == "zoomOut") {
				percentX = (maxx-minx)/(newMaxx-newMinx);
				percentY = (maxy-miny)/(newMaxy-newMiny);
				percent = (percentX+percentY)/2;
				
				widthH = (maxx-minx)/2;
				heightH = (maxy-miny)/2;
				cx = newMinx + widthH;
				cy = newMiny + heightH;
				
				newMinx = cx - percent * widthH;
				newMiny = cy - percent * heightH;
				newMaxx = cx + percent * widthH;
				newMaxy = cy + percent * heightH;
			}
			refreshMap();
		} else
			customMapBox(newMinx, newMiny, newMaxx, newMaxy);
			
	}
	return true;
}

//-------- LAYER SUPPORT FUNCTIONS --------------

// atlas_common.js

// clip layer display to clipleft, cliptip, clipright, clipbottom
function clipLayer(name, clipleft, cliptop, clipright, clipbottom) {		
	var layer = getLayer(name);		
	if (layer != null) {
  		if (isNav4) {
			layer.clip.left   = clipleft;
			layer.clip.top    = cliptop;
		    layer.clip.right  = clipright;
			layer.clip.bottom = clipbottom;
		} else if (isIE) {
			layer.clip = 'rect(' + cliptop + ' ' +  clipright + ' ' + clipbottom + ' ' + clipleft +')';
	    } else {
		    layer.overflow = "hidden";
		    layer.height = clipbottom - cliptop;
			layer.width	= clipright - clipleft;
			layer.top	= (cliptop+vspc) + "px";
			layer.left	= (clipleft+hspc) + "px";
		}
	}
}

// Create a DHTML layer
function createLayer(name, left, top, width, height, visible, content) {
	  var layer;
	  if (isNav4) {
	    document.writeln('<layer name="' + name + '" left=' + left + ' top=' + top + ' width=' + width + ' height=' + height +  ' visibility=' + (visible ? '"show"' : '"hide"') +  '>');
	    document.writeln(content);
	    document.writeln('</layer>');
	    layer = getLayer(name);
	    layer.width = width;
	    layer.height = height;
	  } else {
	    document.writeln('<div id="' + name + '" style="position:absolute; overflow:none; left:' + left + 'px; top:' + top + 'px; width:' + width + 'px; height:' + height + 'px;' + ' visibility:' + (visible ? 'visible;' : 'hidden;') +  '">');
	    document.writeln(content);
	    document.writeln('</div>');
	  }
	  	  clipLayer(name, hspc, vspc, width, height);
}

function makeLayer(name, left, top, width, height, visible, content) {
	  var layer;
	  var strLayer = "";
	  if (isNav4) {
	    strLayer = '<layer name="' + name +' width=' + width + ' height=' + height +  ' visibility=' + (visible ? '"show"' : '"hide"') +  '>';
	    strLayer += content;
	    strLayer += '</layer>';
	  } else {
	    strLayer = '<div id="' + name + '" style="position:absolute; overflow:none; left:' + left + 'px; top:' + top + 'px; width:' + width + 'px; height:' + height + 'px;' + ' visibility:' + (visible ? 'visible;' : 'hidden;') +  '">';
	    strLayer += content;
	    strLayer += '</div>';
	  }
	  
	  return strLayer;
}

// get the layer object called "name"
function getLayer(name) {
	  if (isNav4)
	    return(document.layers[name]);
	  else if (isIE4) {
	  	if ( eval('document.all.' + name) != null) {
		    layer = eval('document.all.' + name + '.style');
		    return(layer);
		} else
			return(null);
	  } else if (is5up) {
		var theObj = document.getElementById(name);
		return theObj.style
	  } else
	    return(null);
}

// toggle layer to invisible
function hideLayer(name) {		
  	var layer = getLayer(name);		
	if (layer != null) {
	 	if (isNav4)
			layer.visibility = "hide";
		else
			layer.visibility = "hidden";
	}
}

// move layer to x,y
function moveLayer(name, x, y) {		
  	var layer = getLayer(name);		
	if (layer != null) {
	  	if (isNav4)
    		layer.moveTo(x, y);
	 	else if (isIE) {
    		layer.left = x + "px";
   			layer.top  = y + "px";
		} else {
			layer.height = iHeight - y;
			layer.width	= iWidth - x;
    		layer.left = x + "px";
   			layer.top  = y + "px";
	  	}
	}
}

// replace layer's content with new content
function replaceLayerContent(name, content) {
	  if (isNav4) {
		    var layer = getLayer(name);
			if (layer != null) {
			    layer.document.open();
			    layer.document.writeln(content);
			    layer.document.close();
			}
	  }  else if (isIE) {
			if (eval("document.all." + name) != null) {
		  		content = content.replace(/\'/g,"\\'");
			    var str = "document.all." + name + ".innerHTML = '" + content + "'";
			    eval(str);
			}
	  }
}

// set layer background color
function setLayerBackgroundColor(name, color) {		
  	var layer = getLayer(name);		
	if (layer != null) {
	    if (isNav4) 
    		layer.bgColor = color;
		else 
    		layer.backgroundColor = color;
	}
}

// toggle layer to visible
function showLayer(name) {		
  	var layer = getLayer(name);		
	if (layer != null) {
	  	if (isNav4)
			layer.visibility = "show";
		else
   		 	layer.visibility = "visible";
	}
}