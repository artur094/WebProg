$(document).ready(function() {   
    $(".welcomeName").append(" Pippo Pasticcio");
    
    var film = {  //nomeFilm prezzo dataAquisto
        film1: "<tr id='ted'>               <td>ted</td>                <td>prezzo</td>   </tr>",
        film2: "<tr id='cattivissimoMe'>    <td>cattivissimoMe</td>     <td>prezzo</td>   </tr>",
        film3: "<tr id='godZIlla'>          <td>godzilla</td>           <td>prezzo</td>   </tr>",
        film4: "<tr id='lego'>              <td>lego</td>               <td>prezzo</td>   </tr>",
    };
    
    $("#tableAcquisti").append("<tr> <th>Titolo</th> <th>Prezzo</th> </tr>");
    $.each(film, function(nome, valore) {
        $("#tableAcquisti").append(valore);
    });
    
    $("#ted").on("click", function(){
        $(".containerInfoDeals").empty();
        //$(".containerInfoDeals").append("</br>");
        $(".containerInfoDeals").append("<p>storia di ted</p>");
    });
    
     $("#cattivissimoMe").on("click", function(){
        $(".containerInfoDeals").empty();
        //$(".containerInfoDeals").append("</br>");
        $(".containerInfoDeals").append("<p>storia cattivissima</p>");
    });                        
    
    $("#btnAccedi").on("click", function(){ 
        alert("sicuro divoler uscire?!"); 
    }); //end btnAccedi.click
}); //end doc ready();    