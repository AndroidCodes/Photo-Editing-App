package com.example.androidcodes.photoeditingapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidcodes.photoeditingapp.Frames.Adapters.ListAdapter;
import com.example.androidcodes.photoeditingapp.Frames.AddTextToFrame.PhotoShort123;
import com.example.androidcodes.photoeditingapp.Frames.Common.Common;
import com.example.androidcodes.photoeditingapp.Frames.Fragments.Fragement_Activity;
import com.example.androidcodes.photoeditingapp.PIP.Common.PIP_Common;
import com.example.androidcodes.photoeditingapp.PIP.PIP_Effect_Activity;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * Created by peacock on 9/4/16.
 */
public class Save_Frame_Activity extends Activity implements View.OnClickListener {

    private String[] font = new String[]{"f.ttf", "b.ttf", "c.ttf", "n.OTF", "o.TTF",
            "j.TTF", "e.TTF", "g.TTF", "h.TTF", "k.TTF", "m.TTF", "d.TTF", "l.TTF",
            "a.ttf", "i.TTF"};
    private int cl = Color.RED, count = 1000;

    private Bitmap bmp_final = null;

    private Activity activity;

//    ProgressDialog pdg;

    // private String action = "";

    //========================================================================================

    private EditText editAddText;

    private ImageView bcolor, iv_finalFrame;

    private RelativeLayout rl_text;
    //private ProgressDialog progress;

    private KProgressHUD pd;

    //private FrameLayout fl_saveLayout;

    //========================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.save_frame_activity);

        activity = Save_Frame_Activity.this;

        bmp_final = Common.final_frame;

        TextView tv_headerText = (TextView) findViewById(R.id.tv_headerText);
        tv_headerText.setText(getString(R.string.save_frame));

        iv_finalFrame = (ImageView) findViewById(R.id.iv_finalFrame);
        iv_finalFrame.setImageBitmap(bmp_final);

        /*FrameLayout fl_saveLayout = (FrameLayout) findViewById(R.id.fl_saveLayout);

        pv_paintView = new PaintView(activity);
        fl_saveLayout.addView(pv_paintView);
        pv_paintView.setDrawingCacheEnabled(true);*/

        rl_text = (RelativeLayout) findViewById(R.id.rl_text);

        findViewById(R.id.iv_back).setOnClickListener(this);

        findViewById(R.id.iv_next).setVisibility(View.GONE);

        findViewById(R.id.iv_addText).setOnClickListener(this);

        findViewById(R.id.iv_saveFrame).setOnClickListener(this);

        /*pd = new KProgressHUD(activity).setCornerRadius(4.0f).
                setCancellable(false).setDimAmount(5.0f).
                setWindowColor(R.color.windowColor);*/

        //findViewById(R.id.btn_share).setOnClickListener(this);

        /*pd = new KProgressHUD(activity).setCornerRadius(4.0f).
                setCancellable(false).setDimAmount(5.0f).
                setWindowColor(R.color.windowColor);*/

