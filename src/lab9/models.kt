package lab9

data class Department(val departmentName: String)

@JvmInline
value class EmployeeID(val id: String)

class Employee(val employeeID: EmployeeID,
               val name: String,
               val department: Department,
               val salary: Int,
               val skills: List<String>)

class EmployeesPortal(private val employees: List<Employee>) : EmployeeApi{

    override fun findHighestPaidEmployee(): Employee? = employees.maxByOrNull { it.salary }

    override fun getEmployeesByDepartment(department: Department): List<Employee> = employees.filter { it.department == department }

    override fun getEmployeesBySalaryRange(salaryRange: IntRange): List<Employee> = employees.filter { it.salary in salaryRange }

    override fun calculateAverageSalaryByDepartment(department: Department): Double {
        val employeesOfDepartment = getEmployeesByDepartment(department)
        return employeesOfDepartment.sumOf { it.salary }.toDouble() / employeesOfDepartment.count()
    }

    override fun findMostCommonSkill(): String {
        val skillCounts = mutableMapOf<String, Int>()

        employees.forEach { employee ->
            employee.skills.forEach { skill ->
                skillCounts[skill] = skillCounts.getOrDefault(skill, 0) + 1
            }
        }

        return skillCounts.maxByOrNull { it.value }?.key ?: ""
    }

}