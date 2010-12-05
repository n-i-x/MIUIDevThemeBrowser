package com.miuidev.themebrowser;

import java.util.ArrayList;

import android.content.Context;
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
        View view = convertView;
        
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.theme_list_item, null);
        }
        
        
        Theme theme = mItems.get(position);
        
        if (theme != null) {
            TextView themeNameText = (TextView) view.findViewById(R.id.ThemeName);
            TextView themeAuthorText = (TextView) view.findViewById(R.id.ThemeAuthorValue);
            TextView themeVersionText = (TextView) view.findViewById(R.id.ThemeVersionValue);
            TextView themeSizeText = (TextView) view.findViewById(R.id.ThemeSizeValue);
            WebImageView themePreviewImage = (WebImageView) view.findViewById(R.id.ListPreviewImage);
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
        return view;
    }
}
