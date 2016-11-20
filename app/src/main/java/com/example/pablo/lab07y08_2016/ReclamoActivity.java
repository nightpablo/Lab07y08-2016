package com.example.pablo.lab07y08_2016;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.pablo.lab07y08_2016.model.Reclamo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ReclamoActivity extends AppCompatActivity implements com.google.android.gms.maps.OnMapReadyCallback{

    final private Integer CODIGO_RESULTADO_ALTA_RECLAMO = 9999;
    private GoogleMap myMap;
    private ArrayList<Reclamo> listaReclamos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamo);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        listaReclamos = new ArrayList<Reclamo>();

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap; // Asigno al mapa una vez lista
        myMap.getUiSettings().setZoomControlsEnabled(true); // Controles de Zoom [ + / - ]
        myMap.getUiSettings().setMapToolbarEnabled(true); // Barra de herramientas
        myMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() { // Creo un click para empezar a realizar un reclamo
            @Override
            public void onMapClick(LatLng latLng) {
                Intent i = new Intent(ReclamoActivity.this,AltaReclamoActivity.class);
                i.putExtra("coordenadas",latLng);
                startActivityForResult(i,CODIGO_RESULTADO_ALTA_RECLAMO);
            }
        });
        localizarMiPosicion();
    }

    private void localizarMiPosicion(){
        if (ContextCompat.checkSelfPermission(ReclamoActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},9999);
            return;
        }
        myMap.setMyLocationEnabled(true);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==CODIGO_RESULTADO_ALTA_RECLAMO && resultCode==RESULT_OK){
            Reclamo nuevoReclamo = (Reclamo) data.getExtras().get("result");
            myMap.addMarker(new MarkerOptions()
            .position(nuevoReclamo.coordenadaUbicacion())
            .title("Reclamo de "+nuevoReclamo.getEmail())
            .snippet(nuevoReclamo.getTitulo()));
            listaReclamos.add(nuevoReclamo);
        }
    }
}
