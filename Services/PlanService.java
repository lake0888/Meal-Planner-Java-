package mealplanner.Services;

import mealplanner.DAO.PlanDAO;
import mealplanner.Model.Plan.PlanEntity;

import java.util.List;

public class PlanService implements IPlan {

    private final PlanDAO planDAO;
    public PlanService() {
        this.planDAO = new PlanDAO();
    }
    @Override
    public List<PlanEntity> findAll() {
        return planDAO.findAll();
    }

    @Override
    public PlanEntity save(PlanEntity planEntity) {
        return planDAO.save(planEntity);
    }

    @Override
    public int lastId() {
        return planDAO.lastId();
    }
}
