package id.adiyusuf.finalproject;

public class KonfigurasiMateri {
    // url web API berada
    public static final String URL_GET_ALL = "http://192.168.92.102/training/materi/tr_datas_materi.php";
    public static final String URL_GET_DETAIL = "http://192.168.92.102/training/materi/tr_detail_materi.php?id_mat=";
    public static final String URL_ADD = "http://192.168.92.102/training/materi/tr_add_materi.php";
    public static final String URL_UPDATE = "http://192.168.92.102/training/materi/tr_update_materi.php";
    public static final String URL_DELETE = "http://192.168.92.102/training/materi/tr_delete_materi.php?id_mat=";

    // key and value json yang muncul di browser
    public static final String KEY_MAT_ID = "id_mat";
    public static final String KEY_MAT_NAMA = "nama_mat";

    // flag json
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_JSON_ID = "id_mat";
    public static final String TAG_JSON_NAMA = "nama_mat";
    public static final String TAG_JSON_NO = "no_mat";

    //variable ID Pegawai
    public static final String MAT_ID = "mat_id";
}
