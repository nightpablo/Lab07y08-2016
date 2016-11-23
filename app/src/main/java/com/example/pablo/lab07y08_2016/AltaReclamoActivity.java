package com.example.pablo.lab07y08_2016;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import com.example.pablo.lab07y08_2016.model.Reclamo;

import java.io.File;

public class AltaReclamoActivity extends AppCompatActivity {

    private Button btnCancelar;
    private Button btnAgregar;
    private EditText txtDescripcion;
    private EditText txtMail;
    private EditText txtTelefono;
    private LatLng ubicacion;
    private ImageButton imageButton_AgregarImagen;
    private ImageView imageView_AgregarImagen;
    private Uri miImageUri;
    private final Integer ACTIVITY_SELECT_IMAGE = 9182;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        ubicacion = (LatLng) extras.get("coordenadas");
        setContentView(R.layout.activity_alta_reclamo);
        btnAgregar = (Button) findViewById(R.id.btnReclamar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        txtDescripcion = (EditText) findViewById(R.id.reclamoTexto);
        txtTelefono= (EditText) findViewById(R.id.reclamoTelefono);
        txtMail= (EditText) findViewById(R.id.reclamoMail);
        imageButton_AgregarImagen = (ImageButton) findViewById(R.id.imageButton_AgregarImagen);
        imageView_AgregarImagen = (ImageView) findViewById(R.id.imageView_AgregarImagen);
        imageView_AgregarImagen.setImageResource(R.drawable.ic_action_name);

        imageButton_AgregarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, ACTIVITY_SELECT_IMAGE);
            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtDescripcion.getText().toString().isEmpty() ||
                        txtTelefono.getText().toString().isEmpty() ||
                        txtMail.getText().toString().isEmpty()){
                    Toast.makeText(AltaReclamoActivity.this, "¡No debe quedar ningún campo vacío!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Reclamo nuevoReclamo = new Reclamo(ubicacion.latitude,
                                                        ubicacion.longitude,
                                                        txtDescripcion.getText().toString(),
                                                        txtTelefono.getText().toString(),
                                                        txtMail.getText().toString());
                    if(imageView_AgregarImagen.getTag()!=null)
                        nuevoReclamo.setImagenPath(imageView_AgregarImagen.getTag().toString());
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result",nuevoReclamo);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED,new Intent());
                finish();

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_SELECT_IMAGE && resultCode == RESULT_OK) {
            miImageUri = data.getData();

            imageView_AgregarImagen.setImageURI(miImageUri);
            imageView_AgregarImagen.setTag(miImageUri.getPath());
        }
    }


}