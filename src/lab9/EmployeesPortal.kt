package lab9

class EmployeesPortal (
    private val employees: List<Employee>
): EmployeeApi {
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
        val departmentEmployees = employees.filter { it.department == department }

        return if(departmentEmployees.isEmpty()) {
            0.0
        } else {
            departmentEmployees
                .map { it.salary }
                .average()
        }
    }

    override fun findMostCommonSkill(): String {
        return employees
            .flatMap{it.skills}
            .groupBy { it }
            .maxByOrNull { it.value.size }
            ?.key?: ""
    }
}