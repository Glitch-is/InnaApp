package is.glitch.innaapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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

/**
 * Created by glitch on 9/11/14.
 */
public class LoginManager extends AsyncTask<Void, Void, Void> {

    private RequestStates listener;
    private String username;
    private String password;
    private User user;

    public LoginManager(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setRequestStates(RequestStates listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        listener.onStart();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost oldInnaPost = new HttpPost("https://www.inna.is/login.jsp");

        HttpResponse response = null;

        List<NameValuePair> postData = new ArrayList<NameValuePair>(2);
        postData.add(new BasicNameValuePair("Kennitala", username));
        postData.add(new BasicNameValuePair("Lykilord", password));
        postData.add(new BasicNameValuePair("_ROWOPERATION", "Staðfesta"));
        try {
            oldInnaPost.setEntity(new UrlEncodedFormEntity(postData));

            response = httpClient.execute(oldInnaPost);
            String responseStr = EntityUtils.toString(response.getEntity());
            if(responseStr.contains("Innskráning tókst ekki")) {
                // Login failed
                Log.v("Inna", "Login failed!");
                user = null;
            }
            else
            {
                String sessionID = response.getHeaders("Set-Cookie")[0].getValue().split("=")[1].split(";")[0];
                HttpGet oldInnaGet = new HttpGet("https://www.inna.is/opna.jsp?adgangur=1");
                BasicCookieStore cookieStore = new BasicCookieStore();
                BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", sessionID);
                cookie.setPath("/");
                cookie.setDomain("www.inna.is");
                cookieStore.addCookie(cookie);
                httpClient.setCookieStore(cookieStore);

                response = httpClient.execute(oldInnaGet);
                responseStr = EntityUtils.toString(response.getEntity());
                String tokenURL = responseStr.split("'")[1];
                HttpGet newInnaGet = new HttpGet(tokenURL);
                cookieStore.clear();
                httpClient.setCookieStore(cookieStore);
                response = httpClient.execute(newInnaGet);
                sessionID = response.getHeaders("Set-Cookie")[0].getValue().split("=")[1].split(";")[0];

                BasicClientCookie newCookie = new BasicClientCookie("JSESSIONID", sessionID);
                newCookie.setPath("/");
                newCookie.setDomain("nam.inna.is");

                cookieStore.addCookie(newCookie);

                user = new User(username, sessionID);

            }
        } catch (IOException e) {
			Log.e("LoginManager", "Failed to connect to the internet");
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listener.onFinish(user);
        super.onPostExecute(aVoid);
    }

    public static interface RequestStates {
        public void onError(Exception e);

        public void onFinish(User result);

        public void onStart();
    }

}
