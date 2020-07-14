import java.util.*;


class Category<E extends Data>{

	/*
	 * AI: 'amici' è la lista di amici che possono leggere e interagire con i dati contenuti
	 * dentro contenuti, il quale a sua volta conterrà dei dati che estendono Data
	 * IR: Per ogni friendI, friendJ € friends_allowed con I!=J, friendI!=friendJ, stessa cosa per
	 * contenuti 
	 */
     private List<E> contenuti; //lista che contiene dati che estendono Data 
     private List<String> amici; //lista di amici che possono leggere e interagire con i dati presenti nella lista 'contenuti'
     //Costruttore base
     
     Category(){
         this.contenuti= new ArrayList<E>();
         this.amici= new ArrayList<String>();
     }
    /*
        Aggiunge un amico all'istanza
        @requires amico != null && amici non contiene amico
        @param amico, l'amico da aggiungere dentro la lista amici
        @modifies this.amici
        @throws FriendAlreadyExists se l'amico esiste già nella lista
    */
     public void addFriend(String amico) throws FriendAlreadyExists{
         if(amico==null) throw new NullPointerException("L'amico deve esistere per essere aggiunto.");
         if(this.amici.contains(amico)) throw new FriendAlreadyExists("L'amico esiste già all'interno della lista di amici con permessi");
         this.amici.add(amico);
     }
 
    /*
        Rimuove un'amico dall'istanza
        @requires amico != null && amici contiene amico
        @param amico, l'amico da rimuovere dentro la lista amici
        @modifies this.amici
    */
 
     public void removeFriend(String amico) throws FriendAlreadyRemoved{
         if(amico==null) throw new NullPointerException("L'amico deve esistere per essere rimosso.");
         if(!this.amici.contains(amico)) throw new FriendAlreadyRemoved("L'amico non è presente in lista quindi non rimuovibile.");
         this.amici.remove(amico);
     }
 
    /*
        Controlla se un amico esiste nell'istanza
        @param amico, l'amico da ricercare dentro la lista amici
        @returns true se amici contiene amico, false altrimenti
    */
 
     public Boolean friendExists(String amico){
         if(amico==null) throw new NullPointerException("L'amico deve esistere per essere cercato");
         return this.amici.contains(amico);
     }
 
    /*
        Controlla se un dato esiste nell'istanza corrente
        #param data, il dato da ricercare dentro la lista contenuti
        @returns true se data è contenuto dentro contenti, false altrimenti
    */
 
     public Boolean dataExists(E data){
         return this.contenuti.contains(data);
     }
 
    /* 
        Aggiunge un dato all'istanza
        @requires data != null && contenuti non deve contenere data
        @param data, il dato da aggiungere dentro la lista contenuti
        @modifies this.contenuti
        @throws DataAlreadyAdded se data è già presente dentro contenuti
    */
 
     public void AddData(E data) throws DataAlreadyAdded{
         if (data==null) throw new NullPointerException("Data non puo essere null per essere aggiunto.");
         if(dataExists(data)) throw new DataAlreadyAdded("Il dato è stato gia aggiunto in lista alla categoria");
         this.contenuti.add(data);
     }
    /*
        Rimuove un dato dall'istanza
        @requires data!=null && dataExists(data)
        @param data, il dato da rimuovere dalla lista contenuti
        #modifies this.contenuti
        @throws DataAlreadyRemoved se il dato non è presente in this.contenuti
    */
     public void removeData(E data) throws DataAlreadyRemoved{
         if(data==null) throw new NullPointerException("Data non puo essere null per essere aggiunto.");
         if(!dataExists(data)) throw new DataAlreadyRemoved("Il dato è gia stato rimosso dalla lista della categoria");
         this.contenuti.remove(data);
     }
    /*
        Prende la copia di un dato all'interno dell'istanza
        @requires data != null && dataExists(data)
        @param data, il dato da cercare in contenuti e se esistente da ritornare in versione copia
        @returns la copia del data richiesto
    */
     public E getContent(E data){
         if(data==null) throw new NullPointerException("Il dato non puo essere null");
         if(!dataExists(data)) throw new DataNotExists("Il dato non è presente all' interno della lista");
         int idx=this.contenuti.indexOf(data);
         return this.contenuti.get(idx).copia();
     }
 
    /*
        Crea una copia della lista di dati nell'istanza
        @returns una copia dell'instanza di ArrayList contenente i dati pubblicati
    */
     public List<E> getContentList(){
         return new ArrayList<E>(this.contenuti);
     }
 
