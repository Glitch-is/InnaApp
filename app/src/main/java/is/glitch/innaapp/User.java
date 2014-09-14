package is.glitch.innaapp;

/**
 * Created by glitch on 9/12/14.
 */
import android.os.Parcel;
import android.os.Parcelable;

import org.apache.http.impl.client.BasicCookieStore;

public class User implements Parcelable {
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel pc) {
            return new User(pc);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private final String LOG_TAG = "Inna";
    private String Username;
    private String SessionID;
    private BasicCookieStore Cookie;

    public User(String username, String sessionID, BasicCookieStore cookie) {
        this.Username = username;
        this.SessionID = sessionID;
        this.Cookie = cookie;
    }

    public User(Parcel pc) {
        Username = pc.readString();
        SessionID = pc.readString();
    }

    public String getUsername() {
        return Username;
    }

    public String getSessionID() { return SessionID; }

    public BasicCookieStore getCookie() { return Cookie; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pc, int flags) {
        pc.writeString(Username);
        pc.writeString(SessionID);
    }

}
