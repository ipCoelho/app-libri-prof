package com.cristianomoraes.libri_retorfit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cristianomoraes.libri_retorfit.model.Usuario;
import com.cristianomoraes.libri_retorfit.remote.APIUtil;
import com.cristianomoraes.libri_retorfit.remote.RouterInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroUsuario extends AppCompatActivity {

    EditText txtNome;
    EditText txtSobrenome;
    EditText txtEmail;
    EditText txtLogin;
    EditText txtSenha;
    Button btnInserir;

    RouterInterface routerInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        txtNome = findViewById(R.id.txtNome);
        txtSobrenome = findViewById(R.id.txtSobrenome);
        txtEmail = findViewById(R.id.txtEmail);
        txtLogin = findViewById(R.id.txtLogin);
        txtSenha = findViewById(R.id.txtSenha);
        btnInserir = findViewById(R.id.btnCadastrarUsuario);

        routerInterface = APIUtil.getUsuarioInterface();

        btnInserir.setOnClickListener(view -> {

            Usuario usuario = new Usuario();

            usuario.setNome(txtNome.getText().toString());
            usuario.setSobrenome(txtSobrenome.getText().toString());
            usuario.setEmail(txtEmail.getText().toString());
            usuario.setLogin(txtLogin.getText().toString());
            usuario.setSenha(txtSenha.getText().toString());
            usuario.setFoto("TESTE DE IMAGEM");

            addUsuario(usuario);

        });

    }//FIM DO MÉTODO onCreate

    public void addUsuario(Usuario usuario){

        Call<Usuario> call = routerInterface.addUsuario(usuario);

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){

                    Toast.makeText(CadastroUsuario.this,
                                    "USUÁRIO INSERIDO",
                                    Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

                Log.d("API-ERRO", t.getMessage());

            }
        });

    }

}//FIM DA CLASSE