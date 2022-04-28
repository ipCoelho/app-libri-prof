package com.cristianomoraes.libri_retorfit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class EditarLivro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_livro);

        int cod_livro = getIntent().getExtras().getInt("cod_livro");
        Log.d("TESTE", String.valueOf(cod_livro));
    }
}