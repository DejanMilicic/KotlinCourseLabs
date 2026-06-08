package lab9

import common.FileReader

fun parseEmployees(employeesCSVLines: List<String>): List<Employee> {
    return employeesCSVLines.drop(1).map { line ->
        val parts = line.split(",")
        Employee(
            employeeID = EmployeeID(parts[0]),
            name = parts[1],
            department = Department(parts[2]),
            salary = parts[3].toInt(),
            skills = parts[4].split("|")
        )
    }
}

fun newEmployeeApi(employees: List<Employee>): EmployeeApi {
    return EmployeesPortal(employees)
}

fun main() {
    val employeesCSVLines = FileReader.readFileInResources("exercise9/employees.csv")
    val employees = parseEmployees(employeesCSVLines)

    val employeeApi : EmployeeApi = newEmployeeApi(employees)
}
