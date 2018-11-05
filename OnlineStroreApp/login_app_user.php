<?php

$dc = new mysqli("localhost", "root", "", "online_store");

$check_login_info=$dc->prepare("select * from app_users_table where email=? and password=?");
$check_login_info->bind_param("ss", $_GET["email"], $_GET["password"]);
$check_login_info->execute();

$login_result = $check_login_info->get_result();

if($login_result->num_rows==0){
    
    echo 'the user does not exist';
}else{
    echo 'the user does exist';
}