    /*
        Aggiunge un like ad un dato
        @requires data != null data è contenuto dentro contenuti && amico != null && amico non è già contenuto nei likes
        @param data, il dato al quale si l'amico aggiunge like
        @param amico, l'amico che mette like al dato
    	@modifies this.contenuti
    */
     public void AddLike(E data,String amico) throws FriendAlreadyLiked{
         if(amico==null) throw new NullPointerException("L'amico non puo esere null");
         if(data==null) throw new NullPointerException("Il dato non puo essere null");
         if(!this.dataExists(data)) throw new DataNotExists("Contenuto/Dato non esistente");
         
         int idx= this.contenuti.indexOf(data);
         this.contenuti.get(idx).addLike(amico);
 
     }
 }

public class MyDataBoard<E extends Data> implements DataBoard<E> {

	/*
	 * AI: La password è la password che permette di modificare la bacheca e le chiavi di categorie
	 *  rappresentano i nomi delle categorie, mentre il loro valore rappresenta il contenuto,
	 *  che a sua volta contiene dei dati che estendono la classe Data
	 *  IR: password != null
	 */

    private String pswd;
    private HashMap<String,Category<E>> categorie;

    /*Costruttore generale per la board
     *@requires: pswd != null
     *@param pswd, la password della board
     *@modifies: this
     */ 
    public MyDataBoard(String pswd){
        if(pswd==null) throw new NullPointerException("Il campo password non puo essere vuoto. Inserisci correttamente una password per la board");
        this.pswd=pswd;
        this.categorie = new HashMap<String, Category<E>>();
    }
       
    /* 
        Crea l’identità ad una categoria di dati
        @requires categoria non appartiene allo spazio delle chiavi di categorie && categoria != null && this.password == passw && passw != null
        @param categoria, il nome della categoria
        @param passw, la password della board
        @throws KeyAlreadyExists se c'è già una categoria nella board con l'attributo name == categoria
        @modifies this.categorie
    */
    public void createCategory(String categoria, String passw) throws KeyAlreadyExists{
        if(passw==null) throw new NullPointerException("La password non puo essere null");
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");
        if(categoria==null) throw new NullPointerException("La categoria non puo essere null");
        if(this.categorie.containsKey(categoria)) throw new KeyAlreadyExists("Categoria gia stata creata");
        this.categorie.put(categoria,new Category<E>());
    }
	/*
        Rimuove l’identità ad una categoria di dati
        @requires categoria € spazio delle chiavi di categories && categoria != null && this.password == passw && passw != null
    	@param categoria, il nome della categoria
        @param passw, la password della board
        @modifies this.categorie
        @throws CategoryAlreadyRemoved se la categoria era già stata rimossa / mai stata aggiunta
    */
    public void removeCategory(String categoria,String passw) throws CategoryAlreadyRemoved{
        if(passw==null) throw new NullPointerException("La password non puo essere null");
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");
        if(categoria==null) throw new NullPointerException("La categoria non puo essere null");
        if(!this.categorie.containsKey(categoria)) throw new NoExistingCategory("La categoria da rimuovere è gia stata rimossa/non è mai esistita");
        this.categorie.remove(categoria);
    }
	/*
        Aggiunge un amico ad una categoria di dati
        @requires categoria != null && amico != null && this.password == passw && passw != null &&
            amico non appartiene alla lista amici nell'istanza di Category associata alla chiave categoria &&
            categoria € spazio delle chiavi di categorie
    	@param categoria, il nome della categoria
        @param passw, la password della board
        @param amico, l' amico da aggiungere nella categoria 
        @modifies this.categorie
    */
    public void addFriend(String categoria, String passw, String amico) throws FriendAlreadyExists{

        if(passw==null) throw new NullPointerException("La password non puo essere null");
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");
        if(categoria==null) throw new NullPointerException("La categoria non puo essere null");
        if(amico==null) throw new NullPointerException("L'amico non puo essere null");
        if(!this.categorie.containsKey(categoria)) throw new NoExistingCategory("La categoria passata non esiste");

        Category<E> cate = this.categorie.get(categoria);
        cate.addFriend(amico);
    }

	/*
        Rimuove un amico da una categoria di dati
        @requires categoria != null && categoria € spazio delle chiavi di categorie && amico != null && passw != null && this.password == passw
            amico appartiene alla lista amici nell'istanza di Category associata alla chiave categoria
    	@param categoria, il nome della categoria
    	@param passw, la password della board
    	@param amico, il nome dell'amico da rimuovere dalla categoria
    	@modifies this.categorie
    	@throws FriendAlreadyRemoved se l'amico era già stato rimosso / mai stato aggiunto nella categoria
    */

