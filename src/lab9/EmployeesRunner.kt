package lab9

import common.FileReader

fun parseEmployees(employeesCSVLines: List<String>): List<Employee> =
    employeesCSVLines
        .drop(1)
        .map { line ->
            val parts = line.split(",")
            Employee(
                employeeID = EmployeeID(parts[0].trim()),
                name = parts[1].trim(),
                department = Department(parts[2].trim()),
                salary = parts[3].trim().toInt(),
                skills = parts[4].trim().split("|").map { it.trim() }
            )
        }

fun newEmployeeApi(employees: List<Employee>): EmployeeApi = EmployeesPortal(employees)

fun main() {
    val employeesCSVLines = FileReader.readFileInResources("lab9/employees.csv")
    val employees = parseEmployees(employeesCSVLines)
    val employeeApi: EmployeeApi = newEmployeeApi(employees)
}
