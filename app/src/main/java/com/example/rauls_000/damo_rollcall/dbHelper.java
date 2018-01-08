package com.example.rauls_000.damo_rollcall;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class dbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dades.db";
    private static final int DATABASE_VERSION = 1;

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE alumne (" +
                "dni TEXT PRIMARY KEY, " +
                "nom TEXT NOT NULL, " +
                "percAssistencia INTEGER);"
        );
        sqLiteDatabase.execSQL("CREATE TABLE assignatura (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nom TEXT UNIQUE NOT NULL, " +
                "acronim TEXT UNIQUE NOT NULL);"
        );
        sqLiteDatabase.execSQL("CREATE TABLE grup (" +
                "id TEXT PRIMARY KEY);"
        );
        Log.d("TABLE CREATED", "GRUP");
        sqLiteDatabase.execSQL("CREATE TABLE sessio (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "data TEXT UNIQUE);"
        );
        sqLiteDatabase.execSQL("CREATE TABLE grup_alumne (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idGrup TEXT NOT NULL, " +
                "idAlumne TEXT NOT NULL, " +
                "UNIQUE (idGrup, idAlumne), " +
                "FOREIGN KEY (idGrup) REFERENCES grup (id)," +
                "FOREIGN KEY (idAlumne) REFERENCES alumne (dni));"
        );
        sqLiteDatabase.execSQL("CREATE TABLE grup_assignatura (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idGrup TEXT NOT NULL UNIQUE, " +
                "idAssignatura TEXT NOT NULL, " +
                "UNIQUE (idGrup, idAssignatura), " +
                "FOREIGN KEY (idGrup) REFERENCES grup (id), " +
                "FOREIGN KEY (idAssignatura) REFERENCES assignatura(id));"
        );
        sqLiteDatabase.execSQL("CREATE TABLE grup_sessio (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idGrup TEXT NOT NULL, " +
                "idSessio TEXT NOT NULL, " +
                "UNIQUE(idGrup, idSessio), " +
                "FOREIGN KEY (idGrup) REFERENCES grup (id), " +
                "FOREIGN KEY (idSessio) REFERENCES sessio(data));"
        );
        sqLiteDatabase.execSQL("CREATE TABLE assistencia (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idSessio TEXT NOT NULL, " +
                "idAlumne TEXT NOT NULL, " +
                "tipusAssistencia INTEGER NOT NULL, " +
                "UNIQUE(idSessio, idAlumne));"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS alumne");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS assignatura");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS grup");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS sessio");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS grup_alumne");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS grup_assignatura");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS grup_sessio");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS assistencia");
        onCreate(sqLiteDatabase);
    }
}
