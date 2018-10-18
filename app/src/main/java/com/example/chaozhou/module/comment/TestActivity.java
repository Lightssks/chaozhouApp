package com.example.chaozhou.module.comment;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chaozhou.R;
import com.example.chaozhou.adapter.MomentAdapter;
import com.example.chaozhou.module.comment.model.Moment;
import com.example.chaozhou.module.comment.model.User;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    public static User sUser = new User(1, "走向远方"); // 当前登录用户

    private ListView mListView;
    private MomentAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.comment_dialog);
        Button rightBtu = (Button) findViewById(R.id.title_right);
        rightBtu.setVisibility(View.GONE);
        TextView textCenter = (TextView) findViewById(R.id.title_center);
        textCenter.setText("详情");
        Button leftBtu = (Button) findViewById(R.id.title_left);
        leftBtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mListView = (ListView) findViewById(R.id.list_moment);

        // 模拟数据
        ArrayList<Moment> moments = new ArrayList<Moment>();
        for (int i = 0; i < 20; i++) {
//            ArrayList<Comment> comments = new ArrayList<Comment>();
//            comments.add(new Comment(new User(i + 2, "用户" + i), "评论" + i, null));
//            comments.add(new Comment(new User(i + 200, "用户" + (i + 200)), "评论" + i, null));
//            comments.add(new Comment(new User(i + 300, "用户" + (i + 300)), "评论" + i, null));
//            moments.add(new Moment("动态 " + i, comments));
        }

        mAdapter = new MomentAdapter(this, moments, new CustomTagHandler(this, new CustomTagHandler.OnCommentClickListener() {
            @Override
            public void onCommentatorClick(View view, User commentator) {
                Toast.makeText(getApplicationContext(), commentator.mName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReceiverClick(View view, User receiver) {
                Toast.makeText(getApplicationContext(), receiver.mName, Toast.LENGTH_SHORT).show();
            }

            // 点击评论内容，弹出输入框回复评论
            @Override
            public void onContentClick(View view, User commentator, User receiver) {
                if (commentator != null && commentator.mId == sUser.mId) { // 不能回复自己的评论
                    return;
                }
                inputComment(view, commentator);
            }
        }));

        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"click "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void inputComment(final View v) {
        inputComment(v, null);
    }

    /**
     * 弹出评论对话框
     * @param v
     * @param receiver
     */
    public void inputComment(final View v, User receiver) {
        CommentFun.inputComment(TestActivity.this, mListView, v, receiver, new CommentFun.InputCommentListener() {
            @Override
            public void onCommitComment() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
