package com.example.first_task
import java.lang.Integer.parseInt

// работа с интерфейсами
interface Interface1{

    val prop1: Int

    var _propWithImpl: String
    var propWithImpl: String
        get() = _propWithImpl
        set(value) {
            _propWithImpl = value + 1
        }


    fun myInterface1()
    fun interface1WithBody(){
        println("Интерфейс 1 с телом")
    }
}

interface Interface2{
    fun myInterface2()
    fun interface2WithBody(){
        println("Интерфейс 2 с телом")
    }
}

// работа с классами

// класс с полями
// если есть поля, то нужно описывать getter-ы и setter-ы
class Person1(name: String) {

//    поля класса
    private var name = ""
    private var age = -1

//    основной конструктор
    init {
        this.name = name
    }

//    дополнительный конструктор
//    :this(name) - вызов основного конструктора перед началом работы дополнительного
//    ЭТО ОБЯЗАТЕЛЬНО
    constructor(name: String, age: Int) : this(name) {
        this.age = age
    }

//    getter для имени
    fun get_name(): String {
        return this.name
    }

//    getter для возраста
    fun get_age(): Int {
        return this.age
    }

//    setter для имени
    fun set_name(new_name: String) {
        this.name = new_name
    }

//    setter для возраста
    fun set_age(new_age: Int) {
        this.age = new_age
    }

}

// класс со свойствами
// для свойств не надо описывать и создавать getter-ы и setter-ы
class Person2(var name: String, var age: Int = -1) {

//    переопределение getter-а
    val stringRepresentation: String
        get() = "Name: ${name}\nAge: ${age}"

}
// работа с наследованием
open class Parent(p: Int) {

    fun parentMethod() {
        println("Это родительский метод")
    }

    open fun for_override(){
        println("Родительский метод, который перезапишем")
    }
}

class Child(p: Int, var age: Int = -1) : Parent(p) {


    fun childMethod(){
        parentMethod()
        println("А это уже дочерний метод: age = ${age}")
    }

    override fun for_override() {
        super.for_override()
        println("А это уже переписанный метод")
    }
}

// работа с абстрактными классами
abstract class Parent1(p: Int) {

    fun parentMethod() {
        println("Это родительский метод класса Parent1")
    }

    abstract fun abstractMethod()
}

class Child1(p: Int) : Parent1(p), Interface1, Interface2 {
    override fun abstractMethod() {
        println("Переписанный абстрактный метод")
    }

    override val prop1: Int
        get() = 29
    override var _propWithImpl: String = "String"

    override fun myInterface1() {
        println("Интерфейс 1")
    }

    override fun myInterface2() {
        println("Интерфейс 2")
    }
}

