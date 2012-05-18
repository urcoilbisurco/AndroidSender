package com.sender.sender;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.sender.sender.utils.HttpRequests;
import com.sender.sender.utils.Preferences;
public class GetLinkActivity extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive);
        Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		String url = extras.getString("android.intent.extra.TEXT");
        if (url!=null){
        	//get auth from sharedpreferences
        	String auth = Preferences.getAuthPref(getApplicationContext());
        	if(auth=="noauth"){
        		android.util.Log.i("receiver", "the user is not logged in!");
        		Toast.makeText(getApplicationContext(), "Go to the app and signup!" ,Toast.LENGTH_LONG).show();
        	}else{
        	android.util.Log.i("receiver", auth);
        	Toast.makeText(getApplicationContext(), "i'm making the transfert..." ,Toast.LENGTH_LONG).show();
        	HttpRequests.doUrlPost(url, auth);
        	android.util.Log.i("receiver", url);
        	}
        
        }else{
        	Toast.makeText(getApplicationContext(),"sorry, something went wrong.",Toast.LENGTH_LONG).show();
        	finish();
        	android.util.Log.i("receiver", extras.toString());
        }
        finish();
        }
    
   
}