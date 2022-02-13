package id.adiyusuf.finalproject;

public class KonfigurasiPeserta {

        // url web API berada
        public static final String URL_GET_ALL = "http://192.168.92.102/training/peserta/tr_datas_peserta.php";
        public static final String URL_GET_DETAIL = "http://192.168.92.102/training/peserta/tr_detail_peserta.php?id_pst=";
        public static final String URL_GET_DETAIL_MAT = "http://192.168.92.102/training/peserta/tr_detail_peserta_id_mat.php?id_mat=";
        public static final String URL_ADD = "http://192.168.92.102/training/peserta/tr_add_peserta.php";
        public static final String URL_UPDATE = "http://192.168.92.102/training/peserta/tr_update_peserta.php";
        public static final String URL_DELETE = "http://192.168.92.102/training/peserta/tr_delete_peserta.php?id_pst=";

        // key and value json yang muncul di browser
        public static final String KEY_PST_ID = "id_pst";
        public static final String KEY_PST_NAMA = "nama_pst";
        public static final String KEY_PST_EMAIL = "email_pst";
        public static final String KEY_PST_HP = "hp_pst";
        public static final String KEY_PST_INSTANSI = "instansi_pst";

        // flwg json
        public static final String TAG_JSON_ARRAY = "result";
        public static final String TAG_JSON_ID = "id_pst";
        public static final String TAG_JSON_NAMA = "nama_pst";
        public static final String TAG_JSON_EMAIL = "email_pst";
        public static final String TAG_JSON_HP = "hp_pst";
        public static final String TAG_JSON_INSTANSI = "instansi_pst";

        //variable ID Pegawai
        public static final String PST_ID = "pst_id";
    }
