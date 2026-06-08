package lab3.task4

/**
 * Task 4: Taxi park
 *
 * The TaxiPark class stores information about the registered drivers, passengers, and their trips.
 * Your task is to implement six functions which collect different statistics about the data.
 *
 * Subtask 1:
 * Find all the drivers who performed no trips.
 */
internal fun TaxiPark.findFakeDrivers(): Set<Driver> {
    val activeDrivers = trips.map { it.driver }.toSet()
    return allDrivers.filter { driver ->
        driver !in activeDrivers
    }.toSet()
}

/**
 * Subtask 2:
 * Find all the clients who completed at least the given number of trips.
 */
internal fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> {
    val passengerTripCounts: Map<Passenger, Int> = trips
        .flatMap { it.passengers }
        .groupingBy { it }
        .eachCount()

    return allPassengers.filter { passenger ->
        (passengerTripCounts[passenger] ?: 0) >= minTrips
    }.toSet()
}

/**
 * Subtask 3:
 * Find all the passengers who were taken by a given driver more than once.
 */
internal fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> {
    return trips.filter { it.driver == driver }
        .flatMap { it.passengers }
        .groupingBy { it }
        .eachCount()
        .filter { it.value > 1 }
        .keys
}

/**
 * Subtask 4:
 * Find the passengers who had a discount for the majority of their trips.
 */
internal fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    val passengerScores = mutableMapOf<Passenger, Int>()

    for (trip in trips) {
        val scoreDelta = if (trip.discount != null) 1 else -1
        for (passenger in trip.passengers) {
            passengerScores[passenger] = (passengerScores[passenger] ?: 0) + scoreDelta
        }
    }

    return allPassengers.filter { passenger ->
        (passengerScores[passenger] ?: 0) > 0
    }.toSet()
}
