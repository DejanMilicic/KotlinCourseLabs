package lab9

class EmployeesPortal(
    private val employees: List<Employee>
) : EmployeeApi {
    override fun findHighestPaidEmployee(): Employee? = employees.maxByOrNull { it.salary }

    override fun getEmployeesByDepartment(department: Department): List<Employee> =
        employees.filter { it.department == department }

    override fun getEmployeesBySalaryRange(salaryRange: IntRange): List<Employee> =
        employees.filter { it.salary in salaryRange }

    override fun calculateAverageSalaryByDepartment(department: Department): Double =
        employees.filter { it.department == department }
            .map { it.salary }
            .average()

    override fun findMostCommonSkill(): String =
        employees.flatMap { it.skills }
            .groupingBy { it }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key ?: ""
}