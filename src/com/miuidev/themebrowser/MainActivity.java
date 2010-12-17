/**
 * This file is part of MIUIDevThemeBrowser.
 *
 *  MIUIDevThemeBrowser is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MIUIDevThemeBrowser is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MIUIDevThemeBrowser.  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  Copyright (c) 2010 miui-dev.com, All rights reserved.
 *  Author: n_i_x (nix@miui-dev.com)
 *  Website: http://www.miui-dev.com
 */

package com.miuidev.themebrowser;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.util.ByteArrayBuffer;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends TabActivity {
	private Handler mHandler;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);

        mHandler = new Handler();
        checkUpdate.start();

        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;

        intent = new Intent().setClass(this, FeaturedThemesActivity.class);
        spec = tabHost.newTabSpec("featuredthemes").setIndicator(getString(R.string.tab_featured_themes)).setContent(intent);
        tabHost.addTab(spec);
        
        intent = new Intent().setClass(this, AllThemesActivity.class);
        spec = tabHost.newTabSpec("allthemes").setIndicator(getString(R.string.tab_all_themes)).setContent(intent);
        tabHost.addTab(spec);

    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    @Override
    public void onPause() {
    	mHandler = null;
    	super.onPause();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.MenuThemeManager:
        	final Intent intentThemeManager = new Intent("android.intent.action.MAIN");
        	intentThemeManager.setComponent(new ComponentName("com.android.thememanager",
        			"com.android.thememanager.ThemeSettingsActivity"));
        	try {
        		startActivity(intentThemeManager);
        	} catch (ActivityNotFoundException e) {
        		Context context = getApplicationContext();
        		CharSequence text = getString(R.string.theme_manager_not_found);
        		int duration = Toast.LENGTH_SHORT;

        		Toast toast = Toast.makeText(context, text, duration);
        		toast.show();
        	}
        	return true;
        case R.id.MenuAbout:
            displayAbout();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    private void displayAbout() {
    	AlertDialog.Builder builder;
    	AlertDialog alertDialog;

    	LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
    	View layout = inflater.inflate(R.layout.dialog_about,
    	                               (ViewGroup) findViewById(R.id.DialogAboutRelativeLayout));

    	TextView text = (TextView) layout.findViewById(R.id.AboutVersionValue);
    	text.setText(getVersionName());

	    builder = new AlertDialog.Builder(this);
	    builder.setView(layout);
	    builder.setCancelable(false)
	       .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                dialog.dismiss();
	           }
	       });
	    alertDialog = builder.create();
	    alertDialog.show();
		
	}

	private String getVersionName() {
        String version = "";
        try {
          PackageInfo pi = getPackageManager().getPackageInfo(getPackageName(), 0);
          version = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
          version = "Package name not found";
        }
        return version;
      }
	
    private Thread checkUpdate = new Thread() {
    	public void run() {
    		try {
    			URL updateURL = new URL("http://www.miui-themes.com/appversion");
    			URLConnection conn = updateURL.openConnection();
    			InputStream is = conn.getInputStream();
    			BufferedInputStream bis = new BufferedInputStream(is);
    			ByteArrayBuffer baf = new ByteArrayBuffer(50);
    			int current = 0;
    			while ((current = bis.read()) != -1) {
    				baf.append((byte)current);
    			}
    			/* Convert the Bytes read to a String. */
    			final String s = new String(baf.toByteArray());
    			
    			/* Get current Version Number */
    			int curVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
    			int newVersion = Integer.valueOf(s);
    			/* Is a higher version than the current already out? */
    			if (newVersion > curVersion) {
	    			/* Post a Handler for the UI to pick up and open the Dialog */
    				if (mHandler != null) {
    					mHandler.post(showUpdate);
    				}
    			}
    		} catch (Exception e) {
    			
    		}
    	}
    };
    
    /* This Runnable creates a Dialog and asks the user to open the Market */
    private Runnable showUpdate = new Runnable(){
    	public void run() {
    		new AlertDialog.Builder(MainActivity.this)
    		.setIcon(R.drawable.icon)
    		.setTitle(getString(R.string.update_available))
    		.setMessage(getString(R.string.update_available_detail))
    		.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int whichButton) {
    				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://bit.ly/eoVaZn"));
    				startActivity(intent);
    			}
    		})
    		.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int whichButton) {
    				/* User clicked Cancel */
    			}
    		})
    		.show();
    	}
    };
}

