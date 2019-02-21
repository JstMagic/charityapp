package com.kodemakers.charity.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kodemakers.charity.R;
import com.kodemakers.charity.custom.AndroidMultiPartEntity;
import com.kodemakers.charity.custom.AppConstants;
import com.kodemakers.charity.custom.FileUploadNotification;
import com.kodemakers.charity.custom.FileUtil;
import com.kodemakers.charity.custom.PathUtils;
import com.kodemakers.charity.custom.PrefUtils;
import com.kodemakers.charity.model.FeedsDetails;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class AddVideoFeedActivity extends AppCompatActivity {

    CardView cvTitle,cvPhoto;
    EditText etTitle,etDescription;
    LinearLayout imgPost,llChange;
    VideoView vv;
    TextView tvAdd;
    ImageView image;

    private Toolbar toolbar;
    private int SELECT_FILE = 1;
    int click = 0;
    private AlertDialog.Builder alertDialogBuilder;
    private AlertDialog alertDialog;
    Uri uri;
    Bitmap thumb;

    File path;
    private File thumbnail;
    long totalSize = 0;
    FeedsDetails feedsDetails;

//    Button btnChange;

    SharedPreferences settings2;
    public String temporary;
    public String userid;
    public static int cx, cy;
    private FirebaseDatabase mFirebaseDatabase;
    final String PREFS_NAME2 = "MyPrefsFile2";
    private FirebaseAuth mAuth;
    private static DatabaseReference myRef;

    FileUploadNotification fileUploadNotification;

    ProgressDialog progressDialog;
    //    LinearLayout llvv;
    ImageView imgBack;
    LinearLayout llPost;
    TextView tvPost;
    private Animation ac;
    private Animation aa;
    private Animation ab;
    boolean is_title=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video_feed);

        setToolbar();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        progressDialog = new ProgressDialog(AddVideoFeedActivity.this);

        etTitle = (EditText) findViewById(R.id.etTitle);
        etDescription = (EditText) findViewById(R.id.etDescription);
        vv = (VideoView) findViewById(R.id.vv);
        imgPost = (LinearLayout) findViewById(R.id.imgPost);
        tvAdd = findViewById(R.id.tvAdd);
        image = findViewById(R.id.image);
        llChange = findViewById(R.id.llChange);

        vv.setVisibility(View.GONE);
        llChange.setVisibility(View.GONE);
        image.setColorFilter(Color.parseColor("#03a9f4"));

        feedsDetails = new FeedsDetails();
        feedsDetails = (FeedsDetails) getIntent().getSerializableExtra("FeedDetails");

        if(getIntent().getStringExtra("type").equalsIgnoreCase("edit")){
                etTitle.setText(feedsDetails.getTitle());
                etDescription.setText(feedsDetails.getDetails());
            imgPost.setVisibility(View.GONE);
            // Start the MediaController
            MediaController mediacontroller = new MediaController(AddVideoFeedActivity.this);
            mediacontroller.setAnchorView(vv);
            mediacontroller.setMediaPlayer(vv);
            // Get the URL from String VideoURL
            vv.setMediaController(mediacontroller);
            vv.setVisibility(View.VISIBLE);
                vv.setVideoURI(Uri.parse(AppConstants.BASE_URL + feedsDetails.getVideoUrl()));
            vv.requestFocus();
            vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {
                    vv.start();
                }
            });
            llChange.setVisibility(View.VISIBLE);
            llChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        videoIntent();
                    }
                });
        }

        imgPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoIntent();
            }
        });


        if(getIntent().getStringExtra("type").equalsIgnoreCase("edit")){
            tvAdd.setText("Edit");
        }else{
            tvAdd.setText("Add");
        }

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uri != null){
                    saveData();
                }

            }
        });

    }

    private void videoIntent() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select Video File"), SELECT_FILE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                uri = data.getData();
//                btnChange.setText("Change Video");
                vv.setVisibility(View.VISIBLE);
                imgPost.setVisibility(View.GONE);

//                if (getMimeType(uri).startsWith("video")) {

                onSelectFromVideoResult(data);
