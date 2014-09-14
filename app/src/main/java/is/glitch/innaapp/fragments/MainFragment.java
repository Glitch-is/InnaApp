package is.glitch.innaapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import is.glitch.innaapp.API;
import is.glitch.innaapp.R;
import is.glitch.innaapp.User;

/**
 * Created by Nasir on 13/09/2014.
 */
public class MainFragment extends Fragment {

    private User user;

	public MainFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        Bundle data = getActivity().getIntent().getExtras();
        user = data.getParcelable("user");

        API a = new API(user);

        a.userInfo();

        /*TextView test = (EditText) rootView.findViewById(R.id.input_username);

        test.setText(s);*/



		return rootView;
	}
}