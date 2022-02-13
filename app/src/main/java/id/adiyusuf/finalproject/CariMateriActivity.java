package id.adiyusuf.finalproject;

import androidx.appcompat.app.AppCompatActivity;

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

public class CariMateriActivity extends AppCompatActivity {
    private Button btn_cari_mat_pst;
    private Spinner spinner_cari_mat;
    private ListView list_cari_mat;
    private String spinner_value,JSON_STRING_PST,JSON_STRING_MAT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_materi);

        btn_cari_mat_pst = findViewById(R.id.btn_cari_mat_pst);
        spinner_cari_mat = findViewById(R.id.spinner_cari_mat);
        list_cari_mat = findViewById(R.id.list_cari_mat);

        getDataMateri();

        btn_cari_mat_pst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataPeserta();
            }
        });
    }

    private void getDataMateri() {
        //bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CariMateriActivity.this,
                        "Mengambil Data", "Harap Tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiMateri.URL_GET_ALL);
//                System.out.println("Result: " + result);

                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING_MAT = message;
                Log.d("DATA JSON: ", JSON_STRING_MAT);
                //Toast.makeText(getActivity(),
                //        message.toString(), Toast.LENGTH_SHORT).show();
                //menampilkan data dalam bentuk list view
                spinnerMateri();
            }
        }
        GetJSON getJSON = new GetJSON();
        getJSON.execute();
    }

    private void spinnerMateri() {
        JSONObject jsonObject = null;
        ArrayList<String> listId = new ArrayList<>();
        ArrayList<String> listNama = new ArrayList<>();

        try {
            jsonObject = new JSONObject(JSON_STRING_MAT);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiMateri.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING_MAT);
            //Toast.makeText(getActivity(), "DATA JSON" + JSON_STRING, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_mat = object.getString(KonfigurasiMateri.TAG_JSON_ID);
                String nama_mat = object.getString(KonfigurasiMateri.TAG_JSON_NAMA);
                listId.add(id_mat);
                listNama.add(nama_mat);
            }

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item,listNama); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_cari_mat.setAdapter(spinnerArrayAdapter);

            spinner_cari_mat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    spinner_value = listId.get(i);
                    Toast.makeText(CariMateriActivity.this, "True Value: "+spinner_value, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getDataPeserta() {
        //bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(CariMateriActivity.this,
                        "Mengambil Data", "Harap Tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiPeserta.URL_GET_DETAIL_MAT,spinner_value);
//                System.out.println("Result: " + result);

                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING_PST = message;
                Log.d("DATA JSON: ", JSON_STRING_PST);

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
            jsonObject = new JSONObject(JSON_STRING_PST);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiPeserta.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING_PST);
            //Toast.makeText(getActivity(), "DATA JSON" + JSON_STRING, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_pst = object.getString(KonfigurasiPeserta.TAG_JSON_ID);
                String nama_pst = object.getString(KonfigurasiPeserta.TAG_JSON_NAMA);
                HashMap<String, String> peserta = new HashMap<>();
                peserta.put(KonfigurasiPeserta.TAG_JSON_ID, id_pst);
                peserta.put(KonfigurasiPeserta.TAG_JSON_NAMA, nama_pst);

                //ubah format JSON menjadi Array List
                list.add(peserta);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // adapter untuk meletakan array list kedalam list view

        ListAdapter adapter = new SimpleAdapter(
                CariMateriActivity.this, list,
                R.layout.list_item_cari_materi,
                new String[]{KonfigurasiPeserta.TAG_JSON_ID, KonfigurasiPeserta.TAG_JSON_NAMA},
                new int[]{R.id.txt_cari_id_pst, R.id.txt_cari_name_pst}
        );
        list_cari_mat.setAdapter(adapter);
    }
}