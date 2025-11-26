package main.java.br.com.academiadev.domain.entities;

import java.util.ArrayList;
import java.util.List;

public class Student extends User{
    private SubscriptionPlan subscriptionPlan;
    private List<Enrollment> enrollments = new ArrayList<>();

    public Student(String name, String email, SubscriptionPlan subscriptionPlan) {
        super(name, email);
        this.subscriptionPlan = subscriptionPlan;
    }

    public boolean canEnroll() {
        int activeCourses = (int) enrollments.stream()
                .filter(Enrollment::isActive)
                .count();
        return subscriptionPlan.canEnroll(activeCourses);
    }

    public void addEnrollment(Enrollment enrollment) {
        this.enrollments.add(enrollment);
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }
}
