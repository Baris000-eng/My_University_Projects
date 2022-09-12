<?php


function check_cid($cid){
    return is_numeric($cid);
}

function check_name($name){
    return ctype_alpha($name) and (strlen($name) < 35);
}

function check_country_code($conn,$country_code){
    return is_contains($conn,$country_code, "CountryCode", "city") and ctype_alpha($country_code) and (strlen($str) < 3);
}

function check_district($district){
    return ctype_alpha($district) and (strlen($district) < 20);
}

function check_population($population){
    return is_numeric($population);
}

/////////////My implementation for the function checking the validity of the country////////
function check_country($conn, $country){
    $sql = "SELECT cnt.Name FROM country cnt WHERE cnt.Name = '$country';"; //query for obtaining all of the country names
    return $result = mysqli_query($conn,$sql) and (strlen($country)>0) and ctype_alpha($country) and (strlen($country)<20000);
    ///checking whether the type of the country variable is a string, 
    ///the length of the string is bigger than 0, and the length of the string is smaller than 20000.
    //checking also whether the query called sql_str is executed sucessfully
    ///Here, 20000 is a made-up upper boundary number.
}
/////////////My implementation for the function checking the validity of the country////////

function get_city_info($conn,$cid){

    if ($result = mysqli_query($conn, "SELECT * FROM city WHERE ID=" . $cid )) {
        return $result;
    }
}



//////////////Q1//////////////////////////////////////////////////////////////////////////
/////My implementation of the is_contains function for the question-1/////////
function is_contains($conn,$string, $needle, $table_name){
    $is_contains = False;
    $sql_str = "SELECT * FROM $table_name WHERE $needle = '$string' ;";
    if($result = mysqli_query($conn,$sql_str)){
        $total_number_of_rows = mysqli_num_rows($result);
        if(is_numeric($total_number_of_rows)) {
            if($total_number_of_rows<=0){
                echo "The number of rows cannot be smaller than or equal to 0 !!!";
                $is_contains = False;
                return $is_contains;
            } else {
                echo "Correct and valid number of rows !!!";
                $is_contains = True;
                return $is_contains;
            }
        } else {
            echo "No numeric value for the row number !!!";
            $is_contains = False;
            return $is_contains;
        }
       
    } else {
        echo "Failure in query execution!!";
        $is_contains = False;
        return $is_contains;
    }
    
    
    return $is_contains;
}
/////My implementation of the is_contains function for the question-1/////////
//////////////Q1//////////////////////////////////////////////////////////////////////////



////////////Q2//////////////////////////////////////////////////////////////////////////////////////
/////////My implementation of the diff_lang function for the question-2 ////////////////////////////
function diff_lang($conn, $country1, $country2){
    $result = Null;
    $sql = "SELECT clan.Language FROM countrylanguage clan,country cnt WHERE clan.CountryCode=cnt.Code AND clan.Language IN (SELECT clan.Language FROM country cnt, countrylanguage clan
    WHERE cnt.Name = '$country1' AND clan.CountryCode=cnt.Code) AND clan.Language NOT IN (SELECT clan.Language FROM country cnt, countrylanguage clan
    WHERE cnt.Name = '$country2' AND clan.CountryCode=cnt.Code) AND cnt.Name IN (SELECT cnt.Name FROM country cnt WHERE cnt.Name='$country1');"; //the nested sql query I used
     if($result = mysqli_query($conn,$sql)){
        echo "Success !!!";
        return $result; //successful query execution
     } else {
        echo "Failiure !!!";
        return Null;    //problem in the query execution
     }

     return Null;
}
/////////My implementation of the diff_lang function for the question-2 ////////////////////////////
////////////Q2//////////////////////////////////////////////////////////////////////////////////////






////////////Q3 comments/////////////////////////////////////////////////////////////////////
//Q3, Languages spoken by country1
//SELECT clan.Language
//FROM countrylanguage clan
//LEFT JOIN country cnt
//ON cnt.Code=clan.CountryCode AND cnt.Name='country1'
//INNER JOIN country cnt2
//ON cnt.Code=cnt2.Code;

//Q3, Languages spoken by country2
//SELECT cl.Language
//FROM countrylanguage cl
//LEFT JOIN country ct
//ON ct.Code=cl.CountryCode AND ct.Name='country2'
//WHERE ct.Code IS NOT NULL;

