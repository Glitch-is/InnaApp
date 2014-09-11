package is.glitch.innaapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import is.glitch.innaapp.LoginManager;

import is.glitch.innaapp.R;

/**
 * Created by nasir on 10/09/14.
 */
public class LoginFragment extends Fragment {

	private View view = null;
    private EditText usernameInput = null;
    private EditText passwordInput = null;

	public LoginFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_login, container, false);

		usernameInput = (EditText) view.findViewById(R.id.input_username);
        passwordInput = (EditText) view.findViewById(R.id.input_password);

		return view;
	}


	public void Login(View view) {
		String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        LoginManager l = new LoginManager(username, password);
	}
}