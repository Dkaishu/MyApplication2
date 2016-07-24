package com.lingyun.loaddemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by dandy on 2016/6/2.
 */
public class LoadView extends RelativeLayout{

    private LoadPreView loadPreView;

    private LoadingView loadingView;

    private LoadSuccessView loadSuccessView;

    public LoadView(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        setUpInit(attributeSet);
    }

    private void setUpInit(AttributeSet attributeSet){
        addPre(attributeSet);
        addLoading(attributeSet);
        addSuccess(attributeSet);
    }

    private void addPre(AttributeSet attributeSet){
        loadPreView = new LoadPreView(getContext(),attributeSet);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        loadPreView.setLayoutParams(params);
        addView(loadPreView);
    }

    private void addLoading(AttributeSet attributeSet){
        loadingView = new LoadingView(getContext(),attributeSet);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        loadingView.setLayoutParams(params);
        loadingView.setVisibility(View.INVISIBLE);
        addView(loadingView);
    }

    private void addSuccess(AttributeSet attributeSet){
        loadSuccessView = new LoadSuccessView(getContext(),attributeSet);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        loadSuccessView.setLayoutParams(params);
        loadSuccessView.setVisibility(INVISIBLE);
        addView(loadSuccessView);
    }

    public void setLoadListener(LoadPreView.LoadListener listener){
        loadPreView.setLoadListener(listener);
    }

    /**
     * 开始加载
     */
    public void startLoad() {
        load(1);
        loadingView.startLoad();
    }

    /**
     * 加载成功
     */
    public void loadSuccess(){
        load(2);
    }

    /**
     * 加载完成
     */
    public void loadFinish(){
        load(0);
        loadingView.finishLoad();
    }

    /**
     * 设置下滑的delay
     * @param delay
     */
    public void bezierDelay(float delay){
        loadPreView.setDelay(delay);
    }

    /**
     * 取消加载刷新动作
     */
    public void loadCancle(){
        loadPreView.bezierBack();
    }

    /**
     * 开始加载
     */
    public void load(int index){
        switch (index){
            case 0:
                loadPreView.setVisibility(VISIBLE);
                loadingView.setVisibility(INVISIBLE);
                loadSuccessView.setVisibility(INVISIBLE);
                break;
            case 1:
                loadPreView.setVisibility(INVISIBLE);
                loadingView.setVisibility(VISIBLE);
                loadSuccessView.setVisibility(INVISIBLE);
                break;
            case 2:
                loadPreView.setVisibility(INVISIBLE);
                loadingView.setVisibility(INVISIBLE);
                loadSuccessView.setVisibility(VISIBLE);
                break;
        }
    }
}
