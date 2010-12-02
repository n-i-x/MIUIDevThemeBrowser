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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.miuidev.themebrowser.RestClient.RequestMethod;

public class RecommendedThemesActivity extends ListActivity {

	private ProgressDialog m_ProgressDialog = null;
    private ArrayList<Theme> m_themes = null;
    private ThemeArrayAdapter m_adapter;
    private Runnable viewThemes;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        m_themes = new ArrayList<Theme>();
        this.m_adapter = new ThemeArrayAdapter(this, R.layout.theme_list_item, m_themes);
        setListAdapter(this.m_adapter);
       
        viewThemes = new Runnable(){
            public void run() {
                getThemes();
            }
        };

        Thread thread =  new Thread(null, viewThemes, "ThemeListGrabber");
        thread.start();
        m_ProgressDialog = ProgressDialog.show(RecommendedThemesActivity.this,    
              "Please wait...", "Downloading Theme list...", true);

    }

    private void getThemes(){
        try{
            m_themes = new ArrayList<Theme>();
            
            Log.i("ThemeGrabber", "Json Parser started..");
            Gson gson = new Gson();
            final String themeJSON = "http://www.miui-dev.com/themes.json?list=top_picks";
            RestClient client = new RestClient(themeJSON);
            client.Execute(RequestMethod.GET);
            Log.i("ThemeGrabber", client.toString());
            Log.i("ThemeGrabber", "Received: " + client.getResponse());
            ThemeList objs = gson.fromJson(client.getResponse(), ThemeList.class);
            Log.i("ThemeGrabber", ""+objs.getThemes().size());
            for(Theme theme : objs.getThemes()){
            	Log.i("ThemeGrabber", theme.getThemeName() + " - " + theme.getThemeURL());
            	m_themes.add(theme);
            }
            
            Thread.sleep(2000);
            Log.i("ARRAY", ""+ m_themes.size());
          } catch (Exception e) {
            Log.e("BACKGROUND_PROC", e.getMessage());
          }
          runOnUiThread(returnRes);
      }

    private Runnable returnRes = new Runnable() {

        public void run() {
            if(m_themes != null && m_themes.size() > 0){
                m_adapter.notifyDataSetChanged();
                for(int i=0;i<m_themes.size();i++)
                m_adapter.add(m_themes.get(i));
            }
            m_ProgressDialog.dismiss();
            m_adapter.notifyDataSetChanged();
        }
      };

    private class ThemeArrayAdapter extends ArrayAdapter<Theme> {

        private ArrayList<Theme> items;

        public ThemeArrayAdapter(Context context, int textViewResourceId, ArrayList<Theme> items) {
            super(context, textViewResourceId, items);
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.theme_list_item, null);
            }
            Theme theme = items.get(position);
            if (theme != null) {
                TextView themeNameText = (TextView) v.findViewById(R.id.ThemeName);
                TextView themeAuthorText = (TextView) v.findViewById(R.id.ThemeAuthorValue);
                TextView themeVersionText = (TextView) v.findViewById(R.id.ThemeVersionValue);
                TextView themeSizeText = (TextView) v.findViewById(R.id.ThemeSizeValue);
                ImageView themePreviewImage = (ImageView) v.findViewById(R.id.ImageView01);
                if (themeNameText != null) {
                      themeNameText.setText(theme.getThemeName());
                }
                if(themeAuthorText != null){
                      themeAuthorText.setText(theme.getThemeAuthor());
                }
                if(themeVersionText != null){
                    themeVersionText.setText(theme.getThemeVersion());
                }
                if(themeSizeText != null){
                    themeSizeText.setText(theme.getThemeSize());
                }
                if(theme.getThemePreviewURL() != null){
                	BitmapFactory.Options bmOptions;
                    bmOptions = new BitmapFactory.Options();
                    bmOptions.inSampleSize = 1;
                    Bitmap bm = LoadImage(theme.getThemePreviewURL(), bmOptions);
                	themePreviewImage.setImageBitmap(bm);
                }
            }
            return v;
        }
    }
    
    public Bitmap LoadImage(String URL, BitmapFactory.Options options) {       
		Bitmap bitmap = null;
		InputStream in = null;       
			try {
				in = OpenHttpConnection(URL);
		        bitmap = BitmapFactory.decodeStream(in, null, options);
		        in.close();
		    } catch (IOException e1) {
		    }
		    return bitmap;               
    }
    
    public InputStream OpenHttpConnection(String strURL) throws IOException {
    	InputStream inputStream = null;
    	URL url = new URL(strURL);
    	URLConnection conn = url.openConnection();

    	try {
    		HttpURLConnection httpConn = (HttpURLConnection)conn;
    		httpConn.setRequestMethod("GET");
    		httpConn.connect();

    		if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
    			inputStream = httpConn.getInputStream();
    		}
    	} catch (Exception ex) {}
    	return inputStream;
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

	    Theme theme = m_themes.get(position);
	    String themeName = theme.getThemeName();
	    
	    AlertDialog.Builder builder;
	    AlertDialog alertDialog;

	    LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
	    View layout = inflater.inflate(R.layout.dialog_theme_preview,
	                                   (ViewGroup) findViewById(R.id.dialog_theme_preview_layout_root));

	    TextView text = (TextView) layout.findViewById(R.id.dialog_theme_preview_theme_name);
	    text.setText(themeName);

	    builder = new AlertDialog.Builder(this);
	    builder.setView(layout);
	    builder.setTitle("Download Theme").setCancelable(false)
	       .setPositiveButton("Download", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                dialog.cancel();
	           }
	       })
	       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                dialog.cancel();
	           }
	       });

	    alertDialog = builder.create();
	    alertDialog.show();
	    
    }
}

