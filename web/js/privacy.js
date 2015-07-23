function confermaPrivacy(){
        $.post("Controller",
            {
             op:"confermaPrivacy"
            },function(){$('.privacy').hide()});
    }