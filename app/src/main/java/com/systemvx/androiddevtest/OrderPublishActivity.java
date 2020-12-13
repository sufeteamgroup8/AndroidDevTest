package com.systemvx.androiddevtest;

import androidx.fragment.app.Fragment;

public class OrderPublishActivity extends AbsFragmentActivity{
    @Override
    public Fragment getFragment()
    {
        return new OrderPublishFragment();
    }
}
