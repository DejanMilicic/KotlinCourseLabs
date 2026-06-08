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

class EmployeesPortal(val employees: List<Employee>) : EmployeeApi {
    override fun findHighestPaidEmployee(): Employee? {
        return employees.maxByOrNull { it.salary }
    }

    override fun getEmployeesByDepartment(department: Department): List<Employee> {
        return employees.filter { it.department == department }
    }

    override fun getEmployeesBySalaryRange(salaryRange: IntRange): List<Employee> {
        return employees.filter { it.salary in salaryRange }
    }

    override fun calculateAverageSalaryByDepartment(department: Department): Double {
        return getEmployeesByDepartment(department).map { it.salary }.average()
    }

    override fun findMostCommonSkill(): String {
        return employees.flatMap { it.skills }.groupBy { it }.maxByOrNull { it.value.size }?.key ?: ""
    }

}