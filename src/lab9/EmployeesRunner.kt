package lab9

import common.FileReader

fun parseEmployees(employeesCSVLines: List<String>): List<Employee> {
    //TODO("Implement parsing of employees")
    val iterable = employeesCSVLines.drop(1)
    val employees = mutableListOf<Employee>()
    iterable.forEach { line ->
        val data = line.split(",");
        println("data: " + data[0].trim() + " " + data[1].trim()+ " " + data[2].trim() + " " + data[3].trim() + data[4].trim().split("|"));
        employees.add(Employee(EmployeeID(data[0]), data[1], Department(data[2]), data[3].toInt(), data[4].split("|")))
    }
    return employees;
}

fun newEmployeeApi(employees: List<Employee>): EmployeeApi {
    //TODO("Instantiate EmployeeApi")
    return EmployeesPortal(employees);
}

fun main() {
    val employeesCSVLines = FileReader.readFileInResources("resources/lab9/employees.csv")
    val employees = parseEmployees(employeesCSVLines)

    val employeeApi : EmployeeApi = newEmployeeApi(employees)
}
