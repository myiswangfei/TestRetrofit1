package com.my.retfit.providerandobserver.observer;

import android.database.ContentObserver;
import android.os.Handler;
import android.os.Message;

public class PersonOberserver extends ContentObserver {

	private Handler handler;

	public PersonOberserver(Handler handler) {
		super(handler);
		this.handler=handler;
	}

	@Override
	public void onChange(boolean selfChange) {
		super.onChange(selfChange);
		//向handler发送消息,更新查询记录
		Message msg = new Message();
		handler.sendMessage(msg);
	}

}
