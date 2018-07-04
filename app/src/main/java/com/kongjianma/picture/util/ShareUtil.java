package com.kongjianma.picture.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;

public class ShareUtil {

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";
    private static Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

    /**
     * 分享到原生支持的全部途径（没有微信朋友圈）
     * @param title 标题
     * @param content 文本内容
     */
    public static void all(Context context, String title, String content) {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setType("text/plain");//设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_SUBJECT, title);//添加分享内容标题
        share_intent.putExtra(Intent.EXTRA_TEXT, content);//添加分享内容
        //创建分享的Dialog
        share_intent = Intent.createChooser(share_intent, "");
        context.startActivity(share_intent);
    }

    /**
     * 分享给微信好友
     *
     * @param content 分享的文本内容
     */
    public static void wechatFrient(Context context, String content) {
        Intent intent = new Intent();
        ComponentName componentName = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI");
        intent.setComponent(componentName);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(intent);
    }

    /**
     * 分享到朋友圈
     *
     * @param content 分享的文本内容
     * @param imgFile 图片。分享到朋友圈必须有图片
     */
    public static void wechat(Context context, String content, File imgFile) {
        Intent intent = new Intent();
        ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
        intent.setComponent(comp);
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("Kdescription", content);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imgFile));//uri为你要分享的图片的uri
        context.startActivity(intent);
    }

    /**
     * 分享到微博
     *
     * @param content 分享的文本内容
     */
    public static void weibo(Context context, String content) {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);//设置分享行为
        share_intent.setPackage("com.sina.weibo");
        share_intent.setType("text/plain");//设置分享内容的类型
        share_intent.putExtra(Intent.EXTRA_TEXT, content);//添加分享内容
        context.startActivity(share_intent);
    }

    /**
     * 分享到qq好友
     *
     * @param content 分享的文本内容
     */
    public static void qq(Context context, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        ComponentName comp = new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity");
        intent.setComponent(comp);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(intent);
    }

}
