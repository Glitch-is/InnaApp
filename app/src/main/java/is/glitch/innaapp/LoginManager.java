package is.glitch.innaapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import is.glitch.innaapp.User;

/**
 * Created by glitch on 9/11/14.
 */
public class LoginManager extends AsyncTask<Void, Void, Void> {

    private String responseStr;
    private String username;
    private String password;
    private User user;

    public LoginManager(String username, String password) {
        this.username = username;
        this.password = password;
        execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost("https://www.inna.is/login.jsp");

        HttpResponse response = null;

        List<NameValuePair> postData = new ArrayList<NameValuePair>(2);
        postData.add(new BasicNameValuePair("Kennitala", username));
        postData.add(new BasicNameValuePair("Lykilord", password));
        postData.add(new BasicNameValuePair("_ROWOPERATION", "Staðfesta"));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(postData));

            response = httpClient.execute(httpPost);
            responseStr = EntityUtils.toString(response.getEntity());
            if(responseStr.contains("Innskráning tókst ekki")) {
                // Login failed
                Log.v("Inna", "Login failed!");
            }
            else
            {
                String sessionID = response.getHeaders("Set-Cookie")[0].getValue().split("=")[1].split(";")[0];
                HttpGet httpget = new HttpGet("https://www.inna.is/opna.jsp?adgangur=1");
                BasicCookieStore cookieStore = new BasicCookieStore();
                BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", sessionID); // They do not expect the cookies like this for some reason...
                cookieStore.addCookie(cookie);
                httpClient.setCookieStore(cookieStore);

                response = httpClient.execute(httpget);
                responseStr = EntityUtils.toString(response.getEntity());
                Log.v("Inna", responseStr);
            }
        } catch (IOException e) {
            responseStr = "Failed to send request";
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

}
