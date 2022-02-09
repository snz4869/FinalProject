package id.adiyusuf.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import id.adiyusuf.finalproject.databinding.ActivityMainBinding;

public class InstrukturDetailActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edit_id_ins,edit_nama_ins, edit_email, edit_hp_ins;
    String id_ins;
    Button btn_update_ins,btn_delete_ins;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruktur_detail);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Detail Data Pegawai");

        edit_id_ins = findViewById(R.id.edit_id_ins);
        edit_nama_ins = findViewById(R.id.edit_nama_ins);
        edit_email = findViewById(R.id.edit_email);
        edit_hp_ins = findViewById(R.id.edit_hp_ins);
        btn_update_ins = findViewById(R.id.btn_update_ins);
        btn_delete_ins = findViewById(R.id.btn_delete_ins);

        Intent receiveIntent = getIntent();
        id_ins = receiveIntent.getStringExtra(Konfigurasi.INS_ID);
        edit_id_ins.setText(id_ins);

        getJSON();

        btn_update_ins.setOnClickListener(this);
        btn_delete_ins.setOnClickListener(this);
    }

    private void getJSON() {
        // batuan dari class AsynTask
        class GetJSON extends AsyncTask<Void,Void,String> { // boleh membuat class dalam method (Inner Class)
            ProgressDialog loading;

            @Override
            protected void onPreExecute() { // sebelum proses
                super.onPreExecute();
                loading = ProgressDialog.show(InstrukturDetailActivity.this,
                        "Mengambil Data","Harap Menunggu...",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) { // saat proses
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_GET_DETAIL,id_ins);
                return result;
            }

            @Override
            protected void onPostExecute(String message) { // setelah proses
                super.onPostExecute(message);
                loading.dismiss();
                displayDetailData(message);
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayDetailData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String nama_ins = object.getString(Konfigurasi.TAG_JSON_NAMA);
            String email = object.getString(Konfigurasi.TAG_JSON_EMAIL);
            String hp_ins = object.getString(Konfigurasi.TAG_JSON_HP);

            edit_nama_ins.setText(nama_ins);
            edit_email.setText(email);
            edit_hp_ins.setText(hp_ins);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View myButton) {
        if(myButton == btn_update_ins){
            updateDataInstruktur();
        } else if(myButton == btn_delete_ins){
            confirmDeleteDataInstruktur();
        }
    }

    private void confirmDeleteDataInstruktur() {
        //Confirmation altert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Data");
        builder.setMessage("Are you sure want to delete this data?");
        builder.setIcon(getResources().getDrawable(android.R.drawable.ic_delete));
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteDataInstruktur();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataInstruktur() {
        class DeleteDataPegawai extends AsyncTask<Void,Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(InstrukturDetailActivity.this,
                        "Menghapus Data","Harap Tunggu",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(Konfigurasi.URL_DELETE, id_ins);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                Toast.makeText(InstrukturDetailActivity.this,"Pesan: " + s,
                        Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(InstrukturDetailActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "instruktur");
                startActivity(myIntent);
            }
        }
        DeleteDataPegawai deleteDataPegawai = new DeleteDataPegawai();
        deleteDataPegawai.execute();
    }

    private void updateDataInstruktur() {
        // variable data pegawai yang akan diubah
        final String nama_ins = edit_nama_ins.getText().toString().trim();
        final String email = edit_email.getText().toString().trim();
        final String hp_ins = edit_hp_ins.getText().toString().trim();

        class UpdateDataInstruktur extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(InstrukturDetailActivity.this,
                        "Mengubah Data","Harap Tunggu",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_INS_ID,id_ins);
                params.put(Konfigurasi.KEY_INS_NAMA,nama_ins);
                params.put(Konfigurasi.KEY_INS_EMAIL,email);
                params.put(Konfigurasi.KEY_INS_HP,hp_ins);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(Konfigurasi.URL_UPDATE, params);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();;

                Fragment fragment = null;

                Toast.makeText(InstrukturDetailActivity.this,
                        "Pesan: " + s, Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(InstrukturDetailActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "instruktur");
                startActivity(myIntent);

            }
        }

        UpdateDataInstruktur updateDataInstruktur = new UpdateDataInstruktur();
        updateDataInstruktur.execute();
    }

}