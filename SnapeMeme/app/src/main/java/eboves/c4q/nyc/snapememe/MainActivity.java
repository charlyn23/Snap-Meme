package eboves.c4q.nyc.snapememe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;


public class MainActivity extends ActionBarActivity {



    static final int REQUEST_IMAGE_CAPTURE = 1;
    private File file;
    private ImageView imageHold;
    private ImageView ivCamera;
    private ImageView ivGallery;
    private ImageView ivExisting;
   // MemeSnap memeSnapClass = new MemeSnap();

    // onCreate should set the stage for gallery access, camera access and existing meme access
    // Basically 3 intents
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myImage.jpg");


      //  memeSnapClass.displayCameraBitmap();
        displayCameraBitmap();

        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    //modify URI
                    Uri outputFileUri = Uri.parse(Environment.DIRECTORY_PICTURES);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        ivGallery = (ImageView) findViewById(R.id.ivGallery);
        ivGallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intentGallery = new Intent(MainActivity.this, MemeSnap.class);
                startActivity(intentGallery);
            }
        });

        ivExisting = (ImageView) findViewById(R.id.ivExisting);
        ivExisting.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View view) {

                Intent intentExisting = new Intent(MainActivity.this, MemeSnap.class);
                startActivity(intentExisting);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //memeSnapClass.displayCameraBitmap();
            displayCameraBitmap();
            // HERE WE PUT THE INTENT TO GO TO THE VANILLA OR DEMOTIVATIONAL
            // HERE PUT EXTRA WITH THE IMAGE THAT YOU JUST TOOK
        }
    }

    private void displayCameraBitmap() {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(file.getAbsolutePath());
            int orientation =   exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        BitmapFactory.Options options = new BitmapFactory.Options();
        // INSTEAD OF FILE GET IT FROM BUNDLE
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        //imageHold.setImageDrawable(new FakeBitmapDrawable(bitmap, degree));
    }

}
