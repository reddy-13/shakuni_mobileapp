package adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nexzenstudent.TimetableListFragment;

/**
 * Created by Rajesh on 2017-09-05.
 */

public class TimetableFragmentAdapter extends FragmentPagerAdapter {

    private int[] sequence;

    public TimetableFragmentAdapter(FragmentManager fm, int[] sequence) {
        super(fm);
        this.sequence = sequence;
    }

    @Override
    public Fragment getItem(int pos) {
        // return fragment and pass data
        return TimetableListFragment.newInstance(pos);
        /*switch (pos) {
            case 0:
                return TimetableListFragment.newInstance(String.valueOf(sequence[pos]));
            case 1:
                return TimetableListFragment.newInstance(String.valueOf(sequence[pos]));
            case 2:
                return TimetableListFragment.newInstance("1");
            case 3:
                return TimetableListFragment.newInstance("2");
            case 4:
                return TimetableListFragment.newInstance("3");
            case 5:
                return TimetableListFragment.newInstance("4");
            case 6:
                return TimetableListFragment.newInstance("5");
            default:
                return null;
        }*/
    }

    @Override
    public int getCount() {
        return 7;
    }

}