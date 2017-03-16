package com.example.androidcodes.photoeditingapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidcodes.photoeditingapp.Frames.Common.Common;
import com.example.androidcodes.photoeditingapp.Frames.CustomGallery.New_CustomGallery;
import com.example.androidcodes.photoeditingapp.PIP.Common.PIP_Common;
import com.example.androidcodes.photoeditingapp.PIP.PIP_Effect_Activity;
import com.example.androidcodes.photoeditingapp.flycoDialog.ShareBottomDialog;
import com.flyco.animation.BaseAnimatorSet;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String ACTION_MULTIPLE_PICK = "luminous.ACTION_MULTIPLE_PICK";

    private Activity activity;

    private BaseAnimatorSet mBasOut, mBasIn;

    private String imgPath;

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private Dialog exit_dialog;

    private ImageView iv_pip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        activity = MainActivity.this;

        //Common.initImageLoader(activity);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {

            Common.permissionStorage = getSharedPreferences("permissions", MODE_PRIVATE);

        }

        mBasIn = new BounceTopEnter();

        mBasOut = new SlideBottomExit();

        findViewById(R.id.iv_frames).setOnClickListener(this);

        findViewById(R.id.iv_myAlbum).setOnClickListener(this);

        iv_pip = (ImageView) findViewById(R.id.iv_pip);
        iv_pip.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {

        exit_dialog = new Dialog(MainActivity.this);
        exit_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        exit_dialog.setContentView(R.layout.exit_app_dialog);
        exit_dialog.setCancelable(true);
        exit_dialog.setCanceledOnTouchOutside(true);

        TextView tv_exitText = (TextView) exit_dialog.findViewById(R.id.tv_text);
        tv_exitText.setTextColor(getResources().getColor(R.color.exit_text));

        Button btn_exit = (Button) exit_dialog.findViewById(R.id.btn_exit);
        btn_exit.setTextColor(getResources().getColor(R.color.frame_inside_color));
        btn_exit.setOnClickListener(this);

        Button btn_cancel = (Button) exit_dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setTextColor(getResources().getColor(R.color.frame_inside_color));
        btn_cancel.setOnClickListener(this);

        exit_dialog.show();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_frames:

                //Intent i = null;

                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {

                    String permission = Common.permissionStorage.getString("storage", "");

                    if (permission.equals("true")) {

                        if (Common.isStorageAvailable() == true) {

                            startActivity(new Intent(activity, New_CustomGallery.class));

                           /* i = new Intent(MainActivity.ACTION_MULTIPLE_PICK);

                            startActivityForResult(i, 200);*/

                            overridePendingTransition(R.anim.right_in, R.anim.left_out);

                            finish();

                        } else {

                            Snackbar.make(findViewById(R.id.ll_layout),
                                    R.string.mountSDcard, Snackbar.LENGTH_LONG).show();

                        }
                    } else {

                        checkPermission();

                    }
                } else if (Common.isStorageAvailable() == true) {

                    startActivity(new Intent(activity, New_CustomGallery.class));

                    /*i = new Intent(MainActivity.ACTION_MULTIPLE_PICK);

                    startActivityForResult(i, 200);*/

                    overridePendingTransition(R.anim.right_in, R.anim.left_out);

                    finish();

                } else {

                    Snackbar.make(findViewById(R.id.ll_layout), R.string.mountSDcard,
                            Snackbar.LENGTH_LONG).show();

                }

                break;

            case R.id.iv_myAlbum:

                //PIP_Common.fromHome = true;

                //finish();

                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {

                    String permission = Common.permissionStorage.getString("storage", "");

                    if (permission.equals("true") && Common.isStorageAvailable() == true) {

                        //Common.getImagesFromexternalStorage();

                        PIP_Common.tempFlag = true;

                        Common.changeActivity(MainActivity.this, Album_Activity.class, false);

                    } else {

                        checkPermission();

                    }
                } else {

                    //Common.getImagesFromexternalStorage();

                    PIP_Common.tempFlag = true;

                    Common.changeActivity(MainActivity.this, Album_Activity.class, false);

                }

                /*startActivity(new Intent(MainActivity.this, Album_Activity.class));

                overridePendingTransition(R.anim.right_in, R.anim.left_out);*/

                break;

            case R.id.iv_pip:

                final ShareBottomDialog dialogShow = new ShareBottomDialog(activity, iv_pip);
                dialogShow.showAnim(mBasIn).dismissAnim(mBasOut).show();
                dialogShow.findViewById(R.id.ll_camera).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialogShow.dismiss();

                        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {

                            String permission = Common.permissionStorage.getString("camera", "");

                            if (permission.equals("true")) {

                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, REQUEST_CAMERA);

                            } else {

                                checkPermission();

                            }
                        } else {

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, REQUEST_CAMERA);

                        }
                    }
                });

                dialogShow.findViewById(R.id.ll_gallery).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent;

                        dialogShow.dismiss();

                        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {

                            intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.addCategory(Intent.CATEGORY_OPENABLE);
                            intent.setType("image/*");
                            startActivityForResult(intent, SELECT_FILE);

                        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {

                            String permission = Common.permissionStorage.getString("storage", "");

                            if (permission.equals("true")) {

                                intent = new Intent(Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select File"),
                                        SELECT_FILE);

                            } else {

                                checkPermission();

                            }
                        } else {

                            intent = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select File"),
                                    SELECT_FILE);

                        }
                    }
                });

                break;

            case R.id.btn_cancel:

                exit_dialog.dismiss();

                break;

            case R.id.btn_exit:

                finish();

                break;

            default:

                break;

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

            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            }

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.CAMERA);

            }

            if (!permissions.isEmpty()) {

                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);

            }
        }
    }
