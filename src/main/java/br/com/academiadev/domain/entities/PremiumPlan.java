package main.java.br.com.academiadev.domain.entities;

public class PremiumPlan implements SubscriptionPlan {
    @Override
    public boolean canEnroll(int currentActiveEnrollments) {
        return true;
    }
}
