<?php

$connection = new mysqli("localhost", "root", "", "online_store");
$sqlCommand = $connection->prepare("delete from temporary_place_order where user=?"); // AND product_id=?
$sqlCommand->bind_param("s", $_GET["user"]); //, $_GET["product_id"]
$sqlCommand->execute();
