import java.util.*;

public abstract class Data {

	/*
	 * AI: likes contiene la lista degli amici che hanno messo like al dato,
	 * contenuto contiene il contenuto del dato, mentre autore è il creatore del dato
	 * IR: autore non cambia mai e likes non contiene duplicati
	 */

    
    protected String autore; //Stringa che contiene l'autore del dato
    protected String contenuto;//Stringa che contiene il contenuto del dato
    protected List<String> likes; //Lista di stringhe contenente i nomi di chi ha messo like al dato


    /* 
        Costruttore per l'inizializzazione "da zero"
        @requires autore != null && contenuto != null
        @param contenuto, il contenuto del dato
        @param autore, l'autore del dato
    */
    public Data(String contenuto, String autore) throws NullPointerException{
        if(contenuto==null || autore == null) throw new NullPointerException("Non si puo istanziare un dato senza il contenuto o l'autore");

        this.autore=autore;
        this.contenuto=contenuto;
        this.likes= new ArrayList<String>();
    }
    /*
      Costruttore per la copia
      @requires post_esistente != null
      @param post_esistente, un'altra istanza di Data
    */
    public Data(Data post_esistente) throws NullPointerException{
        if(post_esistente==null) throw new NullPointerException("Non si puo fare una copia di un dato null");

        this.autore=post_esistente.autore;
        this.contenuto=post_esistente.contenuto;
        this.likes= new ArrayList<String>(post_esistente.likes);
    }
    
    public abstract <E extends Data> E copia(); //uso un metodo astratto per forzare l'implementazione della copia di un oggetto esteso da data
    public abstract String display(); //uso un metodo astratto per forzare l'implementazione di display

    /*
        @requires amico != null && likes non contiene amico
        @modifies this.likes
        @param amico, una stringa contenente il nome dell'amico che sta mettendo like
    */
    public void addLike(String amico) throws FriendAlreadyLiked{
        if(amico == null) throw new NullPointerException("Amico insesistente");
        if(this.likes.contains(amico)) throw new FriendAlreadyLiked("L'amico ha gia messo like al post");
        this.likes.add(amico);
    }
    /*
        @returns this.likes.size()
    */
    public int countLikes(){
        return this.likes.size();
    }


}
