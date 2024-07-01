package com.example.myapplication.train


data class user(val id:Int, val availSeats: MutableMap<String, Int>, val wListSeats: MutableMap<String, Int>)

// chat gpt code
class TrainBookingSystem {
//    private val totalSeats = 8
//    private var availableSeats = totalSeats
//    private var waitingListSeats = 2
    var userId = 0
    lateinit var customers : ArrayList<user>
    private val stationList = listOf("A", "B", "C", "D", "E")
    private val seatMap = mutableMapOf<String, Int>()
    private val wlSeatMap = mutableMapOf<String, Int>()



    init {
        stationList.forEach { station ->
            seatMap[station] = 0
            wlSeatMap[station] = 0
        }
    }

    fun bookTickets(from: String, to: String, numTickets: Int): Boolean {
        println("$from to $to $numTickets")
        if (stationList.indexOf(from) >= stationList.indexOf(to)) {
            println("Invalid booking. Please book in the direction from A to E.")
            return false
        }

        for (i in stationList.indexOf(from) until stationList.indexOf(to)) {
            if (8 - seatMap[stationList[i]]!! < numTickets) {
                if (!bookTicketsWaiting(from, to, numTickets)) {
                    println("Sorry, not enough seats available.")
                    return false
                }else{
                    return true
                }
            }
        }

//        if (numTickets > availableSeats) {
//            if (numTickets <= availableSeats + waitingListSeats) {
//                val remainingSeats = numTickets - availableSeats
//                waitingListSeats -= remainingSeats
//                availableSeats = 0
//                println("$remainingSeats tickets added to waiting list.")
//            } else {
//                println("Sorry, not enough seats available.")
//                return false
//            }
//        } else {
//            availableSeats -= numTickets
//        }

        for (i in stationList.indexOf(from) until stationList.indexOf(to)) {
            seatMap[stationList[i]] = seatMap[stationList[i]]!! + numTickets
        }
        userId++
        customers.add(user(userId,seatMap,wlSeatMap))
        println("$numTickets tickets booked from $from to $to.")
        return true
    }

    fun bookTicketsWaiting(from: String, to: String, numTickets: Int): Boolean {
        if (numTickets > 2) {
            return false
        }

        for (i in stationList.indexOf(from) until stationList.indexOf(to)) {
            if (2 - wlSeatMap[stationList[i]]!! < numTickets) {
                return false
            }
        }

        for (i in stationList.indexOf(from) until stationList.indexOf(to)) {
            wlSeatMap[stationList[i]] = wlSeatMap[stationList[i]]!! + numTickets
        }
        userId++
        customers.add(user(userId,seatMap,wlSeatMap))
        println("$numTickets Waiting list tickets booked from $from to $to.")
        return true
    }


    fun cancelTickets(from: String, to: String, numTickets: Int): Boolean {
        println("Cancel $from to $to $numTickets")
        if (stationList.indexOf(from) >= stationList.indexOf(to)) {
            println("Invalid cancellation. Cancellation should be for the journey from A to E.")
            return false
        }

        for (i in stationList.indexOf(from) until stationList.indexOf(to)) {
            if (seatMap[stationList[i]]!! < numTickets) {
                println("Cannot cancel $numTickets tickets. Not enough tickets booked for the journey.")
                return false
            }
        }

        for (i in stationList.indexOf(from) until stationList.indexOf(to)) {
            seatMap[stationList[i]] = seatMap[stationList[i]]!! - numTickets
        }

//        availableSeats += numTickets
        println("$numTickets tickets cancelled from $from to $to.")
        checkWaitingList()
        return true
    }

    private fun checkWaitingList() {
//        var from = ""
//        var to = ""
//       if (wlSeatMap.any { it.value != 0 }) { // checking is any one value have non-zero
//           for (station in seatMap.values) {
//               if (station != 0) {
//
//               }
//           }
//       }


//        if (waitingListSeats > 0 && availableSeats > 0) {
//            val numConfirm = minOf(waitingListSeats, availableSeats)
//            waitingListSeats -= numConfirm
//            availableSeats -= numConfirm
//            println("$numConfirm tickets from waiting list confirmed.")
//        }
    }

    fun displayAvailableSeats() {
//        println("Available Seats: $availableSeats")
//        println("Waiting List Seats: $waitingListSeats")
        println("Seat Map: $seatMap")
        println("Waiting list Seat Map: $wlSeatMap")
    }
}

fun main() {
    val bookingSystem = TrainBookingSystem()

    bookingSystem.bookTickets("A", "C", 4)
    bookingSystem.displayAvailableSeats()

    bookingSystem.bookTickets("C", "E", 8)
    bookingSystem.displayAvailableSeats()

    bookingSystem.bookTickets("A", "C", 2)
    bookingSystem.displayAvailableSeats()

    bookingSystem.bookTickets("A", "D", 2)
    bookingSystem.displayAvailableSeats()

    bookingSystem.cancelTickets("A", "D", 2)
    bookingSystem.displayAvailableSeats()

    bookingSystem.bookTickets("B", "D", 3)
    bookingSystem.displayAvailableSeats()

    bookingSystem.cancelTickets("B", "D", 2)
    bookingSystem.displayAvailableSeats()

    bookingSystem.bookTickets("D", "E", 6)
    bookingSystem.displayAvailableSeats()

    bookingSystem.cancelTickets("D", "E", 3)
    bookingSystem.displayAvailableSeats()
}