//Q3/////////////////////////////////////////////////////////////////////////////////////////////////
/////////My implementation of the diff_lang_join function for the question-3 ////////////////////////////
function diff_lang_join($conn, $country1, $country2){

    $result = Null;
    $sql = "SELECT clan.Language FROM countrylanguage clan LEFT JOIN country cnt
    ON cnt.Code=clan.CountryCode AND cnt.Name='$country1' LEFT JOIN country cnt3
    ON cnt3.Code=clan.CountryCode AND cnt3.Name='$country2'WHERE cnt3.Code IS NULL;";
    if($result = mysqli_query($conn,$sql)){
        echo "Successful query execution !!!";
        return $result;
    } else {
        echo "Problem in query execution !!!";
        return Null;
    }


    return Null;

    ########
    
}
/////////My implementation of the diff_lang_join function for the question-3 ////////////////////////////
//Q3/////////////////////////////////////////////////////////////////////////////////////////////////



////////////Q4//////////////////////////////////////////////
///////////My implementation for the function called "aggregate_countries"////
function aggregate_countries($conn,$agg_type, $country_name){

    $result = Null;
    
    if(strcasecmp($agg_type,"MIN") == 0){
        $sql = 
        "SELECT cnt.Name,cnt.LifeExpectancy,cnt.GovernmentForm,clan.Language
        FROM country cnt,countrylanguage clan
        WHERE clan.CountryCode=cnt.Code AND 
        cnt.LifeExpectancy < 
        (SELECT cnt.LifeExpectancy FROM country cnt WHERE cnt.Name='$country_name')
        AND cnt.LifeExpectancy>(SELECT MIN(cnt.LifeExpectancy) FROM country cnt);";
        if($result = mysqli_query($conn,$sql)){
            return $result;
        } else {
            return Null;
        }
    } else if(strcasecmp($agg_type,"AVG") == 0){
        $sql =  "SELECT cnt.Name,cnt.LifeExpectancy,cnt.GovernmentForm,clan.Language
                 FROM country cnt,countrylanguage clan
                 WHERE clan.CountryCode=cnt.Code AND cnt.LifeExpectancy < (SELECT cnt.LifeExpectancy FROM country cnt WHERE cnt.Name='$country_name') 
                 AND cnt.LifeExpectancy>(SELECT AVG(cnt.LifeExpectancy) FROM country cnt);";
        if($result = mysqli_query($conn,$sql)){
            print_table('city', $result);
            return $result;
        } else {
            return Null;
        }
    } else {
        echo "Invalid Choice !!!";
        return Null;
    }
    ########
    #Please enter your code here

    ########
    return $result;
}
////////////Q4//////////////////////////////////////////////
///////////My implementation for the function called "aggregate_countries"////



////////My implementation for the function called "find_min_max_continent"
///////For the Part-A of the Question-5//////////////////////
function find_min_max_continent($conn){
    $result = Null;
    $sql = "SELECT cntry1.Name,cntry1.Continent,cntry1.LifeExpectancy FROM country cntry1 WHERE cntry1.LifeExpectancy IN 
    (SELECT MAX(cntry2.LifeExpectancy) FROM country cntry2 WHERE cntry1.Continent=cntry2.Continent) UNION 
    SELECT cntry3.Name,cntry3.Continent,cntry3.LifeExpectancy FROM country cntry3 WHERE cntry3.LifeExpectancy IN 
    (SELECT MIN(cntry4.LifeExpectancy) FROM country cntry4 WHERE cntry3.Continent=cntry4.Continent);"; //The SQL query I write for this part
    if($result = mysqli_query($conn,$sql)){
       echo "Successful query !!!";
       return $result;
    } else {
        echo "Unsuccessful query !!!";
        return Null;
    }

    return Null;
}
///////For the Part-A of the Question-5////////////////////
///////My implementation for the function called "find_min_max_continent"




/////////My implementation for the function called "find_country_language" 
//////which is in the Part-B of the Question-5///////////////////////////
function find_country_language($conn,$percentage,$language) {
    $result = Null;
    $sql = "SELECT cnt.Name,clan.Language,clan.Percentage FROM country cnt,countrylanguage clan WHERE 
    clan.CountryCode=cnt.Code AND clan.Percentage>$percentage AND clan.Language = '$language' ";
    if($result=mysqli_query($conn,$sql)){
        echo "Successful query !!!";
        return $result;
    } else {
        echo "Unsuccessful query !!!";
        return Null;
    }
    return Null;
}
/////////My implementation for the function called "find_country_language" 
//////which is in the Part-B of the Question-5/////////////////////////////




