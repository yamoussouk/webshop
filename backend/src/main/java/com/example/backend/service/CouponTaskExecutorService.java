package com.example.backend.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.example.backend.model.Coupon;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class CouponTaskExecutorService {

    private TaskScheduler scheduler;
    ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();

    private final Map<Integer, DataSet> runningTasks = new HashMap<>();
    private int taskKey = 0;
    private CouponService couponService;

    public CouponTaskExecutorService(CouponService couponService) {
        this.couponService = couponService;
    }

    private Runnable createRunnable(final Coupon coupon, final int enabled, CouponService couponService, int idx) {
        final Runnable aRunnable = new Runnable() {
            public void run() {
                coupon.setEnabled(enabled);
                couponService.saveCoupon(coupon);
            }
        };
        if (idx != 0) {
            runningTasks.remove(idx);
        }
        return aRunnable;
    }

    @Async
    public Map<String, Map<String, String>> createTask(final Map<String, Object> details) {
        String couponName = (String) details.get("name");
        if (couponService.getCouponByName(couponName) == null) {
            // create the coupon
            int couponPercent = (int) details.get("percent");
            final int enabled = (int) details.get("enabled");
            Coupon coupon = new Coupon(couponName, couponPercent, enabled);
            couponService.saveCoupon(coupon);
            scheduler = new ConcurrentTaskScheduler(localExecutor);
            final String from = (String) details.get("from");
            final String to = (String) details.get("to");
            final Runnable fromRunnable = createRunnable(coupon, 0, this.couponService, 0);
            final Runnable toRunnable = createRunnable(coupon, 1, this.couponService, this.taskKey);
            final DataSet taskSet = new DataSet();
            taskSet.setFromTask(scheduler.schedule(fromRunnable, new Date(new Long(from))));
            taskSet.setToTask(scheduler.schedule(toRunnable, new Date(new Long(to))));
            taskSet.setId(coupon.getId());
            taskSet.setName(coupon.getName());
            taskSet.setEnabled(enabled);
            taskSet.setPercent(couponPercent);
            this.runningTasks.put(this.taskKey, taskSet);
            this.taskKey++;
            return getTasks();
        } else {
            return null;
        }
    }

    @Async
    public Map<String, Map<String, String>> cancelTask(final String index, CouponService couponService) {
        // mivan, ha az egyik már elindult
        final DataSet taskSet = this.runningTasks.get(Integer.parseInt(index));
        final boolean isDone = taskSet.getFromTask().isDone();
        if (isDone) {
            Coupon coupon = couponService.getCouponByName(taskSet.getName());
            coupon.setEnabled(0);
        } else {
            taskSet.getFromTask().cancel(false);
        }
        taskSet.getToTask().cancel(false);
        this.runningTasks.remove(Integer.parseInt(index));
        return getTasks();
    }

    public Map<String, Map<String, String>> getTasks() {
        final Date date = new Date();
        final long timeMilli = date.getTime();
        final Map<String, Map<String, String>> taskCollection = new HashMap<>();
        final Iterator<Map.Entry<Integer, CouponTaskExecutorService.DataSet>> it = this.runningTasks.entrySet().iterator();
        while (it.hasNext()) {
            final Map.Entry<Integer, CouponTaskExecutorService.DataSet> pair = it.next();
            final Map<String, String> temp = new HashMap<>();
            final DataSet tempSet = (DataSet) pair.getValue();
            temp.put("from", tempSet.getFromTask().getDelay(TimeUnit.MILLISECONDS) + timeMilli + "");
            temp.put("to", tempSet.getToTask().getDelay(TimeUnit.MILLISECONDS) + timeMilli + "");
            temp.put("id", tempSet.getId().toString());
            temp.put("name", tempSet.getName());
            temp.put("percent", String.valueOf(tempSet.getPercent()));
            temp.put("enabled", String.valueOf(tempSet.getEnabled()));
            taskCollection.put(String.valueOf(pair.getKey()), temp);
        }
        return taskCollection;
    }

    public class DataSet {
        private ScheduledFuture<?> fromTask;
        private ScheduledFuture<?> toTask;
        private Long id;
        private String name;
        private int percent;
        private int enabled;
        public void setFromTask(final ScheduledFuture<?> scheduledFuture) {
            this.fromTask = scheduledFuture;
        }
        public void setToTask(final ScheduledFuture<?> scheduledFuture) {
            this.toTask = scheduledFuture;
        }
        public ScheduledFuture<?> getFromTask() {
            return this.fromTask;
        }
        public ScheduledFuture<?> getToTask() {
            return this.toTask;
        }
        public Long getId() {
            return this.id;
        }
        public void setId(final Long id) {
            this.id = id;
        }
        public String getName() {
            return this.name;
        }
        public void setName(final String name) {
            this.name = name;
        }
        public void setPercent(int percent) {
            this.percent = percent;
        }
        public void setEnabled(int enabled) {
            this.enabled = enabled;
        }
        public int getPercent() {
            return this.percent;
        }
        public int getEnabled() {
            return this.enabled;
        }
    }
}