package is.glitch.innaapp;

import android.content.Intent;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import is.glitch.innaapp.activities.MainActivity;

/**
 * Created by glitch on 9/13/14.
 */
public class API {

    private String responseStr;
    private User user;

    public interface Command {
        void execute();
    }

    public API(User user)
    {
        this.user = user;
    }

    public void Request(User user, String URL, List<NameValuePair> data, String method, final Command cmd) {
        Request r = new Request(user, URL, data, method);

        r.setRequestStates(new Request.RequestStates() {
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onFinish(String rStr) {
                responseStr = rStr;
                cmd.execute();
            }
        });
        r.execute();
    }


    public void userInfo()
    {
        Request(user, "https://nam.inna.is/inna11/api/UserData/GetLoggedInUser", null, "GET", new userInfo_done());
    }

    public class userInfo_done implements Command
    {
        public void execute()
        {
            Log.v("userInfo", responseStr);
            try {
                JSONObject obj = new JSONObject(responseStr);
                user.setStudentID(obj.getString("studentId"));
                scheduleWeek();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void scheduleWeek()
    {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        Calendar today = Calendar.getInstance();
        String start = df.format(today.getTime());
        today.add(Calendar.DATE, 6);
        String end = df.format(today.getTime());
        Log.v("vars", start + " : " + end + " : " + user.getStudentID());
        String url = "https://nam.inna.is/inna11/api/Timetable/GetTimetable?attendanceOverview=&class_id=&classroom_id=&date_from="+start+"&date_to="+end+"&group_id=&module_id=&staff_id=&student_id="+user.getStudentID()+"&terms=";
        Log.v("URL", url);
        Request(user, url, null, "GET", new scheduleWeek_done());
    }

    public class scheduleWeek_done implements Command
    {
        public void execute()
        {
            Log.v("scheduleWeek", responseStr);
        }
    }
}

