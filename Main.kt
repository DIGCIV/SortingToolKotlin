package sorting

import java.util.*
import java.io.File
import kotlin.math.roundToInt

var scanner = Scanner(System.`in`)
var inFileName = ""
var outFileName = ""
var hasInFile = false
var hasOutFile = false

fun main(args: Array<String>) {

    if (args.isNotEmpty()) {

        if (args.contains("-inputFile")) {
            if (args.indexOf("-inputFile") == args.lastIndex) {
                println("Missing input file name")
                return
            } else {
                hasInFile = true
                inFileName = args[args.indexOf("-inputFile") + 1]
            }
        }

        if (args.contains("-outputFile")) {
            if (args.indexOf("-outputFile") == args.lastIndex) {
                println("Missing output file name")
                return
            } else {
                hasOutFile = true
                outFileName = args[args.indexOf("-outputFile") + 1]
            }
        }

        if (args.contains("-sortingType")) {
            if (args.indexOf("-sortingType") == args.lastIndex || ((args[args.indexOf("-sortingType") + 1] != "byCount") && (args[args.indexOf("-sortingType") + 1] != "natural")))
            {
                println("No sorting type defined!")
                return
            }
            if (args[args.indexOf("-sortingType") + 1] == "byCount") sortByCount(args)
            else sortNatural(args)
        } else sortNatural(args)
        /*else if (args.contains("-dataType")) {
            when (args[args.indexOf("-dataType") + 1]) {
                "long" -> long()
                "line" -> line()
                "word" -> word()
                else -> println("Wrong data type")
            }
        }*/
    }
}

fun sortNatural(args: Array<String>) {
    val list: MutableList<out Any>
    val name: String
    if (args.contains("-dataType")) {
        if (args.indexOf("-dataType") == args.lastIndex || (args[args.indexOf("-dataType") + 1] != "long" && args[args.indexOf("-dataType") + 1] != "word" && args[args.indexOf("-dataType") + 1] != "line"))
        {
            println("No data type defined!")
            return
        }
    }
    for (elem in args) {
        if (elem != "-sortingType" && elem != "natural" && elem != "byCount" && elem != "-dataType" && elem != "long" && elem != "word" && elem != "line" && elem != "-inputFile" && elem != "-outputFile" && !elem.contains(".txt") && !elem.contains(".dat"))
            println("\"$elem\" is not a valid parameter. It will be skipped.")
    }
    when (args[args.indexOf("-dataType") + 1]) {
        "long" -> {
            name = "numbers"
            list = mutableListOf<Long>()

            if (hasInFile) {
                val buf = File(inFileName).readText().split(' ', '\t', '\n')
                for (elem in buf) {
                    if (isNumber(elem)) list.add(elem.toLong())
                    else println("\"$elem\" is not a long. It will be skipped")
                }
            } else while (scanner.hasNext()) {
                val curr = scanner.next()
                if (isNumber(curr)) list.add(curr.toLong())
                else println("\"$curr\" is not a long. It will be skipped")
            }
            list.sort()
        }
        "line" -> {
            name = "lines"
            list = mutableListOf<String>()

            if (hasInFile) {
                val buf = File(inFileName).readLines()
                for (elem in buf) list.add(elem)
            } else while (scanner.hasNext()) list.add(scanner.nextLine())
            list.sort()
        }
        "word" -> {
            name = "words"
            list = mutableListOf<String>()

            if (hasInFile) {
                val buf = File(inFileName).readText().split(' ', '\t', '\n')
                for (elem in buf) list.add(elem)
            } else while (scanner.hasNext()) list.add(scanner.next())
            list.sort()
        }
        else -> {
            println("Wrong data type")
            return
        }
    }

    if (hasOutFile) {
        File(outFileName).writeText("Total $name: ${list.count()}.\nSorted data:")
        if (name == "lines") File(outFileName).writeText("\n")
        for (elem in list) if (name == "lines") File(outFileName).writeText("$elem\n")
        else File(outFileName).writeText(" $elem")
    } else {
        print("Total $name: ${list.count()}.\nSorted data:")
        if (name == "lines") println()
        for (elem in list) if (name == "lines") print("$elem\n") else print(" $elem")
    }
}

