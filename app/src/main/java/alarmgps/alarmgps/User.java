package alarmgps.alarmgps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class User extends AppCompatActivity {

    String NoGPS;
    TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        id = (TextView) findViewById(R.id.id);

        String method = "getgps";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method, getIntent().getStringExtra("id"));
        try {
            NoGPS = backgroundTask.get();
            id.setText(NoGPS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void EnviarSMS(String gps, String mensaje) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(gps, null, mensaje, null, null);
    }

    public void ubicarvehiculo(View view) {
        try {
            EnviarSMS(NoGPS, "fix030s001n123456");
            Toast.makeText(getApplicationContext(), "Mensaje Enviado", Toast.LENGTH_LONG).show();
        } catch (RuntimeException e) {

        }
    }

    public void limpiarregistro(View view) {

        try {
            EnviarSMS(NoGPS, "clear123456");
            Toast.makeText(getApplicationContext(), "Mensaje Enviado", Toast.LENGTH_LONG).show();
        } catch (RuntimeException e) {

        }
    }

    public void apagarmotor(View view) {

        try {
            EnviarSMS(NoGPS, "stop123456");
            Toast.makeText(getApplicationContext(), "Mensaje Enviado", Toast.LENGTH_LONG).show();
        } catch (RuntimeException e) {

        }
    }

    public void encendermotor(View view) {

        try {
            EnviarSMS(NoGPS, "resume123456");
            Toast.makeText(getApplicationContext(), "Mensaje Enviado", Toast.LENGTH_LONG).show();
        } catch (RuntimeException e) {

        }
    }

    public void activaralarma(View view) {

        try {
            EnviarSMS(NoGPS, "arm123456");
            Toast.makeText(getApplicationContext(), "Mensaje Enviado", Toast.LENGTH_LONG).show();
        } catch (RuntimeException e) {

        }
    }

    public void desactivaralarma(View view) {

        try {
            EnviarSMS(NoGPS, "disarm123456");
            Toast.makeText(getApplicationContext(), "Mensaje Enviado", Toast.LENGTH_LONG).show();
        } catch (RuntimeException e) {

        }
    }

    public void micon(View view) {

        try {
            EnviarSMS(NoGPS,"monitor123456");
            Toast.makeText(getApplicationContext(), "Mensaje Enviado", Toast.LENGTH_LONG).show();
            Intent intentLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + NoGPS));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intentLlamada);
        }catch (RuntimeException e){

        }
    }

    public void micoff(View view) {
        try {
            EnviarSMS(NoGPS,"tracker123456");
            Toast.makeText(getApplicationContext(),"Mensaje Enviado",Toast.LENGTH_LONG).show();
        }catch (RuntimeException e){

        }
    }

}
