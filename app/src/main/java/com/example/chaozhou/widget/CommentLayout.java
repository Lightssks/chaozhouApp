package com.example.chaozhou.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chaozhou.R;
import com.example.chaozhou.adapter.CommentAdapter;
import com.example.chaozhou.api.bean.CommentInfo;
import com.example.chaozhou.api.service.CommentService;
import com.example.chaozhou.module.home.MainActivity;
import com.example.chaozhou.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class CommentLayout extends LinearLayout {

    @BindView(R.id.rv_comment)
    RecyclerView rvComment;
    @BindView(R.id.cm_add)
    Button cmAdd;

    private static TextView cmNum;
    private static Context mContext;
    private static long uid ;
    private static String cid ;
    private static int state;
    private static CommentAdapter mAdapter;
    private static List<CommentInfo> data = new ArrayList<>();

    public CommentLayout(Context context) {
        super(context);
        mContext = context;
        uid = MainActivity.user.getUid();
    }

    public CommentLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        uid = MainActivity.user.getUid();
        initView();
    }

    public void initView() {
        View.inflate(mContext, R.layout.comment_list, this);
        ButterKnife.bind(this);
        cmNum = findViewById(R.id.cm_num);
        mAdapter = new CommentAdapter(R.layout.adapter_comment_list, data);
        rvComment.setLayoutManager(new LinearLayoutManager(mContext));
        rvComment.setAdapter(mAdapter);
    }

    public void loadData(String id) {
        cid = id;
        CommentService.getCommentsList(cid)
                .subscribe(new Subscriber<List<CommentInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.toString());
                    }

                    @Override
                    public void onNext(List<CommentInfo> commentInfos) {
                        mAdapter.data.clear();
                        mAdapter.addData(commentInfos);
                        cmNum.setText(mAdapter.data.size()+"条评论");
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }


    @OnClick(R.id.cm_add)
    public void onViewClicked() {
        String hint = "请输入你要评论的内容";
        showInputComment((Activity) mContext, hint, new CommentDialogListener() {
            @Override
            public void onClickPublish(final Dialog dialog, EditText input, final TextView btn) {
                final String content = input.getText().toString();
                if (content.trim().equals("")) {
                    ToastUtils.showToast("评论不能为空");
                    return;
                }
                btn.setClickable(false);
                subComments(content);
                if (state == 0) {
                    dialog.dismiss();
                    ToastUtils.showToast("评论成功");
                    loadData(cid);
                } else {
                    dialog.dismiss();
                    ToastUtils.showToast("评论失败，请换个姿势再试一次");
                }

            }

            @Override
            public void onShow(int[] inputViewCoordinatesInScreen) {

            }

            @Override
            public void onDismiss() {

            }
        });
    }

    /**
     * 弹出评论对话框
     */
    private static Dialog showInputComment(Activity activity, CharSequence hint, final CommentDialogListener listener) {
        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.comment_view_input);
        dialog.findViewById(R.id.input_comment_dialog_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onDismiss();
                }
            }
        });
        final EditText input = (EditText) dialog.findViewById(R.id.input_comment);
        input.setHint(hint);
        final TextView btn = (TextView) dialog.findViewById(R.id.btn_publish_comment);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClickPublish(dialog, input, btn);
                }
            }
        });
        dialog.setCancelable(true);
        dialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    int[] coord = new int[2];
                    dialog.findViewById(R.id.input_comment_container).getLocationOnScreen(coord);
                    // 传入 输入框距离屏幕顶部（不包括状态栏）的位置
                    listener.onShow(coord);
                }
            }
        }, 300);
        return dialog;
    }

    /**
     * 评论对话框相关监听
     */
    public interface CommentDialogListener {
        void onClickPublish(Dialog dialog, EditText input, TextView btn);

        /**
         * onShow在输入法弹出后才调用
         * @param inputViewCoordinatesOnScreen 输入框距离屏幕顶部（不包括状态栏）的位置[left,top]
         */
        void onShow(int[] inputViewCoordinatesOnScreen);

        void onDismiss();
    }

    public int subComments(String content) {
        CommentService.subComments(uid,cid,content)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        state = integer;
                    }
                });
        return state;
    }
}
