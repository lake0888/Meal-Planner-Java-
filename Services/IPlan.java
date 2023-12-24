package mealplanner.Services;

import mealplanner.Model.Plan.PlanEntity;

import java.util.List;

public interface IPlan {
    List<PlanEntity> findAll();

    PlanEntity save(PlanEntity planEntity);

    int lastId();
}
