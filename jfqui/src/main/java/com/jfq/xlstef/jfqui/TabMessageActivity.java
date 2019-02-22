package com.jfq.xlstef.jfqui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TabMessageActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_message);
		//设置标题文字
		TextView layout_top_text=(TextView)findViewById(R.id.layout_top_text);
		layout_top_text.setText(R.string.tab_message_title_text);
	}
}
