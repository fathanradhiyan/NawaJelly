package com.example.meetjelly;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {


    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab, container, false);

        TabLayout tabLayout = (TabLayout) v.findViewById(R.id.tab);
        ViewPager viewPager = (ViewPager) v.findViewById(R.id.pager);

        viewPager.setAdapter(new TabSetting(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(
                R.drawable.list
        );

        tabLayout.getTabAt(1).setIcon(
                R.drawable.credit_card
        );

        return v;
    }

    class TabSetting extends FragmentStatePagerAdapter {

        public TabSetting(FragmentManager fm) {
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position==0){
                return getString(R.string.order_list);
            }else if (position==1){
                return getString(R.string.payment_list);
            }
            return super.getPageTitle(position);
        }

        public Fragment getItem(int i){
            if (i==0){
                return new PaymentListFragment();
            }else if (i==1){
                return new OrderListFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
