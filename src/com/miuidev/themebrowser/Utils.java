package com.miuidev.themebrowser;

import android.content.Context;

public class Utils {

    public static String formatSize(Context mContext, Float sizeInBytes) {

    	String[] sizes = mContext.getResources().getStringArray(R.array.sizes);
    	String readableFileSize = null;
    
    	for(String size : sizes) {
    		if (sizeInBytes < 1024) {
    			readableFileSize = String.format("%.2f%s", sizeInBytes, size);
    			break;
    		}
    	
    		sizeInBytes /= 1024;
    	}
    	
    	return readableFileSize;
    }
}
