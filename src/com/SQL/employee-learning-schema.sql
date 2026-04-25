-- ============================================
-- EMPLOYEE DATABASE LEARNING SCHEMA
-- Practice: JOINs, Stored Procedures, Views, Complex Queries, Subqueries
-- ============================================

-- 1. CREATE LOCATION TABLE
CREATE TABLE Location (
    location_id INT PRIMARY KEY AUTO_INCREMENT,
    location_name VARCHAR(100) NOT NULL,
    city VARCHAR(50) NOT NULL,
    state VARCHAR(50),
    country VARCHAR(50)
);

-- 2. CREATE DEPARTMENT TABLE
CREATE TABLE Department (
    dept_id INT PRIMARY KEY AUTO_INCREMENT,
    dept_name VARCHAR(100) NOT NULL,
    location_id INT,
    FOREIGN KEY (location_id) REFERENCES Location(location_id)
);

-- 3. CREATE EMPLOYEE TABLE
CREATE TABLE Employee (
    emp_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    hire_date DATE,
    salary DECIMAL(10, 2),
    dept_id INT,
    manager_id INT,
    FOREIGN KEY (dept_id) REFERENCES Department(dept_id),
    FOREIGN KEY (manager_id) REFERENCES Employee(emp_id)
);

-- 4. CREATE PROJECT TABLE
CREATE TABLE Project (
    project_id INT PRIMARY KEY AUTO_INCREMENT,
    project_name VARCHAR(150) NOT NULL,
    start_date DATE,
    end_date DATE,
    budget DECIMAL(12, 2),
    dept_id INT,
    FOREIGN KEY (dept_id) REFERENCES Department(dept_id)
);

-- 5. CREATE EMPLOYEE_PROJECT TABLE (Many-to-Many)
CREATE TABLE Employee_Project (
    emp_id INT,
    project_id INT,
    hours_worked DECIMAL(6, 2),
    PRIMARY KEY (emp_id, project_id),
    FOREIGN KEY (emp_id) REFERENCES Employee(emp_id),
    FOREIGN KEY (project_id) REFERENCES Project(project_id)
);

-- 6. CREATE SALARY_HISTORY TABLE
CREATE TABLE Salary_History (
    salary_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_id INT,
    old_salary DECIMAL(10, 2),
    new_salary DECIMAL(10, 2),
    change_date DATE,
    FOREIGN KEY (emp_id) REFERENCES Employee(emp_id)
);

-- ============================================
-- INSERT SAMPLE DATA
-- ============================================

-- Insert Locations
INSERT INTO Location (location_name, city, state, country) VALUES
('New York Office', 'New York', 'NY', 'USA'),
('San Francisco Office', 'San Francisco', 'CA', 'USA'),
('Chicago Office', 'Chicago', 'IL', 'USA'),
('London Office', 'London', 'England', 'UK'),
('Bangalore Office', 'Bangalore', 'Karnataka', 'India');

-- Insert Departments
INSERT INTO Department (dept_name, location_id) VALUES
('HR', 1),
('IT', 2),
('Finance', 1),
('Sales', 3),
('Marketing', 5),
('Operations', 4);

-- Insert Employees (Manager-Employee hierarchy)
INSERT INTO Employee (emp_name, email, phone, hire_date, salary, dept_id, manager_id) VALUES
('John Smith', 'john.smith@company.com', '1234567890', '2015-01-15', 120000, 1, NULL),  -- HR Manager
('Sarah Johnson', 'sarah.johnson@company.com', '1234567891', '2016-05-20', 110000, 2, NULL),  -- IT Manager
('Mike Brown', 'mike.brown@company.com', '1234567892', '2017-03-10', 105000, 3, NULL),  -- Finance Manager
('Lisa White', 'lisa.white@company.com', '1234567893', '2018-07-22', 95000, 4, NULL),  -- Sales Manager
('David Lee', 'david.lee@company.com', '1234567894', '2019-02-14', 98000, 5, NULL),  -- Marketing Manager
('Emily Davis', 'emily.davis@company.com', '1234567895', '2020-01-10', 90000, 1, 1),  -- HR Employee
('Robert Wilson', 'robert.wilson@company.com', '1234567896', '2018-09-05', 85000, 2, 2),  -- IT Developer
('Jessica Miller', 'jessica.miller@company.com', '1234567897', '2019-04-18', 82000, 2, 2),  -- IT Developer
('James Taylor', 'james.taylor@company.com', '1234567898', '2020-06-15', 79000, 3, 3),  -- Finance Analyst
('Patricia Anderson', 'patricia.anderson@company.com', '1234567899', '2021-03-22', 75000, 4, 4),  -- Sales Rep
('John Martinez', 'john.martinez@company.com', '1234567900', '2021-08-12', 72000, 4, 4),  -- Sales Rep
('Mary Thompson', 'mary.thompson@company.com', '1234567901', '2022-01-17', 88000, 5, 5),  -- Marketing Specialist
('Charles Garcia', 'charles.garcia@company.com', '1234567902', '2022-05-30', 77000, 2, 2),  -- IT Support
('Jennifer Jackson', 'jennifer.jackson@company.com', '1234567903', '2022-09-10', 80000, 3, 3),  -- Finance Analyst
('Richard Harris', 'richard.harris@company.com', '1234567904', '2023-02-20', 76000, 1, 1);  -- HR Specialist

