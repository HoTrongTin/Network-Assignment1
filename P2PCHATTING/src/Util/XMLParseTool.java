package Util;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import basic.Protocol;
public class XMLParseTool {
	
	public static boolean isXMLCode(String s)
	{
		if (s.contains(Protocol.XMLHEAD))
			return true;
		else
			return false;
	}
	public static Document genXMLDocument(String s)
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
	    DocumentBuilder builder;  
	    try  
	    {  
	        builder = factory.newDocumentBuilder();  
	        Document document = builder.parse( new InputSource( new StringReader(s ) ) );
	        return document;
	    } catch (Exception e) {  
	    		       
	        return null;
	    } 
	    
	}
}