*/
    @Override
    protected void onStart() {
        super.onStart();

        checkPermission();

    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
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
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case 111: {

                for (int i = 0; i < permissions.length; i++) {

                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                        System.out.println("Permissions --> " + "Permission Granted: " +
                                permissions[i]);

                        if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            Common.editSharedPreferences("storage", "true");

                            Common.getAlbums(activity);
                            //preferencesUtility.setString("storage", "true");

                        } else if (permissions[i].equals(Manifest.permission.CAMERA)) {

                            Common.editSharedPreferences("camera", "true");
                            //preferencesUtility.setString("camera", "true");

                        }
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {

                        System.out.println("Permissions --> " + "Permission Denied: " +
                                permissions[i]);

                        if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            Common.editSharedPreferences("storage", "false");
                            //preferencesUtility.setString("storage", "false");

                        } else if (permissions[i].equals(Manifest.permission.CAMERA)) {

                            Common.editSharedPreferences("camera", "false");
                            //preferencesUtility.setString("camera", "false");

                        }
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == SELECT_FILE) {

                if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {

                    PIP_Common.PIP_bitmap = Common.getImageFromKitkat(MainActivity.this, data);

                    /*ParcelFileDescriptor parcelFileDescriptor = null;
                    try {
                        parcelFileDescriptor = getContentResolver().
                                openFileDescriptor(data.getData(), "r");
                        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
                        final int REQUIRED_SIZE = 1080;
                        int scale = 1;
                        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                            scale *= 2;
                        options.inSampleSize = scale;
                        options.inJustDecodeBounds = false;

                        PIP_Common.PIP_bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor,
                                null, options);

                        parcelFileDescriptor.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                } else {

                    onSelectFromGalleryResult(data);

                }
            } else if (requestCode == REQUEST_CAMERA) {

                onCaptureImageResult(data);

            } else {
            }

            Intent i = new Intent(activity, PIP_Effect_Activity.class);
            startActivity(i);

        }
    }

    /*private Uri getUri() {
        String state = Environment.getExternalStorageState();
        if (!state.equalsIgnoreCase(Environment.MEDIA_MOUNTED))
            return MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }*/

    private void onCaptureImageResult(Intent data) {

        Bitmap bitmap1 = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {

            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgPath = destination.getAbsolutePath();

        PIP_Common.PIP_bitmap = BitmapFactory.decodeFile(imgPath);

        /*Intent i = new Intent(activity, PIP_Effect_Activity.class);
        startActivity(i);*/
    }

    private void onSelectFromGalleryResult(Intent data) {

        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        System.out.println("\n\n\n selectedImagePath --> " + selectedImagePath + "\n\n\n");

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 1080;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(selectedImagePath, options);

        if (bitmap.getHeight() > 720 || bitmap.getWidth() > 720) {

            PIP_Common.PIP_bitmap = getCompressedBitmap(selectedImagePath);

        } else {

            PIP_Common.PIP_bitmap = bitmap;

        }
        /*if (bmp.getWidth() > 720 || bmp.getHeight() > 720) {

            bmp = Bitmap.createScaledBitmap(bmp, (bmp.getWidth() / 2),
                    (bmp.getHeight() / 2), true);

            if (PIP_Common.PIP_bitmap.getWidth() > 720 || PIP_Common.PIP_bitmap.getHeight() > 720) {

                PIP_Common.PIP_bitmap = Bitmap.createScaledBitmap(PIP_Common.PIP_bitmap,
                        (bmp.getWidth() / 2), (bmp.getHeight() / 2), true);

            }
        } else {

            PIP_Common.PIP_bitmap = bmp;

        }*/
        /*Intent i = new Intent(activity, PIP_Effect_Activity.class);
        startActivity(i);*/
    }

    public Bitmap getCompressedBitmap(String imagePath) {
        float maxHeight = 1920.0f;
        float maxWidth = 1080.0f;
        Bitmap scaledBitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(imagePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float imgRatio = (float) actualWidth / (float) actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(imagePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
            } else if (orientation == 3) {
                matrix.postRotate(180);
            } else if (orientation == 8) {
                matrix.postRotate(270);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);

        byte[] byteArray = out.toByteArray();

        Bitmap updatedBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        return updatedBitmap;

    }

    private int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }
        return inSampleSize;
    }

    private void checkPermission() {

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

                Common.editSharedPreferences("storage", "true");

                /*SharedPreferences.Editor editPermissions = permissionStorage.edit();
                editPermissions.putString("storage", "true");
                editPermissions.commit();*/


            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            } else {

                Common.editSharedPreferences("storage", "true");

                //preferencesUtility.setString("storage", "true");

            }

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.CAMERA);

            } else {

                Common.editSharedPreferences("camera", "true");
                //preferencesUtility.setString("camera", "true");

            }

            if (!permissions.isEmpty()) {

                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);

            }
        }
    }
}