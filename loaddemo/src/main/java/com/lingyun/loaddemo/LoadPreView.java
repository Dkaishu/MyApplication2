package com.lingyun.loaddemo;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dandy on 2016/6/1.
 */
public class LoadPreView extends View{

    private static final String TAG = "LoadPreView";

    private static final float SIZE = 26f;//默认控件整体大小

    private static final float OUTER_RING_WIDTH = 1.0f;//外部圆环粗值

    private static final float INNER_ARC_WIDTH = 2.5f;//内部圆弧粗值

    private static final float TRIANGLE_LENGTH = 7.0f;//三角形边长

    private static final int INNER_COLOR = Color.parseColor("#aaaaaa");

    private static final int OUTTER_COLOR = Color.parseColor("#666666");

    private int innerCicleColor = INNER_COLOR;

    private int outterRingColor = OUTTER_COLOR;

    private float ringWidth;

    private float arcWidth;

    private float triangleLength;

    private float arrowRadius;

    private DisplayMetrics dm;

    private  int exactlySize;

    private Paint paint;

    private float mCurrentHeaderRadius;

    private float mCurrentFooterRadius;

    private float delaY = 0;//y轴偏移量

    private Path mPath = new Path();

    private float maxBezierLength = -1f;

    private float anchorPercent;

    private float currentPercent = 0;

    private boolean canLoad = false;

    private LoadListener loadListener;

    private int originWidth = -1,originHeight = -1;

    private boolean isFirst = true;

    public LoadPreView(Context context){
        this(context, null);
    }

