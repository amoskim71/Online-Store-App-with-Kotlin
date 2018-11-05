<?php

$connection = new mysqli("localhost", "root", "", "online_store");
$sqlCommand = $connection->prepare("SELECT id,name,price,user,amount FROM temporary_place_order INNER JOIN electronic_products on electronic_products.id=temporary_place_order.product_id where user=?");
$sqlCommand->bind_param("s", $_GET["user"]);
$sqlCommand->execute();

$temporderarray = array();

$sqlResult = $sqlCommand->get_result();
while($row=$sqlResult->fetch_assoc()){
    
    array_push($temporderarray, $row);
    
}
echo json_encode($temporderarray);