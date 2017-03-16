package com.example.androidcodes.photoeditingapp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidcodes.photoeditingapp.Frames.Adapters.MyAlbum_ViewPager_Adapter;
import com.example.androidcodes.photoeditingapp.Frames.Common.Common;
import com.example.androidcodes.photoeditingapp.Frames.FloatingActionButton.FloatingActionMenu;
import com.example.androidcodes.photoeditingapp.PIP.Common.PIP_Common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by peacock on 11/4/16.
 */
public class View_AlbumActivity extends Activity implements View.OnClickListener {

    private int current_item;

    private ArrayList<String> imagesPaths;

    private ArrayList<Bitmap> imagesBitmaps;

    private MyAlbum_ViewPager_Adapter adapter;

    private Intent intent;

    private ViewPager vp_albumImages;

    private FloatingActionMenu fam_options;

    //private ImageView iv_previous, iv_next;

    private Dialog exit_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_album);

        PIP_Common.tempFlag = false;

        current_item = Common.show_selected_image;

        imagesPaths = Common.all_images_paths;

        imagesBitmaps = Common.all_images_bitmaps;

        vp_albumImages = (ViewPager) findViewById(R.id.vp_albumImages);
        vp_albumImages.setPageMargin(20);
        vp_albumImages.setOffscreenPageLimit(1);

        /*iv_previous = (ImageView) findViewById(R.id.iv_previous);
        iv_previous.setOnClickListener(this);

        iv_next = (ImageView) findViewById(R.id.iv_next);
        iv_next.setOnClickListener(this);*/

        fam_options = (FloatingActionMenu) findViewById(R.id.fam_options);
        fam_options.setClosedOnTouchOutside(true);

        findViewById(R.id.fab_home).setOnClickListener(this);

        findViewById(R.id.fab_share).setOnClickListener(this);

        findViewById(R.id.fab_delete).setOnClickListener(this);

        adapter = new MyAlbum_ViewPager_Adapter(View_AlbumActivity.this, imagesBitmaps);

        vp_albumImages.setAdapter(adapter);
        vp_albumImages.setCurrentItem(current_item);
        vp_albumImages.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

                fam_options.close(true);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        current_item = vp_albumImages.getCurrentItem();

        switch (v.getId()) {

            /*case R.id.iv_previous:

                if ((current_item - 1) >= 0) {

                    vp_albumImages.setCurrentItem(current_item - 1);

                }

                break;

            case R.id.iv_next:

                if ((current_item + 1) <= (imagesPaths.size() - 1)) {

                    vp_albumImages.setCurrentItem(current_item + 1);

                }

                break;*/

            case R.id.fab_home:

                finish();

                intent = new Intent(View_AlbumActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                overridePendingTransition(R.anim.left_in, R.anim.right_out);

                break;

            case R.id.fab_share:

                fam_options.close(true);

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");

                Uri imgPathUri = Uri.fromFile(new File(imagesPaths.get(current_item)));

                shareIntent.putExtra(Intent.EXTRA_STREAM, imgPathUri);

                startActivity(Intent.createChooser(shareIntent, "Share Image..."));

                break;

            case R.id.fab_delete:

                exit_dialog = new Dialog(View_AlbumActivity.this);
                exit_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                exit_dialog.setContentView(R.layout.exit_app_dialog);
                exit_dialog.setCancelable(true);
                exit_dialog.setCanceledOnTouchOutside(true);

                TextView tv_actionIcon = (TextView) exit_dialog.findViewById(R.id.tv_actionIcon);
                tv_actionIcon.setBackgroundResource(R.drawable.delete_pager);

                TextView tv_exitText = (TextView) exit_dialog.findViewById(R.id.tv_text);
                tv_exitText.setTextColor(getResources().getColor(R.color.exit_text));
                tv_exitText.setText(getString(R.string.delete_image));

                Button btn_exit = (Button) exit_dialog.findViewById(R.id.btn_exit);
                btn_exit.setTextColor(getResources().getColor(R.color.frame_inside_color));
                btn_exit.setText(getText(R.string.cancel));
                btn_exit.setOnClickListener(this);

                Button btn_cancel = (Button) exit_dialog.findViewById(R.id.btn_cancel);
                btn_cancel.setText(getText(R.string.delete));
                btn_cancel.setTextColor(getResources().getColor(R.color.frame_inside_color));
                btn_cancel.setOnClickListener(this);

                exit_dialog.show();

                break;

            case R.id.btn_cancel:

                fam_options.close(true);

                File detect_image = new File(imagesPaths.get(current_item).toString());

                if (detect_image.exists() == true) {

                    Boolean deleted = detect_image.delete();

                    if (deleted == true) {

                        Toast.makeText(View_AlbumActivity.this, "Deleted Successfully",
                                Toast.LENGTH_SHORT).show();

                        imagesPaths.remove(current_item);

                        imagesBitmaps.remove(current_item);

                        Common.all_images_paths = imagesPaths;

                        Common.all_images_bitmaps = imagesBitmaps;

                        if (imagesBitmaps.size() > 0) {

                            vp_albumImages.setAdapter(
                                    new MyAlbum_ViewPager_Adapter(View_AlbumActivity.this,
                                            imagesBitmaps));

                            vp_albumImages.setCurrentItem(current_item, true);

                        } else {

                            fam_options.toggleMenu(false);

                            vp_albumImages.setVisibility(View.GONE);

                            fam_options.setEnabled(false);

                            /*iv_next.setVisibility(View.GONE);

                            iv_previous.setVisibility(View.GONE);*/

                            finish();

                            intent = new Intent(View_AlbumActivity.this,
                                    Album_Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                            overridePendingTransition(R.anim.left_in, R.anim.right_out);

                        }
                    }
                }

                exit_dialog.cancel();

                break;

            case R.id.btn_exit:

                exit_dialog.cancel();

                break;

            default:

                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Common.changeActivity(View_AlbumActivity.this, Album_Activity.class, true);

       /* finish();

        intent = new Intent(View_AlbumActivity.this, Album_Activity.class);
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
}
