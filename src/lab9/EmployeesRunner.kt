package lab9

import common.FileReader
import java.io.*

fun parseEmployees(employeesCSVLines: List<String>): List<Employee> {
    return employeesCSVLines
        .drop(1)
        .filter{line -> line.isNotBlank()}
        .map{ line ->
            val columns = line.split(",")
            Employee(
                employeeID = EmployeeID(columns[0].trim()),
                name = columns[1].trim(),
                department = Department( columns[2].trim()),
                salary = columns[3].trim().toInt(),
                skills = columns[4].trim().split("|").map { it.trim() }
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
