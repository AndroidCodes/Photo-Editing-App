package com.example.androidcodes.photoeditingapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidcodes.photoeditingapp.Frames.Adapters.All_Images_Adapter;
import com.example.androidcodes.photoeditingapp.Frames.Adapters.ItemDecorView;
import com.example.androidcodes.photoeditingapp.Frames.Common.Common;
import com.example.androidcodes.photoeditingapp.PIP.Common.PIP_Common;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peacock on 28/4/16.
 */
public class Album_Activity extends AppCompatActivity implements View.OnClickListener {

    private File[] listFile;

    private RecyclerView rv_album;

    private ImageView iv_next;

    private All_Images_Adapter adapter;

    //private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.albums);

        findViewById(R.id.iv_back).setOnClickListener(this);

        /*iv_next = (ImageView) */
        findViewById(R.id.iv_next).setVisibility(View.GONE);
        /*iv_next.setImageResource(R.drawable.back);
        iv_next.setRotation(180.0f);
        iv_next.setOnClickListener(this);*/

        TextView tv_headerText = (TextView) findViewById(R.id.tv_headerText);
        tv_headerText.setText(getString(R.string.my_album));
        tv_headerText.setTextColor(getResources().getColor(R.color.frame_inside_color));

        rv_album = (RecyclerView) findViewById(R.id.rv_album);

        if (PIP_Common.tempFlag == true) {

            new GetAllImagesFromSDCard().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        } else {

            if (Common.all_images_paths.size() > 0) {

                rv_album.setLayoutManager(new GridLayoutManager(Album_Activity.this, 2,
                        GridLayout.VERTICAL, false));
                rv_album.setHasFixedSize(true);
                rv_album.addItemDecoration(new ItemDecorView(getResources().
                        getDimensionPixelSize(R.dimen.spacing)));

                adapter = new All_Images_Adapter(Album_Activity.this,
                        Common.all_images_bitmaps);
                rv_album.setAdapter(adapter);
                //rv_album.addItemDecoration(new ItemDecorView(10));

                adapter.setOnItemClickListener(new All_Images_Adapter.OnItemClickListener() {

                    @Override
                    public void OnItemClick(View view, int position) {

                        Common.show_selected_image = position;

                        startActivity(new Intent(Album_Activity.this,
                                View_AlbumActivity.class));

                        overridePendingTransition(R.anim.right_in, R.anim.left_out);

                    }
                });
            } else {

                findViewById(R.id.iv_noImgFound).setVisibility(View.VISIBLE);

                rv_album.setVisibility(View.GONE);

            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_back:

                Common.changeActivity(Album_Activity.this, MainActivity.class, true);

                /*finish();

                intent = new Intent(Album_Activity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                overridePendingTransition(R.anim.left_in, R.anim.right_out);*/

                break;

            /*case R.id.iv_next:

                Common.changeActivity(Album_Activity.this, View_AlbumActivity.class, false);

                finish();

                Common.show_selected_image = new Random().nextInt(Common.all_images_paths.size());

                intent = new Intent(Album_Activity.this, View_AlbumActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                overridePendingTransition(R.anim.right_in, R.anim.left_out);

                break;*/

            default:

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Common.changeActivity(Album_Activity.this, MainActivity.class, true);

        /*finish();

        intent = new Intent(Album_Activity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        overridePendingTransition(R.anim.left_in, R.anim.right_out);*/

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasWritePermission = checkSelfPermission(Manifest.permission.
                    WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = checkSelfPermission(Manifest.permission.
                    READ_EXTERNAL_STORAGE);

            List<String> permissions = new ArrayList<String>();
           /* if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            }*/

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            }

            if (!permissions.isEmpty()) {

                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case 111: {

                for (int i = 0; i < permissions.length; i++) {

                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                        System.out.println("Permissions --> " + "Permission Granted: " +
                                permissions[i]);

                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                        System.out.println("Permissions --> " + "Permission Denied: " +
                                permissions[i]);

                    }
                }
            }

            break;

            default: {

                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            }
        }
    }

    private class GetAllImagesFromSDCard extends AsyncTask<Void, Void, Void> {

        KProgressHUD pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new KProgressHUD(Album_Activity.this).setCornerRadius(4.0f).
                    setCancellable(false).setDimAmount(5.0f).
                    setWindowColor(Color.parseColor("#85000000")).show();

        }

        @Override
        protected Void doInBackground(Void... params) {

            Common.getImagesFromExternalStorage();

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    pd.dismiss();

                    if (Common.all_images_paths.size() > 0) {

                        rv_album.setLayoutManager(new GridLayoutManager(Album_Activity.this, 2,
                                GridLayout.VERTICAL, false));
                        rv_album.setHasFixedSize(true);
                        rv_album.addItemDecoration(new ItemDecorView(getResources().
                                getDimensionPixelSize(R.dimen.spacing)));

                        adapter = new All_Images_Adapter(Album_Activity.this,
                                Common.all_images_bitmaps);
                        rv_album.setAdapter(adapter);
                        //rv_album.addItemDecoration(new ItemDecorView(10));

                        adapter.setOnItemClickListener(new All_Images_Adapter.OnItemClickListener() {

                            @Override
                            public void OnItemClick(View view, int position) {

                                Common.show_selected_image = position;

                                startActivity(new Intent(Album_Activity.this,
                                        View_AlbumActivity.class));

                                overridePendingTransition(R.anim.right_in, R.anim.left_out);

                            }
                        });
                    } else {

                        findViewById(R.id.iv_noImgFound).setVisibility(View.VISIBLE);

                        rv_album.setVisibility(View.GONE);

                    }
                }
            });
        }
    }
}
