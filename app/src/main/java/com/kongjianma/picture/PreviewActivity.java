package com.kongjianma.picture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.kongjianma.picture.widget.BlockRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PreviewActivity extends AppCompatActivity {

    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.brl_show)
    BlockRelativeLayout brlShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);

        String content = getIntent().getStringExtra("content");
        String author = getIntent().getStringExtra("author");
        int color = getIntent().getIntExtra("color", R.color.tianlan);
        tvContent.setText(content);
        tvAuthor.setText(author);
        brlShow.setBackgroundColor(getResources().getColor(color));
    }

}
