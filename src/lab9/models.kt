package lab9

@JvmInline
value class EmployeeID(val id: String)

data class Department(val departmentName: String)

data class Employee(
    val employeeID: EmployeeID,
    val name: String,
    val department: Department,
    val salary: Int,
    val skills: List<String>
)