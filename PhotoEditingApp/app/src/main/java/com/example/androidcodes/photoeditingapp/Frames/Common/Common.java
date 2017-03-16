package com.example.androidcodes.photoeditingapp.Frames.Common;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.example.androidcodes.photoeditingapp.Frames.CustomGallery.Albums;
import com.example.androidcodes.photoeditingapp.Frames.EditImages.Edit_Images;
import com.example.androidcodes.photoeditingapp.R;

import java.io.File;
import java.io.FileDescriptor;
import java.util.ArrayList;

/**
 * Created by peacock on 26/3/16.
 */
public class Common {

    //public static float tiv_zoom_1, tiv_zoom_2, tiv_zoom_3, tiv_zoom_4, tiv_zoom_5;

    //public static ImageLoader imageLoader;

    public static int screenWidth, screenHeight;

    public static int frame_number = 0, selected_image_number = 0, num_of_selected_images = 0,
            show_selected_image = 1, frameColor = Color.WHITE;

    public static Bitmap edit_selected_framed_image = null, final_frame = null;

    public static ArrayList<Albums> allAlbums;

    public static ArrayList<String> all_images_paths;

    public static ArrayList<String> selected;

    public static ArrayList<Bitmap> selected_bitmaps, all_images_bitmaps;

    public static boolean isFromPIP = false, threeFlag = false, fourFlag = false;

    public static SharedPreferences permissionStorage;

    public static ImageView iv_frame_1, iv_frame_2, iv_frame_3, iv_frame_4, iv_frame_5, iv_1, iv_2,
            iv_3, iv_4;

    //public static LinearLayout ll_save;

    public static File file_path = new File(Environment.getExternalStorageDirectory(),
            "/Photo Editing App/");

    public static Bitmap onSelectFromGalleryResult(Activity activity, Intent data) {

        Bitmap selected_bimap = null;

        Uri selectedImageUri = data.getData();

        String[] projection = {MediaStore.MediaColumns.DATA};

        Cursor cursor = activity.managedQuery(selectedImageUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);

        final int REQUIRED_SIZE = 1080;

        int scale = 1;

        while (options.outWidth / scale / 2 >= REQUIRED_SIZE &&
                options.outHeight / scale / 2 >= REQUIRED_SIZE) scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;

        return (selected_bimap = BitmapFactory.decodeFile(selectedImagePath, options));
    }

    public static void applyEffect(int image_number, Activity activity) {

        selected_image_number = image_number;

        edit_selected_framed_image = selected_bitmaps.get(image_number);

        if (edit_selected_framed_image != null) {

            activity.startActivity(new Intent(activity, Edit_Images.class));

            activity.overridePendingTransition(R.anim.slide_up_in, R.anim.slide_up_out);

        }
    }

    public static void swapImagesPaths(int[] images_tags, Bitmap[] images_bitmps) {

        selected_bitmaps.set(images_tags[0], images_bitmps[1]);

        selected_bitmaps.set(images_tags[1], images_bitmps[0]);

    }

    public static boolean isStorageAvailable() {

        String storageState = Environment.getExternalStorageState();

        if (storageState.equals(Environment.MEDIA_MOUNTED)) {

            return true;

        } else {

            return false;

        }
    }

