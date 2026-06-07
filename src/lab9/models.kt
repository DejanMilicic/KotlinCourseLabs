package lab9


// TODO Implement Department entity from Task 1
data class Department(val departmentName: String)

// TODO Implement EmployeeID entity from Task 1
@JvmInline
value class EmployeeID(val id: String)

// TODO Implement Employee entity from Task 1
data class Employee(
    val name: String,
    val department: Department,
    val employeeId: EmployeeID,
    val salary: Int,
    val skills: List<String>
) // provisional implementation, replace with real one