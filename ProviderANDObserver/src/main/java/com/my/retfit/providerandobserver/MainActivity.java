package com.my.retfit.providerandobserver;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.my.retfit.providerandobserver.adapter.MyAdapter;
import com.my.retfit.providerandobserver.bean.Student;
import com.my.retfit.providerandobserver.observer.PersonOberserver;

import java.util.ArrayList;

public class MainActivity extends Activity implements OnClickListener {

	private ContentResolver contentResolver;
	private ListView lvShowInfo;
	private MyAdapter adapter;
	private Button btnInit;
	private Button btnInsert;
	private Button btnDelete;
	private Button btnUpdate;
	private Button btnQuery;
	private Cursor cursor;

	private static final String AUTHORITY = "com.example.studentProvider";
	private static final Uri STUDENT_ALL_URI = Uri.parse("content://" + AUTHORITY + "/student");
	protected static final String TAG = "MainActivity";

	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			//在此我们可以针对数据改变后做一些操作，比方说Adapter.notifyDataSetChanged()等，根据业务需求来定。。
			cursor = contentResolver.query(STUDENT_ALL_URI, null, null, null,null);

			adapter.changeCursor(cursor);
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lvShowInfo=(ListView) findViewById(R.id.lv_show_info);
		initData();
	}

	private void initData() {
		btnInit=(Button) findViewById(R.id.btn_init);
		btnInsert=(Button) findViewById(R.id.btn_insert);
		btnDelete=(Button) findViewById(R.id.btn_delete);
		btnUpdate=(Button) findViewById(R.id.btn_update);
		btnQuery=(Button) findViewById(R.id.btn_query);

		btnInit.setOnClickListener(this);
		btnInsert.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		btnQuery.setOnClickListener(this);

//		使用者				系统提供
		contentResolver = getContentResolver();
		//注册内容观察者
		contentResolver.registerContentObserver(STUDENT_ALL_URI,true,new PersonOberserver(handler));

		adapter=new MyAdapter(MainActivity.this,cursor);
		lvShowInfo.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			//初始化
			case R.id.btn_init:
				ArrayList<Student> students = new ArrayList<Student>();

				Student student1 = new Student("苍老师",25,"一个会教学的好老师");
				Student student2 = new Student("柳岩",26,"大方");
				Student student3 = new Student("杨幂",27,"漂亮");
				Student student4 = new Student("张馨予",28,"不知道怎么评价");
				Student student5 = new Student("范冰冰",29,"。。。");

				students.add(student1);
				students.add(student2);
				students.add(student3);
				students.add(student4);
				students.add(student5);

				for (Student Student : students) {
					ContentValues values = new ContentValues();
					values.put("name", Student.getName());
					values.put("age", Student.getAge());
					values.put("introduce", Student.getIntroduce());
//					此处由系统处理
					contentResolver.insert(STUDENT_ALL_URI, values);
				}
				break;

			//增
			case R.id.btn_insert:

				Student student = new Student("小明", 26, "帅气男人");

				//实例化一个ContentValues对象
				ContentValues insertContentValues = new ContentValues();
				insertContentValues.put("name",student.getName());
				insertContentValues.put("age",student.getAge());
				insertContentValues.put("introduce",student.getIntroduce());

				//这里的uri和ContentValues对象经过一系列处理之后会传到ContentProvider中的insert方法中，
				//在我们自定义的ContentProvider中进行匹配操作
				contentResolver.insert(STUDENT_ALL_URI,insertContentValues);
				break;

			//删
			case R.id.btn_delete:

				//删除所有条目
				contentResolver.delete(STUDENT_ALL_URI, null, null);
				//删除_id为1的记录
				Uri delUri = ContentUris.withAppendedId(STUDENT_ALL_URI,1);
				contentResolver.delete(delUri, null, null);
				break;

			//改
			case R.id.btn_update:

				ContentValues contentValues = new ContentValues();
				contentValues.put("introduce","性感");
				//更新数据，将age=26的条目的introduce更新为"性感"，原来age=26的introduce为"大方".
				//生成的Uri为：content://com.example.studentProvider/student/26
				Uri updateUri = ContentUris.withAppendedId(STUDENT_ALL_URI,26);
				contentResolver.update(updateUri,contentValues, null, null);

				break;
			//查
			case R.id.btn_query:
				//通过ContentResolver获得一个调用ContentProvider对象
				Cursor cursor = contentResolver.query(STUDENT_ALL_URI, null, null, null,null);
				//CursorAdapter的用法，参考此博客：http://blog.csdn.net/dmk877/article/details/44983491
				adapter=new MyAdapter(MainActivity.this,cursor);
				lvShowInfo.setAdapter(adapter);
				cursor = contentResolver.query(STUDENT_ALL_URI, null, null, null,null);
				adapter.changeCursor(cursor);
				break;
		}
	}
}
