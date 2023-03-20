# Основы Kotlin

## Оглавление

- [Введение](#введение)
- [Основные типы](#основные-типы)
  - [Числа](#числа)
  - [Преобразования](#преобразования)
  - [Символы](#символы)
  - [Логический тип](#логический-тип)
  - [Массивы](#массивы)
  - [Строки](#строки)
  - [Строковые шаблоны](#строковые-шаблоны)
- [Пакеты](#пакеты)
- [val и var](#val-и-var)
- [Управляющие конструкции](#управляющие-конструкции)
  - [Условное выражение `if`](#условное-выражение-if)
  - [Условное выражение `when`](#условное-выражение-when)
  - [Цикл `for`](#цикл-for)
  - [Цикл `while`](#цикл-while)
- [Классы](#классы)
  - [Конструкторы](#конструкторы)
  - [Создание эксемпляров классов](#создание-эксемпляров-классов)
  - [Наследование](#наследование)
  - [Переопределение членов класса](#переопределение-членов-класса)
  - [Абстрактные классы](#абстрактные-классы)
  - [Свойства и поля](#свойства-и-поля)
  - [Классы данных](#классы-данных)
- [Интерфейсы](#интерфейсы)
- [Null-безопасность](#null-безопасность)
  - [Nullable типы и Non-Null типы](#nullable-типы-и-non-null-типы)
  - [Проверка на null](#проверка-на-null)
  - [Безопасные вызовы](#безопасные-вызовы)
  - [Элвис-оператор](#элвис-оператор)
  - [Оператор `!!`](#оператор-)
- [Создать проект в IntelliJ IDEA](#создать-проект-в-intellij-idea)

## Введение

Документация на Kotlin: https://kotlinlang.org/docs/reference/basic-syntax.html.

Kotlin — это относительно молодой язык от российской компании JetBrains. Появился он в 2011 году. 

Для нас он интересен тем, что на конференции Google I/O 2017 команда разработчиков Android сообщила, что Kotlin получил официальную поддержку для разработки Android-приложений. Сейчас Kotlin становится основным языком для разработки под Android. Именно его поддержке уделяется наибольшее внимание.

Основные возможности и преимущества Kotlin:

* Компилируется в байт-код JVM, работает на виртуальной машине Java.
* Программы могут использовать все существующие Java-фреймворки и библиотеки.
* Kotlin можно интегрировать с Maven, Gradle и другими системами сборки.
* Язык очень прост для изучения.
* Исходный код открыт.
* Легко читаемый лаконичный синтаксис.
* В популярной среде разработки IntelliJ IDEA доступна автоматическая конвертация Java-кода в Kotlin и наоборот.

IntelliJ IDEA — среда разработки, созданная компанией JetBrains. Сейчас повсеместно используется для разработки Java- и Kotlin-приложений и не только.

## Основные типы

В Kotlin не существует примитивных типов в привычном их понимании. В Kotlin всё является объектом, в том смысле, что пользователь может вызвать метод или получить доступ к свойству любой переменной. Некоторые типы являются встроенными (подобные примитивным типам в Java), хотя для пользователя они могут выглядеть как обычные классы.

### Числа

Kotlin работает с численными типами так же как и Java.
Для представления чисел в Kotlin используются следующие встроенные типы:
* Double — 64 бита.
* Float	— 32 бита.
* Long — 64 бита.
* Int — 32 бита.
* Short — 16 бит.
* Byte — 8 бит.

Числовые константы описываются следующим образом.
Десятичные числа: `123`.
Long-тип обозначаются заглавной буквой `L`: `123L`.
Шестнадцатеричные числа имеют приставку `0x`: `0x0F`.
Двоичные числа имеют приставку `0b`: `0b00001011`.

Чтобы сделать числовые константы более удобными к чтению, можно использовать нижние подчеркивания для разделения разрядов.
Например:

```kotlin
val oneMillion = 1_000_000
val creditCardNumber = 1234_5678_9012_3456L
val socialSecurityNumber = 999_99_9999L
val hexBytes = 0xFF_EC_DE_5E
val bytes = 0b11010010_01101001_10010100_10010010
```

### Преобразования

Меньшие типы являются подтипами больших типов.

```kotlin
// Возможный код, который на самом деле не скомпилируется:
val a: Int = 1 // "Int (java.lang.Integer)
val b: Long = a // Неявное преобразование возвращает Long (java.lang.Long)
println(a == b) // Данное выражение выведет "false" т.к. метод equals() типа Long предполагает, что вторая часть выражения также имеет тип Long
```

Для выполнения корректного сравнения по значению необходимо выполнять преобразования типов:

```kotlin
a.toLong() // Приведение Int к Long
// или
b.toInt() // Приведение Long к Int
```

Каждый численный тип поддерживает следующие преобразования:

* `toByte(): Byte`
* `toShort(): Short`
* `toInt(): Int`
* `toLong(): Long`
* `toFloat(): Float`
* `toDouble(): Double`

При выполнении арифметических действий с разными типами выполняется преобразование результата к наибольшему типу.

```kotlin
val l = 1L + 3 // Long + Int => Long
```

### Символы

Символы в Kotlin представлены типом `Char`. Они не являются числами, однако могут быть преобразованы к `Int`.

Символьные литералы записываются в одинарных кавычках: '1', '\n', '\uFF00'.
Мы можем явно привести символ в число типа Int:

```kotlin
val c = 'A'
val num: Int = c.toInt() // Явное преобразование в число
println(num)
```

### Логический тип

Тип `Boolean` представляет логический тип данных и принимает два значения: `true` и `false`.

Встроенные действия над логическими переменными те же, что и в Java:

* `||` – логическое ИЛИ,
* `&&` – логическое И,
* `!` - отрицание.

### Массивы

Массивы в Kotlin представлены классом `Array<T>`. Массивы обладают функциями `get` и `set` (которые обозначаются [] согласно соглашению о перегрузке операторов), и свойством `size`, а также несколькими полезными встроенными функциями.

Для создания массива можно использовать библиотечную функцию `arrayOf()`, которой в качестве аргумента передаются элементы массива, т.е. выполнение `arrayOf(1, 2, 3)` создаёт массив `[1, 2, 3]`. Также существует библиотечная функция `arrayOfNulls()`. Она может быть использована для создания массива, заполненного значениями `null`.

```kotlin
val arr: Array<Int> = arrayOf(1, 2, 3)
println(arr.contentToString()) // Вывод содержимого массива в лог
```

Также в Kotlin есть особые классы для представления массивов примитивных типов без дополнительных затрат на оборачивание: `ByteArray`, `ShortArray`, `IntArray`, `Boolean` и т.д. Данные классы не наследуют класс `Array`, хотя и обладают тем же набором методов и свойств. У каждого из них есть соответствующая фабричная функция:

```kotlin
val x: IntArray = intArrayOf(1, 2, 3)
x[0] = x[1] + x[2] // [5, 2, 3]
```

### Строки

Строки в Kotlin представлены типом `String`. Строки являются неизменяемыми. Строки состоят из символов, которые могут быть получены с помощью операции индексирования: `s[i]`.

### Строковые шаблоны

Строки могут содержать шаблонные выражения, т.е. участки кода, которые выполняются, а полученный результат встраивается в строку. Шаблон начинается со знака доллара ($) и состоит либо из простого имени переменной, либо из произвольного выражения в фигурных скобках.

Примеры:

```kotlin
val i = 10
val s = "i = $i" // Строка "i = 10"
```

```kotlin
val s = "abc"
val str = "$s.length is ${s.length}" // Строка "abc.length is 3"
```

При необходимости использования символ `$` может быть представлен с помощью следующего синтаксиса:

```kotlin
val price = "${'$'}9.99"
```

## Пакеты

Файл с исходным кодом может начинаться с объявления пакета:

```kotlin
package org.example.kotlinlearn

fun someFunction() {}

class SomeClass {}

// ...
```

Всё содержимое файла с исходниками (например, классы и функции) располагается в объявленном пакете. Таким образом, в приведённом выше примере полное имя функции `someFunction()` будет `org.example.kotlinlearn.someFunction`, а полное имя класса `SomeClass` - `org.example.kotlinlearn.SomeClass`.

Каждый файл может содержать свои собственные объявления импорта.
Можно импортировать одно имя, например:

```kotlin
import org.example.kotlinlearn.SomeClass   
// теперь SomeClass можно использовать без указания пакета
```

или доступное содержимое пространства имён (пакет, класс, объект и т.д.):

```kotlin
import org.example.kotlinlearn.*
// всё в 'org.example.kotlinlearn' становится доступно без указания пакета
```

## val и var

Переменные в Kotlin бывают изменяемые и неизменяемые.
Неизменяемые (только для чтения) переменные объявляются с помощью ключевого слова `val`:

```kotlin
val a: Int = 1
val b = 1   // Тип Int выведен автоматически
val c: Int  // Тип обязателен, когда значение не инициализируется
c = 1       // Последующее присвоение
c = 2       // Изменение невозможно, ошибка при компиляции
```

Изменяемые переменные объявляются с помощью ключевого слова `var`:

```kotlin
var x = 5 // Тип Int выведен автоматически
x += 1
```

## Управляющие конструкции

### Условное выражение `if`

В языке Kotlin `if` является выражением, т.е. оно возвращает значение. Это позволяет отказаться от тернарного оператора (условие ? условие истинно : условие ложно), поскольку выражению `if` может его заменить.

```kotlin
// обычное использование 
var max = a 
if (a < b) 
  max = b 
 
// с блоком else 
var max: Int
if (a > b) 
  max = a 
else 
  max = b 
 
// в виде выражения 
val max = if (a > b) a else b
```

"Ветви" выражения `if` могут содержать несколько строк кода, при этом последнее выражение является значением блока и может быть возвращено:

```kotlin
val max = if (a > b) { 
    println("возвращаем a") 
    a 
} 
else { 
    println("возвращаем b") 
    b 
}
println(max) // Напечатает "возвращаем a", если a > b, и "возвращаем b", если a <= b.
```

Если конструкция `if` используется в качестве выражения (например, возвращая его значение или присваивая его переменной), то использование ветки `else` обязательно.

### Условное выражение `when`

Ключевое слово `when` призвано заменить оператор `switch`, присутствующий в C-подобных языках. В простейшем виде его использование выглядит так:

```kotlin
when (x) {
    1 -> println("x == 1")
    2 -> println("x == 2")
    else -> {
        println("x is neither 1 nor 2")
    }
}
```

Значение ветки `else` вычисляется в том случае, когда ни одно из условий в других ветках не удовлетворено.

Если для нескольких значений выполняется одно и то же действие, то условия можно перечислять в одной ветке через запятую:

```kotlin
when (x) {
    0, 1 -> print("x == 0 or x == 1")
    else -> print("otherwise")
}
```

Помимо констант в ветках можно использовать произвольные выражения:

```kotlin
when (x) {
    parseInt(s) -> println("s encodes x")
    else -> println("s does not encode x")
}
```

Также можно проверять вхождение аргумента в интервал `in` или `!in` или его наличие в коллекции:

```kotlin
when (x) {
    in 1..10 -> println("x is in the range")
    in validNumbers -> println("x is valid")
    !in 10..20 -> println("x is outside the range")
    else -> println("none of the above")
}
```

Иногда выражение `when` удобно использовать вместо цепочки условий вида `if-else if`. При отстутствии аргумента, условия работают как простые логические выражения, а тело ветки выполняется при его истинности. Однако злоупотреблять таким решением не стоит, цепочки `if-else if` чаще бывают понятнее для чтения, особенно, когда условия в них достаточно длинные.

```kotlin
when {
    x.isOdd() -> print("x is odd")
    x.isEven() -> print("x is even")
    else -> print("x is funny")
}
```

### Цикл `for`

Цикл `for` обеспечивает перебор всех значений коллекции.

```kotlin
for (item in collection) {
    print(item)
}
```

Если при проходе по массиву или списку требуется порядковый номер элемента, необходимо использовать свойство `indices`:

```kotlin
for (i in array.indices) {
    print(array[i])
}
```

Также можно использовать библиотечную функцию `withIndex()` для получения индекса и значения по этому индексу одновременно:

```kotlin
for ((index, value) in array.withIndex()) {
    println("the element at $index is $value")
}
```

### Цикл `while`

Циклы `while` и `do..while` абсолютно аналогичны таковым же в Java:

```kotlin
// цикл while
while (x > 0) {
    x--
}

// цикл do..while
do {
    val y = retrieveData()
} while (y != null) // y здесь доступно!
```

## Классы

Классы в Kotlin, как и в Java, объявляются с помощью ключевого слова `class`:

```kotlin
class Person {

}
```

Тело класса является необязательным: если у класса нет тела, фигурные скобки могут быть опущены:

```kotlin
class Customer
```

### Конструкторы

Класс в Kotlin может иметь основной конструктор (**primary constructor**) и один или более дополнительных конструкторов (**secondary constructor**). Основной конструктор является частью заголовка класса, его объявление идёт сразу после имени класса (и необязательных параметров):

```kotlin
class Customer constructor(name: String)
```

Ключевое слово `constructor` может быть опущено:

```
class Customer(name: String)
```

Основной конструктор не содержит в себе исполняемого кода. Код для инициализации класса принято помещать в соответствующий блок (**initializers blocks**), который помечается словом `init`. По сути блок `init` содержит код конструктора в привычном его виде. Параметры основного конструктора могут использоваться в инициализирующем блоке.

```kotlin
class Customer(name: String) {
    init {
        println("Customer initialized with value $name")
    }
}
```

В действительности, для объявления и инициализации свойств основного конструктора в Kotlin есть лаконичное синтаксическое решение:

```kotlin
class Person(val firstName: String, val lastName: String, var age: Int) {
  // Далее в теле класса свойства firstName, lastName и age доступны 
  // и проинициализированы значениями, переданными в конструктор класса Person
}
```

Для объявления дополнительных (**secondary**) конструкторов используется ключевое слово `constructor`:

```kotlin
class Customer(name: String) {

    constructor(name: String, cash: Int) : this(name) {
        println("Customer initialized with name $name and cash $cash")
    }
}
```

Здесь класс `Customer` имеет два конструктора: основной, принимающий на вход параметр `name`; и дополнительный, принимающий на вход параметры `name` и `cash`. Если класс имеет основной конструктор с параметром, то каждый его дополнительный конструктор должен делегировать вызов основному с передачей параметров. Именно для этого после сигнатуры конструктора после двоеточия указывается ключевое слово `this` с передачей параметров. Таким образом сначала будет вызван основной конструктор, а после него дополнительный.

### Создание эксемпляров классов

Для создания экземпляра класса конструктор вызывается так, как если бы он был обычной функцией (без ключевого слова `new`, как в Java):

```kotlin
val customer = Customer("Joe Smith")
```

### Наследование

Для явного объявления суперкласса мы помещаем его имя за знаком двоеточия в объявлении класса:

```kotlin
open class Base(p: Int) {
    fun baseFunc() {}
}

class Derived(p: Int) : Base(p) {
    fun derivedFunc() {
        baseFunc()
    }
}
```

В данном примере класс `Derived` наследуется от класса `Base` и наследует его функцию `baseFunc()`.

Ключевое слово `open` является противоположностью слову `final` в Java: оно позволяет другим классам наследоваться от данного. По умолчанию, все классы в Kotlin имеют статус `final`.

Если у класса нет основного (**primary**) конструктора, тогда каждый дополнительный (**secondary**) конструктор должен включать в себя инициализацию базового типа с помощью ключевого слова `super`. Примечательно, что любые дополнительные конструкторы могут ссылаться на разные конструкторы базового типа:

```kotlin
open class View(ctx: Context) {

  constructor(ctx: Context, attrs: AttributeSet) : this(ctx) {
    this.attrs = attrs
  }
}

class MyView : View {
    constructor(ctx: Context) : super(ctx) {
    }
    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs) {
    }
}
```

### Переопределение методов класса

Kotlin требует указания аннотации и для методов класса, которые могут быть переопределены, и для самого переопределения:

```kotlin
open class Base {
    open fun v() {}
    fun nv() {}
}

class Derived() : Base() {
    override fun v() {}
    override fun nv() {} // Ошибка компиляции, т.к. nv() не помечен как open в классе Base
    open fun nov() {} // Пометить можно, но это не будет иметь эффекта, т.к. сам класс Derived не помечен как open
}
```

Для `Derived.v()` необходима аннотация `override` для переопределения метода. Если она отсутствует, компилятор выдаст ошибку. Если у функции типа `Base.nv()` нет аннотации `open`, переопределение этого метода в производном классе невозможно. В `final `классе (классе без аннотации `open`), запрещено использование аннотации `open` для его методов, как в случае с `Derived.nov()`.

Член класса, помеченный `override`, является сам по себе `open`, т.е. он может быть переопределён в производных классах.

### Абстрактные классы

Класс и некоторые его члены могут быть объявлены как `abstract`. Абстрактный член не имеет реализации в своём классе. Обратите внимание, что нам не надо аннотировать абстрактный класс или функцию словом `open` - это подразумевается и так.

```kotlin
abstract class Base(p: Int) {
    fun baseFunc() {}
    abstract fun abstractFunc();
}

class Derived(p: Int) : Base(p) {
    fun derivedFunc() {
        baseFunc()
    }

    override fun abstractFunc() {
        println("Base.abstractFunc()")
    }
}
```

### Свойства и поля

Классы в Kotlin могут иметь свойства: изменяемые (mutable) и неизменяемые (read-only) — `var` и `val` соответственно.

```kotlin
public class Address {
    public var name: String = ...
    public var street: String = ...
    public var city: String = ...
}
```

Для того, чтобы воспользоваться свойством, мы просто обращаемся к нему по имени:

```kotlin
fun copyAddress(address: Address): Address {
    val result = Address()
    result.name = address.name // вызов методов доступа
    result.street = address.street
    result.city = address.city
    return result
}
```

Методы доступа (геттеры и сеттеры) могут быть описаны самостоятельно, как и обычные функции, прямо при объявлении свойств. Например, пользовательский геттер и сеттер:

```kotlin
val stringRepresentation: String
    get() = "$name of $street in $city"
    set(value) {
        field = "$value new string"
    }
```

Классы в Kotlin не могут иметь полей. Т.е. переменные, которые объявляются внутри класса только выглядят и ведут себя как поля из Java, хотя на самом деле являются свойствами, для них неявно реализуются методы `get()` и `set()`.

### Классы данных

Нередко создаются классы лишь с целью хранения данных. Функционал таких классов зависит от самих данных, которые в них хранятся. В Kotlin класс может быть отмечен словом `data` и такой класс называют *классом данных*:

```kotlin
data class User(val name: String, val age: Int)
```

Компилятор автоматически формирует следующие методы для класса из свойств, объявленных в основном конструкторе:

* пару функций `equals()`/`hashCode()` — для возможности сравнения конкретных объектов по значению и ссылке.
* Функцию `toString()` в форме `"User(name=John, age=42)"` — для приведения объекта к строке,
* Компонентные функции `componentN()`, которые соответствуют свойствам, в соответствии с порядком их объявления (`name=component1(), age=component2()`),
* Функцию `copy()` — для создания копии объекта.

Для обеспечения согласованности и осмысленного поведения сгенерированного кода классы данных должны удовлетворять следующим требованиям:

* Основной конструктор должен иметь как минимум один параметр.
* Все параметры основного конструктора должны быть отмечены, как `val` или `var`.
* Классы данных не могут быть абстрактными или "открытыми" (`open`).

## Интерфейсы

Интерфейсы в Kotlin очень похожи на интерфейсы в Java. Основное отличие — интерфейсы могут содержать методы с реализацией. Эта особенность делает интерфейсы похожими на абстрактные классы, однако интерфейсы в отличие от них позволяют реализовать множественное наследование.

Интерфейс определяется ключевым словом `interface`:

```kotlin
interface MyInterface1 {
    fun interface1()
    fun method1() {
      // тело
    }
}

interface MyInterface2 {
    fun interface2()
    fun method2() {
      // тело
    }
}
```

Класс или объект могут реализовать любое количество интерфейсов:
```kotlin
class Child : MyInterface1, MyInterface2 {
    override fun interface1() {
        // тело
    }
    override fun interface2() {
        // тело
    }
}
```

В интерфейсах могут быть объявлены свойства. Свойство, объявленное в интерфейсе, может быть либо абстрактным, либо иметь свою реализацию методов доступа (геттеров и сеттеров).

```kotlin
interface MyInterface {
    val prop: Int // абстрактное свойство

    var _propWithImpl: String // абстрактное свойство
    var propWithImpl: String
        get() = _propWithImpl
        set(value) {
            _propWithImpl = value
        }

    fun interface2()
    fun method2() {
      // тело
    }
}

class Child : MyInterface {
    override val prop: Int = 29
    override val _propWithImpl: String = "property with implementation"
}
```

В рамках сеттеров интерфейса невозможно использовать ключевое слово `field` для доступа к значению свойства. Компилятор выдаст ошибку. Поэтому, если требуется переопределить сеттер свойства интерфейса, необходимо заводить еще одно дополнительное свойство, которое будет содержать значение свойства, сеттер которого мы переопределяем. Например, в приведенном выше примере выполняется переопределение геттера и сеттера свойства `propWithImpl`. Для этого заводится свойство `_propWithImpl`, которое используется в переопределяемых сеттере и геттере для получения и сохранения значения.

## Null-безопасность

### Nullable типы и Non-Null типы

Система типов в языке Kotlin нацелена на то, чтобы избавить программиста от опасности обращения к `null`-значениям.
Одним из самых распространённых подводных камней многих языков программирования, в том числе Java, является попытка произвести доступ к `null`-значению. Это приводит к ошибке. В Java такая ошибка называется `NullPointerException`.

Kotlin призван исключить ошибки подобного рода. `NullPointerException` в Kotlin могу возникать только в следующих случаях:
* Явное указание `throw NullPointerException()`.
* Использование оператора `!!` (описано ниже).
* Эту ошибку вызвал внешний Java-код.
* Есть какое-то несоответствие при инициализации данных (в конструкторе использована ссылка `this` на данные, которые не были ещё проинициализированы).

Система типов в Kotlin различает два типа ссылок: которые могут иметь значение `null` (nullable ссылки) и те, которые **не могут** иметь null-знаечния (non-null ссылки). Для того, чтобы разрешить `null` значение, необходимо добавить к объявлению типа переменноq знак вопроса `?`, например, `String?`.

Пример:

```kotlin
var a: String = "abc"
a = null // ошибка компиляции, a — по умолчанию non-null
var l = a.length // ошибка компиляции, переменная a имеет значение null

var b: String? = "abc"
b = null // нет ошибки, переменная b может иметь значение null
var l = b.length // ошибка: переменная b имеет значение null
```

Пример выше показывает, что при вызове метода с использованием переменной `a`, получение `NullPointerException` исключено, т.к. компилятор не позволит собрать такой код, где `a` может иметь значение `null`.
Получить доступ к значению переменной `b` для вызова метода — небезопасно и компилятор предупредит об ошибке. Но если есть необходимость получить доступ к свойству, то есть несколько способов:
* Проверка на `null`.
* Безопасные вызовы.
* Элвис-оператор (elvis operator или оператор `?:`).
* Оператор `!!`.

### Проверка на `null`

Первый способ. Можно явно проверить переменную на `null` значение и обработать два варианта по отдельности:

```kotlin
val l = if (a != null) a.length else -1
```

Компилятор отслеживает информацию о проведённой проверке и позволяет вызывать `length` внутри блока `if`.

### Безопасные вызовы

Вторым способом является оператор безопасного вызова `?.`:

```kotlin
var l = a?.length
```

Этот код возвращает `a.length` в том, случае, если `a` не `null`. Иначе он возвращает `null`, и тогда типом этого выражения (переменной `l`) будет `Int?`.

### Элвис-оператор

Он же **elvis operator** или оператор `?:` (не путать с тернарным оператором).  
Полезен в ситуации, когда необходимо либо вернуть некоторое значение сслылки, если она не `null`, либо некое значение "по-умолчанию".

Пример с `if-else`:

```kotlin
val l: Int = if (a != null) b.length else -1
```

Аналогом такому `if`-выражению является элвис-оператор `?:`:

```kotlin
val l = a?.length ?: -1
```

Если выражение, стоящее слева от элвис-оператора, не является `null`, то элвис-оператор его вернёт (`a.length`). В противном случае в качестве возвращаемого значения послужит то, что стоит справа (`-1`).

### Оператор `!!`

Для любителей NPE существует ещё один способ. Можно написать `a!!` и это вернёт либо **non-null** значение `a`, либо выкинет NPE:

```kotlin
val l = a!!.length
```

## Создать проект в IntelliJ IDEA

New -> Kotlin -> JVM -> Next.

Project Name -> Finish.

Add kotlin file -> Add configuration -> Run.