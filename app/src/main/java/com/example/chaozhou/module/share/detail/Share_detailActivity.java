package com.example.chaozhou.module.share.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chaozhou.R;
import com.example.chaozhou.api.bean.ShareListInfo;
import com.example.chaozhou.api.service.Comment_Service;
import com.example.chaozhou.module.home.MainActivity;
import com.example.chaozhou.module.share.Topic;
import com.example.chaozhou.utils.DefIconFactory;
import com.example.chaozhou.utils.ImageLoader;
import com.example.chaozhou.widget.CommentLayout;
import com.example.chaozhou.widget.FlowImageLayout;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;

import static com.example.chaozhou.AndroidApplication.getContext;

public class Share_detailActivity extends AppCompatActivity {

    private static final String INPUT = "input";
    private String pictureBaseUrl = "https://chouzhou-1256247322.cos-website.ap-guangzhou.myqcloud.com/";

    private Button titleLeft;
    private TextView titleCenter;
    private Button titleRight;
    private CommentLayout tvCommentList;

    private Context mContext;
    private ImageView user_image;
    private TextView user_name;
    private TextView topic_content;
    private FlowImageLayout image_layout;
    private TextView content_time;
    private ImageButton comment_button;
    private Topic topic;
    private boolean isoncl=true;
    private String isonclid1;
    private String isonclid2 = "00";
    private Long id = Long.valueOf(1);
    public static String shareuhhid;

    public static void launch(Context context, String input) {
        Intent intent = new Intent(context, Share_detailActivity.class);
        intent.putExtra(INPUT, input);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.fade_entry, R.anim.hold);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_detail);
        initView();

        mContext = getContext();
        shareuhhid = getIntent().getStringExtra("shareid");
        getsharebyid(shareuhhid);
        tvCommentList = new CommentLayout(this);
        tvCommentList.loadData(shareuhhid);
    }

    private void initView() {
        titleLeft = findViewById(R.id.title_left);
        titleCenter = findViewById(R.id.title_center);
        titleRight = findViewById(R.id.title_right);
        tvCommentList = findViewById(R.id.tv_comment_list);
        user_image = findViewById(R.id.user_image);
        user_name = findViewById(R.id.user_name);
        topic_content = findViewById(R.id.topic_content);
        image_layout =  findViewById(R.id.image_layout);
        content_time =  findViewById(R.id.content_time);
        comment_button = findViewById(R.id.image_button_message);
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleCenter.setText("详情");
        titleRight.setVisibility(View.GONE);
    }


    private Topic getsharebyid(String sharehhid){

          Comment_Service.getoneShareInfo(sharehhid)
                .subscribe(new Subscriber<ShareListInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.toString());
                    }

                    @Override
                    public void onNext(ShareListInfo top) {
                        topic = top.getData();
                        initinfo(topic);
                    }
                });
          return topic;
      }

    private void initinfo(final Topic topic){
          if (topic.getHead()==null){
              topic.setHead(MainActivity.user.getHead());
          }
          ImageLoader.loadCenterCrop(getContext(), pictureBaseUrl + topic.getHead(), user_image, DefIconFactory.provideIcon());
          user_name.setText(topic.getUname());
          topic_content.setText(topic.getShatext());
          content_time.setText(topic.getShatime());

          if (topic.getImgs() != null && topic.getImgs().size() > 0) {
              image_layout.setVisibility(View.VISIBLE);
              image_layout.loadImage(topic.getImgs().size(), new FlowImageLayout.OnImageLayoutFinishListener() {
                  @Override
                  public void layoutFinish(List<ImageView> images) {
                      for (int i = 0; i < images.size(); i++) {
                          ImageLoader.loadCenterCrop(getContext(), pictureBaseUrl + topic.getImgs().get(i), images.get(i), DefIconFactory.provideIcon());
                      }
                  }

              });


          } else {
              image_layout.setVisibility(View.GONE);
          }

          isonclid1 = topic.getShassthhId();
          comment_button.setVisibility(View.GONE);

      }


}
