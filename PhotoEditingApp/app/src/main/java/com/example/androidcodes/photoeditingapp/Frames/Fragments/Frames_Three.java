package com.example.androidcodes.photoeditingapp.Frames.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.androidcodes.photoeditingapp.Frames.Common.Common;
import com.example.androidcodes.photoeditingapp.R;
import com.example.androidcodes.photoeditingapp.Save_Frame_Activity;
import com.example.androidcodes.photoeditingapp.TouchImageView.TouchImageView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peacock on 26/3/16.
 */
public class Frames_Three extends Fragment implements View.OnClickListener,
        View.OnLongClickListener, View.OnDragListener {

    private int GALLERY_IMAGE = 100;

    private View view = null;

    private Activity activity;

    private TouchImageView tiv_image_1, tiv_image_2, tiv_image_3;

    private LinearLayout ll_editLayout1, ll_editLayout2, ll_editLayout3;

    private TextView tv_effect1, tv_effect2, tv_effect3, tv_changeImg1, tv_changeImg2,
            tv_changeImg3;

    private BroadcastReceiver recieve_visibility_broadcast = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getStringExtra("Visibility").equals("gone")) {

                ll_editLayout1.setVisibility(View.GONE);

                ll_editLayout2.setVisibility(View.GONE);

                ll_editLayout3.setVisibility(View.GONE);

                LinearLayout ll_save = (LinearLayout) view.findViewById(R.id.ll_save);

                ll_save.setDrawingCacheEnabled(true);

                ll_save.buildDrawingCache();

                Common.final_frame = ll_save.getDrawingCache();

                if (Common.final_frame != null) {

                    startActivity(new Intent(activity, Save_Frame_Activity.class));

                    activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);

                }
            }
        }
    };

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Common.threeFlag = false;

        Common.fourFlag = false;

        if (Common.frame_number == 0) {

            view = inflater.inflate(R.layout.frame_30, null);

        } else if (Common.frame_number == 1) {

            view = inflater.inflate(R.layout.frame_31, null);

        } else if (Common.frame_number == 2) {

            view = inflater.inflate(R.layout.frame_32, null);

        } else if (Common.frame_number == 3) {

            view = inflater.inflate(R.layout.frame_33, null);

        } else if (Common.frame_number == 4) {

            view = inflater.inflate(R.layout.frame_34, null);

        } else if (Common.frame_number == 5) {

            view = inflater.inflate(R.layout.frame_35, null);

        } else if (Common.frame_number == 6) {

            Common.threeFlag = true;

            view = inflater.inflate(R.layout.frame_36, null);

        } else if (Common.frame_number == 7) {

            Common.threeFlag = true;

            Common.fourFlag = true;

            view = inflater.inflate(R.layout.frame_37, null);

        } else if (Common.frame_number == 8) {

            view = inflater.inflate(R.layout.frame_38, null);

        } else if (Common.frame_number == 9) {

            view = inflater.inflate(R.layout.frame_39, null);

        }

        activity = getActivity();

        tiv_image_1 = (TouchImageView) view.findViewById(R.id.tiv_image_1);
        tiv_image_1.setMinZoom(1.0f);
        tiv_image_1.setMaxZoom(4.0f);
        tiv_image_1.setOnLongClickListener(this);
        tiv_image_1.setOnClickListener(this);
        tiv_image_1.setOnDragListener(this);
        tiv_image_1.setTag(0);
        tiv_image_1.setDrawingCacheEnabled(true);

        tiv_image_2 = (TouchImageView) view.findViewById(R.id.tiv_image_2);
        tiv_image_2.setMinZoom(1.0f);
        tiv_image_2.setMaxZoom(4.0f);
        tiv_image_2.setOnLongClickListener(this);
        tiv_image_2.setOnClickListener(this);
        tiv_image_2.setOnDragListener(this);
        tiv_image_2.setTag(1);
        tiv_image_2.setDrawingCacheEnabled(true);

        tiv_image_3 = (TouchImageView) view.findViewById(R.id.tiv_image_3);
        tiv_image_3.setMinZoom(1.0f);
        tiv_image_3.setMaxZoom(4.0f);
        tiv_image_3.setOnLongClickListener(this);
        tiv_image_3.setOnClickListener(this);
        tiv_image_3.setOnDragListener(this);
        tiv_image_3.setTag(2);
        tiv_image_3.setDrawingCacheEnabled(true);

        ll_editLayout1 = (LinearLayout) view.findViewById(R.id.ll_editLayout1);

        ll_editLayout2 = (LinearLayout) view.findViewById(R.id.ll_editLayout2);

        ll_editLayout3 = (LinearLayout) view.findViewById(R.id.ll_editLayout3);

        tv_effect1 = (TextView) view.findViewById(R.id.tv_effect1);
        tv_effect1.setOnClickListener(this);

        tv_effect2 = (TextView) view.findViewById(R.id.tv_effect2);
        tv_effect2.setOnClickListener(this);

        tv_effect3 = (TextView) view.findViewById(R.id.tv_effect3);
        tv_effect3.setOnClickListener(this);

        tv_changeImg1 = (TextView) view.findViewById(R.id.tv_changeImg1);
        tv_changeImg1.setOnClickListener(this);

        tv_changeImg2 = (TextView) view.findViewById(R.id.tv_changeImg2);
        tv_changeImg2.setOnClickListener(this);

        tv_changeImg3 = (TextView) view.findViewById(R.id.tv_changeImg3);
        tv_changeImg3.setOnClickListener(this);

        Common.iv_frame_1 = (ImageView) view.findViewById(R.id.iv_frame_1);
        Common.iv_frame_1.setColorFilter(ContextCompat.getColor(activity,
                R.color.cardview_light_background), PorterDuff.Mode.MULTIPLY);

        Common.iv_frame_2 = (ImageView) view.findViewById(R.id.iv_frame_2);
        Common.iv_frame_2.setColorFilter(ContextCompat.getColor(activity,
                R.color.cardview_light_background), PorterDuff.Mode.MULTIPLY);

        Common.iv_frame_3 = (ImageView) view.findViewById(R.id.iv_frame_3);
        Common.iv_frame_3.setColorFilter(ContextCompat.getColor(activity,
                R.color.cardview_light_background), PorterDuff.Mode.MULTIPLY);

        if (Common.threeFlag == true) {

            Common.iv_1 = (ImageView) view.findViewById(R.id.iv_1);

            Common.iv_2 = (ImageView) view.findViewById(R.id.iv_2);

            Common.iv_3 = (ImageView) view.findViewById(R.id.iv_3);

            if (Common.fourFlag == true) {

                Common.iv_4 = (ImageView) view.findViewById(R.id.iv_4);

            }
        }

        //==========================================================================================

        tiv_image_1.setImageBitmap(Common.selected_bitmaps.get(0));

        tiv_image_2.setImageBitmap(Common.selected_bitmaps.get(1));

        tiv_image_3.setImageBitmap(Common.selected_bitmaps.get(2));

        return view;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tiv_image_1:

                if (ll_editLayout1.getVisibility() == View.GONE) {


                    tv_effect1.setAnimation(AnimationUtils.loadAnimation(activity,
                            android.R.anim.fade_in));

                    tv_changeImg1.setAnimation(AnimationUtils.loadAnimation(activity,
                            android.R.anim.fade_in));

                    ll_editLayout1.setVisibility(View.VISIBLE);

                } else {

                    ll_editLayout1.setVisibility(View.GONE);

                }

                break;

            case R.id.tiv_image_2:

                if (ll_editLayout2.getVisibility() == View.GONE) {

                    tv_effect2.setAnimation(AnimationUtils.loadAnimation(activity,
                            android.R.anim.fade_in));

                    tv_changeImg2.setAnimation(AnimationUtils.loadAnimation(activity,
                            android.R.anim.fade_in));

                    ll_editLayout2.setVisibility(View.VISIBLE);

                } else {

                    ll_editLayout2.setVisibility(View.GONE);

                }

                break;

            case R.id.tiv_image_3:

                if (ll_editLayout3.getVisibility() == View.GONE) {

                    tv_effect3.setAnimation(AnimationUtils.loadAnimation(activity,
                            android.R.anim.fade_in));

                    tv_changeImg3.setAnimation(AnimationUtils.loadAnimation(activity,
                            android.R.anim.fade_in));

                    ll_editLayout3.setVisibility(View.VISIBLE);

                } else {

                    ll_editLayout3.setVisibility(View.GONE);

                }

                break;

            case R.id.tv_effect1:

                Common.applyEffect(0, activity);

                /*PIP_Common.selected_image_number = 0;

                PIP_Common.edit_selected_framed_image = PIP_Common.selected_bitmaps.get(0);

                if (PIP_Common.edit_selected_framed_image != null) {

                    startActivity(new Intent(activity, Edit_Images.class));

                    activity.overridePendingTransition(R.anim.slide_up_in,
                            R.anim.slide_up_out);

                }*/

                break;

            case R.id.tv_effect2:

                Common.applyEffect(1, activity);

                /*PIP_Common.selected_image_number = 1;

                PIP_Common.edit_selected_framed_image = PIP_Common.selected_bitmaps.get(1);

                if (PIP_Common.edit_selected_framed_image != null) {

                    startActivity(new Intent(activity, Edit_Images.class));

                    activity.overridePendingTransition(R.anim.slide_up_in,
                            R.anim.slide_up_out);

                }*/

                break;

            case R.id.tv_effect3:

                Common.applyEffect(2, activity);

                /*PIP_Common.selected_image_number = 2;

                PIP_Common.edit_selected_framed_image = PIP_Common.selected_bitmaps.get(2);

                if (PIP_Common.edit_selected_framed_image != null) {

                    startActivity(new Intent(activity, Edit_Images.class));

                    activity.overridePendingTransition(R.anim.slide_up_in,
                            R.anim.slide_up_out);

                }*/

                break;

            case R.id.tv_changeImg1:

                changeImage(0);

                /*PIP_Common.selected_image_number = 0;

                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image*//*");
                startActivityForResult(Intent.createChooser(intent, "Select File"),
                        GALLERY_IMAGE);

                activity.overridePendingTransition(R.anim.slide_up_in,
                        R.anim.slide_up_out);*/

                break;

            case R.id.tv_changeImg2:

                changeImage(1);

                /*PIP_Common.selected_image_number = 1;

                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image*//*");
                startActivityForResult(Intent.createChooser(intent, "Select File"),
                        GALLERY_IMAGE);

                activity.overridePendingTransition(R.anim.slide_up_in,
                        R.anim.slide_up_out);*/

                break;

            case R.id.tv_changeImg3:

                changeImage(2);

                /*PIP_Common.selected_image_number = 2;

                intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image*//*");
                startActivityForResult(Intent.createChooser(intent, "Select File"),
                        GALLERY_IMAGE);

                activity.overridePendingTransition(R.anim.slide_up_in,
                        R.anim.slide_up_out);*/

                break;

            default:

                break;
        }
    }

    @SuppressLint("NewApi")
    @Override
    public boolean onDrag(View v, DragEvent event) {

        switch (event.getAction()) {

            case DragEvent.ACTION_DROP:

                ImageView target = (ImageView) v;

                ImageView dragged = (ImageView) event.getLocalState();

                Bitmap bmp_target = ((BitmapDrawable) target.getDrawable()).getBitmap();

                Bitmap bmp_dragged = ((BitmapDrawable) dragged.getDrawable()).getBitmap();

                dragged.setImageBitmap(bmp_target);

                target.setImageBitmap(bmp_dragged);

                Common.swapImagesPaths(new int[]{(int) v.getTag(), (int) dragged.getTag()},
                        new Bitmap[]{Common.selected_bitmaps.get((int) v.getTag()),
                                Common.selected_bitmaps.get((int) dragged.getTag())});

        }

        return true;

    }

    @Override
    public boolean onLongClick(View v) {

        ClipData data = ClipData.newPlainText("", "");

        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

        v.startDrag(data, shadowBuilder, v, 0);

        return true;

    }

    /*private void swapImagesPaths(int[] images_tags, Bitmap[] images_bitmps) {

        PIP_Common.selected_bitmaps.set(images_tags[0], images_bitmps[1]);

        PIP_Common.selected_bitmaps.set(images_tags[1], images_bitmps[0]);

    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_IMAGE && resultCode == activity.RESULT_OK &&
                data != null) {

            Bitmap bmp = null;

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {

                bmp = Common.getImageFromKitkat(getActivity(), data);

                /*ParcelFileDescriptor parcelFileDescriptor = null;
                try {
                    parcelFileDescriptor = activity.getContentResolver().
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

                    bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor,
                            null, options);

                    parcelFileDescriptor.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            } else {

                bmp = Common.onSelectFromGalleryResult(activity, data);

            }

            if (bmp != null) {

                Common.selected_bitmaps.set(Common.selected_image_number, bmp);

                if (Common.selected_image_number == 0) {

                    tiv_image_1.setImageBitmap(Common.selected_bitmaps.get(0));

                } else if (Common.selected_image_number == 1) {

                    tiv_image_2.setImageBitmap(Common.selected_bitmaps.get(1));

                } else if (Common.selected_image_number == 2) {

                    tiv_image_3.setImageBitmap(Common.selected_bitmaps.get(2));

                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        LocalBroadcastManager.getInstance(activity).registerReceiver(recieve_visibility_broadcast,
                new IntentFilter("Visibility"));

        Common.changeFrameColor(activity);

        // Common.getSetZoom(false, tiv_image_1, tiv_image_2, tiv_image_3, null, null);

    }

    @Override
    public void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(activity).
                unregisterReceiver(recieve_visibility_broadcast);

        //Common.getSetZoom(true, tiv_image_1, tiv_image_2, tiv_image_3, null, null);

    }

    private void changeImage(int number) {

        Common.selected_image_number = number;

        Intent intent;

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {

            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select File"), GALLERY_IMAGE);

        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {

            String permission = Common.permissionStorage.getString("storage", "");

            if (permission.equals("true")) {

                intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select File"), GALLERY_IMAGE);

            } else {

                checkPermission();

            }
        } else {

            intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select File"), GALLERY_IMAGE);

        }
    }

    private void checkPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasWritePermission = getActivity().checkSelfPermission(Manifest.permission.
                    WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = getActivity().checkSelfPermission(Manifest.permission.
                    READ_EXTERNAL_STORAGE);
            int hasCameraPermission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);

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

    /*public Bitmap svgToBitmap(Resources res, int resource, int height, int width) {
        try {
            // size = (int)(size*res.getDisplayMetrics().density);
            SVG svg = SVGParser.getSVGFromResource(res, resource);

            PictureDrawable pictureDrawable = svg.createPictureDrawable();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawPicture(pictureDrawable.getPicture(), new Rect(0, 0, width, height));

            // svg.renderToCanvas(canvas);

            return bitmap;
        } catch (SVGParseException e) {

            e.printStackTrace();

        }

        return null;

    }*/
}
