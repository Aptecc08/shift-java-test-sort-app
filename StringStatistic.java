public class StringStatistic extends Statistic{
    int maxLength = Integer.MIN_VALUE;
    int minLength = Integer.MAX_VALUE;
    
    @Override
    public void onAddElement(String element){
        super.onAddElement(element);
        checkLength(element.length());
    }

    public void onPrintResult(){
        super.onPrintResult();
            printMinMax();
    }

    private void checkLength(int length){
        if(length < minLength)
            minLength = length;
        if(length > maxLength)
            maxLength = length;
    }

    private void printMinMax(){
        if (count == 0) 
            System.err.println("No data for string statistic");
        else
            System.out.println("Maximum line length: " + maxLength + " Minimum line length: " + minLength);
    }
}
