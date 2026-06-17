package lab9

import common.FileReader

fun parseEmployees(employeesCSVLines: List<String>): List<Employee> {
    val employees = mutableListOf<Employee>()
    for (i in 1 until employeesCSVLines.size) {
        val data = employeesCSVLines[i].split(",")
        val employeeID = data[0]
        val employeeName = data[1]
        val department = Department(data[2])
        val salary = data[3].toInt()
        val skills = data[4].split("|")

        employees.add(Employee(EmployeeID(employeeID), employeeName, department, salary, skills))
    }
    return employees
}

fun newEmployeeApi(employees: List<Employee>): EmployeeApi {
    return EmployeesPortal(employees)
}

fun main() {
    val employeesCSVLines = FileReader.readFileInResources("lab9/employees.csv")
    val employees = parseEmployees(employeesCSVLines)

    val employeeApi: EmployeeApi = newEmployeeApi(employees)
}
