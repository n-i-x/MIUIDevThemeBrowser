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

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends TabActivity {

	public static final String BASEURL = "http://themes.miui-themes.com";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
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

}

