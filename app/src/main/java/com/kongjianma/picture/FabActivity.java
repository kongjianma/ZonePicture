package com.kongjianma.picture;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.kongjianma.picture.mail.SendMailUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FabActivity extends AppCompatActivity {

    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_submit) void submit(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        String content = editContent.getText().toString();
        if(TextUtils.isEmpty(content)) {
            Snackbar.make(view, "反馈内容不能为空~", Snackbar.LENGTH_SHORT).show();
            return;
        }
        SendMailUtil.send("zhaoguojian@kongjianma.com", "来自安卓APP小图片的反馈", content);
        editContent.setText("");
        Snackbar.make(view, "反馈成功~", Snackbar.LENGTH_LONG).show();
    }

}
