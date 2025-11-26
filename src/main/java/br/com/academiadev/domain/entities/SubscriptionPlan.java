package main.java.br.com.academiadev.domain.entities;

public interface SubscriptionPlan {
    boolean canEnroll(int currentActiveEnrollments);
}
