package lab9

data class Department(val departmentName: String) // provisional implementation, replace with real one

data class Employee(
    val employeeID: EmployeeID,
    val name: String,
    val department: Department,
    val salary: Int,
    val skills: List<String>
)

@JvmInline
value class EmployeeID(val name: String) // provisional implementation, replace with real one