-- Insert Projects
INSERT INTO Project (project_name, start_date, end_date, budget, dept_id) VALUES
('Mobile App Development', '2023-01-01', '2023-12-31', 500000, 2),
('Cloud Migration', '2023-03-15', '2024-03-15', 750000, 2),
('Financial Audit System', '2023-06-01', '2023-12-31', 350000, 3),
('Sales Dashboard', '2023-07-01', '2024-06-30', 250000, 4),
('Marketing Campaign', '2023-09-01', '2023-12-31', 150000, 5),
('HR Management System', '2023-10-01', '2024-09-30', 400000, 1),
('Customer Analytics', '2024-01-01', '2024-12-31', 300000, 2);

-- Insert Employee-Project Assignments
INSERT INTO Employee_Project (emp_id, project_id, hours_worked) VALUES
(2, 1, 150),  -- Sarah on Mobile App
(7, 1, 120),  -- Robert on Mobile App
(8, 1, 140),  -- Jessica on Mobile App
(2, 2, 180),  -- Sarah on Cloud Migration
(7, 2, 160),  -- Robert on Cloud Migration
(13, 2, 170), -- Charles on Cloud Migration
(3, 3, 140),  -- Mike on Financial Audit
(9, 3, 130),  -- James on Financial Audit
(14, 3, 125), -- Jennifer on Financial Audit
(4, 4, 150),  -- Lisa on Sales Dashboard
(10, 4, 120), -- Patricia on Sales Dashboard
(11, 4, 110), -- John on Sales Dashboard
(5, 5, 130),  -- David on Marketing Campaign
(12, 5, 125), -- Mary on Marketing Campaign
(1, 6, 160),  -- John on HR System
(6, 6, 140),  -- Emily on HR System
(15, 6, 135); -- Richard on HR System

-- Insert Salary History
INSERT INTO Salary_History (emp_id, old_salary, new_salary, change_date) VALUES
(1, 115000, 120000, '2023-01-01'),
(2, 105000, 110000, '2023-02-15'),
(3, 100000, 105000, '2023-03-01'),
(7, 80000, 85000, '2023-04-01'),
(8, 78000, 82000, '2023-05-01'),
(9, 75000, 79000, '2023-06-01'),
(10, 70000, 75000, '2023-07-01');

-- ============================================
-- PRACTICE QUERIES
-- ============================================

-- 1. SIMPLE JOIN - Employee with Department
SELECT e.emp_name, d.dept_name, l.location_name
FROM Employee e
JOIN Department d ON e.dept_id = d.dept_id
JOIN Location l ON d.location_id = l.location_id;

-- 2. SELF JOIN - Employee with Manager
SELECT e.emp_name AS Employee, m.emp_name AS Manager
FROM Employee e
LEFT JOIN Employee m ON e.manager_id = m.emp_id;

-- 3. AGGREGATE with GROUP BY and HAVING
SELECT d.dept_name, COUNT(e.emp_id) AS employee_count, AVG(e.salary) AS avg_salary
FROM Department d
LEFT JOIN Employee e ON d.dept_id = e.dept_id
GROUP BY d.dept_id, d.dept_name
HAVING COUNT(e.emp_id) >= 2;

-- 4. SUBQUERY - Employees earning more than department average
SELECT e.emp_name, e.salary, d.dept_name
FROM Employee e
JOIN Department d ON e.dept_id = d.dept_id
WHERE e.salary > (
    SELECT AVG(salary) FROM Employee 
    WHERE dept_id = d.dept_id
);

-- 5. MULTIPLE JOINs - Employee, Project, Department
SELECT e.emp_name, p.project_name, ep.hours_worked, d.dept_name
FROM Employee e
JOIN Employee_Project ep ON e.emp_id = ep.emp_id
JOIN Project p ON ep.project_id = p.project_id
JOIN Department d ON e.dept_id = d.dept_id
ORDER BY e.emp_name;

