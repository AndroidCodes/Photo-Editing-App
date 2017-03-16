package com.example.androidcodes.photoeditingapp.Frames.CustomGallery;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidcodes.photoeditingapp.Frames.Common.Common;
import com.example.androidcodes.photoeditingapp.Frames.CustomGallery.NewAdapters.SelectedImageAdapter;
import com.example.androidcodes.photoeditingapp.Frames.Fragments.Fragement_Activity;
import com.example.androidcodes.photoeditingapp.MainActivity;
import com.example.androidcodes.photoeditingapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peacock on 28/6/16.
 */
public class New_CustomGallery extends FragmentActivity implements
        New_AlbumImagesFragment.OnItemClickListener, View.OnClickListener {

    private Activity activity;

    private RecyclerView rv_selectedImage;

    private TextView tv_headerText, tv_selectedImgCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.new_gallery);

        activity = New_CustomGallery.this;

        rv_selectedImage = (RecyclerView) findViewById(R.id.rv_selectedImage);

        Common.selected = new ArrayList<>();

        findViewById(R.id.iv_back).setOnClickListener(this);

        tv_headerText = (TextView) findViewById(R.id.tv_headerText);
        tv_headerText.setText(R.string.gallery);

        tv_selectedImgCount = (TextView) findViewById(R.id.tv_selectedImgCount);
        tv_selectedImgCount.setText(getString(R.string.selecteImgs) + " 0");

        findViewById(R.id.iv_next).setOnClickListener(this);

        getSupportFragmentManager().beginTransaction().
                add(R.id.fl_gallery, new New_AlbumFragment(), "listAlbums").addToBackStack(null).
                commit();

    }

    public TextView getHeaderText() {

        return this.tv_headerText;

    }

    @Override
    public void onBackPressed() {

        if (getSupportFragmentManager().findFragmentById(R.id.fl_gallery)
                instanceof New_AlbumImagesFragment) {

            getSupportFragmentManager().popBackStack();

        } else {

            Common.changeActivity(activity, MainActivity.class, true);

        }
    }

    @Override
    public void onItemClick(String selectedImagePath) {

        if (new File(selectedImagePath).exists() == true &&
                new File(selectedImagePath).length() > 0) {

            if (Common.selected != null && Common.selected.size() <= 4) {

                tv_selectedImgCount.setText(getString(R.string.selecteImgs) + " " +
                        (Common.selected.size() + 1));

                Common.selected.add(selectedImagePath);

                SelectedImageAdapter adapter = new SelectedImageAdapter(activity, Common.selected,
                        null, tv_selectedImgCount);

                rv_selectedImage.setHasFixedSize(true);
                rv_selectedImage.setLayoutManager(new LinearLayoutManager(activity,
                        LinearLayoutManager.HORIZONTAL, false));
                rv_selectedImage.setAdapter(adapter);
                rv_selectedImage.scrollToPosition(adapter.getItemCount() - 1);

            } else {

                Snackbar.make(rv_selectedImage, R.string.warning, Snackbar.LENGTH_SHORT).show();

            }

        } else {

            Snackbar.make(rv_selectedImage, R.string.imgNotFound, Snackbar.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case 111: {

                for (int i = 0; i < permissions.length; i++) {

                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                        System.out.println("Permissions --> " + "Permission Granted: " +
                                permissions[i]);

                        if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            Common.editSharedPreferences("storage", "true");

                            if (Common.isStorageAvailable() != true) {

                                Snackbar.make(findViewById(R.id.fl_layout), R.string.mountSDcard,
                                        Snackbar.LENGTH_LONG).show();

                            }
                        }
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                        System.out.println("Permissions --> " + "Permission Denied: " +
                                permissions[i]);

                        if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            Common.editSharedPreferences("storage", "false");

                        }
                    }
                }
            }

            break;

            default: {

                break;

            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_back:

                if (getSupportFragmentManager().findFragmentById(R.id.fl_gallery)
                        instanceof New_AlbumImagesFragment) {

                    tv_headerText.setText(R.string.gallery);

                    getSupportFragmentManager().popBackStack();

                } else {

                    Common.changeActivity(activity, MainActivity.class, true);

                }

                break;

            case R.id.iv_next:

                if (Common.selected.size() > 0) {

                    Common.selected_bitmaps = new ArrayList<Bitmap>();

                    Common.num_of_selected_images = Common.selected.size();

                    if (Common.num_of_selected_images == 3 ||
                            Common.num_of_selected_images == 4 ||
                            Common.num_of_selected_images == 5) {

                        for (int i = 0; i < Common.selected.size(); i++) {

                            Bitmap bmp = scaleImage(Common.selected.get(i).toString());

                            Common.selected_bitmaps.add(bmp);

                        }

                        Common.changeActivity(activity, Fragement_Activity.class, false);

                    } else {

                        Snackbar.make(rv_selectedImage, R.string.warning,
                                Snackbar.LENGTH_SHORT).show();

                    }
                } else {

                    Toast.makeText(activity, getString(R.string.selectImgsFirst),
                            Toast.LENGTH_LONG).show();

                }

                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            checkPermission();

        }

        if (Common.isStorageAvailable() != true) {

            Snackbar.make(findViewById(R.id.fl_layout), R.string.mountSDcard,
                    Snackbar.LENGTH_LONG).show();

        }
    }


    private Bitmap scaleImage(String imagePath) {

        Bitmap selected_bimap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);

        final int REQUIRED_SIZE = 1080;

        int scale = 1;

        while (options.outWidth / scale / 2 >= REQUIRED_SIZE &&
                options.outHeight / scale / 2 >= REQUIRED_SIZE) scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;

        return (selected_bimap = BitmapFactory.decodeFile(imagePath, options));

    }

    private void checkPermission() {

        int hasWritePermission = checkSelfPermission(Manifest.permission.
                WRITE_EXTERNAL_STORAGE);
        int hasReadPermission = checkSelfPermission(Manifest.permission.
                READ_EXTERNAL_STORAGE);

        List<String> permissions = new ArrayList<String>();
        if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {

            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        } else {

            Common.editSharedPreferences("storage", "true");

        }

        if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {

            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        } else {

            Common.editSharedPreferences("storage", "true");

        }

        if (!permissions.isEmpty()) {

            requestPermissions(permissions.toArray(new String[permissions.size()]), 111);

        }
    }
}
