package com.sender.sender;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import com.sender.sender.utils.HttpRequests;
import com.sender.sender.utils.Md5;
import com.sender.sender.utils.Preferences;
public class SenderActivity extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	android.util.Log.i("sender", "non sono ancora stato invocato!");
    	  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    public void submitButton(View view) {
    	android.util.Log.i("sender", "sono invocato!");
    	EditText emailText = (EditText) findViewById(R.id.emailEditText);
    	EditText passwordText= (EditText) findViewById(R.id.passwordEditText);
    	String email=emailText.getText().toString();

    	//store somewhere email and md5
    	String auth = Md5.create(email + passwordText.getText().toString());
    	Preferences.setEmailPref(getApplicationContext(), email);
    	android.util.Log.i("sender", auth);
    	Preferences.setAuthPref(getApplicationContext(), auth);
    	android.util.Log.i("sender", Preferences.getAuthPref(getApplicationContext()));
    	
    	HttpRequests.doAuthPost(email , auth);
    	finish();
    }
    
}