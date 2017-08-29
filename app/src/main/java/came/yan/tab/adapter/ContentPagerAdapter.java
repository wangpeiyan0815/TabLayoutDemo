package came.yan.tab.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import came.yan.tab.fragment.ExcellentFragment;
import came.yan.tab.fragment.HeadLinsFragment;
import came.yan.tab.fragment.MyFragment;
import came.yan.tab.fragment.SociologyFragment;

/**
 * Created by Victor Liu on 2017/8/30.
 */

public class ContentPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private String[] title;

    public ContentPagerAdapter(FragmentManager fm, Context context, String[] title) {
        super(fm);
        this.context = context;
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        if (0 == position) {
            HeadLinsFragment headLinsFragment = new HeadLinsFragment();
            return headLinsFragment;
        } else if (1 == position) {
            ExcellentFragment excellentFragment = new ExcellentFragment();
            return excellentFragment;
        } else if (2 == position) {
            SociologyFragment sociologyFragment = new SociologyFragment();
            return sociologyFragment;
        } else {
            MyFragment myFragment = new MyFragment();
            return myFragment;
        }
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
