enum class HandType {
    HighCard,
    Pair,
    TwoPairs,
    Three,
    FullHouse,
    Four,
    Five
}

data class Hand(
    val hand: String,
    val handType: HandType,
    val bid: Int
)

val cardMap = mapOf(
    'J' to 1,
    '2' to 2,
    '3' to 3,
    '4' to 4,
    '5' to 5,
    '6' to 6,
    '7' to 7,
    '8' to 8,
    '9' to 9,
    'T' to 10,
    'Q' to 11,
    'K' to 12,
    'A' to 13
)

val compareCards = Comparator<String> { hand1, hand2 ->
    for (i in 0..4) {
        val card1 = hand1[i]
        val card2 = hand2[i]
        if (cardMap[card1]!! > cardMap[card2]!!) {
            return@Comparator 1
        } else if (cardMap[card1]!! < cardMap[card2]!!) {
            return@Comparator -1
        }
    }
    error("Should not happen")
}

fun String.getHand(bid: Int): Hand {
    val handMap = HashMap<Char, Int>()

    forEach { card ->
        handMap[card] = handMap.getOrDefault(card, 0) + 1
    }

    val handType =  when (handMap.size) {
        5 -> HandType.HighCard
        4 -> HandType.Pair
        3 -> if (handMap.values.contains(3)) HandType.Three else HandType.TwoPairs
        2 -> if (handMap.values.contains(4)) HandType.Four else HandType.FullHouse
        else -> HandType.Five
    }

    return Hand(this, handType, bid)
}

fun String.getHandWithJoker(bid: Int): Hand {
    val handMapNoJokers = HashMap<Char, Int>()

    var nrOfJokers = 0
    forEach { card ->
        if (card != 'J') {
            handMapNoJokers[card] = handMapNoJokers.getOrDefault(card, 0) + 1
        } else {
            nrOfJokers++
        }
    }

    val handType =  when (handMapNoJokers.size) {
        5 -> HandType.HighCard
        4 -> HandType.Pair
        3 -> {
            if (handMapNoJokers.values.contains(3)) {
                HandType.Three      // 333TK
            } else if (nrOfJokers == 0) {
                HandType.TwoPairs   // 33TTK
            } else {
                HandType.Three      // 33TKJ or 3TKJJ
            }
        }
        2 -> {
            if (nrOfJokers == 0) {
                if (handMapNoJokers.values.contains(4)) HandType.Four else HandType.FullHouse
            } else if (nrOfJokers == 1) {
                if (handMapNoJokers.values.contains(2)) HandType.FullHouse else HandType.Four
            } else {
                HandType.Four    // 3TTJJ or 3TJJJ
            }
        }
        else -> HandType.Five
    }

    return Hand(this, handType, bid)
}

fun main() {
    fun part1(input: List<String>): Int {
        val regex = "(.+) (.+)".toRegex()

        val hands = input.fold(ArrayList<Hand>()) { acc, line ->
            val match = regex.find(line)?.groupValues
            val hand = match?.get(1)!!
            val bid = match[2].toInt()
            val theHand = hand.getHand(bid)
            acc.add(theHand)
            acc
        }

        hands.sortWith { o1, o2 ->
            if (o1.handType.ordinal == o2.handType.ordinal) {
                compareCards.compare(o1.hand, o2.hand)
            } else {
                o1.handType.ordinal - o2.handType.ordinal
            }
        }

        var sum = 0
        hands.forEachIndexed { index, hand ->
            sum += (index + 1) * hand.bid
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val regex = "(.+) (.+)".toRegex()

        val hands = input.fold(ArrayList<Hand>()) { acc, line ->
            val match = regex.find(line)?.groupValues
            val hand = match?.get(1)!!
            val bid = match[2].toInt()
            val theHand = hand.getHandWithJoker(bid)
            acc.add(theHand)
            acc
        }

        hands.sortWith { o1, o2 ->
            if (o1.handType.ordinal == o2.handType.ordinal) {
                compareCards.compare(o1.hand, o2.hand)
            } else {
                o1.handType.ordinal - o2.handType.ordinal
            }
        }

        var sum = 0
        hands.forEachIndexed { index, hand ->
            sum += (index + 1) * hand.bid
        }
        return sum
    }

    val day = "07"
    val testInput = readInput("input/Day${day}_test")
    check(part1(testInput) == 6440) { "was ${part1(testInput)}" }
    check(part2(testInput) == 5905) { "was ${part2(testInput)}" }

    val input = readInput("input/Day${day}")
    part1(input).println()
    part2(input).println()
}
