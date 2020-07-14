import java.util.*;

public class TestBoard {

    public static void main(String[] args){

        MyDataBoard<Post> board_1= new MyDataBoard<Post>("145236");
        MyDataBoard2<Post> board_2=new MyDataBoard2<Post>("145236");
        System.out.println("\n\n----**** Board num. 1 ****----\n\n");
        Test(board_1);
        System.out.println("\n\n----**** Board num. 2 ****----\n\n");
        Test(board_2);

    }


    public static <T extends DataBoard<Post>> void Test(T board){


        Post post = new Post("Che bello essere qui dentro","Gianfranco");
        Post post2 = new Post("Sono davvero contento di fare il progetto","Studente");
        
        try{
            post.addLike("amico1");
            post.addLike("amico2");
            post.addLike("amico3");
            post2.addLike("Nemico1");
            post2.addLike("Nemico2");
            post2.addLike("Nemico3");
            post2.addLike("Nemico3");
        }
        catch (FriendAlreadyLiked e){
            System.out.println(e);
        }


        try{
            board.createCategory("Calcio", "145236");
            board.createCategory("Politica", "145236");
            board.createCategory("CalcioTennis", "145236");
        }
        catch(KeyAlreadyExists e){
            System.out.println(e);
        }


        try{
            board.addFriend("Calcio", "145236", "Beppe");
            board.addFriend("Calcio", "145236", "Autore");
            board.addFriend("Politica", "145236", "Beppe");
            board.addFriend("CalcioTennis", "145236", "Tizio");
        }
        catch(FriendAlreadyExists e){
            return;
        }

        // Aggiungo un dato e controllo se esiste prendendone una copia e stampandone il contenuto

        board.put("145236",post,"Politica");
        Post copiaGet = board.get("145236",post);
        
        StampaRisultato("Aggiunta di un dato ad una categoria, e successivo display di una copia del dato aggiunto\n\n", 
                        "\nMi Piace: [amico1, amico2, amico3], Autore: \"Gianfranco\", Contenuto: \"Peppe nudo sul bidet\"\n\n", 
                        copiaGet.display()
                        );


        
        //rimuovo un dato dalla board dato che non ho una categoria specifica in cui ricercare e controllo se esiste ancora cercando di crearne una copia 

        board.put("145236",post2,"Calcio");
        try{
            board.remove("145236", post2);
        }
        catch(DataAlreadyRemoved e){
            System.out.println(e);
            return;
        }

        Boolean copiaRemove = (null==board.get("145236",post2));
        ;
        StampaRisultato("Rimuovo un dato dalla board, e controllo se esiste ancora cercando di crearne una copia.Se true, il get del dato sulla board ha return 'null'.\n\n", 
                        "True", 
                        copiaRemove.toString()
                        );

        //rimuovo una categoria, poi provo a cercare/aggiungere un dato nella categoria gia rimossa


        Post post3 = new Post("Ciao mi chiamo Marco.", "Marco");
        board.put("145236", post3, "CalcioTennis");


        try{
            board.removeCategory("CalcioTennis", "145236");
        }
        catch(CategoryAlreadyRemoved e){
            System.out.println(e);
        }

        Boolean rim=false;//usata per il controllo dell'errore sulla categoria 

        try{
            board.put("145236", post, "CalcioTennis");
        }
        catch(NoExistingCategory e){
            rim=true;
        }

        StampaRisultato("Dopo aver rimosso la categoria provo ad aggiungerci un dato.Se true la categoria non esiste e quindi è impossibile aggiungerlo\n\n", 
                        "True", 
                        rim.toString()
                        );

        Boolean cercaElim = (null==board.get("145236",post3));

        StampaRisultato("Dopo aver rimosso la categoria provo ad estrapolare un dato.Se true la categoria non esiste e quindi è impossibile\n\n", 
                        "True", 
                        cercaElim.toString()
                        );


        //Aggiunta di un like ad un dato, verifica di getIterator


        try{
            board.createCategory("Amicizia","145236");
        }
        catch(KeyAlreadyExists e){
            System.out.println(e);
            return;
        }

        try{
            board.addFriend("Amicizia", "145236", "amico"); //aggiungo tra gli amici della categoria Amicizia, amico
            board.addFriend("Amicizia", "145236", "Gionni");
            board.addFriend("Amicizia", "145236", "Milla");
        }
        catch(FriendAlreadyExists e){
            System.out.println(e);
            return;
        }

        Post postLike = new Post("Sono marco amico di gianni", "Marco");
        Post postNuovo = new Post("Sono luca amico di amico","Luca");


        board.put("145236", postLike, "Amicizia");
        board.put("145236", postNuovo, "Amicizia");

        try{
            board.insertLike("amico", postLike);
            board.insertLike("Gionni", postLike);
            board.insertLike("Milla", postLike);
        }
        catch(FriendAlreadyLiked e){
            System.out.println(e);
            return;
        }
;
        Iterator<Post> iterator = board.getIterator("145236");
        
        String StringaGetIterator = new String();
        while(iterator.hasNext()){
            Post tmp=iterator.next();
            StringaGetIterator+=tmp.display()+"\n";
        }
        StampaRisultato("Provo ad utilizzare il getIterator sulla board 1\n",
                         "\nMi Piace: [], Autore: Luca, Contenuto: Sono luca amico di amico\n\n\nMi Piace: [amico1, amico2, amico3], Autore: Gianfranco, Contenuto: Peppe nudo sul bidet\n\n\nMi Piace: [amico, Gionni, Milla], Autore: Marco, Contenuto: Sono marco amico di gianni\n\n\n"
                        , StringaGetIterator);




        //Uso getData category
        
        //prima su una categoria esistente
        String StringaGetDataCategory = new String();
        List<Post> listaAmicizia=board.getDataCategory("145236", "Amicizia");
        for(Post iteratore:listaAmicizia){
            StringaGetDataCategory+=iteratore.display()+"\n";
        }
        StampaRisultato("Lista dati presenti nella categoria Amicizia\n", 
                        "\nMi Piace: [amico, Gionni, Milla], Autore: Marco, Contenuto: Sono marco amico di gianni\n\n\nMi Piace: [], Autore: Luca, Contenuto: Sono luca amico di amico\n\n\n", 
                        StringaGetDataCategory);
        //Poi su una non esistente
        Boolean err=false;
        try{
            board.getDataCategory("145236", "CategoriaInesistente");
        }
        catch(NoExistingCategory e){
            err=true;
        }
        StampaRisultato("Provo a prendere i dati di una categoria inesistente.True se non esiste. False se esite", "True", err.toString());


        //Uso getFriendIterator

        //Prima su qualcosa che esiste
        String StringGetFriendIterator=new String();
        board.put("145236", post2, "Calcio");//Inserisco nella categoria calcio(rimasta vuota post remove) il post2
        try{
            board.addFriend("Calcio", "145236", "amico");//successivamente lo aggiungo ad amico cosicchè da avere anche esso da stampare con l'iterator, oltre a postLike e postNuovo
        }
        catch(FriendAlreadyExists e){
            System.out.println(e);
            return;
        }
        Iterator<Post> Prova = board.getFriendIterator("amico");
        while(Prova.hasNext()){
            Post tmp=Prova.next();
            StringGetFriendIterator+=tmp.display()+"\n";
        }
        StampaRisultato("Stampo il getFriendIterator di amico", 
                        "\n\nMi Piace: [Nemico1, Nemico2, Nemico3], Autore: Studente, Contenuto: Sono davvero contento di fare il progetto\n\n\nMi Piace: [amico, Gionni, Milla], Autore: Marco, Contenuto: Sono marco amico di gianni\n\n\nMi Piace: [], Autore: Luca, Contenuto: Sono luca amico di amico\n\n", 
                        "\n"+StringGetFriendIterator);

        //Poi su un amico che non esiste

        Iterator<Post> Prova2 = board.getFriendIterator("amicoInsesistente");
        err= Prova2.hasNext()==false;//Vedo se ha un next, se non lo ha allora ritornerà false e indicherà che l'amico essendo insesitente non appartiene a nessuna categoria
        StampaRisultato("Prova di getFriendIterator su un amico inesistente", "true", err.toString());


        //Successivamente provo a rimuovere la visibilità della categoria Amicizia ad amico
        
        try{
            board.removeFriend("Amicizia", "145236", "amico");
        }
        catch(FriendAlreadyRemoved e){
            System.out.println(e);
            return;
        }
        String str=new String();
        Prova = board.getFriendIterator("amico");
        while(Prova.hasNext()){
            Post tmp=Prova.next();
            str+=tmp.display()+"\n";
        }

        StampaRisultato("Provo a rimuovere la visibilità della categoria Amicizia ad amico:", 
                        "\n\nMi Piace: [Nemico1, Nemico2, Nemico3], Autore: Studente, Contenuto: Sono davvero contento di fare il progetto\n\n", 
                        str);

        //Provo anche a rimuoverla ad un amico inesistente
        err=false;
        try{
            board.removeFriend("Amicizia", "145236", "amicoInsesistente");
        }
        catch(FriendAlreadyRemoved e){
            err=true;
        }
        StampaRisultato("Rimuovo un amico inesistente da una categoria. Mi aspetto true nel caso di errore", "true", err.toString());

        //Provo a mettere like allo stesso post due volte

        board.put("145236", post, "Calcio");
        err=false;
        try{
            board.addFriend("Calcio", "145236", "Luigi");
        }
        catch(FriendAlreadyExists e){
            System.out.println(e);
            return;
        }
        try{
            board.insertLike("Luigi", post);
            board.insertLike("Luigi", post);
        }
        catch(FriendAlreadyLiked e){
            err=true;
        }
        StampaRisultato("Provo a mettere like allo stesso post due volte.Errore=true", "True", err.toString());

    }

    private static void StampaRisultato(String descrizione,String ricercato,String reale){
        System.out.println(String.format("\nDescrizione: %s\nAspettato:\t{%s}\n\nReale:\t{%s}", descrizione,ricercato,reale));
    }
    
}