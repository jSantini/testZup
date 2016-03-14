package br.com.jsantini.testzup.model.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import br.com.jsantini.testzup.model.domain.Movie;

/**
 * Created by jsantini on 13/03/16.
 * Singleton para gerenciar toda a comunicação com o banco de dados.
 * Seria indicado utilizar um ORM como Sugar para este app, simplificando
 * e agilizando o desenvolvimento. Porém, optei por este modelo para
 * demonstar implementações como Singleton, controle de transação e gerenciamento básico
 * no android.
 */
public class TestZupDataBaseHelper extends SQLiteOpenHelper {


    //Nome do banco de dados
    private static final String DB_NAME = "testZup";
    //Extensão
    private static final String DB_SUFFIX = ".db";
    private static final int DB_VERSION = 1;
    private static TestZupDataBaseHelper mInstance;
    private static SQLiteDatabase db;

    /**
     * Construtor privado característico do padrão Singleton
     * Garante que uma única instancia seja criada
     * @param context
     */
    private TestZupDataBaseHelper(Context context) {
        //Previnindo criação externa da instância por reflection
        super(context, DB_NAME + DB_SUFFIX, null, DB_VERSION);
    }

    /**
     * Construtor privado para garantir que uma única instância seja usada
     * @param context
     * @return
     */
    private static TestZupDataBaseHelper getInstance(Context context) {
        if (null == mInstance) {
            mInstance = new TestZupDataBaseHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(MovieTable.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Retorna uma instância do banco alocando a tabela
     * @param context
     * @return
     */
    public static SQLiteDatabase getWritableDatabase(Context context) {
        return getInstance(context).getWritableDatabase();
    }

    /**
     * Retorna uma instancia apenas para leitura sem alocar a tabela
     * @param context
     * @return
     */
    public static SQLiteDatabase getReadableDatabase(Context context) {
        return getInstance(context).getReadableDatabase();
    }
}
