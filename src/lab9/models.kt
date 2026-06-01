package lab9

data class Department(val departmentName: String)

@JvmInline
value class EmployeeID(val id: String)

data class Employee(
    val employeeID: EmployeeID,
    val name: String,
    val department: Department,
    val salary: Int,
    val skills: List<String>
)

class EmployeesPortal(private val employees: List<Employee>) : EmployeeApi {

    override fun findHighestPaidEmployee(): Employee? =
        employees.maxByOrNull { it.salary }

    override fun getEmployeesByDepartment(department: Department): List<Employee> =
        employees.filter { it.department == department }

    override fun getEmployeesBySalaryRange(salaryRange: IntRange): List<Employee> =
        employees.filter { it.salary in salaryRange }

    override fun calculateAverageSalaryByDepartment(department: Department): Double =
        getEmployeesByDepartment(department).map { it.salary }.average()

    override fun findMostCommonSkill(): String =
        employees.flatMap { it.skills }
            .groupingBy { it }
            .eachCount()
            .maxBy { it.value }
            .key
}