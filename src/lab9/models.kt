package lab9

data class Department(val departmentName: String)

@JvmInline
value class EmployeeID(val id: String)

class Employee(
    val name: String,
    val department: Department,
    val employeeID: EmployeeID,
    val salary: Int,
    val skills: List<String>
)