package com.example.mytest7_2;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.media.AudioAttributes;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class YikuActivity extends Activity {
    // 桌面的宽度
    private int tableWidth;
    // 桌面的高度
    private int tableHeight;
    // 球拍的垂直位置
    private int racketY;
    // 下面定义球拍的高度和宽度
    private final int RACKET_HEIGHT = 30;
    private final int RACKET_WIDTH = 90;
    // 小球的大小
    private final int BALL_SIZE = 16;
    // 小球纵向的运行速度
    private int ySpeed = 20;
    Random rand = new Random();
    // 返回一个-0.5~0.5的比率，用于控制小球的运行方向
    private double xyRate = rand.nextDouble() - 0.5;
    // 小球横向的运行速度
    private int xSpeed = (int) (ySpeed * xyRate * 2);
    // ballX和ballY代表小球的坐标
    private int ballX = rand.nextInt(200) + 20;
    private int ballY = rand.nextInt(10) + 20;
    // racketX代表球拍的水平位置
    private int racketX = rand.nextInt(200);
    // 游戏是否结束的旗标
    private boolean isLose = false;

    private SoundPool soundPool;
    //private int id;
    HashMap<Integer, Integer> soundMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        // 去掉窗口标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 创建GameView组件
        final GameView gameView = new GameView(this);

        setContentView(R.layout.activity_main);
        // 获取窗口管理器
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        // 获得屏幕宽和高
        tableWidth = metrics.widthPixels - 80;
        tableHeight = metrics.heightPixels;
        Log.d("1", tableHeight + "");
        Log.d("1", tableWidth + "");
        racketY = (int) tableHeight * 3 / 4 - 80;

        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        ll.addView(gameView);


        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x123) {
                    gameView.invalidate();
                }
            }
        };
/*        gameView.setOnKeyListener(new OnKeyListener() // ②
        {
            @Override
            public boolean onKey(View source, int keyCode, KeyEvent event) {
                // 获取由哪个键触发的事件
                switch (event.getKeyCode()) {
                    // 控制挡板左移
                    case KeyEvent.KEYCODE_A:
                        if (racketX > 0) racketX -= 10;
                        break;
                    // 控制挡板右移
                    case KeyEvent.KEYCODE_D:
                        if (racketX < tableWidth - RACKET_WIDTH) racketX += 10;
                        break;
                }
                // 通知gameView组件重绘
                gameView.invalidate();
                return true;
            }
        });
*/

        //由于提示音的加载时需要时间的，因此我们将其放在onCreate方法中
        int version = Build.VERSION.SDK_INT;
        if (version > 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setMaxStreams(2);
            //设置声音的类型
            AudioAttributes.Builder audioAttributesBuilder = new AudioAttributes.Builder();
            audioAttributesBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            AudioAttributes aa = audioAttributesBuilder.build();
            builder.setAudioAttributes(aa);
            soundPool = builder.build();
        } else {
            //第一个参数是最大声音数，第二个参数是声音的类型，第三个参数是声音的品质。
            soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        }
        //加载音频
        soundMap.put(1, soundPool.load(this, R.raw.start, 1));
        soundMap.put(2, soundPool.load(this, R.raw.over, 1));
        soundMap.put(3, soundPool.load(this,R.raw.playbutton,1));
        //id =  soundPool.load(getApplicationContext(), R.raw.start, 1);


        Button left = (Button) findViewById(R.id.left);
        Button right = (Button) findViewById(R.id.right);
        Button button = (Button) findViewById(R.id.button);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (racketX > 0) racketX -= 30;
                gameView.invalidate();

            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (racketX < tableWidth - RACKET_WIDTH) racketX += 30;
                gameView.invalidate();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //音效
                soundPool.play(soundMap.get(1), 1, 1, 0, 0, 1);
                recreate();
            }
        });


        final Timer timer = new Timer();
        timer.schedule(new TimerTask() // ①
        {
            @Override
            public void run() {
                // 如果小球碰到左边边框
                if (ballX <= 0 || ballX >= tableWidth - BALL_SIZE) {
                    xSpeed = -xSpeed;
                    //音效
                    soundPool.play(soundMap.get(3), 1, 1, 0, 0, 1);
                }
                // 如果小球高度超出了球拍位置，且横向不在球拍范围之内，游戏结束
                if (ballY >= racketY - BALL_SIZE
                        && (ballX < racketX || ballX > racketX
                        + RACKET_WIDTH)) {
                    timer.cancel();
                    // 设置游戏是否结束的旗标为true
                    isLose = true;
                }
                // 如果小球位于球拍之内，且到达球拍位置，小球反弹
                else if (ballY <= 0
                        || (ballY >= racketY - BALL_SIZE
                        && ballX > racketX && ballX <= racketX
                        + RACKET_WIDTH)) {
                    ySpeed = -ySpeed;
                    //音效
                    soundPool.play(soundMap.get(3), 1, 1, 0, 0, 1);
                }
                // 小球坐标增加
                ballY += ySpeed;
                ballX += xSpeed;
                // 发送消息，通知系统重绘组件
                handler.sendEmptyMessage(0x123);
            }
        }, 0, 100);//100：小球重绘间隔

    }

    class GameView extends View {
        Paint paint = new Paint();

        public GameView(Context context) {
            super(context);
            setFocusable(true);
        }

        // 重写View的onDraw方法，实现绘画
        public void onDraw(Canvas canvas) {
            paint.setStyle(Paint.Style.FILL);
            // 设置去锯齿
            paint.setAntiAlias(true);
            // 如果游戏已经结束
            if (isLose) {
                paint.setColor(Color.RED);
                paint.setTextSize(40);
                canvas.drawText("游戏已结束", tableWidth / 2 - 100, 200, paint);
                //音效
                soundPool.play(soundMap.get(2),1,1,0,0,1);
            }
            // 如果游戏还未结束
            else {
                // 设置颜色，并绘制小球
                paint.setColor(Color.rgb(255, 0, 0));
                canvas.drawCircle(ballX, ballY, BALL_SIZE, paint);
                // 设置颜色，并绘制球拍
                paint.setColor(Color.rgb(80, 80, 200));
                canvas.drawRect(racketX, racketY, racketX + RACKET_WIDTH,
                        racketY + RACKET_HEIGHT, paint);
            }
        }
    }
}