//                } else {
//
//                    Toast.makeText(AddVideoFeedActivity.this, "Please Select Video File Only.", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
            }
        } else {
            finish();
        }
    }

    private void onSelectFromVideoResult(Intent data) {

        try {
            path = FileUtil.from(AddVideoFeedActivity.this, data.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            uri = data.getData();


            // Start the MediaController
            MediaController mediacontroller = new MediaController(AddVideoFeedActivity.this);
            mediacontroller.setAnchorView(vv);
            mediacontroller.setMediaPlayer(vv);
            // Get the URL from String VideoURL
            vv.setMediaController(mediacontroller);
            vv.setVideoURI(uri);
            thumb = ThumbnailUtils.createVideoThumbnail(PathUtils.getPath(AddVideoFeedActivity.this, uri), MediaStore.Video.Thumbnails.MINI_KIND);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        vv.requestFocus();
        vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                vv.start();
            }
        });
    }

    private void saveData() {
        FileInputStream fis = null;
        byte[] bytesArray = null;
        try {
            bytesArray = new byte[(int) new File(PathUtils.getPath(AddVideoFeedActivity.this, uri)).length()];
            fis = new FileInputStream(new File(PathUtils.getPath(AddVideoFeedActivity.this, uri)));
            fis.read(bytesArray); //read file into bytes[]
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] byteArrayImage = null;
        try {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            thumbnail = new File(getFilesDir(), "nirav" + ".jpg");
            FileOutputStream fos = new FileOutputStream(thumbnail);
            thumb.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            byteArrayImage = stream.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("thumbnail", thumbnail.toString());
        Log.e("path", path.toString());


        if(getIntent().getStringExtra("type").equalsIgnoreCase("edit")){
            new EditFileToServerVideo().execute();
        }else{
            new UploadFileToServerVideo().execute();
        }



//        Toast.makeText(AddVideoFeedActivity.this, "Video Upload in Progress", Toast.LENGTH_SHORT).show();
//        finish();
    }


    private class UploadFileToServerVideo extends AsyncTask<Void, Integer, String> {


        final String url = AppConstants.ADD_VIDEO_FEEDS;


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(final Integer... progress) {


            //code to show progress in notification bar
//             fileUploadNotification = new FileUploadNotification(AddVideoFeedActivity.this);
//            fileUploadNotification.updateNotification(String.valueOf(progress[0]), path.getName(), "Video Upload","Upload Complete");


            progressDialog.setMessage("Uploading Video... " + String.valueOf(progress[0] + "%"));
            progressDialog.setCancelable(false);
            progressDialog.show();


        }


        @Override
        protected String doInBackground(Void... params) {

            return uploadFile();
        }


        private String uploadFile() {
            String responseString = null;

            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("UTF-8"),
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });


                // Extra parameters if you want to pass to server
                entity.addPart("title", new StringBody(etTitle.getText().toString().trim().replace("\'", ""), Charset.forName("UTF-8")));
                entity.addPart("details", new StringBody(etDescription.getText().toString().trim().replace("\'", ""), Charset.forName("UTF-8")));
                entity.addPart("charity_id", new StringBody(PrefUtils.getUser(AddVideoFeedActivity.this).getCharityId()));
                entity.addPart("file", new FileBody(path));
                entity.addPart("video_thumbnail", new FileBody(thumbnail));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);


                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();


                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;

                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("upload", "Response from server: " + result);

            // showing the server response in an alert dialog
//            showAlert(result);

            super.onPostExecute(result);

            Toast.makeText(AddVideoFeedActivity.this, "Feed Added Successfully", Toast.LENGTH_SHORT).show();

            progressDialog.dismiss();

            finish();
        }

    }

    private class EditFileToServerVideo extends AsyncTask<Void, Integer, String> {


        final String url = AppConstants.EDIT_VIDEO_FEEDS;


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(final Integer... progress) {


            //code to show progress in notification bar
//             fileUploadNotification = new FileUploadNotification(AddVideoFeedActivity.this);
//            fileUploadNotification.updateNotification(String.valueOf(progress[0]), path.getName(), "Video Upload","Upload Complete");


            progressDialog.setMessage("Uploading Video... " + String.valueOf(progress[0] + "%"));
            progressDialog.setCancelable(false);
            progressDialog.show();


        }


        @Override
        protected String doInBackground(Void... params) {

            return uploadFile();
        }


        private String uploadFile() {
            String responseString = null;

            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, Charset.forName("UTF-8"),
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });


                // Extra parameters if you want to pass to server
                entity.addPart("feed_id", new StringBody(feedsDetails.getFeedId()));
                entity.addPart("title", new StringBody(etTitle.getText().toString().trim().replace("\'", ""), Charset.forName("UTF-8")));
                entity.addPart("details", new StringBody(etDescription.getText().toString().trim().replace("\'", ""), Charset.forName("UTF-8")));
                entity.addPart("charity_id", new StringBody(PrefUtils.getUser(AddVideoFeedActivity.this).getCharityId()));
                entity.addPart("file", new FileBody(path));
                entity.addPart("video_thumbnail", new FileBody(thumbnail));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);


                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();


                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;

                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.e("upload", "Response from server: " + result);

            // showing the server response in an alert dialog
//            showAlert(result);

            super.onPostExecute(result);

            Toast.makeText(AddVideoFeedActivity.this, "Feed Updated Successfully", Toast.LENGTH_SHORT).show();

            progressDialog.dismiss();

            finish();
        }

    }

    // convert internal Java String format to UTF-8
    public static String convertStringToUTF8(String s) {
        String out = null;
        try {
            out = new String(s.getBytes("UTF-8"), "ISO-8859-1");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
        return out;
    }

    public String getMimeType(Uri uri) {
        String mimeType = null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = AddVideoFeedActivity.this.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

//    public void startauthentication() {
//
//        try {
//            LoginDetailsModel user = PrefUtils.getUser(AddVideoFeedActivity.this);
//            if (user.user_id.length() < 0) {
//                Intent i = new Intent(AddVideoFeedActivity.this, LoginOptionsActivity.class);
//                startActivity(i);
//                finish();
//            }else {
//                doPostNetworkOperationUserStatus(PrefUtils.getUser(AddVideoFeedActivity.this).user_id);
//            }
//        } catch (Exception e) {
//            Intent i = new Intent(AddVideoFeedActivity.this, LoginOptionsActivity.class);
//            startActivity(i);
//            finish();
//        }
//    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            if (getIntent().getStringExtra("type").equalsIgnoreCase("edit")) {
                toolbar.setTitle("Edit Video");
            }else {
                toolbar.setTitle("Add Video");
            }
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
            toolbar.setTitleTextColor(Color.parseColor("#000000"));
            toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }


}
