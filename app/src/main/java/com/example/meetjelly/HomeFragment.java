package com.example.meetjelly;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor

    }

    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions.

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_home, container, false);
        final LinearLayout layout_agent = x.findViewById(R.id.layout_agent);
        final LinearLayout layout_reseller = x.findViewById(R.id.layout_reseller);


        SharedPreferences prefs = getActivity().getSharedPreferences("DATA_LOGIN", MODE_PRIVATE);
        final int id = prefs.getInt("id", 0);
        String role = prefs.getString("role", "-");

        try {
            assert getArguments() != null;
            role = getArguments().getString("role");
            //Toast.makeText(getActivity(), "" +role, Toast.LENGTH_SHORT).show();
            if (role != null) {
                if (role.equals("agent")) {
                    layout_reseller.setVisibility(View.GONE);
                    layout_agent.setVisibility(View.VISIBLE);
                } else if (role.equals("reseller")) {
                    layout_reseller.setVisibility(View.VISIBLE);
                    layout_agent.setVisibility(View.GONE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        LinearLayout order = x.findViewById(R.id.order);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), OrderActivity.class);
                startActivity(i);
            }
        });

        LinearLayout order_reseller = x.findViewById(R.id.order_reseller);
        order_reseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), OrderActivity.class);
                startActivity(i);
            }
        });

        LinearLayout orderList_reseller = x.findViewById(R.id.orderHistory);
        final String finalRole = role;
        orderList_reseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), OrderHistoryActivity.class);
                i.putExtra("id", id);
                i.putExtra("role", finalRole);
                startActivity(i);
            }
        });

        LinearLayout orderList = x.findViewById(R.id.orderList);
        orderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), OrderListActivity.class);
                i.putExtra("id", id);
                i.putExtra("role", finalRole);
                startActivity(i);
            }
        });

        LinearLayout addReseller = x.findViewById(R.id.addReseller);
        addReseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), RegisterActivity.class);
                i.putExtra("id", id);
                i.putExtra("role", finalRole);
                startActivity(i);
            }
        });

        LinearLayout viewReseller = x.findViewById(R.id.viewReseller);
        viewReseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), MyResellerActivity.class);
                i.putExtra("id", id );
                i.putExtra("role", finalRole);
                startActivity(i);
            }
        });

        LinearLayout help = x.findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), HelpActivity.class);
                startActivity(i);
            }
        });

        LinearLayout reward = x.findViewById(R.id.reward);
        reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity().getApplicationContext(), RewardActivity.class);
                i.putExtra("id", id);
                i.putExtra("role", finalRole);
                startActivity(i);
            }
        });

        final ViewPager viewPager = x.findViewById(R.id.viewPager);
        ImageAdapter adapter = new ImageAdapter(getActivity());
        viewPager.setAdapter(adapter);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            private static final int NUM_PAGES = 8;

            public void run() {
                if (currentPage == NUM_PAGES - 1) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer.schedule(new TimerTask() { // task to be scheduled
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);

        return x;
    }
}