fun sortByCount(args: Array<String>) {
    val map: MutableMap<out Any, Int>
    //val sorted: SortedMap<out Any, Int>
    val sorted: Map<out Any, Int>
    val fSorted: Map<out Any, Int>
    val name: String
    if (args.contains("-dataType")) {
        if (args.indexOf("-dataType") == args.lastIndex || (args[args.indexOf("-dataType") + 1] != "long" && args[args.indexOf("-dataType") + 1] != "word" && args[args.indexOf("-dataType") + 1] != "line"))
        {
            println("No data type defined!")
            return
        }
    }
   /*
   for (elem in args) {
        if (elem != "-sortingType" && elem != "natural" && elem != "byCount" && elem != "-dataType" && elem != "long" && elem != "word" && elem != "line")
            println("\"$elem\" is not a valid parameter. It will be skipped.")
    }

    */
    when (args[args.indexOf("-dataType") + 1]) {
        "long" -> {
            name = "numbers"
            map = mutableMapOf<Long, Int>()

            if (hasInFile) {
                val buf = File(inFileName).readText().split(' ', '\n', '\t')
                for (elem in buf) {
                    if (isNumber(elem)) {
                        if (map.containsKey(elem.toLong())) map[elem.toLong()] = map.getValue(elem.toLong()) + 1
                        else map[elem.toLong()] = 1
                    } else println("\"$elem\" is not a long. It will be skipped.")
                }
            } else while (scanner.hasNext()) {
                val buf = scanner.next()
                if (isNumber(buf)) {
                    if (map.containsKey(buf.toLong())) map[buf.toLong()] = map.getValue(buf.toLong()) + 1
                    else map[buf.toLong()] = 1
                } else println("\"$buf\" is not a long. It will be skipped.")
            }
            sorted = map.toList().sortedBy { (key, _) -> key }.toMap()
            fSorted = sorted.toList().sortedBy { (_, value) -> value }.toMap()
            //sorted = map.toSortedMap(compareBy<Long, Int>{ it.first }.thenBy { it.second)
        }
        "line" -> {
            name = "lines"
            map = mutableMapOf<String, Int>()

            if (hasInFile) {
                val buf = File(inFileName).readLines()
                for (elem in buf) {
                    if (map.containsKey(elem)) map[elem] = map.getValue(elem) + 1
                    else map[elem] = 1
                }
            } else while (scanner.hasNext()) {
                val buf = scanner.nextLine()
                if (map.containsKey(buf)) map[buf] = map.getValue(buf) + 1
                else map[buf] = 1
            }
            sorted = map.toList().sortedBy { (key, _) -> key }.toMap()
            fSorted = sorted.toList().sortedBy { (_, value) -> value }.toMap()
            //sorted = map.toSortedMap(compareBy<String> { it.length }.thenBy { it })
        }
        "word" -> {
            name = "words"
            map = mutableMapOf<String, Int>()
            if (hasInFile) {
                val buf = File(inFileName).readText().split(' ', '\n', '\t')
                for (elem in buf) {
                    if (map.containsKey(elem)) map[elem] = map.getValue(elem) + 1
                    else map[elem] = 1
                }
            } else while (scanner.hasNext()) {
                val buf = scanner.next()
                if (map.containsKey(buf)) map[buf] = map.getValue(buf) + 1
                else map[buf] = 1
            }
            sorted = map.toList().sortedBy { (key, _) -> key }.toMap()
            fSorted = sorted.toList().sortedBy { (_, value) -> value }.toMap()
            //sorted = map.toSortedMap(compareBy<String> { it.length }.thenBy { it })
        }
        else -> {
            println("Wrong data type")
            if (hasOutFile) File(outFileName).writeText("")
            return
        }
    }
    var count = 0
    for (elem in fSorted) count += elem.value
    if (hasOutFile) File(outFileName).writeText("Total $name: $count.\n")
    else print("Total $name: $count.\n")
    for (elem in fSorted) {
        val percent = (elem.value / count.toDouble() * 100).roundToInt()
        if (hasOutFile) File(outFileName).writeText("${elem.key}: ${elem.value} time(s), $percent%")
        else println("${elem.key}: ${elem.value} time(s), $percent%")
    }
}

fun isNumber(str: String): Boolean {
    for (elem in str) {
        if (!elem.isDigit() && elem != '.' && elem != ',' && elem != '-') return false
    }
    return true
}


/*
fun long() {
    var count = 0
    var maxNum = 0L
    var countMaxNum = 0
    var curNum: Long

    while (scanner.hasNext()) {
        curNum = scanner.nextLong()
        if (curNum > maxNum) {
            maxNum = curNum
            countMaxNum = 1
        } else if (curNum == maxNum) countMaxNum++
        count++
    }

    val percent = (countMaxNum.toDouble() / count * 100).toInt()
    println("Total numbers: $count.\nThe greatest number: $maxNum ($countMaxNum time(s), $percent%).")
}

fun line() {
    var count = 0
    var maxLine = ""
    var countMaxLine = 0
    var curLine: String

    while (scanner.hasNext()) {
        curLine = scanner.nextLine()
        if (curLine.length > maxLine.length) {
            maxLine = curLine
            countMaxLine = 1
        } else if (curLine == maxLine) countMaxLine++
        count++
    }

    val percent = (countMaxLine.toDouble() / count * 100).toInt()
    println("Total lines: $count.\nThe longest line:\n$maxLine\n($countMaxLine time(s), $percent%).")
}

fun word() {
    var count = 0
    var maxWord = ""
    var countMaxWord = 0
    var curWord: String

    while (scanner.hasNext()) {
        curWord = scanner.next()
        if (curWord.length > maxWord.length) {
            maxWord = curWord
            countMaxWord = 1
        } else if (curWord == maxWord) countMaxWord++
        count++
    }

    val percent = (countMaxWord.toDouble() / count * 100).toInt()
    println("Total words: $count.\nThe longest word: $maxWord ($countMaxWord time(s), $percent%).")
}
*/
