-- ============================================
-- SQL PRACTICE QUESTIONS
-- ============================================

-- BASIC QUERIES (START HERE)
-- ============================================

-- Q1: List all employees with their department names
-- Write your query here:


-- Q2: Get employee name and their manager name
-- Write your query here:


-- Q3: Find all employees in IT department
-- Write your query here:


-- Q4: List employees earning more than 80000
-- Write your query here:


-- INTERMEDIATE QUERIES (Try these next)
-- ============================================

-- Q5: Count employees in each department
-- Write your query here:


-- Q6: Find department with maximum average salary
-- Write your query here:


-- Q7: List employees and their projects they are working on
-- Write your query here:


-- Q8: Find employees who are managers (no manager assigned to them)
-- Write your query here:


-- Q9: Get total hours worked on each project
-- Write your query here:


-- Q10: Find employees earning above their department average
-- Write your query here:


-- ADVANCED QUERIES (Challenge yourself)
-- ============================================

-- Q11: List all employees with their manager, department, and location
-- Hint: Use 3 JOINs (Employee-Manager Self Join + Department + Location)
-- Write your query here:


-- Q12: Find departments with more than 2 employees and calculate their avg salary
-- Hint: Use GROUP BY + HAVING
-- Write your query here:


-- Q13: Get project name, team size, and total hours for each project
-- Hint: Use JOINs with aggregation
-- Write your query here:


-- Q14: Find the highest earning employee in each department
-- Hint: Use subquery in WHERE clause
-- Write your query here:


-- Q15: List employees not assigned to any project
-- Hint: Use LEFT JOIN + WHERE IS NULL
-- Write your query here:


-- COMPLEX QUERIES WITH SUBQUERIES
-- ============================================

-- Q16: Find employees earning more than the company average salary
-- Write your query here:


-- Q17: Get departments where average salary is above company average
-- Write your query here:


-- Q18: Find employees working on only one project
-- Write your query here:


-- Q19: Get the second highest paid employee in IT department
-- Write your query here:


-- Q20: List all employees who work on the same project as the employee 'Sarah Johnson'
-- Write your query here:


-- VIEWS AND STORED PROCEDURES
-- ============================================

-- Q21: Create a VIEW showing employee hierarchy (emp_name, manager_name, dept_name)
-- Write your CREATE VIEW statement:


-- Q22: Create a STORED PROCEDURE to get employees by department name
-- The procedure should accept department name as parameter
-- Write your CREATE PROCEDURE:


-- Q23: Create a VIEW showing department-wise salary summary
-- Should show: dept_name, total_employees, avg_salary, max_salary, min_salary
-- Write your CREATE VIEW:


-- Q24: Create a STORED PROCEDURE that gives salary raise to an employee
-- Parameters: emp_id, raise_percentage
-- Should also insert into Salary_History table
-- Write your CREATE PROCEDURE:


-- AGGREGATE WITH MULTIPLE CONDITIONS
-- ============================================

-- Q25: Get salary range (max-min) for each department with salary details
-- Write your query here:


-- Q26: Find employees in top 3 paying departments
-- Write your query here:


-- Q27: Get cumulative salary by department
-- Write your query here:


-- STRING AND DATE FUNCTIONS
-- ============================================

-- Q28: Get employees hired in 2023 with their years of service
-- Write your query here:


-- Q29: Get employees whose email contains 'company.com' and salary > 75000
-- Write your query here:


-- Q30: List employees grouped by hire year and count
-- Write your query here:


-- ============================================
-- ANSWERS SECTION (Check after attempting)
-- ============================================

