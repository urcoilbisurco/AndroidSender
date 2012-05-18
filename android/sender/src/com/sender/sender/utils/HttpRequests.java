package com.sender.sender.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;


public class HttpRequests {
	public static void doAuthPost(String email, String auth){
		android.util.Log.i("sender", "preparo post per /api/auth");
    	String call="auth";
        List<NameValuePair> postData = new ArrayList<NameValuePair>(2);
        postData.add(new BasicNameValuePair("email", email ));
        postData.add(new BasicNameValuePair("auth", auth ));
        doPost(call, postData);
        }
	public static  void doUrlPost(String url, String auth){
		android.util.Log.i("sender", "preparo post per /api/message");
        String call="message";
		List<NameValuePair> postData = new ArrayList<NameValuePair>(2);
		postData.add(new BasicNameValuePair("url", url ));
		postData.add(new BasicNameValuePair("auth", auth ));
		doPost(call, postData);
	}
	public static void doPost(String call, List<NameValuePair> data){
		android.util.Log.i("sender", "chiamata al server...");
    	HttpParams params = new BasicHttpParams();
        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpClient httpclient = new DefaultHttpClient(params);
        
        HttpPost httppost = new HttpPost("http://growing-autumn-1715.herokuapp.com/api/" + call);

        try {
            // Add your data
            httppost.setEntity(new UrlEncodedFormEntity(data));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            android.util.Log.i("sender", "chiamata effettuata!");
            
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
	}
}
