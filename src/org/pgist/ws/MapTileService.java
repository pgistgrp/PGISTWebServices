package org.pgist.ws;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.Node;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;

import java.net.URL;
import java.net.URLConnection;
import java.net.MalformedURLException;

/**
 * Servlet implementation class for Servlet: WMSService
 *
 */
 public class MapTileService extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public MapTileService() {
		super();
	} 
	
	/* (non-Javadoc)
	 * @see javax.servlet.Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}   	
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
         * http://collab.geog.washington.edu:8080/servlets-examples/pgistmap?servername=collab&mapservice=LIT_ImageMap1&bbox=-122.3224667,47.65985278,-122.1224667,47.67985278&width=256&height=256&x=1&y=1&z=3
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 1. prepare parameters
		String tilepath = "D:\\ArcIMS\\litmaptiles\\";
		String outputpath = "D:\\ArcIMS\\output\\";
		String image_url;
		
		String xmin, ymin, xmax, ymax;
		String x, y, z;
		String width, height;
		String image_name, image_type;
		byte[] return_image = new byte[1];
		
                String bbox = request.getParameter("BBOX");
                String[] coords = bbox.split(",");
		xmin = coords[0];
		ymin = coords[1];
		xmax = coords[2];
		ymax = coords[3];
		x = request.getParameter("X");
		y = request.getParameter("Y");
		z = request.getParameter("zoom");
		width = request.getParameter("WIDTH");
		height = request.getParameter("HEIGHT");
		
		if(request.getParameter("image_type") != null)
			image_type = request.getParameter("image_type");
		else
			image_type = "png";
		
		//2. make image name
		image_name = "lit1map__" + x + "_" + y + "_" + z + "." + image_type;
                System.out.println("--requested image name: " + image_name);
		
		//3. prepare image bytes
		File imagefile = new File(tilepath + image_name);
		if(imagefile.exists()){
			FileInputStream in = new FileInputStream(imagefile);
			return_image = new byte[ (int)imagefile.length() ];
			in.read( return_image );
			in.close();
		}else {
	    	String axl = "<ARCXML version=\"1.1\">";
	    	axl += "<REQUEST><GET_IMAGE autoresize =\"true\"><PROPERTIES>";
	    	axl += "<BACKGROUND color=\"255,255,255\" transcolor=\"255,255,255\"/>";
	        //<!---Lat Long envelope from above.  Google Map tile lat-longs reprojected to Mercator--->
	    	axl += "<ENVELOPE minx=\"" + xmin
	    		+ "\" miny=\" " + ymin + "\" maxx=\"" 
	    		+ xmax + "\" maxy=\"" + ymax + "\" />";
	    	
	    	//<!---projection for Google Maps--Mercator --->
	    	//axl += "<FEATURECOORDSYS id="54004" /><FILTERCOORDSYS id="54004"  />
	        
	    	//<!---image width and height from above--->
	    	axl += "<IMAGESIZE height=\"" + height + "\" width=\"" + width + "\" />";
	    	
	    	axl += "<OUTPUT type=\"" + image_type + "\" />";
	    	axl += "</PROPERTIES></GET_IMAGE></REQUEST></ARCXML>"; 
			
	    	try{
		    	axl = postXML("http://collab.geog.washington.edu:8080/servlet/com.esri.esrimap.Esrimap?ServiceName=LIT_ImageMap1",
		    			axl);
		    		//System.out.println("---returned axl: " + axl);
				Document document = DocumentHelper.parseText(axl);
				Node output = document.selectSingleNode("/ARCXML/RESPONSE/IMAGE/OUTPUT");
                                image_url = output.valueOf("@url");
				System.out.println("---image url: " + image_url);
				if(image_url.indexOf(".png") > 0){
					String filename = image_url.substring(image_url.lastIndexOf("/")+1);
					System.out.println("---output file: " + outputpath + filename );
					File f = new File(outputpath + filename);
                                        if(!f.exists()){
                                            System.out.println("---file not there yet: " + outputpath + filename);
                                        }else{
                                            FileInputStream in = new FileInputStream(outputpath + filename);
                                            return_image = new byte[ (int)f.length() ];
                                            in.read( return_image );
                                            in.close();
                                        }
					
					//cache the image:
					if(return_image.length > 1){
						FileOutputStream fos = new FileOutputStream(tilepath + image_name);
						fos.write(return_image, 0, return_image.length);
						fos.close();
					}
				}
				
	    	}catch (Exception e){
	    		e.printStackTrace();
	    	}
		}
		
		//4. write back image
		response.setContentType("image/png");
		ServletOutputStream outServ = response.getOutputStream();
		outServ.write(return_image);
		outServ.flush();
		outServ.close();
		
	}  	
	
	public String postXML(String theURL, String theData) {
		String theResponse = "";
		String ln;
		try {

			URL imsURL = new URL(theURL);
			URLConnection connection = imsURL.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);

			BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(bos, "UTF8"));
			writer.write(theData, 0, theData.length());
			writer.flush();
			writer.close();

			BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(bis, "UTF8"));
			while ((ln = reader.readLine()) != null) {
				theResponse += ln;
			}
			reader.close();

		} catch(MalformedURLException mue) {
			theResponse = "MalformedURLException:: " + mue;
		} catch(IOException ioe) {
			theResponse = "IOException:: " + ioe;
		} catch(Exception e) {
			theResponse = "Exception:: " + e;
		}
		return(theResponse.trim());
	}
	
    /* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}   	  	    
}