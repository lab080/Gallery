/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.nikolas.gallery;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import com.nikolas.gallery.PhotoView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.util.Log;

/**
 * Lock/Unlock button is added to the ActionBar.
 * Use it to temporarily disable ViewPager navigation in order to correctly interact with ImageView by gestures.
 * Lock/Unlock state of ViewPager is saved and restored on configuration changes.
 * 
 * Julia Zudikova
 */

public class ViewPagerActivity extends Activity {

	private static final String ISLOCKED_ARG = "isLocked";
	
	private ViewPager mViewPager;
	private MenuItem menuLockItem;
	private static String[] URL = new String[3];
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.activity_view_pager);
        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);

		mViewPager.setAdapter(new SamplePagerAdapter());

		if (savedInstanceState != null) {
			boolean isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG, false);
			((HackyViewPager) mViewPager).setLocked(isLocked);
		}
	}

	static class SamplePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			//Preparazione array 
			URL[0] = "http://www.androidblog.it/wp-content/uploads/2014/06/Android1.jpg";
			URL[1] = "http://www.techfest.org/Img/android.jpg";
			URL[2] = "http://wp-up.s3.amazonaws.com/aw/2014/01/AndroidWallpaper.jpg";

			//Intent intent = null;
			//ArrayList<String> json_data = (ArrayList<String>) intent.getSerializableExtra("String");
			//URL = json_data.toArray(new String[json_data.size()]);
			
			return URL.length;
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			
			//photoView.setImageResource(sDrawables[position]);
			Log.i("ViewPagerActivity", "Sta caricando l'immagine : "+URL[position]);
			
			Drawable drawable = creaImmagineDaUrl(URL[position]);
			
			photoView.setImageDrawable(drawable);

			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.viewpager_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menuLockItem = menu.findItem(R.id.menu_lock);
        toggleLockBtnTitle();
        menuLockItem.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				toggleViewPagerScrolling();
				toggleLockBtnTitle();
				return true;
			}
		});

        return super.onPrepareOptionsMenu(menu);
    }
    
    private void toggleViewPagerScrolling() {
    	if (isViewPagerActive()) {
    		((HackyViewPager) mViewPager).toggleLock();
    	}
    }
    
    private void toggleLockBtnTitle() {
    	boolean isLocked = false;
    	if (isViewPagerActive()) {
    		isLocked = ((HackyViewPager) mViewPager).isLocked();
    	}
    	String title = (isLocked) ? getString(R.string.menu_unlock) : getString(R.string.menu_lock);
    	if (menuLockItem != null) {
    		menuLockItem.setTitle(title);
    	}
    }

    private boolean isViewPagerActive() {
    	return (mViewPager != null && mViewPager instanceof HackyViewPager);
    }
    
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		if (isViewPagerActive()) {
			outState.putBoolean(ISLOCKED_ARG, ((HackyViewPager) mViewPager).isLocked());
    	}
		super.onSaveInstanceState(outState);
	}
	
	static Drawable creaImmagineDaUrl(String url)
    {
         StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

	 StrictMode.setThreadPolicy(policy); 
	 
         try
         {
             InputStream is = (InputStream) new URL(url).getContent();
             Drawable d = Drawable.createFromStream(is, null);
             Log.i("ViewPagerActivity", "L'immagine è caricata : "+d);
             return d;
         }catch (Exception e) {
             System.out.println("Exc="+e);
             Log.i("ViewPagerActivity", "L'immagine NON e stata caricata : "+e);
             return null;
         }
     }
    
}
