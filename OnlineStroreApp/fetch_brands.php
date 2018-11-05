<?php

$connection = new mysqli("localhost", "root", "", "online_store");
$selectBrands = $connection->prepare("select distinct brand from electronic_products"); //when u distinct the brands it will sort the same names into 1 name
$selectBrands->execute();

$brandsResult = $selectBrands->get_result();

$brands = array();

while ($row = $brandsResult->fetch_assoc()){
    
    array_push($brands, $row);
    
}

echo json_encode($brands);