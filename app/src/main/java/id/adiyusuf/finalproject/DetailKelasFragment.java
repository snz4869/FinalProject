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
 * Use the {@link DetailKelasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailKelasFragment extends Fragment {
    ListView list_view_dtl_kls;
    private String JSON_STRING;
    Button btn_add_dtl_kls_frag;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailKelasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailKelasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailKelasFragment newInstance(String param1, String param2) {
        DetailKelasFragment fragment = new DetailKelasFragment();
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
        View view = inflater.inflate(R.layout.fragment_detail_kelas, container, false);
        list_view_dtl_kls = view.findViewById(R.id.list_view_dtl_kls);
        btn_add_dtl_kls_frag = view.findViewById(R.id.btn_add_dtl_kls_frag);

        getJSON();

        list_view_dtl_kls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity(),
                        DetailKelasDetailActivity.class);
                HashMap<String, String> map = (HashMap) parent.getItemAtPosition(position);
                String dtlKlsId = map.get(KonfigurasiDetailKelas.TAG_JSON_ID).toString();
                myIntent.putExtra(KonfigurasiDetailKelas.DTL_KLS_ID, dtlKlsId);
                startActivity(myIntent);
            }
        });

        btn_add_dtl_kls_frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DetailKelasAddActivity.class));
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
                String result = handler.sendGetResponse(KonfigurasiDetailKelas.URL_GET_ALL);
//                System.out.println("Result: " + result);

                return result;
            }

            @Override
            protected void onPostExecute(String message) {
                super.onPostExecute(message);
                loading.dismiss();
                JSON_STRING = message;

//                Toast.makeText(getActivity(), "JSON: " + message, Toast.LENGTH_SHORT).show();
                Log.d("DATA JSON: ", JSON_STRING);

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
            JSONArray result = jsonObject.getJSONArray(KonfigurasiDetailKelas.TAG_JSON_ARRAY);
            Log.d("DATA JSON: ", JSON_STRING);
//            Toast.makeText(getActivity(), "DATA JSON" + JSON_STRING, Toast.LENGTH_SHORT).show();

            for (int i = 0; i < result.length(); i++) {
                JSONObject object = result.getJSONObject(i);
                String id_dtl_kls = object.getString(KonfigurasiDetailKelas.TAG_JSON_ID);
                String id_kls_dtl_kls = object.getString(KonfigurasiDetailKelas.TAG_JSON_ID_KLS);
                String id_pst_dtl_kls = object.getString(KonfigurasiDetailKelas.TAG_JSON_ID_PST);
                HashMap<String, String> peserta = new HashMap<>();
                peserta.put(KonfigurasiDetailKelas.TAG_JSON_ID, id_dtl_kls);
                peserta.put(KonfigurasiDetailKelas.TAG_JSON_ID_KLS, id_kls_dtl_kls);
                peserta.put(KonfigurasiDetailKelas.TAG_JSON_ID_PST, id_pst_dtl_kls);

                //ubah format JSON menjadi Array List
                list.add(peserta);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // adapter untuk meletakan array list kedalam list view

        ListAdapter adapter = new SimpleAdapter(
                getActivity(), list,
                R.layout.list_item_detail_kelas,
                new String[]{KonfigurasiDetailKelas.TAG_JSON_ID, KonfigurasiDetailKelas.TAG_JSON_ID_KLS,
                        KonfigurasiDetailKelas.TAG_JSON_ID_PST},
                new int[]{R.id.txt_id_dtl_kls, R.id.txt_id_kls_dtl, R.id.txt_id_pst_dtl}
        );
        list_view_dtl_kls.setAdapter(adapter);
    }
}