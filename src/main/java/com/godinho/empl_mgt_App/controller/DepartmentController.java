package com.godinho.empl_mgt_App.controller;

import com.godinho.empl_mgt_App.model.Department;
import com.godinho.empl_mgt_App.model.Department;
import com.godinho.empl_mgt_App.model.Employee;
import com.godinho.empl_mgt_App.service.DepartmentService;
import com.godinho.empl_mgt_App.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    @Autowired
    public DepartmentController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping
    public String showDepartmentList(Model model) {
        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("departments", departments);
        return "departmentList";
    }

    @GetMapping("/{id}")
    public String showDepartmentDetails(@PathVariable Long id, Model model) {
        Department department = departmentService.getDepartmentById(id);
        if (department == null) {
            return "redirect:/departments";
        }
        List<Employee> employees = employeeService.getEmployeesByDepartmentId(id);
        model.addAttribute("department", department);
        model.addAttribute("employees", employees);
        return "departmentDetails";
    }

    @GetMapping("/add")
    public String showAddDepartmentForm(Model model) {
        model.addAttribute("department", new Department());
        return "addDepartment";
    }

    @PostMapping("/add")
    public String addDepartment(@ModelAttribute("department") Department department, Model model) {
        // Check if the department already exists
        if (departmentService.doesDepartmentExist(department.getName())) {
            model.addAttribute("errorMessage", "Department with the same name already exists.");
            return "addDepartment"; // Return to the form with the error message
        }

        // If department doesn't exist, save it to the database
        departmentService.saveDepartment(department);
        return "redirect:/departments"; // Redirect to the department list page
    }
    @PostMapping("/delete")
    public String deleteDepartment(@RequestParam("id") Long departmentId) {
        // Call the service layer to delete the department
        departmentService.deleteDepartment(departmentId);

        // Redirect to the department list page after deletion
        return "redirect:/departments";
    }
}
