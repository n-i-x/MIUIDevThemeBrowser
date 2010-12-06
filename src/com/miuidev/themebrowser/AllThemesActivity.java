/**
 *  This file is part of MIUIDevThemeBrowser.
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.droidfu.widgets.WebImageView;
import com.google.gson.Gson;
import com.miuidev.themebrowser.RestClient.RequestMethod;

public class AllThemesActivity extends ListActivity {

	private ProgressDialog themeDownloadProgress = null;
	private ProgressDialog themeListDownloadProgress = null;
    private ArrayList<Theme> themeList = null;
    private ThemeArrayAdapter themeArrayAdapter;
    private Runnable viewThemes;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        themeList = new ArrayList<Theme>();
        this.themeArrayAdapter = new ThemeArrayAdapter(this, R.layout.theme_list_item, themeList);
        setListAdapter(this.themeArrayAdapter);
       
        viewThemes = new Runnable(){
            public void run() {
                getThemes();
            }
        };

        Thread thread =  new Thread(null, viewThemes, "ThemeListGrabber");
        thread.start();
        themeListDownloadProgress = ProgressDialog.show(AllThemesActivity.this,    
              getString(R.string.dialog_please_wait) + "...", getString(R.string.dialog_download_theme_list)  + "...", true);

    }

    private void getThemes(){
        try{
            themeList = new ArrayList<Theme>();
            
            Gson gson = new Gson();
            final String themeJSON = MIUIDevThemeBrowser.ALL_THEMES_JSON;
            RestClient client = new RestClient(themeJSON);
            client.Execute(RequestMethod.GET);
            ThemeList objs = gson.fromJson(client.getResponse(), ThemeList.class);
            for(Theme theme : objs.getThemes()){
            	// Protect against malformed JSON
            	if (theme != null) {
            		Log.d("MIUIDevThemeBrowser", "Adding theme " + theme.getThemeName() + " to list");
            		themeList.add(theme);
            	}
            }
            
            Thread.sleep(2000);
            Log.i("ARRAY", ""+ themeList.size());
          } catch (Exception e) {
        	  Log.e("BACKGROUND_PROC", e.getMessage());
          }
          runOnUiThread(returnRes);
      }

    private Runnable returnRes = new Runnable() {

        public void run() {
            if(themeList != null && themeList.size() > 0){
                themeArrayAdapter.notifyDataSetChanged();
                for(int i = 0; i < themeList.size(); i++)
                themeArrayAdapter.add(themeList.get(i));
            }
            themeListDownloadProgress.dismiss();
            themeArrayAdapter.notifyDataSetChanged();
        }
      };
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {


	    final Theme theme = themeList.get(position);
	    final String themeName = theme.getThemeName();
	    final String themeURL = theme.getThemeURL();
	    final String[] theme_screenshot_urls = theme.getThemeScreenshotURLs();

	    LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
	    View layoutDialog = inflater.inflate(R.layout.dialog_theme_preview,
	                                         (ViewGroup) findViewById(R.id.dialog_theme_preview_layout_root));

	    TextView text = (TextView) layoutDialog.findViewById(R.id.dialog_theme_preview_theme_name);
	    text.setText(themeName);
	    
	    for(int i = 0; i < theme_screenshot_urls.length; i++) {
	    	// Protect against malformed JSON
	    	if (theme_screenshot_urls[i] != null) {
		    	int resID = getResources().getIdentifier("ThemePreview" + i, "id", getPackageName());
	            WebImageView themePreviewImage = (WebImageView) layoutDialog.findViewById(resID);
	            themePreviewImage.setVisibility(View.VISIBLE);
	            themePreviewImage.setImageUrl(theme_screenshot_urls[i]);
	        	themePreviewImage.loadImage();
	    	}
	    }

	    AlertDialog.Builder builder;
	    AlertDialog alertDialog;
	    builder = new AlertDialog.Builder(AllThemesActivity.this);
	    builder.setView(layoutDialog);
	    builder.setTitle(getString(R.string.dialog_download_theme)).setCancelable(false)
	       .setPositiveButton(getString(R.string.download), new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                DownloadFileTask DownloadFile = new DownloadFileTask();
	                DownloadFile.execute(themeURL);
	                dialog.dismiss();
	           }
	       })
	       
	       .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                dialog.cancel();
	           }
	       });

	    alertDialog = builder.create();
	    alertDialog.show();
	    
    }

    private class DownloadFileTask extends AsyncTask<String, String, String>{

    	private final File SDCARD = Environment.getExternalStorageDirectory();
    	
        @Override
        protected String doInBackground(String... fileURL) {
            int count;

            try {
                URL url = new URL(fileURL[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.connect();

                int contentLength = connection.getContentLength();

                File outputDir = new File (SDCARD.getAbsoluteFile() + "/MIUI/theme");
                outputDir.mkdirs();
                String fileName = url.toString().substring( url.toString().lastIndexOf('/')+1, url.toString().length() );
                File outputFileName = new File(outputDir, fileName);
                FileOutputStream output = new FileOutputStream(outputFileName);
                
                InputStream input = connection.getInputStream();

                byte[] buffer = new byte[1024];

                long total = 0;

                while ((count = input.read(buffer)) != -1) {
                    total += count;
                    publishProgress(""+(int)total*100/contentLength);
                    output.write(buffer, 0, count);
                }

                output.flush();
                output.close();
                input.close();
            } catch (Exception e) {
            	Log.e("DownloadThemeTask", e.getMessage());
            }
            return null;
        }

        @Override
        public void onProgressUpdate(String... args) {
        	themeDownloadProgress.setProgress((int) Float.parseFloat(args[0]));
        }
        
        @Override
    	protected void onPreExecute() {
    		themeDownloadProgress = new ProgressDialog(AllThemesActivity.this);
    		themeDownloadProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    		themeDownloadProgress.setMessage(getString(R.string.dialog_downloading_theme) + "...");
    		themeDownloadProgress.setCancelable(false);
    		themeDownloadProgress.show();
    	}
        
        @Override
        protected void onPostExecute(String unused) {
        	themeDownloadProgress.dismiss();
        	Context context = getApplicationContext();
        	CharSequence text = "Theme downloaded to SD Card";
        	int duration = 3;

        	Toast toast = Toast.makeText(context, text, duration);
        	toast.show();
        }
    }
}