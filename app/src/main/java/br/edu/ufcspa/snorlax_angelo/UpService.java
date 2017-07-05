package br.edu.ufcspa.snorlax_angelo;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import br.edu.ufcspa.snorlax_angelo.client.RecordingClient;
import br.edu.ufcspa.snorlax_angelo.database.DataBaseAdapter;
import br.edu.ufcspa.snorlax_angelo.model.RecordedFiles;
import br.edu.ufcspa.snorlax_angelo.model.Recording;
import br.edu.ufcspa.snorlax_angelo.model.SendRecording;
import br.edu.ufcspa.snorlax_angelo.view.UploadFilesAsync;

/**
 * Created by icaromsc on 01/02/2017.
 *
 *  Classe responsável por instanciar o serviço de Upload dos arquivos de áudio para o servidor angELO
 *
 * @author icaromsc
 *
 */

public class UpService extends Service {

    private static Timer timer = new Timer();
    private Context ctx;
    private static final String AUDIO_RECORDER_FOLDER = "Snore_angELO";
    MainTask myTask= new MainTask();;
    //
    private Integer counter=1000 * 60;
    private boolean threadActive=false;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    public void onCreate()
    {
        super.onCreate();
        Log.d("snorlax_service","on create service...");
        ctx = this;
        if(!threadActive)
            startService();
    }


    /**
     * método que agenda uma instância da classe TimerTask
     para ser executada de acordo com o valor do counter em milissegundos
     */



    private void startService()
    {
        Log.d("snorlax_service","start service...");
        timer.scheduleAtFixedRate(myTask, 0, counter);
    }

    protected class MainTask extends TimerTask
    {
        public void run()
        {
            Log.d("snorlax_service","running Timer task...");
            if(isOnline()){
                processFilesToBeUploaded();
                DataBaseAdapter data = DataBaseAdapter.getInstance(ctx);
                data.updateStatusRecordingOnUploadFilesFinished();
                syncRecordings(data);
            }
            else {
                Log.d("snorlax_service", "device offline...");
            }
        }
    }

    public void onDestroy()
    {
        Log.d("snorlax_service","destroying service...");
        myTask=null;
        super.onDestroy();
    }

    @Override
    public boolean stopService(Intent name) {
        Log.d("snorlax_service","finishing service...");
        return super.stopService(name);
    }




    private ArrayList<RecordedFiles> getFiles(){
        DataBaseAdapter data = DataBaseAdapter.getInstance(ctx);
        return (ArrayList<RecordedFiles>) data.getRecordedFilesToBeUploaded();
    }


    /**
     * método responsável por iniciar a async task de upload
     */

    private void processFilesToBeUploaded(){
        ArrayList<RecordedFiles> recordedFiles= new ArrayList<RecordedFiles>();
        recordedFiles=getFiles();
        if(recordedFiles.size()>0){
            Log.d("snorlax_service","files to be uploaded:"+recordedFiles.size());
            RecordedFiles[] files = new RecordedFiles[recordedFiles.size()];
            recordedFiles.toArray(files);
            new UploadFilesAsync().execute(files);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void syncRecordings(DataBaseAdapter data){
        Log.d("snorlax_service","start sync recording ");
        ArrayList<Recording> list = (ArrayList<Recording>) data.getFinishedRecordings();
        if(list.size()>0){
            for (Recording r: list) {
                SendRecording send = new SendRecording(r.getIdRecording(),data.getUserId(),formatDate(r.getDateStart()),formatDate(r.getDateStop()),data.getCountRecordedFiles(r.getIdRecording()));
                RecordingClient client = new RecordingClient(getBaseContext(),send);
                Log.d("snorlax_service","start json request ");
                client.send();
            }
        }else{
            Log.d("snorlax_service","no recording to sync ");
        }
    }



    private long formatDate(String date){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        Date myDate1 = null;
        try {
            myDate1 = (Date)formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long r = myDate1.getTime();
        return r;
    }


    @Override
    public void onLowMemory() {
        Log.d("snorlax_service","pouca memoria no smartphone");
        super.onLowMemory();
    }
}

