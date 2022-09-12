<?php

require_once 'include/dbConnect.php';
require_once 'include/functions_quiz.php';

if (isset($_POST['insert'])){

    $cid = $_POST["cid_insert"];
    $name = $_POST["name_insert"];
    $country_code = $_POST["country_code_insert"];
    $district = $_POST["district_insert"];
    $population = $_POST["population_insert"];
    
    if(check_cid($cid) !== true){
        exit("Wrong cid value");
    }

    if(check_name($name) !== true){
        exit("Wrong name");
    }

    if(check_country_code($conn,$country_code) !== true){
        exit("Wrong country code");
    }

    if(check_district($district) !== true){
        exit("Wrong district");
    }

    if(check_population($population) !== true){
        exit("Wrong population");
    }

    $result = get_city_info($conn,$cid);
    echo "Returned rows are: " . mysqli_num_rows($result);
    print_table('city', $result);
    mysqli_free_result($result);
    insert_city($conn,$cid, $name, $country_code, $district, $population);
    $result = get_city_info($conn,$cid);
    echo "Returned rows are: " . mysqli_num_rows($result);
    print_table('city', $result);
    mysqli_free_result($result);
}


if (isset($_POST['remove'])){

    $cid = $_POST["cid_remove"];

    if(check_cid($cid) !== true){
        exit("Wrong cid value");
    }
    $result = get_city_info($conn,$cid);
    echo "Returned rows are: " . mysqli_num_rows($result);
    print_table('city', $result);
    mysqli_free_result($result);
    remove_city($conn,$cid);
    $result = get_city_info($conn,$cid);
    echo "Returned rows are: " . mysqli_num_rows($result);
    print_table('city', $result);
    mysqli_free_result($result);
}


if (isset($_POST['manipulate'])){

    $cid = $_POST["cid_manipulate"];
    $name = $_POST["name_insert"];

    if(check_cid($cid) !== true){
        exit("Wrong cid value");
    }
    get_city_info($conn,$cid);
    manipulate_city($conn,$cid,$name);
    get_city_info($conn,$cid);
}


if(isset($_POST['get differences'])){
    $first_Countr=$_POST["first_country_insert"];
    $second_Countr=$_POST["second_country_insert"];
    
    if(check_country($conn, $first_Countr) !== true){
        exit("Wrong or Invalid Country !!!");
    }
    
    if(check_country($conn, $second_Countr) !== true){
        exit("Wrong or Invalid Country !!!");
    }
    
    
    $result = diff_lang($conn,$first_Countr,$second_Countr);
    echo "Returned rows are: " . mysqli_num_rows($result);
    print_table('countrylanguage', $result);
    mysqli_free_result($result);
    
}

if(isset($_POST['get differences join'])){
    $first_Countr_join=$_POST["first_country_insert_join"];
    $second_Countr_join=$_POST["second_country_insert_join"];
    
    if(check_country($conn, $first_Countr_join) !== true){
        exit("Wrong or Invalid Country !!!");
    }
    
    if(check_country($conn, $second_Countr_join) !== true){
        exit("Wrong or Invalid Country !!!");
    }
    
    $result = diff_lang_join($conn,$first_Countr_join,$second_Countr_join);
    echo "Returned rows are: " . mysqli_num_rows($result);
    print_table('countrylanguage', $result);
    mysqli_free_result($result);
}

 if(isset($_POST['get life expectancy'])){
     
     
     $picked_oper = $_POST["Operation"];
     $country_name_val = $_POST["country_nm"];
     
     if(check_country($conn,$country_name_val) !== true){
         exit("Wrong or Invalid Country !!!");
     }
     
     
     $result = aggregate_countries($conn,$picked_oper,$country_name_val);
     echo "Returned rows are: " . mysqli_num_rows($result);
     print_table('country', $result);
     mysqli_free_result($result);
     
    
     
 }


?>

