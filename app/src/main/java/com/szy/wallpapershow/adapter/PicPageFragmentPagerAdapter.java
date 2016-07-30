package com.szy.wallpapershow.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;

import com.szy.wallpapershow.fragment.PicPageFragment;

import java.util.List;

/**
 * Created by szjlc on 16/7/29.
 */
public class PicPageFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private List<PicPageFragment> fragmentList;
    public PicPageFragmentPagerAdapter(FragmentManager fm,Context context,List<PicPageFragment> list) {
        super(fm);
        this.context = context;
        this.fragmentList = list;
    }

    @Override
    public Fragment getItem(int position) {

        return  this.fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return this.fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

      return  this.fragmentList.get(position).getTitle();
    }

}
