package crypto.currency.crypto_app.Tabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;

import crypto.currency.crypto_app.R;
import crypto.currency.crypto_app.Utils.MyService;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.home,
            R.drawable.coins,
            R.drawable.history
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }


        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);

        viewPager=(ViewPager)findViewById(R.id.viewPager);
        tabLayout.setUnboundedRipple(true);
        tabLayout.hasUnboundedRipple();
        tabLayout.getTabSelectedIndicator();

        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[0]));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[1]));
        tabLayout.addTab(tabLayout.newTab().setIcon(tabIcons[2]));

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#2485D3"));
        tabLayout.setTabTextColors(Color.parseColor("#7E7C7C"), Color.parseColor("#f0639c"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            tabLayout.getTabAt(0).getIcon().setTint(getResources().getColor(R.color.colorPrimary,getTheme()));
        }

        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.transparent));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final TabsAdapter adapter = new TabsAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tab.getIcon().setTint(getResources().getColor(R.color.colorPrimary,getTheme()));
                }
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    tab.getIcon().setTint(getResources().getColor(R.color.black,getTheme()));
                }
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        startService(new Intent(getBaseContext(), MyService.class));
    }
}