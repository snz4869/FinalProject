package id.adiyusuf.finalproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KelasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KelasFragment extends Fragment {

    ListView list_view_kls;
    private String JSON_STRING;
    Button btn_add_kls_frag;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public KelasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KelasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KelasFragment newInstance(String param1, String param2) {
        KelasFragment fragment = new KelasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kelas, container, false);
        list_view_kls = view.findViewById(R.id.list_view_kls);
        btn_add_kls_frag = view.findViewById(R.id.btn_add_kls_frag);

        getJSON();

        list_view_kls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity(),
                        KelasDetailActivity.class);
                HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
                String insId = map.get(KonfigurasiKelas.TAG_JSON_ID).toString();
                myIntent.putExtra(KonfigurasiKelas.KLS_ID, insId);
                startActivity(myIntent);
            }
        });

        btn_add_kls_frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), KelasAddActivity.class));
            }
        });

        return view;
    }

    private void getJSON() {
        //bantuan dari class AsyncTask
        class GetJSON extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),
                        "Mengambil Data", "Harap Tunggu...",
                        false, false);
            }

            @Override
            protected String doInBackground(Void... voids) {
                HttpHandler handler = new HttpHandler();
                String result = handler.sendGetResponse(KonfigurasiKelas.URL_GET_ALL);
//                System.out.println("Result: " + result);
                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;
                Log.d("DATA JSON: ", JSON_STRING);

//                Toast.makeText(getActivity(),
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
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiKelas.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING);
//            Toast.makeText(getActivity(), "DATA JSON" + JSON_STRING, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_kls = object.getString(KonfigurasiKelas.TAG_JSON_ID);
                String tgl_mulai = object.getString(KonfigurasiKelas.TAG_JSON_TGL_MULAI);
                String tgl_akhir = object.getString(KonfigurasiKelas.TAG_JSON_TGL_AKHIR);
                HashMap<String, String> kelas = new HashMap<>();
                kelas.put(KonfigurasiKelas.TAG_JSON_ID, id_kls);
                kelas.put(KonfigurasiKelas.TAG_JSON_TGL_MULAI, tgl_mulai);
                kelas.put(KonfigurasiKelas.TAG_JSON_TGL_AKHIR, tgl_akhir);

                //ubah format JSON menjadi Array List
                list.add(kelas);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // adapter untuk meletakan array list kedalam list view

        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list,
                R.layout.list_item_kelas,
                new String[]{KonfigurasiKelas.TAG_JSON_ID, KonfigurasiKelas.TAG_JSON_TGL_MULAI,
                        KonfigurasiKelas.TAG_JSON_TGL_AKHIR},
                new int[]{R.id.txt_id_kls, R.id.txt_tgl_mulai_kls, R.id.txt_tgl_akhir_kls}
        );
        list_view_kls.setAdapter(adapter);
    }
}