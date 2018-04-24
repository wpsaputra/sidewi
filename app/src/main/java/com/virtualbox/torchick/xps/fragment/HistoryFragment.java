package com.virtualbox.torchick.xps.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.virtualbox.torchick.xps.R;
import com.virtualbox.torchick.xps.adapter.HistoryAdapter;
import com.virtualbox.torchick.xps.adapter.PublikasiAdapter;
import com.virtualbox.torchick.xps.database.ModelPublikasi;
import com.virtualbox.torchick.xps.helper.DividerItemDecoration;
import com.virtualbox.torchick.xps.model.Publikasi;
import com.virtualbox.torchick.xps.network.ApiClient;
import com.virtualbox.torchick.xps.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // Custom
    private List<Publikasi> publikasiList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ConstraintLayout constraintLayout;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        constraintLayout = (ConstraintLayout) view.findViewById(R.id.constraintlayout);

        historyAdapter = new HistoryAdapter(publikasiList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(historyAdapter);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                getPublikasi();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPublikasi();
            }
        });
    }

    private void getPublikasi() {
        swipeRefreshLayout.setRefreshing(true);

        ModelPublikasi mp = new ModelPublikasi(getContext());
        List<Publikasi> lp = mp.getAll();
        publikasiList.clear();
        for(int i=0; i<lp.size(); i++){
            System.out.println(lp.get(i));
            publikasiList.add(lp.get(i));
        }

        if(lp.size()>0){
            constraintLayout.setVisibility(View.INVISIBLE);
        }

        historyAdapter.notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);

//        ApiInterface apiService =
//                ApiClient.getClient().create(ApiInterface.class);
//
//        Call<List<Publikasi>> call = apiService.getPublikasi("publikasi");
//        call.enqueue(new Callback<List<Publikasi>>() {
//            @Override
//            public void onResponse(Call<List<Publikasi>> call, Response<List<Publikasi>> response) {
//                // clear the inbox
//                publikasiList.clear();
//
//                // add all the messages
//                // messages.addAll(response.body());
//
//                // TODO - avoid looping
//                // the loop was performed to add colors to each message
//                for (Publikasi publikasi : response.body()) {
//                    // generate a random color
////                    message.setColor(getRandomMaterialColor("400"));
//                    publikasiList.add(publikasi);
//                }
//
//                historyAdapter.notifyDataSetChanged();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//
//            @Override
//            public void onFailure(Call<List<Publikasi>> call, Throwable t) {
//                Toast.makeText(getContext(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
//                swipeRefreshLayout.setRefreshing(false);
//            }
//        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
