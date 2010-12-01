package com.miuidev.themebrowser;

import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MostDownloadedThemesActivity extends ListActivity {

	private ProgressDialog m_ProgressDialog = null;
    private ArrayList<Theme> m_themes = null;
    private ThemeArrayAdapter m_adapter;
    private Runnable viewThemes;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        String jString = "[{\"name\": \"Theme 1\", \"url\": \"http://www.savoxis.com/miuinix/swg-v1.2.zip\", \"version\": \"1.0\", \"developer\": \"n_i_x\", \"screenshots\": [\"http://a.imageshack.us/img291/56/snap20100826125837.png\", \"http://a.imageshack.us/img829/4162/snap20100826125826.png\"], \"thumbnail\": \"http://img829.imageshack.us/img829/1894/thumbnaild.png\", \"size\": \"3.9mb\"}]";
        m_themes = new ArrayList<Theme>();
        this.m_adapter = new ThemeArrayAdapter(this, R.layout.theme_list_item, m_themes);
                setListAdapter(this.m_adapter);
       
        viewThemes = new Runnable(){
            public void run() {
                getThemes();
            }
        };
    Thread thread =  new Thread(null, viewThemes, "MagentoBackground");
        thread.start();
        m_ProgressDialog = ProgressDialog.show(MostDownloadedThemesActivity.this,    
              "Please wait...", "Retrieving data ...", true);
    }

    private void getThemes(){
        try{
            m_themes = new ArrayList<Theme>();
            Theme t1 = new Theme();
            t1.setThemeName("Theme 1");
            t1.setThemeAuthor("n_i_x");
            t1.setThemeVersion("1.0");
            t1.setThemeSize("1.1mb");
            Theme t2 = new Theme();
            t2.setThemeName("Theme 2");
            t2.setThemeAuthor("n_i_x");
            t2.setThemeVersion("1.1");
            t2.setThemeSize("1.2mb");
            Theme t3 = new Theme();
            t3.setThemeName("Theme 3");
            t3.setThemeAuthor("n_i_x");
            t3.setThemeVersion("1.1");
            t3.setThemeSize("1.2mb");
            Theme t4 = new Theme();
            t4.setThemeName("Theme 4");
            t4.setThemeAuthor("n_i_x");
            t4.setThemeVersion("1.1");
            t4.setThemeSize("1.2mb");
            Theme t5 = new Theme();
            t5.setThemeName("Theme 5");
            t5.setThemeAuthor("n_i_x");
            t5.setThemeVersion("1.1");
            t5.setThemeSize("1.2mb");
            m_themes.add(t1);
            m_themes.add(t2);
            m_themes.add(t3);
            m_themes.add(t4);
            m_themes.add(t5);
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
                }
                return v;
        }
    }
}

