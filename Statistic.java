public class Statistic implements Observer{
    protected int count = 0; 

    @Override
    public void onAddElement(String element){
        count++;
        return;
    }

    public void onPrintResult(){
        System.out.println("Total items added: " + count);
    }
}
