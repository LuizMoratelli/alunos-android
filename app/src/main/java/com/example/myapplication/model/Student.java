package com.example.myapplication.model;

import java.io.Serializable;

public class Student implements Serializable{
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String website;
    private String picture;
    private Double grade;

    public static class StudentSerializer implements Serializable {
        private Long id;
        private String name;
        private String phone;
        private String address;
        private String website;
        private String picture;
        private Double grade;

        public StudentSerializer(Student student) {
            this.id = student.getId();
            this.name = student.getName();
            this.phone = student.getPhone();
            this.address = student.getAddress();
            this.website = student.getWebsite();
            this.picture = student.getPicture();
            this.grade = student.getGrade();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        // return String.format("Student { id=%s;name=%s;phone=%s;address=%s}", id, name, phone, address);
        return "Student {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", website='" + website + '\'' +
                ", picture='" + picture + '\'' +
                ", grade=" + grade +
                '}';
    }
}
