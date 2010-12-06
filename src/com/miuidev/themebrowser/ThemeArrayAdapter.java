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

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.github.droidfu.widgets.WebImageView;

public class ThemeArrayAdapter extends ArrayAdapter<Theme> {

    private ArrayList<Theme> mItems;
    private Context mContext;

    public ThemeArrayAdapter(Context context, int textViewResourceId, ArrayList<Theme> items) {
        super(context, textViewResourceId, items);
        mItems = items;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	Log.d("getView", position + " " + convertView);
        View row = convertView;
        
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        	row = inflater.inflate(R.layout.theme_list_item, null);
        }
        
        
        Theme theme = mItems.get(position);
        
        if (theme != null) {
            TextView themeNameText = (TextView) row.findViewById(R.id.ThemeName);
            TextView themeAuthorText = (TextView) row.findViewById(R.id.ThemeAuthorValue);
            TextView themeVersionText = (TextView) row.findViewById(R.id.ThemeVersionValue);
            TextView themeSizeText = (TextView) row.findViewById(R.id.ThemeSizeValue);
            WebImageView themePreviewImage = (WebImageView) row.findViewById(R.id.ListPreviewImage);
            
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
            	themePreviewImage.setImageUrl(theme.getThemePreviewURL());
            	themePreviewImage.loadImage();
            }
        }
        return row;
    }
}
