/*
This class is borrowed from ESRI code template, so the copyright goes to ESRI.

COPYRIGHT 1992-2003 ESRI

TRADE SECRETS: ESRI PROPRIETARY AND CONFIDENTIAL
Unpublished material - all rights reserved under the
Copyright Laws of the United States.

For additional information, contact:
Environmental Systems Research Institute, Inc.
Attn: Contracts Dept
380 New York Street
Redlands, California, USA 92373

email: contracts@esri.com
*/
package org.pgist.util;


public class Properties
{
  public static java.util.Hashtable getProp(String fname) {
   try {
     String fileName = "/" + fname.trim();
     java.util.Properties props = new java.util.Properties();
     ClassLoader cl = Thread.currentThread().getContextClassLoader();
     java.io.InputStream in = cl.getResourceAsStream(fileName);
     if (in == null) {
       Class c = Class.forName("geog465.util.Properties");
       in = c.getResourceAsStream(fileName);
     }
     props.load(in);
     return props;
   }
   catch (Exception ie) {
     System.out.println("\"" + fname + "\" file cannot be loaded or not exist ");
     return null;
   }
 }
}
