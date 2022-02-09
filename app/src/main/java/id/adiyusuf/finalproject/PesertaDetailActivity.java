package id.adiyusuf.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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

public class PesertaDetailActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edit_id_pst,edit_nama_pst, edit_email_pst, edit_hp_pst,edit_instansi;
    String id_pst;
    Button btn_update_pst,btn_delete_pst;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peserta_detail);

        edit_id_pst = findViewById(R.id.edit_id_pst);
        edit_nama_pst = findViewById(R.id.edit_nama_pst);
        edit_email_pst = findViewById(R.id.edit_email_pst);
        edit_hp_pst = findViewById(R.id.edit_hp_pst);
        edit_instansi = findViewById(R.id.edit_instansi);
        btn_update_pst = findViewById(R.id.btn_update_pst);
        btn_delete_pst = findViewById(R.id.btn_delete_pst);

        Intent receiveIntent = getIntent();
        id_pst = receiveIntent.getStringExtra(KonfigurasiPeserta.PST_ID);
        edit_id_pst.setText(id_pst);

        getJSON();

        btn_update_pst.setOnClickListener(this);
        btn_delete_pst.setOnClickListener(this);
    }

    private void getJSON() {
        // batuan dari class AsynTask
        class GetJSON extends AsyncTask<Void,Void,String> { // boleh membuat class dalam method (Inner Class)
            ProgressDialog loading;

            @Override
            protected void onPreExecute() { // sebelum proses
                super.onPreExecute();
                loading = ProgressDialog.show(PesertaDetailActivity.this,
                        "Mengambil Data","Harap Menunggu...",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) { // saat proses
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiPeserta.URL_GET_DETAIL,id_pst);
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

    private void displayDetailData(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiPeserta.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String nama_pst = object.getString(KonfigurasiPeserta.TAG_JSON_NAMA);
            String email_pst = object.getString(KonfigurasiPeserta.TAG_JSON_EMAIL);
            String hp_pst = object.getString(KonfigurasiPeserta.TAG_JSON_HP);
            String instansi = object.getString(KonfigurasiPeserta.TAG_JSON_INSTANSI);

            edit_nama_pst.setText(nama_pst);
            edit_email_pst.setText(email_pst);
            edit_hp_pst.setText(hp_pst);
            edit_instansi.setText(instansi);
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
        if(myButton == btn_update_pst){
            updateDataPeserta();
        } else if(myButton == btn_delete_pst){
            confirmDeleteDataPeserta();
        }
    }

    private void confirmDeleteDataPeserta() {
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
                deleteDataPeserta();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataPeserta() {
        class DeleteDataPeserta extends AsyncTask<Void,Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(PesertaDetailActivity.this,
                        "Menghapus Data","Harap Tunggu",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiPeserta.URL_DELETE, id_pst);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                Toast.makeText(PesertaDetailActivity.this,"Pesan: " + s,
                        Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(PesertaDetailActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "peserta");
                startActivity(myIntent);
            }
        }
        DeleteDataPeserta deleteDataPeserta = new DeleteDataPeserta();
        deleteDataPeserta.execute();
    }

    private void updateDataPeserta() {
        // variable data pegawai yang akan diubah
        final String nama_pst = edit_nama_pst.getText().toString().trim();
        final String email_pst = edit_email_pst.getText().toString().trim();
        final String hp_pst = edit_hp_pst.getText().toString().trim();
        final String instansi = edit_instansi.getText().toString().trim();

        class UpdateDataPeserta extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(PesertaDetailActivity.this,
                        "Mengubah Data","Harap Tunggu",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(KonfigurasiPeserta.KEY_PST_ID,id_pst);
                params.put(KonfigurasiPeserta.KEY_PST_NAMA,nama_pst);
                params.put(KonfigurasiPeserta.KEY_PST_EMAIL,email_pst);
                params.put(KonfigurasiPeserta.KEY_PST_HP,hp_pst);
                params.put(KonfigurasiPeserta.KEY_PST_INSTANSI,instansi);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(KonfigurasiPeserta.URL_UPDATE, params);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();;

                Fragment fragment = null;

                Toast.makeText(PesertaDetailActivity.this,
                        "Pesan: " + s, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(PesertaDetailActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "peserta");
                startActivity(myIntent);

            }
        }

        UpdateDataPeserta updateDataPeserta = new UpdateDataPeserta();
        updateDataPeserta.execute();
    }
}