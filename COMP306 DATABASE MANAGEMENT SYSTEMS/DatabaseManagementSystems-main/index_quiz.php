<!--
    COMP306-

-->
<html>
<head></head>
<body>

    <!--Insert operation -->
    <form action='result_quiz.php' method='post'>
        <label>CID:         </label><input type='number' name='cid_insert' /><br/>
        <label>Name:        </label><input type='text' name='name_insert' /><br/>
        <label>CountryCode: </label><input type='text' name='country_code_insert' /><br/>
        <label>District:    </label><input type='text' name='district_insert' /><br/>
        <label>Population:  </label><input type='text' name='population_insert' /><br/>
        <input name="insert", value='Insert' type='submit'/></p>
    </form>
    <hr>

    <!--Remove operation -->
    <form action='result_quiz.php' method='post'>
        <label>CID:</label><input type='number' name='cid_remove' /><input name="remove", value='Remove' type='submit'/></p>
    </form>
    <hr>

    <!-- Manipulation operation -->
    <form action='result_quiz.php' method='post'>
        <label>Name: </label><input type='text' name='name_insert' /><br/>
        <label>CID:</label><input type='number' name='cid_manipulate' /><input name="manipulate", value='Manipulate' type='submit'/></p>
    </form>
    <hr>

    <!-- No GUI necessary for Question 1 -->


    
    <!--The GUI that I have written to the Question-2-->
    <!-- Please write Question 2 GUI Here -->
    <form action='result_quiz.php' method='post'>
        <label>First Country:</label><input type='text' name='first_country_insert'/><br/>
        <label>Second Country:</label><input type='text' name='second_country_insert'/><input name="get differences", value='Get Differences' type='submit'/></p>
    </form>
    <hr>
    <!--The GUI that I have written to the Question-2-->



    <!--The GUI that I have written to the Question-3-->
    <!-- Please write Question 3 GUI Here -->
    <form action='result_quiz.php' method='post'>
        <label>First Country: </label><input type='text' name='first_country_insert_join'/><br/>
        <label>Second Country:</label><input type='text' name='second_country_insert_join'/><input name="get differences join", value='Get Differences' type='submit'/></p>
    </form>
    <hr>
    <!--The GUI that I have written to the Question-3-->



<!--The GUI that I have written to the Question-4-->
<!-- Please write Question 4 GUI Here -->
<form action='result_quiz.php' method='post'>
<label>Choose an operation type:</label>
<select name="Operation" id="operType">
   <option value="MIN">MIN</option>
   <option value="AVG">AVG</option>
</select>
<br/>
    <label>Country Name:</label><input type='text' name='country_nm'/> <br/> <input name="get life expectancy", value='Get Life Expectancy' type='submit'/></p>
</form>
<hr>

<!--The GUI that I have written to the Question-4-->




<!-- No GUI necessary Question 5 -->


</body>
</html>
