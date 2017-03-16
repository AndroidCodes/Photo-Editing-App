package com.example.androidcodes.photoeditingapp.Frames.CustomGallery.Temp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidcodes.photoeditingapp.Frames.Adapters.GalleryAdapter;
import com.example.androidcodes.photoeditingapp.Frames.Adapters.Recycler_Adapter;
import com.example.androidcodes.photoeditingapp.Frames.Common.Common;
import com.example.androidcodes.photoeditingapp.Frames.Fragments.Fragement_Activity;
import com.example.androidcodes.photoeditingapp.MainActivity;
import com.example.androidcodes.photoeditingapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomGalleryActivity extends Activity implements View.OnClickListener {

    public GalleryAdapter adapter;
    RecyclerView recyclerView;
    Recycler_Adapter rec_adapter;
    GridView gridGallery;
    Handler handler;

    /*AdapterView.OnItemClickListener mItemSingleClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> l, View v, int position, long id) {
            CustomGallery item = adapter.getItem(position);
            Intent data = new Intent().putExtra("single_path", item.sdcardPath);
            setResult(RESULT_OK, data);
            finish();
        }
    };*/

    AdapterView.OnItemClickListener mItemMulClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> l, View v, int position, long id) {

            if (Common.selected.size() < 5) {

                String imgPath = adapter.changeSelection(v, position);

                //adapter.getSelected();

                if (!imgPath.equals("")) {

                    Common.selected.add(imgPath);

                    rec_adapter = new Recycler_Adapter(CustomGalleryActivity.this, Common.selected,
                            null, adapter);

                    recyclerView.setAdapter(rec_adapter);

                    recyclerView.scrollToPosition(Common.selected.size() - 1);

                }
            } else {

                Snackbar.make(recyclerView, R.string.warning, Snackbar.LENGTH_SHORT).show();

            }
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.gallery);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {

            Common.permissionStorage = getSharedPreferences("permissions", MODE_PRIVATE);

        }

        Common.screenWidth = getResources().getDisplayMetrics().widthPixels;

        Common.screenHeight = getResources().getDisplayMetrics().heightPixels;

        Common.selected = new ArrayList<>();

        TextView tv_headerText = (TextView) findViewById(R.id.tv_headerText);
        tv_headerText.setText("Gallery");

        findViewById(R.id.iv_next).setOnClickListener(this);

        findViewById(R.id.iv_back).setOnClickListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        if (Common.isStorageAvailable() == true) {

            init();

        } else {

            Snackbar.make(findViewById(R.id.fl_layout), R.string.mountSDcard,
                    Snackbar.LENGTH_LONG).show();

        }

    }

    private void init() {

        handler = new Handler();
        gridGallery = (GridView) findViewById(R.id.gridGallery);
        gridGallery.setFastScrollEnabled(true);
        //adapter = new GalleryAdapter(CustomGalleryActivity.this/*, Common.imageLoader*/);

        gridGallery.setOnItemClickListener(mItemMulClickListener);
        //adapter.setMultiplePick(true);

        gridGallery.setAdapter(adapter);

        new Thread() {

            @Override
            public void run() {
                Looper.prepare();
                handler.post(new Runnable() {

                    @Override
                    public void run() {

                        adapter.addAll(getGalleryPhotos());

                    }
                });
                Looper.loop();
            }

            ;
        }.start();
    }

    private ArrayList<CustomGallery> getGalleryPhotos() {

        ArrayList<CustomGallery> galleryList = new ArrayList<CustomGallery>();

        try {

            final String[] columns = {MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media._ID};

            final String orderBy = MediaStore.Images.Media._ID;

            Cursor imagecursor = getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                    null, null, orderBy);

            if (imagecursor != null && imagecursor.getCount() > 0) {

                while (imagecursor.moveToNext()) {
                    CustomGallery item = new CustomGallery();

                    int dataColumnIndex = imagecursor
                            .getColumnIndex(MediaStore.MediaColumns.DATA);

                    item.sdcardPath = imagecursor.getString(dataColumnIndex);

                    galleryList.add(item);
                }
            }
        } catch (Exception e) {

            e.printStackTrace();

        }

        Collections.reverse(galleryList);

        return galleryList;

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            checkPermission();

        }

        if (Common.isStorageAvailable() == true) {

            init();

        } else {

            Snackbar.make(findViewById(R.id.fl_layout), R.string.mountSDcard,
                    Snackbar.LENGTH_LONG).show();

        }
    }

    /*@Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasWritePermission = checkSelfPermission(Manifest.permission.
                    WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = checkSelfPermission(Manifest.permission.
                    READ_EXTERNAL_STORAGE);
            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);

            List<String> permissions = new ArrayList<String>();
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            } else {

                editSharedPreferences("storage", "true");

                *//*SharedPreferences.Editor editPermissions = permissionStorage.edit();
                editPermissions.putString("storage", "true");
                editPermissions.commit();*//*


            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            } else {

                editSharedPreferences("storage", "true");

                //preferencesUtility.setString("storage", "true");

            }

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.CAMERA);

            } else {

                editSharedPreferences("camera", "true");
                //preferencesUtility.setString("camera", "true");

            }

            if (!permissions.isEmpty()) {

                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);

            }
        }
    }*/

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

                            if (Common.isStorageAvailable() == true) {

                                //Common.initImageLoader(CustomGalleryActivity.this);

                                init();

                            } else {

                                Snackbar.make(findViewById(R.id.fl_layout), R.string.mountSDcard,
                                        Snackbar.LENGTH_LONG).show();

                            }

                            //preferencesUtility.setString("storage", "true");

                        } /*else if (permissions[i].equals(Manifest.permission.CAMERA)) {

                            editSharedPreferences("camera", "true");
                            //preferencesUtility.setString("camera", "true");

                        }*/
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                        System.out.println("Permissions --> " + "Permission Denied: " +
                                permissions[i]);

                        if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            Common.editSharedPreferences("storage", "false");
                            //preferencesUtility.setString("storage", "false");

                        } /*else if (permissions[i].equals(Manifest.permission.CAMERA)) {

                            editSharedPreferences("camera", "false");
                            //preferencesUtility.setString("camera", "false");

                        }*/
                    }
                }
            }

            break;

            default: {

                super.onRequestPermissionsResult(requestCode, permissions, grantResults);

            }
        }
    }

    @Override
    public void onBackPressed() {

        Common.selected_bitmaps = new ArrayList<>();

        Common.changeActivity(CustomGalleryActivity.this, MainActivity.class, true);

        /*finish();

        startActivity(new Intent(CustomGalleryActivity.this, MainActivity.class));

        overridePendingTransition(R.anim.left_in, R.anim.right_out);*/

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_next:

                if (Common.selected != null) {

                    Common.selected_bitmaps = new ArrayList<Bitmap>();

                    Common.num_of_selected_images = Common.selected.size();

                    if (Common.num_of_selected_images == 3 ||
                            Common.num_of_selected_images == 4 ||
                            Common.num_of_selected_images == 5) {

                        for (int i = 0; i < Common.selected.size(); i++) {

                        /*Bitmap bmp = BitmapFactory.
                                decodeFile(Common.selected.get(i).sdcardPath.toString());*/

                            Bitmap bmp = scaleImage(Common.selected.get(i).toString());

                            Common.selected_bitmaps.add(bmp);

                        }

                        Common.changeActivity(CustomGalleryActivity.this, Fragement_Activity.class,
                                false);

                        /*Intent intent = new Intent(this, Fragement_Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                        overridePendingTransition(R.anim.right_in, R.anim.left_out);*/

                    } else {

                        Snackbar.make(recyclerView, R.string.warning, Snackbar.LENGTH_SHORT).
                                show();

                    }
                } else {

                    Toast.makeText(CustomGalleryActivity.this,
                            "Please Select Images First!!!", Toast.LENGTH_LONG).show();

                }

                break;

            case R.id.iv_back:

                Common.selected_bitmaps = new ArrayList<>();

                Common.changeActivity(CustomGalleryActivity.this, MainActivity.class, true);

                /*Intent intent = new Intent(CustomGalleryActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                finish();

                overridePendingTransition(R.anim.left_in, R.anim.right_out);*/

                break;

            default:

                break;

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
