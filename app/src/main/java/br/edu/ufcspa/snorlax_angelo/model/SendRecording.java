package br.edu.ufcspa.snorlax_angelo.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by icaromsc on 17/03/2017.
 */

public class SendRecording {
    private Integer idRecordingApp;
    private Integer idUser;
    private long dateStart;
    private long dateStop;
    private Integer nFiles;


    public SendRecording(Integer idRecordingApp, Integer idUser, long dateStart, long dateStop, Integer nFiles) {
        this.idRecordingApp = idRecordingApp;
        this.idUser = idUser;
        this.dateStart = dateStart;
        this.dateStop = dateStop;
        this.nFiles = nFiles;
    }

    public Integer getIdRecordingApp() {
        return idRecordingApp;
    }

    public void setIdRecordingApp(Integer idRecordingApp) {
        this.idRecordingApp = idRecordingApp;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public long getDateStart() {
        return dateStart;
    }

    public void setDateStart(long dateStart) {
        this.dateStart = dateStart;
    }

    public long getDateStop() {
        return dateStop;
    }

    public void setDateStop(long dateStop) {
        this.dateStop = dateStop;
    }

    public Integer getnFiles() {
        return nFiles;
    }

    public void setnFiles(Integer nFiles) {
        this.nFiles = nFiles;
    }

    public JSONObject toJson(){
        JSONObject j = new JSONObject();
        try {
            j.put("id_recording_app",getIdRecordingApp());
            j.put("id_user",getIdUser());
            j.put("date_start",getDateStart());
            j.put("date_stop",getDateStop());
            j.put("n_files",getnFiles());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return j;
    }


    @Override
    public String toString() {
        return "SendRecording{" +
                "idRecordingApp=" + idRecordingApp +
                ", idUser=" + idUser +
                ", dateStart=" + dateStart +
                ", dateStop=" + dateStop +
                ", nFiles=" + nFiles +
                '}';
    }
}
