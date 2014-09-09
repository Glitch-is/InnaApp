package is.glitch.innaapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class LoginManager {
	
	private CloseableHttpClient httpclient = HttpClients.createDefault();
	private HttpClientContext context = HttpClientContext.create();
	private CookieStore cookieStore = new BasicCookieStore();
	
	public LoginManager(String kennitala, String password)
	{
		attemptLogin(kennitala, password);
	}
	
	public void attemptLogin(String kennitala, String password)
	{
		
		HttpPost oldInna = new HttpPost("https://www.inna.is/login.jsp");
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("Kennitala", kennitala));
		nvps.add(new BasicNameValuePair("Lykilord", password));
		nvps.add(new BasicNameValuePair("_ROWOPERATION", "Staðfesta"));

		/**
		* UrlEncodedFormEntity encodes form parameters and produce an
		* output like param1=value1&param2=value2
		*/
		try {
			oldInna.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		// Executing the request.
		try {
			CloseableHttpResponse response = httpclient.execute(oldInna);
			HttpEntity entity = response.getEntity();
			System.out.println(entity.getContent().toString());
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
