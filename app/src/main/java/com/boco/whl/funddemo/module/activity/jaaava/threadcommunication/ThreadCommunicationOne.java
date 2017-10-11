package com.boco.whl.funddemo.module.activity.jaaava.threadcommunication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.boco.whl.funddemo.R;
import com.boco.whl.funddemo.module.activity.jaaava.threadcommunication.thread.MyConsumer;
import com.boco.whl.funddemo.module.activity.jaaava.threadcommunication.thread.MyProducer;
import com.boco.whl.funddemo.utils.LogUtil;
import com.boco.whl.funddemo.utils.SharedPreferencesUtil;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * <pre>
 *  author : honglei92
 *  desc :
 *  blog :
 *  createtime : 2017/6/19 0019 17:20
 *  updatetime : 2017/6/19 0019 17:20
 * </pre>
 */
public class ThreadCommunicationOne extends Activity {
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.btn8)
    Button btn8;
    private Context context = ThreadCommunicationOne.this;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 123) {
                ToastUtils.showShort("我收到了子线程的handler消息");
            }
        }
    };
    private static final String COMMUNICATION_ACTION = "action.communication";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threadcommunicationone);
        ButterKnife.bind(this);
        wayOne();

    }

    /**
     * way1  SharedPreference
     */
    private void wayOne() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtils.d("Thread", Thread.currentThread().getName());
                SharedPreferencesUtil.setLoginUserPhone(context, "13982268713");
            }
        }).start();
    }

    /**
     * 管道通信
     */
    private void wayTwo() {
        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream();
        try {
            pis.connect(pos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new MyProducer(pos).start();
        new MyConsumer(pis).start();

    }

    /**
     * handler通信
     */
    private void wayThree() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(123);
            }
        }).start();
    }

    /**
     * runOnUIThread
     */
    private void wayFour() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String KEY = "oppo";
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort("我来自子线程RunOnUIThread" + KEY);
                    }
                });
            }
        }).start();

    }

    /**
     * postDelayed
     */
    private void wayFive() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LogUtil.d("post0  " + Thread.currentThread().getName());
                new Handler(context.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn5.setText("postDelayed");
                        btn5.setVisibility(View.VISIBLE);
                        LogUtil.d("post1  " + Thread.currentThread().getName());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LogUtil.d("post2  " + Thread.currentThread().getName());
                                btn5.setVisibility(View.GONE);
                            }
                        }, 2000);
                    }
                }, 1000);
            }
        }).start();
    }

    /**
     * 循环
     */
    private void waySex() {
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                LogUtils.d("post", "Heart Beat!");
                handler.postDelayed(this, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);
    }

    private void waySeven() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setAction(COMMUNICATION_ACTION);
                intent.putExtra("name", "peach form" + Thread.currentThread().getName());
                sendBroadcast(intent);
            }
        }).start();
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                LogUtils.d("Thread", Thread.currentThread().getName());
                ToastUtils.showShort(SharedPreferencesUtil.getPreLoginUserPhone(context));
                break;
            case R.id.btn2:
                wayTwo();
                break;
            case R.id.btn3:
                wayThree();
                break;
            case R.id.btn4:
                wayFour();
                break;
            case R.id.btn5:
                wayFive();
                break;
            case R.id.btn6:
                waySex();
                break;
            case R.id.btn7:
                waySeven();
                break;
            case R.id.btn8:
                break;
        }
    }


}