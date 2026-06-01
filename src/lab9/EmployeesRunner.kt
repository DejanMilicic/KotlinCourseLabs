package lab9

import common.FileReader

fun parseEmployees(employeesCSVLines: List<String>): List<Employee> {
    return employeesCSVLines
        .drop(1)
        .map { line ->
            val columns = line.split(",")
            Employee(
                employeeID = EmployeeID(columns[0]),
                name = columns[1],
                department = Department(columns[2]),
                salary = columns[3].toInt(),
                skills = columns[4].split("|")
            )
        }
}

fun newEmployeeApi(employees: List<Employee>): EmployeeApi = EmployeesPortal(employees)

fun main() {
    val employeesCSVLines = FileReader.readFileInResources("exercise9/employees.csv")
    val employees = parseEmployees(employeesCSVLines)

    val employeeApi : EmployeeApi = newEmployeeApi(employees)
}