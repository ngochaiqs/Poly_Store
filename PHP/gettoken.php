<?php
include "connect.php";
$status = $_POST['status'];
if($status == 1){
	$query = "SELECT * FROM `nguoiDung` WHERE `status` = ".$status;
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)){
	$result[] = ($row);
	//code
}
if (!empty($result)){
	$arr = [
		'success' => true,
		'message' => "thanh cong",
		'result' => $result
	];
}else{
	$arr = [
		'success' => false,
		'message' => " khong thanh cong",
		'result' => $result
	];
}
}else if ($status == 0){
	//code...
	$maND = $_POST['maND'];
	$query = "SELECT * FROM `nguoiDung` WHERE `maND" = ".$maND. AND `status` = ".$status;
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)){
	$result[] = ($row);
	//code...
}
if (!empty($result)){
	$arr = [
		'success' => true,
		'message' => "thanh cong",
		'result' => $result
	];
}else{
	$arr = [
		'success' => false,
		'message' => "khong thanh cong"
		'result' => $result
	];
}

}

print_r(json_encode($arr));
?>