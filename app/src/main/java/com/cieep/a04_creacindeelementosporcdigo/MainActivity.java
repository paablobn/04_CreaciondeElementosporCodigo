package com.cieep.a04_creacindeelementosporcdigo;

import android.content.Intent;
import android.os.Bundle;

import com.cieep.a04_creacindeelementosporcdigo.configs.Constantes;
import com.cieep.a04_creacindeelementosporcdigo.modelos.Piso;
import com.google.android.material.snackbar.Snackbar;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;


import com.cieep.a04_creacindeelementosporcdigo.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // 1. Definir la variable del Binding
    private ActivityMainBinding binding;

    private ActivityResultLauncher<Intent> launcherCreatePiso;
    private ActivityResultLauncher<Intent> launcherEditarPiso;

    private int num;

    // Elementos Necesarios para Mostrar Datos en un contenedor
    // Contenedor de los datos (binding.content.contenidoMain)
    // Los datos
    private ArrayList<Piso> pisosList;
    // Lógica para montar los elementos en contenedor
    // Una plantilla para mostrar los elementos


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 2. Instanciar el Binding al layout
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        // 3. Asignar el Binding como Contenido de la Actividad
        setContentView(binding.getRoot());

        pisosList = new ArrayList<>();

        inicializaLaunchers();
        pintarElementos();


        setSupportActionBar(binding.toolbar);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launcherCreatePiso.launch(new Intent(MainActivity.this, AddPisoActivity.class));
            }
        });
    }


    private void inicializaLaunchers() {
        launcherCreatePiso = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            if (result.getData() != null) {
                                if (result.getData().getExtras() != null) {
                                    if (result.getData().getExtras().getSerializable(Constantes.INMUEBLE) != null) {
                                        Piso piso = (Piso) result.getData().getExtras().getSerializable(Constantes.INMUEBLE);
                                        pisosList.add(piso);
                                        pintarElementos();
                                    } else {
                                        Toast.makeText(MainActivity.this, "El bundle no lleva el tag INMUEBLE", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(MainActivity.this, "NO HAY BUNDLE EN EL INTENT", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "NO HAY BUNDLE EN EL RESULT", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Ventana cancelada", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        launcherEditarPiso = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            if (result.getData() != null) {
                                if (result.getData().getExtras() != null) {
                                    Piso piso = (Piso) result.getData().getExtras().getSerializable(Constantes.INMUEBLE);
                                    int posicion = result.getData().getExtras().getInt(Constantes.POSICION);
                                    int idBoton = result.getData().getExtras().getInt(Constantes.BOTON);
                                    switch (idBoton) {
                                        case R.id.btEditar:
                                            pisosList.set(posicion, piso);
                                            break;
                                        case R.id.btEliminar:
                                            pisosList.remove(posicion);
                                    }
                                    pintarElementos();
                                } else {
                                    Toast.makeText(MainActivity.this, "NO HAY BUNDLE EN EL INTENT", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "NO HAY BUNDLE EN EL RESULT", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Ventana cancelada", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

    }

    /**
     * Encargada de recorrer la lista de Alumnos y por cada uno insertarlo en el contenedor
     */
    private void pintarElementos() {
        binding.content.contenerdorMain.removeAllViews();
        for (int i = 0; i < pisosList.size(); i++) {
            Piso piso = pisosList.get(i);
            View pisoView = LayoutInflater.from(MainActivity.this).inflate(R.layout.piso_layout, null);
            TextView lbCalle = pisoView.findViewById(R.id.lbCallePiso);
            TextView lbNumero = pisoView.findViewById(R.id.lbNumeroPiso);
            TextView lbCiudad = pisoView.findViewById(R.id.lbCiudad);
            TextView lbProvincia = pisoView.findViewById(R.id.lbProvinciaPiso);
            RatingBar rbvaloracion = pisoView.findViewById(R.id.rbValPiso);

            lbCalle.setText(piso.getDireccion());
            lbNumero.setText(String.valueOf(piso.getNumero()));
            lbCiudad.setText(piso.getCiudad());
            lbProvincia.setText(piso.getProvincia());
            rbvaloracion.setRating(piso.getValoracion());

            binding.content.contenerdorMain.addView(pisoView);

            int finalI = i;
            pisoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    num = finalI;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constantes.INMUEBLE, piso);
                    Intent intent = new Intent(MainActivity.this, EditarActivity.class);
                    intent.putExtras(bundle);
                    launcherEditarPiso.launch(intent);
                }
            });
        }
    }
}