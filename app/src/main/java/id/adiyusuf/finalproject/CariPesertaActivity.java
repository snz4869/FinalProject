package id.adiyusuf.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class CariPesertaActivity extends AppCompatActivity {
    private Button btn_cari_pst_mat;
    private Spinner spinner_cari_pst;
    private ListView list_cari_pst;
    private String spinner_value,JSON_STRING_PST,JSON_STRING_KLS;
    Toolbar toolbar_c_pst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_peserta);

        btn_cari_pst_mat = findViewById(R.id.btn_cari_pst_mat);
        spinner_cari_pst = findViewById(R.id.spinner_cari_pst);
        list_cari_pst = findViewById(R.id.list_cari_pst);
        toolbar_c_pst = findViewById(R.id.toolbar_c_pst);

        setSupportActionBar(toolbar_c_pst);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        
        getDataPeserta();

        btn_cari_pst_mat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataKelas();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getDataKelas() {
        //bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CariPesertaActivity.this,
                        "Mengambil Data", "Harap Tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiKelas.URL_GET_DETAIL_PST,spinner_value);
//                System.out.println("Result: " + result);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING_KLS = message;
                Log.d("DATA JSON: ", JSON_STRING_KLS);

//                Toast.makeText(CariPesertaActivity.this,
//                        message.toString(), Toast.LENGTH_SHORT).show();
                //menampilkan data dalam bentuk list view
                displayAllData();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void displayAllData() {
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            jsonObject = new JSONObject(JSON_STRING_KLS);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiKelas.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING_KLS);
//            Toast.makeText(getActivity(), "DATA JSON" + JSON_STRING, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_kls = object.getString(KonfigurasiKelas.TAG_JSON_ID);
                String instruktur = object.getString(KonfigurasiKelas.TAG_JSON_ID_INS);
                String materi = object.getString(KonfigurasiKelas.TAG_JSON_ID_MAT);
                HashMap<String, String> kelas = new HashMap<>();
                kelas.put(KonfigurasiKelas.TAG_JSON_ID, id_kls);
                kelas.put(KonfigurasiKelas.TAG_JSON_ID_INS, instruktur);
                kelas.put(KonfigurasiKelas.TAG_JSON_ID_MAT, materi);

                //ubah format JSON menjadi Array List
                list.add(kelas);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // adapter untuk meletakan array list kedalam list view

        ListAdapter adapter = new SimpleAdapter(
                CariPesertaActivity.this, list,
                R.layout.list_item_cari_peserta,
                new String[]{KonfigurasiKelas.TAG_JSON_ID, KonfigurasiKelas.TAG_JSON_ID_INS,
                        KonfigurasiKelas.TAG_JSON_ID_MAT},
                new int[]{R.id.txt_cari_id_kls, R.id.txt_cari_ins_kls, R.id.txt_cari_materi_kls}
        );
        list_cari_pst.setAdapter(adapter);
    }

    private void getDataPeserta() {
        //bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CariPesertaActivity.this,
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
                JSON_STRING_PST = message;
                Log.d("DATA JSON: ", JSON_STRING_PST);

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
            jsonObject = new JSONObject(JSON_STRING_PST);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiPeserta.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING_PST);
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
            spinner_cari_pst.setAdapter(spinnerArrayAdapter);

            spinner_cari_pst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinner_value = listId.get(i);
//                    Toast.makeText(CariPesertaActivity.this, "True Value: "+spinner_value, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
