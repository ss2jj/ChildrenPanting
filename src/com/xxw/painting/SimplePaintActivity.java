package com.xxw.painting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xxw.painting.utils.DataDao;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SimplePaintActivity extends Activity {
	 private GridView gridView_low;
	 private List<Map<String, Object>> dataList_low;
	 private SimpleAdapter adapter_low;
	 private TextView mTextViewContent;
	 private StringBuilder mStrContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);   
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);  
		setContentView(R.layout.activity_simplepainting);
		gridView_low = (GridView) findViewById(R.id.simplepainting_gridview_low);
		mTextViewContent =(TextView) findViewById(R.id.simple_shuoming);
		mStrContent=new StringBuilder();
	        //两个tab键，用于段落开头
        mStrContent.append("    ").append("学习简笔画，可以从画点线开始练习，"
                ).append("继而过渡到形，即用简练的笔法通过线与线的结合来组合图形。").append("\n")
        .append("    ").append("现在，点击下方的卡通图案，开始学习绘画吧!");
        mTextViewContent.setText(mStrContent.toString());
		Typeface mtypeface=Typeface.createFromAsset(getAssets(),"youyuan.TTF");
		 mTextViewContent.setTypeface(mtypeface);
		initData();
		String[] from={"img","text"};

        int[] to={R.id.img,R.id.text};

        adapter_low=new SimpleAdapter(this, dataList_low, R.layout.simple_grid_view_item, from, to);
        gridView_low.setAdapter(adapter_low);
       
        gridView_low.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Log.d("SimplePaintActivity", "arg2 "+arg2);
			}
		});
	}
	
	 void initData() {
	      
	        dataList_low = new ArrayList<Map<String, Object>>();
	      
	        for (int i = 0; i <DataDao.icno.length; i++) {
	            Map<String, Object> map=new HashMap<String, Object>();
	            map.put("img", DataDao.icno[i]);
	            map.put("text",DataDao.name[i]);
	            dataList_low.add(map);
	           
	        }
	    }

	
	 public void back(View view)	{
		 
		  startActivity(new Intent(SimplePaintActivity.this,MainActivity.class));
		  finish();
	 }
}