    public LoadPreView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        setWillNotDraw(false);
        setUpInit();
    }

    private void setUpInit(){
        dm = Resources.getSystem().getDisplayMetrics();
        paint = new Paint();
        paint.setAntiAlias(true);
        this.ringWidth = applyDimension(OUTER_RING_WIDTH);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**计算宽**/
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST){
            width = applyDimension(SIZE);
        }

        /**计算高**/
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            height = applyDimension(SIZE);
        }

        maxBezierLength = exactlySize * 1.5f;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(isFirst && w > 0 && h > 0){
            isFirst = false;
            this.originWidth = w;
            this.originHeight = h;
            /**
             * 取小的值作为控件的大小
             */
            exactlySize = originWidth >= originHeight ? originHeight:originWidth;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        resetValue(currentPercent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        resetBeaierPath();
        int saveCount = canvas.getSaveCount();
        canvas.save();
        canvas.translate(originWidth / 2.0f, originHeight / 2.0f);
        drawOutterRing(canvas, 0, 0, mCurrentHeaderRadius);
        drawOutterRing(canvas, 0, delaY, mCurrentFooterRadius);
        drawBezier(canvas, innerCicleColor, Paint.Style.FILL);
        drawBezier(canvas, outterRingColor, Paint.Style.STROKE);
        drawInnerCicle(canvas, 0, 0, mCurrentHeaderRadius);
        drawInnerCicle(canvas, 0, delaY, mCurrentFooterRadius);
        drawArc(canvas, 0, 0);
        drawTriangle(canvas);
        canvas.restoreToCount(saveCount);
    }

    /**
     * 绘制外部圆环
     * @param canvas
     * @param centerX
     * @param centerY
     * @param radius
     */
    private void drawOutterRing(Canvas canvas,float centerX,float centerY,float radius){
        paint.setColor(outterRingColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ringWidth);
        canvas.drawCircle(centerX, centerY, radius, paint);
    }

    /**
     * 绘制内部实心圆
     * @param canvas
     * @param centerX
     * @param centerY
     * @param radius
     */
    private void drawInnerCicle(Canvas canvas,float centerX,float centerY, float radius) {
        paint.setColor(innerCicleColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius, paint);
    }

    /**
     * 绘制内部圆弧
     * @param canvas
     * @param centerX
     * @param centerY
     */
    private void drawArc(Canvas canvas,float centerX, float centerY){
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(arcWidth);
        RectF oval = new RectF(centerX-arrowRadius,centerY-arrowRadius,centerX+arrowRadius, centerY + arrowRadius);
        canvas.drawArc(oval, 0, 270, false, paint);
    }

    /**
     * 绘制内部三角形
     */
    private void drawTriangle(Canvas canvas){
        Path path = new Path();
        path.moveTo(0, -(arrowRadius - triangleLength / 2.0f));
        path.lineTo(0, -(arrowRadius + triangleLength / 2.0f));
        path.lineTo(triangleLength * 0.618f,-(arrowRadius));
        path.lineTo(0, -(arrowRadius - triangleLength / 2.0f));
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);
    }

    /**
     * 重置贝塞尔曲线
     */
    private void resetBeaierPath(){
         /* 四个点 */
        final float headerX1 = -mCurrentHeaderRadius;
        final float headerX2 = mCurrentHeaderRadius;
        final float headerY = 0;
        final float footerX1 = -mCurrentFooterRadius;
        final float footerX2 = mCurrentFooterRadius;
        final float footerY = delaY;

         /* 控制点 */
        final float anchorX1 = headerX1 / anchorPercent;
        float anchorY = delaY / anchorPercent;
        anchorY = anchorY > exactlySize / 1.5f?exactlySize / 1.5f:anchorY;
        final float anchorX2 = headerX2 / anchorPercent;

         /* 画贝塞尔曲线 */

        mPath.reset();
        mPath.moveTo(headerX1, headerY);
        mPath.quadTo(anchorX1, anchorY, footerX1, footerY);
        mPath.lineTo(footerX2, footerY);
        mPath.quadTo(anchorX2, anchorY, headerX2, headerY);
        mPath.lineTo(headerX1, headerY);
    }

    /**
     * 绘制贝塞尔曲线
     * @param canvas
     */
    private void drawBezier(Canvas canvas,int color,Paint.Style style) {
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(ringWidth / 2.0f);
        canvas.drawPath(mPath, paint);
    }

    /**
     * 改变某控件的大小
     */
    private void modityViewSize(int height){
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = height;
        params.width = originWidth;
        setLayoutParams(params);
    }

    /**
     * px2dp
     * @param value
     */
    private int applyDimension(float value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm);
    }

    /**
     * @param percent 绘制贝塞尔曲线的比例
     */
    private void setPercent(float percent){
        currentPercent = percent >= 1.0f?1.0f:percent;
       if(currentPercent == 1.0f){
           canLoad = true;
           bezierBack();
        }
    }

    private void resetValue(float percent){
        this.anchorPercent = 1.0f + percent;
        this.delaY = maxBezierLength * percent;
        this.mCurrentFooterRadius = (1 - percent >= 0.25f? 1- percent:0.25f) * (exactlySize / 2.0f - ringWidth);
        float p = 1.0f - percent / 3.0f;
        this.arrowRadius = exactlySize / 4.5f * p;
        this.triangleLength = applyDimension(TRIANGLE_LENGTH) * p;
        this.arcWidth = applyDimension(INNER_ARC_WIDTH) * p;
        this.mCurrentHeaderRadius = p * (exactlySize / 2.0f - ringWidth);
    }

    /**
     * 贝塞尔曲线返回
     */
    public void bezierBack(){
        currentPercent = currentPercent >= 1.0f?1.0f:currentPercent;
        LoadPreView.this.post(mRunnable);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            LoadPreView.this.removeCallbacks(this);
            if (currentPercent > 0.05f) {
                setDelay(maxBezierLength * currentPercent);
                LoadPreView.this.post(mRunnable);
                currentPercent -= 0.05f;
            }else{
                currentPercent = 0f;
                setDelay(0);
                Log.d(TAG,"loadListener != null:"+(loadListener != null)+",canLoad = "+canLoad);
                if(loadListener != null && canLoad){
                    canLoad = false;
                    loadListener.loadStart();
                }
            }
        }
    };

    /**
     * @param delay，绘制贝塞尔曲线的长度
     */
    public void setDelay(float delay){
        if(!canLoad){
            setPercent(delay / maxBezierLength);
        }
        modityViewSize((int) (originHeight + delay));
    }

    public void setLoadListener(LoadListener loadListener){
        this.loadListener = loadListener;
    }

    public interface LoadListener{
        void loadStart();
    }
}