fun main() {

//    строковые типы данных
    println("Строковые типы данных")
    val str: String = "Hello World!"
    println(str)
    println(str[11])
    val num = 1
    println("its my $num string")
    println(str + num)
    println("string length ${str.length}")
    println("str with ${'$'}")
    println("----------")

//    числовые типы данных
    val a: Int = 1
//    val - НЕ изменяемая переменная
    var b: Double = 2.0
    b = 3.0
//    var - изменяемая
    val c: Long = 1_000_000
    val a_1: Long = a.toLong()
//    большие числа можно разделять символом '_',
//    чтобы было удобнее смотреть и понимать число

    println("Числовые типы данных")
    println(a)
    println(b)
    println(c)
    println(a_1)
    println("----------")

    println("Сравнение числовых типов данных")
    //    при неравенстве вернет -1
    println(a.compareTo(c))
    // при равенстве вернет
    println(a.compareTo(a_1))


    //    можно сравнивать и с помощью знаков == и !=,
    //    тогда возвращается true/false
    println(a.toLong() == a_1)
    println("----------")

    //    логический тип данных
    val isTrue: Boolean = true
    val isFalse: Boolean = false

    println("Сравнение с логичискими типами данных")
    //    логические операции с булевыми типами данных
    println(isTrue || isFalse)
    println(isTrue && isFalse)
    println(!isTrue)
    println("----------")

    //    массивы разных типов данных
    val str_array: Array<String>
    val int_array: Array<Int>


    var array: Array<String> = arrayOf("Hello", ",", "World", "!")
    println(array[0])
    array[0] = "HELLO"
    println(array[0])
    println("----------")

//    обертка для создания сразу массива с нужным типом данных
    val int_arr: IntArray = intArrayOf(1, 2, 3)
    val double_arr: DoubleArray

//    работа с if
    val if_a = true
    val if_b = 1
    println("Работа с if")
//    синтаксис if-ов
    if (if_a) {
        println("if_a is True")
    }
//    if умеет возвращать значения
    var if_return = if (if_b == 0) {
        println("if_b is 0")
        0
    }
    else {
        println("if_b is ${if_b}")
        1
    }
    println(if_return)
//    сокращенное возвращение if-а
    if_return = if (if_b == 1) 0 else 1
    println(if_return)
    println("-------")

//    работа с аналогом switch/case - when
    println("Работа с when")
    val x = 0
    when(x) {
        1 -> println("x == 1")
        0 -> println("x == 0")
        -1 -> println("x == -1")
        else -> {
            println("x == ${x}")
        }
    }

    val u = 0
    when(u) {
        1, -1 -> println("u == ${u}")
        else -> println("u != 1 and u != -1")
    }

    val s = "1"
    when(u) {
        s.toInt() -> println("s is equal to u")
        else -> println("not equal")
    }

    when(u) {
        in 1..10 -> println("1 <= u <= 10")
        else -> println("not equal")
    }

    println("-------")

//    работа с циклами
    println("Работа с циклами for")
    val for_arr: IntArray = intArrayOf(10, 9, 8, 7)

//    итерация по элементам
    for (item in for_arr) {
        println(item)
    }

//    итерация по индексам
    for (index in for_arr.indices) {
        println("index: ${index}")

    }

    //    итерация в паре по индексу и значению
    for ((index, value) in for_arr.withIndex()) {
        println("${index}: ${value}")

    }

    println("-------")

    println("Работа с циклами while")

    var i = 0
    while (i < for_arr.size - 1) {
        println("${i}: ${for_arr[i]}")
        i++
    }

    println("-------")

//    работа с классами
    println("Работа с классами")

    val person1 = Person1("Diana")
    val person1_1 = Person1("Maxim", 21)
    println("проверка работы getter-ов")
    println("person_1: ${person1.get_name()}")
    println("person1_1: ${person1_1.get_name()} + ${person1_1.get_age()}")
    println("Проверка работы setter-а")
    person1_1.set_name("MAXIM")
    println(person1_1.get_name())
    val person2 = Person2("John")
    println("Проверка работы переопределения getter-a")
    println("${person2.name}: ${person2.age}")
    println(person2.stringRepresentation)

    println("-------")

//    работа с наследованием
    println("Работа с наследованием")
    val child = Child(12)
    child.childMethod()
    child.for_override()
    println("-------")

//    работа с абстрактными классами
    println("Работа с абстрактными классами")
    val child1 = Child1(12)
    child1.abstractMethod()
    println("-------")

//    работа с интерфейсами
    println("Работа с интерфейсами")
    val child1_2 = Child1(12)
    child1_2.myInterface1()
    child1_2.myInterface2()
    child1_2.interface1WithBody()
    child1_2.interface2WithBody()
    println( child1_2.propWithImpl)
    println( child1_2._propWithImpl)
    println("-------")

//    работа с Null-безопасностью
    println("работа с Null-безопасностью")

    var a_1_1: String = "String"
    a_1_1 = "null"
//    проверка if-ом на null
    var l = if (a_1_1 != null) a_1_1.length else -1
//    безопасный вызов
    var a_1_2: String? = "String"
    a_1_2 = "null"
    var l_1  = a_1_2?.length
//    вызов с исключением
    a_1_2!!.compareTo("1")
//    безопасный вызов с обработкой if
    var a_1_3: String? = "String"
    a_1_3 = "null"
    var l_2  = a_1_3?.length ?: -1



}