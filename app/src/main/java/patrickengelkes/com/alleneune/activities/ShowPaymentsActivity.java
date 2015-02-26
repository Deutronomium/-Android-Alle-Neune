package patrickengelkes.com.alleneune.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import patrickengelkes.com.alleneune.R;
import patrickengelkes.com.alleneune.adapters.fragment_adapters.FragmentPagerAdapter;
import patrickengelkes.com.alleneune.fragments.UserDrinkPaymentsFragment;
import patrickengelkes.com.alleneune.fragments.UserFinePaymentsFragment;
import roboguice.activity.RoboFragmentActivity;

public class ShowPaymentsActivity extends RoboFragmentActivity implements ActionBar.TabListener,
        UserDrinkPaymentsFragment.OnFragmentInteractionListener,
        UserFinePaymentsFragment.OnFragmentInteractionListener {

    private ViewPager viewPager;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private ActionBar actionBar;

    private int userID;
    private int eventID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_payments);

        Intent intent = getIntent();
        userID = intent.getIntExtra(ShowEventActivity.USER_ID, 0);
        eventID = intent.getIntExtra(ShowEventActivity.EVENT_ID, 0);

        //init
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), userID, eventID);

        viewPager.setAdapter(fragmentPagerAdapter);
        //select the respective tab on swiping
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.addTab(actionBar.newTab().setText(R.string.drink_tab_name).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(R.string.fine_tab_name).setTabListener(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_payments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}
