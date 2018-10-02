package scnd.wave.flashlight;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;




public class Home extends AppCompatActivity {


    public  Boolean Main;
    public  Boolean Blink;
    public  Boolean Sos;
    public  Boolean Torch;

    private Light lightrunner;
    private Thread lightthread;

    private Blinker blinkerrunner;
    private Thread blinkthread;

    private Soser sosrunner;
    private Thread sosthread;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);


        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA},69);
        }



        lightrunner = Light.getInstance();
        lightrunner.controller = this;

        sosrunner = Soser.getInstance();
        sosrunner.controller = this;

        blinkerrunner = Blinker.getInstance();
        blinkerrunner.controller = this;



        final ImageButton MainBTN = findViewById(R.id.main);

        final ImageButton SosBTN = findViewById(R.id.sos);
        final ImageButton BlinkBTN = findViewById(R.id.blink);
        final ImageButton TorchBTN = findViewById(R.id.torch);

        final TextView MainTXT = findViewById(R.id.MainTXT);
        final TextView OF = findViewById(R.id.ofn);

        Main=false;
        Blink=false;
        Sos=false;

        Torch=true;


        TorchON();

        MainBTN.setImageResource(R.drawable.ic_off);
        MainTXT.setTextColor(Color.parseColor("#f50057"));
        OF.setTextColor(Color.parseColor("#f50057"));
        OF.setText(R.string.TurnedOFF);
        MainTXT.setText(R.string.Torch);



        MainBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Torch) {
                    if (!Main){

                        lightthread = new Thread(lightrunner);
                        lightthread.start();

                        Main=true;
                        MainBTN.setImageResource(R.drawable.ic_on);
                        MainTXT.setTextColor(Color.parseColor("#00ff99"));
                        OF.setTextColor(Color.parseColor("#00ff99"));
                        OF.setText(R.string.TurnedON);

                        SosInactive();
                        BlinkInactive();

                    } else {

                        lightrunner.requestStop =true;
                        lightthread.interrupt();

                        Main=false;
                        MainBTN.setImageResource(R.drawable.ic_off);
                        MainTXT.setTextColor(Color.parseColor("#f50057"));
                        OF.setTextColor(Color.parseColor("#f50057"));
                        OF.setText(R.string.TurnedOFF);

                        SosOff();
                        BlinkOff();
                    }
                }

                if (Sos) {
                    if (!Main){

                        sosthread = new Thread(sosrunner);
                        sosthread.start();

                        Main=true;
                        MainBTN.setImageResource(R.drawable.ic_on);
                        MainTXT.setTextColor(Color.parseColor("#00ff99"));
                        OF.setTextColor(Color.parseColor("#00ff99"));
                        OF.setText(R.string.TurnedON);

                        TorchInactive();
                        BlinkInactive();


                    } else {
                        sosrunner.requestStop =true;
                        sosthread.interrupt();
                        Main=false;
                        MainBTN.setImageResource(R.drawable.ic_off);
                        MainTXT.setTextColor(Color.parseColor("#f50057"));
                        OF.setTextColor(Color.parseColor("#f50057"));
                        OF.setText(R.string.TurnedOFF);

                        TorchOff();
                        BlinkOff();
                    }
                }

                if (Blink) {
                    if (!Main){

                        blinkthread = new Thread(blinkerrunner);
                        blinkthread.start();
                        Main=true;
                        MainBTN.setImageResource(R.drawable.ic_on);
                        MainTXT.setTextColor(Color.parseColor("#00ff99"));
                        OF.setTextColor(Color.parseColor("#00ff99"));
                        OF.setText(R.string.TurnedON);

                        SosInactive();
                        TorchInactive();

                    } else {
                        blinkerrunner.requestStop=true;
                        blinkthread.interrupt();
                        Main=false;
                        MainBTN.setImageResource(R.drawable.ic_off);
                        MainTXT.setTextColor(Color.parseColor("#f50057"));
                        OF.setTextColor(Color.parseColor("#f50057"));
                        OF.setText(R.string.TurnedOFF);

                        SosOff();
                        TorchOff();
                    }
                }



            }
        });

        //end of main button

        TorchBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    TorchON();
                    BlinkOff();
                    SosOff();

                    Torch=true;
                    Sos=false;
                    Blink=false;
            }
        });


        BlinkBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    TorchOff();
                    BlinkON();
                    SosOff();

                Torch=false;
                Sos=false;
                Blink=true;
            }
        });

        //end of the blink button

        SosBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    TorchOff();
                    BlinkOff();
                    SosON();

                Torch=false;
                Sos=true;
                Blink=false;

            }
        });

        //end of sos button
    }

    //on set

    public void TorchON(){
        ImageButton TorchBTN = findViewById(R.id.torch);
        TextView TorchTXT = findViewById(R.id.torchTXT);
        TorchBTN.setImageResource(R.drawable.ic_on);
        TorchTXT.setTextColor(Color.parseColor("#00ff99"));
        TorchBTN.setClickable(false);

        ImageButton MainBTN = findViewById(R.id.main);
        TextView MainTXT = findViewById(R.id.MainTXT);
        final TextView OF = findViewById(R.id.ofn);
        OF.setText(R.string.TurnedOFF);
        MainBTN.setImageResource(R.drawable.ic_off);
        MainTXT.setText(R.string.Torch);
        MainTXT.setTextColor(Color.parseColor("#f50057"));
    }

    public void SosON(){
        Sos=true;
        ImageButton SosBTN = findViewById(R.id.sos);
        TextView SosTXT = findViewById(R.id.sosTXT);
        SosBTN.setImageResource(R.drawable.ic_on);
        SosTXT.setTextColor(Color.parseColor("#00ff99"));
        SosBTN.setClickable(false);

        ImageButton MainBTN = findViewById(R.id.main);
        TextView MainTXT = findViewById(R.id.MainTXT);
        final TextView OF = findViewById(R.id.ofn);
        OF.setText(R.string.TurnedOFF);
        MainBTN.setImageResource(R.drawable.ic_off);
        MainTXT.setText(R.string.SOS);
        MainTXT.setTextColor(Color.parseColor("#f50057"));
    }

    private void BlinkON(){
        Blink=true;
        ImageButton BlinkBTN = findViewById(R.id.blink);
        TextView BlinkTXT = findViewById(R.id.blinkTXT);
        BlinkBTN.setImageResource(R.drawable.ic_on);
        BlinkTXT.setTextColor(Color.parseColor("#00ff99"));
        BlinkBTN.setClickable(false);

        ImageButton MainBTN = findViewById(R.id.main);
        TextView MainTXT = findViewById(R.id.MainTXT);
        final TextView OF = findViewById(R.id.ofn);
        OF.setText(R.string.TurnedOFF);
        MainBTN.setImageResource(R.drawable.ic_off);
        MainTXT.setText(R.string.Blink);
        MainTXT.setTextColor(Color.parseColor("#f50057"));
    }



    //Inactive set

    private void TorchInactive(){
        ImageButton TorchBTN = findViewById(R.id.torch);
        TextView TorchTXT = findViewById(R.id.torchTXT);
        TorchBTN.setImageResource(R.drawable.ic_inactive);
        TorchTXT.setTextColor(Color.parseColor("#ffc107"));
        TorchBTN.setClickable(false);
    }

    private void SosInactive(){
        ImageButton SosBTN = findViewById(R.id.sos);
        TextView SosTXT = findViewById(R.id.sosTXT);
        SosBTN.setImageResource(R.drawable.ic_inactive);
        SosTXT.setTextColor(Color.parseColor("#ffc107"));
        SosBTN.setClickable(false);
    }

    private void BlinkInactive(){
        ImageButton BlinkBTN = findViewById(R.id.blink);
        TextView BlinkTXT = findViewById(R.id.blinkTXT);
        BlinkBTN.setImageResource(R.drawable.ic_inactive);
        BlinkTXT.setTextColor(Color.parseColor("#ffc107"));
        BlinkBTN.setClickable(false);
    }



    //off set

    private void TorchOff(){
        ImageButton TorchBTN = findViewById(R.id.torch);
        TextView TorchTXT = findViewById(R.id.torchTXT);
        TorchBTN.setImageResource(R.drawable.ic_off);
        TorchTXT.setTextColor(Color.parseColor("#f50057"));
        TorchBTN.setClickable(true);
    }

    private void SosOff(){
        ImageButton SosBTN = findViewById(R.id.sos);
        TextView SosTXT = findViewById(R.id.sosTXT);
        SosBTN.setImageResource(R.drawable.ic_off);
        SosTXT.setTextColor(Color.parseColor("#f50057"));
        SosBTN.setClickable(true);
    }

    private void BlinkOff(){
        ImageButton BlinkBTN = findViewById(R.id.blink);
        TextView BlinkTXT = findViewById(R.id.blinkTXT);
        BlinkBTN.setImageResource(R.drawable.ic_off);
        BlinkTXT.setTextColor(Color.parseColor("#f50057"));
        BlinkBTN.setClickable(true);
    }


}