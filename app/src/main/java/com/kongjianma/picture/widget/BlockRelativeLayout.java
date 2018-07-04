package com.kongjianma.picture.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 定义一个方形的布局。
 * 如果使用常规的RelativeLayout，需要在代码中进行设置，实现和这个自定义布局相似的效果
 * WindowManager wm = this.getWindowManager();
 * int width = wm.getDefaultDisplay().getWidth();
 * final RelativeLayout rlShow = (RelativeLayout) findViewById(R.id.rl_show);
 * LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) rlShow.getLayoutParams();
 * linearParams.height = width;
 * rlShow.setLayoutParams(linearParams);
 */
public class BlockRelativeLayout extends RelativeLayout {

    public BlockRelativeLayout(Context context) {
        super(context);
    }

    public BlockRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlockRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

}
