package com.example.backend.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.example.backend.model.Discount;
import com.example.backend.model.Product;
import com.example.backend.repository.DiscountRepository;

import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class DiscountTaskExecutorService {

    private TaskScheduler scheduler;
    ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
    private final DiscountService discountService;
    private final DiscountRepository discountRepository;
    private final ProductServiceImpl productServiceImpl;
    private Map<Integer, DataSet> runningTasks = new HashMap<>();
    private int taskKey = 0;

    public DiscountTaskExecutorService(DiscountRepository discountRepository, DiscountService discountService,
            ProductServiceImpl productServiceImpl) {
        this.discountRepository = discountRepository;
        this.discountService = discountService;
        this.productServiceImpl = productServiceImpl;
    }

    /*
     * :param: discount: Discount object, hold the percentage, a name and the
     * products on which the discount will be set. :param: enabled: int number, if
     * zero the discount on the products should be set to zero, if not zero the
     * discount on the products should be set of the percentage of the discount
     * object.
     */
    private Runnable createRunnable(Discount discount, int enabled) {
        Runnable aRunnable = new Runnable() {
            public void run() {
                boolean zero = enabled == 0 ? true : false;
                List<Product> ps = discount.getProducts().stream()
                        .map(prod_id -> productServiceImpl.getProduct(prod_id)).collect(Collectors.toList());
                discountService.setDiscountOnProducts(ps, discount.getPercent(), false);
                if (zero) {
                    discount.setEnabled(0);
                    discountRepository.save(discount);
                }
            }
        };
        return aRunnable;
    }

    /*
     * :param: discount: Discount object, hold the percentage, a name and the
     * products on which the discount will be set. :param: from - from where the
     * discount should be set. :param: to - until where the discount should be
     * unset.
     */
    @Async
    public Map<String, Map<String, String>> createTask(Discount discount, String from, String to) {
        scheduler = new ConcurrentTaskScheduler(localExecutor);
        Runnable fromRunnable = createRunnable(discount, 0);
        Runnable toRunnable = createRunnable(discount, 1);
        List<Long> productIds = discount.getProducts().stream().map(p -> p).collect(Collectors.toList());
        List<String> productNames = discount.getProducts().stream().map(p -> productServiceImpl.getProduct(p).getName())
                .collect(Collectors.toList());
        // add tasks to a map to be searched by index and increase key
        DataSet taskSet = new DataSet();
        taskSet.setId(discount.getId());
        taskSet.setName(discount.getName());
        taskSet.setFromTask(scheduler.schedule(fromRunnable, new Date(new Long(from))));
        taskSet.setToTask(scheduler.schedule(toRunnable, new Date(new Long(to))));
        taskSet.setPercent(discount.getPercent());
        taskSet.setProductNames(productNames);
        taskSet.setProductIDs(productIds);
        this.runningTasks.put(this.taskKey, taskSet);
        this.taskKey++;
        return getTasks();
    }

    @Async
    public Map<String, Map<String, String>> cancelTask(final String index) {
        final List<Map.Entry<Integer, DataSet>> tasks = this.runningTasks.entrySet().stream()
                .filter(d -> String.valueOf(d.getValue().getId()).equals(index)).collect(Collectors.toList());
        for (Map.Entry<Integer, DataSet> task : tasks) {
            task.getValue().getFromTask().cancel(false);
            task.getValue().getToTask().cancel(false);
            this.runningTasks.remove(task.getKey());
        }
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
            temp.put("id", tempSet.getId().toString());
            temp.put("name", tempSet.getName());
            temp.put("percent", String.valueOf(tempSet.getPercent()));
            temp.put("enabled", String.valueOf(tempSet.getEnabled()));
            String nameString = tempSet.getProductNames().stream().collect(Collectors.joining(", "));
            temp.put("productNames", nameString);
            String idString = tempSet.getProductIDs().stream().map(p -> String.valueOf(p))
                    .collect(Collectors.joining(", "));
            temp.put("productIds", idString);
            taskCollection.put(String.valueOf(pair.getKey()), temp);
        }
        return taskCollection;
    }

    public boolean hasAnyProductFutureDiscount(List<Integer> products) {
        Iterator<Entry<String, Map<String, String>>> it = getTasks().entrySet().iterator();
        boolean found = false;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            List<String> productIds = new ArrayList<String>(Arrays.asList(((Map) pair.getValue()).get("productIds").toString().split(", ")));
            found = products.stream().anyMatch(p -> productIds.contains(String.valueOf(p)));
            break;
        }
        return found;
    }

    public void createTaskPreparator(final List<Integer> products, final String name, final int percent, final String from, final String to) {
        final List<Long> p = new ArrayList<>();
        for (int productId : products) {
            p.add(new Long(productId));
        }
        Discount d = new Discount(name, percent, 0, p);
        if (from.equals("null") || to.equals("null")) {
            d.setEnabled(1);
            discountService.saveDiscount(d);
            List<Product> ps = d.getProducts().stream().map(prod_id -> productServiceImpl.getProduct(prod_id)).collect(Collectors.toList());
            discountService.setDiscountOnProducts(ps, d.getPercent(), false);
        } else {
            discountService.saveDiscount(d);
            this.createTask(d, from, to);
        }
    }

    public class DataSet {
        private Long id;
        private ScheduledFuture<?> fromTask;
        private ScheduledFuture<?> toTask;
        private String name;
        private double percent;
        private int enabled;
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
        public void setEnabled(int enabled) {
            this.enabled = enabled;
        }
        public int getEnabled() {
            return this.enabled;
        }
    }
}