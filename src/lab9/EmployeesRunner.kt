package lab9

import common.FileReader

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
        return employees.filter { it.department == department }.map { it.salary }.average()
    }

    override fun findMostCommonSkill(): String {
        return employees.flatMap { it.skills }
            .groupingBy { it }.eachCount().maxByOrNull { it.value }!!.key

    }
}

fun parseEmployees(employeesCSVLines: List<String>): List<Employee> {
    return employeesCSVLines.drop(1).map { line ->
        val columns = line.split(",")
        val id = columns[0].trim()
        val name = columns[1].trim()
        val department = columns[2].trim()
        val salary = columns[3].trim()
        val skills = columns[4].trim().split("|")

        Employee(
            employeeId = EmployeeID(id),
            name = name,
            department = Department(department),
            salary = salary.toInt(),
            skills = skills

        )
    }

}


fun newEmployeeApi(employees: List<Employee>): EmployeeApi {
    return EmployeesPortal(employees)
}

fun main() {
    val employeesCSVLines = FileReader.readFileInResources("resources/lab9/employees.csv")
    val employees = parseEmployees(employeesCSVLines)

    val employeeApi: EmployeeApi = newEmployeeApi(employees)
}

