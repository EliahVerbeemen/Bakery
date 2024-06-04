package kdg.be.Controllers;

import kdg.be.Models.Batch;
import kdg.be.Models.BatchState;
import kdg.be.Models.Product;
import kdg.be.RabbitMQ.RabbitSender;
import kdg.be.Services.Interfaces.IBatchService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class BatchController {
    private final RabbitSender rabbitSender;
    IBatchService batchService;

    public BatchController(IBatchService batchService, RabbitSender rabbitSender) {
        this.batchService = batchService;
        this.rabbitSender = rabbitSender;
    }


    @GetMapping("/batch")
    public ModelAndView ShowCurrentBatches(Model model) {
        Batch notYetPrepared;
        List<Batch> optionalNotYetPreparedBatch = batchService.findBatchByState(BatchState.NOT_YET_PREPARED);
        if (optionalNotYetPreparedBatch.isEmpty()) {
            notYetPrepared = batchService.save(new Batch(BatchState.NOT_YET_PREPARED));
        } else {
            notYetPrepared = optionalNotYetPreparedBatch.get(0);
        }
        model.addAttribute("notYetPrepared", notYetPrepared);
        boolean canPrepare = notYetPrepared.getProductsinBatch().keySet().stream().anyMatch(e -> (e.getComposition().size() >= 2 && e.getSteps().size() >= 2 && e.getName().length() > 3));
        model.addAttribute("start", canPrepare);


        List<Batch> optionalInPreparation = batchService.findBatchByState(BatchState.IN_PREPARATION);
        if (optionalInPreparation.isEmpty()) {
            Batch inprep = new Batch(BatchState.IN_PREPARATION);
            Batch inprepreparation = batchService.save(inprep);
            model.addAttribute("inPreparation", inprepreparation);
        } else {
            model.addAttribute("inPreparation", optionalInPreparation);
        }
        return new ModelAndView("Batchen/Batch", (Map<String, ?>) model);
    }

    @GetMapping("/batch/batchReady/{batchId}")
    public String FinalizePreparation( @PathVariable int batchId) {
        Optional<Batch> optionalBatch = batchService.findBatchById((long) batchId);
        if (optionalBatch.isPresent()) {
            Batch batch = optionalBatch.get();
            batch.setBatchState(BatchState.PREPARED);
            batchService.saveOrUpdate(batch);
            return "redirect:/batch";
        }
        return "redirect:/batch";
    }


    private Batch filterBatch(Batch batch) {
        Map<Product, Integer> filteredBatch = new HashMap<>();
        Map<Product, Integer> OKBatch = new HashMap<>();
        batch.getProductsinBatch().forEach((p, q) -> {
            if (p.getComposition().size() < 2 || p.getSteps().size() < 3 || p.getName().length() < 3) {
                filteredBatch.put(p, q);
            } else {
                OKBatch.put(p, q);
            }
        });

        Batch batch1 = new Batch();
        batch1.setBatchDate(batch.getBatchDate());
        batch1.setProductsinBatch(filteredBatch);
        batch1.setBatchId(batch.getBatchId());
        batch1.setBatchState(BatchState.NOT_YET_PREPARED);
        batchService.saveOrUpdate(batch1);

        List<Batch> batchesInPrep = batchService.findBatchByState(BatchState.IN_PREPARATION);
        Batch batch2 = null;
        if (!batchesInPrep.isEmpty()) {
            batch2 = batchesInPrep.get(0);
            batch2.getProductsinBatch().putAll(OKBatch);
            batch2.setBatchState(BatchState.IN_PREPARATION);
            batch2 = batchService.saveOrUpdate(batch2);
        } else {
            batch2 = new Batch();
            batch1.setBatchDate(batch.getBatchDate());
            batch1.setProductsinBatch(OKBatch);
            batch1.setBatchId(batch.getBatchId() + 2);
            batch1.setBatchState(BatchState.IN_PREPARATION);
            batchService.saveOrUpdate(batch2);
        }


        return batch2;
    }

    @GetMapping("/batch/startpreparing/{batchId}")
    public String StartPeparation(Model model, @PathVariable int batchId) {
        Optional<Batch> optionalBatch = batchService.findBatchById((long) batchId);
        if (optionalBatch.isPresent()) {
            Batch batch = optionalBatch.get();
            Batch filteredBatch = this.filterBatch(batch);
            if (!filteredBatch.getProductsinBatch().isEmpty()) {
                rabbitSender.sendBatch(filteredBatch);
            }
        }
        return "redirect:/batch";
    }

    @Scheduled(cron = "0 0 22 ? * *")
    public void StartBakingAtTen() {
        batchService.findBatchByState(BatchState.NOT_YET_PREPARED).forEach(b -> {
            b.setBatchState(BatchState.PREPARED);
            batchService.saveOrUpdate(b);
        });
    }
}
