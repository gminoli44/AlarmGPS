package alarmgps.alarmgps;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {

    EditText ET_GPS,ET_PASS;
    String gps,pass;
    private static final int SOLICITUD_PERMISO_CALL_PHONE = 2;
    private static final int SOLICITUD_PERMISO_SEND_TEXT = 1;
    SharedPreferences misDatos;
    SharedPreferences.Editor miEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ET_GPS = (EditText)findViewById(R.id.numerogps);
        ET_PASS=(EditText)findViewById(R.id.pass);
        misDatos = getSharedPreferences("fichero",0);

        /**
         * ¿Tengo el permiso para hacer la accion?
         */                                                                             ///PERMISO CONCENDIDO
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
           // startActivity(intentLLamada);
           // Toast.makeText(this, "Permiso Concedido", Toast.LENGTH_SHORT).show();
        } else {
            explicarUsoPermiso();
            solicitarPermisoHacerLlamada();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            // startActivity(intentLLamada);
            // Toast.makeText(this, "Permiso Concedido", Toast.LENGTH_SHORT).show();
        } else {
            explicarUsoPermisotxt();
            //solicitarPermisoEnviarTxt();
        }

        if(readEstado().equals("OK")){
            Intent intent = new Intent(getApplicationContext(),User.class);
            intent.putExtra("id", readId());
            startActivity(intent);
        }
    }

    private void explicarUsoPermiso() {
        //Este IF es necesario para saber si el usuario ha marcado o no la casilla [] No volver a preguntar
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
          //  Toast.makeText(this, "Explicamos razonadamente porque necesitamos el permiso", Toast.LENGTH_SHORT).show();
            //Explicarle al usuario porque necesitas el permiso (Opcional)
            alertDialogLlamada();
        }
    }

    private void explicarUsoPermisotxt() {
        //Este IF es necesario para saber si el usuario ha marcado o no la casilla [] No volver a preguntar
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CALL_PHONE)) {
          //  Toast.makeText(this, "Explicamos razonadamente porque necesitamos el permiso", Toast.LENGTH_SHORT).show();
            //Explicarle al usuario porque necesitas el permiso (Opcional)
            alertDialogMensaje();
        }
    }

    private void solicitarPermisoHacerLlamada() {
        //Pedimos el permiso o los permisos con un cuadro de dialogo del sistema
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.SEND_SMS},SOLICITUD_PERMISO_CALL_PHONE);
     //   Toast.makeText(this, "Pedimos el permiso con un cuadro de dialogo del sistema", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        /**
         * Si tubieramos diferentes permisos solicitando permisos de la aplicacion, aqui habria varios IF
         */
        if (requestCode == SOLICITUD_PERMISO_CALL_PHONE) {

            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Realizamos la accion
              //  startActivity(intentLLamada);
            //    Toast.makeText(this, "Permiso Concedido", Toast.LENGTH_SHORT).show();
            } else {
                //1-Seguimos el proceso de ejecucion sin esta accion: Esto lo recomienda Google
                //2-Cancelamos el proceso actual
                //3-Salimos de la aplicacion
            //    Toast.makeText(this, "Permiso No Concedido", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == SOLICITUD_PERMISO_SEND_TEXT) {

            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Realizamos la accion
                //  startActivity(intentLLamada);
           //     Toast.makeText(this, "Permiso Concedido", Toast.LENGTH_SHORT).show();
            } else {
                //1-Seguimos el proceso de ejecucion sin esta accion: Esto lo recomienda Google
                //2-Cancelamos el proceso actual
                //3-Salimos de la aplicacion
           //     Toast.makeText(this, "Permiso No Concedido", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void alertDialogLlamada() {
        // 1. Instancia de AlertDialog.Builder con este constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 2. Encadenar varios métodos setter para ajustar las características del diálogo
        builder.setMessage("Permiso para realizar llamadas concedido");
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.show();
    }

    public void alertDialogMensaje() {
        // 1. Instancia de AlertDialog.Builder con este constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 2. Encadenar varios métodos setter para ajustar las características del diálogo
        builder.setMessage("Permiso para enviar mensajes concedido");
        builder.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.show();
    }
    //Escribe los datos

    public void setMiEditor(String id) {
        this.miEditor = this.misDatos.edit();
        this.miEditor.putString("estado","OK");
        this.miEditor.putString("id",id);
        this.miEditor.commit();
       // Toast.makeText(this, "creado", Toast.LENGTH_SHORT).show();
    }

    public String readEstado(){
        String estado = this.misDatos.getString("estado","NoLog");
       // Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
        return estado;
    }

    public String readId(){
        String id = this.misDatos.getString("id","0");
        // Toast.makeText(this, estado, Toast.LENGTH_SHORT).show();
        return id;
    }




    public void userLogin(View view) throws ExecutionException, InterruptedException {
        gps = ET_GPS.getText().toString();
        pass = ET_PASS.getText().toString();
        String method = "login";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method,gps,pass);
        String e = backgroundTask.get();
        if(e.equals("Datos Incorrectos")){
        }
        else{
            setMiEditor(e);
            Intent intent = new Intent(getApplicationContext(),User.class);
            intent.putExtra("id", e);
            startActivity(intent);
        }

    }
}
