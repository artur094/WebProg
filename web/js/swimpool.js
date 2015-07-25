$( document ).ready(function() {
    //alert('ciao');
    var swimpool = $('.swimpool');
    var belts = $('.belt');
    var prenotati = 0;
    var maxPrenotati = 10;
    //alert(cars.children().eq(0).hasClass('locked'));

    belts.each(function(){
        $(this).on("click", function(){
            if($(this).hasClass('prenotato'))
            {
                $(this).removeClass('prenotato');
              //  checkPool($(this).parent());
                prenotati--;
            }
            else
            {
                if(!$(this).hasClass('taken') && !$(this).parent().hasClass('taken') && !$(this).hasClass('locked') && !$(this).parent().hasClass('locked'))
                {
                    if(prenotati<maxPrenotati)
                    {
                        $(this).addClass('prenotato');
                       // checkPool($(this).parent());
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

    /*function checkPool(c){
        //alert('entrato');
        var status="free";
        if(c.children().eq(4).hasClass('locked') && c.children().eq(5).hasClass('locked') && c.children().eq(6).hasClass('locked')  && c.children().eq(7).hasClass('locked'))
            status="locked";
        if(c.children().eq(4).hasClass('taken') && c.children().eq(5).hasClass('taken') && c.children().eq(6).hasClass('taken') && c.children().eq(7).hasClass('taken'))
            status="taken";
        if(c.children().eq(4).hasClass('prenotato') && c.children().eq(5).hasClass('prenotato') && c.children().eq(6).hasClass('prenotato') && c.children().eq(7).hasClass('prenotato'))
            status="prenotato";
        else
        {
            c.css('background-image','url(img/belt.png)');
        }
        switch(status)
        {
            case "free": c.removeClass('taken');c.removeClass('locked');c.removeClass('prenotato');break;
            case "locked": c.removeClass('taken');c.addClass('locked');c.removeClass('prenotato');break;
            case "taken": c.addClass('taken');c.removeClass('locked');c.removeClass('prenotato');break;
            case "prenotato": c.removeClass('taken');c.removeClass('locked');c.css('background-image','url(img/userCar.PNG)');break;
            default: c.css('background-image','url(img/car3.PNG)');break;
        }
    }*/
    
});