package com.example.businesstriphelper.main.weather.util;

public interface HttpCallbackListener {

	void onFinish(String response);

	void onError(Exception e);

}
