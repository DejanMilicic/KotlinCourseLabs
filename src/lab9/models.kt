package lab9


data class Department(val departmentName: String)

class EmployeeID(val id: String)

data class Employee(
    val employeeID: EmployeeID,
    val name: String,
    val department: Department,
    val salary: Int,
    val skills: List<String>
)

class EmployeesPortal(val employees: List<Employee>) : EmployeeApi {
    init {
        require(employees.isNotEmpty()) { throw IllegalArgumentException("Employees can not be empty") }
    }

    override fun findHighestPaidEmployee(): Employee {
        return employees.maxBy { it.salary }
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
        val skills = employees.flatMap { employee -> employee.skills.filter { it.isNotEmpty() } }
        return if (skills.isNotEmpty())
            skills.groupBy { it }.mapValues { it.value.size }
                .maxBy { it.value }.key
        else
            "No skills available"
    }

}