package com.example.meetjelly;

import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class OnSwipeTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector = new GestureDetector(new GestureListener());

    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }

    private final class GestureListener extends SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                    }
                } else {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeTop() {
    }

    public void onSwipeBottom() {
    }
}
/* cara panggil di class lain
    LinearLayout ln = (LinearLayout) findViewById(R.id.myLinear); //kalo pake relative tinggal ganti relativenya aja
        ln.setOnTouchListener(new OnSwipeTouchListener() {
public void onSwipeTop() {
        // swipe ke atas
        Toast.makeText(ChalengeLinear.this, "Top", Toast.LENGTH_SHORT).show();
        }
public void onSwipeRight() {
        // swipe layar ke kanan
        onBackPressed();
        }
public void onSwipeLeft() {
        // swipe layar ke kiri
        Intent go = new Intent(MainActivity.this, ActivitySelanjutnya.class);
        startActivity(go);

        }
public void onSwipeBottom() {
        // swipe layar ke bawah
        Toast.makeText(ChalengeLinear.this, "Bottom", Toast.LENGTH_SHORT).show();
        }
        });
        */