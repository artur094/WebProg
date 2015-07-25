$( document ).ready(function() {
    //alert('ciao');
    var sweet = $('.sweet');
    var cuscini = $('.pillow');
    var prenotati = 0;
    var maxPrenotati = 10;
    //alert(cars.children().eq(0).hasClass('locked'));
    
    $('.pillow').each(function(){
        if($(this).hasClass('pillowR'))
        {
            if($(this).hasClass('prenotato'))
                $(this).css('background-image','url(img/PillowRPrenotato.PNG)');
            if($(this).hasClass('locked'))
                $(this).css('background-image','url(img/PillowRLocked.PNG)');
            if($(this).hasClass('taken'))
                $(this).css('background-image','url(img/PillowRTaken.PNG)');
        }
        if($(this).hasClass('pillowL'))
        {
            
            if($(this).hasClass('prenotato'))
                $(this).css('background-image','url(img/PillowLPrenotato.PNG)');
            if($(this).hasClass('locked'))
                $(this).css('background-image','url(img/PillowLLocked.PNG)');
            if($(this).hasClass('taken'))
                $(this).css('background-image','url(img/PillowLTaken.PNG)');
        }
        checkBed($(this).parent());
        //alert('ciao');
    });

    $('.pillow').each(function(){
        $(this).on("click", function(){
            if($(this).hasClass('prenotato'))
            {
                $(this).removeClass('prenotato');
                if($(this).hasClass('pillowR'))
                    $(this).css('background-image','url(img/PillowR.PNG)');
                if($(this).hasClass('pillowL'))
                    $(this).css('background-image','url(img/PillowL.PNG)');
                checkBed($(this).parent());
                prenotati--;
            }
            else
            {
                if(!$(this).hasClass('taken') && !$(this).parent().hasClass('taken') && !$(this).hasClass('locked') && !$(this).parent().hasClass('locked'))
                {
                    if(prenotati<maxPrenotati)
                    {
                        $(this).addClass('prenotato');
                        if($(this).hasClass('pillowR'))
                            $(this).css('background-image','url(img/PillowRPrenotato.PNG)');
                        if($(this).hasClass('pillowL'))
                            $(this).css('background-image','url(img/PillowLPrenotato.PNG)');
                        checkBed($(this).parent());
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

    function checkBed(c){
        //alert('entrato');
        var status="free";
        if(c.children().eq(1).hasClass('locked') && c.children().eq(2).hasClass('locked'))
            status="locked";
        if(c.children().eq(1).hasClass('taken') && c.children().eq(2).hasClass('taken'))
            status="taken";
        if(c.children().eq(1).hasClass('prenotato') && c.children().eq(2).hasClass('prenotato'))
            status="prenotato";
        else
        {
            ////////////qui da cambiare
            /*c.css('background-image','url(img/Bed2.PNG)');*/
        }
        switch(status)
        {
            case "free": c.removeClass('taken');c.removeClass('locked');c.removeClass('prenotato');c.children().eq(0).css('background-image','url(img/Bed2.PNG)');break;
            case "locked": c.removeClass('taken');c.addClass('locked');c.removeClass('prenotato');c.children().eq(0).css('background-image','url(img/BedLocked.PNG)');break;
            case "taken": c.addClass('taken');c.removeClass('locked');c.removeClass('prenotato');c.children().eq(0).css('background-image','url(img/BedTaken.PNG)');break;
            case "prenotato": c.removeClass('taken');c.removeClass('locked');c.addClass('prenotato');c.children().eq(0).css('background-image','url(img/BedPrenotato.PNG)');break;
            default: /*c.css('background-image','url(img/Bed2.PNG)');*/break;
        }
    }
    
});