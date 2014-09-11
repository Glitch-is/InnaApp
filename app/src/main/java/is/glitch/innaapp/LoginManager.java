package is.glitch.innaapp;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by glitch on 9/11/14.
 */
public class LoginManager extends AsyncTask<Void, Void, Void> {

    private String responseStr;
    private String username;
    private String password;

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
        HttpClient httpClient = new DefaultHttpClient();
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
        } catch (IOException e) {
            responseStr = "Failed to send request";
        }
        Log.v("Inna", responseStr);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

}
