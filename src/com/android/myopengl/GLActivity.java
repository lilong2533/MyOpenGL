package com.android.myopengl;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GLActivity extends Activity {
	private Context mContext;
	public static String POSITION  = "Position";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.main);
        ListView mylistview = (ListView) findViewById(R.id.mylistview); 
        mylistview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
        mylistview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				Intent intent = new Intent(mContext,SurfaceViewRenderList.class);
				intent.putExtra(POSITION, position);
				mContext.startActivity(intent);
			}
        });
    }
    
    private List<String> getData(){
        List<String> data = new ArrayList<String>();
        data.add("三角形");
        data.add("旋转立方体");
        data.add("手控旋转立方体");
        data.add("星云效果");
        data.add("3D空间游走");
        return data;
    }
}