        rl_text.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                disableall();

            }
        });
    }

    /*public void saveScaledImage() {

        //iv_finalFrame.setDrawingCacheEnabled(true);

        Bitmap bitmap1 = Common.final_frame;

        //Bitmap bitmap1 = iv_finalFrame.getDrawingCache();

        int bmOriginalWidth = bitmap1.getWidth();
        int bmOriginalHeight = bitmap1.getHeight();

        double originalWidthToHeightRatio = 1.0 * bmOriginalWidth / bmOriginalHeight;
        double originalHeightToWidthRatio = 1.0 * bmOriginalHeight / bmOriginalWidth;

        int maxHeight = 1080;

        int maxWidth = 1080;

        bitmap1 = getScaledBitmap(bitmap1, bmOriginalWidth, bmOriginalHeight,
                originalWidthToHeightRatio, originalHeightToWidthRatio,
                maxHeight, maxWidth);

        //ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        //final_bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        rl_text.setDrawingCacheEnabled(true);

        Bitmap bmp_text = rl_text.getDrawingCache();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        if (bmp_text != null) {

            bmp_text = getScaledBitmap(bmp_text, bmOriginalWidth, bmOriginalHeight,
                    originalWidthToHeightRatio, originalHeightToWidthRatio,
                    maxHeight, maxWidth);

            Bitmap bmOverlay = Bitmap.createBitmap(bitmap1.getWidth(),
                    bitmap1.getHeight(), bitmap1.getConfig());

            Canvas canvas = new Canvas(bmOverlay);
            canvas.drawBitmap(bitmap1, new Matrix(), null);
            canvas.drawBitmap(bmp_text, 0, 0, null);

            bmOverlay.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        } else {

            bitmap1.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        }

        try {

            File dir = new File(Environment.getExternalStorageDirectory()
                    + "/Photo Editing App/");

            if (!dir.exists()) {

                dir.mkdirs();

            }

            File file = new File(dir, System.currentTimeMillis() + ".png");

            FileOutputStream fo = new FileOutputStream(file);

            fo.write(bytes.toByteArray());

            fo.close();

            rl_text.setDrawingCacheEnabled(false);

           *//* if (action.equals("save")) {

                action = "";*//*

            Toast.makeText(activity, file.getAbsolutePath(),
                    Toast.LENGTH_LONG).show();

            //}

            //fl_saveLayout.setDrawingCacheEnabled(false);

            //return file.getAbsolutePath();

        } catch (IOException e) {

            System.out.print("File Exception ...>>>... " + e.getMessage());

            //return "";

        }
    }*/

    /*private String saveImage() {

        fl_saveFrame.setDrawingCacheEnabled(true);

        fl_saveFrame.buildDrawingCache();

        Bitmap final_bitmap = fl_saveFrame.getDrawingCache();

        File filepath = Environment.getExternalStorageDirectory();

        // Create a new folder in SD Card
        File dir = new File(filepath.getAbsolutePath() + "/Photo Editing App/Frames_Three/");

        if (!dir.exists()) {

            dir.mkdirs();

        }

        File file = new File(dir, System.currentTimeMillis() + ".jpg");

        if (action.equals("save")) {

            Toast.makeText(activity, "Frame Saved to SD Card",
                    Toast.LENGTH_SHORT).show();

        }

        try {

            FileOutputStream output = new FileOutputStream(file);
            final_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
            output.flush();
            output.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        fl_saveFrame.setDrawingCacheEnabled(false);

        return file.getAbsolutePath();

    }*/

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_back:

                finish();

                overridePendingTransition(R.anim.left_in, R.anim.right_out);

                //findIntent();

                /*if (Common.isFromPIP == true) {

                    startActivity(new Intent(activity,
                            PIP_Effect_Activity.class));

                } else {

                    startActivity(new Intent(activity,
                            Fragement_Activity.class));

                }

                overridePendingTransition(R.anim.left_in, R.anim.right_out);*/

                break;

            case R.id.iv_addText:

                addtext();

                break;

            case R.id.iv_saveFrame:

                disableall();

                PIP_Common.selectedFrame = PIP_Common.selectedForeground =
                        PIP_Common.selectedBackground = Common.frame_number = 0;

                Common.frameColor = Color.WHITE;

                findViewById(R.id.iv_saveFrame).setEnabled(false);

                findViewById(R.id.iv_saveFrame).setClickable(false);

                /*pd = new KProgressHUD(activity).setCornerRadius(4.0f).
                        setCancellable(false).setDimAmount(5.0f).
                        setWindowColor(R.color.windowColor);*/

                //saveScaledImage();

                new saveImage().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                break;
        }
    }

    private void addtext() {

        try {

            final Dialog dia = new Dialog(activity);
            dia.setCancelable(true);
            dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dia.setContentView(R.layout.add_text_dialog);

            editAddText = (EditText) dia.findViewById(R.id.edtText);

            bcolor = (ImageView) dia.findViewById(R.id.bcolor);

            editAddText.setTextColor(this.cl);

            bcolor.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    colorpicker();

                }
            });

            GridView list = (GridView) dia.findViewById(R.id.list);
            list.setAdapter(new ListAdapter(activity, font));
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> adapterView, View arg1, int pos,
                                        long arg3) {

                    if (editAddText.getText().toString().equals("")) {

                        Toast.makeText(activity, "First Add Text",
                                Toast.LENGTH_SHORT).show();

                    } else {

                        if (editAddText.getText().toString().trim().length() > 0) {

                            AddText(editAddText.getText().toString().trim(), pos);

                        }
                    }

                    if (rl_text.getVisibility() == View.GONE) {

                        rl_text.setVisibility(View.VISIBLE);

                    }

                    dia.cancel();

                }
            });

            dia.show();

        } catch (Exception e) {
        }
    }

    private void AddText(String srt, int pos2) {

        Typeface typ = Typeface.createFromAsset(getAssets(), this.font[pos2]);

        Paint paint = new Paint();
        paint.setTextSize(100.0f);
        paint.setTypeface(typ);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(this.cl);

        float baseline = (float) ((int) ((-paint.ascent()) + 0.5f));

        Bitmap image = Bitmap.createBitmap((int) (paint.measureText(srt) + 0.5f),
                (int) ((paint.descent() + baseline) + 50.0f), Bitmap.Config.ARGB_8888);

        new Canvas(image).drawText(srt, 0.0f, baseline, paint);

        Drawable d = new BitmapDrawable(getResources(), image);

        if (rl_text.getVisibility() == View.GONE) {

            rl_text.setVisibility(View.VISIBLE);

        }

        PhotoShort123 ca = new PhotoShort123(this);
        PhotoShort123.image.setImageDrawable(d);

        rl_text.addView(ca);

        int i = count;

        count = i + 1;

        ca.setId(i);
        ca.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                disableall();

            }
        });
    }

   /* public String saveScaledImage() {

        //iv_finalFrame.setDrawingCacheEnabled(true);

        //Bitmap bitmap1 = bmp_final;

        //Bitmap bitmap1 = iv_finalFrame.getDrawingCache();

        int bmOriginalWidth = bmp_final.getWidth();
        int bmOriginalHeight = bmp_final.getHeight();

        double originalWidthToHeightRatio = 1.0 * bmOriginalWidth / bmOriginalHeight;
        double originalHeightToWidthRatio = 1.0 * bmOriginalHeight / bmOriginalWidth;

        int maxHeight = 1080;

        int maxWidth = 1080;

        bmp_final = getScaledBitmap(bmp_final, bmOriginalWidth, bmOriginalHeight,
                originalWidthToHeightRatio, originalHeightToWidthRatio,
                maxHeight, maxWidth);

        //ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        //final_bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        rl_text.setDrawingCacheEnabled(true);

        Bitmap bmp_text = rl_text.getDrawingCache();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        if (bmp_text != null) {

            bmp_text = getScaledBitmap(bmp_text, bmOriginalWidth, bmOriginalHeight,
                    originalWidthToHeightRatio, originalHeightToWidthRatio,
                    maxHeight, maxWidth);

            Bitmap bmOverlay = Bitmap.createBitmap(bmp_final.getWidth(),
                    bmp_final.getHeight(), bmp_final.getConfig());

            Canvas canvas = new Canvas(bmOverlay);
            canvas.drawBitmap(bmp_final, new Matrix(), null);
            canvas.drawBitmap(bmp_text, 0, 0, null);

            bmOverlay.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        } else {

            bmp_final.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        }

        try {
            *//*File dir = new File(Environment.getExternalStorageDirectory()
                    + "/Photo Editing App/");*//*

            if (!Common.file_path.exists()) {

                Common.file_path.mkdirs();

            }

            File file = new File(Common.file_path, System.currentTimeMillis() + ".png");

            FileOutputStream fo = new FileOutputStream(file);

            fo.write(bytes.toByteArray());

            fo.close();

            rl_text.setDrawingCacheEnabled(false);

           *//* if (action.equals("save")) {

                action = "";*//*

            Toast.makeText(activity, file.getAbsolutePath(),
                    Toast.LENGTH_LONG).show();

            //}

            //fl_saveLayout.setDrawingCacheEnabled(false);

            return file.getAbsolutePath();

        } catch (IOException e) {

            System.out.print("File Exception ...>>>... " + e.getMessage());

            return "";

        }
    }

    private Bitmap getScaledBitmap(Bitmap bm, int bmOriginalWidth, int bmOriginalHeight,
                                   double originalWidthToHeightRatio,
                                   double originalHeightToWidthRatio,
                                   int maxHeight, int maxWidth) {

        if (bmOriginalWidth < maxWidth || bmOriginalHeight < maxHeight) {

            *//*if (bmOriginalWidth < bmOriginalHeight) {*//*

            bm = scaleDeminsFromHeight(bm, maxHeight, bmOriginalHeight,
                    originalWidthToHeightRatio);

            *//*} else if (bmOriginalHeight > bmOriginalWidth) {

                bm = scaleDeminsFromWidth(bm, maxWidth, bmOriginalHeight,
                        originalHeightToWidthRatio);

            }*//*
        }

        return bm;

    }

    private Bitmap scaleDeminsFromHeight(Bitmap bm, int maxHeight, int bmOriginalHeight,
                                         double originalWidthToHeightRatio) {

        int newHeight = (int) Math.max(maxHeight, bmOriginalHeight * .55);

        int newWidth = (int) (newHeight * originalWidthToHeightRatio);

        bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);

        return bm;

    }*/

    public void colorpicker() {

        new AmbilWarnaDialog(activity, cl,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {

                    public void onCancel(AmbilWarnaDialog dialog) {
                    }

                    public void onOk(AmbilWarnaDialog dialog, int color) {

                        editAddText.setTextColor(color);

                        cl = color;

                    }
                }).show();
    }

    public void disableall() {

        for (int i = 0; i < rl_text.getChildCount(); i++) {

            if (rl_text.getChildAt(i) instanceof PhotoShort123) {

                ((PhotoShort123) rl_text.getChildAt(i)).disableAll();

            }
        }
    }

    /*private Bitmap scaleDeminsFromWidth(Bitmap bm, int maxWidth, int bmOriginalWidth,
                                        double originalHeightToWidthRatio) {

        int newWidth = (int) Math.max(maxWidth, bmOriginalWidth * .75);

        int newHeight = (int) (newWidth * originalHeightToWidthRatio);

        bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);

        return bm;

    }*/

    private Bitmap getScaledBitmap(Bitmap bm, int bmOriginalWidth, int bmOriginalHeight,
                                   double originalWidthToHeightRatio,
                                   double originalHeightToWidthRatio,
                                   int maxHeight, int maxWidth) {

        if (bmOriginalWidth < maxWidth || bmOriginalHeight < maxHeight) {

            /*if (bmOriginalWidth < bmOriginalHeight) {*/

            bm = scaleDeminsFromHeight(bm, maxHeight, bmOriginalHeight,
                    originalWidthToHeightRatio);

            /*} else if (bmOriginalHeight > bmOriginalWidth) {

                bm = scaleDeminsFromWidth(bm, maxWidth, bmOriginalHeight,
                        originalHeightToWidthRatio);

            }*/
        }

        return bm;

    }

    private Bitmap scaleDeminsFromHeight(Bitmap bm, int maxHeight, int bmOriginalHeight,
                                         double originalWidthToHeightRatio) {

        int newHeight = (int) Math.max(maxHeight, bmOriginalHeight * .55);

        int newWidth = (int) (newHeight * originalWidthToHeightRatio);

        bm = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);

        return bm;

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
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            }

           /* if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {

                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            }*/

            if (!permissions.isEmpty()) {

                requestPermissions(permissions.toArray(new String[permissions.size()]), 111);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //findIntent();

        overridePendingTransition(R.anim.left_in, R.anim.right_out);

    }

    private void findIntent() {

        if (Common.isFromPIP == true) {

            startActivity(new Intent(activity, PIP_Effect_Activity.class));

        } else {

            startActivity(new Intent(activity, Fragement_Activity.class));

        }
    }

    private class saveImage extends AsyncTask<Void, Void, Void> {

        Bitmap bmp_text = null;

        File file;

        FrameLayout fl_saveLayout;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new KProgressHUD(activity).setCornerRadius(4.0f).
                    setCancellable(false).setDimAmount(5.0f).
                    setWindowColor(Color.parseColor("#85000000")).show();

            rl_text.setDrawingCacheEnabled(true);

            fl_saveLayout = (FrameLayout) findViewById(R.id.fl_saveLayout);

            bmp_text = rl_text.getDrawingCache();

        }

        @Override
        protected Void doInBackground(Void... params) {

            Bitmap bitmap1 = Common.final_frame;

            //Bitmap bitmap1 = iv_finalFrame.getDrawingCache();

            int bmOriginalWidth = bitmap1.getWidth();
            int bmOriginalHeight = bitmap1.getHeight();

            double originalWidthToHeightRatio = 1.0 * bmOriginalWidth / bmOriginalHeight;
            double originalHeightToWidthRatio = 1.0 * bmOriginalHeight / bmOriginalWidth;

            int maxHeight = 1080;

            int maxWidth = 1080;

            bitmap1 = getScaledBitmap(bitmap1, bmOriginalWidth, bmOriginalHeight,
                    originalWidthToHeightRatio, originalHeightToWidthRatio,
                    maxHeight, maxWidth);

            //ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            //final_bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            Bitmap bmOverlay = null;

            if (bmp_text != null) {

                bmp_text = getScaledBitmap(bmp_text, bmOriginalWidth, bmOriginalHeight,
                        originalWidthToHeightRatio, originalHeightToWidthRatio,
                        maxHeight, maxWidth);

                bmOverlay = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(),
                        Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(bmOverlay);
                canvas.drawBitmap(bitmap1, new Matrix(), null);
                canvas.drawBitmap(bmp_text, 0, 0, null);

                bmOverlay.compress(Bitmap.CompressFormat.PNG, 100, bytes);

            } else {

                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, bytes);

            }

            try {

                File dir = new File(Environment.getExternalStorageDirectory()
                        + "/Photo Editing App/");

                if (!dir.exists()) {

                    dir.mkdirs();

                }

                file = new File(dir, new Random().nextInt(10000) + ".png");

                FileOutputStream fo = new FileOutputStream(file);

                fo.write(bytes.toByteArray());

                fo.close();

            } catch (IOException e) {

                System.out.print("File Exception ...>>>... " + e.getMessage());

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            rl_text.setDrawingCacheEnabled(false);

            Common.getImagesFromExternalStorage();

            pd.dismiss();

            Toast.makeText(activity, file.getAbsolutePath(),
                    Toast.LENGTH_LONG).show();

            Common.changeActivity(activity, Album_Activity.class, false);

        }
    }

    /*private void combineTwoImages() {

        your_view_1.setDrawingCacheEnabled(true);

        Bitmap bitmap_1 = your_view_1.getDrawingCache();

        int bmOriginalWidth = bitmap_1.getWidth();
        int bmOriginalHeight = bitmap_1.getHeight();

        double originalWidthToHeightRatio = 1.0 * bmOriginalWidth / bmOriginalHeight;
        double originalHeightToWidthRatio = 1.0 * bmOriginalHeight / bmOriginalWidth;

        int maxHeight = 1024;

        int maxWidth = 1024;

        bitmap_1 = getScaledBitmap(bitmap_1, bmOriginalWidth, bmOriginalHeight,
                originalWidthToHeightRatio, originalHeightToWidthRatio,
                maxHeight, maxWidth);

        your_view_2.setDrawingCacheEnabled(true);

        Bitmap bitmap_2 = your_view_2.getDrawingCache();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        if (bitmap_1 != null) {

            bitmap_1 = getScaledBitmap(bitmap_1, bmOriginalWidth, bmOriginalHeight,
                    originalWidthToHeightRatio, originalHeightToWidthRatio,
                    maxHeight, maxWidth);

            Bitmap bmOverlay = Bitmap.createBitmap(bitmap_1.getWidth(),
                    bitmap_1.getHeight(), bitmap_1.getConfig());

            Canvas canvas = new Canvas(bmOverlay);
            canvas.drawBitmap(bitmap_1, new Matrix(), null);
            canvas.drawBitmap(bitmap_1, 0, 0, null);

            bmOverlay.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        } else {

            bitmap_1.compress(Bitmap.CompressFormat.PNG, 100, bytes);

        }

        try {
            File dir = new File(Environment.getExternalStorageDirectory()
                    + "/Photo Editing App/Frames_Three/");

            if (!dir.exists()) {

                dir.mkdirs();

            }

            File file = new File(dir, System.currentTimeMillis() + ".jpg");

            FileOutputStream fo = new FileOutputStream(file);

            fo.write(bytes.toByteArray());

            fo.close();

            your_view_1.setDrawingCacheEnabled(false);

            your_view_2.setDrawingCacheEnabled(false);

            if (action.equals("save")) {

                action = "";

                Toast.makeText(activity, file.getAbsolutePath(),
                        Toast.LENGTH_LONG).show();

            }

            //return file.getAbsolutePath();

        } catch (IOException e) {

            e.printStackTrace();

            //return "";

        }
    }*/
}
