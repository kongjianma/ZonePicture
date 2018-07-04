package com.kongjianma.picture;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kongjianma.picture.util.ShareUtil;
import com.kongjianma.picture.widget.BlockRelativeLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.brl_show)
    BlockRelativeLayout brlShow;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_preview)
    Button btnPreview;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private String[] colorstring = new String[] { "天蓝", "偏红", "偏黄", "紫色", "偏灰", "深灰", "浅黄", "深绿", "浅紫" };
    private int[] colors = new int[] { R.color.tianlan, R.color.pianhong,
            R.color.pianhuang, R.color.zise, R.color.pianhui, R.color.shenhui,
            R.color.qianhuang, R.color.shenlv, R.color.qianzi };
    private int currentColorIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FabActivity.class));
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("选择主题颜色")
                    .setSingleChoiceItems(
                            colorstring, currentColorIndex, new DialogInterface.OnClickListener() {
                                public void onClick( DialogInterface dialog, int which) {
                                    brlShow.setBackgroundColor(getResources().getColor(colors[which]));
                                    currentColorIndex = which;
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("取消", null).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Uri uri = Uri.parse("https://github.com/kongjianma/ZonePicture");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(MainActivity.this, AboutActivity.class));
        } else if (id == R.id.nav_share) {
            shareList();
        } else if (id == R.id.nav_send) {
            startActivity(new Intent(MainActivity.this, FabActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void shareList() {
        final String items[] = {"微信好友", "微信朋友圈", "微博", "QQ好友", "其他途径"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("选择分享途径").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                String content = "我正在使用小图片APP，有源码可以查看，地址是：http://github.com/kongjianma/ZonePicture";
                switch (which) {
                    case 0:
                        ShareUtil.wechatFrient(MainActivity.this, content);
                        break;
                    case 1:
                        ShareUtil.wechat(MainActivity.this, content, writeBytesToFile());
                        break;
                    case 2:
                        ShareUtil.weibo(MainActivity.this, content);
                        break;
                    case 3:
                        ShareUtil.qq(MainActivity.this, content);
                        break;
                    default:
                        ShareUtil.all(MainActivity.this, "来自小图片APP的分享", content);
                }

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        builder.create().show();
    }

    public File writeBytesToFile() {
        try {
            InputStream is = getAssets().open("share.jpg");
            File file = new File(getExternalCacheDirectory(this, null), System.currentTimeMillis() + ".jpg");
            FileOutputStream fos = null;
            try {
                byte[] data = new byte[2048];
                int nbread = 0;
                fos = new FileOutputStream(file);
                while((nbread=is.read(data))>-1){
                    fos.write(data,0,nbread);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally{
                if (fos!=null){
                    fos.close();
                }
            }
            return file;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件夹类型 如果为空则返回 /storage/emulated/0/Android/data/app_package_name/cache
     *             否则返回对应类型的文件夹如Environment.DIRECTORY_PICTURES 对应的文件夹为 .../data/app_package_name/files/Pictures
     */
    public static File getExternalCacheDirectory(Context context, String type) {
        File appCacheDir = null;
        if( Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            if (TextUtils.isEmpty(type)){
                appCacheDir = context.getExternalCacheDir();
            }else {
                appCacheDir = context.getExternalFilesDir(type);
            }

            if (appCacheDir == null){// 有些手机需要通过自定义目录
                appCacheDir = new File(Environment.getExternalStorageDirectory(),"Android/data/"+context.getPackageName()+"/cache/"+type);
            }

            if (appCacheDir == null){
                Log.e("getExternalDirectory","getExternalDirectory fail ,the reason is sdCard unknown exception !");
            }else {
                if (!appCacheDir.exists()&&!appCacheDir.mkdirs()){
                    Log.e("getExternalDirectory","getExternalDirectory fail ,the reason is make directory fail !");
                }
            }
        }else {
            Log.e("getExternalDirectory","getExternalDirectory fail ,the reason is sdCard nonexistence or sdCard mount fail !");
        }
        return appCacheDir;
    }


    @OnClick(R.id.tv_content) void content() {
        final EditText editText = new EditText(this);
        editText.setText(tvContent.getText().toString());
        new AlertDialog.Builder(this)
                .setTitle("请输入内容")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvContent.setText(editText.getText().toString());
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @OnClick(R.id.tv_author) void author() {
        final EditText editText = new EditText(this);
        editText.setText(tvAuthor.getText().toString());
        new AlertDialog.Builder(this)
                .setTitle("请输入作者")
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvAuthor.setText(editText.getText().toString());
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @OnClick(R.id.btn_clean) void clean() {
        tvContent.setText("");
        tvAuthor.setText("");
    }

    @OnClick(R.id.btn_preview) void preview(View view) {
        String content = tvContent.getText().toString();
        String author = tvAuthor.getText().toString();
        if(TextUtils.isEmpty(content)) {
            Snackbar.make(view, "内容不能为空", Snackbar.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(MainActivity.this, PreviewActivity.class);
        intent.putExtra("content", tvContent.getText().toString());
        intent.putExtra("author", tvAuthor.getText().toString());
        intent.putExtra("color", colors[currentColorIndex]);
        startActivity(intent);
    }

    @OnClick(R.id.btn_save) void save(View view) {
        String[] PERMISSIONS = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE" };
        int permission = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS,1);
        }
        String content = tvContent.getText().toString();
        String author = tvAuthor.getText().toString();
        if(TextUtils.isEmpty(content)) {
            Snackbar.make(view, "内容不能为空", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(author)) {
            tvAuthor.setVisibility(View.GONE);
        }
        if(saveImg(brlShow)) {
            Snackbar.make(view, "已保存到相册", Snackbar.LENGTH_SHORT).show();
        } else {
            Snackbar.make(view, "保存失败，建议进行运行反馈", Snackbar.LENGTH_SHORT).show();
        }
        tvAuthor.setVisibility(View.VISIBLE);
    }

    private boolean saveImg(View view) {
        File file;
        String fileName;
        String name = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        if(Build.BRAND.equals("Xiaomi") ){ // 小米手机
            fileName = Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/"+name;
        }else {  // Meizu 、Oppo
            fileName = Environment.getExternalStorageDirectory().getPath()+"/DCIM/"+name;
        }
        file = new File(fileName);
        if(file.exists()) {
            file.delete();
        }
        view.setDrawingCacheEnabled(true);
        try {
            Bitmap bitmap = view.getDrawingCache();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), name, null);
            fos.flush();
            fos.close();
            this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
