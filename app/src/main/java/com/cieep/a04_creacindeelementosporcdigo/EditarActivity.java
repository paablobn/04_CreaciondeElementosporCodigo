package com.cieep.a04_creacindeelementosporcdigo;

import static com.cieep.a04_creacindeelementosporcdigo.configs.Constantes.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cieep.a04_creacindeelementosporcdigo.configs.Constantes;
import com.cieep.a04_creacindeelementosporcdigo.databinding.ActivityEditarBinding;
import com.cieep.a04_creacindeelementosporcdigo.modelos.Piso;

public class EditarActivity extends AppCompatActivity {

    private ActivityEditarBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Piso piso = (Piso) bundle.getSerializable(Constantes.INMUEBLE);

        binding.txtEditarCiudad.setText(piso.getCiudad());
        binding.txtEditarCP.setText(piso.getCp());
        binding.txtEditarProvincia.setText(piso.getProvincia());
        binding.txtEditarDireccion.setText(piso.getDireccion());
        binding.txtEditarNumero.setText(String.valueOf(piso.getNumero()));
        binding.rbEditarVal.setRating(piso.getValoracion());


        Log.d("INMU", piso.toString());

        binding.btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Piso piso;
                if ((piso = pisoOK()) != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(INMUEBLE, piso);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(EditarActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentElim = new Intent();
                Bundle bundleElim = new Bundle();
                bundleElim.putInt(BOTON,view.getId());
                bundleElim.putInt(POSICION,bundleElim.getInt(POSICION));
                int posicion = bundleElim.getInt(POSICION);
                bundleElim.putInt(POSICION,posicion);
                intentElim.putExtras(bundleElim);
                setResult(RESULT_OK,intentElim);
                finish();
            }
        });

        Log.d("INMU", piso.toString());
    }

    private Piso pisoOK() {
        if (binding.txtEditarDireccion.getText().toString().isEmpty()
                || binding.txtEditarNumero.getText().toString().isEmpty()
                || binding.txtEditarCP.getText().toString().isEmpty()
                || binding.txtEditarCiudad.getText().toString().isEmpty()
                || binding.txtEditarProvincia.getText().toString().isEmpty()
        )
            return null;
        return new Piso(
                binding.txtEditarDireccion.getText().toString(),
                Integer.parseInt(binding.txtEditarNumero.getText().toString()),
                binding.txtEditarCP.getText().toString(),
                binding.txtEditarCiudad.getText().toString(),
                binding.txtEditarProvincia.getText().toString(),
                binding.rbEditarVal.getRating()

        );
    }
}