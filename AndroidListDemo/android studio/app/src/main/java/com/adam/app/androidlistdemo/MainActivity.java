package com.adam.app.androidlistdemo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends ListActivity {

    private final String[] mItem = {

            "One",
            "Two"
    };

    private List<Map<String, Object>> getMapData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        Map<String, Object> item1 = new HashMap<String, Object>();
        item1.put("name", "Exit");
        list.add(item1);

        Map<String, Object> item2 = new HashMap<String, Object>();
        Intent it1 = new Intent(this, CustBtnMain.class);
        item2.put("name", "One");
        item2.put("intent", it1);
        list.add(item2);

        Map<String, Object> item3 = new HashMap<String, Object>();
        Intent it2 = new Intent(this, DialogDemo.class);
        item3.put("name", "AlertDialog");
        item3.put("intent", it2);
        list.add(item3);

        Map<String, Object> item4 = new HashMap<String, Object>();
        Intent it4 = new Intent(this, MyImageViewAct.class);
        item4.put("name", "MyImageViewAct");
        item4.put("intent", it4);
        list.add(item4);

        Map<String, Object> item5 = new HashMap<String, Object>();
        Intent it5 = new Intent(this, ReadTextTest.class);
        item5.put("name", "ReadTextTest");
        item5.put("intent", it5);
        list.add(item5);

        Map<String, Object> item6 = new HashMap<String, Object>();
        Intent it6 = new Intent(this, ParcelableActivity1.class);
        item6.put("name", "ParcelableActivity1");
        item6.put("intent", it6);
        list.add(item6);

        Map<String, Object> item7 = new HashMap<String, Object>();
        Intent it7 = new Intent(this, CanvasView.class);
        item7.put("name", "CanvasView");
        item7.put("intent", it7);
        list.add(item7);

        Map<String, Object> item8 = new HashMap<String, Object>();
        Intent it8 = new Intent(this, URLTest.class);
        item8.put("name", "URLTest");
        item8.put("intent", it8);
        list.add(item8);

        Map<String, Object> item9 = new HashMap<String, Object>();
        Intent it9 = new Intent(this, HandlerTest.class);
        item9.put("name", "HandlerTest");
        item9.put("intent", it9);
        list.add(item9);

        Map<String, Object> item = addListItem(AsyncTaskTest.class);
        list.add(item);


        return list;
    }

    protected Map<String, Object> addListItem(Class<?> cls) {
        Map<String, Object> item = new HashMap<String, Object>();
        Intent it = new Intent(this, cls);
        item.put("name", cls.getName());
        item.put("intent", it);
        return item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListAdapter adapter = new SimpleAdapter(this, getMapData(), android.R.layout.simple_list_item_1,
                new String[]{"name"}, new int[]{android.R.id.text1});
        this.setListAdapter(adapter);

//		this.setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mItem));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);

        Map map = (Map) l.getItemAtPosition(position);
        String str = map.get("name").toString();

        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

        if ("Exit".equals(str)) {
            finish();
        } else {
            Intent it = (Intent) map.get("intent");
            this.startActivity(it);
        }

    }


}
