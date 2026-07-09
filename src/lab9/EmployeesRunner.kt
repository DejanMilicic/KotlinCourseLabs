package lab9

import common.FileReader

fun parseEmployees(employeesCSVLines: List<String>): List<Employee> {
    return employeesCSVLines.drop(1).map { line ->
        val parts = line.split(",")
        val employeeid = EmployeeID(parts[0])
        val name = parts[1]
        val dep = Department(parts[2])
        val salary = parts[3].toInt()
        val skills = parts[4].split("|")

        Employee(employeeid, name, dep, salary, skills)
    }
}

fun newEmployeeApi(employees: List<Employee>): EmployeeApi {
    return EmployeePortal(employees)
}

fun main() {
    val employeesCSVLines = FileReader.readFileInResources("lab9/employees.csv")
    val employees = parseEmployees(employeesCSVLines)

    val employeeApi: EmployeeApi = newEmployeeApi(employees)
}
