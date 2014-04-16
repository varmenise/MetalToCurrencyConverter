package com.valentina.converter_metals.app;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlPullParser;
import java.io.InputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import org.xmlpull.v1.XmlPullParserException;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.content.Context;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.view.View;



public class main extends ActionBarActivity {
    private Spinner spinner;
    private String curr_selected;
    private CustomAdapter adapter;

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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences sharedpreferences = getSharedPreferences("valentina", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addItemsOnSpinner();


        rowItems = new ArrayList<MetalObject>();
        for (int i = 0; i < titles.length; i++) {
            String valueCurrency=descriptions[i];
            MetalObject item = new MetalObject( titles[i],descriptions[i],images[i]);
            rowItems.add(item);
        }


        Log.v("ciao", "ciao");
        addItemsOnList();
        getMetalsFeed();

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                curr_selected = parent.getSelectedItem().toString();
                rowItems = new ArrayList<MetalObject>();
                for (int i = 0; i < titles.length; i++) {
                    String valueCurrency=curr_selected;
                    String key=valueCurrency.toLowerCase()+titles[i].toLowerCase();
                    String value=sharedpreferences.getString(key,"not available");
                    MetalObject item = new MetalObject( titles[i],value,images[i]);
                    rowItems.add(item);
                }

                addItemsOnList();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getMetalsFeed(){
        URL url=null;

        try{
            url = new URL("http://www.xmlcharts.com/cache/precious-metals.php");
            //more code goes here
        }catch(MalformedURLException ex){
//do exception handling here
        }

        MetalsFeedAsync metalsFeedasync= new MetalsFeedAsync();
        metalsFeedasync.execute(url);


    }


    public void addItemsOnSpinner() {

        spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_arrays, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    }

    public void addItemsOnList(){

        listView = (ListView) findViewById(R.id.listView);
        adapter = new CustomAdapter(this, rowItems);
        listView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private class MetalsFeedAsync extends AsyncTask<URL, Integer, List<Currency>> {

        public MetalsFeedAsync() {
            super();
        }

        @Override
        protected List<Currency> doInBackground(URL... params) {
            List<Currency>currencies =getMetalsFeed();
            return currencies;
        }

        @Override
        protected void onPostExecute(List<Currency>currencies) {
            super.onPostExecute(currencies);
            SharedPreferences sharedpreferences = getSharedPreferences("valentina", Context.MODE_PRIVATE);

            // Binding data
          //  ArrayAdapter adapter = new ArrayAdapter(this,
          //          android.R.layout.simple_list_item_1, currencies);

            // setListAdapter(adapter);
            Editor editor = sharedpreferences.edit();
            for (int i=0;i<currencies.size();i++){
                int num_metals=currencies.get(i).getMetals_value().size();
                for(int j=0;j<num_metals;j++){

                    Currency current= currencies.get(i);
                    List<Metal> metals= current.getMetals_value();
                    String key=current.getCurrency_name()+metals.get(j).getName_metal();
                    editor.putString(key,metals.get(j).getValue());
                    Log.v("list"+key,metals.get(j).getValue());
                }
            }
            editor.commit();


        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

   /* @Override
    protected void onCancelled(Long aLong) {
        super.onCancelled(aLong);
    }
    */

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }


        public List<Currency> getMetalsFeed(){

            Log.v("ciao", "ciao");

            List<Currency> currencies = new ArrayList<Currency>();

            try {
                URL url = new URL("http://www.xmlcharts.com/cache/precious-metals.php");

                Log.v("ciao","ciao");


                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);

                XmlPullParser xpp = factory.newPullParser();

                // We will get the XML from an input stream
                xpp.setInput(getInputStream(url), "UTF_8");
                Log.v("ciao","ciaociao");



        /* We will parse the XML content looking for the "<title>" tag which appears inside the "<item>" tag.
         * However, we should take in consideration that the rss feed name also is enclosed in a "<title>" tag.
         * As we know, every feed begins with these lines: "<channel><title>Feed_Name</title>...."
         * so we should skip the "<title>" tag which is a child of "<channel>" tag,
         * and take in consideration only "<title>" tag which is a child of "<item>"
         *
         * In order to achieve this, we will make use of a boolean variable.
         */
                boolean insideItem = false;

                // Returns the type of current event: START_TAG, END_TAG, etc..
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        Log.v("ciao","currency");

                        if (xpp.getName().equalsIgnoreCase("prices")) {
                            Log.v("ciao","prices");

                        }

                        if (xpp.getName().equalsIgnoreCase("currency")) {
                            Log.v("ciao","currency");

                            String currency=xpp.getAttributeValue(null,"access");

                            Currency c=readCurrency(xpp, currency);
                            currencies.add(c);
                        }

                    }else if(eventType==XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("currency")){
                        insideItem=false;
                    }

                    eventType = xpp.next(); //move to next element
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.v("ciao",e.getMessage());
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                Log.v("ciao",e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.v("ciao",e.getMessage());
            }
            catch(Exception e){

                Log.v("ciao",e.getMessage());
            }



            return currencies;
        }


        private Currency readCurrency(XmlPullParser parser, String currency_name) throws XmlPullParserException, IOException {
            parser.require(XmlPullParser.START_TAG, null, "currency");


            Currency currency = new Currency();
            currency.setCurrency_name(currency_name);
            List<Metal> metals = new ArrayList<Metal>();
            while (parser.next() != XmlPullParser.END_TAG) {
                if (parser.getEventType() != XmlPullParser.START_TAG) {
                    continue;
                }
                String name = parser.getName();
                if (name.equals("price")) {
                    Metal metal  = new Metal();
                    metal.setName_metal(parser.getAttributeValue(null,"access"));
                    metal.setValue(parser.nextText());
                    metals.add(metal);
                    Log.v("ciao",metal.getValue());
                    Log.v("ciao",metal.getValue());

                }
            }

            currency.setMetals_value(metals);
            return currency;

        }

        public InputStream getInputStream(URL url) {
            try {
                return url.openConnection().getInputStream();
            } catch (IOException e) {
                Log.v("ciao","ciaobaby");
                Log.v("ciao",e.getMessage());
                return null;
            }
        }
    }

}
