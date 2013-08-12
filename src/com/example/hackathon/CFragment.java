package com.example.hackathon;
import com.example.hackathon.adapter.EventAdapter;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class CFragment extends ListFragment  {
	
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.afragment, container, false);
//    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
      super.onActivityCreated(savedInstanceState);
//      String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
//          "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
//          "Linux", "OS/2" };
//      ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//          android.R.layout.simple_list_item_1, values);
      String [] test_string = {"this is testing", "test string2"};

     // setListAdapter(new EventAdapter(getActivity(), test_string));
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
      // Do something with the data

    }
    
}

