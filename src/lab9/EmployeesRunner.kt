package lab9

import common.FileReader

fun parseEmployees(employeesCSVLines: List<String>): List<Employee> {
    if (employeesCSVLines.isEmpty()) return emptyList()
    return employeesCSVLines.drop(1).map { line ->
        val parts = line.split(",")
        val id = EmployeeID(parts[0].trim())
        val name = parts[1].trim()
        val dept = Department(parts[2].trim())
        val salary = parts[3].trim().toInt()
        val skills = parts[4].split("|").map { it.trim() }
        Employee(id, name, dept, salary, skills)
    }
}

class EmployeesPortal(private val employees: List<Employee>) : EmployeeApi {
    override fun findHighestPaidEmployee(): Employee? =
        employees.maxByOrNull { it.salary }

    override fun getEmployeesByDepartment(department: Department): List<Employee> =
        employees.filter { it.department == department }

    override fun getEmployeesBySalaryRange(salaryRange: IntRange): List<Employee> =
        employees.filter { it.salary in salaryRange }

    override fun calculateAverageSalaryByDepartment(department: Department): Double {
        val deptEmployees = employees.filter { it.department == department }
        if (deptEmployees.isEmpty()) return 0.0
        return deptEmployees.map { it.salary }.average()
    }

    override fun findMostCommonSkill(): String {
        val allSkills = employees.flatMap { it.skills }
        if (allSkills.isEmpty()) return ""
        return allSkills.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key ?: ""
    }
}

fun newEmployeeApi(employees: List<Employee>): EmployeeApi {
    return EmployeesPortal(employees)
}

fun main() {
    val employeesCSVLines = FileReader.readFileInResources("lab9/employees.csv")
    val employees = parseEmployees(employeesCSVLines)

    val employeeApi: EmployeeApi = newEmployeeApi(employees)
}

