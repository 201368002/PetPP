package com.example.tacademy.petpp.ui.main.frag;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.tacademy.petpp.R;
import com.example.tacademy.petpp.ui.mypage.act.MyPageActivity;
import com.example.tacademy.petpp.util.Log;
import com.example.tacademy.petpp.util.U;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static android.view.View.VISIBLE;

public class MainMapFragment extends Fragment implements OnMapReadyCallback {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    LayoutInflater inflater;

    LinearLayout detailMarker;
    RelativeLayout mapLayout;
    MapView map;

    public MainMapFragment() {
    }

    public static MainMapFragment newInstance(String param1, String param2) {
        MainMapFragment fragment = new MainMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater   = inflater;
        View view       = inflater.inflate(R.layout.fragment_main_map, container, false);
        detailMarker    = (LinearLayout) view.findViewById(R.id.detailMarker);
        mapLayout       = (RelativeLayout) view.findViewById(R.id.mapLayout);
        map             = (MapView)view.findViewById(R.id.map);

        // ========================= 이벤트 리스너 =============================
        detailMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPageActivity.class);
                intent.putExtra("type", false);
                startActivity(intent);
            }
        });
        // ===================================================================

        // 초기화
        MapView mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    // ======================================================
    // implements OnMapReadyCallback ========================

    static final LatLng SEOUL = new LatLng(37.56, 126.97);

    private GoogleMap mMap;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 임시 상대방 위치 : 37.4809911,126.9640615
        LatLng youPosision
                = new LatLng(37.4809911, 126.9640615);

        // 상대방 위치 마커 추가
        Marker marker =
                        mMap.addMarker(new MarkerOptions().position(youPosision)
                                .title("임시 상대방 위치"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(youPosision, 12));

        // 지도상에서 내위치 정보 표시 및 획득 -> 빨간줄 : 퍼미션문제
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener(){
            @Override
            public void onMyLocationChange(Location location) {
                Log.getInstance().gpsLog("구글지도상내위치정보:" + location.getLatitude()
                        + "," + location.getLongitude());

                // 내 위치 세팅
                LatLng myPosision
                        = new LatLng(location.getLatitude(),
                        location.getLongitude());

                // 구글지도상내위치정보:37.4663881,126.9605299
                // 새로운위치정보:37.4663881,126.9605299
            }
        });

        // 마커 클릭
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker) {
                Log.getInstance().gpsLog("내가 찍은 위치:" + marker.getPosition().latitude
                        + "," + marker.getPosition().longitude);
                // 클릭시 하단 상세데이터 보이기
                detailMarker.setVisibility(VISIBLE);
                return false;
            }
        });
    }

    // ======================================================

//    public void onDetailMarker(View view){
//        Intent intent = new Intent(getActivity(), MyPageActivity.class);
//        startActivity(intent);
//    }
}
