package is.glitch.innaapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import is.glitch.innaapp.R;

/**
 * Created by nasir on 10/09/14.
 */
public class LoginFragment extends Fragment {

	public LoginFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_login, container, false);
		return rootView;
	}

    public void Login(View view)
    {
        EditText user = (EditText) getView().findViewById(R.id.username_input);
        String name = user.getText().toString();
        Log.v("Inna", name);
    }

}