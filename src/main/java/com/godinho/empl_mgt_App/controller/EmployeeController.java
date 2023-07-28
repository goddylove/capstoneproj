package com.godinho.empl_mgt_App.controller;

import com.godinho.empl_mgt_App.dto.EmployeeDTO;
import com.godinho.empl_mgt_App.model.Department;
import com.godinho.empl_mgt_App.model.Employee;
import com.godinho.empl_mgt_App.repo.EmployeeRepo;
import com.godinho.empl_mgt_App.service.DepartmentService;
import com.godinho.empl_mgt_App.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepo employeeRepo;
    private final DepartmentService departmentService;
    private final EmployeeService employeeService;
    @Autowired
    public EmployeeController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;

        this.employeeService = employeeService;
    }

    @GetMapping({"/showEmployees", "/list"})
    public ModelAndView showEmployees(){
        ModelAndView mav = new ModelAndView("list_Employees");
        List<Employee> employeeList= employeeRepo.findAll();
        mav.addObject("employees", employeeList);
        return mav;
    }


    @GetMapping("/addEmployeeForm")
    public String showAddEmployeeForm(Model model) {
        // Fetch the list of departments from the database
        List<Department> departments = departmentService.getAllDepartments();

        // Create a new EmployeeDTO object to bind the form fields
        EmployeeDTO employeeDTO = new EmployeeDTO();

        // Add the list of departments and the EmployeeDTO object to the model
        model.addAttribute("departments", departments);
        model.addAttribute("employeeDTO", employeeDTO);

        // Return the name of the Thymeleaf template to render
        return "add-employee-form";
    }
    @PostMapping("/add")
    public String addEmployee(@ModelAttribute("employeeDTO") EmployeeDTO employeeDTO) {
        employeeService.createEmployee(employeeDTO);
        return "redirect:/list"; // Redirect to the list of employees after adding a new employee.
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateEmployeeForm(@RequestParam Long employeeId) {
        ModelAndView mav = new ModelAndView("add-employee-form");
        Optional<Employee> optionalEmployee = employeeRepo.findById(employeeId);

        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            // Map the Employee object to EmployeeDTO for editing
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setName(employee.getName());
            employeeDTO.setEmail(employee.getEmail());
            employeeDTO.setDepartmentName(employee.getDepartment().getName());

            mav.addObject("employeeDTO", employeeDTO);
            mav.addObject("employeeId", employeeId); // Add employeeId to keep track of the specific employee

            // Add the list of departments to populate the dropdown

            List<Department> departments = departmentService.getAllDepartments();
            mav.addObject("departments", departments);
        } else {
            mav.addObject("errorMessage", "Employee not found with ID: " + employeeId);
        }

        return mav;
    }


    @PostMapping("/updateEmployee")
    public String updateEmployee(@ModelAttribute("employeeDTO") EmployeeDTO employeeDTO,
                                 @RequestParam Long employeeId, Model model) {
        Employee existingEmployee = employeeRepo.findById(employeeId).orElse(null);
        if (existingEmployee == null) {
            model.addAttribute("errorMessage", "Employee not found with ID: " + employeeId);
            return "error-page"; // Create an error page to display the error message
        }

        // Update the existing employee with the new details from employeeDTO
        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setEmail(employeeDTO.getEmail());

        Department department = departmentService.getDepartmentByName(employeeDTO.getDepartmentName());
        if (department != null) {
            existingEmployee.setDepartment(department);
        } else {
            model.addAttribute("errorMessage", "Department not found with name: " + employeeDTO.getDepartmentName());
            return "error-page"; // Create an error page to display the error message
        }

        // Save the updated employee to the database
        employeeRepo.save(existingEmployee);

        List<Department> departments = departmentService.getAllDepartments();
        model.addAttribute("departments", departments);

        return "redirect:/list"; // Redirect to the list of employees after updating an employee.
    }



    @GetMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam Long employeeId) {
        employeeRepo.deleteById(employeeId);
        return "redirect:/list";
    }
}
