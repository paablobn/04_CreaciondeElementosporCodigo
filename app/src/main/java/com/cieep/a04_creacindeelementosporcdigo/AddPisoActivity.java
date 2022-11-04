package com.cieep.a04_creacindeelementosporcdigo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cieep.a04_creacindeelementosporcdigo.configs.Constantes;
import com.cieep.a04_creacindeelementosporcdigo.databinding.ActivityAddPisoBinding;
import com.cieep.a04_creacindeelementosporcdigo.modelos.Piso;

import java.text.ParseException;

public class AddPisoActivity extends AppCompatActivity {

    private ActivityAddPisoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // OJO
        // 1. inflate -> Instancia los Objetos de la vista
        // 2. LayoutInflater -> Encargado del trabajo del inflate
        binding = ActivityAddPisoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCancelarAddPiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        binding.btnCrearAddPiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Piso piso;
                if ((piso = pisoOK()) != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constantes.INMUEBLE, piso);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(AddPisoActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Piso pisoOK() {

        if (binding.txtDIreccionAddPiso.getText().toString().isEmpty()
                || binding.txtNumeroAddPiso.getText().toString().isEmpty()
                || binding.txtCPAddPiso.getText().toString().isEmpty()
                || binding.txtCiudadAddPiso.getText().toString().isEmpty()
                || binding.txtProvinciaAddPiso.getText().toString().isEmpty()
        )
            return null;
        return new Piso(
                binding.txtDIreccionAddPiso.getText().toString(),
                Integer.parseInt(binding.txtNumeroAddPiso.getText().toString()),
                binding.txtCPAddPiso.getText().toString(),
                binding.txtCiudadAddPiso.getText().toString(),
                binding.txtProvinciaAddPiso.getText().toString(),
                binding.rbValoracionAddPiso.getRating()

        );
    }
}