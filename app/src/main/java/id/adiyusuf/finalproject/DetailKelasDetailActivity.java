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

public class DetailKelasDetailActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edit_id_dtl_kls,edit_id_kls_dtl_kls, edit_id_pst_dtl_kls;
    String id_dtl_kls;
    Button btn_update_dtl_kls,btn_delete_dtl_kls;

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kelas_detail);

        edit_id_dtl_kls = findViewById(R.id.edit_id_dtl_kls);
        edit_id_kls_dtl_kls = findViewById(R.id.edit_id_kls_dtl_kls);
        edit_id_pst_dtl_kls = findViewById(R.id.edit_id_pst_dtl_kls);
        btn_update_dtl_kls = findViewById(R.id.btn_update_dtl_kls);
        btn_delete_dtl_kls = findViewById(R.id.btn_delete_dtl_kls);

        Intent receiveIntent = getIntent();
        id_dtl_kls = receiveIntent.getStringExtra(KonfigurasiDetailKelas.DTL_KLS_ID);
        edit_id_dtl_kls.setText(id_dtl_kls);

        getJSON();

        btn_update_dtl_kls.setOnClickListener(this);
        btn_delete_dtl_kls.setOnClickListener(this);
    }

    private void getJSON() {
        // batuan dari class AsynTask
        class GetJSON extends AsyncTask<Void,Void,String> { // boleh membuat class dalam method (Inner Class)
            ProgressDialog loading;

            @Override
            protected void onPreExecute() { // sebelum proses
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasDetailActivity.this,
                        "Mengambil Data","Harap Menunggu...",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) { // saat proses
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiDetailKelas.URL_GET_DETAIL,id_dtl_kls);
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
            JSONArray result = jsonObject.getJSONArray(KonfigurasiDetailKelas.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String id_kls = object.getString(KonfigurasiDetailKelas.TAG_JSON_ID_KLS);
            String id_pst = object.getString(KonfigurasiDetailKelas.TAG_JSON_ID_PST);

            edit_id_kls_dtl_kls.setText(id_kls);
            edit_id_pst_dtl_kls.setText(id_pst);
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
        if(myButton == btn_update_dtl_kls){
            updateDataDetailKelas();
        } else if(myButton == btn_delete_dtl_kls){
            confirmDeleteDataDetailKelas();
        }
    }

    private void updateDataDetailKelas() {
        // variable data pegawai yang akan diubah
        final String id_kelas = edit_id_kls_dtl_kls.getText().toString().trim();
        final String id_peserta = edit_id_pst_dtl_kls.getText().toString().trim();

        class UpdateDataDetailKelas extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasDetailActivity.this,
                        "Mengubah Data","Harap Tunggu",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(KonfigurasiDetailKelas.KEY_DTL_KLS_ID,id_dtl_kls);
                params.put(KonfigurasiDetailKelas.KEY_DTL_KLS_ID_KLS,id_kelas);
                params.put(KonfigurasiDetailKelas.KEY_DTL_KLS_ID_PST,id_peserta);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(KonfigurasiDetailKelas.URL_UPDATE, params);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();;

                Fragment fragment = null;

                Toast.makeText(DetailKelasDetailActivity.this,
                        "Pesan: " + s, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(DetailKelasDetailActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "detail kelas");
                startActivity(myIntent);

            }
        }

        UpdateDataDetailKelas updateDataDetailKelas = new UpdateDataDetailKelas();
        updateDataDetailKelas.execute();
    }

    private void confirmDeleteDataDetailKelas() {
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
                deleteDataDetailKelas();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataDetailKelas() {
        class DeleteDataDetailKelas extends AsyncTask<Void,Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(DetailKelasDetailActivity.this,
                        "Menghapus Data","Harap Tunggu",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiDetailKelas.URL_DELETE, id_dtl_kls);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                Toast.makeText(DetailKelasDetailActivity.this,"Pesan: " + s,
                        Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(DetailKelasDetailActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "detail kelas");
                startActivity(myIntent);
            }
        }
        DeleteDataDetailKelas deleteDataDetailKelas = new DeleteDataDetailKelas();
        deleteDataDetailKelas.execute();
    }
}