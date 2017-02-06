package com.example.tacademy.petpp.ui.main.frag;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tacademy.petpp.R;
import com.example.tacademy.petpp.util.Log;

public class MainTLFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    // ============================

    ListView main_listView;
    MyAdapter listMyAdapter;
    LayoutInflater inflater;

    // 임의 데이터
    String[] tmpDate = {"가나다1","가나다2","라나다3","가나다4","가나다라5","라나다6",
            "가나다7","가나다라8","라나다9","가나다0","가나다라9","라나다8",
            "가나다77","가나다6라","라5나다","가나4다","가3나다라","2라나다"};

    // ============================
    public MainTLFragment() {
    }

    public static MainTLFragment newInstance(String param1, String param2) {
        MainTLFragment fragment = new MainTLFragment();
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
        View view = inflater.inflate(R.layout.fragment_main_tl, container, false);
        main_listView = (ListView)view.findViewById(R.id.main_listView);

        listMyAdapter = new MyAdapter();
        main_listView.setAdapter(listMyAdapter);

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

    // =============================

    class ViewHolder{
        TextView tl_name = null;
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if(tmpDate == null) return 0;
            return tmpDate.length;
        }

        @Override
        public String getItem(int position) {
            return tmpDate[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                // holder 초기화, 그릇 생성
                holder = new ViewHolder();

                // 최초 화면을 구성할 때 최대로 필요한 수만큼 여기가 작동됨
                convertView =
                        inflater.inflate(R.layout.cell_list_tl_layout, parent, false);

                holder.tl_name = (TextView)convertView.findViewById(R.id.tl_name);

                // 그릇을 뷰에 설정
                convertView.setTag(holder);

                Log.getInstance().log("셀생성 " + position);
            }else {
                // 이제 로테이션시킬 양이 모두 채워졌다. 로테이션 시작의 의미
                // 재활용시 셀의 구성을 담는 그릇을 획득
                holder = (ViewHolder) convertView.getTag();
            }
            // 데이터 설정
            // 이름 세팅
            holder.tl_name.setText("" + getItem(position));

            return convertView;
        }
    }

    // =============================
}