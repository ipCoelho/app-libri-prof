package com.cristianomoraes.libri_retorfit.remote;

import com.cristianomoraes.libri_retorfit.model.Livro;
import com.cristianomoraes.libri_retorfit.model.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;
import retrofit2.http.Path;

public interface RouterInterface {

    /** MAPEAMENTO DAS ROTAS DE USUÁRIOS **/
    @POST("/usuario/cadastrarUsuario")
    Call<Usuario> addUsuario(@Body Usuario usuario);

    /** MAPEAMENTO DAS ROTAS DE LIVROS **/
    @POST("/livro/cadastrarLivro")
    Call<Livro> addLivro(@Body Livro livro);

    @GET("/livro/listarLivro")
    Call<List<Livro>>getLivros();

    @DELETE("/livro/excluirLivro/{cod_livro}")
    Call<Livro> deleteLivro(@Path("cod_livro") int cod_livro);
}
