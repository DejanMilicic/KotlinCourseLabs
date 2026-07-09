package lab9

class EmployeePortal(val employees: List<Employee>) : EmployeeApi {
    override fun findHighestPaidEmployee(): Employee? {
        return employees.sortedByDescending { e -> e.salary }.firstOrNull()
    }

    override fun getEmployeesByDepartment(department: Department): List<Employee> {
        return employees.filter { it.department == department }
    }

    override fun getEmployeesBySalaryRange(salaryRange: IntRange): List<Employee> {
        return employees.filter { it.salary in salaryRange }
    }

    override fun calculateAverageSalaryByDepartment(department: Department): Double {
        return employees.filter { it.department == department }.map { it.salary }.average()
    }

    override fun findMostCommonSkill(): String {
        return employees.flatMap { it.skills }.groupBy { it }.maxBy { it.value.size }.key
    }
}