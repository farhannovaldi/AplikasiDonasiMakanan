package com.project.nvl.aplikasidonasimakanan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DaftarPenyumbangFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter<RecyclerAdapter.ViewHolder> adapter;

    public DaftarPenyumbangFragment() {
        // Required empty public constructor
    }

    public static DaftarPenyumbangFragment newInstance(String param1, String param2) {
        DaftarPenyumbangFragment fragment = new DaftarPenyumbangFragment();
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
        View view = inflater.inflate(R.layout.fragment_daftar_penyumbang, container, false);

        // Menentukan LayoutManager untuk RecyclerView
        layoutManager = new LinearLayoutManager(getContext());

        // Hubungkan RecyclerView dengan class Adapter
        adapter = new RecyclerAdapter(); // Sesuaikan dengan constructor RecyclerAdapter yang Anda gunakan
        RecyclerView recyclerView = view.findViewById(R.id.list_penyumbang);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
