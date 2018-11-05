<?php

$connection = new mysqli("localhost", "root", "", "online_store");
$getTemporaryOrdersCommand = $connection->prepare("select * from temporary_place_order where user=?");
$getTemporaryOrdersCommand->bind_param("s", $_GET["user"]);
$getTemporaryOrdersCommand->execute();
$temporaryOrdersResult=$getTemporaryOrdersCommand->get_result();

$populateInvoiceWithEmailCommand = $connection->prepare("insert into invoice(user) values(?)");
$populateInvoiceWithEmailCommand->bind_param("s", $_GET["user"]);
$populateInvoiceWithEmailCommand->execute();

$getLastestInvoiceNumberCommand=$connection->prepare("select max(invoice_num) as latest_invoice_num from invoice where user=?");
$getLastestInvoiceNumberCommand->bind_param("s", $_GET["user"]);
$getLastestInvoiceNumberCommand->execute();
$invoice_number_result = $getLastestInvoiceNumberCommand->get_result();
$row_invoiceNumber=$invoice_number_result->fetch_assoc();


while($row=$temporaryOrdersResult->fetch_assoc()){
    
    $populateInvoiceDetailsCommand = $connection->prepare("insert into invoice_details values(?,?,?)");
    $populateInvoiceDetailsCommand->bind_param("iii", $row_invoiceNumber["latest_invoice_num"], $row["product_id"], $row["amount"]);
    $populateInvoiceDetailsCommand->execute();
    

    $deleteTempOrdersCommand = $connection->prepare("delete from temporary_place_order where user=?");
    $deleteTempOrdersCommand->bind_param("s", $_GET["user"]);
    $deleteTempOrdersCommand->execute();
    
    
}


echo $row_invoiceNumber["latest_invoice_num"];