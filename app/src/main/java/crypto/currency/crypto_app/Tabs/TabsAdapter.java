package crypto.currency.crypto_app.Tabs;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import crypto.currency.crypto_app.Home_Frag.HomeFragment;
import crypto.currency.crypto_app.SettingsFragment;

public class TabsAdapter extends FragmentPagerAdapter {

    private Context myContext;
            int totalTabs;

    public TabsAdapter(Context context, FragmentManager fm, int totalTabs) {
            super(fm);
            myContext = context;
            this.totalTabs = totalTabs;
            }

        // this is for fragment tabs
        @Override
        public Fragment getItem(int position) {
                switch (position) {
                case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
                case 1:
                SettingsFragment dappsFragment = new SettingsFragment();
                return dappsFragment;
                case 2:
                SettingsFragment dexFragment = new SettingsFragment();
                return dexFragment;
                default:
                 return null;
                }
        }
            // this counts total number of tabs
            @Override
            public int getCount() {
                    return totalTabs;
            }
}