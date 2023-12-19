package model;

import java.io.Serializable;

public class Employee implements Serializable {

        private String Employeeid;
        private String name;
        private int age;

        public Employee(String Employeeid, String name, int age) {
            this.Employeeid = Employeeid;
            this.name = name;
            this.age = age;
        }

    public Employee() {

    }

    public  String getEmployeeid() {
            return Employeeid;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Employee ID: " + Employeeid + ", Name: " + name + ", Age: " + age;
        }
    }


