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

	private View view = null;
	private EditText usernameInput = null;

	public LoginFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_login, container, false);

		usernameInput = (EditText) view.findViewById(R.id.input_username);

		return view;
	}


	public void Login() {
		String usernameTxt = usernameInput.getText().toString();
		Log.v("Inna", usernameTxt);
	}
}