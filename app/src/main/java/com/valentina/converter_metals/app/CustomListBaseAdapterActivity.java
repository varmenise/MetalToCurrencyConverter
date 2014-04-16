package com.valentina.converter_metals.app;

        import java.util.ArrayList;
        import java.util.List;
        import android.app.Activity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.ListView;
        import android.widget.Toast;
import android.view.Gravity;


public class CustomListBaseAdapterActivity extends Activity implements
        OnItemClickListener {

    public static final String[] titles = new String[] { "Gold",
            "Silver", "Palladium", "Platinum" };

    public static final String[] descriptions = new String[] {
            "fake gold",
            "fake silver", "fake palladium",
            "fake platinum" };

    public static final Integer[] images = { R.drawable.gold,
            R.drawable.silver, R.drawable.palladium, R.drawable.platinum };

    ListView listView;
    List<MetalObject> rowItems;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rowItems = new ArrayList<MetalObject>();
        for (int i = 0; i < titles.length; i++) {
            String valueCurrency=descriptions[i];
            MetalObject item = new MetalObject( titles[i],descriptions[i],images[i]);
            rowItems.add(item);
        }

        listView = (ListView) findViewById(R.id.listView);
        CustomAdapter adapter = new CustomAdapter(this, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Item " + (position + 1) + ": " + rowItems.get(position),
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}