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
import android.util.TypedValue;
import android.view.View;

/**
 * Created by dandy on 2016/6/2.
 */
public class LoadSuccessView extends View{

    private static final String TAG = "LoadSuccessView";

    private static final float HEIGHT = 6.5f;//默认高度

    private static final int COLOR = Color.parseColor("#5a5a5a");//起始颜色

    private static final int PADDING = 4;

    private static final int TEXTSIZE = 11;

    private static final String TEXT = "刷新成功";

    private float radius;

    private int padding;

    private DisplayMetrics dm;

    private Paint paint,textPaint;

    public LoadSuccessView(Context context){
        this(context,null);
    }

    public LoadSuccessView(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        setUpInit();
    }

    private void setUpInit(){
        dm = Resources.getSystem().getDisplayMetrics();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(COLOR);
        paint.setStrokeWidth(applyDimension(1.0f));

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setColor(COLOR);
        textPaint.setTextSize(applyDimension(TEXTSIZE));

        radius = applyDimension(HEIGHT);
        padding = applyDimension(PADDING);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**计算宽**/
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode == MeasureSpec.UNSPECIFIED || widthMode == MeasureSpec.AT_MOST){
            width =(int)(radius*2+padding*3+textPaint.measureText(TEXT));
        }

        /**计算高**/
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            height = (int)(radius+padding)*2;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawText(canvas);
        drawRightLogo(canvas);
        drawCircle(canvas);
    }

    /**
     * 绘制加载成功logo
     * @param canvas
     */
    private void drawCircle(Canvas canvas){
        canvas.save();
        canvas.translate(padding + radius, padding + radius);
        canvas.drawCircle(0, 0, radius, paint);
        canvas.restore();
    }

    /**
     * 绘制对号
     * @param canvas
     */
    private void drawRightLogo(Canvas canvas){
        canvas.save();
        canvas.translate(padding + radius, padding + radius);
        Path path = new Path();
        path.moveTo(-radius*0.65f,0);
        path.lineTo(-radius * 0.15f, radius*0.45f);
        path.lineTo(radius * 0.75f, -radius*0.45f);
        canvas.drawPath(path,paint);
        canvas.restore();
    }

    /**
     * 绘制加载成功文字提示
     * @param canvas
     */
    private void drawText(Canvas canvas){
        canvas.save();
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        RectF targetRect = new RectF((padding + radius)*2,0,getWidth(),getHeight());
        int baseLine =  (int)((targetRect.bottom + targetRect.top - fontMetrics.bottom - fontMetrics.top) / 2);
        canvas.translate((padding + radius)*2,baseLine);
        canvas.drawText(TEXT,0,0,textPaint);
        canvas.restore();
    }

    /**
     * px2dp
     * @param value
     */
    private int applyDimension(float value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm);
    }

}
