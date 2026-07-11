package lab9

class EmployeesPortal(val employees: List<Employee>) : EmployeeApi {

    override fun findHighestPaidEmployee(): Employee? {
        //TODO("Not yet implemented")
        return employees.maxByOrNull { employee -> employee.salary };
    }

    override fun getEmployeesByDepartment(department: Department): List<Employee> {
        //TODO("Not yet implemented")
        return employees.filter {employee -> employee.department == department};
    }

    override fun getEmployeesBySalaryRange(salaryRange: IntRange): List<Employee> {
        //TODO("Not yet implemented")
        return employees.filter { employee -> employee.salary in IntRange(salaryRange.first, salaryRange.last) }
    }

    override fun calculateAverageSalaryByDepartment(department: Department): Double {
        // TODO("Not yet implemented")
        val employeesOfDepartment =  getEmployeesByDepartment(department);
        return (employeesOfDepartment.sumOf { employee -> employee.salary }.toDouble() / employeesOfDepartment.size);
    }

    override fun findMostCommonSkill(): String {
        // TODO("Not yet implemented")
        val skills = employees.flatMap { employee -> employee.skills }
        val skillCounts = skills.groupingBy { skill -> skill }.eachCount();
        return skillCounts.maxByOrNull { it.value }.toString().split("=")[0];
    }

}