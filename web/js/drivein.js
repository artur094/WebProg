$( document ).ready(function() {
    //alert('ciao');
    var drivein = $('.drivein');
    var cars = $('.car');
    var prenotati = 0;
    var maxPrenotati = 10;
    //alert(cars.children().eq(0).hasClass('locked'));

    cars.children().each(function(){
        $(this).on("click", function(){
            if($(this).hasClass('prenotato'))
            {
                $(this).removeClass('prenotato');
                checkCar($(this).parent());
                prenotati--;
            }
            else
            {
                if(!$(this).hasClass('taken') && !$(this).parent().hasClass('taken') && !$(this).hasClass('locked') && !$(this).parent().hasClass('locked'))
                {
                    if(prenotati<maxPrenotati)
                    {
                        $(this).addClass('prenotato');
                        checkCar($(this).parent());
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

    function checkCar(c){
        //alert('entrato');
        var status="free";
        if(c.children().eq(0).hasClass('locked') && c.children().eq(1).hasClass('locked'))
            status="locked";
        if(c.children().eq(0).hasClass('taken') && c.children().eq(1).hasClass('taken'))
            status="taken";
        if(c.children().eq(0).hasClass('prenotato') && c.children().eq(1).hasClass('prenotato'))
            status="prenotato";
        else
        {
            c.css('background-image','url(img/car3.PNG)');
        }
        switch(status)
        {
            case "free": c.removeClass('taken');c.removeClass('locked');c.removeClass('prenotato');break;
            case "locked": c.removeClass('taken');c.addClass('locked');c.removeClass('prenotato');break;
            case "taken": c.addClass('taken');c.removeClass('locked');c.removeClass('prenotato');break;
            case "prenotato": c.removeClass('taken');c.removeClass('locked');c.css('background-image','url(img/userCar.PNG)');break;
            default: c.css('background-image','url(img/car3.PNG)');break;
        }
    }
    
});