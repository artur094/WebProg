$( document ).ready(function() {

    var slider = $('.slider');
    var leftArrow = $('.slider-left');
    var rightArrow = $('.slider-right');
    var indexShow = 0;
    var rounded = $('.containerIndex');
    var timerScroll;
    timeScroll();

    leftArrow.on("click", function(){
        if(indexShow>0){
            $('.slider-img').animate({left:"+=10%"});
            rounded.children().eq(indexShow).removeClass("activeIndex");
            indexShow -= 1;
            rounded.children().eq(indexShow).addClass("activeIndex");
        }
        else
        {
            $('.slider-img').animate({left:"-=90%"});
            rounded.children().eq(indexShow).removeClass("activeIndex");
            indexShow = 9;
            rounded.children().eq(indexShow).addClass("activeIndex");
        }
        clearInterval(timerScroll);
        timeScroll();
    });

    rightArrow.on("click", function(){
        if(indexShow<9){
            $('.slider-img').animate({left:"-=10%"});
            rounded.children().eq(indexShow).removeClass("activeIndex");

            indexShow += 1;
            rounded.children().eq(indexShow).addClass("activeIndex");
        }
        else
        {
            $('.slider-img').animate({left:"+=90%"});
            rounded.children().eq(indexShow).removeClass("activeIndex");
            indexShow = 0;
            rounded.children().eq(indexShow).addClass("activeIndex");
        }
        clearInterval(timerScroll);
        timeScroll();
    });

    $('.roundedIndex').each(function (index) {
        $(this).on("click", function(){
            rounded.children().eq(indexShow).removeClass("activeIndex");

            var tmp = indexShow - index;
            var v = (Math.abs(tmp)*10)+"%";
            //alert(tmp);
            if(tmp > 0)
                $('.slider-img').animate({left:"+="+v});
            else
                $('.slider-img').animate({left:"-="+v});
            indexShow = index;
            rounded.children().eq(indexShow).addClass("activeIndex");
            //alert(index);
            clearInterval(timerScroll);
            timeScroll();
        });

    });
    
    function timeScroll(){
        timerScroll = setInterval(rightScroll,5000);
    }
            
    function rightScroll(){
        if(indexShow<9){
            $('.slider-img').animate({left:"-=10%"});
            rounded.children().eq(indexShow).removeClass("activeIndex");
            indexShow += 1;
            rounded.children().eq(indexShow).addClass("activeIndex");
        }
        else
        {
            $('.slider-img').animate({left:"+=90%"});
            rounded.children().eq(indexShow).removeClass("activeIndex");
            indexShow = 0;
            rounded.children().eq(indexShow).addClass("activeIndex");
        }
    }
});