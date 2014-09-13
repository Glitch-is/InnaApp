package is.glitch.innaapp;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by glitch on 9/13/14.
 */
public class API {

    public static String PostRequest(User user, String URL, List<NameValuePair> data) {
        DefaultHttpClient http = new DefaultHttpClient();
        HttpPost post = new HttpPost(URL);

        try {
            post.setEntity(new UrlEncodedFormEntity(data));
            HttpResponse response = http.execute(post);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e)
        {
            Log.v("Inna API", e.getMessage());
            return e.getMessage();
        }
    }

    public static String GetRequest(User user, String URL)
    {
        DefaultHttpClient http = new DefaultHttpClient();
        HttpGet get = new HttpGet(URL);

        try {
            HttpResponse response = http.execute(get);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e)
        {
            Log.v("Inna API", e.getMessage());
            return e.getMessage();
        }
    }


    public static String userInfo(User user)
    {
        return GetRequest(user, "https://nam.inna.is/inna11/api/UserData/GetLoggedInUser");
    }
}

