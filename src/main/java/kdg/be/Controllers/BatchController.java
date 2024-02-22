package kdg.be.Controllers;

import kdg.be.Managers.IBatchManager;
import kdg.be.Models.Batch;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class BatchController {


    IBatchManager iBatchManager;

    public BatchController(IBatchManager iBatchManager) {
        this.iBatchManager = iBatchManager;
    }


    @GetMapping(value={"/batch","/batch"})
    //Werken met kleine letter
    public ModelAndView ToonBatch(@RequestParam(required = false  )@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localdate) {
        System.out.println(localdate);
        ModelAndView modelAndView = new ModelAndView("Batchen/Batch");
        if (localdate == null) localdate=LocalDate.now();
          if (!iBatchManager.VindBatchOpDatum(localdate).isPresent()) {
            Batch batch = new Batch(localdate);
            iBatchManager.save(batch);
            modelAndView.addObject("Batch", batch);

        } else {
          Batch batch= iBatchManager.VindBatchOpDatum(localdate).get();
            modelAndView.addObject("Batch", batch);

        }

        return modelAndView;
    }


}
