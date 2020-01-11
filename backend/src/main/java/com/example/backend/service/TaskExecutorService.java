package com.example.backend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.example.backend.model.Logo;
import com.example.backend.model.Planner;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class TaskExecutorService {

    private TaskScheduler scheduler;
    private Double discountPercent;
    private List<Logo> logoDiscount = new ArrayList<>();
    private List<Planner> plannerDiscount = new ArrayList<>();
    private final LogoService logoService;
    private final PlannerService plannerService;

    public TaskExecutorService(LogoService logoService, PlannerService plannerService) {
        this.logoService = logoService;
        this.plannerService = plannerService;
    }

    private Runnable createRunnable(final double percent, List<Logo> logos, 
    LogoService logoService, List<Planner> planners, PlannerService plannerService){

        Runnable aRunnable = new Runnable(){
            public void run(){
                System.out.println("Setting discount");
                for (Logo l : logos) {
                    l.setDiscount(percent);
                    logoService.saveLogo(l);
                }
                for (Planner p : planners) {
                    p.setDiscount(percent);
                    plannerService.savePlanner(p);
                }
            }
        };
        return aRunnable;
    }

    @Async
    public void executeTaskT(Long timestampInMillisec, Double percent, List<Planner> planners, List<Logo> logos) {
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);
        this.discountPercent = percent;
        this.logoDiscount = logos;
        this.plannerDiscount = planners;
        Runnable exampleRunnable = createRunnable(discountPercent, logoDiscount, logoService, plannerDiscount, plannerService);
        // get products, set discount on them

        scheduler.schedule(exampleRunnable,
                new Date(timestampInMillisec));
    }
}