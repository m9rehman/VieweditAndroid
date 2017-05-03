package com.example.mrehman.vieweditandroid;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private RelativeLayout mMainView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMainView = (RelativeLayout) getView().findViewById(R.id.mainrelview);
        mMainView.setOnTouchListener(new OnSwipeTouchListener(getActivity()){

            public void onSwipeTop() {
                Toast.makeText(getActivity(), "BACK", Toast.LENGTH_SHORT).show();
//                getActivity().onBackPressed();
                ((MainActivity)getActivity()).removeFragment();
            }


        });

    }


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

}
