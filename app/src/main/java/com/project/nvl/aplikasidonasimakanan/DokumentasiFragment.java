package com.project.nvl.aplikasidonasimakanan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DokumentasiFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView.LayoutManager layoutManager;
    private DokumentasiAdapter dokumentasiAdapter; // Perubahan ini

    public DokumentasiFragment() {
        // Required empty public constructor
    }

    public static DokumentasiFragment newInstance(String param1, String param2) {
        DokumentasiFragment fragment = new DokumentasiFragment();
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
        View view = inflater.inflate(R.layout.fragment_dokumentasi, container, false);

        // Menentukan LayoutManager untuk RecyclerView
        layoutManager = new LinearLayoutManager(getContext());

        // Hubungkan RecyclerView dengan class Adapter
        dokumentasiAdapter = new DokumentasiAdapter();
        RecyclerView recyclerView = view.findViewById(R.id.list_dokumentasi);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(dokumentasiAdapter);

        return view;
    }
}
