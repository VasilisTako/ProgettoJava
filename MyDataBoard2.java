import java.util.*;


class Category2<E extends Data> extends Category<E>{
    /*
     * AI: 'nome' è il nome della categoria, per il resto la specifica è uguale alla classe category che estende
     */
    public final String nome;

    Category2(String nome){
        super();
        this.nome=nome;
    }
}


public class MyDataBoard2<E extends Data> implements DataBoard<E> {

	/*
	 * AI: La password è la password che permette di modificare la bacheca e le chiavi di categorie
	 *  rappresentano i nomi delle categorie, mentre il loro valore rappresenta il contenuto,
	 *  che a sua volta contiene dei dati che estendono la classe Data
	 *  IR: password != null && Per ogni categoria dentro categorie categoriaI.name != categoriaJ.name
	 */

    private String pswd;
    private List<Category2<E>> categorie;

    /*Costruttore generale per la board
     *@requires: pswd != null
     *@param pswd, la password della board
     *@modifies: this
     */ 
    public MyDataBoard2(String pswd){
        if(pswd==null) throw new NullPointerException("Il campo password non puo essere vuoto. Inserisci correttamente una password per la board");
        this.pswd=pswd;
        this.categorie = new ArrayList<Category2<E>>();
    }
       
    /* 
        Crea l’identità ad una categoria di dati
        @requires categoria non è uguale a nessun attributo name delle istanze presenti nella lista &&
         	categoria != null && this.password == passw && passw != null
        @param categoria, il name della categoria
        @param passw, la password della board
        @throws KeyAlreadyExists se c'è già una categoria nella board con l'attributo name == categoria
        @modifies this.categorie
    */
    public void createCategory(String categoria, String passw) throws KeyAlreadyExists{
        if(passw==null) throw new NullPointerException();
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");
        if(categoria==null) throw new NullPointerException();
        if(this.newGet(categoria)!=null) throw new KeyAlreadyExists("Categoria gia stata creata");  
        this.categorie.add(new Category2<E>(categoria));
    }
	/*
        Rimuove l’identità ad una categoria di dati
        @requires categoria è uguale ad un attributo name delle istanze presenti nella lista &&
        	categoria != null && this.password == passw && passw != null
        @param categoria, il name della categoria
        @param passw, la password della board
        @modifies this.categorie
    */
    public void removeCategory(String categoria,String passw) throws CategoryAlreadyRemoved{
        if(passw==null) throw new NullPointerException();
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");
        if(categoria==null) throw new NullPointerException();
        if(this.newGet(categoria)==null) throw new NoExistingCategory("La categoria da rimuovere è gia stata rimossa/non è mai esistita");
        this.categorie.remove(this.categorie.indexOf(this.newGet(categoria)));
    }
	/*
        Aggiunge un amico ad una categoria di dati
        @requires categoria != null && amico != null && this.password == passw && passw != null &&
            amico non appartiene alla lista amici nell'istanza di Category2 associata al name categoria &&
            categoria è uguale ad un attributo name delle istanze presenti nella lista
    	@param categoria, il name della categoria
    	@param passw, la password della board
    	@param amico, il nome dell'amico da rimuovere dalla categoria
        @modifies this.categorie
    */
    public void addFriend(String categoria, String passw, String amico) throws FriendAlreadyExists{

        if(passw==null) throw new NullPointerException();
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");
        if(categoria==null) throw new NullPointerException();
        if(amico==null) throw new NullPointerException();
        if(this.newGet(categoria) == null) throw new NoExistingCategory("La categoria cercata non esiste");

        Category2<E> cat = this.newGet(categoria);
        cat.addFriend(amico);
    }
	/*
        Rimuove un amico da una categoria di dati
        @requires categoria != null && categoria è uguale ad un attributo name delle istanze presenti nella lista 
        	&& amico != null && passw != null && this.password == passw
            amico appartiene alla lista amici nell'istanza di Category2 associata alla chiave categoria
    	@param categoria, il name della categoria
    	@param passw, la password della board
    	@param amico, il nome dell'amico da rimuovere dalla categoria
    	@modifies this.categorie
    */

