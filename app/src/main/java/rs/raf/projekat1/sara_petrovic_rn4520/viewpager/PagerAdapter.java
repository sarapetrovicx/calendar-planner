package rs.raf.projekat1.sara_petrovic_rn4520.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import rs.raf.projekat1.sara_petrovic_rn4520.fragments.CalendarFragment;
import rs.raf.projekat1.sara_petrovic_rn4520.fragments.TaskDisplayFragment;
import rs.raf.projekat1.sara_petrovic_rn4520.fragments.UserProfileFragment;


public class PagerAdapter extends FragmentPagerAdapter {

    private final int ITEM_COUNT = 3;
    public static final int FRAGMENT_1 = 0;
    public static final int FRAGMENT_2 = 1;
    public static final int FRAGMENT_3 = 2;

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case FRAGMENT_1: fragment = new CalendarFragment(); break;
            case FRAGMENT_2: fragment = new TaskDisplayFragment(); break;
            default: fragment = new UserProfileFragment(); break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case FRAGMENT_1: return "1";
            case FRAGMENT_2: return "2";
            default: return "3";
        }
    }
}
