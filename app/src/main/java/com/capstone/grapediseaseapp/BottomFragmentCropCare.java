package com.capstone.grapediseaseapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstone.grapediseaseapp.ml.Model1;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;


public class BottomFragmentCropCare extends Fragment {

    Button upload_picture, take_picture, btnInfo;
    TextView result;
    ImageView image;
    Bitmap bitmap;
    int imageSize = 227;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_crop_care, null);

        //Permission
        getPermission();

        upload_picture = view.findViewById(R.id.btn_upload_picture);
        take_picture = view.findViewById(R.id.btn_take_picture);
        result = view.findViewById(R.id.result);
        image = view.findViewById(R.id.image);
        btnInfo = view.findViewById(R.id.btn_relatedInfo);

        upload_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 12);
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), RelatedInfoActivity.class));
            }
        });
        return view;
    }
    void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA}, 11);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==1){
            if(grantResults.length>0){
                if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    this.getPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void classifyImage(Bitmap bitmap){
        try {
            Model1 model = Model1.newInstance(getContext());
            // Load Image
            TensorImage ti=new TensorImage(DataType.FLOAT32);
            ti.load(bitmap);

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 227, 227, 3}, DataType.FLOAT32);

            inputFeature0.loadBuffer(ti.getBuffer());

            // Runs model inference and gets result.
            Model1.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();

            //finds the index of the class with Highest Confidence.
            int maxPos = 0;
            float maxConfidence =0;
            for(int i = 0; i< confidences.length; i++){

                if(confidences[i] > maxConfidence){
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            String[] classes = {"Black_Rot", "Downy_Mildew", "Esca", "Healthy", "Leaf_Blight", "Powdery_Mildew", "Oops! Wrong image detected."};
            result.setText(classes[maxPos]);


            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==10)
        {
            if(data!=null)
            {
                Uri uri = data.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                image.setImageBitmap(bitmap);
                bitmap = Bitmap.createScaledBitmap(bitmap,imageSize,imageSize,false);
                image.setImageBitmap(bitmap);
                classifyImage(bitmap);
            }
        }
        else if(requestCode==12){
            bitmap = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(bitmap.getWidth(),bitmap.getHeight());
            bitmap = ThumbnailUtils.extractThumbnail(bitmap,dimension,dimension);
            bitmap=bitmap.copy(Bitmap.Config.ARGB_8888,true);
            image.setImageBitmap(bitmap);

            bitmap = Bitmap.createScaledBitmap(bitmap,imageSize,imageSize,false);
            classifyImage(bitmap);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}