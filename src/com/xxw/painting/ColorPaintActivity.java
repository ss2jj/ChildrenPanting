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

public class ColorPaintActivity extends Activity {
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
		setContentView(R.layout.activity_colorpainting);
		gridView_low = (GridView) findViewById(R.id.colorpainting_gridview_low);
		mTextViewContent =(TextView) findViewById(R.id.color_shuoming);
		mStrContent=new StringBuilder();
	        //两个tab键，用于段落开头
        mStrContent.append("    ").append("根据喜欢的样式为可爱的卡通人物涂上喜欢的颜色，还由于什么呢，快动手创作你自己的卡通角色吧！"
                );
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
				Intent intent = new Intent(ColorPaintActivity.this,SimplePaintTeachActivity.class);
				intent.putExtra("ID", arg2);
				startActivity(intent);
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
		 
		  startActivity(new Intent(ColorPaintActivity.this,MainActivity.class));
		  finish();
	 }
}
