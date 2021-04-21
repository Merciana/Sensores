package com.example.sensorproximacao

data class SensoresDta(var x:Float, var y:Float, var z:Float, var timestamp:Long){
    constructor(x:Float, timestamp: Long) : this(x, 0.0f, 0.0f, timestamp){}
}