/////My implementation for the function called "find_country_count" 
//////which is in the Part-C of the Question-5////////////////////
function find_country_count($conn,$amount){
    $result=null;
    $sql = "SELECT cnt.Name,cnt.LifeExpectancy,cnt.Continent FROM country cnt 
    WHERE cnt.LifeExpectancy = (SELECT MAX(c1.LifeExpectancy) FROM country c1 
    WHERE cnt.Continent=c1.Continent AND c1.Code IN (SELECT cnt.Code FROM 
    country cnt JOIN city cty ON cty.CountryCode=cnt.Code GROUP BY cnt.Code 
    HAVING COUNT(cnt.Code)>$amount));"; //sql query for part-c q5
    if($result=mysqli_query($conn,$sql)){
       return $result;
    } else {
        return null;
    }

    return null;


}
/////My implementation for the function called "find_country_count" 
//////which is in the Part-C of the Question-5////////////////////







function print_table($table_name, $result){

    if ($table_name === 'city'){ //city table

        ?><br>

        <table border='1'>

        <tr>

        <th>ID</th>

        <th>Name</th>

        <th>Country Code</th>

        <th>District</th>

        <th>Population</th>

        </tr>

        <?php


        foreach($result as $row){

            echo "<tr>";

            echo "<td>" . $row['ID'] . "</td>";

            echo "<td>" . $row['Name'] . "</td>";

            echo "<td>" . $row['CountryCode'] . "</td>";

            echo "<td>" . $row['District'] . "</td>";

            echo "<td>" . $row['Population'] . "</td>";

            echo "</tr>";
        }

        echo "</table>";
    } else if ($table_name === 'country'){ //country table
        ?><br>

        <table border='1'>

        <tr>

        <th>Code</th>

        <th>Name</th>

        <th>Continent</th>

        <th>Region</th>

        <th>SurfaceArea</th>
        
        <th>IndepYear</th>
        
        <th>Population</th>
        
        <th>LifeExpectancy</th>
        
        <th>GNP</th>
        
        <th>GNPOld</th>
        
        <th>LocalName</th>
        
        <th>GovernmentForm</th>
        
        <th>HeadOfState</th>
        
        <th>Capital</th>
        
        <th>Code2</th>
        
        </tr>

        <?php


        foreach($result as $row){

            echo "<tr>";

            echo "<td>" . $row['Code'] . "</td>";

            echo "<td>" . $row['Name'] . "</td>";

            echo "<td>" . $row['Continent'] . "</td>";

            echo "<td>" . $row['Region'] . "</td>";

            echo "<td>" . $row['SurfaceArea'] . "</td>";
            
            echo "<td>" . $row['IndepYear'] . "</td>";

            echo "<td>" . $row['Population'] . "</td>";

            echo "<td>" . $row['LifeExpectancy'] . "</td>";

            echo "<td>" . $row['GNP'] . "</td>";

            echo "<td>" . $row['GNPOld'] . "</td>";
            
            echo "<td>" . $row['LocalName'] . "</td>";

            echo "<td>" . $row['GovernmentForm'] . "</td>";

            echo "<td>" . $row['HeadOfState'] . "</td>";

            echo "<td>" . $row['Capital'] . "</td>";
            
            echo "<td>" . $row['Code2'] . "</td>";
            
            echo "</tr>";
    }
        echo "</table>";
        
    } else if($table_name === 'countrylanguage'){ //countrylanguage table
        ?><br>

        <table border='1'>

        <tr>

        <th>CountryCode</th>

        <th>Language</th>

        <th>isOfficial</th>

        <th>Percentage</th>
        
        </tr>

        <?php
        
        foreach($result as $row){

            echo "<tr>";

            echo "<td>" . $row['CountryCode'] . "</td>";

            echo "<td>" . $row['Language'] . "</td>";

            echo "<td>" . $row['isOfficial'] . "</td>";

            echo "<td>" . $row['Percentage'] . "</td>";
            
            echo "</tr>";
        }
        echo "</table>";
    }

}

function insert_city($conn,$cid, $name, $country_code, $district, $population){


    $sql = "INSERT INTO city(ID, Name, CountryCode, District, Population) VALUES('$cid', '$name', '$country_code', '$district','$population');";
    if ($conn->query($sql) === TRUE) { #We used different function to run our query.
        echo "<br>Record updated successfully<br>";
    } else {
        echo "<br>Error updating record: " . $conn->error . "<br>";
    }
}

function remove_city($conn,$cid){
    $sql = "DELETE FROM city WHERE ID='$cid'";
    if ($conn->query($sql) === TRUE) {
        echo "Record updated successfully";
    } else {
        echo "Error updating record: " . $conn->error;
    }

}

function manipulate_city($conn,$cid,$name){

    $sql = "UPDATE city SET Name='$name' WHERE ID='$cid'" ;
    if ($conn->query($sql) === TRUE) {
        echo "Record updated successfully";
    } else {
        echo "Error updating record: " . $conn->error;
    }

}
