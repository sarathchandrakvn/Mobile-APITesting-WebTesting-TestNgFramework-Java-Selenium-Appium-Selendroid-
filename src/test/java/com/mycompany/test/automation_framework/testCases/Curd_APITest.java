/**
 * 
 */
package com.mycompany.test.automation_framework.testCases;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mycompany.test.automation_framework.utils.Constants;
 
/**
 * @author Sarath
 * Email Sarath.qaengineer@gmail.com
 */
public class Curd_APITest {

	private HttpURLConnection con;
	private JSONObject request_body;
	private JSONObject response_body;
	private String line;
	private StringBuilder sb;
	private URL url;
	private BufferedReader br;
	private OutputStreamWriter wr;
	private int response_code;
	private static final Logger log= Logger.getLogger(Curd_APITest.class);
	
	@BeforeTest
	public void init()
	{
		//Initialize variables
	    request_body = new JSONObject();
	    response_body = new JSONObject();
		line = null;
		response_code=0;
		url = null;
	    sb = new StringBuilder();
  		//construct Json request_body 
		request_body.put("Key", "Value");
		request_body.put("Key", "Value");
		request_body.put("Key", "Value");
 	
 	}
 
	@Test
	public void testAPIS() throws IOException
	{
		try
		{
			
				url = new URL(Constants.url);
				con = (HttpURLConnection) url.openConnection();
				//Get - Delete - Put - Post - Patch
				con.setRequestMethod("POST");
				con.setReadTimeout(5000); // 5 seconds timeout
				con.setRequestProperty("Content-Type", "application/json");
				con.setDoOutput(Boolean.TRUE);
				con.setDoInput(Boolean.TRUE);
				//write request_body Json
				wr =  new OutputStreamWriter(con.getOutputStream());
				wr.write(request_body.toString());
				wr.flush();
				response_code = con.getResponseCode();
				log.info("API Response code =" +response_code);
				
		}
		catch(Exception e)
		{
				//Handle exception
				e.printStackTrace();
		}
		
		try
		{
				Assert.assertEquals(response_code, 200);
		}
		catch(AssertionError e)
		{
				// Handle assertion error.
				log.error(e.toString());
			
		}
		
		try
		{
			
		 
		if (200 <= response_code && response_code <= 299) 
		   //HTTP.OK
				br  = new BufferedReader(new InputStreamReader(con.getInputStream()));
		else  
				br = new BufferedReader(new InputStreamReader((con.getErrorStream())));
		
		while((line=br.readLine())!=null)
		{
			sb.append(line+"\n");
		}
        br.close();
        response_body = new JSONObject(sb.toString());
        //log response_body Json
        log.info(response_body);
			
		}
		catch(Exception e)
		{
			 // Handle Exception
			 	log.error(e.toString());
		}
	}
	
	@AfterTest
	public void tearDown() throws IOException
	{
		br.close();
		con.disconnect();
	}
}
