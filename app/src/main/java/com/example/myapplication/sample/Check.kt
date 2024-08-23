package com.example.myapplication.sample

 open class Check1() {
   open fun testFun() {}
 }
 class Abc1: Check1() {
    override fun testFun() {
        super.testFun()
    }
}
/*------------------------------- */
abstract class Check2() {
    abstract fun testFun()
}
 class Abc2: Check2() {
    override fun testFun() {

    }
}




fun main() {


}