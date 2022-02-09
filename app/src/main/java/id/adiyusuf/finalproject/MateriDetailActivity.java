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

public class MateriDetailActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edit_id_mat,edit_nama_mat;
    String id_mat;
    Button btn_update_mat,btn_delete_mat;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi_detail);

        edit_id_mat = findViewById(R.id.edit_id_mat);
        edit_nama_mat = findViewById(R.id.edit_nama_mat);
        btn_update_mat = findViewById(R.id.btn_update_mat);
        btn_delete_mat = findViewById(R.id.btn_delete_mat);

        Intent receiveIntent = getIntent();
        id_mat = receiveIntent.getStringExtra(KonfigurasiMateri.MAT_ID);
        edit_id_mat.setText(id_mat);

        getJSON();

        btn_update_mat.setOnClickListener(this);
        btn_delete_mat.setOnClickListener(this);
    }

    private void getJSON() {
        // batuan dari class AsynTask
        class GetJSON extends AsyncTask<Void,Void,String> { // boleh membuat class dalam method (Inner Class)
            ProgressDialog loading;

            @Override
            protected void onPreExecute() { // sebelum proses
                super.onPreExecute();
                loading = ProgressDialog.show(MateriDetailActivity.this,
                        "Mengambil Data","Harap Menunggu...",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) { // saat proses
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiMateri.URL_GET_DETAIL,id_mat);
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
            JSONArray result = jsonObject.getJSONArray(KonfigurasiMateri.TAG_JSON_ARRAY);
            JSONObject object = result.getJSONObject(0);

            String nama_mat = object.getString(KonfigurasiMateri.TAG_JSON_NAMA);

            edit_nama_mat.setText(nama_mat);
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
        if(myButton == btn_update_mat){
            updateDataMateri();
        } else if(myButton == btn_delete_mat){
            confirmDeleteDataMateri();
        }
    }

    private void confirmDeleteDataMateri() {
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
                deleteDataMateri();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteDataMateri() {
        class DeleteDataMateri extends AsyncTask<Void,Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(MateriDetailActivity.this,
                        "Menghapus Data","Harap Tunggu",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiMateri.URL_DELETE, id_mat);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();
                Toast.makeText(MateriDetailActivity.this,"Pesan: " + s,
                        Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(MateriDetailActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "materi");
                startActivity(myIntent);
            }
        }
        DeleteDataMateri deleteDataMateri = new DeleteDataMateri();
        deleteDataMateri.execute();
    }

    private void updateDataMateri() {
        // variable data pegawai yang akan diubah
        final String nama_mat = edit_nama_mat.getText().toString().trim();

        class UpdateDataMateri extends AsyncTask<Void,Void,String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MateriDetailActivity.this,
                        "Mengubah Data","Harap Tunggu",
                        false,false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HashMap<String,String> params = new HashMap<>();
                params.put(KonfigurasiMateri.KEY_MAT_ID,id_mat);
                params.put(KonfigurasiMateri.KEY_MAT_NAMA,nama_mat);
                HttpHandler handler = new HttpHandler();
                String result = handler.sendPostRequest(KonfigurasiMateri.URL_UPDATE, params);

                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();;

                Fragment fragment = null;

                Toast.makeText(MateriDetailActivity.this,
                        "Pesan: " + s, Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(MateriDetailActivity.this, MainActivity.class);
                myIntent.putExtra("keyName", "materi");
                startActivity(myIntent);

            }
        }

        UpdateDataMateri updateDataMateri = new UpdateDataMateri();
        updateDataMateri.execute();
    }

}