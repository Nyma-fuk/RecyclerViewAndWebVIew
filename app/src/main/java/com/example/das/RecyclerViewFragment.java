package com.example.das;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public interface OnItemClickListener {
        void onItemClicked(String url);
    }

    private OnItemClickListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemClickListener) {
            mListener = (OnItemClickListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnItemClickListener");
        }
    }
    private List<String> getHtmlFileNames() {
        List<String> fileNames = new ArrayList<>();
        File externalStorageDir = getContext().getExternalFilesDir(null);

        if (externalStorageDir != null) {
            File[] files = externalStorageDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".html")) {
                        fileNames.add(file.getName());
                    }
                }
            }
        }
        return fileNames;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<String> htmlFileNames = getHtmlFileNames();

        mAdapter = new MyAdapter(htmlFileNames);

        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mListener != null && position >= 0 && position < htmlFileNames.size()) {
                    String fileName = htmlFileNames.get(position);
                    String url = "file://" + new File(getContext().getExternalFilesDir(null), fileName).getAbsolutePath();
                    mListener.onItemClicked(url);
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        return view;
    }
}
