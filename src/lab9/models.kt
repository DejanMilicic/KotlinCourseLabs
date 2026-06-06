package lab9


data class Department(val departmentName: String)

@JvmInline
value class EmployeeID(val id: String)

data class Employee(
    val employeeId: EmployeeID,
    val name: String,
    val department: Department,
    val salary: Int,
    val skills: List<String>
)

class EmployeesPortal(val employees: List<Employee>): EmployeeApi {
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
        val employees = getEmployeesByDepartment(department)
        if (employees.isEmpty()) {
            return 0.0
        }

        return employees.map { it.salary }.average()
    }

    override fun findMostCommonSkill(): String {
        val skills = employees.flatMap { it.skills }
        if (skills.isEmpty()) {
            return ""
        }

        return skills
            .groupingBy { it }
            .eachCount()
            .maxBy { it.value }
            .key
    }

}