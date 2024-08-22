package com.example.demoapp

class Rectangle(val width: Int, val height: Int) {
    init{
        count++
    }
    fun getArea(): Int{
        return width * height
    }
    companion object{
        private var count = 0
        fun numberOfRectangle(): Int{
            return count
        }
    }
}