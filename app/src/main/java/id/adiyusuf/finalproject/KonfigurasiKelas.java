package id.adiyusuf.finalproject;

public class KonfigurasiKelas {
    // url web API berada
    public static final String URL_GET_ALL = "http://192.168.92.102/training/kelas/tr_datas_kelas.php";
    public static final String URL_GET_DETAIL = "http://192.168.92.102/training/kelas/tr_detail_kelas.php?id_kls=";
    public static final String URL_GET_DETAIL_PST = "http://192.168.92.102/training/kelas/tr_detail_kelas_id_pst.php?id_pst=";
    public static final String URL_GET_DETAIL_TGL = "http://192.168.92.102/training/kelas/tr_detail_kelas_tgl_pst.php";
    public static final String URL_ADD = "http://192.168.92.102/training/kelas/tr_add_kelas.php";
    public static final String URL_UPDATE = "http://192.168.92.102/training/kelas/tr_update_kelas.php";
    public static final String URL_DELETE = "http://192.168.92.102/training/kelas/tr_delete_kelas.php?id_kls=";

    // key and value json yang muncul di browser
    public static final String KEY_KLS_ID = "id_kls";
    public static final String KEY_KLS_TGL_MULAI = "tgl_mulai_kls";
    public static final String KEY_KLS_TGL_AKHIR = "tgl_akhir_kls";
    public static final String KEY_KLS_ID_INS = "id_ins";
    public static final String KEY_KLS_ID_MAT = "id_mat";

    // flwg json
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_ID = "id_kls";
    public static final String TAG_JSON_TGL_MULAI = "tgl_mulai_kls";
    public static final String TAG_JSON_TGL_AKHIR = "tgl_akhir_kls";
    public static final String TAG_JSON_ID_INS = "nama_ins";
    public static final String TAG_JSON_ID_MAT = "nama_mat";

    //variable ID Pegawai
    public static final String KLS_ID = "kls_id";
}
