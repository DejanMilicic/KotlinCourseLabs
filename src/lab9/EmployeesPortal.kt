package lab9

class EmployeesPortal(val employees: List<Employee>) : EmployeeApi {
    override fun findHighestPaidEmployee(): Employee? {
        return employees.maxByOrNull { it.salary }
    }

    override fun getEmployeesByDepartment(department: Department): List<Employee> {
        return employees.filter { it.department == department }
    }

    override fun getEmployeesBySalaryRange(salaryRange: IntRange): List<Employee> {
        return employees.filter { it.salary in salaryRange }
    }

    override fun calculateAverageSalaryByDepartment(department: Department): Double {
        val deptEmployees = employees.filter { it.department == department }
        if (deptEmployees.isEmpty()) return 0.0
        return deptEmployees
            .map { it.salary }
            .average()
    }

    override fun findMostCommonSkill(): String =
        employees
            .flatMap { it.skills }
            .groupingBy { it }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key ?: ""

}
