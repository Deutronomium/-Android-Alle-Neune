package patrickengelkes.com.alleneune.adapters.fragment_adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import patrickengelkes.com.alleneune.fragments.ShowUserDrinkPaymentsFragment;
import patrickengelkes.com.alleneune.fragments.UserFineListFragment;

/**
 * Created by patrickengelkes on 26/02/15.
 */
public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {


    private int userID;
    private int eventID;

    public FragmentPagerAdapter(FragmentManager fm, int userID, int eventID) {
        super(fm);
        this.userID = userID;
        this.eventID = eventID;
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                return new ShowUserDrinkPaymentsFragment(userID, eventID);
            case 1:
                return new UserFineListFragment(userID, eventID);
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
