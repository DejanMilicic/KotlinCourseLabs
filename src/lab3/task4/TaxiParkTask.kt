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
    return allDrivers - activeDrivers
}

/**
 * Subtask 2:
 * Find all the clients who completed at least the given number of trips.
 */
internal fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> {
    if (minTrips <= 0) return allPassengers

    return trips
        .flatMap { it.passengers }
        .groupingBy { it }
        .eachCount()
        .filterValues { it >= minTrips }
        .keys
}

/**
 * Subtask 3:
 * Find all the passengers who were taken by a given driver more than once.
 */
internal fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> {
    return trips
        .filter { it.driver == driver }
        .flatMap { it.passengers }
        .groupingBy { it }
        .eachCount()
        .filterValues { it > 1 }
        .keys
}

/**
 * Subtask 4:
 * Find the passengers who had a discount for the majority of their trips.
 */
internal fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    val totalTripsByPassenger = trips
        .flatMap { it.passengers }
        .groupingBy { it }
        .eachCount()

    val discountedTripsByPassenger = trips
        .filter { it.discount != null }
        .flatMap { it.passengers }
        .groupingBy { it }
        .eachCount()

    return totalTripsByPassenger
        .filter { (passenger, totalTrips) ->
            discountedTripsByPassenger.getOrDefault(passenger, 0) > totalTrips / 2
        }
        .keys
}
