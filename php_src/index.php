<?php
header("Content-type: text/html; charset=utf-8");

require './muni.php';

$a = file_get_contents('php://input');

$a = str_replace(' ','',$a);
$latlng = explode(',',$a);
$lat = $latlng[0];
$lng = $latlng[1];

$URL = 'https://mreversegeocoder.gsi.go.jp/reverse-geocoder/LonLatToAddress?lat='.$lat.'&lon='.$lng;

$curl = curl_init();
curl_setopt($curl, CURLOPT_URL,$URL);
curl_setopt($curl, CURLOPT_CUSTOMREQUEST,'GET');
curl_setopt($curl, CURLOPT_SSL_VERIFYPEER,false);
curl_setopt($curl, CURLOPT_RETURNTRANSFER,true);
$resp = curl_exec($curl);
curl_close($curl);

$result = json_decode($resp,true);

$city_code = $result['results']['muniCd'];
$addr = $result['results']['lv01Nm'];

$munidat = getMUNI($city_code);
$munidat = explode(',',$munidat);

$pref_name = $munidat[1];
$city_name = $munidat[3];
$a = $city_name;

echo $pref_name.$city_name;

$w = '現在'.$pref_name.$city_name.$addr.'に居ます';

file_put_contents('./a.txt',$a);
?>