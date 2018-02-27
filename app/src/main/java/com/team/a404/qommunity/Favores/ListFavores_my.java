package com.team.a404.qommunity.Favores;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.a404.qommunity.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFavores_my extends Fragment {


    public ListFavores_my() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_favores_my, container, false);
    }

}
