<?php 
	//Import File Koneksi Database
	require_once('../koneksi.php');
	
	//Membuat SQL Query
	$sql = "SELECT t.*, @rownum := @rownum + 1 AS no_mat
	FROM tb_materi t,
	(SELECT @rownum := 0) r
	ORDER BY nama_mat";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"id_mat"=>$row['id_mat'],
			"nama_mat"=>$row['nama_mat'],
			"no_mat"=>$row['no_mat']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>