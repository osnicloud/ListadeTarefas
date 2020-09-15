package com.cursoandroid.listadetarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cursoandroid.listadetarefas.R;
import com.cursoandroid.listadetarefas.helper.TarefaDAO;
import com.cursoandroid.listadetarefas.model.Tarefa;
import com.google.android.material.textfield.TextInputEditText;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.textTarefa);

        //Recuperar tarefa, caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //Configurar tarefa na caixa de texto
        if(tarefaAtual != null){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemSalvar:
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                //Executa açao para o item salvar
               if(tarefaAtual != null) { //edicao, verifica se objeto é diferente de nulo
                   String nomeTarefa = editTarefa.getText().toString();
                    if(!nomeTarefa.isEmpty()){
                        Tarefa tarefa = new Tarefa();
                        tarefa.setNomeTarefa(nomeTarefa);
                        tarefa.setId(tarefaAtual.getId());

                        //atualizar no banco de dados
                        if(tarefaDAO.atualizar(tarefa)){
                            finish();
                            Toast.makeText(this, "Sucesso ao atualizar tarefa!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(this, "Erro ao atualizar tarefa!", Toast.LENGTH_SHORT).show();
                        }
                    }
               }else{ //salvar
                   //Executa ação para o item salvar
                   String nomeTarefa = editTarefa.getText().toString();
                   if(!nomeTarefa.isEmpty()){ //Verifica se esta vazio
                       Tarefa tarefa = new Tarefa();
                       tarefa.setNomeTarefa(nomeTarefa);

                       if(tarefaDAO.salvar(tarefa)){ //tarefaDAO.salvar(tarefa) retorna true se nao deu erro
                           finish(); //Encerra activity
                           Toast.makeText(this, "Sucesso ao salvar tarefa!", Toast.LENGTH_SHORT).show();
                       }else {
                           Toast.makeText(this, "Erro ao salvar tarefa!", Toast.LENGTH_SHORT).show();
                       }


                   }
               }
            break;
        }
    return super.onOptionsItemSelected(item);
    }
}
