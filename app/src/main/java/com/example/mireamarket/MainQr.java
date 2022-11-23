package com.example.mireamarket;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainQr extends AppCompatActivity {
    ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_qr);
        im=findViewById(R.id.imageView4);
        generateQrCode(user.getUid());
    }
    private void generateQrCode(String text){
        QRGEncoder qrGenerator=new QRGEncoder(text,null, QRGContents.Type.TEXT,500);
        try{
            Bitmap bMap=qrGenerator.encodeAsBitmap();
            im.setImageBitmap(bMap);
        }
        catch (WriterException e){

        }
    }
}