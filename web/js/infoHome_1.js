$(document).ready(function() {
    
    //$('.containTabPrezzi').hide(); //( "slow", function() { alert( "Animation complete." );} );
    //alert("ciao");
    
    
    //containTabPrezzi   containTabInfo   containTabOrariContatti containTabSale containTabPreview   
    var showIndex=0;
    
    $('#linkTabs span').on('click',function(){
        //alert('show='+showIndex + "   --   index="+$(this).index());
        if(showIndex == $(this).index())
        {
            //nothing
        }
        else
        {                         $('#contieniContainer').children().eq(showIndex).stop(true,true).fadeOut(100,function(){});
            $('#linkTabs span').eq(showIndex).removeClass('activeTab');
            showIndex = $(this).index();
            $('#contieniContainer').children().eq(showIndex).delay(250).fadeIn(100,function(){});
            $(this).addClass('activeTab');
        }
    });
    
    /////////////////////Login////////////////////////////////////
    
    var pos;
    $("#btnAccedi").on("click", function(){ 
        $('.blur').fadeIn( "slow", function() { 
            $('.form').fadeIn( "slow", function() { });
        pos = $('.form').css('width');
    });
        
        
        $('.form').find('input, textarea').on('keyup blur focus', function (e) {
          var $this = $(this),
              label = $this.prev('label');
              if (e.type === 'keyup') {
                    if ($this.val() === '') {
                  label.removeClass('active highlight');
                } else {
                  label.addClass('active highlight');
                }
            } else if (e.type === 'blur') {
                if( $this.val() === '' ) {
                    label.removeClass('active highlight'); 
                    } else {
                    label.removeClass('highlight');   
                    }   
            } else if (e.type === 'focus') {
              if( $this.val() === '' ) {
                    label.removeClass('highlight'); 
                    } 
              else if( $this.val() !== '' ) {
                    label.addClass('highlight');
                    }
            }
        });      
        $('.tab a').on('click', function (e) {
          e.preventDefault();
          $(this).parent().addClass('active');
          $(this).parent().siblings().removeClass('active');
          target = $(this).attr('href');
          $('.tab-content > div').not(target).hide();
          $(target).fadeIn(600);
            
            if($('.form').css('width')<pos){
                $('.form').css('left', '36%');
                $('.form').css('right','36%');}
            else{
                $('.form').css('left', '25%');
                $('.form').css('right','25%');}
        });
        $(".blur").on('click',function(){
            
            $('.form').fadeOut("slow", function(){
                        $('.blur').fadeOut("slow",function(){});
            });
         });
    });//end btn accedi
    
}); //end doc funz