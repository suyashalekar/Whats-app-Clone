package com.example.whatsapp.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class FragmentAdaptor extends FragmentPagerAdapter {
    public FragmentAdaptor(@NonNull FragmentManager fm) {
        super(fm);
    }

    public FragmentAdaptor(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {


        switch (position){

            case 0: return new ChatsFragment();
            case 1: return new StatusFragment();
            case 2: return  new CallFragment();

            default:return new ChatsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position){

            case 0: title ="Chat";
            break;
            case 1: title = "Status";
            break;
            case 2: title = "Call";
            break;
        }
        return title;
    }
}
