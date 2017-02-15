package com.example.tacademy.petpp.ui.mypage.frag;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.tacademy.petpp.R;
import com.example.tacademy.petpp.ui.DetailPostActivity;
import com.example.tacademy.petpp.util.ImageProc;


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
    String[] phDate = {"http://www.9dog.co.kr/wp-content/uploads/2013/08/foot3.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/09/puppy-belly.aaaaah-l.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/09/2882926426_f3118f102f_z.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/08/pr2.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/07/img_01.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/08/sul.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/07/ep50.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/07/img_0121.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/07/img_0413.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/07/ep_02.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/07/img_0214.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/07/img_051.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/08/foot3.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/09/puppy-belly.aaaaah-l.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/09/2882926426_f3118f102f_z.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/08/pr2.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/07/img_01.jpg",
            "http://www.9dog.co.kr/wp-content/uploads/2013/08/sul.jpg"};
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
            if(phDate == null) return 0;
            return phDate.length;
        }

        @Override
        public String getItem(int position) {
            return phDate[position];
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
            ImageView myPageGridImageView = (ImageView)convertView.findViewById(R.id.myPageGridImageView);

            myPageGridImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailPostActivity.class);
                    startActivity(intent);
                }
            });
            ImageProc.getInstance().drawImage(
                    phDate[position], myPageGridImageView
            );

            return convertView;
        }
    }

}