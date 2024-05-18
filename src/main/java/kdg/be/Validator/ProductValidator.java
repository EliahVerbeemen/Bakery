package kdg.be.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolation;
import kdg.be.Models.Ingredient;
import kdg.be.Models.PurchaseOrder.PurchaseProduct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import jakarta.validation.executable.ExecutableValidator;
import jakarta.validation.metadata.BeanDescriptor;
import kdg.be.Models.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Qualifier("ProductValidator")
public class ProductValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        {
            return Product.class.equals(clazz)|| PurchaseProduct.class.equals(clazz);
        }
    }

    @Override
    public void validate(Object pr, Errors errors) {

        Product product= (Product) pr;
        if(product.getName().length()<3){

            errors.rejectValue("name","",new Object[]{"'name'"},"A name should at least be three letters long");
        }

        if(product.getSteps().stream().filter(a->a.length()!=0).toList().size()<3){

            errors.rejectValue("steps","",new Object[]{"'composition'"},"A valid recepy has three steps");
        }
       if(product.getAmounts().stream().anyMatch(a->a<=0)){

            errors.rejectValue("amounts","",new Object[]{"'amounts'"},"Quantities have to be positive numbers");
        }
        if(product.getComposition().size()<2){

            errors.rejectValue("composition","",new Object[]{"'composition'"},"Products should consist of at least 2 ingredients");
        }
        else {

            Map<Ingredient, Integer> m = new HashMap<>();
            product.getComposition().forEach(p -> {
                if (m.containsKey(p)) {
                    int k = m.get(p) + 1;
                    m.replace(p, k);

                } else {
                    m.put(p, 1);
                }


            });



            Map<Ingredient,Integer> ing=m.entrySet().stream().filter((k)->k.getValue()>1).collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));

            if(ing.size()>0){

                errors.rejectValue("composition","",new Object[]{"'composition'"},"Some ingredients are mentioned twice");

            }


        }
System.out.println("check");


        if(product.getAmounts().size()==0&&product.getComposition().size()>0){
    errors.rejectValue("composition","",new Object[]{"'composition'"},"Some ingredients are mentioned twice");



}
        product.getAmounts().forEach(ee->{


            if(ee.isNaN()){

                errors.rejectValue("composition","",new Object[]{"'composition'"},"Some ingredients are mentioned twice");
System.out.println("check2");

            }

        });



        }










    @Override
    public Errors validateObject(Object pr) {



        return Validator.super.validateObject(pr);
    }
}
