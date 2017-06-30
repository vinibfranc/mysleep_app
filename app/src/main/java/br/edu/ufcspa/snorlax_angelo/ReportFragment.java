package br.edu.ufcspa.snorlax_angelo;


import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import br.edu.ufcspa.snorlax_angelo.database.DataBaseAdapter;
import br.edu.ufcspa.snorlax_angelo.model.Recording;
import ufcspa.edu.br.snorlax_angelo.R;


/**
 * Fragment que mostra lista das gravações efetuadas no app
 */
public class ReportFragment extends Fragment {
    View myView;

    public ReportFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myView= inflater.inflate(R.layout.fragment_report, container, false);
        return myView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView myList  =(ListView) myView.findViewById(R.id.listview_recordings);

        DataBaseAdapter data = DataBaseAdapter.getInstance(myView.getContext());

        List<Recording> recordingList = data.getRecordings();

        if(recordingList.size()>0){
            RecordingAdapter adapter = new RecordingAdapter(recordingList,myView.getContext());
            myList.setAdapter(adapter);
        }
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.frame_content, new ViewMetricsFragment()).commit();
            }
        });
    }
}
