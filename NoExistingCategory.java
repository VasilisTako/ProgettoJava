public class NoExistingCategory extends RuntimeException {
    
    private final static long serialVersionUID=8297592779731048132L;
    

    public NoExistingCategory(String str){
        super(str);
    }
}