-- 6. COMPLEX QUERY - Projects with highest hour contribution
SELECT p.project_name, SUM(ep.hours_worked) AS total_hours, COUNT(DISTINCT ep.emp_id) AS team_size
FROM Project p
JOIN Employee_Project ep ON p.project_id = ep.project_id
GROUP BY p.project_id, p.project_name
ORDER BY total_hours DESC;

-- ============================================
-- VIEWS FOR LEARNING
-- ============================================

-- Create View: Employee Details with Department and Location
CREATE VIEW vw_employee_details AS
SELECT 
    e.emp_id,
    e.emp_name,
    e.email,
    e.salary,
    d.dept_name,
    l.location_name,
    CASE 
        WHEN e.manager_id IS NULL THEN 'Manager'
        ELSE 'Employee'
    END AS role
FROM Employee e
JOIN Department d ON e.dept_id = d.dept_id
JOIN Location l ON d.location_id = l.location_id;

-- Create View: Employee with Manager Details
CREATE VIEW vw_employee_manager AS
SELECT 
    e.emp_id,
    e.emp_name,
    e.salary,
    COALESCE(m.emp_name, 'No Manager') AS manager_name
FROM Employee e
LEFT JOIN Employee m ON e.manager_id = m.emp_id;

-- Create View: Department Salary Summary
CREATE VIEW vw_dept_salary_summary AS
SELECT 
    d.dept_name,
    COUNT(e.emp_id) AS total_employees,
    AVG(e.salary) AS avg_salary,
    MAX(e.salary) AS max_salary,
    MIN(e.salary) AS min_salary,
    SUM(e.salary) AS total_salary
FROM Department d
LEFT JOIN Employee e ON d.dept_id = e.dept_id
GROUP BY d.dept_id, d.dept_name;

-- ============================================
-- STORED PROCEDURES FOR LEARNING
-- ============================================

-- Procedure 1: Get employees by department
DELIMITER //
CREATE PROCEDURE sp_get_employees_by_dept(IN p_dept_name VARCHAR(100))
BEGIN
    SELECT 
        e.emp_id,
        e.emp_name,
        e.salary,
        d.dept_name
    FROM Employee e
    JOIN Department d ON e.dept_id = d.dept_id
    WHERE d.dept_name = p_dept_name
    ORDER BY e.emp_name;
END //
DELIMITER ;

-- Procedure 2: Get department statistics
DELIMITER //
CREATE PROCEDURE sp_dept_statistics()
BEGIN
    SELECT 
        d.dept_name,
        COUNT(e.emp_id) AS emp_count,
        AVG(e.salary) AS avg_salary,
        MAX(e.salary) AS max_salary,
        MIN(e.salary) AS min_salary
    FROM Department d
    LEFT JOIN Employee e ON d.dept_id = e.dept_id
    GROUP BY d.dept_id, d.dept_name;
END //
DELIMITER ;

-- Procedure 3: Give salary raise
DELIMITER //
CREATE PROCEDURE sp_give_raise(IN p_emp_id INT, IN p_raise_percent DECIMAL(5, 2))
BEGIN
    DECLARE v_old_salary DECIMAL(10, 2);
    DECLARE v_new_salary DECIMAL(10, 2);
    
    SELECT salary INTO v_old_salary FROM Employee WHERE emp_id = p_emp_id;
    SET v_new_salary = v_old_salary * (1 + p_raise_percent / 100);
    
    UPDATE Employee SET salary = v_new_salary WHERE emp_id = p_emp_id;
    INSERT INTO Salary_History VALUES (NULL, p_emp_id, v_old_salary, v_new_salary, CURDATE());
    
    SELECT CONCAT('Salary updated from ', v_old_salary, ' to ', v_new_salary) AS result;
END //
DELIMITER ;

-- ============================================
-- SAMPLE TEST QUERIES
-- ============================================

-- Test 1: List all employees with their managers and departments
SELECT * FROM vw_employee_details;

-- Test 2: Call stored procedure
-- CALL sp_get_employees_by_dept('IT');
-- CALL sp_dept_statistics();

-- Test 3: Find employees working on specific projects
SELECT DISTINCT e.emp_name
FROM Employee e
WHERE e.emp_id IN (
    SELECT DISTINCT ep.emp_id 
    FROM Employee_Project ep
    JOIN Project p ON ep.project_id = p.project_id
    WHERE p.project_name = 'Cloud Migration'
);

-- Test 4: Find highest earning employee in each department
SELECT d.dept_name, e.emp_name, e.salary
FROM Employee e
JOIN Department d ON e.dept_id = d.dept_id
WHERE e.salary = (
    SELECT MAX(salary) 
    FROM Employee 
    WHERE dept_id = d.dept_id
);

-- ============================================
-- END OF LEARNING SCHEMA
-- ============================================
