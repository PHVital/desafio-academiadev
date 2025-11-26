package main.java.br.com.academiadev.domain.entities;

import main.java.br.com.academiadev.domain.exceptions.BusinessException;

public class Enrollment {
    private Student student;
    private Course course;
    private int progressPercentage;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.progressPercentage = 0;
    }

    public void updateProgress(int newProgress) {
        if (newProgress < 0 || newProgress > 100) {
            throw new BusinessException("Progresso deve ser entre 0 e 100");
        }
        this.progressPercentage = newProgress;
    }

    public boolean isActive() {
        return true;
    }

    public Course getCourse() {
        return course;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public Student getStudent() {
        return student;
    }
}
