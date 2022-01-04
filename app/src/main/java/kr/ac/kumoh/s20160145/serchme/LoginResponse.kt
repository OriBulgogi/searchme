package kr.ac.kumoh.s20160145.serchme
import java.io.Serializable

data class LoginResponse(
    val success: String,
    val userID: String,

    val a_peanut : Int,
    val a_welnut : Int,
    val a_salmon : Int,
    val a_shrimp : Int,
    val a_wheat : Int,
    val a_milk : Int,
    val a_crusta : Int,
    val a_peach : Int,
    val a_lacquer : Int,

    val p_boil : Int,
    val p_fry : Int,
    val p_roast : Int,
    val p_steaming : Int,
    val p_stirfry : Int,
    val p_etc : Int


    ) : Serializable
