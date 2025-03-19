package com.example.Office.Management.System.Controller;

import com.example.Office.Management.System.Entity.NewEmployeeTask;
import com.example.Office.Management.System.Entity.User;
import com.example.Office.Management.System.Service.NewEmployeeTaskService;
import com.example.Office.Management.System.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/new-employee-tasks")
public class NewEmployeeTaskController {

    @Autowired
    private NewEmployeeTaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/add")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new NewEmployeeTask());
        return "/task/new-employee-task-form";
    }

    @PostMapping("/add")
    public String addTask(@ModelAttribute NewEmployeeTask task, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = principal.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        task.setUser(user);
        task.setStatus(NewEmployeeTask.TaskStatus.PENDING);
        taskService.addTask(task);

        return "redirect:/new-employee-tasks/userList";
    }

    @GetMapping("/list")
    public String listTasks(Principal principal, Model model) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = principal.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        List<NewEmployeeTask> tasks = taskService.getTasksForUser(user.getId());
        model.addAttribute("tasks", tasks);
        return "/task/new-employee-task-list";
    }

    @GetMapping("/userList")
    public String employeeListTasks(Principal principal, Model model) {
        if (principal == null) {
            throw new RuntimeException("User not authenticated");
        }

        String email = principal.getName();
        User user = userService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        List<NewEmployeeTask> tasks = taskService.getTasksForUser(user.getId());
        model.addAttribute("tasks", tasks);
        return "/task/employee-task-list";
    }

    @PostMapping("/{id}/update")
    public String updateTaskStatus(@PathVariable Long id, @RequestParam("status") NewEmployeeTask.TaskStatus status) {
        NewEmployeeTask task = new NewEmployeeTask();
        task.setStatus(status);
        taskService.updateTaskStatus(id, task);
        return "redirect:/new-employee-tasks/list";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/new-employee-tasks/list";
    }
}
