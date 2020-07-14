
import java.util.Iterator;
import java.util.List;

public interface DataBoard<E extends Data>{

    /* 
        Crea l’identità ad una categoria di dati
        @requires categoria non esiste in lista &&
         	categoria != null && passw == pass della board && passw != null
        @param categoria, il name della categoria
        @param passw, la password della board
        @throws KeyAlreadyExists se c'è già una categoria nella board con l'attributo name == categoria
        @modifies this.categorie
    */
	public void createCategory(String category, String passw) throws KeyAlreadyExists;
	/*
        Rimuove l’identità ad una categoria di dati
        @requires categoria esiste in lista &&
        	categoria != null && passw == pass della board && passw != null
        @param categoria, il name della categoria
        @param passw, la password della board
        @modifies this.categorie
        @throws KeyAlreadyExists se la categoria esiste già
    */
	public void removeCategory(String category, String passw) throws CategoryAlreadyRemoved;

	/*
        Aggiunge un amico ad una categoria di dati
        @requires categoria != null && amico != null &&  passw == pass della board && passw != null &&
            amico non esiste nella lista di amici della categoria &&
            categoria esiste in lista
    	@param categoria, il name della categoria
    	@param passw, la password della board
    	@param amico, il nome dell'amico da rimuovere dalla categoria
        @modifies this.categorie
        @throws CategoryAlreadyRemoved se la categoria non è stata trovata quindi gia stata rimossa/mai esistita
    */
	public void addFriend(String category, String passw, String friend) throws FriendAlreadyExists;
	/*
        Rimuove un amico da una categoria di dati
        @requires categoria != null && categoria esiste in lista 
        	&& amico != null && passw != null && passw == pass della board
            amico esiste nella lista di amici della categoria
    	@param categoria, il name della categoria
    	@param passw, la password della board
    	@param amico, il nome dell'amico da rimuovere dalla categoria
        @modifies this.categorie
        @throws FriendAlreadyExists se l'amico risulta gia aggiunto alla lista degli amici
    */
	public void removeFriend(String category, String passw, String friend) throws FriendAlreadyRemoved;

	/*
        Inserisce un dato in bacheca se vengono rispettati i controlli di identità
        @requires categoria != null && categoria è uguale ad un attributo name delle istanze presenti nella lista
        	&& dato != null && passw != null && passw == pass della board
            && la lista dei dati contenuti dalla categoria non contiene dato
        @param passw, la password della board
        @param dato, il dato da aggiungere
        @param categoria, il nome della categoria in cui aggiungerlo
        @modifies this
        @returns true se è andato a buon fine, false altrimenti
        @throws FriendAlreadyRemoved se l'amico non è presente in lista amici
	*/
	public boolean put(String passw, E dato, String category);

	/*
        Ottiene una copia del dato in bacheca se vengono rispettati i controlli di identità
        @requires dato != null && passw != null && passw == pass della board
        @param passw, la password della board
        @param dato, il dato da cercare
        @returns null se non è stato trovato, un copia del dato altrimenti
	*/	
	public E get(String passw, E dato);

	/*
        Rimuove il dato dalla bacheca se vengono rispettati i controlli di identità
        @requires dato != null && passw != null && passw == pass della board
        	&& il dato esiste in almeno una categoria
        @param passw, la password della board
        @param dato, il dato da rimuovere
        @modifies this.categorie
        @returns dato se il dato è stato rimosso, null altrimenti
	*/	
	public E remove(String passw, E dato) throws DataAlreadyRemoved;

	/*
        Crea la lista dei dati in bacheca su una determinata categoria se vengono rispettati i controlli di identità
        @requires categoria != null && passw != null && passw == pass della board
        	&& categoria è uguale ad un attributo name delle istanze presenti nella lista
        @param passw, la password della board
        @param categoria, la categoria di cui prendere la lista dei dati
        @returns lista dei dati della categoria richiesta
        @throws se il dato è gia stato rimosso/mai esistito
	*/
	public List<E> getDataCategory(String passw, String category);

	/*
        Restituisce un iteratore che genera tutti i dati in bacheca ordinati rispetto al numero di like.
        @requires passw == pass della board
        @param passw, la password della board
        @returns un iteratore che genera i dati in bacheca
    */
	public Iterator<E> getIterator(String passw);

	/*
        Aggiunge un like a un dato
        @requires data != null && data è contenuto in almeno una categoria &&
            amico != null && amico è contenuto dentro amici della relativa categoria
        @param amico, l'amico che mette il like
        @param data, il dato a cui è stato messo il like
        @modifies this.categorie
    */
	public void insertLike(String friend, E data) throws FriendAlreadyLiked;

	/*
        Legge un dato condiviso, restituisce un iteratore (senza remove) che genera tutti i dati in bacheca condivisi.
        @requires amico != null
        @param amico, l'amico di cui prendere i dati nelle categorie a cui ha accesso
        @returns i dati a cui ha accesso un utente
        @throws FriendAlreadyLiked se l'amico ha gia inserito il suo like al data
	*/
	public Iterator<E> getFriendIterator(String friend);


}

