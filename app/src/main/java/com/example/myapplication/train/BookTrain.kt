package com.example.myapplication.train

import java.util.Scanner

class BookTrain {
}

data class ticketData(val id: Int, val seats:Int, val stations:List<String>)

internal class TrainTicketBooking {
    private var bookId = 0
    private var availableSeats = 8
    private var partialAvailSeats = 0
    private var summary = mutableListOf<ticketData>()
    val stationsAvail = mutableListOf("A","B","C","D","E")

    // Method to book a train ticket
    fun bookTicket(numberOfTickets: Int, startStn: String, endStn: String) {
        var stationsToBook = mutableListOf<String>()
        val indexStart = stationsAvail.indexOf(startStn)
        val indexEnd = stationsAvail.indexOf(endStn)
        println("start station - $startStn and start index - $indexStart")
        println("end station - $endStn and end index - $indexEnd")
        stationsToBook = stationsAvail.subList(indexStart,indexEnd)
        println("Stations traveling $stationsToBook")
        if (numberOfTickets <= availableSeats) {
            bookId++
            val data = ticketData(bookId,numberOfTickets,stationsToBook)
            summary.add(data)
            availableSeats -= numberOfTickets
            println("$numberOfTickets ticket(s) booked successfully!")
        } else if(checkPartialStationsAvail(stationsToBook,numberOfTickets)) {
            bookId++
            val data = ticketData(bookId,numberOfTickets,stationsToBook)
            summary.add(data)
            println("$numberOfTickets ticket(s) booked successfully! (partial)")
            partialAvailSeats = 0
            availableSeats -= numberOfTickets
            if (availableSeats<=0){
                availableSeats = 0
            }
        }else{
            println("Sorry, only $availableSeats ticket(s) available.")
        }
    }

    fun checkPartialStationsAvail(stationsToBook: MutableList<String>, numberOfTickets: Int): Boolean {
        if(summary.size==0){
            return false
        }else{
            partialAvailSeats = availableSeats
            for (i in summary){
                var checkContains = false
                for (j in stationsToBook){
                    println("${i.stations} contains the check with $j")
                    if(i.stations.contains(j)){
                        checkContains = true
                    }
                }
                if (!checkContains){
                    partialAvailSeats+= i.seats
                }
            }
            println("Summary - $summary")
            println("partial seats avail - $partialAvailSeats")
            return numberOfTickets<=partialAvailSeats
        }

    }

    // Method to check available seats
    fun checkAvailableSeats() {
        println("Available seats: $availableSeats")
//        val scanner = Scanner(System.`in`)
//        print("Enter your choice: ")
//        val choice = scanner.nextLine()
//        if (choice=="book"){
//            print("custom book successfully ")
//        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val ticketBooking = TrainTicketBooking()
            val scanner = Scanner(System.`in`)
            while (true) {
                println("1. Book Ticket")
                println("2. Check Available Seats")
                println("3. Exit")
                print("Enter your choice: ")
                val choice = scanner.nextInt()
                when (choice) {
                    1 -> {
                        print("Enter number of tickets to book: ")
                        val numberOfTickets = scanner.nextInt()
                        println("Enter start station to book: ")
                        val some = scanner.nextLine()
                        val startStn = scanner.nextLine()
                        println("Enter end station to book: ")
                        val endStn = scanner.nextLine()

                        println("$startStn to $endStn")
                        ticketBooking.bookTicket(numberOfTickets,startStn.toString(),endStn.toString())
                    }

                    2 -> ticketBooking.checkAvailableSeats()
                    3 -> {
                        println("Exiting...")
                        System.exit(0)
                        println("Invalid choice!")
                    }

                    else -> println("Invalid choice!")
                }
            }
        }
    }
}

