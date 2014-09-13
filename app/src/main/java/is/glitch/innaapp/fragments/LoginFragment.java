package is.glitch.innaapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import is.glitch.innaapp.LoginManager;
import is.glitch.innaapp.Main;
import is.glitch.innaapp.R;
import is.glitch.innaapp.User;

/**
 * Created by nasir on 10/09/14.
 */
public class LoginFragment extends Fragment {

	private View view = null;
	private Button loginSubmit = null;
    private EditText usernameInput = null;
    private EditText passwordInput = null;

	public LoginFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_login, container, false);

		loginSubmit = (Button) view.findViewById(R.id.button_login);
		usernameInput = (EditText) view.findViewById(R.id.input_username);
        passwordInput = (EditText) view.findViewById(R.id.input_password);

		loginSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final String username = usernameInput.getText().toString().trim();
				final String password = passwordInput.getText().toString();

                Login(username, password);
			}
		});

		return view;
	}


	public void Login(final String username, final String password) {
        LoginManager loginManager = new LoginManager(username, password);

        loginManager.setRequestStates(new LoginManager.RequestStates() {
	        @Override
	        public void onError(Exception e) {
		        e.printStackTrace();
	        }

	        @Override
	        public void onStart() {
		        // Disable input and show loader
		        usernameInput.setEnabled(false);
		        passwordInput.setEnabled(false);
		        loginSubmit.setEnabled(false);

		        usernameInput.setText(username);
		        passwordInput.setText(password);
	        }

	        @Override
	        public void onFinish(User user) {
		        if (user != null) {

			        Intent mainIntent = new Intent(getActivity(), Main.class)
					        .putExtra("user", user);
			        startActivity(mainIntent);
			        getActivity().finish();
		        } else {
			        usernameInput.setError("Invalid Credentials");
			        usernameInput.requestFocus();
		        }

		        usernameInput.setEnabled(true);
		        passwordInput.setEnabled(true);
		        loginSubmit.setEnabled(true);
	        }
        });
		loginManager.execute();

	}
}