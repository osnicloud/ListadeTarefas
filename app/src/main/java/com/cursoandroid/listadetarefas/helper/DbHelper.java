package com.cursoandroid.listadetarefas.helper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    /*
        Ao lançar nova versao este atributo deve ser alterado
        Quando trocar e publicar o app novamente e o usuario atualizar o app, entao o método
        onUpgrade será chamado
    */

    public static int VERSION = 1; //Se alterar este valor é executado o metodo onUpgrade
    public static String NOME_DB = "DB_TAREFAS";
    public static String TABELA_TAREFAS = "tarefas";


    //Selecionar o primeiro construtor da lista ao clicar na Lampada
    public DbHelper(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    /*
       Só é executado quando instala
       Este método só é chamado uma vez, serve para criar bd, quando usuario instalar o app
       Por exemplo ao criar tabelas
    */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS
               + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome TEXT NOT NULL ); ";

        /*
        Poderia ter outros comandos...
        (String sqlUsuarios = "CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " nome TEXT NOT NULL ); ";
        */

        try{
            db.execSQL(sql);
            //db.execSQL(sqlUsuarios);
            Log.i("INFO DB", "Sucesso ao criar a tabela");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage());
        }
    }

    /*
      Só é executado quando muda a versão...
      Quando o usuario ja tem o app instalado e esta apenas atualizando o app
      No metodo é possivel fazer varias alteracoes como remover tabela e fazer alteracoes na estrutura
      O metodo recupera a versao antiga e a nova versao nos parametros
    */
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS " + TABELA_TAREFAS + " ;";

        //String sql = "ALTER TABLE " + TABELA_TAREFAS + " ADD COLUMN status VARCHAR(2)";

        try{
            db.execSQL(sql);
            //db.execSQL(sqlUsuarios); Poderia ter outros comandos...


            /*
                Deleta tabela entao cria novamente usando o onCreate, lembrando que isto é apenas para exemplificar pois o ideal nao é
                deletar tabelas
            */
            onCreate(db);
            Log.i("INFO DB", "Sucesso ao atualizar App");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao atualizar App" + e.getMessage());
        }
    }
}
