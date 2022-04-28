package com.cristianomoraes.libri_retorfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cristianomoraes.libri_retorfit.model.Item;
import com.cristianomoraes.libri_retorfit.model.Livro;
import com.cristianomoraes.libri_retorfit.remote.APIUtil;
import com.cristianomoraes.libri_retorfit.remote.RouterInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedLivro extends AppCompatActivity {
    RouterInterface routerInterface;
    List<Livro> list = new ArrayList<Livro>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_livro);

        routerInterface = APIUtil.getUsuarioInterface();
        recyclerView = findViewById(R.id.recyclerview);

        /** RECEBE OS DADOS DE LIVROS VINDOS DA APIREST **/
        Call<List<Livro>> call = routerInterface.getLivros();

        call.enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {
                List<Item> itens = new ArrayList<>();
                list = response.body();

                for(int i = 0; i < list.size(); i++){
                    itens.add(new Item(0, list.get(i)));
                }

                recyclerView.setAdapter(new LivroAdapter(itens));
            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {
                Log.d("API-ERRO", t.getMessage());
            }
        });


    }//FIM DO MÉTODO ONCREATE

    /** CLASSE DE ADAPTER DO RECYCLERVIEW **/
    private class LivroAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        List<Item> itens;

        public LivroAdapter(List<Item> itens) {
            this.itens = itens;
        }//FIM DO MÉTODO CONSTRUTOR DE LIVROADAPTER

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            /** INFLA A ESTRUTURA XML E OS DADOS REFERENTES A LIVRO **/
            if(viewType == 0){

                return new LivroAdapter.LivroViewHolder(
                        LayoutInflater.from(parent.getContext())
                        .inflate(
                                R.layout.item_container_livro,
                                parent,
                                false)
                );

            }

            return null;

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            /** RECUPERA OS DADOS DE LIVRO **/
            if(getItemViewType(position) == 0){

                Livro livro = (Livro) itens.get(position).getObject();
                ((LivroAdapter.LivroViewHolder) holder).setlivroData(livro);

            }

            /** RECUPERA OS DADOS DE HQ **/
            //if(getItemViewType(position) == 1){}

            /** RECUPERA OS DADOS DE MANGA **/
            //if(getItemViewType(position) == 2){}

        }

        @Override
        public int getItemCount() {
            return itens.size();
        }

        /** RECUPERA O TIPO DO OBJETO DE ITEM **/
        public int getItemViewCount(int position){
            return itens.get(position).getType();
        }

        /** CLASSE DE VIEWHOLDER DA RECYCLERVIEW **/
        class LivroViewHolder extends RecyclerView.ViewHolder{

            private TextView txtTitulo, txtDescricao;
            private int cod_livro;

            public LivroViewHolder(@NonNull View itemView) {
                super(itemView);

                txtTitulo = itemView.findViewById(R.id.txtCardTituloLivro);
                txtDescricao = itemView.findViewById(R.id.txtCardDescricaoLivro);

                /** TRATAMENTO DE CLIQUE PARA ALTERAÇÃO E EXCLUSÃO DE LIVROS **/
                itemView.setOnClickListener(view -> {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(FeedLivro.this)
                            .setMessage("O que deseja fazer?")
                            .setPositiveButton("Alterar", (dialog, width) -> {
                                Intent intent = new Intent(FeedLivro.this, EditarLivro.class);
                                intent.putExtra("cod_livro", cod_livro);
                                startActivity(intent);
                            })
                            .setNegativeButton("Excluir",(dialog, width) -> {
                                routerInterface = APIUtil.getUsuarioInterface();
                                Call<Livro> call = routerInterface.deleteLivro(cod_livro);

                                call.enqueue(new Callback<Livro>() {
                                    @Override
                                    public void onResponse(Call<Livro> call, Response<Livro> response) {
                                        Toast.makeText(
                                                FeedLivro.this,
                                                "Livro "+txtTitulo.getEditableText().toString()+" excluído.",
                                                Toast.LENGTH_SHORT
                                        ).show();

                                        startActivity(new Intent(FeedLivro.this, FeedLivro.class));
                                    }

                                    @Override
                                    public void onFailure(Call<Livro> call, Throwable t) {

                                    }
                                });
                            })
                    ;
                    alertDialog.show();
                });

            }//FIM DO MÉTODO CONSTRUTOR DE LIVROVIEWHOLDER

            public void setlivroData(Livro livro){

                txtTitulo.setText(livro.getTitulo());
                txtDescricao.setText(livro.getDescricao());
                cod_livro = livro.getCod_livro();

            }

        }//FIM DA CLASSE LIVROVIEWHOLDER

    }//FIM DA CLASSE LIVROADAPTER


}//FIM DA CLASSE FEEDLIVRO