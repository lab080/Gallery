package com.nikolas.gallery;

import java.util.ArrayList;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nikolas.gallery.ViewPagerActivity.SamplePagerAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * This class echoes a string called from JavaScript.
 */
public class gallery extends CordovaPlugin {
	Context context;
	
	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("echo")) {
            String message = args.getString(0); 
            this.echo(message, callbackContext);
            return true;
        }
        return false;
    }

    private void echo(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {   
        	ArrayList<String> array = null;
        	//Aggiungere i parametri da passare
        	array.add(new String("http://www.androidblog.it/wp-content/uploads/2014/06/Android1.jpg"));
        	
        	Intent i = new Intent(context, ViewPagerActivity.class);
        	i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	i.putStringArrayListExtra("json_array", array);
        	context.startActivity(i);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }
}