    public static void changeActivity(Activity fromActivity, Class toActivity, boolean flag) {

        Intent intent = new Intent(fromActivity, toActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        fromActivity.startActivity(intent);

        fromActivity.finish();

        if (flag == false) {

            fromActivity.overridePendingTransition(R.anim.right_in, R.anim.left_out);

        } else {

            fromActivity.overridePendingTransition(R.anim.left_in, R.anim.right_out);

        }
    }

    public static Bitmap getImageFromKitkat(Activity activity, Intent data) {

        ParcelFileDescriptor parcelFileDescriptor = null;
        try {
            parcelFileDescriptor = activity.getContentResolver().
                    openFileDescriptor(data.getData(), "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
            final int REQUIRED_SIZE = 700;
            int scale = 1;
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                    && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;

            Bitmap bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor,
                    null, options);

            parcelFileDescriptor.close();

            return bmp;

        } catch (Exception e) {

            System.out.print("KitkatGalleryException ...>>>..." + e.getMessage());

            return null;

        }
    }

    public static void getImagesFromExternalStorage() {

        File[] listFile = null;

        all_images_paths = new ArrayList<>();

        all_images_bitmaps = new ArrayList<>();

        if (file_path.isDirectory()) {

            listFile = file_path.listFiles();

            for (int i = 0; i < listFile.length; i++) {

                all_images_paths.add(listFile[i].toString());

                all_images_bitmaps.add(getResizedBitmap(BitmapFactory.
                        decodeFile(all_images_paths.get(i)), 1080, 1080));

            }
        }
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {

        int width = bm.getWidth();

        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();

        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();

        return resizedBitmap;

    }

    public static void editSharedPreferences(String tag, String value) {

        SharedPreferences.Editor editPermissions = permissionStorage.edit();
        editPermissions.putString(tag, value);
        editPermissions.commit();

    }

    /*public static void initImageLoader(Context context) {
        try {

            String CACHE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/.temp_tmp";
            new File(CACHE_DIR).mkdirs();

            File cacheDir = StorageUtils.getOwnCacheDirectory(context, CACHE_DIR);

            DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                    .cacheOnDisc(true).cacheInMemory(true).cacheOnDisk(true).
                            displayer(new RoundedBitmapDisplayer(8)).bitmapConfig(Bitmap.
                            Config.RGB_565).build();
            ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.
                    Builder(context).defaultDisplayImageOptions(defaultOptions)
                    .memoryCache(new WeakMemoryCache());

            ImageLoaderConfiguration config = builder.build();
            imageLoader = ImageLoader.getInstance();
            imageLoader.init(config);

        } catch (Exception e) {

        }
    }*/

    public static void changeFrameColor(Activity activity) {

        iv_frame_1.setColorFilter(frameColor, PorterDuff.Mode.MULTIPLY);

        iv_frame_2.setColorFilter(frameColor, PorterDuff.Mode.MULTIPLY);

        iv_frame_3.setColorFilter(frameColor, PorterDuff.Mode.MULTIPLY);

        if (threeFlag == true) {

            iv_1.setBackgroundColor(frameColor);

            iv_2.setBackgroundColor(frameColor);

            iv_3.setBackgroundColor(frameColor);

            if (fourFlag == true) {

                iv_4.setBackgroundColor(frameColor);

            }
        }

        if (num_of_selected_images > 3) {

            iv_frame_4.setColorFilter(frameColor, PorterDuff.Mode.MULTIPLY);

            if (num_of_selected_images == 5) {

                iv_frame_5.setColorFilter(frameColor, PorterDuff.Mode.MULTIPLY);

            }
        }
    }

    public static ArrayList<Albums> getAlbums(Activity activity) {

        allAlbums = new ArrayList<>();

        String[] projection = new String[]{"Distinct " +
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cur = activity.getContentResolver().query(images,
                projection, // Which columns to return
                null,       // Which rows to return (all rows)
                null,       // Selection arguments (none)
                null        // Ordering
        );

        if (cur.moveToFirst()) {

            int bucketColumn = cur.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

            do {

                photoCountByAlbum(activity, cur.getString(bucketColumn));

            } while (cur.moveToNext());
        }

        /*for (int i = 0; i < Common.albums.size(); i++) {

            System.out.println("result ==> Name : " + Common.albums.get(i).getAlbumName() +
                    ", Count : " + Common.albums.get(i).getCount() + ", Logo : " +
                    Common.albums.get(i).getAlbumLogoPath());

        }

        for (int j = 0; j < Common.albums.size(); j++) {

            System.out.print("\n result --> " + Common.albums.get(j).getImages());

        }*/

        cur.close();

        return allAlbums;

    }

    public static void photoCountByAlbum(Activity activity, String bucketName) {

        String searchParams = null, albumName = bucketName, albumLogoPath = "";

        int count = 0;

        try {

            final String orderBy = MediaStore.Images.Media.DATE_TAKEN;

            searchParams = "bucket_display_name = \"" + albumName + "\"";

            Cursor mPhotoCursor = activity.getContentResolver().
                    query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            new String[]{MediaStore.MediaColumns.DATA}, searchParams, null,
                            orderBy + " DESC");

            if (null != mPhotoCursor) {

                if (mPhotoCursor.getCount() > 0) {

                    count = mPhotoCursor.getCount();

                    if (mPhotoCursor.moveToFirst()) {

                        albumLogoPath = mPhotoCursor.getString(0);

                    }
                }

                Common.allAlbums.add(new Albums(albumName, albumLogoPath, count,
                        getAlbumImages(activity, albumName)));

                mPhotoCursor.close();

            }

        } catch (Exception e) {

            System.out.println("Exception --> " + e.getMessage());

        }
    }

    public static ArrayList<String> getAlbumImages(Activity activity, String albumName) {

        ArrayList<String> imagesPaths = new ArrayList<>();

        String[] projection = {MediaStore.MediaColumns.DATA};

        String selection = MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " = ?";

        String[] selectionArgs = {albumName};

        final Cursor cursor = activity.getContentResolver().
                query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection,
                        selectionArgs, MediaStore.Images.Media.DATE_TAKEN + " desc");

        if (cursor.moveToFirst()) {

            final int dataColumn = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

            do {

                imagesPaths.add(cursor.getString(dataColumn));

            } while (cursor.moveToNext());
        }

        cursor.close();

        return imagesPaths;

    }

    /*public static void getSetZoom(boolean flag, TouchImageView tiv1, TouchImageView tiv2,
                                  TouchImageView tiv3, TouchImageView tiv4, TouchImageView tiv5) {

        if (flag == false) {

            tiv1.setZoom(tiv_zoom_1);

            tiv2.setZoom(tiv_zoom_2);

            tiv3.setZoom(tiv_zoom_3);

            if (num_of_selected_images == 4) {

                tiv4.setZoom(tiv_zoom_4);

            } else if (num_of_selected_images == 5) {

                tiv5.setZoom(tiv_zoom_5);

            }
        } else {

            tiv_zoom_1 = tiv1.getCurrentZoom();

            tiv_zoom_2 = tiv2.getCurrentZoom();

            tiv_zoom_3 = tiv3.getCurrentZoom();

            if (num_of_selected_images == 4) {

                tiv_zoom_4 = tiv4.getCurrentZoom();

            } else if (num_of_selected_images == 5) {

                tiv_zoom_5 = tiv5.getCurrentZoom();

            }
        }
    }*/

    /*public static void hideKeyboard(Activity activity) {

        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity
                    .getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void showKeyboard(Activity activity) {

        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

    }

    public String compressImage(String imagePath) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

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

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
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
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2,
                new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return filename;

    }*/
   /* public static ArrayList<String> getAllImagesPaths() {

        File file_share = new File(Environment.getExternalStorageDirectory(),
                "/Photo Editing App/Frames/");

        if (file_share.isDirectory()) {

            listFile = file_share.listFiles();

            for (int i = 0; i < listFile.length; i++) {

                imagesPaths.add(listFile[i].getAbsolutePath());

            }
        }
    }*/
}
