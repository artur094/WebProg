$(document).ready(function() {   
    //containTabPrezzi   containTabInfo   containTabOrariContatti containTabSale containTabPreview
    $("#homeLinkPrezzi").on("click", function(){
       //alert("ciao");
        $("#containTabInfo").fadeOut( "slow", function() { $("#containTabPrezzi").fadeIn( "slow", function() { });});
        $("#containTabOrariContatti").fadeOut( "slow", function() { $("#containTabPrezzi").fadeIn( "slow", function() { });});
        $("#containTabSale").fadeOut( "slow", function() { $("#containTabPrezzi").fadeIn( "slow", function() { });});
        $("#containTabPreview").fadeOut( "slow", function() { $("#containTabPrezzi").fadeIn( "slow", function() { });});
    });
                               
    
    $("#btnAccedi").on("click", function(){ 
        alert("sicuro divoler uscire?!"); 
    }); //end btnAccedi.click
}); //end doc ready();    