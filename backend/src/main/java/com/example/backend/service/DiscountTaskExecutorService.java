package com.example.backend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.example.backend.dto.TaskDetailsDto;
import com.example.backend.model.Logo;
import com.example.backend.model.Planner;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class DiscountTaskExecutorService {

    private TaskScheduler scheduler;
    ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
    private Double discountPercent;
    private List<Logo> logoDiscount = new ArrayList<>();
    private List<Planner> plannerDiscount = new ArrayList<>();
    private final LogoService logoService;
    private final PlannerService plannerService;
    private Map<Integer, DataSet> runningTasks = new HashMap<>();
    private int taskKey = 0;

    public DiscountTaskExecutorService(LogoService logoService, PlannerService plannerService) {
        this.logoService = logoService;
        this.plannerService = plannerService;
    }

    private Runnable createRunnable(final double percent, List<Logo> logos, LogoService logoService,
            List<Planner> planners, PlannerService plannerService) {

        Runnable aRunnable = new Runnable() {
            public void run() {
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
    public Map<String, Map<String, String>> createTask(TaskDetailsDto dto) {
        scheduler = new ConcurrentTaskScheduler(localExecutor);
        this.discountPercent = dto.getPercent();
        this.logoDiscount = dto.getLogos();
        this.plannerDiscount = dto.getPlanners();
        Runnable fromRunnable = createRunnable(discountPercent, logoDiscount, logoService, plannerDiscount,
                plannerService);
        Runnable toRunnable = createRunnable(new Double(0), logoDiscount, logoService, plannerDiscount,
        plannerService);
        List<String> logoNames = 
        this.logoDiscount.stream()
                .map(Logo::getName)
                .collect(Collectors.toList());
        List<String> plannerNames = 
        this.plannerDiscount.stream()
                .map(Planner::getName)
                .collect(Collectors.toList());
        logoNames.addAll(plannerNames);
        List<Long> logoIDs = 
        this.logoDiscount.stream()
                .map(Logo::getId)
                .collect(Collectors.toList());
        List<Long> plannerIDs = 
        this.plannerDiscount.stream()
                .map(Planner::getId)
                .collect(Collectors.toList());
        logoIDs.addAll(plannerIDs);
        // add tasks to a map to be searched by index and increase key
        DataSet taskSet = new DataSet();
        taskSet.setFromTask(scheduler.schedule(fromRunnable, new Date(dto.getFrom())));
        taskSet.setToTask(scheduler.schedule(toRunnable, new Date(dto.getTo())));
        taskSet.setPercent(this.discountPercent);
        taskSet.setProductNames(logoNames);
        taskSet.setProductIDs(logoIDs);
        this.runningTasks.put(this.taskKey, taskSet);
        this.taskKey++;
        return getTasks();
    }

    @Async
    public Map<String, Map<String, String>> cancelTask(String index) {
        // mivan, ha az egyik már elindult
        DataSet taskSet = this.runningTasks.get(Integer.parseInt(index));
        boolean isDone = taskSet.getFromTask().isDone();
        if (isDone) {
            List<Long> ids = taskSet.getProductIDs();
            for (Long id : ids) {
                Logo l = logoService.findById(id);
                if (l == null) {
                    Planner p = plannerService.findById(id);
                    p.setDiscount(new Double(0));
                    plannerService.savePlanner(p);
                } else {
                    l.setDiscount(new Double(0));
                    logoService.saveLogo(l);
                }
            }
        } else {
            taskSet.getFromTask().cancel(false);
        }
        taskSet.getToTask().cancel(false);
        this.runningTasks.remove(Integer.parseInt(index));
        return getTasks();
    }

    public Map<String, Map<String, String>> getTasks() {
        Date date = new Date();
        long timeMilli = date.getTime();
        Map<String, Map<String, String>> taskCollection = new HashMap<>();
        Iterator<Map.Entry<Integer, DiscountTaskExecutorService.DataSet>> it = this.runningTasks.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, DiscountTaskExecutorService.DataSet> pair = it.next();
            Map<String, String> temp = new HashMap<>();
            DataSet tempSet = (DataSet) pair.getValue();
            temp.put("from", tempSet.getFromTask().getDelay(TimeUnit.MILLISECONDS) + timeMilli + "");
            temp.put("to", tempSet.getToTask().getDelay(TimeUnit.MILLISECONDS) + timeMilli + "");
            temp.put("percent", String.valueOf(tempSet.getPercent()));
            String nameString = tempSet.getProductNames().stream().collect(Collectors.joining(", "));
            temp.put("products", nameString);
            taskCollection.put(String.valueOf(pair.getKey()), temp);
        }
        return taskCollection;
    }

    public class DataSet {
        private ScheduledFuture<?> fromTask;
        private ScheduledFuture<?> toTask;
        private double percent;
        private List<String> productNames;
        private List<Long> productIDs;
        public void setFromTask(ScheduledFuture<?> scheduledFuture) {
            this.fromTask = scheduledFuture;
        }
        public void setToTask(ScheduledFuture<?> scheduledFuture) {
            this.toTask = scheduledFuture;
        }
        public void setPercent(double percent) {
            this.percent = percent;
        }
        public void setProductNames(List<String> names) {
            this.productNames = names;
        }
        public void setProductIDs(List<Long> ids) {
            this.productIDs = ids;
        }
        public ScheduledFuture<?> getFromTask() {
            return this.fromTask;
        }
        public ScheduledFuture<?> getToTask() {
            return this.toTask;
        }
        public double getPercent() {
            return this.percent;
        }
        public List<String> getProductNames() {
            return this.productNames;
        }
        public List<Long> getProductIDs() {
            return this.productIDs;
        }
    }
}