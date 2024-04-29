package kdg.be.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import jakarta.validation.executable.ExecutableValidator;
import jakarta.validation.metadata.BeanDescriptor;
import kdg.be.Models.Product;

import java.util.Set;
@Component
@Qualifier("ProductValidator")
public class ProductValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object pr, Errors errors) {

        Product product= (Product) pr;
        if(product.getName().length()<3){

            errors.rejectValue("name","",new Object[]{"'name'"},"A name should at least be three letters long");
        }

        if(product.getSteps().stream().filter(a->a.length()==0).toList().size()<3){

            errors.rejectValue("steps","",new Object[]{"'composition'"},"A valid recepy has three steps");
        }
       if(product.getAmounts().stream().anyMatch(a->Double.isNaN(a))){

            errors.rejectValue("amounts","",new Object[]{"'amounts'"},"Quantities have to be positive numbers");
        }
        if(product.getComposition().size()<2){

            errors.rejectValue("composition","",new Object[]{"'composition'"},"Products should consist of at least 2 ingredients");
        }

    }

    @Override
    public Errors validateObject(Object pr) {



        return Validator.super.validateObject(pr);
    }
}
