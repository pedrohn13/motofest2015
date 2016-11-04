package br.edu.ufcg.embedded.motofest.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufcg.embedded.motofest.R;
import br.edu.ufcg.embedded.motofest.utils.CameraFile;
import br.edu.ufcg.embedded.motofest.utils.MoldurasListView;


public class ImageActivity extends Activity {

    private ProgressBar progressBar;
    private ImageView ivPicture;
    private static final int[] MOLDURAS = {R.drawable.app_motofest_xxxhdpi_molduras_0000_moldura_00,
            R.drawable.app_motofest_xxxhdpi_molduras_0001_moldura_01,
            R.drawable.app_motofest_xxxhdpi_molduras_0002_moldura_02,
            R.drawable.app_motofest_xxxhdpi_molduras_0003_moldura_03,
            R.drawable.app_motofest_xxxhdpi_molduras_0004_moldura_04};
    private static final int[] MOLDURAS_PEQUENAS = {R.drawable.app_motofest_xxxhdpi_molduras_0000_moldura_00_small,
            R.drawable.app_motofest_xxxhdpi_molduras_0001_moldura_01_small,
            R.drawable.app_motofest_xxxhdpi_molduras_0002_moldura_02_small,
            R.drawable.app_motofest_xxxhdpi_molduras_0003_moldura_03_small,
            R.drawable.app_motofest_xxxhdpi_molduras_0004_moldura_04_small};

    private String pictureFile;
    private boolean imported;

    private static final int QUALIDADE = 100;
    private static final String LOG = "Image Activity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_activity);
        Intent intent = getIntent();
        pictureFile = intent.getStringExtra("PICTURE_FILE");
        imported = intent.getBooleanExtra("IMPORTED", false);
        int moldura = intent.getIntExtra("MOLDURA_POSICAO", 0);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        ivPicture = (ImageView) findViewById(R.id.iv_image_taked);
        ImageView shared = (ImageView) findViewById(R.id.iv_shared);
        ImageView sharedIcon = (ImageView) findViewById(R.id.iv_shared_icon);
        ImageView save = (ImageView) findViewById(R.id.iv_save);
        ImageView close = (ImageView) findViewById(R.id.iv_close);

        MoldurasListView listview = (MoldurasListView) findViewById(R.id.lv_molduras);
        listview.setAdapter(mAdapter);

        try {
            aplicaMoldura(moldura);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_SHORT).show();
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = CameraFile.getOutputMediaFile();
                saveImageView(file);

                onShareClick(file);
            }
        });

        sharedIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = CameraFile.getOutputMediaFile();
                saveImageView(file);

                onShareClick(file);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = CameraFile.getOutputMediaFile();
                saveImageView(file);
                Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.img_saved), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(file);
                mediaScanIntent.setData(contentUri);
                ImageActivity.this.sendBroadcast(mediaScanIntent);
            }
        });
    }

    private void onShareClick(File file) {
        List<Intent> targetShareIntents = new ArrayList<>();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        List<ResolveInfo> resInfos = getPackageManager().queryIntentActivities(shareIntent, 0);

        for (ResolveInfo resInfo : resInfos) {
            String packageName = resInfo.activityInfo.packageName;
            if (packageName.contains("com.twitter.android")
                    || packageName.contains("com.facebook.katana")
                    || packageName.contains("com.instagram.android")
                    || packageName.contains("com.whatsapp")
                    && !resInfo.activityInfo.name.contains("com.twitter.android.DMActivity")) {
                Intent intent = getNewIntent();
                ComponentName comp = getNewComponentName(resInfo, packageName);
                intent.setComponent(comp);
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///" + file.toString()));
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));
                intent.setPackage(packageName);
                targetShareIntents.add(intent);
            }
        }

        if (targetShareIntents.size() > 0) {
            Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), getString(R.string.choose_app));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
            startActivity(chooserIntent);
        } else {
            Toast.makeText(this, getString(R.string.no_app_to_share), Toast.LENGTH_LONG).show();
        }
    }

    private ComponentName getNewComponentName(ResolveInfo resInfo, String packageName) {
        return new ComponentName(packageName, resInfo.activityInfo.name);
    }

    private Intent getNewIntent() {
        return new Intent();
    }

    private void saveImageView(File file) {
        Bitmap bitmap1 = ((BitmapDrawable) ivPicture.getDrawable()).getBitmap();
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(file);
            bitmap1.compress(Bitmap.CompressFormat.PNG, QUALIDADE, out);
        } catch (Exception e) {
            Log.d(LOG,
                    "Error creating file, check storage permissions.");
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                Log.d(LOG,
                        "Error closing file");
            }
        }
    }

    private BaseAdapter mAdapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return MOLDURAS.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater viewAux = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View retval = viewAux.inflate(R.layout.list_item_image, parent, false);
            ImageView image = (ImageView) retval.findViewById(R.id.image);
            image.setImageResource(MOLDURAS_PEQUENAS[position]);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((BitmapDrawable) ivPicture.getDrawable()).getBitmap().recycle();
                    aplicaMoldura(position);
                }
            });

            return retval;
        }
    };

    private void aplicaMoldura(int position) {
        progressBar.setVisibility(View.VISIBLE);
        Bitmap bitmap1 = BitmapFactory.decodeFile(pictureFile);
        Bitmap bitmap = Bitmap.createBitmap(bitmap1.getHeight(), bitmap1.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap bitmap2 = BitmapFactory.decodeResource(getApplicationContext().getResources(), MOLDURAS[position]);
        Drawable drawable2 = new BitmapDrawable(bitmap2);
        Canvas canvas = new Canvas(bitmap);
        if (imported) {
            float originalWidth = bitmap1.getWidth(), originalHeight = bitmap1.getHeight();
            float scale = bitmap1.getHeight() / originalWidth;
            float xTranslation = 0.0f, yTranslation = (bitmap1.getHeight() - originalHeight * scale) / 2.0f;
            Matrix transformation = new Matrix();
            transformation.postTranslate(xTranslation, yTranslation);
            transformation.preScale(scale, scale);
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            canvas.drawBitmap(bitmap1, transformation, paint);

        } else {
            bitmap1 = Bitmap.createBitmap(bitmap1, 0, 0, bitmap1.getHeight(), bitmap1.getHeight());
            Drawable drawable1 = new BitmapDrawable(bitmap1);
            drawable1.setBounds(0, 0, bitmap1.getHeight(), bitmap1.getHeight());
            drawable1.draw(canvas);
        }
        drawable2.setBounds(0, 0, bitmap1.getHeight(), bitmap1.getHeight());
        drawable2.draw(canvas);
        progressBar.setVisibility(View.INVISIBLE);
        ivPicture.setImageBitmap(bitmap);
    }


}
