package com.iarasantos.math;

import org.springframework.web.bind.annotation.PathVariable;

public class SimpleMath {
    public Double sum(@PathVariable("numberOne") Double numberOne, @PathVariable("numberTwo") Double numberTwo) {

        Double result = numberOne + numberTwo;
        return result;
    }

    public Double subtraction(@PathVariable("numberOne") Double numberOne, @PathVariable("numberTwo") Double numberTwo) {

        Double result = numberOne - numberTwo;
        return result;
    }

    public Double multiplication(@PathVariable("numberOne") Double numberOne, @PathVariable("numberTwo") Double numberTwo) {

        Double result = numberOne * numberTwo;
        return result;
    }

    public Double division(@PathVariable("numberOne") Double numberOne, @PathVariable("numberTwo") Double numberTwo) {

        Double result = numberOne / numberTwo;
        return result;
    }

    public Double mean(@PathVariable("numberOne") Double numberOne, @PathVariable("numberTwo") Double numberTwo) {

        Double result = (numberOne + numberTwo) / 2;
        return result;
    }

    public Double squareRoot(@PathVariable("number") Double number) {

        Double result = Math.sqrt(number);
        return result;
    }
}
