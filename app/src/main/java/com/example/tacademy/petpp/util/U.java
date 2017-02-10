package com.example.tacademy.petpp.util;

import android.location.Location;

import com.squareup.otto.Bus;

/**
 * Created by Tacademy on 2017-02-03.
 */
public class U {
    private static U ourInstance = new U();

    public static U getInstance() {
        return ourInstance;
    }

    private U() {
    }

    // 지도의 나의 현재 위치 실시간 변경하기 위한 변수
    double myLat, myLng;
    Location myLocation;

    Bus bus = new Bus();

    public Bus getBus() {
        return bus;
    }

    public double getMyLat() {
        return myLat;
    }

    public void setMyLat(double myLat) {
        this.myLat = myLat;
    }

    public double getMyLng() {
        return myLng;
    }

    public void setMyLng(double myLng) {
        this.myLng = myLng;
    }

    public Location getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }

    // 다른 사람 피드인지 나의 피드인지 확인 할 type
    Boolean myPageType;

    public Boolean getMyPageType() {
        return myPageType;
    }

    public void setMyPageType(Boolean myPageType) {
        this.myPageType = myPageType;
    }
}
