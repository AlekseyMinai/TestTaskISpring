package com.alesno.testtaskispring.model.service

class Test {


}
fun <T: Int>Some<T>.sum(): Int {
    return i+k
}
fun main(arg: Array<String>){
    print(Some<Int>(1, 3).sum())
}

class Some<T: Int>(val i: T, val k: T) {
    fun multiply(): Int {
        return i*k
    }
}