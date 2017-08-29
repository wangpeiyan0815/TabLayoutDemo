package came.yan.tab;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

import came.yan.tab.adapter.ContentPagerAdapter;


/**
 * TabLayout 的简单使用
 */
public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] title = {"头条", "优秀稿件", "社会实践", "我的小新闻", "小米", "黑米", "红米"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intiView();
        initTab();
    }

    private void intiView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

    }

    private void initTab() {
        ContentPagerAdapter contentAdapter = new ContentPagerAdapter(getSupportFragmentManager(), this, title);
        viewPager.setAdapter(contentAdapter);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        ViewCompat.setElevation(tabLayout, 10);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicatorWidth();
            }
        });
    }

    //设置指示器的宽度
    public void setIndicatorWidth() {
        try {
            //拿到tabLayout的mTabStrip属性
            Field mTabStripField = tabLayout.getClass().getDeclaredField("mTabStrip");
            mTabStripField.setAccessible(true);
            LinearLayout mTabStrip = (LinearLayout) mTabStripField.get(tabLayout);
            int dp10 = dip2px(this, 10);
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                View tabView = mTabStrip.getChildAt(i);
                //拿到tabView的mTextView属性
                Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                mTextViewField.setAccessible(true);
                TextView mTextView = (TextView) mTextViewField.get(tabView);
                tabView.setPadding(0, 0, 0, 0);
                //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                int width = 0;
                width = mTextView.getWidth();
                if (width == 0) {
                    mTextView.measure(0, 0);
                    width = mTextView.getMeasuredWidth();
                }
                //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                params.width = width;
                params.leftMargin = dp10;
                params.rightMargin = dp10;
                tabView.setLayoutParams(params);
                tabView.invalidate();
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