/*
-- A1: List all employees with their department names
SELECT e.emp_name, d.dept_name 
FROM Employee e 
JOIN Department d ON e.dept_id = d.dept_id;

-- A2: Get employee name and their manager name
SELECT e.emp_name AS Employee, m.emp_name AS Manager 
FROM Employee e 
LEFT JOIN Employee m ON e.manager_id = m.emp_id;

-- A3: Find all employees in IT department
SELECT e.emp_name, e.salary 
FROM Employee e 
JOIN Department d ON e.dept_id = d.dept_id 
WHERE d.dept_name = 'IT';

-- A4: List employees earning more than 80000
SELECT emp_name, salary 
FROM Employee 
WHERE salary > 80000;

-- A5: Count employees in each department
SELECT d.dept_name, COUNT(e.emp_id) AS emp_count 
FROM Department d 
LEFT JOIN Employee e ON d.dept_id = e.dept_id 
GROUP BY d.dept_id, d.dept_name;

-- A6: Find department with maximum average salary
SELECT d.dept_name, AVG(e.salary) AS avg_salary 
FROM Department d 
JOIN Employee e ON d.dept_id = e.dept_id 
GROUP BY d.dept_id, d.dept_name 
ORDER BY avg_salary DESC LIMIT 1;

-- A7: List employees and their projects they are working on
SELECT e.emp_name, p.project_name, ep.hours_worked 
FROM Employee e 
JOIN Employee_Project ep ON e.emp_id = ep.emp_id 
JOIN Project p ON ep.project_id = p.project_id;

-- A8: Find employees who are managers
SELECT DISTINCT e.emp_name 
FROM Employee e 
WHERE e.emp_id IN (SELECT manager_id FROM Employee WHERE manager_id IS NOT NULL);

-- A9: Get total hours worked on each project
SELECT p.project_name, SUM(ep.hours_worked) AS total_hours 
FROM Project p 
JOIN Employee_Project ep ON p.project_id = ep.project_id 
GROUP BY p.project_id, p.project_name;

-- A10: Find employees earning above their department average
SELECT e.emp_name, e.salary, d.dept_name 
FROM Employee e 
JOIN Department d ON e.dept_id = d.dept_id 
WHERE e.salary > (SELECT AVG(salary) FROM Employee WHERE dept_id = d.dept_id);

-- A11: List all employees with manager, department, and location
SELECT e.emp_name, 
       COALESCE(m.emp_name, 'No Manager') AS manager,
       d.dept_name, 
       l.location_name 
FROM Employee e 
LEFT JOIN Employee m ON e.manager_id = m.emp_id 
JOIN Department d ON e.dept_id = d.dept_id 
JOIN Location l ON d.location_id = l.location_id;

-- A12: Departments with >2 employees and avg salary
SELECT d.dept_name, COUNT(e.emp_id) AS emp_count, AVG(e.salary) AS avg_salary 
FROM Department d 
JOIN Employee e ON d.dept_id = e.dept_id 
GROUP BY d.dept_id, d.dept_name 
HAVING COUNT(e.emp_id) > 2;

-- A13: Project name, team size, total hours
SELECT p.project_name, COUNT(DISTINCT ep.emp_id) AS team_size, SUM(ep.hours_worked) AS total_hours 
FROM Project p 
JOIN Employee_Project ep ON p.project_id = ep.project_id 
GROUP BY p.project_id, p.project_name;

-- A14: Highest earning employee in each department
SELECT d.dept_name, e.emp_name, e.salary 
FROM Employee e 
JOIN Department d ON e.dept_id = d.dept_id 
WHERE e.salary = (SELECT MAX(salary) FROM Employee WHERE dept_id = d.dept_id);

-- A15: Employees not assigned to any project
SELECT e.emp_name 
FROM Employee e 
LEFT JOIN Employee_Project ep ON e.emp_id = ep.emp_id 
WHERE ep.emp_id IS NULL;

-- A16: Employees earning above company average
SELECT e.emp_name, e.salary 
FROM Employee e 
WHERE e.salary > (SELECT AVG(salary) FROM Employee);

-- A17: Departments with above average salary
SELECT d.dept_name, AVG(e.salary) AS avg_salary 
FROM Department d 
JOIN Employee e ON d.dept_id = e.dept_id 
GROUP BY d.dept_id, d.dept_name 
HAVING AVG(e.salary) > (SELECT AVG(salary) FROM Employee);

-- A18: Employees working on only one project
SELECT e.emp_name, COUNT(ep.project_id) AS project_count 
FROM Employee e 
JOIN Employee_Project ep ON e.emp_id = ep.emp_id 
GROUP BY e.emp_id, e.emp_name 
HAVING COUNT(ep.project_id) = 1;

-- A19: Second highest paid employee in IT
SELECT e.emp_name, e.salary 
FROM Employee e 
JOIN Department d ON e.dept_id = d.dept_id 
WHERE d.dept_name = 'IT' 
ORDER BY e.salary DESC LIMIT 1, 1;

-- A20: Employees on same project as Sarah Johnson
SELECT DISTINCT e.emp_name 
FROM Employee e 
WHERE e.emp_id IN (
    SELECT ep.emp_id FROM Employee_Project ep 
    WHERE ep.project_id IN (
        SELECT ep2.project_id FROM Employee_Project ep2 
        JOIN Employee e2 ON ep2.emp_id = e2.emp_id 
        WHERE e2.emp_name = 'Sarah Johnson'
    )
) AND e.emp_name != 'Sarah Johnson';
*/

-- ============================================
-- BONUS: INDEX PRACTICE
-- ============================================

-- Create indexes to improve query performance
-- Q31: Create an index on Employee email column
-- Write your CREATE INDEX:


-- Q32: Create a composite index on Employee department_id and salary
-- Write your CREATE INDEX:


-- ============================================
-- END OF PRACTICE QUESTIONS
-- ============================================
