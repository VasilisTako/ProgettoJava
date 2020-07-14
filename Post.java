

public class Post extends Data {

   //Overview: classe che estende data ed implementa i metodi copia e display
    
    //COSTRUTTORE BASE PER INIZIALIZZAZIONE POST CON CONTENUTO E AUTORE
    public Post(String contenuto,String autore){
        super(contenuto,autore); //chiamo il costruttore di data usando la super visto che estendo la classe Data
    }
    //COSTRUTTORE PER COPIA POST CON VECCHIO POST GIA ESISTENTE
    public Post(Post post_esistente){
        super(post_esistente);//chiamo il costruttore di data usando la super visto che estendo la classe Data
    }
    @SuppressWarnings("unchecked") //per la rimozione del warning su Post copia

    //Implementazione dei metodi astratti di data
    
    public Post copia(){
        //Effects: chiamo il costruttore per post gia esistenti e gli passo this
        return new Post(this);
    }

    public String display(){
        //Effects: Stampa a schermo una stringa formattata con i dati del post
        return String.format("\nMi Piace: %s, Autore: %s, Contenuto: %s\n\n", this.likes,this.autore,this.contenuto);
    }
}