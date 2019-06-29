package com.example.hoangnguyen.americanstem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Space;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainMenu extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;

    private String treeID[] = {"0"}; //Not for use yet
    private String name[] = {"Test"};
    private String person[] = {"Dr. Le"};
    private String bdate[] = {"6/9/2017"};
    private String extrainfo[] = {"bla bla"};
    private int[] picID = {R.drawable.logo};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int h = dm.heightPixels;
        int w = dm.widthPixels;
        ImageView background = (ImageView)findViewById(R.id.background);
        ImageView showImg = (ImageView)findViewById(R.id.dialog_imageview);
        Space blank = (Space)findViewById(R.id.blank);
        Button but1 = (Button)findViewById(R.id.btn1);
        Button but2 = (Button)findViewById(R.id.btn2);
        Button but3 = (Button)findViewById(R.id.btn3);
        ImageButton but4 = (ImageButton)findViewById(R.id.btn4);
        background.getLayoutParams().height = (h/2);
        background.getLayoutParams().width = (w - (w/100)*5);
        background.setImageResource(R.drawable.logo);
        showImg.getLayoutParams().height = h/4*3;
        showImg.getLayoutParams().width = w/4*3;
        blank.getLayoutParams().height = (h/20);
        but1.getLayoutParams().height = (w/2 - w/4 + w/16);
        but1.getLayoutParams().width = (w/2 - w/4 + w/16);
        but2.getLayoutParams().height = (w/2 - w/4 + w/16);
        but2.getLayoutParams().width = (w/2 - w/4 + w/16);
        but3.getLayoutParams().height = (w/2 - w/4 + w/16);
        but3.getLayoutParams().width = (w/2 - w/4 + w/16);
        but4.getLayoutParams().width = (w/2 - w/4 + w/16);
        but4.getLayoutParams().height = (w/2 - w/4 + w/16);
        Animation fadeIn = new AlphaAnimation(0, 1.0f);
        fadeIn.setDuration(2222);
        background.setAnimation(fadeIn);
        but1.setAnimation(fadeIn);
        but2.setAnimation(fadeIn);
        but3.setAnimation(fadeIn);
        but4.setAnimation(fadeIn);
    }

    public void goScan (View v)
    {
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
        mScannerView.stopCameraPreview();

    }
    @Override
    public void handleResult(Result rawResault) {
        Log.e("handler", rawResault.getText());
        Log.e("handler", rawResault.getBarcodeFormat().toString());

        String dialogMessage;
        int codeID;
        String rawResult = rawResault.getText().toString();
        //String newLine = System.getProperty("line.separator");
        String getID[] = rawResult.split("\\s+", 2);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.sample, null);

        try
        {
            codeID = Integer.parseInt(getID[0]);
            dialogMessage = ("Name: " + name[codeID] + "\nPlanted by(Tree only): " + person[codeID] + "\nDOB: " + bdate[codeID] + "\nExtra: " + extrainfo[codeID]);
            ImageView showImg = (ImageView)findViewById(R.id.dialog_imageview);
            showImg.setImageResource(picID[codeID]);

        } catch (NumberFormatException e) {
            dialogMessage = ("Invalid AmericanSTEM QR Code");
        }

        //int QRres = Integer.parseInt(rawResault.getText());

        builder.setMessage(dialogMessage); //Result goes here
        builder.setPositiveButton("Okay",
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
        builder.setView(view);
        AlertDialog showRes = builder.create();
        showRes.show();
    }

    public void goAbout (View v)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://americanstem.vn"));
        startActivity(browserIntent);
        System.exit(0);
    }

    public void goQuit (View v)
    {
        ImageView bg = (ImageView)findViewById(R.id.background);
        Button but1 = (Button)findViewById(R.id.btn1);
        Button but2 = (Button)findViewById(R.id.btn2);
        Button but3 = (Button)findViewById(R.id.btn3);
        Animation fadeOut = new AlphaAnimation(1.0f, 0);
        fadeOut.setDuration(6666);
        bg.setAnimation(fadeOut);
        but1.setAnimation(fadeOut);
        but2.setAnimation(fadeOut);
        but3.setAnimation(fadeOut);
        try {
            System.exit(0);
            Thread.sleep(5000);
        } catch (InterruptedException ex) { }
    }

    public void goAme (View v)
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://americanstem.vn"));
        startActivity(browserIntent);
        System.exit(0);
    }
}
