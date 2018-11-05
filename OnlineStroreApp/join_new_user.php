<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$connection = new mysqli("localhost", "root", "", "online_store");


//$sqlCommand = $connection->prepare("insert into app_users_table values (?, ?, ?)");
//$sqlCommand->bind_param("sss", $_GET["email"], $_GET["username"], $_GET["pass"]);
//$sqlCommand->execute();


//if email exists
$emailCheckSQLCommand = $connection->prepare("select * from app_users_table where email=?");
$emailCheckSQLCommand->bind_param("s", $_GET["email"]);
$emailCheckSQLCommand->execute();
$emailResult = $emailCheckSQLCommand->get_result();

if($emailResult->num_rows == 0){
    
    $sqlCommand = $connection->prepare("insert into app_users_table values(?, ?, ?)");
    $sqlCommand->bind_param("sss", $_GET["email"], $_GET["username"], $_GET["password"]);
    $sqlCommand->execute();
    echo 'The registration is successful';
    
    
}else{
    echo "a user with this email already exists";
}