    public void removeFriend(String categoria,String passw,String amico) throws FriendAlreadyRemoved{
        if(categoria==null) throw new NullPointerException("La categoria non puo essere null");
        if(passw==null) throw new NullPointerException("La password non puo essere null");
        if(amico==null) throw new NullPointerException("L'amico non puo essere null");
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");
        if(!this.categorie.containsKey(categoria)) throw new NoExistingCategory("La categoria ricercata è stata rimossa/non è mai esistita");

        Category<E> cate = this.categorie.get(categoria);
        cate.removeFriend(amico);
    }
	/*
        Inserisce un dato in bacheca se vengono rispettati i controlli di identità
        @requires categoria != null && categoria € spazio delle chiavi di categorie && dato != null && passw != null && this.password == passw
            && la lista dei dati contenuti dalla categoria non contiene dato
        @param categoria, il nome della categoria
    	@param passw, la password della board
    	@param dato, il dato da aggiungere alla categoria
    	@modifies this.categorie
    */
    public boolean put(String passw, E dato, String categoria){
        if(passw==null) throw new NullPointerException("La password non puo essere null");
        if(dato==null) throw new NullPointerException("Il dato non puo essere null");
        if(categoria==null) throw new NullPointerException("La categoria non puo essere null");
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");
        if(!this.categorie.containsKey(categoria)) throw new NoExistingCategory("La categoria ricercata è stata rimossa/non è mai esistita");

        Category<E> cate=this.categorie.get(categoria);

        try{
            cate.AddData(dato);
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
        if(passw==null) throw new NullPointerException("La password non puo essere null");
        if(dato==null) throw new NullPointerException("Il dato non puo essere null");
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");

        for(Category<E> categoria: this.categorie.values()){ //uso solo la lista delle chiavi dell' hashmap
            if(categoria.dataExists(dato)){
                return categoria.getContent(dato);
            }
        }
        return null;
    }

	/*
        Rimuove il dato dalla bacheca se vengono rispettati i controlli di identità
        @requires dato != null && passw != null && this.password == passw && il dato esiste in almeno una categoria
        @param passw, la password della board
        @param dato, il dato da rimuovere
        @modifies this.categorie
        @returns dato se il dato è stato rimosso
        @throws DataAlreadyRemoved se il dato non era presente in nessuna delle categorie
    */

    public E remove(String passw,E dato) throws DataAlreadyRemoved{
        if(passw==null) throw new NullPointerException("La password non puo essere null");
        if(dato==null) throw new NullPointerException("Il dato non puo essere null");
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");  
        
        for(Category<E> categoria:this.categorie.values()){
            if(categoria.dataExists(dato)){
                categoria.removeData(dato);
                return dato;
            }
        }
        throw new DataAlreadyRemoved("La categoria da rimuovere è gia stata rimossa/non è mai esistita");
        
    }
	/*
        Crea la lista dei dati in bacheca su una determinata categoria se vengono rispettati i controlli di identità
        @requires categoria != null && passw != null && this.password == passw && categoria € spazio delle chiavi di this.categorie
    	@param passw, la password della board
        @param categoria, la categoria di cui prendere la lista dei dati
        @returns lista dei dati della categoria richiesta
    */
    public List<E> getDataCategory(String passw, String categoria){
        if(passw==null) throw new NullPointerException("La password non puo essere null");
        if(categoria==null) throw new NullPointerException("La categoria non puo essere null");
        if(this.pswd!=passw) throw new PasswordErrata("Password errata");  
        if(!this.categorie.containsKey(categoria)) throw new NoExistingCategory("Categoria inesistente");

        return this.categorie.get(categoria).getContentList(); //ritorno una copia della lista dei dati in bacheca per una determinata categoria

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

        for(Category<E> categoria:this.categorie.values()){
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
    public void insertLike(String amico, E dato) throws FriendAlreadyLiked{
        if(amico==null) throw new NullPointerException("L'amico non può essere null");
        if(dato==null) throw new NullPointerException("Il dato non puo essere null");

        for(Category<E> categoria:this.categorie.values()){
            if(categoria.friendExists(amico) && categoria.dataExists(dato)){
                categoria.AddLike(dato, amico);
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

    for (Category<E> categoria : this.categorie.values()) {
        if (categoria.friendExists(amico)) array.addAll(categoria.getContentList());
    }

    return array.listIterator();
        
    }
}

/*Overview: Ritorna tmp1 se diff>=0, altrimenti tmp2  
 *Returns:  tipo di dato E
 */
class OrdinaPerLike<E extends Data> implements Comparator<E>{
    public int compare(E tmp1,E tmp2){
        return tmp1.countLikes() - tmp2.countLikes();
    }
}


