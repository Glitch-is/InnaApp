package is.glitch.innaapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by glitch on 9/11/14.
 */
public class Request extends AsyncTask<Void, Void, Void> {

    private RequestStates listener;
    private User user;
    private String URL;
    private List<NameValuePair> data;
    private String method;
    private String responseStr;

    public Request(User user, String URL, List<NameValuePair> data, String method) {
        this.user = user;
        this.URL = URL;
        this.data = data;
        this.method = method;
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
        DefaultHttpClient http = new DefaultHttpClient();

        if(method == "POST")
        {
            HttpPost post = new HttpPost(URL);
            HttpResponse response = null;

            try {
                if(data != null)
                    post.setEntity(new UrlEncodedFormEntity(data));
                BasicCookieStore store = new BasicCookieStore();
                BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", user.getSessionID());
                cookie.setDomain("nam.inna.is");
                cookie.setPath("/");
                store.addCookie(cookie);
                http.setCookieStore(store);
                response = http.execute(post);
                responseStr = EntityUtils.toString(response.getEntity());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            HttpGet get = new HttpGet(URL);
            HttpResponse response = null;

            try {
                BasicCookieStore store = new BasicCookieStore();
                BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", user.getSessionID());
                cookie.setDomain("nam.inna.is");
                cookie.setPath("/");
                store.addCookie(cookie);
                http.setCookieStore(store);
                response = http.execute(get);
                responseStr = EntityUtils.toString(response.getEntity());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        listener.onFinish(responseStr);
        super.onPostExecute(aVoid);
    }

    public static interface RequestStates {
        public void onError(Exception e);

        public void onFinish(String responseStr);

        public void onStart();
    }

}
