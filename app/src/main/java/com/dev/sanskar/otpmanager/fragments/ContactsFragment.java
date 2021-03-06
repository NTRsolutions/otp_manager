package com.dev.sanskar.otpmanager.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.sanskar.otpmanager.R;
import com.dev.sanskar.otpmanager.adapters.ContactsAdapter;
import com.dev.sanskar.otpmanager.models.ContactModel;
import com.dev.sanskar.otpmanager.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";


    RecyclerView recyclerView;

    public ContactsFragment() {
        // Required empty public constructor
    }

    public static ContactsFragment newInstance(String sectionNumber) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Toasty.info(getActivity(), "We got : " +getArguments().getString(ARG_SECTION_NUMBER), Toast.LENGTH_SHORT, false).show();

        recyclerView = view.findViewById(R.id.recycler_view_contacts);
        refreshFeed();

    }


    private void refreshFeed(){

        List<ContactModel> items = new ArrayList<ContactModel>();


        try {
            JSONArray jarr = new JSONArray(Utils.JSON_CONTACTS);

            for(int i=0; i<jarr.length();i++){
                JSONObject obj = jarr.getJSONObject(i);
                items.add(new ContactModel(obj.getString("name"),obj.getString("number")));
            }
        } catch (JSONException e) {
            Toasty.info(getActivity(),"ERROR JSON ",Toast.LENGTH_SHORT,false).show();
            e.printStackTrace();
        }


        if(!items.isEmpty()){

            Log.e("from refreshfeed()"," items is NOT empty");
            Log.e("from refreshfeed()"," items : \n "+ "");

            ContactsAdapter adapter = new ContactsAdapter(items);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }

    }





}
