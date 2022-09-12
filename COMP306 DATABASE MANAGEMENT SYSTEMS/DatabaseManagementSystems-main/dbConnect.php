<?php

/*
COMP306-DATABASE MANAGEMENT SYSTEMS


More information about the parameters can be found: 
https://www.php.net/manual/en/mysqli.construct.php
*/

$host     = 'localhost';

////My username///////////////////////////////////////
$username = 'root'; # Please write your username here.
////My username//////////////////////////////////////

/////My password////////////////////////////////////////
$passwd   = 'Bk454454.'; # Please write your passwd here.
/////My password///////////////////////////////////////

$dbName   = 'world';

$conn = mysqli_connect($host, $username, $passwd, $dbName);

if(!$conn){
    die('Connection failed: '.mysqli_connect_error());
}

////Closing the PHP Tag
?>