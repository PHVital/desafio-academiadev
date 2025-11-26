package main.java.br.com.academiadev.domain.entities;

public class BasicPlan implements SubscriptionPlan {
    @Override
    public boolean canEnroll(int currentActiveEnrollments) {
        return currentActiveEnrollments < 3;
    }
}