    public void removeFriend(String categoria,String passw,String amico) throws FriendAlreadyRemoved{
        if(categoria==null) throw new NullPointerException();
        if(passw==null) throw new NullPointerException();
        if(amico==null) throw new NullPointerException();
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");
        if(this.newGet(categoria) == null) throw new NoExistingCategory("La categoria ricercata è stata rimossa/non è mai esistita");

        Category2<E> cat = this.newGet(categoria);
        cat.removeFriend(amico);
    }
	/*
        Inserisce un dato in bacheca se vengono rispettati i controlli di identità
        @requires categoria != null && categoria è uguale ad un attributo name delle istanze presenti nella lista
        	&& dato != null && passw != null && this.password == passw
            && la lista dei dati contenuti dalla categoria non contiene dato
        @param passw, la password della board
        @param dato, il dato da aggiungere
        @param categoria, il nome della categoria in cui aggiungerlo
        @modifies this
        @returns true se è andato a buon fine, false altrimenti
    */
    public boolean put(String passw, E dato, String categoria){
        if(passw==null) throw new NullPointerException();
        if(dato==null) throw new NullPointerException();
        if(categoria==null) throw new NullPointerException();
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");
        if(this.newGet(categoria)==null) throw new NoExistingCategory("La categoria ricercata è stata rimossa/non è mai esistita");

        Category2<E> cat=this.newGet(categoria);

        try{
            cat.AddData(dato);
        }
        catch(DataAlreadyAdded e){
            return false;
        }
        return true;
    }
	/*
        Ottiene una copia del dato in bacheca se vengono rispettati i controlli di identità
        @requires dato != null && passw != null && this.password == passw
        @param passw, la password della board
        @param dato, il dato da cercare
        @returns null se non è stato trovato, un copia del dato altrimenti
    */
    public E get(String passw,E dato){
        if(passw==null) throw new NullPointerException();
        if(dato==null) throw new NullPointerException();
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");

        for(Category<E> categoria: this.categorie){ //uso solo la lista delle chiavi dell' hashmap
            if(categoria.dataExists(dato)){
                return categoria.getContent(dato);
            }
        }
        return null;
    }
	/*
        Rimuove il dato dalla bacheca se vengono rispettati i controlli di identità
        @requires dato != null && passw != null && this.password == passw 
        	&& il dato esiste in almeno una categoria
        @param passw, la password della board
        @param dato, il dato da rimuovere
        @modifies this.categorie
        @returns dato se il dato è stato rimosso, null altrimenti
    */
    public E remove(String passw,E dato) throws DataAlreadyRemoved{
        if(passw==null) throw new NullPointerException();
        if(dato==null) throw new NullPointerException();
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");  
        
        for(Category<E> categoria:this.categorie){
            if(categoria.dataExists(dato)){
                categoria.removeData(dato);
                return dato;
            }
        }
        throw new DataAlreadyRemoved("La categoria da rimuovere è gia stata rimossa/non è mai esistita");
        
    }
	/*
        Crea la lista dei dati in bacheca su una determinata categoria se vengono rispettati i controlli di identità
        @requires categoria != null && passw != null && this.password == passw 
        	&& categoria è uguale ad un attributo name delle istanze presenti nella lista
        @param passw, la password della board
        @param categoria, la categoria di cui prendere la lista dei dati
        @returns lista dei dati della categoria richiesta
    */
    public List<E> getDataCategory(String passw, String categoria){
        if(passw==null) throw new NullPointerException();
        if(categoria==null) throw new NullPointerException();
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");  
        if(this.newGet(categoria) == null) throw new NoExistingCategory("Categoria inesistente");

        return this.newGet(categoria).getContentList(); //ritorno una copia della lista dei dati in bacheca per una determinata categoria

    }
	/*
        Restituisce un iteratore (senza remove) che genera tutti i dati in bacheca ordinati rispetto al numero di like.
        @requires this.password == passw
        @param passw, la password della board
        @returns un iteratore che genera i dati in bacheca
    */

    public Iterator<E> getIterator(String passw){
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");  
        List<E> array = new ArrayList<E>();

        for(Category2<E> categoria:this.categorie){
            array.addAll(categoria.getContentList());
        }

        Collections.sort(array,new OrdinaPerLike<E>());
        return array.listIterator();
    }
	/*
        Aggiunge un like a un dato
        @requires data != null && data è contenuto in almeno una categoria &&
            amico != null && amico è contenuto dentro amici della relativa categoria
        @param amico, l'amico che mette il like
        @param data, il dato a cui è stato messo il like
        @modifies this.categorie
    */
    public void insertLike(String amico, E data) throws FriendAlreadyLiked{
        if(amico==null) throw new NullPointerException();
        if(data==null) throw new NullPointerException();

        for(Category2<E> categoria:this.categorie){
            if(categoria.friendExists(amico) && categoria.dataExists(data)){
                //if(categoria.getContentList().contains(data)) throw new FriendAlreadyLiked("L'amico ha gia messo like");
                categoria.AddLike(data, amico);
                
            }
        }
    }
	/*
        Legge un dato condiviso, restituisce un iteratore (senza remove) che genera tutti i dati in bacheca condivisi.
        @requires amico != null
        @param amico, l'amico di cui prendere i dati nelle categorie a cui ha accesso
        @returns i dati a cui ha accesso un utente
    */
    public Iterator<E> getFriendIterator(String amico){
    if (amico == null) throw new NullPointerException("L'amico non può essere null");

    List<E> array = new ArrayList<E>();

    for (Category2<E> categoria : this.categorie) {
        if (categoria.friendExists(amico)) array.addAll(categoria.getContentList());
    }

    return array.listIterator();
    }


	/*
	  	Cerca la categoria col nome uguale a key
	  	@returns null se non la trova, altrimenti l'istanza della categoria con name==key
	*/
    private Category2<E> newGet(String nome){
        for(Category2<E> iteratore:this.categorie){
            if(iteratore.nome == nome) return iteratore;
        }
        return null;
    }

}