<?php 
	//Import File Koneksi Database
	require_once('../koneksi.php');
	
	//Membuat SQL Query
	$sql = "SELECT (SELECT COUNT(*) FROM tb_peserta) c_pst,  
    (SELECT COUNT(*) FROM tb_detail_kelas) c_detail_kelas,  
    (SELECT COUNT(*) FROM tb_kelas) c_kls,
    (SELECT COUNT(*) FROM tb_materi) c_mat,
    (SELECT COUNT(*) FROM tb_instruktur) c_ins";
	
	//Mendapatkan Hasil
	$r = mysqli_query($con,$sql);
	
	//Membuat Array Kosong 
	$result = array();
	
	while($row = mysqli_fetch_array($r)){
		
		//Memasukkan Nama dan ID kedalam Array Kosong yang telah dibuat 
		array_push($result,array(
			"c_pst"=>$row['c_pst'],
			"c_detail_kelas"=>$row['c_detail_kelas'],
            "c_kls"=>$row['c_kls'],
            "c_mat"=>$row['c_mat'],
            "c_ins"=>$row['c_ins']
		));
	}
	
	//Menampilkan Array dalam Format JSON
	echo json_encode(array('result'=>$result));
	
	mysqli_close($con);
?>