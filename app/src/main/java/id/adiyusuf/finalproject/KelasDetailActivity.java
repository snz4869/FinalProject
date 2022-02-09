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

public class KelasDetailActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edit_id_kls,edit_tgl_mulai, edit_tgl_akhir, edit_id_ins_kls, edit_id_mat_kls;
    String id_kls;
    Button btn_update_kls,btn_delete_kls;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelas_detail);

        edit_id_kls = findViewById(R.id.edit_id_kls);
        edit_tgl_mulai = findViewById(R.id.edit_tgl_mulai);
        edit_tgl_akhir = findViewById(R.id.edit_tgl_akhir);
        edit_id_ins_kls = findViewById(R.id.edit_id_ins_kls);
        edit_id_mat_kls = findViewById(R.id.edit_id_mat_kls);
        btn_update_kls = findViewById(R.id.btn_update_kls);
        btn_delete_kls = findViewById(R.id.btn_delete_kls);

        Intent receiveIntent = getIntent();
        id_kls = receiveIntent.getStringExtra(KonfigurasiKelas.KLS_ID);
        edit_id_kls.setText(id_kls);

        getJSON();

        btn_update_kls.setOnClickListener(this);
        btn_delete_kls.setOnClickListener(this);
    }

    private void getJSON() {
        // batuan dari class AsynTask
        class GetJSON extends AsyncTask<Void,Void,String> { // boleh membuat class dalam method (Inner Class)
            ProgressDialog loading;

            @Override
            protected void onPreExecute() { // sebelum proses
                super.onPreExecute();
                loading = ProgressDialog.show(KelasDetailActivity.this,
                        "Mengambil Data","Harap Menunggu...",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) { // saat proses
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiKelas.URL_GET_DETAIL,id_kls);
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
            JSONArray result = jsonObject.getJSONArray(KonfigurasiKelas.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String tgl_mulai = object.getString(KonfigurasiKelas.TAG_JSON_TGL_MULAI);
            String tgl_akhir = object.getString(KonfigurasiKelas.TAG_JSON_TGL_AKHIR);
            String id_ins_kls = object.getString(KonfigurasiKelas.TAG_JSON_ID_INS);
            String id_mat_kls = object.getString(KonfigurasiKelas.TAG_JSON_ID_MAT);

            edit_tgl_mulai.setText(tgl_mulai);
            edit_tgl_akhir.setText(tgl_akhir);
            edit_id_ins_kls.setText(id_ins_kls);
            edit_id_mat_kls.setText(id_mat_kls);
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
        if(myButton == btn_update_kls){
            updateDataKelas();
        } else if(myButton == btn_delete_kls){
            confirmDeleteDataKelas();
        }
    }

    private void confirmDeleteDataKelas() {
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
                deleteDataKelas();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataKelas() {
        class DeleteDataKelas extends AsyncTask<Void,Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(KelasDetailActivity.this,
                        "Menghapus Data","Harap Tunggu",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiKelas.URL_DELETE, id_kls);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                Toast.makeText(KelasDetailActivity.this,"Pesan: " + s,
                        Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(KelasDetailActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "kelas");
                startActivity(myIntent);
            }
        }
        DeleteDataKelas deleteDataKelas = new DeleteDataKelas();
        deleteDataKelas.execute();
    }

    private void updateDataKelas() {
        // variable data pegawai yang akan diubah
        final String tgl_mulai = edit_tgl_mulai.getText().toString().trim();
        final String tgl_akhir = edit_tgl_akhir.getText().toString().trim();
        final String id_ins_kls = edit_id_ins_kls.getText().toString().trim();
        final String id_mat_kls = edit_id_mat_kls.getText().toString().trim();

        class UpdateDataKelas extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(KelasDetailActivity.this,
                        "Mengubah Data","Harap Tunggu",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(KonfigurasiKelas.KEY_KLS_ID,id_kls);
                params.put(KonfigurasiKelas.KEY_KLS_TGL_MULAI,tgl_mulai);
                params.put(KonfigurasiKelas.KEY_KLS_TGL_AKHIR,tgl_akhir);
                params.put(KonfigurasiKelas.KEY_KLS_ID_INS,id_ins_kls);
                params.put(KonfigurasiKelas.KEY_KLS_ID_MAT,id_mat_kls);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(KonfigurasiKelas.URL_UPDATE, params);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();;

                Fragment fragment = null;

                Toast.makeText(KelasDetailActivity.this,
                        "Pesan: " + s, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(KelasDetailActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "kelas");
                startActivity(myIntent);

            }
        }

        UpdateDataKelas updateDataKelas = new UpdateDataKelas();
        updateDataKelas.execute();
    }
}