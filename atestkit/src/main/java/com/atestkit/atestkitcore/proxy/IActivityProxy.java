package com.atestkit.atestkitcore.proxy;

import android.os.Bundle;

/**
 * Created by lijianan on 2017/2/14.
 */

public interface IActivityProxy {
    void onCreate(Bundle savedInstanceState);
    void onStart();
    void onResume();
    void onPause();
    void onStop();
    void onDestroy();
}
