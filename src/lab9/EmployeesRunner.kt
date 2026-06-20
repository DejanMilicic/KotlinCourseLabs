package lab9

import common.FileReader

fun parseEmployees(employeesCSVLines: List<String>): List<Employee> {
    return employeesCSVLines.drop(1).map {
        val fields = it.split(",")
        Employee(
            employeeID = EmployeeID(fields[0]),
            name = fields[1],
            department = Department(fields[2]),
            salary = fields[3].toInt(),
            skills = fields[4].split("|")
        )
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