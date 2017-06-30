package br.edu.ufcspa.snorlax_angelo;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ufcspa.edu.br.snorlax_angelo.R;

/**
 * Created by icaromsc on 13/06/2017.
 *
 * Fragment dos termos de uso do app
 *
 */

public class TcleFragment extends Fragment {

    Button btOk;


    public TcleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tcle, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btOk=(Button) getActivity().findViewById(R.id.tcle_ok);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentLogin frag = new FragmentLogin();
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_content, frag, null)
                        .addToBackStack(null)
                        .commit();

            }
        });
    }





}
