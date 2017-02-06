package com.example.tacademy.petpp.util;

import android.support.multidex.MultiDexApplication;

import com.miguelbcr.ui.rx_paparazzo.RxPaparazzo;

/**
 * Created by Tacademy on 2017-01-31.
 */

public class MyApplication extends MultiDexApplication
{
    @Override
    public void onCreate() {
        super.onCreate();
        RxPaparazzo.register(this);
    }
}

