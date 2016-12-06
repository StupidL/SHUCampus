package me.stupideme.shucampus.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by StupidL on 2016/8/12.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> fragmentList;
    private String[] tabTitles = new String[]{"聊天","提及","评论"};

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        fragmentList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return tabTitles[position];
    }

}
