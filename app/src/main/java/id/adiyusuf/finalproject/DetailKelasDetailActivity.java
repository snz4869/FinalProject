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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import id.adiyusuf.finalproject.databinding.ActivityMainBinding;

public class DetailKelasDetailActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edit_id_dtl_kls; //,edit_id_kls_dtl_kls, edit_id_pst_dtl_kls;
    String id_dtl_kls,public_nama,public_kelas;
    Button btn_update_dtl_kls,btn_delete_dtl_kls;
    private Spinner spinner_nama_pst_edit,spinner_nama_kelas_edit;
    private int spinner_value, spinner_value_kelas;
    private String JSON_STRING, JSON_STRING_KLS;

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kelas_detail);

        edit_id_dtl_kls = findViewById(R.id.edit_id_dtl_kls);
//        edit_id_kls_dtl_kls = findViewById(R.id.edit_id_kls_dtl_kls);
//        edit_id_pst_dtl_kls = findViewById(R.id.edit_id_pst_dtl_kls);
        btn_update_dtl_kls = findViewById(R.id.btn_update_dtl_kls);
        btn_delete_dtl_kls = findViewById(R.id.btn_delete_dtl_kls);
        spinner_nama_pst_edit = findViewById(R.id.spinner_nama_pst_edit);
        spinner_nama_kelas_edit = findViewById(R.id.spinner_nama_kelas_edit);

        Intent receiveIntent = getIntent();
        id_dtl_kls = receiveIntent.getStringExtra(KonfigurasiDetailKelas.DTL_KLS_ID);
        edit_id_dtl_kls.setText(id_dtl_kls);

        getJSON();

        btn_update_dtl_kls.setOnClickListener(this);
        btn_delete_dtl_kls.setOnClickListener(this);

        getDataKelas();
        getDataPesetra();
    }

    private void getDataKelas() {
        //bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasDetailActivity.this,
                        "Mengambil Data", "Harap Tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiKelas.URL_GET_ALL);

                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING_KLS = message;
                Log.d("DATA JSON: ", JSON_STRING_KLS);

                spinnerKelas();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void spinnerKelas() {

        JSONObject jsonObject = null;
        ArrayList<String> listIdKls = new ArrayList<>();
        ArrayList<String> listNamaKls = new ArrayList<>();

        try {
            jsonObject = new JSONObject(JSON_STRING_KLS);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiKelas.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING_KLS);
//            Toast.makeText(DetailKelasDetailActivity.this, "DATA JSON Result: " + result, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_kls = object.getString(KonfigurasiKelas.TAG_JSON_ID);
                String nama_kls = object.getString(KonfigurasiKelas.TAG_JSON_ID_MAT);
                listIdKls.add(id_kls);
                listNamaKls.add(nama_kls);
            }
//            Toast.makeText(this, "test: "+listNamaKls.toString(), Toast.LENGTH_SHORT).show();

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item,listNamaKls); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_nama_kelas_edit.setAdapter(spinnerArrayAdapter);

            spinner_nama_kelas_edit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinner_value_kelas = Integer.parseInt(listIdKls.get(i));
//                    Toast.makeText(DetailKelasDetailActivity.this, "True Value: "+spinner_value_kelas, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spinner_nama_kelas_edit.setSelection(listNamaKls.indexOf(public_kelas));//set selected value in spinner

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getDataPesetra() {
        //bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKelasDetailActivity.this,
                        "Mengambil Data", "Harap Tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiPeserta.URL_GET_ALL);

                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA JSON: ", JSON_STRING);

                spinnerPeserta();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void spinnerPeserta() {
        JSONObject jsonObject = null;
        ArrayList<String> listId = new ArrayList<>();
        ArrayList<String> listNama = new ArrayList<>();

        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiPeserta.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING);
            //Toast.makeText(getActivity(), "DATA JSON" + JSON_STRING, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_pst = object.getString(KonfigurasiPeserta.TAG_JSON_ID);
                String nama_pst = object.getString(KonfigurasiPeserta.TAG_JSON_NAMA);
                listId.add(id_pst);
                listNama.add(nama_pst);
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item,listNama); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_nama_pst_edit.setAdapter(spinnerArrayAdapter);

            spinner_nama_pst_edit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinner_value = Integer.parseInt(listId.get(i));
                    Toast.makeText(DetailKelasDetailActivity.this, "True Value: "+spinner_value, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinner_nama_pst_edit.setSelection(listNama.indexOf(public_nama));//set selected value in spinner

        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

//            String id_kls = object.getString(KonfigurasiDetailKelas.TAG_JSON_ID_KLS);
//            String id_pst = object.getString(KonfigurasiDetailKelas.TAG_JSON_ID_PST);
            String id_kls = object.getString(KonfigurasiDetailKelas.TAG_JSON_ID_KLS);
            String id_pst = object.getString(KonfigurasiDetailKelas.TAG_JSON_ID_PST);

            public_kelas = id_kls;
            public_nama = id_pst;

//            edit_id_kls_dtl_kls.setText(id_kls);
//            edit_id_pst_dtl_kls.setText(id_pst);
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
        final String id_kelas = Integer.toString(spinner_value);
        final String id_peserta = Integer.toString(spinner_value_kelas);

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