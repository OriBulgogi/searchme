<?php
    $con = mysqli_connect("localhost", "searchme1", "searchme123", "searchme1");

    mysqli_query($con,'SET NAMES utf8');

    $userID = $_POST["id"];
    $userPassword = $_POST["password"];
    //
    $query ="SELECT * FROM user WHERE user_id = ? AND password = ?";
    $statement = mysqli_prepare($con, $query);

    mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID);
    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["userID"] = $userID;
      }

    $query ="SELECT * FROM allergy_type WHERE user_id= ?";
    $statement = mysqli_prepare($con, $query);

    mysqli_stmt_bind_param($statement, "s", $userID);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID,
    $a_peanut, $a_walnut, $a_salmon, $a_shrimp, $a_wheat, $a_milk, $a_crusta, $a_peach, $a_lacquer);


    while(mysqli_stmt_fetch($statement)) {
        $response["a_peanut"] = $a_peanut;
        $response["a_welnut"] = $a_walnut;
        $response["a_salmon"] = $a_salmon;
        $response["a_shrimp"] = $a_shrimp;
        $response["a_wheat"] = $a_wheat;
        $response["a_milk"] = $a_milk;
        $response["a_crusta"] = $a_crusta;
        $response["a_peach"] = $a_peach;
        $response["a_lacquer"] = $a_lacquer;
    }

    $query ="SELECT * FROM preference WHERE user_id= ?";
    $statement = mysqli_prepare($con, $query);

    mysqli_stmt_bind_param($statement, "s", $userID);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID,
    $p_boil, $p_fry, $p_roast, $p_steaming, $p_stirfry, $p_etc);


    while(mysqli_stmt_fetch($statement)) {
        $response["p_boil"] = $p_boil;
        $response["p_fry"] = $p_fry;
        $response["p_roast"] = $p_roast;
        $response["p_steaming"] = $p_steaming;
        $response["p_stirfry"] = $p_stirfry;
        $response["p_etc"] = $p_etc;
    }

    echo json_encode($response);

?>
