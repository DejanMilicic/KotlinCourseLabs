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
    return this.allDrivers.filter { driver -> driver !in this.trips.map { it.driver } }.toSet()
}

/**
 * Subtask 2:
 * Find all the clients who completed at least the given number of trips.
 */
internal fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> {
    return this.trips.flatMap { it.passengers }.groupBy { it.name }.filter { el -> el.value.size >= minTrips }
        .map { it.value.first() }.toSet()
}

/**
 * Subtask 3:
 * Find all the passengers who were taken by a given driver more than once.
 */
internal fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> {
    return this.trips.filter { trip -> trip.driver == driver }.flatMap { it.passengers }
        .groupBy { it.name }.filter { el -> el.value.size > 1 }.map { it.value.first() }.toSet()
}

/**
 * Subtask 4:
 * Find the passengers who had a discount for the majority of their trips.
 */
internal fun TaxiPark.findSmartPassengers(): Set<Passenger> {
    val discount = HashMap<Passenger, Int>()
    val noDiscount = HashMap<Passenger, Int>()

    this.trips.forEach { trip ->
        trip.passengers.forEach { passenger ->
            if (trip.discount != null) {
                if (discount.containsKey(passenger))
                    discount[passenger] = discount[passenger]!! + 1
                else discount[passenger] = 1
            } else {
                if (noDiscount.containsKey(passenger))
                    noDiscount[passenger] = noDiscount[passenger]!! + 1
                else noDiscount[passenger] = 1
            }
        }
    }
    val result = HashSet<Passenger>()
    for (p in discount) {
        if (noDiscount.containsKey(p.key) && p.value > noDiscount[p.key]!!) {
            result.add(p.key)
        } else if (!noDiscount.containsKey(p.key))
            result.add(p.key)
    }
    return result
}
