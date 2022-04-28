package com.cristianomoraes.libri_retorfit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cristianomoraes.libri_retorfit.model.Livro;
import com.cristianomoraes.libri_retorfit.remote.APIUtil;
import com.cristianomoraes.libri_retorfit.remote.RouterInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroLivro extends AppCompatActivity {

    EditText txtTitulo;
    EditText txtDescricao;
    EditText txtFoto;
    Button btnInserirLivro;

    RouterInterface routerInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_livro);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtLivroDescricao);
        txtFoto = findViewById(R.id.txtFoto);
        btnInserirLivro = findViewById(R.id.btnCadastrarLivro);

        routerInterface = APIUtil.getUsuarioInterface();

        btnInserirLivro.setOnClickListener(view->{

            Livro livro = new Livro();
            livro.setTitulo(txtTitulo.getText().toString());
            livro.setDescricao(txtDescricao.getText().toString());
            livro.setImagem(txtFoto.getText().toString());
            livro.setTblUsuarioCodUsuario(1);

            addLivro(livro);

        });


    }//FIM DO MÉTODO onCreate

    public void addLivro(Livro livro){

        Call<Livro> call = routerInterface.addLivro(livro);

        call.enqueue(new Callback<Livro>() {
            @Override
            public void onResponse(Call<Livro> call, Response<Livro> response) {
                if(response.isSuccessful()){

                    Toast.makeText(CadastroLivro.this,
                            "LIVRO INSERIDO",
                            Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Livro> call, Throwable t) {
                Log.d("API-ERRO", t.getMessage());
            }
        });

    }

}//FIM DA CLASSE