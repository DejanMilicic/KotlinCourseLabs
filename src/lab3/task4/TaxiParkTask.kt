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

internal fun TaxiPark.findFakeDrivers(): Set<Driver> =
    allDrivers - trips.map { it.driver }.toSet()

/**
 * Subtask 2:
 * Find all the clients who completed at least the given number of trips.
 */

internal fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> {
    val result = mutableSetOf<Passenger>()
    val passengers = hashMapOf<Passenger, Int>()

    for (passenger in this.allPassengers) {
        passengers[passenger] = 0
    }

    for (trip in this.trips) {
        for (passenger in trip.passengers) {
            passengers[passenger] = passengers.getOrDefault(passenger, 0) + 1
        }
    }

    for (passenger in passengers) {
        if (passenger.value >= minTrips) {
            result.add(passenger.key)
        }
    }

    return result
}

/**
 * Subtask 3:
 * Find all the passengers who were taken by a given driver more than once.
 */
internal fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> {
    val passengers = mutableSetOf<Passenger>()
    val result = mutableSetOf<Passenger>()

    for (trip in this.trips) {
        if (trip.driver == driver) {
            for (passenger in trip.passengers) {
                if (!passengers.contains(passenger)) {
                    passengers.add(passenger)
                } else {
                    result.add(passenger)
                }
            }
        }
    }

    return result
}

/**
 * Subtask 4:
 * Find the passengers who had a discount for the majority of their trips.
 */
internal fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    val passengers = hashMapOf<Passenger, Int>()
    val result = mutableSetOf<Passenger>()

    for (trip in this.trips) {
        for (passenger in trip.passengers) {
            if (trip.discount == null || trip.discount == .0) {
                passengers[passenger] = passengers.getOrDefault(passenger, 0) - 1
            } else {
                passengers[passenger] = passengers.getOrDefault(passenger, 0) + 1
            }
        }
    }

    for (passenger in passengers) {
        if (passenger.value > 0) {
            result.add(passenger.key)
        }
    }

    return result
}
