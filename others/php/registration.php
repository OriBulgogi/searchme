<?php
    $con = mysqli_connect("localhost", "searchme1", "searchme123", "searchme1");

    mysqli_query($con, 'SET NAMES utf8');
    $response = array();
    $userID = $_POST["id"];
    $userPassword = $_POST["password"];
    $preference = $_POST["preference"];
    $allergy = $_POST["allergy"];

    $statement = mysqli_prepare($con, "SELECT user_id from user where user_id = ?");
    mysqli_stmt_bind_param($statement, "s", $userID);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $get_userId);
    while(mysqli_stmt_fetch($statement)){
      $get_userId;
    }

    if($userID == $get_userId){
      $response["success"] = duplication;
      echo json_encode($response);
    }else{
      $response["success"] = true;
      $statement = mysqli_prepare($con, "INSERT INTO user VALUES (?, ?)"); //DB에 값 저장
      mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
      mysqli_stmt_execute($statement);


      $statement = mysqli_prepare($con, "INSERT INTO preference VALUES (?, $preference[0], $preference[1], $preference[2], $preference[3], $preference[4], $preference[5])"); //DB에 값 저장
      mysqli_stmt_bind_param($statement, "s", $userID);
      mysqli_stmt_execute($statement);

      $statement = mysqli_prepare($con, "INSERT INTO allergy_type VALUES (?, $allergy[0], $allergy[1], $allergy[2], $allergy[3], $allergy[4], $allergy[5], $allergy[6], $allergy[7], $allergy[8])"); //DB에 값 저장
      mysqli_stmt_bind_param($statement, "s", $userID);
      mysqli_stmt_execute($statement);

      echo json_encode($response);
    }

?>
