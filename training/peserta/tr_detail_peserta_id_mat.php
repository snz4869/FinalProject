<?php 
	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	$id_mat = $_GET['id_mat'];
	
	//Importing database
	require_once('../koneksi.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT a.* FROM tb_peserta a 
    JOIN tb_detail_kelas b ON a.id_pst = b.id_pst
    JOIN tb_kelas c ON b.id_kls = c.id_kls
    JOIN tb_materi d ON c.id_mat = d.id_mat 
    WHERE d.id_mat=$id_mat";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"id_pst"=>$row['id_pst'],
			"nama_pst"=>$row['nama_pst'],
            "email_pst"=>$row['email_pst'],
            "hp_pst"=>$row['hp_pst'],
            "instansi_pst"=>$row['instansi_pst']
		));
	}

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>