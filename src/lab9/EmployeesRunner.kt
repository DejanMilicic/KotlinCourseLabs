package lab9

import common.FileReader

fun parseEmployees(employeesCSVLines: List<String>): List<Employee> {
    val dataLines = employeesCSVLines.subList(1, employeesCSVLines.size)
    val employeeList = mutableListOf<Employee>()

    dataLines.forEach {
        val data = it.split(",").map { it.trim() }
        employeeList.add(
            Employee(
                EmployeeID(data[0]),
                data[1],
                Department(data[2]),
                data[3].toInt(),
                data[4].split("|").map { it.trim() }
            )
        )
    }

    return employeeList
}

fun newEmployeeApi(employees: List<Employee>): EmployeeApi {
    return EmployeesPortal(employees)
}

fun main() {
    val employeesCSVLines = FileReader.readFileInResources("lab9/employees.csv")
    val employees = parseEmployees(employeesCSVLines)

    val employeeApi : EmployeeApi = newEmployeeApi(employees)
}
