public class NumericStatistic extends Statistic{
    private float max = -Float.MAX_VALUE;
    private float min = Float.MAX_VALUE;
    private float avarage = 0;
    private float summ = 0;

    @Override
    public void onAddElement(String element){
        float temp;
        try {
            temp = Float.parseFloat(element);
            super.onAddElement(element);
            updateParameters(temp);
        } catch (NumberFormatException e) {
             System.err.println("Invalid parse to float: " + element);
        }
    }

    public void onPrintResult(){
        super.onPrintResult();
            printMinMax();
    }

    private void updateParameters(float element){
        checkMinMax(element);
        summ += element;
        avarage = summ/count;
    }

    private void checkMinMax(float element){
        if(element < min)
            min = element;
        if(element > max)
            max = element;
    }

    private void printMinMax(){
        if (count == 0) 
            System.err.println("No data for numeric statistic");
        else
            System.out.println("Maximum: " + max + " Minimum: " + min + " Sum: " + summ + " Average: " + avarage);
    }
}
