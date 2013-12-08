package com.moj.bcspreliminaryhelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	TextView tv,tv1;

	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		
		return true;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tv=(TextView)findViewById(R.id.textView1);
	    tv1=(TextView)findViewById(R.id.textView2);
		setContentView(R.layout.activity_main);
		
		
		//getMenuInflater().inflate(R.menu.main, menu);
		JSONArray jArray = null;
		String result = null;
		StringBuilder sb = null;
		InputStream is = null;


		
		//http post
		try{
		     HttpClient httpclient = new DefaultHttpClient();
		     ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		     //Why to use 10.0.2.2
		     HttpPost httppost = new HttpPost("http://127.0.0.1/BCShelper/myfile.php");
		     httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		     HttpResponse response = httpclient.execute(httppost);
		     HttpEntity entity = response.getEntity();
		     is = entity.getContent();
		     }catch(Exception e){
		         Log.e("log_tag", "Error in http connection"+e.toString());
		    }
		//convert response to string
		try{
		      BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),10);
		       sb = new StringBuilder();
		       sb.append(reader.readLine() + "\n");

		       String line="0";
		       while ((line = reader.readLine()) != null) {
		                      sb.append(line + "\n");
		        }
		        is.close();
		        result=sb.toString();
		        tv.setText(result);
		        }catch(Exception e){
		              Log.e("log_tag", "Error converting result "+e.toString());
		        }

		//String name;
		try{
		      jArray = new JSONArray(result);
		      JSONObject json_data=null;
		      String ct_name,lol = null;
		      
		      for(int i=0;i<jArray.length();i++){
		             json_data = jArray.getJSONObject(i);
		             ct_name=json_data.getString("name");
		             lol+=ct_name;//here "Name" is the column name in database
		         }
		      tv1.setText(lol);
		      }
		      catch(JSONException e1){
		       Toast.makeText(getBaseContext(), "No Data Found" ,Toast.LENGTH_LONG).show();
		      } catch (ParseException e1) {
		   e1.printStackTrace();
		 
	}
	}
	

}
