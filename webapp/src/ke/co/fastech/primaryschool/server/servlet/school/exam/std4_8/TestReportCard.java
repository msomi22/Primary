/**
 * 
 */
package ke.co.fastech.primaryschool.server.servlet.school.exam.std4_8;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.junit.Test;

/**
 * @author peter
 *
 */
public class TestReportCard {


	final String CGI_URL = "http://localhost:8080/Primary/primary/reportCard";

	final String ACCOUNTUUID = "9DEDDC49-444E-499B-BDB9-D6625D2F79F4";
	final String STREAMUUID = "2B0F8F79-DEF2-419D-9A76-17450B5CF768";

	/**
	 * Test method for {@link ke.co.fastech.primaryschool.server.servlet.school.exam.std4_8.ReportCard#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)}.
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public final void testDoPostHttpServletRequestHttpServletResponse()  { 
		    try {
				System.out.println("response is :\n" + 
				getResponse(CGI_URL + "?" + "accountuuid=" + URLEncoder.encode(ACCOUNTUUID,"UTF-8") + "&" +"streamuuid=" + URLEncoder.encode(STREAMUUID,"UTF-8") )); 
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				fail("Test to get result for account uuid " + ACCOUNTUUID + " and stream " + STREAMUUID);
	        	
			}
		    //file:///home/peter/test/form.html?phone=0718953974&email=mwendapeter72%40gmail.com&major=computer+science&units=7&submit=Register
	}

	private String getResponse(String urlStr) {		
        URLConnection conn;
        URL url;
        BufferedReader reader;
		String line;
		StringBuffer stringBuff = new StringBuffer();
		
		try {            
            url = new URL(urlStr);
            conn = url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true); 
            
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
            while( (line = reader.readLine()) != null) {
            	stringBuff.append(line);
            }
            
            reader.close();
            
        } catch(MalformedURLException e) {
            System.err.println("MalformedURLException exception");
            e.printStackTrace();
            
        } catch(IOException e) {
            System.err.println("IOException exception");
            e.printStackTrace();
        }
        
		return stringBuff.toString();
	}
	
}
