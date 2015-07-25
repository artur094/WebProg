$( document ).ready(function() {
    //alert('ciao');
    var heroes = $('.heroes');
    var poltrona = $('.poltrona');
    var prenotati = 0;
    var maxPrenotati = 10;
    //alert(cars.children().eq(0).hasClass('locked'));

   //     alert('ciao');
    $('.poltrona').each(function(){
        $(this).on("click", function(){
            if($(this).children().eq(1).hasClass('prenotato'))
            {
                $(this).children().eq(1).removeClass('prenotato'); $(this).children().eq(3).removeClass('prenotato');
                //checkCar($(this).parent());
                prenotati--;
            }
            else
            {
                if(!$(this).children().eq(1).hasClass('taken') && !$(this).children().eq(1).hasClass('locked'))
                {
                    if(prenotati<maxPrenotati)
                    {
                        $(this).children().eq(1).addClass('prenotato'); $(this).children().eq(3).addClass('prenotato');
                        //checkCar($(this).parent());
                        prenotati++;
                    }
                    else
                    {
                        alert('Hai prenotato troppi posti');
                    }
                }
            }
            
        });
    });
    
});