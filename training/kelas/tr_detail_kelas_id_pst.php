<?php 
	//Mendapatkan Nilai Dari Variable ID Pegawai yang ingin ditampilkan
	$id_pst = $_GET['id_pst'];
	
	//Importing database
	require_once('../koneksi.php');
	
	//Membuat SQL Query dengan pegawai yang ditentukan secara spesifik sesuai ID
	$sql = "SELECT a.id_kls, a.tgl_mulai_kls, a.tgl_akhir_kls,c.nama_ins, b.nama_mat 
	FROM tb_kelas a 
	JOIN tb_materi b ON a.id_mat = b.id_mat 
	JOIN tb_instruktur c ON a.id_ins = c.id_ins 
    JOIN tb_detail_kelas d ON a.id_kls = d.id_kls 
	WHERE id_pst = $id_pst";
	
	//Mendapatkan Hasil 
	$r = mysqli_query($con,$sql);
	
	//Memasukkan Hasil Kedalam Array
	$result = array();

	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"id_kls"=>$row['id_kls'],
			"tgl_mulai_kls"=>$row['tgl_mulai_kls'],
			"tgl_akhir_kls"=>$row['tgl_akhir_kls'],
            "nama_ins"=>$row['nama_ins'],
            "nama_mat"=>$row['nama_mat']
		));
	}

	//Menampilkan dalam format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>