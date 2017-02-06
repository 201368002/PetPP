package com.example.tacademy.petpp.ui.mypage.frag;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tacademy.petpp.R;

public class MyViewFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // ===========================================
    GridView myPageGridView;
    MyAdapter myAdapter;
    LayoutInflater inflater;


    // 임의 데이터
    String[] name = {"가나다1","가나다2","라나다3","가나다4","가나다라5","라나다6",
            "가나다7","가나다라8","라나다9","가나다0","가나다라9","라나다8",
            "가나다77","가나다6라","라5나다","가나4다","가3나다라","2라나다",
            "가나다7","가나다라8","라나다9","가나다0","가나다라9","라나다8",
            "가나다77","가나다6라","라5나다","가나4다","가3나다라","2라나다"};
    // ===========================================

    public MyViewFragment() {
    }

    public static MyViewFragment newInstance(String param1, String param2) {
        MyViewFragment fragment = new MyViewFragment();
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
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.fragment_my_view, container, false);
        myPageGridView = (GridView)view.findViewById(R.id.myPageGridView);
        myAdapter = new MyAdapter();
        myPageGridView.setAdapter(myAdapter);

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(name == null) return 0;
            return name.length;
        }

        @Override
        public String getItem(int position) {
            return name[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                // 리스트뷰
                convertView = inflater.inflate(R.layout.cell_grid_mypage_layout, parent, false);
            }
            ImageView profile = (ImageView)convertView.findViewById(R.id.myPageGridImageView);
            TextView nickname = (TextView)convertView.findViewById(R.id.nickname);

            // 이름 세팅
            nickname.setText(name[position]);

            return convertView;
        }
    }
}
