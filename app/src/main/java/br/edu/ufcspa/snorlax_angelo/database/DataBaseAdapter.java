
package br.edu.ufcspa.snorlax_angelo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.edu.ufcspa.snorlax_angelo.model.RecordedFiles;
import br.edu.ufcspa.snorlax_angelo.model.Recording;
import br.edu.ufcspa.snorlax_angelo.model.User;


/**
 * Created by icaromsc on 17/01/2017.
 *
 * Classe responsavel por gerenciar banco de dados SQLITE do app
 *
 */

public class DataBaseAdapter {

    private static DataBaseAdapter mInstance = null;
    private SQLiteDatabase db;
    private DataBase helper;
    private String tag = "database";
    private Context context;
    private static String TB_RECORDINGS="recordings";
    private static String TB_USERS="users";
    private static String TB_RECORDED_FILES="recorded_files";

    private DataBaseAdapter(Context ctx) {
        helper = DataBase.getInstance(ctx);
        db = helper.getDatabase();
        context = ctx;
    }

    public static DataBaseAdapter getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new DataBaseAdapter(ctx);
        }
        return mInstance;
    }


    /**
     *
     * @return nome das tabelas no banco de dados
     */
    public String listarTabelas() {
        String query = "SELECT name from sqlite_master where type='table'";
        String r = "";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                r += c.getString(0) + "\n";
                //r += c.getString(1) + "\n";
            } while (c.moveToNext());
        } else {
            r = "não há tabelas";
        }

        //db.close();

        return r;
    }

    /**
     *
     * @return id do usuario no app
     */
    public Integer getUserId() {
        String query = "SELECT id_user from users";
        Integer id=0;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
                id = c.getInt(0);
        }
        return id;
    }


    /**
     * insere usuario do app no banco de dados
     * @param user
     */
    public void insertUser(User user) {
            ContentValues cv = new ContentValues();
            cv.put("id_user", user.getIdUser());
            cv.put("id_user_google", user.getId_user_google());
            cv.put("id_user_facebook", user.getId_user_facebook());
            cv.put("name", user.getName());
            cv.put("email", user.getEmail());
        try {
               db.insert("users", null, cv);

            }catch (SQLiteConstraintException v){
               // Log.e(tag, "errro, usuario já existe no banco");
                v.printStackTrace();
            }
            catch (Exception e) {
                Log.e(tag, "erro ao inserir tb_user:" + e.getMessage());
                e.printStackTrace();
            }

        }


    /**
     *
     * @return usuario do app no banco de dados
     */
    public User getUser() {
        String query = "SELECT * from users";
        User user = new User(0,null,null,null,null,null);

        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
                user= new User(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),null);
        } else {

        }
        return user;
    }


    /**
     * insere gravação no banco de dados
     * @param rec
     * @return id da ultima gravação
     */
    public int insertRecording(Recording rec) {
        ContentValues cv = new ContentValues();
        cv.put("date_start", String.valueOf(rec.getDateStart()));
        cv.put("status", Recording.STATUS_PROCESSING);
        try {
            db.insert(TB_RECORDINGS, null, cv);

        }catch (SQLiteConstraintException v){
            // Log.e(tag, "errro, usuario já existe no banco");
            v.printStackTrace();
        }
        catch (Exception e) {
            Log.e(tag, "erro ao inserir recording:" + e.getMessage());
            e.printStackTrace();
        }

        Cursor cursor = db.rawQuery("SELECT MAX(id_recording) FROM "+TB_RECORDINGS+";", null);
        int lastID = 0;
        if (cursor.moveToFirst()) {
            lastID = cursor.getInt(0);
            Log.d(tag, "last id_recording in table:" + lastID);
        }
        return lastID;
    }


    /**
     * insere arquivo de gravação no banco de dados
     * @param rec
     */
    public void insertRecordedFile(RecordedFiles rec) {
        ContentValues cv = new ContentValues();
        cv.put("id_recording", rec.getIdRecording());
        cv.put("filename", rec.getFilename());
        cv.put("sequence", rec.getSequence());
        cv.put("status_upload", RecordedFiles.STATUS_PENDING_UPLOAD);
        try {
            db.insert(TB_RECORDED_FILES, null, cv);

        }catch (SQLiteConstraintException v){
            // Log.e(tag, "errro, usuario já existe no banco");
            v.printStackTrace();
        }
        catch (Exception e) {
            Log.e(tag, "erro ao inserir recorded_files:" + e.getMessage());
            e.printStackTrace();
        }


    }

    /**
     * Atualiza status da gravação no banco de dados com data de termino
     * @param recording
     * @return bollean se conseguiu realizar update
     */
    public boolean updateFinalizeRecording(Recording recording) {
        ContentValues cv = new ContentValues();
        cv.put("date_stop", recording.getDateStop());
        cv.put("status", Recording.STATUS_UPLOADING);
        try {
            db.update(TB_RECORDINGS, cv, "id_recording=?", new String[]{"" + recording.getIdRecording()});
            return true;
        } catch (Exception e) {
            Log.d(tag,"error updating recording:"+ e.getMessage());
            return false;
        }
    }


    /**
     * método para atualizar gravação como finalizada quando todos os arquivos forem sincronizados com o servidor
     */
    public void updateStatusRecordingOnUploadFilesFinished() {
        String query = "UPDATE recordings set status = '"+Recording.STATUS_FINISHED+"'\n" +
                "where\n" +
                "(Select count (id_recorded_file) from recorded_files where recorded_files.id_recording = recordings.id_recording and recorded_files.status_upload = 'U') = 0\n" +
                "and recordings.status = 'U'";
        try{
            db.execSQL(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * atualiza gravação que foi sincronizada com webservice
     * @param idRecording
     * @return
     */
    public boolean updateSyncRecording(int idRecording) {
        ContentValues cv = new ContentValues();
        cv.put("status", Recording.STATUS_SYNCHRONIZED);
        try {
            db.update(TB_RECORDINGS, cv, "id_recording=?", new String[]{"" + idRecording});
            return true;
        } catch (Exception e) {
            Log.d(tag,"error updating recording:"+ e.getMessage());
            return false;
        }
    }


    /**
     * atualiza status do arquivo de gravação
     * @param rec
     */
    public void updateStatusRecordedFile(RecordedFiles rec) {
        ContentValues cv = new ContentValues();
        cv.put("status_upload", rec.getStatus_upload());
        try {
            db.update(TB_RECORDED_FILES, cv, "id_recorded_file=?", new String[]{"" + rec.getIdRecordedFile()});
        } catch (Exception e) {
            Log.d(tag,"error updating recorded_file:"+ e.getMessage());
        }
    }




    /**
     * Lista todas gravações
     * @return
     */
    public List<Recording> getRecordings() {
        Recording recording;
        ArrayList<Recording> lista = new ArrayList<>();
        Cursor c = null;
        String query = "SELECT * FROM ".concat(TB_RECORDINGS)+" ORDER BY id_recording DESC";
        c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                recording = new Recording(c.getInt(0),c.getString(1),c.getString(2),c.getString(3));
                //            user.setPhoto(c.getString(5), null);
                lista.add(recording);
            } while (c.moveToNext());
        }
        return lista;
    }





    /**
     * Busca gravações finalizadas com status F
     * @return
     */
    public List<Recording> getFinishedRecordings() {
        Recording recording;
        ArrayList<Recording> lista = new ArrayList<>();
        Cursor c = null;
        String query = "SELECT * FROM ".concat(TB_RECORDINGS)+" WHERE status='F';";
        c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                recording = new Recording(c.getInt(0),c.getString(1),c.getString(2),c.getString(3));
                //            user.setPhoto(c.getString(5), null);
                lista.add(recording);
            } while (c.moveToNext());
        }
        return lista;
    }


    /**
     * Busca arquivos de gravação
     * @return Lista de RecordedFiles
     */
    public List<RecordedFiles> getRecordedFiles() {
        RecordedFiles rec;
        ArrayList<RecordedFiles> lista = new ArrayList<>();
        Cursor c = null;
        String query = "SELECT * FROM ".concat(TB_RECORDED_FILES);
        c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                rec = new RecordedFiles(c.getInt(0),c.getInt(1),c.getInt(2),c.getString(3),c.getString(4));
                lista.add(rec);
            } while (c.moveToNext());
        }
        return lista;
    }


    /**
     * Busca nº arquivos de uma gravação
     * @param idRecording
     * @return número de arquivos de uma gravação
     */
    public int getCountRecordedFiles(int idRecording) {
        int count=0;
        ArrayList<RecordedFiles> lista = new ArrayList<>();
        Cursor c = null;
        String query = "SELECT count(*) FROM ".concat(TB_RECORDED_FILES)+" WHERE id_recording="+idRecording;
        c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            count=c.getInt(0);
        }
        return count;
    }


    /**
     * Busca no banco de dados os arquivos de gravação com status pendente
     * @return Lista de RecordedFiles
     */
    public List<RecordedFiles> getRecordedFilesToBeUploaded() {
        RecordedFiles rec;
        ArrayList<RecordedFiles> lista = new ArrayList<>();
        Cursor c = null;
        String query = "SELECT * FROM ".concat(TB_RECORDED_FILES).concat(" WHERE status_upload = '").concat(RecordedFiles.STATUS_PENDING_UPLOAD).concat("';");
        Log.d(tag,"getRecToUp query: "+query);
        c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                rec = new RecordedFiles(c.getInt(0),c.getInt(1),c.getInt(2),c.getString(3),c.getString(4));
                lista.add(rec);
            } while (c.moveToNext());
        }
        return lista;
    }


    }

