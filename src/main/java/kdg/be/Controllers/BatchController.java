package kdg.be.Controllers;

import kdg.be.Managers.IBatchManager;
import kdg.be.Models.Batch;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller
public class BatchController {


    IBatchManager batchMgr;

    public BatchController(IBatchManager batchMgr) {
        this.batchMgr = batchMgr;
    }


    @GetMapping(value = {"/batch", "/batch"})
    //Werken met kleine letter
    public ModelAndView ShowBatch(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localdate) {
        System.out.println(localdate);
        ModelAndView modelAndView = new ModelAndView("Batchen/Batch");
        if (localdate == null) localdate = LocalDate.now();
        if (!batchMgr.findBatchByDate(localdate).isPresent()) {
            Batch batch = new Batch(localdate);
            batchMgr.save(batch);
            modelAndView.addObject("Batch", batch);

        } else {
            Batch batch = batchMgr.findBatchByDate(localdate).get();
            modelAndView.addObject("Batch", batch);
        }
        return modelAndView;
    }


}
