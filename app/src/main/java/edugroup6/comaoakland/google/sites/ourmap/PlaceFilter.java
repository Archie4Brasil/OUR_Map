package edugroup6.comaoakland.google.sites.ourmap;

import android.app.ListActivity;
import android.app.Notification;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PlaceFilter extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_filter);

        String[] places = {"this", "is", "where", "places", "will", "go"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, places);
       // ListView myList = (ListView) findViewById(R.id.list);

        getListView().setAdapter(adapter);

    }

}
