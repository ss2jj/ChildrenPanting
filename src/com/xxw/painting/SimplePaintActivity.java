package com.xxw.painting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class SimplePaintActivity extends Activity {
	 private GridView gridView_low,gridView_medium,gridView_high;
	 private List<Map<String, Object>> dataList_low,dataList_medium,dataList_high;
	 private SimpleAdapter adapter_low,adapter_medium,adapter_high;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  
		setContentView(R.layout.activity_simplepainting);
		gridView_low = (GridView) findViewById(R.id.simplepainting_gridview_low);
		gridView_medium = (GridView) findViewById(R.id.simplepainting_gridview_medium);
		gridView_high = (GridView) findViewById(R.id.simplepainting_gridview_high);
		initData();
		String[] from={"img","text"};

        int[] to={R.id.img,R.id.text};

        adapter_low=new SimpleAdapter(this, dataList_low, R.layout.simple_grid_view_item, from, to);
        gridView_low.setAdapter(adapter_low);
        adapter_medium=new SimpleAdapter(this, dataList_low, R.layout.simple_grid_view_item, from, to);
        gridView_medium.setAdapter(adapter_medium);
        adapter_high=new SimpleAdapter(this, dataList_low, R.layout.simple_grid_view_item, from, to);
        gridView_high.setAdapter(adapter_high);
        gridView_low.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	 void initData() {
	        //图标
	        int icno[] = { R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
	                R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
	                R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
	                R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
	                R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
	                R.drawable.ic_launcher, R.drawable.ic_launcher};
	        //图标下的文字
	        String name[]={"时钟","信号","宝箱","秒钟","大象","FF","记事本","书签","印象","商店","主题","迅雷","","","","","","","","","","","",""};
	        dataList_low = new ArrayList<Map<String, Object>>();
	        dataList_medium = new ArrayList<Map<String, Object>>();
	        dataList_high = new ArrayList<Map<String, Object>>();
	        for (int i = 0; i <icno.length; i++) {
	            Map<String, Object> map=new HashMap<String, Object>();
	            map.put("img", icno[i]);
	            map.put("text",name[i]);
	            dataList_low.add(map);
	            dataList_medium.add(map);
	            dataList_high.add(map);
	        }
	    }

	
}
