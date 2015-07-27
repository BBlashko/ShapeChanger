package com.lynx.bblashko.shapechanger;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Brett Blashko on 3/14/2015.
 */
class TouchHandler implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener{
    private Player player;

    private final int SWIPE_MIN_DISTANCE = 100;
    private final int SWIPE_MAX_OFF_PATH = 300;
    private final int SWIPE_THRESHOLD_VELOCITY = 25;

    public TouchHandler(Player player){
        this.player = player;
    }

    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    public boolean onDown(MotionEvent e) {
        return false;
    }

    public void onShowPress(MotionEvent e) {

    }

    public boolean onSingleTapUp(MotionEvent e) {
        player.setShape(0);
        player.setJumping();
        return true;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    public void onLongPress(MotionEvent e) {

    }

    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        try{
            //Vertical swipe
            if(Math.abs(e1.getX() - e2.getX()) <= SWIPE_MAX_OFF_PATH){
                if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {

                    if(player.getShape() == 2){
                        player.setShape(0);
                    }
                    player.setJumping();

                    Log.d("TouchHandler", "Swipe = up");
                    return true;
                } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityY) > SWIPE_THRESHOLD_VELOCITY) {
                    player.setShape(1);
                    player.setDown();
                    Log.d("TouchHandler", "Swipe = down");
                    return true;
                }
            }

            //Horizontal swipe
            else if(Math.abs(e1.getY() - e2.getY()) <= SWIPE_MAX_OFF_PATH){
                if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d("TouchHandler", "Swipe = right");
                    player.setShape(2);
                    player.setBurst();


                    return true;
                }
                else if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    Log.d("TouchHandler", "Swipe = left");
                    player.setShape(0);
                    return true;
                }
            }
            Log.d("TouchHandler", "Swipe = NOSWIPE");
        }catch(Exception e){}

        return false;
    }
}

