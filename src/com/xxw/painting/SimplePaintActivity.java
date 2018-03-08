package com.xxw.painting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class SimplePaintActivity extends Activity {
	 private GridView gridView;
	 private List<Map<String, Object>> dataList;
	 private SimpleAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  
		setContentView(R.layout.activity_simplepainting);
		gridView = (GridView) findViewById(R.id.simplepainting_gridview);
		initData();
		String[] from={"img","text"};

        int[] to={R.id.img,R.id.text};

        adapter=new SimpleAdapter(this, dataList, R.layout.simple_grid_view_item, from, to);

        gridView.setAdapter(adapter);
	}
	
	 void initData() {
	        //图标
	        int icno[] = { R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
	                R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher,
	                R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher };
	        //图标下的文字
	        String name[]={"时钟","信号","宝箱","秒钟","大象","FF","记事本","书签","印象","商店","主题","迅雷"};
	        dataList = new ArrayList<Map<String, Object>>();
	        for (int i = 0; i <icno.length; i++) {
	            Map<String, Object> map=new HashMap<String, Object>();
	            map.put("img", icno[i]);
	            map.put("text",name[i]);
	            dataList.add(map);
	        }
	    }

	
}
